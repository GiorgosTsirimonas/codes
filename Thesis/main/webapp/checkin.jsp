<%-- 
    Document   : checkin
    Created on : Sep 27, 2019, 3:20:56 AM
    Author     : death
--%>

<%@page import="java.awt.SystemColor.window"%>
<%@page import="com.mycompany.mavenproject2.CurrentUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
        Keep the page ideal for 30 seconds
   <br> And try reloading the page
   <br> you will be redirected to home page automatically
   <br>

   window.onload = function () { history.forward(); }; //force user to redirect
    window.onload = function() {}; // trick for some browsers like IE
   
   
<%
  if (!CurrentUser.CurrentName.equals("") ){
                response.sendRedirect("Boss_Home");
            }
  

  %>
   </br>
   </br>
       
   </br>
   </br>
        
        
        
        
        
    

