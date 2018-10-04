<%@page import="eg.edu.alexu.csd.Administer"%>
<%@page import="eg.edu.alexu.csd.util.TestRunInfo"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="eg.edu.alexu.csd.util.Environment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <script src="js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){  
            $('#reload').on('click',function(event){
                event.preventDefault();
                $.ajax({  
                    type: "GET",
                    data: "reload",
                    url: "Administer",  
                    dataType: 'text',
                    success: function(msg){ 
                        if (msg == 'true')
                            $("#output").html("<b><font color='green'>System Parameters loaded successfully</font></b>");
                        else
                            $("#output").html("<b><font color='red'>Error loading System Parameters!</font></b>");
                    },
                    error: function(request, textStatus, errorThrown) {
                        alert(errorThrown);
                    }
              });   
            });  
        });  
    </script>
    <div align="center">
        <div style="width: 850px">
            <form action="Administer" method="post">
                <input type="hidden" name="course" value="<%=request.getParameter("course")%>">
                <input type="hidden" name="type" value="<%=request.getParameter("type")%>">
                <table>
                    <tr>
                        <td valign="top">
                            <h2>Administration</h2>
                            <table>
                                <tr>
                                    <td>
                                        Test Suite:
                                    </td>
                                    <td style="width:360px">
                                        &nbsp;
                                        <%
                                        boolean found = false;
                                        %>
                                        <select name="test" id="selectedTest" <%=request.getParameter("test")!=null?"onfocus='this.defaultIndex=this.selectedIndex;' onchange='this.selectedIndex=this.defaultIndex;'":""%>>
                                            <%
                                                for (String testName : Environment.findTests(request.getParameter("course"))) {
                                            %>
                                            <option value="<%=testName%>" <%=testName.equals(request.getParameter("test"))?"selected='selected'":""%>><%=testName%></option>
                                            <%
                                                }
                                            %>
                                        </select>
                                        <input type="submit" value="<%=request.getParameter("test")!=null?"Save":"Load"%>">
                                        <%
                                            if(request.getParameter("test") != null){
                                        %>
                                        &nbsp;&nbsp;&nbsp;
                                        <a href="Admin.jsp?course=<%=request.getParameter("course")%>">Back</a>
                                        <%
                                            }
                                        %>
                                        <%
                                            if(request.getParameter("saved") != null){
                                        %>
                                        &nbsp;&nbsp;&nbsp;
                                        <b><font color="green">Tests Saved Successfully</font></b>
                                        <%
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        System Parameters: 
                                    </td>
                                    <td>
                                        &nbsp;
                                        <input type="submit" id="reload" name="reload" value="Reload">
                                        &nbsp;&nbsp;&nbsp;
                                        <span id="output"></span>
                                    </td>
                                </tr>
                            </table>
                        </td>
                        <td align="right"><img src="icon_big.png" /></td>
                    </tr>
                    
                <%
                    if(request.getParameter("test") == null){
                %>
                    <tr>
                        <td>
                            <b>Server Start Time:</b>&nbsp;&nbsp;<%=Environment.startTime%><br>
                            <b>System Load:</b>&nbsp;&nbsp;<%=Environment.getSystemLoad()%>%<br>
                            <%
                            int[] statistics = Environment.getTestCountsStatistics();
                            %>
                            <b>Number Of Tests in the last:</b><br>
                            &nbsp;&nbsp;&nbsp; 24 hours = <%=statistics[2]%> Tests<br>
                            &nbsp;&nbsp;&nbsp; 1 hour = <%=statistics[1]%> Tests<br>
                            &nbsp;&nbsp;&nbsp; 5 minutes= <%=statistics[0]%> Tests
                        </td> 
                    </tr>       
                    <tr>
                        <td colspan="2">
                            <hr>
                <%
                			for(Entry entry : Environment.getSystemInfo().entrySet()) {
                                    %>
                                    <b><%=entry.getKey()%></b> = <%=entry.getValue()%><br>
                                    <%
                            }     
                            %>
                            <hr>
                            <b>Tests Count = <%=Environment.queue.size()%></b> 
                            <%
                            for(TestRunInfo info : Environment.queue){
                                %>
                                <br/><%=info.toString()%>
                                <%
                            }
                %>
                            <hr>
                        </td>
                    </tr>
                <%      
                    }
                    for(Entry<String, String> test : Environment.findTests(request.getParameter("course"), request.getParameter("test")).entrySet()){
                %>
                    <tr>
                        <td colspan="2">
                            <b><%=test.getKey()%></b><br>
                            <textarea rows="10" cols="100" name="<%=test.getKey()%>"><%=test.getValue()%></textarea>
                        </td>
                    </tr>
                <%                              
                    };
                %>
                </table>
            </form>
        </div>
    </div>
</body>
</html>