/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Boss_Nav;
import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
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
 * @author dgiorgos tsirimonas
 */
@WebServlet(name = "Boss_Profile", urlPatterns = {"/Boss_Profile"})
public class Boss_Profile extends HttpServlet {

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
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
           
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }
         
            out.println(Html());
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
           
            out.write("<style>");
            String css = Css();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet Boss_Profile</title>");            
            out.println("</head>");
            out.println("<body>");
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
          
            out.println(Boss_Nav());
            String teststring, onoma="";
            String name = CurrentUser.CurrentName;
            ResultSet rs = stmt.executeQuery("SELECT * FROM APP.BOSS WHERE NAME = '" + name + "'");
            int ilikia=0, salary=0, years=0, did=0, daysoff=0, overtimes=0, children=0;
            if (rs.next()) {
               ilikia = rs.getInt("AGE");
               onoma = rs.getString("ADDRESS");
               salary = rs.getInt("SALARY");
               years = rs.getInt("WORKING_YEARS");
               did = rs.getInt("B_ID");
               daysoff = rs.getInt("DAYSOFF");
               overtimes = rs.getInt("OVERTIMES");
               children = rs.getInt("CHILDREN");
            }
            
            out.write(Html2());
            teststring ="<table border='3' id=\"lol\"  class=\"table table-hover table-blue\">" +"<thead>"+
                          "<tr>"+
                               "<td>Name</td>"+"<td>"+name+"</td>"+
                           "</tr>"+
                            "<tr>"+
                               "<td>Age</td>"+"<td>"+String.valueOf(ilikia)+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>B_ID</td>" +"<td>"+String.valueOf(did)+
                            "</tr>"+
                            "<tr>"+
                               "<td>Salary</td>" +"<td>"+String.valueOf(salary)+"</td>"+
                            "</tr>"+
                            "<tr>"+           
                                    "<td>Working_Years</td>" +"<td>"+String.valueOf(years)+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Daysoff</td>" +"<td>"+String.valueOf(daysoff)+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Overtimes</td>" +"<td>"+String.valueOf(overtimes)+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Children</td>" +"<td>"+String.valueOf(children)+"</td>"+
                              "<tr>"+ 
                             "<td>Edit</td>"+"<td>"+"<form action=\"Edit_Boss\" method=\"POST\">"+
                        "<input type=\"submit\" value=\"Edit\" />"+
                        "</form>"+"</td>"+
                        "</tr>"+"<thead>"+   "<tbody>"+"</table>";
        
            out.println("<br>");
            out.println("<p>This is your info</p>");
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
            Logger.getLogger(Boss_Profile.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Boss_Profile.class.getName()).log(Level.SEVERE, null, ex);
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
