package eg.edu.alexu.csd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

public class TestingTask implements Runnable {
    public static final int TEST_TIMEOUT = 60;
    
    private final File root;
    private final File[] sources;
    private final String course;
    private final String unitTest;
    private String summary;
    private String report;
    private List<CodeProfile> profile = new LinkedList<TestingTask.CodeProfile>();
    
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public static class CodeProfile {
        
        private String read(String path){
            String s = "";
            FileReader fr = null;
            try {
                fr = new FileReader(path);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null){
                    line = line.trim().toLowerCase().replaceAll(" |\\r|\\n|\\t|;", "");
                    if(line.contains("package") || line.contains("class") || line.startsWith("//") || line.startsWith("@"))
                        continue;
                    s += line;
                }
                return s;
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally
            {
                if (fr != null)
                {
                    try
                    {
                        fr.close();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return "***";
        }
        
        private String hash(String content){
            try {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.reset();
                byte[] buffer = content.getBytes("UTF-8");
                md.update(buffer);
                byte[] digest = md.digest();
                String hexStr = "";
                for (int i = 0; i < digest.length; i++) {
                    hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
                }
                return hexStr;
            } catch (Exception e) {
                return "***";
            }
        }
        
        private int wordCount(String content, String word){
            return (content.length() - content.replace(word, "").length()) / word.length();
        }
        
        public String repository;
        public String fileName;
        public String digest;
        public int forCount;
        public int ifCount;
        public int whileCount;
        public int bracesCount;
        public int equalsCount;
        public int primitivesCount;
        public int similar;
        
        public CodeProfile(String path) {
            String content = read(path);
            fileName = new File(path).getName();
            digest = hash(content);
            forCount = wordCount(content, "for");
            ifCount = wordCount(content, "if");
            whileCount = wordCount(content, "while");
            equalsCount = wordCount(content, "=");
            primitivesCount = wordCount(content, "int") + wordCount(content, "float") + wordCount(content, "double") + wordCount(content, "char") + wordCount(content, "string");
            bracesCount = wordCount(content, "(");
        }
        
        @Override
        public String toString() {
            return String.format("%02d", similar) + " : " + getSummary() + " : " + digest + " : " + fileName + " : " + repository;
        }
        
        public String getSummary() {
            return 
                String.format("%02d", forCount) + "-" +
                String.format("%02d", ifCount) + "-" +
                String.format("%02d", whileCount) + "-" +
                String.format("%03d", equalsCount) + "-" +
                String.format("%03d", primitivesCount) + "-" +
                String.format("%03d", bracesCount) 
                ;
        }
    }
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    
    public TestingTask(File root, File[] sources, String course, String unitTest){
        this.root = root;
        this.sources = sources;
        this.course = course;
        this.unitTest = unitTest;
        summary = "Timeout!";
        report = "{{{Sorry your code timesout in " + TEST_TIMEOUT + " seconds. It seems you are executing a dangerous code!}}}";
    }
    
    public String getSummary(){
        return summary;
    }
    
    public String getReport(){
        return report;
    }
    
    public List<CodeProfile> getCodeProfile(){
        return profile;
    }
    
    @Override
    public void run() {
        String resourcesDir = Environment.getResourceDir();
        List<String> jars = new LinkedList<String>();
        jars.add(resourcesDir + "/WEB-INF/lib/org.hamcrest.core_1.3.0.v201303031735.jar");
        jars.add(resourcesDir + "/WEB-INF/lib/junit.jar");
        for(String sysJar : System.getProperty("java.class.path").split(File.pathSeparatorChar + ""))
            jars.add(sysJar);
        ClassLoader classLoader = new DynamicClassLoader(root.getAbsolutePath(), jars);
        List<String> classes = new LinkedList<String>();
        String rootPath = root.getAbsolutePath().replaceAll("\\\\", "/") + "/";
        for (File source : sources)
            classes.add(source
                    .getAbsolutePath()
                    .replaceAll("\\\\", "/")
                    .replaceAll(rootPath, "").replaceAll("\\.java", "")
                    .replaceAll("/", "."));
        Class<?> test;
        try {
            
            
            // Changing the user.dir
            String originalUserDir = System.getProperty("user.dir");
            System.setProperty("user.dir", root.getAbsolutePath());
            String originalSystemDir = System.getProperty("java.class.path");
            System.setProperty("java.class.path", root.getAbsolutePath());
            //*/
            
            test = Class.forName("eg.edu.alexu.csd.TestRunner", true, classLoader);
            Method method = test.getMethod("run", String.class, String.class, List.class);
            String[] result = (String[]) method.invoke(null, resourcesDir, unitTest, classes);
            summary = result[0];
            report = result[1];
            String[] impls = result[2].split(";");
            if(impls!=null && impls.length>0) {
                for(String impl : impls) {
                    if(impl.trim().isEmpty())
                        continue;
                    //System.err.println(">>>>>> " + rootPath + impl.replaceAll("\\.", "/") + ".java");
                    profile.add(new CodeProfile(rootPath + impl.replaceAll("\\.", "/") + ".java"));
                }
            }
            
            
            // Restoring the user.dir
            System.setProperty("user.dir", originalUserDir);
            System.setProperty("java.class.path", originalSystemDir);
            //*/
            
        } catch (Throwable e) {
            e.printStackTrace();
            summary = "RunFail";
            report = "{{{Failed to create tests, please fix all compilation errors above first!}}}";
        }
    }
}