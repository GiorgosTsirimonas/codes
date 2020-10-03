/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "css", urlPatterns = {"/css"})
public class css extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static String Css(){
        System.out.println("mpoum");
        String css = "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 50%;\n" +
                        "text-align:center;"+
                        "}\n" +
                        "\n" +
"                   th, td {\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "\n" +
                "tr:nth-child(even){background-color: #f2f2f2}\n" +
                        "\n" +
                "th {\n" +
                    "  background-color: gray;\n" +
                    "  color: white;\n" +
        "}";
          css = css.concat("p {  font-family: verdana;\n" +
                        "  font-size: 20px;text-align;}");
        css = css.concat(".nav ul {\n" +
         "	list-style:none;\n" +
         "	text-align:center;\n" +
         "	padding:0;\n" +
         "	margin:0;\n" +
         "	background:black;\n" +
         "	}\n" +
         "	\n" +
         "	.nav li {\n" +
         "	display:inline-block;\n" +
         "	}\n" +
         "	\n" +
         "	.nav a{\n" +
         "	text-decoration:none;\n" +
         "	color:white;\n" +
         "	width:120px;\n" +
         "	display:block;\n" +
         "	padding:15px;\n" +
         "	font-size:17px;\n" +
         "	font-family:Helvetica;\n" +
         "	transition :0.4;\n" +
         "	}\n" +
         "	\n" +
         "	.nav a:hover {\n" +
         "	brackground:#000;\n" +
         "	transition:0,4s;}\n" +
         "	\n" +
         "	.header { padding: 30px;\n" +
         "  text-align: center;\n" +
         " background: black;\n" +
         "  color: white;\n" +
         "  font-size: 10px;\n" +
         "}");
          
        css = css.concat(".dropdown-content {" +
        "  display: none;" +
        "  position: absolute;" +
        "  background-color: #f9f9f9;" +
        "  min-width: 160px;" +
        "  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);" +
        "  z-index: 1;" +
        "}");
                   css = css.concat(
        ".dropdown-content a {\n" +
        "  float: none;\n" +
        "  color: black;\n" +
        "  padding: 12px 16px;\n" +
        "  text-decoration: none;\n" +
        "  display: block;\n" +
        "  text-align: left;\n" +
        "}");	
                css = css.concat(".dropdown-content a:hover {" +
        "  background-color: #ddd;" +
        "}"); 
        css = css.concat(
        ".dropdown:hover .dropdown-content {" +
        "  width:120px;" +
        "	display:block;" +
        "	padding:15px;" +
        "	font-size:17px;" +
        "	font-family:Helvetica;	}");  
        
        css = css.concat(".notification {\n" +
      
        "  color: white;\n" +
        "  text-decoration: none;\n" +
        "  padding: 15px 26px;\n" +
        "  position: relative;\n" +
        "  display: inline-block;\n" +
        "  border-radius: 2px;\n" +
        "}");
        
        css = css.concat(".notification .badge {\n" +
        "  position: absolute;\n" +
        "  top: 0px;\n" +
        "  right: 30px;\n" +
        "  padding: 5px 10px;\n" +
        "  border-radius: 50%;\n" +
        "  background-color: red;\n" +
        "  color: white;\n" +
                 "}");
        return css;
        
    }
    
    public static String Css_Calendar(){
        String css = "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 50%;\n" +
                        "text-align:center;"+
                        "}\n" +
                        "\n" +
"                   th, td {\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "\n" +
                "tr:nth-child(even){background-color: #f2f2f2}\n" +
                        "\n" +
                "th {\n" +
                    "  background-color: blue;\n" +
                    "  color: white;\n" +
        "}";
           css = css.concat("p {  font-family: verdana;\n" +
                        "  font-size: 20px;text-align:center;}"+
                            "active {\n" +
                 "  padding: 5px;\n" +
                 "  background: #1abc9c;\n" +
                 "  color: white !important\n" +
                 "}");
           css = css.concat(".month {\n" +
            "  padding: 70px 25px;\n" +
            "  width: 100%;\n" +
            "  background: #1abc9c;\n" +
            "  text-align: center;\n" +
            "}\n" +
            "\n" +
          
            ".month ul {\n" +
            "  margin: 0;\n" +
            "  padding: 0;\n" +
            "}\n" +
            "\n" +
            ".month ul li {\n" +
            "  color: white;\n" +
            "  font-size: 20px;\n" +
            "  text-transform: uppercase;\n" +
            "  letter-spacing: 3px;\n" +
            "}\n" +
            "\n" +
   
            ".month .prev {\n" +
            "  float: left;\n" +
            "  padding-top: 10px;\n" +
            "}\n" +
            "\n" +
       
            ".month .next {\n" +
            "  float: right;\n" +
            "  padding-top: 10px;\n" +
            "}");
           
           
             css = css.concat(".nav ul {\n" +
                "	list-style:none;\n" +
                "	text-align:center;\n" +
                "	padding:0;\n" +
                "	margin:0;\n" +
                "	background:black;\n" +
                "	}\n" +
                "	\n" +
                "	.nav li {\n" +
                "	display:inline-block;\n" +
                "	}\n" +
                "	\n" +
                "	.nav a{\n" +
                "	text-decoration:none;\n" +
                "	color:white;\n" +
                "	width:120px;\n" +
                "	display:block;\n" +
                "	padding:15px;\n" +
                "	font-size:17px;\n" +
                "	font-family:Helvetica;\n" +
                "	transition :0.4;\n" +
                "	}\n" +
                "	\n" +
                "	.nav a:hover {\n" +
                "	brackground:#000;\n" +
                "	transition:0,4s;}\n" +
                "	\n" +
                "	.header { padding: 30px;\n" +
                "  text-align: center;\n" +
                " background: black;\n" +
                "  color: white;\n" +
                "  font-size: 10px;\n" +
                "}");
           
                css = css.concat(".dropdown-content {" +
                "  display: none;" +
                "  position: absolute;" +
                "  background-color: #f9f9f9;" +
                "  min-width: 160px;" +
                "  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);" +
                "  z-index: 1;" +
                "}");
           css = css.concat(
            ".dropdown-content a {\n" +
            "  float: none;\n" +
            "  color: black;\n" +
            "  padding: 12px 16px;\n" +
            "  text-decoration: none;\n" +
            "  display: block;\n" +
            "  text-align: left;\n" +
            "}");	
        css = css.concat(".dropdown-content a:hover {" +
        "  background-color: #ddd;" +
        "}"); 
        css = css.concat(
        ".dropdown:hover .dropdown-content {" +
        "  width:120px;" +
        "	display:block;" +
        "	padding:15px;" +
        "	font-size:17px;" +
        "	font-family:Helvetica;	}"); 
           
           css = css.concat("ul {list-style-type: none;}");
           
           
            css = css.concat(".notification {\n" +
      
        "  color: white;\n" +
        "  text-decoration: none;\n" +
        "  padding: 15px 26px;\n" +
        "  position: relative;\n" +
        "  display: inline-block;\n" +
        "  border-radius: 2px;\n" +
        "}");
        
        css = css.concat(".notification .badge {\n" +
        "  position: absolute;\n" +
        "  top: 0px;\n" +
        "  right: 30px;\n" +
        "  padding: 5px 10px;\n" +
        "  border-radius: 50%;\n" +
        "  background-color: red;\n" +
        "  color: white;\n" +
        "}");
        
        return css;
    }
    
    public static String Css_DWS(){
        String css = "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 50%;\n" +
                        "text-align:center;"+
                        "}\n" +
                        "\n" +
"                   th, td {\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "\n" +
                "tr:nth-child(even){background-color: #f2f2f2}\n" +
                        "\n" +
                "th {\n" +
                    "  background-color: blue;\n" +
                    "  color: white;\n" +
        "}";
           css = css.concat("p {  font-family: verdana;\n" +
                        "  font-size: 20px; size:10px;}");
           css = css.concat(".nav ul {\n" +
        "	list-style:none;\n" +
        "	text-align:center;\n" +
        "	padding:0;\n" +
        "	margin:0;\n" +
        "	background:black;\n" +
        "	}\n" +
        "	\n" +
        "	.nav li {\n" +
        "	display:inline-block;\n" +
        "	}\n" +
        "	\n" +
        "	.nav a{\n" +
        "	text-decoration:none;\n" +
        "	color:white;\n" +
        "	width:120px;\n" +
        "	display:block;\n" +
        "	padding:15px;\n" +
        "	font-size:17px;\n" +
        "	font-family:Helvetica;\n" +
        "	transition :0.4;\n" +
        "	}\n" +
        "	\n" +
        "	.nav a:hover {\n" +
        "	brackground:#000;\n" +
        "	transition:0,4s;}\n" +
        "	\n" +
        "	.header { padding: 30px;\n" +
        "  text-align: center;\n" +
        " background: black;\n" +
        "  color: white;\n" +
        "  font-size: 10px;\n" +
        "}");

       css = css.concat(".dropdown-content {" +
        "  display: none;" +
        "  position: absolute;" +
        "  background-color: #f9f9f9;" +
        "  min-width: 160px;" +
        "  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);" +
        "  z-index: 1;" +
        "}");
                   css = css.concat(
        ".dropdown-content a {\n" +
        "  float: none;\n" +
        "  color: black;\n" +
        "  padding: 12px 16px;\n" +
        "  text-decoration: none;\n" +
        "  display: block;\n" +
        "  text-align: left;\n" +
        "}");	
                css = css.concat(".dropdown-content a:hover {" +
        "  background-color: #ddd;" +
        "}"); 
        css = css.concat(
        ".dropdown:hover .dropdown-content {" +
        "  width:120px;" +
        "	display:block;" +
        "	padding:15px;" +
        "	font-size:17px;" +
        "	font-family:Helvetica;	}");  
        
            css = css.concat(".notification {\n" +
      
        "  color: white;\n" +
        "  text-decoration: none;\n" +
        "  padding: 15px 26px;\n" +
        "  position: relative;\n" +
        "  display: inline-block;\n" +
        "  border-radius: 2px;\n" +
        "}");
        
        css = css.concat(".notification .badge {\n" +
        "  position: absolute;\n" +
        "  top: 0px;\n" +
        "  right: 30px;\n" +
        "  padding: 5px 10px;\n" +
        "  border-radius: 50%;\n" +
        "  background-color: red;\n" +
        "  color: white;\n" +
        "}");
        
        return css;
    }
    
    public static String Css_EOM(){
        String css =  "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 50%;\n" +
                        "text-align:center;"+
                        "}\n" +
                        "\n" +
"                   th, td {\n" +
                        "  text-align: left;\n" +
                        "  padding: 8px;\n" +
                        "}\n" +
                        "\n" +
                "tr:nth-child(even){background-color: #f2f2f2}\n" +
                        "\n" +
                "th {\n" +
                    "  background-color: blue;\n" +
                    "  color: white;\n" +
        "}"
          +"p {  font-family: verdana;\n" +
                        "  font-size: 20px; size:10px;"
                            +"text-align:center;}"
           +".nav ul {\n" +
            "	list-style:none;\n" +
            "	text-align:center;\n" +
            "	padding:0;\n" +
            "	margin:0;\n" +
            "	background:black;\n" +
            "	}\n" +
            "	\n" +
            "	.nav li {\n" +
            "	display:inline-block;\n" +
            "	}\n" +
            "	\n" +
            "	.nav a{\n" +
            "	text-decoration:none;\n" +
            "	color:white;\n" +
            "	width:120px;\n" +
            "	display:block;\n" +
            "	padding:15px;\n" +
            "	font-size:17px;\n" +
            "	font-family:Helvetica;\n" +
            "	transition :0.4;\n" +
            "	}\n" +
            "	\n" +
            "	.nav a:hover {\n" +
            "	brackground:#000;\n" +
            "	transition:0,4s;}\n" +
            "	\n" +
            "	.header { padding: 30px;\n" +
            "  text-align: center;\n" +
            " background: black;\n" +
            "  color: white;\n" +
            "  font-size: 10px;\n" +
            "}"

            +".dropdown-content {" +
            "  display: none;" +
            "  position: absolute;" +
            "  background-color: #f9f9f9;" +
            "  min-width: 160px;" +
            "  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);" +
            "  z-index: 1;" +
            "}"
                       +
            ".dropdown-content a {\n" +
            "  float: none;\n" +
            "  color: black;\n" +
            "  padding: 12px 16px;\n" +
            "  text-decoration: none;\n" +
            "  display: block;\n" +
            "  text-align: left;\n" +
            "}"+	
                ".dropdown-content a:hover {" +
            "  background-color: #ddd;" +
            "}" 
            +
            ".dropdown:hover .dropdown-content {" +
            "  width:120px;" +
            "	display:block;" +
            "	padding:15px;" +
            "	font-size:17px;" +
            "	font-family:Helvetica;	}"           

              +"form {\n" +
            "	width: 100%;\n" +
            "	text-align:center;}"
                                 + "input[type=submit] {\n" +
            "  width: 50%;\n" +
            "  background-color: #4CAF50;\n" +
            "  color: white;\n" +
            "  padding: 14px 20px;\n" +
            "  margin: 8px 0;\n" +
            "  border: none;\n" +
            "  border-radius: 4px;\n" +
            "  cursor: pointer;\n" +
            "}\n" +
            "\n" +
            "input[type=submit]:hover {\n" +
            "  background-color: #45a049;\n" +
            "}"        
            
              +".notification {\n" +
      
        "  color: white;\n" +
        "  text-decoration: none;\n" +
        "  padding: 15px 26px;\n" +
        "  position: relative;\n" +
        "  display: inline-block;\n" +
        "  border-radius: 2px;\n" +
        "}"
        
        +".notification .badge {\n" +
        "  position: absolute;\n" +
        "  top: 0px;\n" +
        "  right: 30px;\n" +
        "  padding: 5px 10px;\n" +
        "  border-radius: 50%;\n" +
        "  background-color: red;\n" +
        "  color: white;\n" +
        "}";
        
        return css;
    }
    
    public static String Css_Edit(){
        String css = "p {  font-family: verdana;\n" +
                        "  font-size: 20px; size:10px;}";
        css = css.concat("body {font-family: Arial, Helvetica, sans-serif;}\n" +
                "\n" +
                "/* Full-width input fields */\n" +
                "input[type=text], input[type=password] {\n" +
                "  width: 30%;\n" +
                "  padding: 12px 20px;\n" +
                "  margin: 8px 0;\n" +
                "  display: inline-block;\n" +
                "  border: 1px solid #ccc;\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "/* Set a style for all buttons */\n" +
                "button {\n" +
                "  background-color: #4CAF50;\n" +
                "  color: white;\n" +
                "  padding: 14px 20px;\n" +
                "  margin: 8px 0;\n" +
                "  border: none;\n" +
                "  cursor: pointer;\n" +
                "  width: 30%;\n" +
                "  text-align:center;\n" +
                "}\n" +
                "\n" +
                "button:hover {\n" +
                "  opacity: 0.8;\n" +
                "  text-align:center;\n" +
                "}\n" +
                "\n" +
                "/* Extra styles for the cancel button */\n" +
                ".cancelbtn {\n" +
                "  width: auto;\n" +
                "  padding: 10px 18px;\n" +
                "  background-color: #f44336;\n" +
                "}\n" +
                "\n" +
                "/* Center the image and position the close button */\n" +
                ".imgcontainer {\n" +
                "  text-align: center;\n" +
                "  margin: 24px 0 12px 0;\n" +
                "  position: relative;\n" +
                "}\n" +
                "\n" +
                "img.avatar {\n" +
                "  width: 40%;\n" +
                "  border-radius: 50%;\n" +
                "}\n" +
                "\n" +
                ".container {\n" +
                "  padding: 16px;\n" +
                "  text-align:center;\n" +
                "}\n" +
                "\n" +
                "span.psw {\n" +
                "  float: right;\n" +
                "  padding-top: 16px;\n" +
                "}\n" +
                "\n" +
                "/* The Modal (background) */\n" +
                ".modal {\n" +
                "  display: none; /* Hidden by default */\n" +
                "  position: fixed; /* Stay in place */\n" +
                "  z-index: 1; /* Sit on top */\n" +
                "  left: 0;\n" +
                "  top: 0;\n" +
                "  width: 100%; /* Full width */\n" +
                "  height: 100%; /* Full height */\n" +
                "  overflow: auto; /* Enable scroll if needed */\n" +
                "  background-color: rgb(0,0,0); /* Fallback color */\n" +
                "  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */\n" +
                "  padding-top: 60px;\n" +
                "}\n" +
                "\n" +
                "/* Modal Content/Box */\n" +
                ".modal-content {\n" +
                "  background-color: #fefefe;\n" +
                "  margin: 5% auto 15% auto; /* 5% from the top, 15% from the bottom and centered */\n" +
                "  border: 1px solid #888;\n" +
                "  width: 80%; /* Could be more or less, depending on screen size */\n" +
                "}\n" +
                "\n" +
                "/* The Close Button (x) */\n" +
                ".close {\n" +
                "  position: absolute;\n" +
                "  right: 25px;\n" +
                "  top: 0;\n" +
                "  color: #000;\n" +
                "  font-size: 35px;\n" +
                "  font-weight: bold;\n" +
                "}\n" +
                "\n" +
                ".close:hover,\n" +
                ".close:focus {\n" +
                "  color: red;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                "\n" +
                "/* Add Zoom Animation */\n" +
                ".animate {\n" +
                "  -webkit-animation: animatezoom 0.6s;\n" +
                "  animation: animatezoom 0.6s\n" +
                "}\n" +
                "\n" +
                "@-webkit-keyframes animatezoom {\n" +
                "  from {-webkit-transform: scale(0)} \n" +
                "  to {-webkit-transform: scale(1)}\n" +
                "}\n" +
                "  \n" +
                "@keyframes animatezoom {\n" +
                "  from {transform: scale(0)} \n" +
                "  to {transform: scale(1)}\n" +
                "}\n" +
                "\n" +
                "/* Change styles for span and cancel button on extra small screens */\n" +
                "@media screen and (max-width: 300px) {\n" +
                "  span.psw {\n" +
                "     display: block;\n" +
                "     float: none;\n" +
                "  }\n" +
                "  .cancelbtn {\n" +
                "     width: 100%;\n" +
                "  }\n" +
                "}");
        css = css.concat("article {\n" +
"		text-align: center;\n" +
"		}");
        css = css.concat(" header {text-align:center;}");
        return css;
        
    }
    
    public static String Css_Edit2(){
        String css = "form {\n" +
            "	width: 100%;\n" +
            "	text-align:center;}\n" +
            "	\n" +
            "	input {\n" +
            "	width: 50%;\n" +
            "	height: 5%;\n" +
            "	 padding: 12px 20px;\n" +
            "  margin: 8px 0;\n" +
            "  display: inline-block;\n" +
            "  border: 1px solid #ccc;\n" +
            "  border-radius: 4px;\n" +
            "  box-sizing: border-box;\n" +
            "	text-align:center;}\n" +
            "	\n" +
            "	input[type=submit] {\n" +
            "  width: 50%;\n" +
            "  background-color: #4CAF50;\n" +
            "  color: white;\n" +
            "  padding: 14px 20px;\n" +
            "  margin: 8px 0;\n" +
            "  border: none;\n" +
            "  border-radius: 4px;\n" +
            "  cursor: pointer;\n" +
            "}\n" +
            "\n" +
            "input[type=submit]:hover {\n" +
            "  background-color: #45a049;\n" +
            "}\n" +
            "\n" +
            "\n" +
            "div {\n" +
            "  border-radius: 5px;\n" +
            "  background-color: #f2f2f2;\n" +
            "  padding: 20px;\n" +
            "}  \n" +
            "\n" +
            "header{text-align:center;\n" +
            "border-radius: 5px;\n" +
            "  background-color: #f2f2f2;\n" +
            "  padding: 20px;}\n" +
            "	";
        css = css.concat("p {  font-family: verdana;\n" +
                        "  font-size: 20px;text-align:center;}");
        return css;
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet css</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet css at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
