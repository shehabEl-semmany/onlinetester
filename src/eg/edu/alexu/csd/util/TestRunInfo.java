package eg.edu.alexu.csd.util;

import java.util.Date;

public class TestRunInfo {

    String result;
    String repository;
    String branch;
    String project;
    Date startedTime;
    long testTime;
    long downloadTime;
    long compileTime;
    
    public TestRunInfo(String repository, String branch, String project, long downloadTime, long compileTime, long testTime, String result) {
        this.repository = repository;
        this.branch = branch;
        this.project = project;
        this.startedTime = new Date();
        this.downloadTime = downloadTime;
        this.testTime = testTime;
        this.compileTime = compileTime;
        this.result = result;
    }
    
    @Override
    public String toString() {
        return startedTime + "---- [" + downloadTime/1000 + " sec] [" + compileTime/1000 + " sec]  [" + testTime/1000 + " sec] [" + result + "] " + repository + ":" + branch + (project==null || project.isEmpty() ? "" : " @" + project);
    }
}
