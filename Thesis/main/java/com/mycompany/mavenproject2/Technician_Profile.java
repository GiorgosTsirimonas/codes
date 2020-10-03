/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
import static com.mycompany.mavenproject2.Functions.Techn_Nav;
import static com.mycompany.mavenproject2.css.Css;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Technician_Profile", urlPatterns = {"/Technician_Profile"})
public class Technician_Profile extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            out.println(Html());
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            out.write("<style>");
            String css = Css();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet Technician_Profile</title>");            
            out.println("</head>");
            out.println("<body>");
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            CurrentUser.Ses=1;
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            out.println(Techn_Nav());
            out.write(Html2());
            out.println("<br></br>");
            out.println("<p>These are your data</p>");
            
            ResultSet rs = stmt.executeQuery("SELECT AGE,CHILDREN,ADDRESS,SALARY,WORKING_YEARS,D_ID,DAYSOFF,SPECIALITY_1,SPECIALITY_2,OVERTIMES,T_ID   FROM APP.TECHNICIAN WHERE NAME = '" + CurrentUser.CurrentName + "'");
         
            int ilikia=-1,paidia=-1,salary=-1,years=-1,did=-1,repo=-1,overtimes=-1,e_id=-1;
            String onoma=CurrentUser.CurrentName,address="",spe="",spe2="";
            
            if (rs.next()) {
               e_id = rs.getInt("T_ID");
               ilikia = rs.getInt("AGE");
               paidia = rs.getInt("CHILDREN");
               address = rs.getString("ADDRESS");
               salary = rs.getInt("SALARY");
               years = rs.getInt("WORKING_YEARS");
               did = rs.getInt("D_ID");
               repo = rs.getInt("DAYSOFF");
               spe = rs.getString("SPECIALITY_1");
               spe2 = rs.getString("SPECIALITY_2");
               overtimes = rs.getInt("OVERTIMES");
            }
            
            String teststring ="<table border='1' id=\"lol\" class=\"table table-hover table-blue\" >" +
                "<thead>" + 
                    "<tr>"+
                        "<td>Name</td>"+"<td>"+CurrentUser.CurrentName+"</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>Age</td>"+"<td>"+ilikia+"</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>Department</td>" +"<td>"+did+
                    "</tr>"+
                    "<tr>"+
                        "<td>ID</td>" +"<td>"+e_id+
                    "</tr>"+
                    "<tr>"+
                        "<td>Salary</td>" +"<td>"+salary+"</td>"+
                    "</tr>"+
                    "<tr>"+           
                        "<td>Working_Years</td>" +"<td>"+years+"</td>"+
                    "</tr>"+
                    "<tr>"+
                         "<td>Daysoff</td>" +"<td>"+repo+"</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>Overtimes</td>" +"<td>"+overtimes+"</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>Children</td>" +"<td>"+paidia+"</td>"+"</tr>"+
                    "<tr>"+
                        "<td>Speciality_1</td>" +"<td>"+spe+"</td>"+"</tr>"+
                    "<tr>"+
                         "<td>Speciality_2</td>" +"<td>"+spe2+"</td>"+"</tr>"+"<thead>"+"<tbody>"+"</table>";
            out.write(teststring); 
            out.write(Html3());
            conn.close();
            stmt.close();
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Technician_Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Technician_Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
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
