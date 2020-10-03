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
@WebServlet(name = "Edit_Boss2", urlPatterns = {"/Edit_Boss2"})
public class Edit_Boss2 extends HttpServlet {

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
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            String Password = request.getParameter("Password");
            String Address = request.getParameter("Address");
            String Children =  request.getParameter("Children");
            String Salary = request.getParameter("Salary");
            String Working_years = request.getParameter("Working_years");
            String Daysoff =request.getParameter("Daysoff");
            String Age = request.getParameter("Age");
            String Overtimes = request.getParameter("Overtimes");
          
            ResultSet rs = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            int id=0;
            if (rs.next()){
                id = rs.getInt("B_ID");
            }
            if (Password != ""){
               out.println(Password);
               stmt.executeUpdate("UPDATE APP.BOSS SET PASSWORD = '" + Password+ "' WHERE B_ID = " + id + " ");
            }
          
            if (Address != ""){
                stmt.executeUpdate("UPDATE APP.BOSS SET ADDRESS = '" + Address+ "' WHERE B_ID = " + id + " ");
            }
            
            if (Children != ""){
                int ch = Integer.parseInt(Children);
                stmt.executeUpdate("UPDATE APP.BOSS SET CHILDREN = " + ch+ " WHERE B_ID = " + id + " ");
            }
          
            if (Salary != ""){
                int sal = Integer.parseInt(Salary);
                stmt.executeUpdate("UPDATE APP.BOSS SET SALARY = " + sal+ " WHERE B_ID = " + id + " ");
            }
          
            if (Working_years != ""){
                int work = Integer.parseInt(Working_years);
                stmt.executeUpdate("UPDATE APP.BOSS SET WORKING_YEARS = " + work+ " WHERE B_ID = " + id + " ");
            }
          
            if (Daysoff != ""){
                int days = Integer.parseInt(Daysoff);
                stmt.executeUpdate("UPDATE APP.BOSS SET DAYSOFF = " + days+ " WHERE B_ID = " + id + " ");
            }
            
            if (Age != ""){
                int ilikia = Integer.parseInt(Age);
                stmt.executeUpdate("UPDATE APP.BOSS SET AGE = " + ilikia+ " WHERE B_ID = " + id + " ");
            }
           
            if (Overtimes != ""){
                int over = Integer.parseInt(Overtimes);
                stmt.executeUpdate("UPDATE APP.BOSS SET OVERTIMES = " +over+ " WHERE B_ID = " + id + " ");
            }
           
            response.sendRedirect("Boss_Profile");
            
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
            Logger.getLogger(Edit_Boss2.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Edit_Boss2.class.getName()).log(Level.SEVERE, null, ex);
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
