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
import static com.mycompany.mavenproject2.css.Css_DWS;
import static com.mycompany.mavenproject2.js.Search;
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
@WebServlet(name = "Daily_Employees", urlPatterns = {"/Daily_Employees"})
public class Daily_Employees extends HttpServlet {

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
            String css = Css_DWS();
            out.write(css);
            out.write("</style>");
            
            out.write(Search());  
            out.write("<script>function GotoView(id){\n" +
                "	var d = id;\n" +
                        "var type = \"employee\";"+
                "	document.cookie = \"id\" + \"=\" + d;\n" +
                 "	document.cookie = \"type\" + \"=\" + type;\n" +       
                "\n" +
                "	window.location.replace(\"View_Profile\");\n" +
                "	\n" +
            "}</script>");
            
            out.println("<title>Servlet Daily_Employees</title>");            
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
            String dailyemp="Daily_Employees";
           
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            
            out.println(Boss_Nav());
            ResultSet rs,r;
            rs = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            int id=0;
            if (rs.next()){
                id = rs.getInt("B_ID");
            }
            Calendar calendarios = Calendar.getInstance(); 
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR), temp=0, em_id,overtime,i=0,j=0;
            rs = stmt.executeQuery("SELECT NAME,E_ID FROM APP.EMPLOYEE WHERE D_ID  = "+id+"");
           
            String teststring="<table border='3'  class=\"table table-hover table-blue\" >" +"<thead>" + 
                          "<tr>"+
                               "<th>Name</th>"+
                               "<th>E_ID</th>"+
                               "<th>8:00-10:00</th>"+
                               "<th>10:00-12:00</th>"+
                               "<th>12:00-14:00</th>" +
                               "<th>14:00-16:00</th>" +
                               "<th>Overtime</th>" +
                               "<th>Add/Update</th>" +
                               "<th>Dayoff/Recall</th>" +
                            "</tr>";
            String name="",type="EMPLOYEE",first="",second="",third="",forth="",dayoff ="dayoff";
            String names[]=new String[50];
            int ari8moi[]=new int[50];
            while (rs.next()){ 
                ari8moi[i]=rs.getInt("E_ID");
                names[i] = rs.getString("NAME");
                i++;
            }
            while (j<i){
                r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '" +type+"' )");
                if (r.next() == false){
                     teststring = teststring.concat("<tbody id=\"myTable\">"+"<tr>"+
                        "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                        "<td>"+ari8moi[j]+"</td>"+
                        "<td>"+""+"</td>"+
                        "<td>"+""+"</td>"+
                        "<td>"+""+"</td>" +
                        "<td>"+""+"</td>" +
                        "<td>"+""+"</td>" +
                        "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                             "<input type=\"submit\" value=\"Add\" />"+
	                 "</form>"+"</td>"+"<td>"+
                         "<form action=\"Dayoff\" method=\"POST\">"+
                            "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                            "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                            "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                            "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                            "<input type=\"hidden\" id=\"over\" name=\"over\" value="+dayoff+">"+
                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                            "<input type=\"submit\" value=\"Dayoff\" />"+
                        "</form>"+"</td>"+
                   "</tr>");
                }else{
                    first = r.getString("FIRST_2");
                    second = r.getString("SECOND_2");
                    third = r.getString("THIRD_2");
                    forth = r.getString("FORTH_2");
                    overtime = r.getInt("OVERTIME");
                    teststring = teststring.concat("<tbody id=\"myTable\">"+"<tr>"+
                        "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                        "<td>"+ari8moi[j]+"</td>"+
                        "<td>"+first+"</td>"+
                        "<td>"+second+"</td>"+
                        "<td>"+third+"</td>" +
                        "<td>"+forth+"</td>" +
                        "<td>"+overtime+"</td>");
                        if (!(first.equalsIgnoreCase(dayoff))){
                            if (first != null){
                                teststring = teststring.concat( "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+
                                    "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                    "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                    "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                    "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                    "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                    "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                    "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                    "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                                    " <input type=\"submit\" value=\"UpDate\" />"+
                                "</form>"+"</td>");
                                        
                                teststring = teststring.concat(  "</form>"+"</td>"+"<td>"+
                                    "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                                        " <input type=\"submit\" value=\"Dayoff\" />"+
                                    "</form>"+"</td>"+
                                "</tr>");
                                     }else{
                                        teststring = teststring.concat( "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                                            " <input type=\"submit\" value=\"Add\" />"+
                                        "</form>"+"</td>");
                                        
                                        teststring = teststring.concat( "</form>"+"</td>"+"<td>"+
                                        "<form action=\"Dayoff\" method=\"POST\">"+
                                            "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                                            " <input type=\"submit\" value=\"Dayoff\" />"+
                                         "</form>"+"</td>"+
                                        "</tr>");
                                      }
                                }else{
                                    teststring = teststring.concat("<td>"+" "+"</td>"+"<td>"+
                                     "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+dailyemp+">"+ 
                                        "<input type=\"submit\" value=\"Recall\" />"+
                                     "</form>"+
                                    "</td>"+                 
                                    "</tr>");
                                }
               }
               j++;
            }
            
            out.write(Html2());
            teststring = teststring.concat("<thead>"+ "<tbody>"+"</table>");
            out.println("<br>");
            out.println("<p>This is employees today programm<p>");
            out.write(
            "  <input class=\"form-control\" id=\"myInput\" type=\"text\" placeholder=\"Search..\">\n" +
            "  <br>");
            out.write(teststring);
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
            Logger.getLogger(Daily_Employees.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Daily_Employees.class.getName()).log(Level.SEVERE, null, ex);
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
