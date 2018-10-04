<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="eg.edu.alexu.csd.Grader"%>
<html lang="en">

<%@page import="eg.edu.alexu.csd.util.Environment"%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Online Tester</title>

    <!-- Bootstrap Core CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="css/callout.css" type="text/css">

    <!-- Custom Fonts -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css" type="text/css">

    <!-- Plugin CSS -->
    <link rel="stylesheet" href="css/animate.min.css" type="text/css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/creative.css" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body id="page-top">

    <nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand page-scroll" href="index.html?"><i class="fa fa-bug wow bounceIn"></i> Online Tester</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a class="page-scroll" href="index.html?#about">About</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="index.html?#courses">Courses</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="index.html?#help">Help</a>
                    </li>
                    <li>
                        <a class="page-scroll" href="index.html?#contact">Contact</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>

    <header class="header2" style="min-height: 50px;">
    </header>
    
    <section id="courses" style="padding: 20px;">
       <%
        String course = request.getParameter("course");
        String plagiarism = request.getParameter("plag");
        String update = request.getParameter("update");
        int year = -1;
        try{
            year = Integer.parseInt(request.getParameter("year"));          
        }catch(Exception e){
        }

       %>
       <div class="header-content">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <h3 class="section-heading"><i class="fa fa-check wow bounceIn" data-wow-delay=".1s"></i>&nbsp;&nbsp;<b>Grades</b></h3>
                        <hr class="primary">
                    </div>
                </div>
               <%
                Map<String, Map<String, String>> results = Grader.getInstance().geGrades(year, course, update);
                Date lastUpdated = Grader.getInstance().getLastUpdate(year, course, update);
                if (results != null)
                {
               %>
                <div class="row">
                    <div class="alert alert-warning" role="alert">
                        <b>Last Updated:</b>&nbsp;&nbsp;<%=lastUpdated==null ? "In Progress... Please Wait!" : new SimpleDateFormat("yyyy-MM-dd HH:mm").format(lastUpdated)%>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 text-left bs-callout bs-callout-danger ">
                        <div class="container">
                            <div class="row">
                                <div class="col-sm-3"></div>
                                 <%
                                    for(String test : Environment.findTests(course)){
                                %>
                                <div class="col-sm-1 text-center"><b><%=test%></b></div>                                
                                <%
                                    }
                                %>
                                <div class="col-sm-1 text-center"><b>Style</b></div>    
                            </div>
                            <div class="row">
                                <div class="col-sm-3"></div>
                                 <%
                                    for(String test : Environment.findTests(course)){
                                %>
                                <div class="col-sm-1 text-center"><hr class="primary"></div>                                
                                <%
                                    }
                                %>
                                <div class="col-sm-1 text-center"><hr class="primary"></div>        
                            </div>                      
                            <%
                                for(String repository : Environment.findRepositories(year)){
                            %>      
                            <div class="row">
                                <div class="col-sm-3"><b><%=repository.split("/")[0]%></b></div>
                                <%
                                    Map<String, String> studentResults = results.get(repository);
                                    for(String test : Environment.findTests(course)){
                                        String r = studentResults==null ? null : studentResults.get(test);
                                        String color = "black";
                                        if(r!=null){
                                            if(plagiarism==null || plagiarism.trim().isEmpty())
                                                r = r.replaceAll("\\*[\\d]*", "");
                                            String token[] = r.trim().split("/");
                                            if(token.length < 2)
                                                color = "red";
                                            else if(token[0].equals(token[1]))
                                                color = token[0].equals("0") ? "red" : "green";
                                            else
                                                color = "orange";
                                        }
                                %>
                                <div class="col-sm-1 text-center" style="color: <%=color%>;"><%=r==null ? "--" : r%></div>                              
                                <%
                                    }
                                %>
                                <%
                                    String checkstyle = null;
                                    String color = "black";
                                    if(studentResults!=null){
                                        checkstyle = studentResults.get("CheckStyle");
                                        if(checkstyle!=null){
                                            try{
                                                int v = Integer.parseInt(checkstyle);
                                                if(v>100)
                                                    color = "red";
                                                else if(v>50)
                                                    color = "orange";
                                                else{
                                                    color = "green";
                                                    if(v==0)
                                                        checkstyle = "<i class='fa fa-trophy wow bounceIn'></i>";
                                                }
                                            }catch(Exception e){}
                                        }
                                    }
                                %>
                                <div class="col-sm-1 text-center" style="color: <%=color%>;"><%=checkstyle==null ? "--" : checkstyle%></div>
                            </div>
                            <%
                                }
                            %>      
                        </div>  
                    </div>  
                </div> 
                <%
                } else {
                %>   
                Invalid course or year!</br></br>
                <%
                }
                %>
                <div class="row">
                    <div class="alert alert-warning" role="alert">
                        <b>Server Start Time:</b>&nbsp;&nbsp;<%=Environment.startTime%>,&nbsp;&nbsp;&nbsp; <b>System Load:</b>&nbsp;&nbsp;<%=Environment.getSystemLoad()%>%<br>
                        <% int[] statistics = Environment.getTestCountsStatistics(); %>
                        <b>Number Of Tests in the last:</b>&nbsp;&nbsp;&nbsp; 24 hours = <%=statistics[2]%> Tests,&nbsp;&nbsp;&nbsp; 1 hour = <%=statistics[1]%> Tests,&nbsp;&nbsp;&nbsp;  5 minutes = <%=statistics[0]%> Tests             
                    </div>
                </div>            
            </div>
        </div>
    </section>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>
    <script src="js/jquery.fittext.js"></script>
    <script src="js/wow.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="js/creative.js"></script>

</body>

</html>
