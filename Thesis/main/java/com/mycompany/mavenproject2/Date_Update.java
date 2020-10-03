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
@WebServlet(name = "Date_Update", urlPatterns = {"/Date_Update"})
public class Date_Update extends HttpServlet {

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
           
            String over = request.getParameter("Overtime");
            String first = request.getParameter("first");
            String second = request.getParameter("second");
            String third = request.getParameter("third");
            String forth = request.getParameter("forth");
            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String type = request.getParameter("type");
            String id = request.getParameter("e_id");
            String direction = request.getParameter("direction");
            int sum = 0;
            
            if (over != ""){
                stmt.executeUpdate("UPDATE APP.DAY SET OVERTIME = " + Integer.parseInt(over)+ " WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                
                ResultSet r = stmt.executeQuery("SELECT OVERTIME FROM APP.DAY WHERE (ID  = "+Integer.parseInt(id)+" AND PERSON_TYPE = '"+type+"' AND MONTH_NUM = "+Integer.parseInt(month)+")");
                while (r.next()){
                    out.println("the overtime of the day is "+r.getInt("OVERTIME"));
                    sum = sum + r.getInt("OVERTIME");
                }
                if (type.equals("BOSS")){
                    stmt.executeUpdate("UPDATE APP.BOSS SET OVERTIMES = "+sum+"  WHERE B_ID = "+Integer.parseInt(id)+" ");
                }else if (type.equals("EMPLOYEE")){
                    
                    stmt.executeUpdate("UPDATE APP.EMPLOYEE SET OVERTIMES = "+sum+"  WHERE E_ID = "+Integer.parseInt(id)+" ");
                }else{
                    stmt.executeUpdate("UPDATE APP.TECHNICIAN SET OVERTIMES = "+sum+"  WHERE T_ID = "+Integer.parseInt(id)+" ");
                }
            }
            
            if (first != ""){
                stmt.executeUpdate("UPDATE APP.DAY SET FIRST_2 = '" +first + "' WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
            }
           
            if (second != ""){
                stmt.executeUpdate("UPDATE APP.DAY SET SECOND_2 = '" +second + "' WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
            }
            
            if (third != ""){
                stmt.executeUpdate("UPDATE APP.DAY SET THIRD_2 = '" +third + "' WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
            }
            
            if (forth != ""){
                stmt.executeUpdate("UPDATE APP.DAY SET FORTH_2 = '" +forth + "' WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
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
            Logger.getLogger(Date_Update.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Date_Update.class.getName()).log(Level.SEVERE, null, ex);
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
