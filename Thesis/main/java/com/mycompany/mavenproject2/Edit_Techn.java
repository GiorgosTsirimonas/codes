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
@WebServlet(name = "Edit_Techn", urlPatterns = {"/Edit_Techn"})
public class Edit_Techn extends HttpServlet {

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
           
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            CurrentUser.Ses=1;
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }
            
            out.println(Html());
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            out.write("<style>");
            out.write(Css_Edit2());
            out.write("</style>");
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            int e_id = Integer.parseInt(request.getParameter("name"));
            String name="",address="",spec1="",spec2="",password="";
            int id=0,daysoff=0,overtimes=0,age=0,salary=0,children=0,working_years=0;
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM APP.TECHNICIAN WHERE T_ID  = "+e_id+"");
            if (rs.next()){
                name = rs.getString("NAME");
                password=rs.getString("PASSWORD");
                address = rs.getString("ADDRESS");
                spec1 = rs.getString("SPECIALITY_1");
                age = rs.getInt("AGE");
                spec2 = rs.getString("SPECIALITY_2");
                id = rs.getInt("D_ID");
                daysoff = rs.getInt("DAYSOFF");
                overtimes = rs.getInt("OVERTIMES");
                age = rs.getInt("AGE");
                salary = rs.getInt("SALARY");
                children = rs.getInt("CHILDREN");
                working_years = rs.getInt("WORKING_YEARS");
            }
            
            out.println("<p>Please update the sections you wish to change</p>");
            out.write("<form action=\"Edit_Techn02\" method=\"POST\">"+
                    "<label for=\"Name\"></label>"+
                     "<input type=\"text\" name=\"Name\" placeholder="+name+">"+
                     "<label for=\"Password\"></label>"+
                     "<input type=\"text\" name=\"Password\" placeholder="+password+">"+
                     "<label for=\"Age\"></label>"+
                     "<input type=\"number\" name=\"Age\" placeholder="+age+">"+
                      "<label for=\"D_ID\"></label>"+
                     "<input type=\"number\" name=\"D_ID\" placeholder="+id+">"+
                     "<label for=\"Salary\"></label>"+
                     "<input type=\"number\" name=\"Salary\" placeholder="+salary+">"+         
                      "<label for=\"Working_years\"></label>"+
                     "<input type=\"number\" name=\"Working_years\" placeholder="+working_years+">"+         
                      "<label for=\"Daysoff\"></label>"+
                     "<input type=\"number\" name=\"Daysoff\" placeholder="+daysoff+">"+               
                      "<label for=\"Overtimes\"></label>"+
                     "<input type=\"number\" name=\"Overtimes\" placeholder="+overtimes+">"+ 
                     "<label for=\"Address\"></label>"+
                     "<input type=\"text\" name=\"Address\" placeholder="+address+">"+ 
                     "<label for=\"Speciality_1\"></label>"+
                     "<input type=\"text\" name=\"Speciality_1\" placeholder="+spec1+">"+
                     "<label for=\"Speciality_2\"></label>"+
                     "<input type=\"text\" name=\"Speciality_2\" placeholder="+spec2+">"+
                    "<label for=\"Children\"></label>"+
                     "<input type=\"number\" name=\"Children\" placeholder="+children+">"+ 
                    "<br>"+
                     "<td>"+"<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+e_id+">"+
                     "<input type=\"submit\" value=\"Submit\">"+
		"</form>");    
            out.println("</nav>");
            out.println("<body>");  
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
            Logger.getLogger(Edit_Techn.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Edit_Techn.class.getName()).log(Level.SEVERE, null, ex);
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
