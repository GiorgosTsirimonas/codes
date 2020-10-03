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
import java.sql.PreparedStatement;
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
@WebServlet(name = "Edit_Employee", urlPatterns = {"/Edit_Employee"})
public class Edit_Employee extends HttpServlet {

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
            
            String Name = request.getParameter("Name");
            String Password = request.getParameter("Password");
            String Address = request.getParameter("Address");
            String Bachelor = request.getParameter("Bachelor");
            String Master = request.getParameter("Master");
            String Children =  request.getParameter("Children");
            String Salary = request.getParameter("Salary");
            String Working_years = request.getParameter("Working_years");
            String Daysoff =request.getParameter("Daysoff");
            String D_ID = request.getParameter("D_ID");
            String Age = request.getParameter("Age");
            String Overtimes = request.getParameter("Overtimes");
            String e_id = request.getParameter("e_id");
            int id = Integer.parseInt(e_id);	
           
            if (Name != ""){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET NAME = '" + Name+ "' WHERE E_ID = " + id + " ");
            }
          
            if (Password != ""){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET PASSWORD = '" + Password+ "' WHERE E_ID = " + id + " ");
            }
          
            if (Address != ""){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET ADDRESS = '" + Address+ "' WHERE E_ID = " + id + " ");
            }
           
            if (Bachelor != ""){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET BACHELOR = '" + Bachelor+ "' WHERE E_ID = " + id + " ");
            }
            
            if (Master != ""){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET MSC = '" + Master+ "' WHERE E_ID = " + id + " ");
            }
           
            if (Children != ""){
                int ch = Integer.parseInt(Children);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET CHILDREN = " + ch+ " WHERE E_ID = " + id + " ");
            }
            
            if (Salary != ""){
                int sal = Integer.parseInt(Salary);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET SALARY = " + sal+ " WHERE E_ID = " + id + " ");
            }
         
            if (Working_years != ""){
                int work = Integer.parseInt(Working_years);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET WORKING_YEARS = " + work+ " WHERE E_ID = " + id + " ");
            }
         
            if (Daysoff != ""){
                int days = Integer.parseInt(Daysoff);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET DAYSOFF = " + days+ " WHERE E_ID = " + id + " ");
            }
         
            if (D_ID != ""){
                int did = Integer.parseInt(D_ID);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET D_ID = " + did+ " WHERE E_ID = " + id + " ");
            }
       
            if (Age != ""){
                int ilikia = Integer.parseInt(Age);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET AGE = " + ilikia+ " WHERE E_ID = " + id + " ");
            }
          
            if (Overtimes != ""){
                int over = Integer.parseInt(Overtimes);
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET OVERTIMES = " +over+ " WHERE E_ID = " + id + " ");
            }
         
            response.sendRedirect("Staff");
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
            Logger.getLogger(Edit_Employee.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Edit_Employee.class.getName()).log(Level.SEVERE, null, ex);
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
