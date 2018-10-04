package eg.edu.alexu.csd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import eg.edu.alexu.csd.util.Environment;
import eg.edu.alexu.csd.util.FilesUtils;
import eg.edu.alexu.csd.util.TestingTask;
import eg.edu.alexu.csd.util.TestingTask.CodeProfile;

public class Grader extends AbstractServlet{
    
    private static final long serialVersionUID = 1L;
    private static int REFRESH_RATE;
    
    private static Grader instance;
    public static Grader getInstance(){
        if(instance==null){
            synchronized(Grader.class){
                if(instance == null)
                    instance = new Grader();
            }
        }
        return instance;
    }
    
    private Grader(){
        REFRESH_RATE = 1000 * 60 * 60 * Environment.contants.get("Refresh_Rate");
    }
    
    public static final Map<String, Long> lastUpdated = new HashMap<String, Long>();
    public static final Map<String, Map<String, Map<String, String>>> all_results = new ConcurrentHashMap<String, Map<String, Map<String, String>>>();
    public static final Map<String, Map<String, Map<String, List<CodeProfile>>>> all_digests = new ConcurrentHashMap<String, Map<String,Map<String,List<CodeProfile>>>>();

    public Date getLastUpdate(final int paramYear, final String paramCourse, final String shouldUpdate) throws IOException{
        
    	// security check
    	if(paramYear < 1 || paramYear > 4) {
    		report(paramYear, paramCourse, "unknown parameter year");
    		return null;
    	}
    	if (paramCourse != null && paramCourse.length() > 0) {
        	boolean found = false;
        	for (String word: Environment.tests.get("courses")) {
        		if (paramCourse.equalsIgnoreCase(word)) {
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		report(paramYear, paramCourse, "unknown parameter course");
    			return null;
        	}
        }
    	else
    		return null;
    	
    	String key = paramYear + paramCourse;
        
        // force update
        final boolean forceUpdate = (shouldUpdate != null && shouldUpdate.equals(Environment.tests.get("refresh")[0]))? true: false;
        if (forceUpdate)
            return null;
        
        Long update = lastUpdated.get(key);
        // fixed from machine itself!
        /*
        Date date = update==null ? null: new Date(update);
        if (update != null) {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            isoFormat.setTimeZone(TimeZone.getTimeZone("GMT+2"));
            try {
                date = isoFormat.parse(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(update)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
        //*/
        return update==null ? null : new Date(update);
    }
    
    private synchronized void reloadGrades(int paramYear, String paramCourse, boolean forceUpdate) throws IOException{
        String key = paramYear + paramCourse;
        
        // force updating
        if (forceUpdate)
            lastUpdated.remove(key);
        
        Long updated = lastUpdated.get(key);
        if (updated != null && System.currentTimeMillis() - updated < REFRESH_RATE)
            return;
        lastUpdated.remove(key);
        
        Map<String, Map<String, String>> results = all_results.get(key);
        Map<String, Map<String, List<CodeProfile>>> digests = new ConcurrentHashMap<String, Map<String, List<CodeProfile>>>();
        all_digests.put(paramYear + paramCourse, digests);
        
        // Opening properties file
        System.out.println("Loading new grading criteria!");
        BufferedReader reader = new BufferedReader(new FileReader(new File(Environment.getResourceDir() + "/WEB-INF/grades_properties.txt")));
        String line = null;
        HashMap<String, Float> gradesProperties = new HashMap<String, Float>();
        while ((line = reader.readLine()) != null) {
            if (line.contains("=")) {
                String[] strings = line.split("=");
                gradesProperties.put(strings[0], Float.parseFloat(strings[1]));
            }
        }
        reader.close();
        
        // Opening the grades file
        BufferedWriter bw = new BufferedWriter(new FileWriter(Environment.getResourceDir() + "_" + paramCourse + "_grades.txt"));   
        for(String repository : Environment.findRepositories(paramYear)) {
            System.out.println(new Date() + " :" + repository);
            
            // Write student repo
            bw.write(String.format("%-45s\t", repository));
            
            // checkout
            File root = checkout(paramYear, repository, null, null, null);
            if(root==null) {
                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                root = checkout(paramYear, repository, null, null, null);
            }
            Map<String, String> studentResults = new ConcurrentHashMap<String, String>();
            Map<String, List<CodeProfile>> studentDigest = new ConcurrentHashMap<String, List<CodeProfile>>();
            if(root == null) {
                for(String test : Environment.findTests(paramCourse)) {
                    studentResults.put(test, "NoSrc");
                    bw.write("X" + "\t");
                }
            } else {
            	File[] sources = FilesUtils.getFiles(root, ".java").toArray(new File[0]);
            	
            	// Check code safety!
            	File[] sources2 = FilesUtils.getFiles(root, null).toArray(new File[0]);
            	boolean stop = false;
            	for (File f: sources2) {
            		if (stop) break;
                	String content = FilesUtils.read(f.getPath());
                	for (String s: Environment.tests.get("words")) {
                		if (content == null || s == null)
                			break;
                		if (content.contains(s)) {
                			BufferedWriter reportFile = new BufferedWriter(new FileWriter(Environment.getResourceDir() 
                					+ "_" + "report.txt", true));
                			reportFile.append(new Date(System.currentTimeMillis()).toString()
                					+ " >>> Course: " + paramCourse + ", Grader Page" 
                					+ ", Name: " + repository + ", Word: " + s + "\n");
                			reportFile.close();
                			for(String test : Environment.findTests(paramCourse)) {
                                studentResults.put(test, "Compile");
                                bw.write("X" + "\t");
                            }
                			stop = true;
                			break;
                		}
                	}
                }
            	if (!stop) {
	                // checkstyle
	                int checkstyle = checkstyle(paramYear, root, sources, null);
	                studentResults.put("CheckStyle", checkstyle<0 ? "N/A" : String.valueOf(checkstyle));
	                
	                // compile
	                String result;
	                addTests(root, paramCourse, null);
	                sources = FilesUtils.getFiles(root, ".java").toArray(new File[0]);
	                try {
	                    compile(root, sources, null);
	                } catch (Exception e) {
	                	result = "Compile";
	                }
	                for(String test : Environment.findTests(paramCourse)){
	                    String unitTestPackage = Environment.getTestPackage(paramCourse) + "." + test;
	                    
	                    // test
	                    try{
	                        TestingTask task = test(root, sources, paramCourse, unitTestPackage);
	                        result = task.getSummary();
	                        List<CodeProfile> profiles = task.getCodeProfile();
	                        for(CodeProfile p : profiles)
	                            p.repository = repository;
	                        studentDigest.put(test, profiles);
	                    } catch (Throwable e) {
	                        result = "N/A";
	                    }
	                    studentResults.put(test, result);
	                    
	                    // Write grades
	                    String token[] = result.trim().split("/");
	                    if (token.length >= 2){
	                        int studentMark = Integer.parseInt(token[0]);
	                        int fullMark = Integer.parseInt(token[1]);
	                        float ratio = 0;
	                        if (fullMark != 0)
	                            ratio = (float) studentMark / fullMark;
	                        char grade;
	                        if (ratio > gradesProperties.get("Grading_A")) grade = 'A';
	                        else if (ratio > gradesProperties.get("Grading_B")) grade = 'B';
	                        else if (ratio > gradesProperties.get("Grading_C")) grade = 'C';
	                        else if (ratio > gradesProperties.get("Grading_D")) grade = 'D';
	                        else grade = 'X';
	                        bw.write(grade + "\t");
	                    }
	                    else
	                        bw.write("X" + "\t");
	                }
	            }
            }
            
            //>>>>>>>>>>>>
            results.put(repository, studentResults);
            digests.put(repository, studentDigest);
            
            // Write Check Style
            String studentStyle = studentResults.get("CheckStyle");
            if (studentStyle != null){
                int styleValue = Integer.parseInt(studentStyle);
                char style;
                if (styleValue > gradesProperties.get("Style_D")) style = 'D';
                else if (styleValue > gradesProperties.get("Style_C")) style = 'C';
                else if (styleValue > gradesProperties.get("Style_B")) style = 'B';
                else style = 'A';
                bw.write(style);
            }
            else
                bw.write("X");
            // write new line
            bw.write("\n");
            //bw.flush();
        }
        // Close the grades file
        bw.close();
        
        // Plagiarism Detection
        for(Entry<String, Map<String, List<CodeProfile>>> student : digests.entrySet()) {
            String repository = student.getKey();
            for(Entry<String, List<CodeProfile>> implDigist : student.getValue().entrySet()) {
                String test = implDigist.getKey();
                List<CodeProfile> dig = implDigist.getValue();
                if(dig==null || dig.isEmpty())
                    continue;
                int found = 0;
                for(CodeProfile profile : dig) {
                    for(Map<String, List<CodeProfile>> others : digests.values()) {
                        List<CodeProfile> otherDig = others.get(test);
                        if(otherDig==null || otherDig.isEmpty())
                            continue;
                        for(CodeProfile otherProfile : otherDig) {
                            if(otherProfile.equals(profile))
                                continue;
                            if(profile.digest.equals(otherProfile.digest)) {
                                found++;    
                                profile.similar++;
                            }
                            if(profile.getSummary().equals(otherProfile.getSummary())) {
                                found++;    
                                profile.similar++;
                            }
                        }
                    }
                }
                if(found > 0) {
                    String grade = results.get(repository).get(test);
                    results.get(repository).put(test, grade + " *" + found);
                }
            }
        }
        
        lastUpdated.put(key, System.currentTimeMillis());
    }
    
    public Map<String, Map<String, String>> geGrades(final int paramYear, final String paramCourse, final String shouldUpdate) throws IOException{
    	
    	// security check
    	if(paramYear < 1 || paramYear > 4) {
    		report(paramYear, paramCourse, "unknown parameter year");
    		return null;
    	}
    	if (paramCourse != null && paramCourse.length() > 0) {
        	boolean found = false;
        	for (String word: Environment.tests.get("courses")) {
        		if (paramCourse.equalsIgnoreCase(word)) {
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		report(paramYear, paramCourse, "unknown parameter course");
    			return null;
        	}
        }
    	else
    		return null;
    	
    	String key = paramYear + paramCourse;
        
        // force update
        final boolean forceUpdate = (shouldUpdate != null && shouldUpdate.equals(Environment.tests.get("refresh")[0]))? true: false;
        
        Map<String, Map<String, String>> results = all_results.get(key);
        if(results == null){
            synchronized (Grader.class) {
                results = all_results.get(key);
                if(results==null){
                    results = new ConcurrentHashMap<String, Map<String, String>>();
                    all_results.put(key, results);
                }
            }
        }
        
        new Thread(){
            public void run() {
                try {
                    reloadGrades(paramYear, paramCourse, forceUpdate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
        }.start();
        
        return results;
    }
    
    public static List<CodeProfile> getProfiles(final int year, final String course, String test){
        List<CodeProfile> profiles = new LinkedList<CodeProfile>();
        Map<String, Map<String, List<CodeProfile>>> digests = Grader.all_digests.get(year + course);
        if(digests!=null){
            for(String repository : Environment.findRepositories(year)){
                Map<String, List<CodeProfile>> studentProfile = digests.get(repository);
                if(studentProfile == null)
                    continue;
                List<CodeProfile> testProfile = studentProfile.get(test);
                if(testProfile == null)
                    continue;
                profiles.addAll(testProfile);
            }
        }       
        Collections.sort(profiles, new Comparator<CodeProfile>() {
            @Override
            public int compare(CodeProfile p1, CodeProfile p2) {
                int result = p1.getSummary().compareTo(p2.getSummary());
                if(result==0)
                    result = p1.digest.compareTo(p2.digest);
                if(result==0)
                    result = p1.repository.compareTo(p2.repository);
                if(result==0)
                    result = p1.fileName.compareTo(p2.fileName);
                return result;
            }
        });
        return profiles;
    }
    
    private void report(int paramYear, String paramCourse, String s) {
    	try {
    		BufferedWriter reportFile = new BufferedWriter(new FileWriter(Environment.getResourceDir() 
					+ "_" + "report.txt", true));
			reportFile.append(new Date(System.currentTimeMillis()).toString()
					+ " >>> Course: " + paramCourse + ", Grader Page" 
					+ ", Year: " + paramYear + ", Report: " + s + "\n");
			reportFile.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
