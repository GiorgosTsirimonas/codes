/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Boss_Nav;
import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
import static com.mycompany.mavenproject2.css.Css;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Boss_Home", urlPatterns = {"/Boss_Home"})
public class Boss_Home extends HttpServlet {

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
         
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
      
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }

            String main = "Boss_Home";
            out.println(Html());
            out.println("<title>Servlet Boss_Home</title>");  
            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            out.write("<style>");
            String css = Css();
            out.write(css);
            out.write("</style>");
            out.println("</head>");
            out.println("<body>");
         
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
           
            out.println(Boss_Nav());
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE),  month = calendarios.get(Calendar.MONTH) + 1,  year =calendarios.get(Calendar.YEAR), e_id = 0, counter=0;
            String boss = "BOSS", first = "", second = "", third = "", forth = "", over = "";
             
            ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            
            if (r.next()){
                e_id = r.getInt("B_ID");
            }
             
            String type = "BOSS", dayoff = "dayoff";
            ResultSet rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+e_id+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '"+type+"')");
             
            out.println(Html2());
            out.println("<br>");
            if (rs.next() == false) {
                out.println("<p>There are not any assignments for today but you can add them</p>");
                out.write(
                       "<form action=\"Day_Single\" method=\"POST\">"+ 
                 "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                 "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                 "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                 "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                 "<input type=\"hidden\" id=\"direction\" name=\"direction\" value="+main+">"+
		" <input type=\"submit\" value=\"Assign\" />"+"</form>");
                
                out.write("<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+main+">"+ 
		                    " <input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>");
                    
            } else{
                 
                first = rs.getString("FIRST_2");
                second = rs.getString("SECOND_2");
                third = rs.getString("THIRD_2");
                forth = rs.getString("FORTH_2");
                over = rs.getString("OVERTIME");
                String teststring="";
                 
                if (!(first.equalsIgnoreCase(dayoff))){
                    out.println("<p>These are your tasks for today</p>");
                    teststring="<form action=\"Day_Alter\" method=\"POST\">";
                    teststring = teststring.concat("<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
                          "<thead>"+"<tr>"+
                               "<td>8:00-10:00</td>"+"<td>"+first+"</td>"+
                           "</tr>"+
                           "<tr>"+
                               "<td>10:00-12:00</td>"+"<td>"+second+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>12:00-14:00</td>"+"<td>"+third+"</td>"+
                            "</tr>"+
                           "<tr>"+
                               "<td>14:00-16:00</td>" +"<td>"+forth+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>Overtime</td>" +"<td>"+Integer.parseInt(over)+"</td>"+
                            "</tr>"+
                            "<tr>"+
                                 "<td>Update</td>"+      
                                 "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+          
                                    "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                    "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                    "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                    "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                    "<input type=\"hidden\" id=\"over\" name=\"over\" value="+over+">"+
                                    "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                    "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                    "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Home"+">"+
                                    "<input type=\"submit\" value=\"UpDate\" />"+
                                         "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                          "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                          "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                          "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                          "<input type=\"hidden\" id=\"over\" name=\"over\" value="+over+">"+
                                          "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                          "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                          "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                          "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                          "</form>"+
                                        "</td>"+ 
                      "</tr>"+            
                      "<tr>");
                          
                      teststring = teststring.concat("<td>Dayoff</td>"+"<td>"+
                                     "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+over+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+main+">"+ 
		                    " <input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>"+
                           "</td>"+          
                          "</tr>"+"<thead>"+   "<tbody>"+"</table>");
                    }else{
                        out.println("<p>You have a dayoff today</p>");
                        teststring = teststring.concat("<table border='3' id = \"lol\" class=\"table table-hover table-blue\"  >" +
                          "<thead>"+
                          "<tr>"+
                               "<td>8:00-10:00</td>"+"<td>"+first+"</td>"+
                           "</tr>"+
                           "<tr>"+
                               "<td>10:00-12:00</td>"+"<td>"+second+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>12:00-14:00</td>"+"<td>"+third+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>14:00-16:00</td>" +"<td>"+forth+"</td>"+
                            "</tr>"+
                            "<tr>"+
                               "<td>Overtime</td>" +"<td>"+Integer.parseInt(over)+"</td>"+
                        "</tr>"+
                         "</thead>"+"<tbody>"+
                        "<tr>");
                        teststring = teststring.concat("<td>Recall</td>"+"<td>"+
                                     "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+main+">"+ 
		                    " <input type=\"submit\" value=\"Recall\" />"+
                                     "</form>"+
                           "</td>"+             
                          "</tr>"+"<thead>"+"<tbody>"+"</table>");
                    }
                out.write(teststring);
            }
          out.println(Html3());
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
            Logger.getLogger(Boss_Home.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Boss_Home.class.getName()).log(Level.SEVERE, null, ex);
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
