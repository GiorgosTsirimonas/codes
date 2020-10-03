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
@WebServlet(name = "Insert_Day", urlPatterns = {"/Insert_Day"})
public class Insert_Day extends HttpServlet {

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
           
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            int id=0;
            String type = request.getParameter("type");
            if (type.equals("BOSS")){
                 String em = request.getParameter("e_id");
                 id = Integer.parseInt(em);
                 
            }else if (type.equals("EMPLOYEE")){
                String em = request.getParameter("e_id");
                id = Integer.parseInt(em);
                
            }else{
                String em = request.getParameter("e_id");
                id = Integer.parseInt(em);
            }
            
            String d = request.getParameter("day"), month = request.getParameter("month"),  first = request.getParameter("8-10"), second = request.getParameter("10-12");
            String third = request.getParameter("12-14"), forth = request.getParameter("14-16"), over = request.getParameter("Overtime"), direction = request.getParameter("direction");
            int day = Integer.parseInt(d), m = Integer.parseInt(month), sum=0;
            stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+id+",'"+type+"',"+day+","+m+",'"+first+"','"+second+"','"+third+"','"+forth+"',"+over+")");
           
            ResultSet r = stmt.executeQuery("SELECT OVERTIME FROM APP.DAY WHERE (ID  = "+id+" AND PERSON_TYPE = '"+type+"' AND MONTH_NUM = "+Integer.parseInt(month)+")");
            while (r.next()){
                sum = sum + r.getInt("OVERTIME");
            }
            if (type.equals("BOSS")){
                stmt.executeUpdate("UPDATE APP.BOSS SET OVERTIMES = "+sum+"  WHERE B_ID = "+id+" ");
           }else if (type.equals("EMPLOYEE")){
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET OVERTIMES = "+sum+"  WHERE E_ID = "+id+" ");
            }else{
               stmt.executeUpdate("UPDATE APP.TECHNICIAN SET OVERTIMES = "+sum+"  WHERE T_ID = "+id+" ");
           }
            
            response.sendRedirect(direction);
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
            Logger.getLogger(Insert_Day.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Insert_Day.class.getName()).log(Level.SEVERE, null, ex);
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
