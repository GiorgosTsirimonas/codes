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
@WebServlet(name = "Insert_Technician", urlPatterns = {"/Insert_Technician"})
public class Insert_Technician extends HttpServlet {

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
           
            String Name = request.getParameter("Name"), Password = request.getParameter("Password"),  Address = request.getParameter("Address");
            String Speciality_1 = request.getParameter("Speciality_1"), Speciality_2 = request.getParameter("Speciality_2");
            
            int Age = Integer.parseInt(request.getParameter("Age")), Working_years = Integer.parseInt(request.getParameter("Working_Years"));
            int Daysoff = Integer.parseInt(request.getParameter("Daysoff")), Children =  Integer.parseInt(request.getParameter("Children"));
            int Salary = Integer.parseInt(request.getParameter("Salary")), Overtimes = Integer.parseInt(request.getParameter("Overtimes"));
         
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            String name = CurrentUser.CurrentName;
            ResultSet rs = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME = '" + name + "'");
            int id = 1;
            if (rs.next()) {
               id = rs.getInt("B_ID");
               out.println("to B_ID einai");
               out.println(id);
            }
            
           stmt.executeUpdate("INSERT INTO TECHNICIAN (NAME,PASSWORD,CHILDREN,ADDRESS,AGE,SALARY,WORKING_YEARS,D_ID,DAYSOFF,OVERTIMES,SPECIALITY_1,SPECIALITY_2)" + "VALUES ('"+Name+"','"+Password+"',"+Children+",'"+Address+"',"+Age+","+Salary+","+Working_years+","+id+","+Daysoff+","+Overtimes+",'"+Speciality_1+"','"+Speciality_2+"')");
           response.sendRedirect("Boss_Home");
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
            Logger.getLogger(Insert_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Insert_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
