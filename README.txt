BitBucket Accounts
=====================
AlexCS2018:2018alex
AlexCS2018@gmail.com:2018alex

AlexCS2019:2019alex
AlexCS2019@gmail.com:2019alex

alexcs.student.name@gmail.com:namestudent

Tomcat Configuration
=====================
/home/msaad/apache-tomcat-7.0.64/

Apache Configuration
=====================
/etc/httpd/conf/httpd.conf
/etc/httpd/conf/workers.properties



LoadModule jk_module modules/mod_jk.so

<IfModule jk_module>
    JkWorkersFile conf/workers.properties
    JkLogFile logs/mod_jk.log
    JkLogStampFormat "[%b %d %Y - %H:%M:%S] "
    JkRequestLogFormat "%w %V %T"
    JkLogLevel info

    JkOptions +ForwardKeySize +ForwardURICompat -ForwardDirectories
</IfModule>

<VirtualHost *:80>
     ServerAdmin mohamed.m.saad@gmail.com
     DocumentRoot /home/msaad/apache-tomcat-7.0.64/webapps/OnlineTester
     ServerName alexcs.noip.me
     ServerAlias *.alexcs.noip.me

     ErrorLog /home/msaad/apache-tomcat-7.0.64/logs/apache-error_log
     CustomLog /home/msaad/apache-tomcat-7.0.64/logs/apache-access_log combined

#     <Directory "/home/msaad/apache-tomcat-7.0.64/webapps/OnlineTester">
#       Options FollowSymLinks
#       AllowOverride None
#       Order allow,deny
#       Allow from all
#     </Directory>

     JkMount /CSBox    onlinetester
     JkMount /CSBox/*  onlinetester

     JkMount /OnlineTester    onlinetester
     JkMount /OnlineTester/*  onlinetester

</VirtualHost>
========================================================================================================================

Codes: 
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

******************************************
Online Tester 2017 using Amazon server   *
******************************************

In Environment file, make some changes in getResourceDir function