<%@page import="java.util.HashMap"%>
<%@page import="eg.edu.alexu.csd.util.TestingTask.CodeProfile"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="eg.edu.alexu.csd.Grader"%>
<%@page import="eg.edu.alexu.csd.util.Environment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Plagiarism</title>
</head>
<body style="font-family:Courier New;">
 <%
String course = request.getParameter("course");
int year = 1;
try{
    year = Integer.parseInt(request.getParameter("year"));          
}catch(Exception e){
}
%>

<%
Map<String, Map<String, List<CodeProfile>>> digests = Grader.all_digests.get(year + course);
if(digests!=null){
    for(String test : Environment.findTests(course)){
%>
    <h3><%=test%></h3>  
<%
        for(CodeProfile profile : Grader.getProfiles(year, course, test)){
            if(profile.similar>0){
                %>
                <b>
                <%
            }
            %>
            <%=profile%><br>
            <%
            if(profile.similar>0){
                %>
                </b>
                <%
            }
        }
%>
    <hr/>
<%
    }
}
%>

</body>
</html>