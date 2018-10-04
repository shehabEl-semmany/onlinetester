package eg.edu.alexu.csd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Environment {

    public static final Date startTime = new Date();
    
    public static ConcurrentLinkedQueue<TestRunInfo> queue = new ConcurrentLinkedQueue<TestRunInfo>();

    public static final HashMap<String, Integer> contants = new HashMap<String, Integer>();
    public static final HashMap<String, String[]> tests = new HashMap<String, String[]>();
    
    static {
        loadSystemParameters();
    }
    
    public static boolean loadSystemParameters() {
        boolean res = true;
        System.out.println("Loading constants");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(getResourceDir() + "/WEB-INF/startup_parameters.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] strings = line.split("=");
                    contants.put(strings[0], Integer.parseInt(strings[1]));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        
        System.out.println("Loading tests names in order");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(getResourceDir() + "/WEB-INF/tests_names.txt")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("=")) {
                    String[] strings = line.split("=");
                    tests.put(strings[0], strings[1].split(","));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }
        
        return res;
    }
    
    public static String getTestPackage(String course){
         return "eg.edu.alexu.csd." + course +  ".test";
    }
    
    public static double getSystemLoad() {
    	com.sun.management.OperatingSystemMXBean osBean = ManagementFactory
                .getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);
        return roundBy(osBean.getSystemCpuLoad() * 100, 3);
    }
    
    public static HashMap<String, String> getSystemInfo() {
    	com.sun.management.OperatingSystemMXBean osBean = ManagementFactory
                .getPlatformMXBean(com.sun.management.OperatingSystemMXBean.class);
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("AvailableProcessors", Integer.toString(osBean.getAvailableProcessors()));
        res.put("CommittedVirtualMemorySize", Double.toString(roundBy(osBean.getCommittedVirtualMemorySize()/1024.0/1024.0, 3)) + " MB");
        res.put("FreePhysicalMemorySize", Double.toString(roundBy(osBean.getFreePhysicalMemorySize()/1024.0/1024.0, 3)) + " MB");
        res.put("FreeSwapSpaceSize", Double.toString(roundBy(osBean.getFreeSwapSpaceSize()/1024.0/1024.0, 3)) + " MB");
        res.put("ProcessCpuLoad", Double.toString(roundBy(osBean.getProcessCpuLoad()*100, 3)) + "%");
        res.put("ProcessCpuTime", Double.toString(roundBy(osBean.getProcessCpuTime()/1000000000.0, 3)) + " S");
        res.put("SystemCpuLoad", Double.toString(roundBy(osBean.getSystemCpuLoad()*100, 3)) + "%");
        //res.put("SystemLoadAverage", Double.toString(osBean.getSystemLoadAverage()));
        res.put("TotalPhysicalMemorySize", Double.toString(roundBy(osBean.getTotalPhysicalMemorySize()/1024.0/1024.0, 3)) + " MB");
        res.put("TotalSwapSpaceSize", Double.toString(roundBy(osBean.getTotalSwapSpaceSize()/1024.0/1024.0, 3)) + " MB");
        return res;
    }
    
    public static double roundBy(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
    
    public static int[] getTestCountsStatistics(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, -5);
        Date fiveMinutesAgo = calendar.getTime();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -1);
        Date oneHourAgo = calendar.getTime();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date yesterday = calendar.getTime();
        int fiveMinutesTests = 0;
        int oneHourTests = 0;
        int oneDayTests = 0;
        for(TestRunInfo testRunInfo : queue){
            if(testRunInfo.startedTime.before(yesterday))
                continue;
            oneDayTests++;
            if(testRunInfo.startedTime.before(oneHourAgo))
                continue;
            oneHourTests++;
            if(testRunInfo.startedTime.before(fiveMinutesAgo))
                continue;
            fiveMinutesTests++;
        }
        return new int[] { fiveMinutesTests, oneHourTests, oneDayTests };
    }

    public static String getGitCommand(){
        return System.getProperty("os.name").toLowerCase().indexOf("windows") >=0 ?  "git" : "git";
    }
    
    public static String getResourceDir(){
        try {
            //return ((WebappClassLoader) Environment.class.getClassLoader()).getResources().getNameInNamespace();
            String path = new File(System.getProperty("catalina.base")).getAbsoluteFile().getAbsolutePath();
            String append = System.getProperty("os.name").toLowerCase().indexOf("windows") >=0 ?  
                    "/wtpwebapps/OnlineTester" : "/webapps/ROOT";
            path = path + append;
            return path;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return null;
    }
    
    public static Map<String, String> findTests(String course, String test){
        Map<String, String> tests = new HashMap<String, String>();
        if(test==null)
            return tests;
        String resourcesDir = getResourceDir();
        File testsSuiteDir = new File(resourcesDir + "/WEB-INF/res/" + getTestPackage(course).replaceAll("\\.", "/") + "/" + test);
        if(!testsSuiteDir.exists() || !testsSuiteDir.isDirectory())
            return tests;
        for(File file : testsSuiteDir.listFiles()){
            try {
                tests.put(file.getName().replaceAll("\\.java", ""), new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tests;
    }
    
    public static void setTests(String course, String testSuite, Map<String, String> tests) {
        String resourcesDir = getResourceDir();
        String path = resourcesDir + "/WEB-INF/res/" + getTestPackage(course).replaceAll("\\.", "/") + "/" + testSuite;
        File testsSuiteDir = new File(path);
        FilesUtils.delete(testsSuiteDir);
        testsSuiteDir.mkdirs();
        for(Entry<String, String> test : tests.entrySet()) {
            try {
                Files.write(Paths.get(new File(testsSuiteDir, test.getKey() + ".java").getAbsolutePath()), test.getValue().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String[] findTests(String course){
        /*
        String resourcesDir = getResourceDir();
        File testsDir = new File(resourcesDir + "/WEB-INF/res/" + getTestPackage(course).replaceAll("\\.", "/"));
        if(!testsDir.exists())
            return new String[] {};
        return testsDir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        //*/
    	if (course == null || course.length() == 0)
    		return new String[] {};
        return tests.get(course);
    }
    
    public static String[] findRepositories(int year){
        try {
            String[] repositories = new String(Files.readAllBytes(Paths.get(getResourceDir() + "/WEB-INF/repositories" + year + ".txt"))).split("\r\n|\r|\n");
            Arrays.sort(repositories, String.CASE_INSENSITIVE_ORDER);
            return repositories;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
    
    public static String getBitbucketLogin(int year) {
        return Environment.tests.get("account_N")[0] + ":" + Environment.tests.get("account_P")[0];
    }
    
}
