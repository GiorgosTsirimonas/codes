/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Employee_Nav;
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
import java.util.Calendar;
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
@WebServlet(name = "Employee_Home", urlPatterns = {"/Employee_Home"})
public class Employee_Home extends HttpServlet {

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
            
            CurrentUser.Ses=1;
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
           out.println("<title>Servlet Employee_Home</title>");            
           out.println("</head>");
           out.println("<body>");
           Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
           Statement stmt = conn.createStatement();
           try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
           } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
           }
            
            out.println(Employee_Nav());
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR), e_id = 0;
            String type = "EMPLOYEE",dayoff = "dayoff",first = "",second = "", third = "",forth = "",over = "";
            ResultSet r = stmt.executeQuery("SELECT E_ID FROM APP.EMPLOYEE WHERE (NAME  = '"+CurrentUser.CurrentName+"' )");
            if (r.next()){
                 e_id = r.getInt("E_ID");
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+e_id+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '"+type+"')");
             
            out.write(Html2());
            out.println("<br></br>");
            if (rs.next() == false) {
                out.println("<p>There are not any assignments for today</p>");
            }else{
                out.println("<p>There are your assignments for today</p>");
                first = rs.getString("FIRST_2");
                second = rs.getString("SECOND_2");
                third = rs.getString("THIRD_2");
                forth = rs.getString("FORTH_2");
                over = rs.getString("OVERTIME");
                String  teststring=("<table border='3' id=\"lol\"  class=\"table table-hover table-blue\" >" +
                          "<thead>" +
                          "<tr>"+
                               "<td>8-10</td>"+"<td>"+first+"</td>"+
                           "</tr>"+
                           "<tr>"+
                               "<td>10-12</td>"+"<td>"+second+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>12-14</td>"+"<td>"+third+"</td>"+
                          "</tr>"+
                         "<tr>"+
                               "<td>14-16</td>" +"<td>"+forth+"</td>"+
                          "</tr>"+
                          "<tr>"+
                               "<td>Overtime</td>" +"<td>"+Integer.parseInt(over)+"</td>"+
                          "</tr>"+
                          "<tr>"+"<thead>"+"<tbody>"+"</table>");
                   out.write(teststring); 
            }
            
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
            Logger.getLogger(Employee_Home.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Employee_Home.class.getName()).log(Level.SEVERE, null, ex);
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
