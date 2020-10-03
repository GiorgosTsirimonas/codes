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
import static com.mycompany.mavenproject2.css.Css_Calendar;
import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Boss_Weekly", urlPatterns = {"/Boss_Weekly"})
public class Boss_Weekly extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
      
public  int dayofweek(int d, int m, int y) 
{ 
    int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 }; 
    y -= (m < 3) ? 1 : 0; 
    return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7; 
} 
    
        
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
            String css = Css_Calendar();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet Boss_Weekly</title>");            
            out.println("</head>");
            out.println("<body>");
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            CurrentUser.Ses=1;
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }
            
            String weekly="Boss_Weekly";
           
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
          
            out.println(Boss_Nav());
            out.println("<br>");
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR), e_id = 0,temp,overtime=-1;
            String boss = "BOSS", first=null, second=null, third=null, forth=null, teststring, dayoff ="dayoff";
            String days[]=new String[]{ "Su","Mo","Tu","We","Th","Fr","Sa" };
           
            int akr = dayofweek(day,month,year); //finding the day1s name, 0 if its sunday
            int i=day,j=0;
            while (akr != 0){ //calculate how far is monday
                akr--;
                i--;
            }
            i++;
            out.write(Html2());
            teststring = "<table border='3' id=\"lol\"  class=\"table table-hover table-blue\" >" +"<thead>" +
                          "<tr>"+
                               "<th>Day and Date</th>"+
                               "<th>8:00-10:00</th>"+
                               "<th>10:00-12:00</th>"+
                               "<th>12:00-14:00</th>"+
                               "<th>14:00-16:00</th>"+
                               "<th>Overtime</th>"+
                               "<th>Edit/Add</th>"+
                               "<th>Dayoff/Recall</th>"+
                           "</tr>";
            ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            ResultSet rs;
            
            if (r.next()){
                 e_id = r.getInt("B_ID");
            }

            int dayn=0;
            while (j<5){
                first = " ";
                second = "";
                third = "";
                forth = "";
                overtime = -1;
                rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+e_id+" AND DAY_NUM = "+i+" AND MONTH_NUM = "+month+" AND PERSON_TYPE= '"+boss+"')");
                if (rs.next()){
                     first = rs.getString("FIRST_2");
                     second = rs.getString("SECOND_2");
                     third = rs.getString("THIRD_2");
                     forth = rs.getString("FORTH_2");
                     overtime = rs.getInt("OVERTIME");
                 }
                if ((first != null || first != " ") && overtime != -1){
                    teststring = teststring.concat("<tr>"+
                        "<td>"+days[j+1]+" "+i+"/"+month+"</td>"+
                        "<td>"+first+"</td>"+
                        "<td>"+second+"</td>"+
                        "<td>"+third+"</td>"+
                        "<td>"+forth+"</td>"+
                        "<td>"+overtime+"</td>");
                        if (!(first.equalsIgnoreCase(dayoff))){
                            teststring = teststring.concat( "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+
                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+boss+">"+
                                "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weekly+">"+ 
                                "<input type=\"submit\" value=\"UpDate\" />"+
                            "</form>"+"</td>");
                                  
                            teststring = teststring.concat( 
                                "<td>"+
                                     "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weekly+">"+ 
		                      "<input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>"+
                                      "</td>"+   
                             "</tr>");
                            }else{
                                teststring = teststring.concat( "<td>"+" "+"</td>"+
                                        "<td>"+
                                     "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weekly+">"+ 
		                        "<input type=\"submit\" value=\"Recall\" />"+
                                     "</form>"+
                                     "</td>"+                   
                                "</tr>");     
                            }
                        }else{
                        teststring = teststring.concat("<tr>"+
                            "<td>"+days[j+1]+" "+i+"/"+month+"</td>"+
                            "<td>"+""+"</td>"+
                            "<td>"+""+"</td>"+
                            "<td>"+""+"</td>"+
                            "<td>"+""+"</td>"+
                            "<td>"+""+"</td>");
                            
                            teststring = teststring.concat( "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+boss+">"+
                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weekly+">"+ 
                                " <input type=\"submit\" value=\"Add\" />"+
	                    "</form>"+"</td>");
                            teststring = teststring.concat(
                                   "<td>"+
                                     "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weekly+">"+ 
		                       "<input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>"+
                                     "</td>"+   
                            "</tr>");
                        }
                i++;
                j++;
          }
          teststring=teststring.concat("<thead>"+ "<tbody>"+"</table>");
          out.println("<p>This is your weekly programm<p>");
          out.println(teststring);
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
        Logger.getLogger(Boss_Weekly.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(Boss_Weekly.class.getName()).log(Level.SEVERE, null, ex);
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
