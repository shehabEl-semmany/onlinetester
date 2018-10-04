package eg.edu.alexu.csd;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

import eg.edu.alexu.csd.AbstractServlet.SummaryAuditListener.AuditError;
import eg.edu.alexu.csd.util.Environment;
import eg.edu.alexu.csd.util.FilesUtils;
import eg.edu.alexu.csd.util.TestingTask;

public abstract class AbstractServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void showError(Throwable e, ServletOutputStream out) throws IOException {
        if(e==null || out==null)
            return;
        e.printStackTrace();
        if (Environment.contants.get("Debug") == 1)
        	print("{{{Error: " + e + " " + e.getMessage(), out);
        else
        	print("{{{Error: " + e, out);
        if (Environment.contants.get("Debug") == 1) {
        	for (StackTraceElement trace : e.getStackTrace()) {
                print("\t" + trace.getClassName() + "."
                        + trace.getMethodName() + "(): Line "
                        + trace.getLineNumber(), out);
            }
        }
        print("}}}", out);
    }
    
    protected void print(String message, ServletOutputStream out)
            throws IOException {
        if(message==null || out==null)
            return;
        out.println(message);       
    }
    
    protected File checkout(int year, String repository, String branch, String project, ServletOutputStream out) throws IOException{
        ///*
    	if(branch==null)
            branch = "master";
        String resourcesDir = Environment.getResourceDir();
        File tempDir = new File(resourcesDir, "temp");
        File repositoryDir = new File(tempDir, repository);
        if (!repositoryDir.exists())
            repositoryDir.mkdirs();
        for(File oldDir : repositoryDir.listFiles())
            if(oldDir.isDirectory() && oldDir.lastModified() < System.currentTimeMillis() - 1000*60*10) // delete temp directories older than 5 minutes
                FilesUtils.delete(oldDir);
        File workDir = new File(repositoryDir, String.valueOf(System.currentTimeMillis()));
        FilesUtils.delete(workDir);
        workDir.mkdirs();
       
        if (Environment.contants.get("Server_Status") == 0) {
        	print("Server has been stopped temporary by the Admin", out);
            return null;
        }
        
        try {
            String[] args = new String[] {
                    Environment.getGitCommand(),
                    "clone",
                    "https://" + Environment.getBitbucketLogin(year) + "@bitbucket.org/" + repository + ".git",
                    "-b",
                    branch,
                    workDir.getAbsolutePath()
                };
            System.out.println(Arrays.toString(args));
            Runtime.getRuntime().exec(args).waitFor(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            print("Checkout failed!", out);
            if (Environment.contants.get("Debug") == 1)
                showError(e, out);
        }
        
        if(project != null && project.length() > 0) {
            File projectDir = new File(workDir, project);
            if(projectDir.exists() && projectDir.exists()) {
                workDir = projectDir;
            }
        }
        
        File root = FilesUtils.find(workDir, "src");
        if(root == null){
            print("Checkout failed or timedout!", out);
            print("Failed to find the source directory ('src' folder) !", out);
            print("Please check your repo name and project name", out);
            print("Please double check Bitbucket access permission to the online tester account", out);
            return null;
        }
        return root;
        //*/
        
        //return new File("C:\\Users\\ahmed hamdy\\Desktop\\tokaelgindy\\data-structure\\src");
    }
    
    protected void addTests(File root, String course, ServletOutputStream out) throws IOException{
        String resourcesDir = Environment.getResourceDir();
        try {
            FilesUtils.copy(
                    new File(resourcesDir + "/WEB-INF/res/eg/edu/alexu/csd/" + course), 
                    new File(root.getAbsolutePath() + "/eg/edu/alexu/csd/" + course));
            FilesUtils.copy(
                    new File(resourcesDir + "/WEB-INF/res/eg/edu/alexu/csd/TestRunner.java"), 
                    new File(root.getAbsolutePath() + "/eg/edu/alexu/csd/TestRunner.java"));
        } catch (Throwable e) {
            e.printStackTrace();
            print("Please check your packages organization", out);
            if (Environment.contants.get("Debug") == 1)
                showError(e, out);
        }
    }

    private String relative(File root, File child){
        return child.getAbsolutePath().replaceAll("\\\\", "/")
        .replaceAll(
                root.getAbsolutePath().replaceAll("\\\\", "/"),
                "");
    }
    protected String normalize(File root, File... sources) {
        return Arrays.toString(sources)
                .replaceAll("\\\\", "/")
                .replaceAll(
                        root.getAbsolutePath().replaceAll("\\\\", "/"),
                        "");
    }
    
    protected int checkstyle(int year, File root, File[] sources, ServletOutputStream out) throws IOException {
        try {
            String resourcesDir = Environment.getResourceDir();
            File resRoot = new File(resourcesDir + "/WEB-INF/res");
            Set<String> exclude = new HashSet<String>();
            for(File f : FilesUtils.getFiles(resRoot, ".java"))
                exclude.add(relative(resRoot, f));
            
            List files = Lists.newLinkedList();
            for(File s : sources)
                if(!exclude.contains(relative(root, s)))
                    files.add(s);
            Checker c = new Checker();
            ClassLoader moduleClassLoader = Checker.class.getClassLoader();
            c.setModuleClassLoader(moduleClassLoader);
            c.configure(ConfigurationLoader.loadConfiguration(resourcesDir + "/WEB-INF/checkstyle/" + year + "_checks.xml", new PropertiesExpander(System.getProperties())));
            SummaryAuditListener auditListener = new SummaryAuditListener();
            c.addListener(auditListener);
            c.process(files);
            int total = 0;
            StringBuffer logger = new StringBuffer();
            for(Entry<String, AuditError> entry : auditListener.errors.entrySet()){
                String source = entry.getKey();
                logger.append("\t{{{" + source.substring(source.lastIndexOf(".")+1).replace("Check", "") + "}}}\n");
                AuditError details = entry.getValue();
                logger.append("\t\t<<<Count:>>> " + details.count + "\n");
                total += details.count;
                logger.append("\t\t<<<Level:>>> " + details.sample.getSeverityLevel() + "\n");
                logger.append("\t\t<<<Sample:>>> " + 
                        normalize(root, new File(details.sample.getFileName())) + 
                        ", Line: " + details.sample.getLine() +
                        ", Column: " + details.sample.getColumn() + 
                        ": " + details.sample.getMessage() +
                        "}}}\n");
            }
            print("[[[Total CheckStyle Recommendations: " + total + "]]]", out);
            print(logger.toString(), out);
            c.destroy();
            return total;
        } catch (CheckstyleException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    class SummaryAuditListener implements AuditListener{

        class AuditError{
            int count = 0;
            AuditEvent sample;
        }
        
        Map<String, AuditError> errors = new HashMap<String, AbstractServlet.SummaryAuditListener.AuditError>();
        
        @Override
        public void addError(AuditEvent event) {
            String source = event.getSourceName();
            AuditError auditError = errors.get(source);
            if(auditError==null){
                auditError = new AuditError();
                auditError.sample = event;
                errors.put(source,  auditError);
            }
            auditError.count++;
        }

        @Override
        public void addException(AuditEvent event, Throwable e) {}

        @Override
        public void auditFinished(AuditEvent event) {}

        @Override
        public void auditStarted(AuditEvent event) {}

        @Override
        public void fileFinished(AuditEvent event) {}

        @Override
        public void fileStarted(AuditEvent event) {}
    }
    
    protected void compile(File root, File[] sources, ServletOutputStream out) throws IOException{
        String resourcesDir = Environment.getResourceDir();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> dc;
        dc = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager sjfm = compiler.getStandardFileManager(dc,
                null, null);
        List<String> optionList = new ArrayList<String>();
        final String junitJar = resourcesDir + "/WEB-INF/lib/junit.jar";
        optionList.addAll(Arrays.asList("-classpath",
                System.getProperty("java.class.path") + File.pathSeparatorChar + junitJar));
        print("Compile: " + sources.length + " Files", out);
        if (Environment.contants.get("Debug") == 1)
            print("Options: " + Arrays.toString(optionList.toArray()), out);
        Iterable<? extends JavaFileObject> fileObjects = sjfm
                .getJavaFileObjects(sources);
        CompilationTask task = compiler.getTask(null, sjfm, dc, optionList,
                null, fileObjects);
        task.call();
        int i = 0;
        for (Diagnostic<?> d : dc.getDiagnostics()) {
            print("<<<" + d.getKind().name() + " " + ++i + ">>>", out);
            try {
            	// remove file name from message if any
            	if (d.getMessage(null).contains(".java"))
            		print("\tMessage: " + "Code " + d.getMessage(null)
            			.replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t")
            			.substring(d.getMessage(null).lastIndexOf(".java") + 5), out);
            	else
            		print("\tMessage: " + d.getMessage(null).replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t"), out);
            } catch (Exception e) {
            	e.printStackTrace();
            	if (Environment.contants.get("Debug") == 1)
            		print("\tMessage: " + d.getMessage(null).replaceAll("\\r\n|\\n\\r|\\r|\\n", "\n\t\t\t"), out);
            }
            print("\tLine number: " + d.getLineNumber(), out);
            Object source = d.getSource();
            if(source != null) {
            	try {
            		// show if the error in student file
            		if(source.toString().replaceAll("cs\\d+", "remove").contains("remove")) {
            			print("\tFile: " + source.toString()
            					.replaceAll("\\]", " ")
            					.replaceAll("\\[", " ")
            					.substring(source.toString().lastIndexOf("eg"))
                                .replaceAll("\\\\", "/")
                                .replaceAll(
                                        root.getAbsolutePath().replaceAll("\\\\", "/")
                                                + "/", "").replaceAll("\\.java", "")
                                , out);
            		} else if (Environment.contants.get("Debug") == 1) {
            			print("\tFile:" + source.toString()
	            			.replaceAll("\\]", " ")
	    					.replaceAll("\\[", " ")
	                        .replaceAll("\\\\", "/")
	                        .replaceAll(
	                                root.getAbsolutePath().replaceAll("\\\\", "/")
	                                        + "/", "").replaceAll("\\.java", "")
	                        , out);
            		} else
            			print("\tFile: " + "Server error", out);
                } catch (Exception e) {
                	e.printStackTrace();
                	if (Environment.contants.get("Debug") == 1) {
                		print("\tFile:" + source.toString()
	                		.replaceAll("\\]", " ")
	    					.replaceAll("\\[", " ")
	                        .replaceAll("\\\\", "/")
	                        .replaceAll(
	                                root.getAbsolutePath().replaceAll("\\\\", "/")
	                                        + "/", "").replaceAll("\\.java", "")
	                        , out);
                	}
                }
            }
            
        }
        if (i == 0)
            print("[[[Compiled Successfully]]]", out);
        else
            print("{{{Compiled with " + i + " Errors/Warnings. Please Fix!}}}", out);
    }
    
    protected TestingTask test(File root, File[] sources, String course, String unitTest){
        final TestingTask testingTask = new TestingTask(root, sources, course, unitTest);
        Thread thread = new Thread(testingTask);
        thread.setDaemon(false);
        /* Default priority of a thread 
         * (MIN_PRIORITY) --> 1 
         * (NORM_PRIORITY) --> 5 
         * (MAX_PRIORITY) --> 10
        */
        thread.setPriority(Environment.contants.get("Thread_P"));
        
        // Disable students prints
        PrintStream out = System.out;
        if (Environment.contants.get("Student_Output") == 0)
            System.setOut(new PrintStream(new OutputStream() {
                @Override public void write(int b) throws IOException {}
            }));
        
        thread.start();
        try {
            thread.join(1500 * TestingTask.TEST_TIMEOUT);
        } catch (Throwable e) {
        }
        try {
            thread.interrupt();
        } catch (Throwable e2) {
        }
        try {
            thread.stop();
        } catch (Exception e2) {
        }
        try {
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            if (threadSet.size() > 16) {
                for (Thread t : threadSet) {
                    if (!(t.isDaemon())) {
                        final StackTraceElement[] threadStackTrace = t.getStackTrace();
                        if (threadStackTrace.length > 1) {
                            StackTraceElement firstMethodInvocation = threadStackTrace[threadStackTrace.length - 1];
                            if (firstMethodInvocation.getClassName().startsWith("org.junit")) {
                                // HACK: must use deprecated method
                                t.stop();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        
        // restoring the output stream
        System.setOut(out);
        
        return testingTask;
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
