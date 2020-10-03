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
@WebServlet(name = "Recall_2", urlPatterns = {"/Recall_2"})
public class Recall_2 extends HttpServlet {

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
           
            String em = request.getParameter("e_id"), type = request.getParameter("type"), d = request.getParameter("day");
            String month = request.getParameter("month"), direction= request.getParameter("direction"), first = request.getParameter("8-10");
            String second = request.getParameter("10-12"), third = request.getParameter("12-14"), forth = request.getParameter("14-16");
            String over = request.getParameter("Overtime");
            int dayoff=0,overtimes=0;
            
            stmt.executeUpdate("DELETE FROM APP.DAY WHERE ( ID =  " +Integer.parseInt(em)+"   AND PERSON_TYPE = '"+type+"' AND DAY_NUM = "+Integer.parseInt(d)+" AND MONTH_NUM =  "+Integer.parseInt(month)+") " );
            if (type.equals("BOSS")){
                ResultSet r=stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.BOSS WHERE (B_ID  = "+Integer.parseInt(em)+" )");
                if (r.next()){
                     dayoff = r.getInt("DAYSOFF") + 1;
                     overtimes = r.getInt("OVERTIMES") + Integer.parseInt(over) ;
                }
                stmt.executeUpdate("UPDATE APP.BOSS SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( B_ID = " + Integer.parseInt(em) + " )");
                stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+Integer.parseInt(em)+",'"+type+"',"+Integer.parseInt(d)+","+Integer.parseInt(month)+",'"+first+"','"+second+"','"+third+"','"+forth+"',"+Integer.parseInt(over)+")"); 
            }else if (type.equals("EMPLOYEE")){
                ResultSet r=stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.EMPLOYEE WHERE (E_ID  = "+Integer.parseInt(em)+" )");
                if (r.next()){
                     dayoff = r.getInt("DAYSOFF") + 1;
                     overtimes = r.getInt("OVERTIMES") + Integer.parseInt(over) ;
                }
                stmt.executeUpdate("UPDATE APP.EMPLOYEE SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( E_ID = " + Integer.parseInt(em) + " )");
                stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+Integer.parseInt(em)+",'"+type+"',"+Integer.parseInt(d)+","+Integer.parseInt(month)+",'"+first+"','"+second+"','"+third+"','"+forth+"',"+Integer.parseInt(over)+")");          
            }else{
                ResultSet r=stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.TECHNICIAN WHERE (T_ID  = "+Integer.parseInt(em)+" )");
                if (r.next()){
                     dayoff = r.getInt("DAYSOFF") + 1;
                     overtimes = r.getInt("OVERTIMES") + Integer.parseInt(over) ;
                }
                stmt.executeUpdate("UPDATE APP.TECHNICIAN SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( T_ID = " + Integer.parseInt(em) + " )");
                stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+Integer.parseInt(em)+",'"+type+"',"+Integer.parseInt(d)+","+Integer.parseInt(month)+",'"+first+"','"+second+"','"+third+"','"+forth+"',"+Integer.parseInt(over)+")");
             }
            response.sendRedirect(direction);  
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
            Logger.getLogger(Recall_2.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Recall_2.class.getName()).log(Level.SEVERE, null, ex);
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
