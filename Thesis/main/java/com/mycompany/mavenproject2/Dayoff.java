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
@WebServlet(name = "Dayoff", urlPatterns = {"/Dayoff"})
public class Dayoff extends HttpServlet {

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
            
            String type = request.getParameter("type");
            String over = request.getParameter("over");
            String first = request.getParameter("first");
            String second = request.getParameter("second");
            String third = request.getParameter("third");
            String forth = request.getParameter("forth");
            String day = request.getParameter("day");
            String month = request.getParameter("month");
            String direction = request.getParameter("direction");
            String id = request.getParameter("e_id");
            String proto="dayoff";
            int dayoff=0,overtimes=0,temp=0;
            
            if (type.equals("BOSS")){
                ResultSet rs = stmt.executeQuery("SELECT FIRST_2 FROM APP.DAY WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                out.println("boss");
                if (rs.next()){
                   if (proto.equals(rs.getString("FIRST_2"))){
                       out.println("You already have a dayoff");
                   } 
                   else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.BOSS WHERE (B_ID  = "+Integer.parseInt(id)+" )");
                     if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                             out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            temp = Integer.parseInt(over);
                            overtimes = r.getInt("OVERTIMES") - temp ;
                            stmt.executeUpdate("UPDATE APP.DAY SET SECOND_2 = '" +second + "', FIRST_2 = '"+first+"' , THIRD_2 = '"+third+"' , FORTH_2 = '"+forth+"' , OVERTIME = "+0+" WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                                    " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                            stmt.executeUpdate("UPDATE APP.BOSS SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( B_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                         }
                    }
                  }
                
                }else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.BOSS WHERE (B_ID  = "+Integer.parseInt(id)+" )");
                     if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                             out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+id+",'"+type+"',"+day+","+Integer.parseInt(month)+",'"+proto+"','"+proto+"','"+proto+"','"+proto+"',"+0+")");
                            stmt.executeUpdate("UPDATE APP.BOSS SET DAYSOFF = " +dayoff+ " WHERE ( B_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                         }
                     }
                }
            
            }else if (type.equals("EMPLOYEE")){
                ResultSet rs = stmt.executeQuery("SELECT FIRST_2 FROM APP.DAY WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                if (rs.next()){
                   if (proto.equals(rs.getString("FIRST_2"))){
                       out.println("You already have a dayoff");
                   } 
                   else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.EMPLOYEE WHERE (E_ID  = "+Integer.parseInt(id)+" )");
                    if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                             out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            temp = Integer.parseInt(over);
                            overtimes = r.getInt("OVERTIMES") - temp ;
                            stmt.executeUpdate("UPDATE APP.DAY SET SECOND_2 = '" +second + "', FIRST_2 = '"+first+"' , THIRD_2 = '"+third+"' , FORTH_2 = '"+forth+"' , OVERTIME = "+0+" WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                                    " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                            stmt.executeUpdate("UPDATE APP.EMPLOYEE SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( E_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                         }
                    }
                  }
                 }else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.EMPLOYEE WHERE (E_ID  = "+Integer.parseInt(id)+" )");
                   
                     if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                                out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+id+",'"+type+"',"+day+","+Integer.parseInt(month)+",'"+proto+"','"+proto+"','"+proto+"','"+proto+"',"+0+")");
                            stmt.executeUpdate("UPDATE APP.EMPLOYEE SET DAYSOFF = " +dayoff+ " WHERE ( E_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                         }
                     }
                   }
            }else{
                ResultSet rs = stmt.executeQuery("SELECT FIRST_2 FROM APP.DAY WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                        " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                if (rs.next()){
                   if (proto.equals(rs.getString("FIRST_2"))){
                       out.println("You already have a dayoff");
                   } 
                   else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.TECHNICIAN WHERE (T_ID  = "+Integer.parseInt(id)+" )");
                    if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                             out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            temp = Integer.parseInt(over);
                            overtimes = r.getInt("OVERTIMES") - temp ;
                            stmt.executeUpdate("UPDATE APP.DAY SET SECOND_2 = '" +second + "', FIRST_2 = '"+first+"' , THIRD_2 = '"+third+"' , FORTH_2 = '"+forth+"' , OVERTIME = "+0+" WHERE ( ID = " + Integer.parseInt(id) + " AND PERSON_TYPE = '"+type+"' AND DAY_NUM = " +Integer.parseInt(day)+
                                    " AND MONTH_NUM = " + Integer.parseInt(month)+ " )");
                            stmt.executeUpdate("UPDATE APP.TECHNICIAN SET DAYSOFF = " +dayoff+ ", OVERTIMES = " + overtimes+" WHERE ( T_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                            }
                    }
                  }
                   }else{
                    ResultSet r = stmt.executeQuery("SELECT DAYSOFF,OVERTIMES FROM APP.TECHNICIAN WHERE (T_ID  = "+Integer.parseInt(id)+" )");
                
                     if (r.next()){
                         if (r.getInt("DAYSOFF") == 0){
                             out.println("No more daysoff");
                         }else{
                            dayoff = r.getInt("DAYSOFF") - 1;
                            stmt.executeUpdate("INSERT INTO APP.DAY (ID,PERSON_TYPE,DAY_NUM,MONTH_NUM,FIRST_2,SECOND_2,THIRD_2,FORTH_2,OVERTIME)" + "VALUES ("+id+",'"+type+"',"+day+","+Integer.parseInt(month)+",'"+proto+"','"+proto+"','"+proto+"','"+proto+"',"+0+")");
                            stmt.executeUpdate("UPDATE APP.TECHNICIAN SET DAYSOFF = " +dayoff+ " WHERE ( T_ID = " + Integer.parseInt(id) + " )");
                            response.sendRedirect(direction);
                         }
                     }
                   }
            }
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
            Logger.getLogger(Dayoff.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Dayoff.class.getName()).log(Level.SEVERE, null, ex);
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
