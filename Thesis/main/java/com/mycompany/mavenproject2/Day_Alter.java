/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
import static com.mycompany.mavenproject2.css.Css_Edit;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
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
@WebServlet(name = "Day_Alter", urlPatterns = {"/Day_Alter"})
public class Day_Alter extends HttpServlet {

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
           
            out.println(Html());
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            
            out.write("<style>");
            out.println(Css_Edit());
            out.write(" header {text-align:center;}");
            out.write("</style>");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"); 
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            
            String em = request.getParameter("e_id"), type = request.getParameter("type"), d = request.getParameter("day"), month = request.getParameter("month"),
                  direction = request.getParameter("direction");
            
            out.write("<header>");
            out.println("<h3>Please fill the day tasks</h3>");
            out.write("</header>");
            out.write("<nav>");
            out.write("</nav>");
            out.write(Html2());
            
            out.write("<form action=\"Date_Update\" method=\"POST\">"+
                          "<label for=\"8-10\"></label>"+
                                "<input type=\"text\" name=\"first\" placeholder="+request.getParameter("first")+">"+
                          "<label for=\"10-12\"></label>"+
                                "<input type=\"text\" name=\"second\" placeholder="+request.getParameter("second")+">"+ 
                          "<label for=\"12-14\"></label>"+
                               "<input type=\"text\" name=\"third\" placeholder="+request.getParameter("third")+">"+ 
                          "<label for=\"14-16\"></label>"+
                               "<input type=\"text\" name=\"forth\" placeholder="+request.getParameter("forth")+">"+
                          "<label for=\"Overtime\"></label>"+
                               "<input type=\"text\" name=\"Overtime\" placeholder="+request.getParameter("over")+">"+
                           "<br>"+
                     "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+em+">"+
                     "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                     "<input type=\"hidden\" id=\"day\" name=\"day\" value="+d+">"+
                     "<input type=\"hidden\" id=\"month\" name=\"month\" value="+month+">"+
                     "<input type=\"hidden\" id=\"direction\" name=\"direction\" value="+direction+">"+
                     "<input type=\"submit\" value=\"Submit\">"+ "</br>"+ "<form>");
            out.write("</div>");
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
            Logger.getLogger(Day_Alter.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Day_Alter.class.getName()).log(Level.SEVERE, null, ex);
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
