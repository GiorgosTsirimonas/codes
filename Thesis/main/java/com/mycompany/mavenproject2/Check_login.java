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
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Check_login", urlPatterns = {"/Check_login"})
public class Check_login extends HttpServlet {

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
            throws ServletException, IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
             try {
               Class.forName("org.apache.derby.jdbc.ClientDriver");
               Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
                Statement stmt = conn.createStatement();
                int check =0;
                String name = request.getParameter("name");
                String password = request.getParameter("pass");
                String sql = "SELECT NAME,PASSWORD FROM APP.BOSS";
                ResultSet rs = stmt.executeQuery(sql);
                String n1,n2;
                response.setContentType("text/html");
                
                while (rs.next()) {
                    n1 = rs.getString("NAME");
                    n2 = rs.getString("PASSWORD");
                    if ( (n1.equals(name)) && (password.equals(n2))){
                        CurrentUser.CurrentName = n1;
                        CurrentUser.Typos="BOSS";
                        CurrentUser.Ses=1;
                        HttpSession session = request.getSession();
                        session.setAttribute("UserName",CurrentUser.CurrentName );
                        response.sendRedirect("Boss_Home");
                        check = 1;
                        break;
                    }
                }
            
                if (check == 0){
                    String sq2 = "SELECT NAME,PASSWORD FROM APP.EMPLOYEE";
                    ResultSet r = stmt.executeQuery(sq2);
                    while (r.next()){
                         n1 = r.getString("NAME");
                         n2 = r.getString("PASSWORD");
                         if ( (n1.equals(name)) && (password.equals(n2))){
                            CurrentUser.CurrentName = n1;
                            response.sendRedirect("Employee_Home");
                            check = 1;
                            break;
                         }
                    }
                }
            
                if (check == 0){
                    String sq3 = "SELECT NAME,PASSWORD FROM APP.TECHNICIAN";
                    ResultSet rt = stmt.executeQuery(sq3);
                    while (rt.next()){
                         n1 = rt.getString("NAME");
                         n2 = rt.getString("PASSWORD");
                         if ( (n1.equals(name)) && (password.equals(n2))){
                            CurrentUser.CurrentName = n1;
                            response.sendRedirect("Technician_Home");
                            check = 1;
                            break;
                         }
                    }
                }
            out.println("bad login...");
            conn.close();
            stmt.close();

        } catch (Exception e) {
            System.out.println(e);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
