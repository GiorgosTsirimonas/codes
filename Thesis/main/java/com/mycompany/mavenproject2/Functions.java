/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Functions", urlPatterns = {"/Functions"})
public class Functions extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static String Html(){
        String html = "<!DOCTYPE html><html><head>";
        return html;
    }
    
    public static String Html2(){
        String html = "<div class=\"container\"><div class=\"row\"><div class=\"card\"></div><div class=\"card\"><article class=\"card-body\">";
        return html;
    }
    
   public static String Html3(){
        String html = "</article></div></body></html>";
        return html;
    }
    
    public static String Boss_Nav() throws SQLException{
        
        String techn ="Staff_Techn", main = "Boss_Home",  profile = "Boss_Profile", chat = "Chat", calendar = "Boss_Calendar", logout= "Logout.jsp", nav= "",
                eomtec="EOM_Techn", eom = "EOM", staff = "Staff", register = "register.html", monthly ="Boss_Monthly", weekly="Boss_Weekly", dailyemp = "Daily_Employees",
                weeklyemp="Weekly_Employees", monthlyemp="Monthly_Employees", dailytec="Daily_Technicians", weeklytec="Weekly_Technician", monthlytec="Monthly_Technicians"; 
        int not = 0;
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
        Statement stmt = conn.createStatement();
        ResultSet t = stmt.executeQuery("SELECT COUNT(SAW) FROM MAILS WHERE (SAW = 1 AND RECEIVER_NAME = '"+CurrentUser.CurrentName+"')");
        t.next();
        not = t.getInt(1);
        nav = "<div class=\"nav\">"+
                "<ul>\n" +
                    "<li class=\\\"active\\\"><a href=\""+main+"\">Home</a></li>\n" +
                    "<li><a href=\""+profile+"\">Profile</a></li>\n" +
                    "<li><div class=\"dropdown\"> \n" +
                    "<a href=\""+staff+"\">Staff</a>"+
                    "<div class=\"dropdown-content\">\n" +
                        "<a href=\""+staff+"\">Employees</a>\n" +
                        "<a href=\""+techn +"\">Technicians</a>\n" +
                    "</div>\n" +
            "</div>\n" +
            "</li>\n" +
            "<li><div class=\"dropdown\"> \n" +
                        "<a href=\""+calendar+"\">Calendar</a>"+
                        "<div class=\"dropdown-content\">\n" +
                            "<a href=\""+calendar+"\">Daily</a>\n" +
                            "<a href=\""+weekly+"\">Weekly</a>\n" +
                            "<a href=\""+monthly+"\">Monthly</a>"+
                            "<a href=\""+dailyemp+"\">E_Daily</a>"+
                            "<a href=\""+weeklyemp+"\">E_Weekly</a>"+
                            "<a href=\""+monthlyemp+"\">E_Monthly</a>"+
                            "<a href=\""+dailytec+"\">T_Daily</a>"+
                            "<a href=\""+weeklytec+"\">T_Weekly</a>"+
                            "<a href=\""+monthlytec+"\">T_Monthly</a>"+
                        "</div>\n" +
            "</div>\n" +
            "</li>\n" +
            "<li><a href="+register+">Register</a></li>"+
            "<li><div class=\"dropdown\"> \n" +
            "<a href=\""+eom+"\">EOM</a>"+ 
                "<div class=\"dropdown-content\">\n" +
                    "<a href="+eom+">Employees</a>"+
                    "<a href="+eomtec+">Technicians</a>"+
            "</div>\n" +
            "</div>\n" +
            "</li>\n";
            
            if (not != 0){
                nav = nav.concat("<li><a href=\""+chat+"\" class=\"notification\">Chat<span class=\"badge\">"+not+"</span></a></li>"+
                        "<li><a href="+logout+">Logout</a></li>"+
                " </ul> </div></div>");
            }else{
                nav = nav.concat("<li><a href=\""+chat+"\">Chat</a></li>"+
                       "<li><a href="+logout+">Logout</a></li>"+
                " </ul> </div></div>");
            }
            conn.close();
            stmt.close();
            return nav;
    }
    
    public static String Techn_Nav() throws SQLException{
        String main = "Technician_Home", profile = "Technician_Profile", department = "Technician_Department", calendar = "Technician_Calendar", logout= "Logout.jsp",
               cal_week = "Technician_Cal_Week", cal_month = "Technician_Cal_Month", chat = "Chat_Technician";
        
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
        Statement stmt = conn.createStatement();
        int not = 0;
        ResultSet t = stmt.executeQuery("SELECT COUNT(SAW) FROM MAILS WHERE (SAW = 1 AND RECEIVER_NAME = '"+CurrentUser.CurrentName+"')");
        t.next();
        not = t.getInt(1);
        String nav = "<div class=\"nav\">"+
            "<ul>\n" +
                "<li class=\\\"active\\\"><a href=\""+main+"\">Home</a></li>\n" +
                "<li><a href=\""+profile+"\">Profile</a></li>\n" +
                "<li><a href=\""+department+"\">Department</a></li>\n" +
                "<li>\n" +
                "<div class=\"dropdown\"> \n" +
                   "<a href=\""+calendar+"\">Calendar</a>"+
                "<div class=\"dropdown-content\">\n" +
                    "<a href=\""+calendar+"\">Daily</a>\n" +
                    "<a href=\""+cal_week+"\">Weekly</a>\n" +
                    "<a href=\""+cal_month+"\">Monthly</a>\n" +
                "</div>\n" +
                "</div>\n" +
                "</li> ";
            
        if (not != 0){
            nav = nav.concat("<li><a href=\""+chat+"\" class=\"notification\">Chat<span class=\"badge\">"+not+"</span></a></li>"+
                  "<li><a href="+logout+">Logout</a></li>"+
                    "</ul> </div></div>");
        }else{
            nav = nav.concat("<li><a href=\""+chat+"\">Chat</a></li>"+
                  "<li><a href="+logout+">Logout</a></li>"+
                    "</ul> </div></div>");
        }
        conn.close();
        stmt.close();
        return nav;
    }
    
    public static String Employee_Nav() throws SQLException{
        String main = "Employee_Home", profile = "Employee_Profile", department = "Employee_Department", calendar = "Employee_Calendar",
            cal_week = "Employee_Cal_Week", cal_month = "Employee_Cal_Monthly", logout= "Logout.jsp", nav ="", chat = "Chat_Employee";
        
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
        Statement stmt = conn.createStatement();
        int not = 0;
        ResultSet t = stmt.executeQuery("SELECT COUNT(SAW) FROM MAILS WHERE (SAW = 1 AND RECEIVER_NAME = '"+CurrentUser.CurrentName+"')");
        t.next();
        not = t.getInt(1);
        nav = "<div class=\"nav\">"+
                    "<ul>\n" +
                        "<li class=\\\"active\\\"><a href=\""+main+"\">Home</a></li>\n" +
                        "<li><a href=\""+profile+"\">Profile</a></li>\n" +
                        "<li><a href=\""+department+"\">Department</a></li>\n" +
                        "<li>\n" +
                        "<div class=\"dropdown\"> \n" +
                            "<a href=\""+calendar+"\">Calendar</a>"+
                            "<div class=\"dropdown-content\">\n" +
                                "<a href=\""+calendar+"\">Daily</a>\n" +
                                "<a href=\""+cal_week+"\">Weekly</a>\n" +
                                "<a href=\""+cal_month+"\">Monthly</a>\n" +
                            "</div>\n" +
                        "</div>\n" +
                    "</li> ";
            
        if (not != 0){
            nav = nav.concat("<li><a href=\""+chat+"\" class=\"notification\">Chat<span class=\"badge\">"+not+"</span></a></li>"+
                "<li><a href="+logout+">Logout</a></li>"+
                "</ul> </div></div>");
        }else{
            nav = nav.concat("<li><a href=\""+chat+"\">Chat</a></li>"+
                "<li><a href="+logout+">Logout</a></li>"+
                "</ul> </div></div>");
        }
        return nav;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Functions</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Functions at " + request.getContextPath() + "</h1>");
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
