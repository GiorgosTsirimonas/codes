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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "View_Profile", urlPatterns = {"/View_Profile"})
public class View_Profile extends HttpServlet {

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
            String css = Css();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet View_Profile</title>");            
            out.println("</head>");
            out.println("<body>");
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            
            out.println(Boss_Nav());
            String type = "", name = "", id = "", onoma="", address="", teststring = "", dayoff="dayoff", first="", second="", third="", forth="";
            int ilikia=-1,paidia=-1,salary=-1,years=-1,did=-1,repo=-1,overtimes=-1,e_id=-1,overtime=0;
            Cookie ck[]=request.getCookies();  
            for(int i=0;i<ck.length;i++){ 
               if (ck[i].getName().equals("id")) {
                   id = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("type")) {
                   type = ck[i].getValue();
                   ck[i].setMaxAge(0);
                } 
            }  
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR);
            if (type.equals("employee")){
                ResultSet rs = stmt.executeQuery("SELECT *  FROM APP.EMPLOYEE WHERE E_ID = " + Integer.parseInt(id) + "");
                String Bachelor="",master="";
                if (rs.next()) {
                    name = rs.getString("NAME");
                    ilikia = rs.getInt("AGE");
                    paidia = rs.getInt("CHILDREN");
                    address = rs.getString("ADDRESS");
                    salary = rs.getInt("SALARY");
                    years = rs.getInt("WORKING_YEARS");
                    did = rs.getInt("D_ID");
                    repo = rs.getInt("DAYSOFF");
                    Bachelor = rs.getString("BACHELOR");
                    master = rs.getString("MSC");
                    overtimes = rs.getInt("OVERTIMES");
                }
                rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+Integer.parseInt(id)+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '" +"EMPLOYEE"+"' )");
                teststring ="<table border='3' id=\"lol\"  class=\"table table-hover table-blue\" >" +
                          "<thead>" +
                          "<tr>"+
                               "<td>Name</td>"+"<td>"+name+"</td>"+
                           "</tr>"+
                          
                            "<tr>"+
                               "<td>Age</td>"+"<td>"+ilikia+"</td>"+
                    "</tr>"+
                   "<tr>"+
                               "<td>Department</td>" +"<td>"+did+
                    "</tr>"+
                   "<tr>"+
                               "<td>ID</td>" +"<td>"+id+
                    "</tr>"+
                    "<tr>"+
                               "<td>Salary</td>" +"<td>"+salary+"</td>"+
                    "</tr>"+
                    "<tr>"+           
                    "<td>Working_Years</td>" +"<td>"+years+"</td>"+
                    "</tr>"+
                    "<tr>"+
                    "<td>Daysoff</td>" +"<td>"+repo+"</td>"+
                    "</tr>"+
                    "<tr>"+
                    "<td>Overtimes</td>" +"<td>"+overtimes+"</td>"+
                    "</tr>"+
                    "<tr>"+
                    "<td>Children</td>" +"<td>"+paidia+"</td>"+"</tr>"+
                    "<tr>"+
                    "<td>Bachelor</td>" +"<td>"+Bachelor+"</td>"+"</tr>"+
                     "<tr>"+
                    "<td>Master</td>" +"<td>"+master+"</td>"+"</tr>";//+"<thead>"+"<tbody>"+"</table>";
            
                if (rs.next() == false){
                    teststring = teststring.concat(
                            "<tr>"+
                                    "<td>Assign</td>"+
                                    "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
                                        " <input type=\"submit\" value=\"Assign\" />"+"</form>"+
                                     "</td>"+
                            "</tr>"+
                            "<tr>"+
                                    "<td>Dayoff</td>"+
                                    "<td>"+"<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                        "<input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+
                                    "</td>"+      
                            "</tr>"+              
                            "<tr>"+
                                    "<td>Edit</td>"+
                                    "<td>"+
                                        "<form action=\"Edit\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Employee\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+
                            "</thead>"
                            +"</tbody>"+"</table>");
                }else{
                    
                    first = rs.getString("FIRST_2");
                    second = rs.getString("SECOND_2");
                    third = rs.getString("THIRD_2");
                    forth = rs.getString("FORTH_2");
                    overtime = rs.getInt("OVERTIME");
                    if (!(first.equalsIgnoreCase(dayoff))){
                         teststring = teststring.concat(
                            "<tr>"+
                                    "<td>Update</td>"+
                                    "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+          
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+
                                        "<input type=\"submit\" value=\"UpDate\" />"+
                                        "</form>"+
                                    "</td>"+ 
                            "</tr>"+ 
                            "<tr>"+
                                    "<td>Dayoff</td>"+
                                    "<td>"+"<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                        "<input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+
                                    "</td>"+      
                            "</tr>"+                       
                            "<tr>"+
                                    "<td>Edit</td>"+
                                    "<td>"+
                                        "<form action=\"Edit\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Employee\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+                   
                                                
                            "</thead>"
                            +"</tbody>"+"</table>");
                    }else{
                        teststring = teststring.concat( "<td>"+ "Recall"+"</td>"+"<td>"+ "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                        " <input type=\"submit\" value=\"Recall\" />"+
                                        "</form>"+
                                         "</td>"+  
                                         "<tr>"+"<td>Edit</td>"+
                                        "<td>"+
                                        "<form action=\"Edit\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Employee\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+          
                                       "</tr>"+"<thead>"+"<tbody>"+"</table>");
                    } 
                }
            } /*else if (type.equals("boss")){
            } */else{
                ResultSet rs = stmt.executeQuery("SELECT * FROM APP.TECHNICIAN WHERE T_ID = " + id + ""); 
                String spe="",spe2="";
                if (rs.next()) {
                   name = rs.getString("NAME");
                    e_id = rs.getInt("T_ID");
                    ilikia = rs.getInt("AGE");
                    paidia = rs.getInt("CHILDREN");
                    address = rs.getString("ADDRESS");
                    salary = rs.getInt("SALARY");
                    years = rs.getInt("WORKING_YEARS");
                    did = rs.getInt("D_ID");
                    repo = rs.getInt("DAYSOFF");
                    spe = rs.getString("SPECIALITY_1");
                    spe2 = rs.getString("SPECIALITY_2");
                    overtimes = rs.getInt("OVERTIMES");
                }
            
                teststring ="<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
                          "<thead>" + 
                          "<tr>"+
                               "<td>Name</td>"+"<td>"+name+"</td>"+
                           "</tr>"+
                          
                            "<tr>"+
                               "<td>Age</td>"+"<td>"+ilikia+"</td>"+
                            "</tr>"+
                           "<tr>"+
                               "<td>Department</td>" +"<td>"+did+
                            "</tr>"+
                           "<tr>"+
                               "<td>ID</td>" +"<td>"+e_id+
                            "</tr>"+
                            "<tr>"+
                               "<td>Salary</td>" +"<td>"+salary+"</td>"+
                            "</tr>"+
                            "<tr>"+           
                            "<td>Working_Years</td>" +"<td>"+years+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Daysoff</td>" +"<td>"+repo+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Overtimes</td>" +"<td>"+overtimes+"</td>"+
                            "</tr>"+
                            "<tr>"+
                            "<td>Children</td>" +"<td>"+paidia+"</td>"+"</tr>"+
                            "<tr>"+
                            "<td>Speciality_1</td>" +"<td>"+spe+"</td>"+"</tr>"+
                             "<tr>"+
                            "<td>Speciality_2</td>" +"<td>"+spe2+"</td>"+"</tr>";
             
                rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+Integer.parseInt(id)+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '" +"TECHNICIAN"+"' )");

                if (rs.next() == false){
                    teststring = teststring.concat(
                            "<tr>"+
                                    "<td>Assign</td>"+
                                    "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"TECHNICIAN"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff_Techn"+">"+ 
                                        " <input type=\"submit\" value=\"Assign\" />"+"</form>"+
                                     "</td>"+
                            "</tr>"+
                            "<tr>"+
                                    "<td>Dayoff</td>"+
                                    "<td>"+"<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"TECHNICIAN"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff_Techn"+">"+ 
		                        "<input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+
                                    "</td>"+      
                            "</tr>"+              
                            "<tr>"+
                                    "<td>Edit</td>"+
                                    "<td>"+
                                        "<form action=\"Edit_Techn\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Technician\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+
                            "</thead>"
                            +"</tbody>"+"</table>");
                  
                }else{
                
                    first = rs.getString("FIRST_2");
                    second = rs.getString("SECOND_2");
                    third = rs.getString("THIRD_2");
                    forth = rs.getString("FORTH_2");
                    overtime = rs.getInt("OVERTIME");
                    
                    if (!(first.equalsIgnoreCase(dayoff))){
                        teststring = teststring.concat(
                            "<tr>"+
                                    "<td>Update</td>"+
                                    "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+          
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(e_id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"TECHNICIAN"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff_Techn"+">"+
                                        "<input type=\"submit\" value=\"UpDate\" />"+
                                        "</form>"+
                                    "</td>"+ 
                            "</tr>"+ 
                            "<tr>"+
                                    "<td>Dayoff</td>"+
                                    "<td>"+"<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+overtime+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"TECHNICIAN"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff_Techn"+">"+ 
		                        "<input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+
                                    "</td>"+      
                            "</tr>"+                       
                            "<tr>"+
                                    "<td>Edit</td>"+
                                    "<td>"+
                                        "<form action=\"Edit_Techn\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Technician\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+                   
                                                
                            "</thead>"
                            +"</tbody>"+"</table>");
                         
                     }else{
                        teststring = teststring.concat( "<td>"+ "Recall"+"</td>"+"<td>"+ "<form action=\"Recall\" method=\"POST\">"+
                                        
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"TECHNICIAN"+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff_Techn"+">"+ 
		                        " <input type=\"submit\" value=\"Recall\" />"+
                                        "</form>"+
                                         "</td>"+  
                                         "<tr>"+"<td>Edit</td>"+
                                        "<td>"+
                                        "<form action=\"Edit_Techn\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                        "<input type=\"submit\" value=\"Edit\" />"+
                                        "</form>"+
                                    "</td>"+
                            "</tr>"+
                            "<tr>"+
                                   "<td>Delete</td>"+
                                   "<td>"+"<form action=\"Delete_Technician\" method=\"POST\">"+ 
                                   "<input type=\"hidden\" id=\"name\" name=\"name\" value="+id+">"+
                                   "<input type=\"submit\" value=\"Delete\" />"+
                            "</tr>"+          
                                       "</tr>"+"<thead>"+"<tbody>"+"</table>");
                    }
            }      
        }
            
            out.write(Html2());
            out.println("<br>");
            out.println("<p>This is the user you have been looking for</p><br>");
            out.write(teststring); 
            out.println("</div>");
            out.write(Html3());
            stmt.close();
            conn.close();
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
            Logger.getLogger(View_Profile.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(View_Profile.class.getName()).log(Level.SEVERE, null, ex);
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
