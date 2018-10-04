package eg.edu.alexu.csd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eg.edu.alexu.csd.util.Environment;

/**
 * Administer the testing
 */
public class Administer extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("reload") != null) {
            boolean res = Environment.loadSystemParameters();
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(""+res);
            return;
        }
        
        String nextJSP = "Admin.jsp";
        String paramTestSuite = request.getParameter("test");
        String paramCourse = request.getParameter("course");
        String param = request.getParameter("type");
        
        if (param == null || !param.equals(Environment.tests.get("param")[0]))
            return; 
        
        if(paramTestSuite != null && paramTestSuite.length() > 0) {
            Map<String, String> tests = new HashMap<String, String>();
            for(String test : Environment.findTests(paramCourse, paramTestSuite).keySet()) {
                String content = request.getParameter(test);
                if(content != null && content.length() > 0)
                    tests.put(test, content);
            }
            if(!tests.isEmpty()) {
                Environment.setTests(paramCourse, paramTestSuite, tests);
                nextJSP += "?saved=true";
            }
        }
        getServletContext().getRequestDispatcher("/" + nextJSP).forward(request,response);
    }
}
