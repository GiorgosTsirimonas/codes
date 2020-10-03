/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.css.Css_Edit2;
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
@WebServlet(name = "Edit_Boss", urlPatterns = {"/Edit_Boss"})
public class Edit_Boss extends HttpServlet {

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
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            
            out.write("<style>");
            out.write(Css_Edit2());
            out.write("</style>");
            out.println("<title>Servlet Edit_Boss</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String main = "Boss_Home";
            String profile = "Boss_Profile";
            String apps = "Boss_Applications";
            String calendar = "Boss_Calendar";
            String logout= "Logout.jsp";
            String tasks = "Task";
            String eom = "EOM";
            String staff = "Staff";
            String register = "register.html";
            String techn ="Staff_Techn";

            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            String onoma = CurrentUser.CurrentName, address="",password="";
            ResultSet r = stmt.executeQuery("SELECT AGE,SALARY,DAYSOFF,OVERTIMES,CHILDREN,WORKING_YEARS,ADDRESS,PASSWORD FROM APP.BOSS WHERE NAME = '" + onoma + "'");
            int daysoff=-1,age=-1,salary=-1,overtimes=-1,children=-1,years=-1;
            
            if (r.next()) {
                daysoff = r.getInt("DAYSOFF");
                age = r.getInt("AGE");
                salary = r.getInt("SALARY");
                overtimes = r.getInt("OVERTIMES");
                children = r.getInt("CHILDREN");
                years = r.getInt("WORKING_YEARS");
                address = r.getString("ADDRESS");
                password = r.getString("PASSWORD");
            }
      
            out.println("<br>");        
            out.println("<p>Please update the sections you wish to change<p>");  
            out.write("<form action=\"Edit_Boss2\" method=\"POST\">"+
                    "<label for=\"Password\"></label>"+
                     "<input type=\"text\" name=\"Password\" placeholder="+password+">"+
                       "<label for=\"Age\"></label>"+
                     "<input type=\"number\" name=\"Age\" placeholder="+age+">"+
                     "<label for=\"Salary\"></label>"+
                     "<input type=\"number\" name=\"Salary\" placeholder="+salary+">"+         
                      "<label for=\"Working_years\"></label>"+
                     "<input type=\"number\" name=\"Working_years\" placeholder="+years+">"+         
                      "<label for=\"Daysoff\"></label>"+
                     "<input type=\"number\" name=\"Daysoff\" placeholder="+daysoff+">"+               
                      "<label for=\"Overtimes\"></label>"+
                     "<input type=\"number\" name=\"Overtimes\" placeholder="+overtimes+">"+ 
                     "<label for=\"Address\"></label>"+
                     "<input type=\"text\" name=\"Address\" placeholder="+address+">"+ 
                    "<label for=\"Children\"></label>"+
                     "<input type=\"number\" name=\"Children\" placeholder="+children+">"+ 
                    "<br>"+
                     "<input type=\"submit\" value=\"Submit\">"+
		"</form>");
            
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(Edit_Boss.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Edit_Boss.class.getName()).log(Level.SEVERE, null, ex);
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
