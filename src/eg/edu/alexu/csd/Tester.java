package eg.edu.alexu.csd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eg.edu.alexu.csd.util.Environment;
import eg.edu.alexu.csd.util.FilesUtils;
import eg.edu.alexu.csd.util.TestRunInfo;

/**
 * Run online test on the given repostiry and project
 */
public class Tester extends AbstractServlet {

    private static final long serialVersionUID = 1L;
    
    private void report(String paramCourse, String paramTest, String paramRepository, String s) {
    	try {
    		BufferedWriter reportFile = new BufferedWriter(new FileWriter(Environment.getResourceDir() 
					+ "_" + "report.txt", true));
			reportFile.append(new Date(System.currentTimeMillis()).toString()
					+ " >>> Course: " + paramCourse + ", Project: " + paramTest 
					+ ", Name: " + paramRepository + ", Report: " + s + "\n");
			reportFile.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("text/html");
        out.print("<html><head></head><body style='word-wrap:break-word;'>");
        
        String paramRepository = request.getParameter("repo");
        String paramBranch = request.getParameter("branch");
        String paramProject = request.getParameter("proj");
        String paramTest = request.getParameter("test");
        String paramCourse = request.getParameter("course");
        int paramYear = -1;
        try {
            paramYear = Integer.parseInt(request.getParameter("year"));
        } catch (Exception e) {
        }
        if(paramYear < 1 || paramYear > 4){
            print("Missing parameter 'year'. Please provide a valid year", out);
            return;         
        }
        if(paramRepository == null || paramRepository.isEmpty()){
            print("Missing parameter 'repository'. Please provide a valid repository name", out);
            return;
        }
        if(paramTest == null || paramTest.isEmpty()){
            print("Missing parameter 'test'. Please provide a valid test name", out);
            return;
        }
        
        print("Time: " + new Date(), out);
        print("Repository: <<<" + paramRepository + ">>>", out);
        print("Branch: <<<" + paramBranch + ">>>", out);
        if(paramProject!=null && paramProject.length() > 0)
            print("Project: <<<" + paramProject + ">>>", out);
        
        // Security check over project, repo, branch, ...
        if (paramProject != null && paramProject.length() > 0) {
        	if (paramProject.length() > Integer.parseInt(Environment.tests.get("security_check_length")[0])) {
        		report(paramCourse, paramTest, paramRepository, "project length was too long");
        		return;
        	}
        	if (paramProject.contains(" ")) {
        		report(paramCourse, paramTest, paramRepository, "project contains space");
        		return;
        	}
        	for (String word: Environment.tests.get("security_check_words"))
        		if (paramProject.toLowerCase().contains(word)) {
        			report(paramCourse, paramTest, paramRepository, "project contains malicious word");
        			return;
        		}
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
        		report(paramCourse, paramTest, paramRepository, "unknown parameter course");
    			return;
        	}
        }
        else
        	return;
        
        if (paramBranch != null && paramBranch.length() > 0) {
        	boolean found = false;
        	if (!paramBranch.equalsIgnoreCase("master")) {
        		for (String word: Environment.tests.get(paramCourse)) {
	        		if (paramBranch.equalsIgnoreCase(word)) {
	        			found = true;
	        			break;
	        		}
	        	}
        		if (!found) {
            		report(paramCourse, paramTest, paramRepository, "unknown parameter branch");
        			return;
            	}
        	}
        }
        else
        	return;
        
        if (paramTest != null && paramTest.length() > 0) {
        	boolean found = false;
        	for (String word: Environment.tests.get(paramCourse)) {
        		if (paramTest.equalsIgnoreCase(word)) {
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		report(paramCourse, paramTest, paramRepository, "unknown parameter test suite");
    			return;
        	}
        }
        else
        	return;
        
        if (paramRepository != null && paramRepository.length() > 0) {
        	boolean found = false;
        	for (String word: Environment.findRepositories(paramYear)) {
        		if (paramRepository.equalsIgnoreCase(word)) {
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		report(paramCourse, paramTest, paramRepository, "unknown repository");
    			return;
        	}
        }
        else
        	return;
        
        String unitTestPackage = Environment.getTestPackage(paramCourse) + "." + paramTest;
        
        print("", out);
        print("<<<<<<<<<<<<<<<<<<<<CHECKOUT>>>>>>>>>>>>>>>>>>>>", out);
        long checkoutTime = System.currentTimeMillis();
        File root = null;
        try {
        	root = checkout(paramYear, paramRepository, paramBranch, paramProject, out);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        if(root == null){
            print("Failed to check-out repository: " + paramRepository, out);
            return;
        }
            
        File[] sources = FilesUtils.getFiles(root, ".java").toArray(new File[0]);
        print("Sources: " + normalize(root, sources), out);
        
        print("", out);
        print("<<<<<<<<<<<<<<<<<<<<CHECKSTYLE>>>>>>>>>>>>>>>>>>>>", out);
        checkstyle(paramYear, root, sources, out);
        
        // Check code safety!
        File[] sources2 = FilesUtils.getFiles(root, null).toArray(new File[0]);
        for (File f: sources2) {
        	String content = FilesUtils.read(f.getPath());
        	for (String s: Environment.tests.get("words")) {
        		if (content == null || s == null)
        			break;
        		if (content.contains(s)) {
        			BufferedWriter reportFile = new BufferedWriter(new FileWriter(Environment.getResourceDir() 
        					+ "_" + "report.txt", true));
        			reportFile.append(new Date(System.currentTimeMillis()).toString()
        					+ " >>> Course: " + paramCourse + ", Project: " + paramTest 
        					+ ", Name: " + paramRepository + ", Word: " + s + "\n");
        			reportFile.close();
        			print("", out);
        			print("<<<<<<<<<<<<<<<<<<<<WARNING>>>>>>>>>>>>>>>>>>>>", out);
        			print("{{{You are running unsafe code}}}", out);
        			return;
        		}
        	}
        }
        
        print("", out);
        print("<<<<<<<<<<<<<<<<<<<<COMPILE>>>>>>>>>>>>>>>>>>>>", out);
        addTests(root, paramCourse, out);
        sources = FilesUtils.getFiles(root, ".java").toArray(new File[0]);
        long compileTime = System.currentTimeMillis();
        compile(root, sources, out);

        print("", out);
        print("<<<<<<<<<<<<<<<<<<<<TEST>>>>>>>>>>>>>>>>>>>>", out);
        long testTime = System.currentTimeMillis();
        String result[] = new String[] { "N/A", "", "" };
        try{
            print(test(root, sources, paramCourse, unitTestPackage).getReport(), out);
        } catch (Throwable e) {
            showError(e, out);
            if (e != null)
            	showError(e.getCause(), out);
        }
        long finishedTime = System.currentTimeMillis();
        try {
            Environment.queue.add(new TestRunInfo(paramRepository, paramBranch, paramProject, compileTime - checkoutTime, testTime - compileTime, finishedTime - testTime, result[0]));
        } catch (Throwable e) {
        }
        print("</body></html>", out);
    }

    @Override
    protected void print(String message, ServletOutputStream out)
            throws IOException {
        if(message==null || out==null)
            return;
        out.println(
                message
                    .replaceAll("\\n\\r|\\r\\n|\\n|\\r", "<br>")
                    .replaceAll("\\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")
                    .replaceAll("<br><br>", "<br>")
                    .replace("<<<<<<<<<<<<<<<<<<<<", "<hr><center><h4>")
                    .replace(">>>>>>>>>>>>>>>>>>>>", "</h4></center>")
                    .replaceAll("\\{\\{\\{", "<font color='red'><b>")
                    .replaceAll("\\}\\}\\}", "</b></font>")
                    .replaceAll("\\[\\[\\[", "<font color='green'><b>")
                    .replaceAll("\\]\\]\\]", "</b></font>")
                    .replaceAll("<<<", "<b>")
                    .replaceAll(">>>", "</b>")
            + "<br>");
    }
    
}
