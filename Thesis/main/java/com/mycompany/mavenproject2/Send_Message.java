/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Html;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
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
@WebServlet(name = "Send_Message", urlPatterns = {"/Send_Message"})
public class Send_Message extends HttpServlet {

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
            out.println("<title>Servlet Send_Message</title>");       
        
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
         
            out.println("</head>");
            out.println("<body>");
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            
            String receiver_name = "", receiver_type = "", sender_name = "", sender_type = "", message = "";
            int receiver_id = -1, sender_id = -1;
            receiver_name = request.getParameter("receiver_name");
            receiver_type = request.getParameter("receiver_type");
            sender_name = request.getParameter("sender_name");
            sender_type = request.getParameter("sender_type");
            message = request.getParameter("message");
            
            receiver_id = Integer.parseInt(request.getParameter("receiver_id"));
            sender_id = Integer.parseInt(request.getParameter("sender_id"));
            
            LocalDateTime now = LocalDateTime.now();
            int year = now.getYear(), month = now.getMonthValue(), day = now.getDayOfMonth(), hour = now.getHour(), minute = now.getMinute(), second = now.getSecond();
            
            stmt.executeUpdate("INSERT INTO MAILS (SENDER_NAME,SENDER_TYPE,SENDER_ID,RECEIVER_NAME,RECEIVER_TYPE,RECEIVER_ID,MESSAGE,YEARS,MONTHS,DAYS,HOURS,MINUTES,SECONDS,SAW)" + "VALUES ('"+sender_name+"','"+sender_type+"',"
                    + ""+sender_id+",'"+receiver_name+"','"+receiver_type+"',"+receiver_id+",'"+message+"',"+year+","+month+","+day+","+hour+","+minute+","+second+","+1+")");
            
            if (sender_type.equals("employee")){
                response.sendRedirect("Chat_view_Employee");
            }else if (sender_type.equals("technician")){
                response.sendRedirect("Chat_view_Technician");
            }else{
               response.sendRedirect("Chat_view"); 
            }
            
            conn.close();
            stmt.close();
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Send_Message.class.getName()).log(Level.SEVERE, null, ex);
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
