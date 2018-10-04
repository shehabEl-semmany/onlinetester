<!DOCTYPE html>
<html lang="en">

<%@page import="eg.edu.alexu.csd.util.Environment"%>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Test your code">
    <meta name="author" content="">

    <title>Online Tester</title>
    <link rel="icon" href="icon_big.png">

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
       <%String course = courseName.toLowerCase().replaceAll(" ",""); %>
       <div class="header-content">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12 text-center">
                        <i class="fa fa-4x fa-<%=courseIcon%> wow bounceIn" data-wow-delay=".1s"></i>
                        <h2 class="section-heading"><%=courseName%></h2>
                        <hr class="primary">
                    </div>
                    <div class="col-sm-12 text-left bs-callout bs-callout-danger ">
                        <h4>Test</h4>
                        
                        <form target="_blank" action="Tester" method="post" class="navbar-form navbar-left" role="search">      
                            <input type="hidden" name="course" value="<%=course%>">
                            <input type="hidden" name="year" value="<%=year%>">                         
                            <div class="container">
                                <div class="row">
                                  <div class="col-md-2 col-sm-3 col-xs-12">Repository:</div>
                                  <div class="col-md-10 col-sm-9 col-xs-12">
                                    <select name="repo" id="selectedRepository">
                                    <%
                                        for(String repository : Environment.findRepositories(year)){
                                    %>      
                                                <option value="<%=repository%>"><%=repository%></option>
                                    <%
                                        }
                                    %>                              
                                    </select>
                                  </div>
                                  <div class="col-md-2 col-sm-3 col-xs-12">Branch:</div>
                                  <div class="col-md-10 col-sm-9 col-xs-12">
                                    <select name="branch" id="selectedBranch">
                                        <option value="master">master</option>
                                    <%
                                        for (String testName : Environment.findTests(course)) {
                                    %>
                                                <option value="<%=testName%>"><%=testName%></option>
                                    <%
                                        }
                                    %>                                  
                                    </select>
                                  </div>
                                  <div class="col-md-2 col-sm-3 col-xs-12">Project (Optional):</div>
                                  <div class="col-md-10 col-sm-9 col-xs-12">
                                    <input type="text" name="proj" class="line" placeholder="project name"></input>
                                  </div>
                                  <div class="col-md-2 col-sm-3 col-xs-12">Test Suite:</div>
                                  <div class="col-md-10 col-sm-9 col-xs-12">
                                    <select name="test" id="selectedTest">
                                    <%
                                        for(String testName : Environment.findTests(course)){
                                    %>      
                                                <option value="<%=testName%>"><%=testName%></option>
                                    <%
                                        }
                                    %>                              
                                    </select>
                                  </div>
                                </div>
                            </div>
                            <div class="col-xs-12 text-center">
                                <button type="submit" class="btn btn-danger"><i class="fa fa-fire wow bounceIn"></i>  Run Test</button>
                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal"><i class="fa fa-question wow bounceIn"></i> Help</button>
                                <a target="_blank" href="Grades.jsp?course=<%=course%>&year=<%=year%>" class="btn btn-info" role="button"><i class="fa fa-check wow bounceIn"></i> Grades</a>
                            </div>
                        </form>
                    </div>  
                </div>              
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="alert alert-warning" role="alert">
                    <b>Server Start Time:</b>&nbsp;&nbsp;<%=Environment.startTime%>,&nbsp;&nbsp;&nbsp; <b>System Load:</b>&nbsp;&nbsp;<%=Environment.getSystemLoad()%>%<br>
                    <% int[] statistics = Environment.getTestCountsStatistics(); %>
                    <b>Number Of Tests in the last:</b>&nbsp;&nbsp;&nbsp; 24 hours = <%=statistics[2]%> Tests,&nbsp;&nbsp;&nbsp; 1 hour = <%=statistics[1]%> Tests,&nbsp;&nbsp;&nbsp;  5 minutes = <%=statistics[0]%> Tests             
                </div>
            </div>
        </div>      
    </section>


    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel">Welcome to Online Tester</h4>
          </div>
          <div class="bs-callout bs-callout-success">
                <ol>
                    <li>Push all your code to your Bitbucket
                    <li>Select your repository and the branch from the drop-down lists
                    <li>If you have multiple projects in the same branch, please enter the project name you want to test in the second text box
                    <li>Choose the JUnit test suite
                    <li>Press the "Run Test" button, and get surprised :p
                </ol>
           </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>

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
