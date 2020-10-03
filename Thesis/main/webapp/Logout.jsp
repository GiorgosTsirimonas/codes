<%-- 
    Document   : Logout
    Created on : Sep 27, 2019, 5:14:00 PM
    Author     : death
--%>

<%@page import="com.mycompany.mavenproject2.CurrentUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
        
        
        <%
  //response.setHeader("Cache-Control","no-cache");
  // response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");
  response.setHeader("Pragma","no-cache");
  response.setHeader("Cache-Control","no-store");
  response.setHeader("Expires", "0");
  response.setDateHeader ("Expires", -1);
  session.invalidate();

 CurrentUser.CurrentName = " ";
 CurrentUser.Ses = -1;
 
 //if (session.getAttribute("UserName")== null){
   //  response.sendRedirect("login.html");
 //}
 
    response.sendRedirect("login.html");  

  %> 
  <p>You have succesfully logged out....
  <a href="login.html">Login</a>
  
    </body>
</html>
