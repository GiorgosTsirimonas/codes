/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Functions.Employee_Nav;
import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
import static com.mycompany.mavenproject2.Functions.Techn_Nav;
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
@WebServlet(name = "View_Profile_View", urlPatterns = {"/View_Profile_View"})
public class View_Profile_View extends HttpServlet {

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
            out.println("<title>Servlet View_Profile_View</title>");            
            out.println("</head>");
            out.println("<body>");
           
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            if (CurrentUser.CurrentName.equals("")){
               response.sendRedirect("login.html");
            }
            
            // CurrentUser.Ses=1;
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }else{
             //  out.println("to name einai "+CurrentUser.CurrentName);
            }
           
            if (CurrentUser.Ses == -1){
               response.sendRedirect("login.html");
            }else{
             //  out.println("to sses einai "+CurrentUser.Ses);
            }
            
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            String type = "",name = CurrentUser.CurrentName, id = "", onoma="", address="", type2 ="", spe = "", dayoff="dayoff", spe2 = "", table="", nav ="";
            int ilikia=-1, paidia=-1, salary=-1, years=-1, did=-1, repo=-1, overtimes=-1, e_id=-1, overtime=0, not = 0;
            Cookie ck[]=request.getCookies();  
            for(int i=0;i<ck.length;i++){ 
                
               if (ck[i].getName().equals("id")) {
                   id = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("type")) {
                   type = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("type2")) {
                   type2 = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }
            }  
        
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR);
            ResultSet t = stmt.executeQuery("SELECT COUNT(SAW) FROM MAILS WHERE (SAW = 1 AND RECEIVER_NAME = '"+CurrentUser.CurrentName+"')");
            t.next();
            not = t.getInt(1);
            if (type2.equals("technician")){ //if we are seeing the user as a technician
                out.println(Techn_Nav());
                
                if (type.equals("technician")){//aif we are seeing another technician technician
                    ResultSet rs = stmt.executeQuery("SELECT *  FROM APP.TECHNICIAN WHERE T_ID = " + id + "");
                 
                    if (rs.next()) {
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
                       name = rs.getString("NAME");
                       overtimes = rs.getInt("OVERTIMES");
                    }
                    
                    table = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
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
                            "<td>Speciality_2</td>" +"<td>"+spe2+"</td>"+"</tr>"+"<thead>"+"<tbody>"+"</table>";
                    
                }else{
                    ResultSet rs = stmt.executeQuery("SELECT *  FROM APP.BOSS WHERE B_ID = " + id + "");
                    
                    if (rs.next()) {
                       ilikia = rs.getInt("AGE");
                       paidia = rs.getInt("CHILDREN");
                       address = rs.getString("ADDRESS");
                       salary = rs.getInt("SALARY");
                       years = rs.getInt("WORKING_YEARS");
                       did = rs.getInt("B_ID");
                       repo = rs.getInt("DAYSOFF");
                       name = rs.getString("NAME");
                       overtimes = rs.getInt("OVERTIMES");
                    }
                    
                    table = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
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
                            "<td>Children</td>" +"<td>"+paidia+"</td>"+"</tr>"+"<thead>"+"<tbody>"+"</table>";
                }
            }else{
                
                response.setHeader("Cache-Control","no-cache");
                response.setHeader("Cache-Control","no-store");
                response.setHeader("Pragma","no-cache");
                response.setDateHeader ("Expires", 0);

                out.println(Employee_Nav());
                String bachelor = "", master = "";
           
                if (type.equals("employee")){
                    ResultSet rs = stmt.executeQuery("SELECT *  FROM APP.EMPLOYEE WHERE E_ID = " + id + "");
                
                    if (rs.next()) {
                       e_id = rs.getInt("E_ID");
                       ilikia = rs.getInt("AGE");
                       paidia = rs.getInt("CHILDREN");
                       address = rs.getString("ADDRESS");
                       salary = rs.getInt("SALARY");
                       years = rs.getInt("WORKING_YEARS");
                       did = rs.getInt("D_ID");
                       repo = rs.getInt("DAYSOFF");
                       bachelor = rs.getString("BACHELOR");
                       master = rs.getString("MSC");
                       name = rs.getString("NAME");
                       overtimes = rs.getInt("OVERTIMES");
                    }
                    
                    table = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
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
                            "<td>Bachelor</td>" +"<td>"+bachelor+"</td>"+"</tr>"+
                             "<tr>"+
                            "<td>Master</td>" +"<td>"+master+"</td>"+"</tr>"+"<thead>"+"<tbody>"+"</table>";
                    
                }else{
                    ResultSet rs = stmt.executeQuery("SELECT *  FROM APP.BOSS WHERE B_ID = " + id + "");
                    if (rs.next()) {
                       ilikia = rs.getInt("AGE");
                       paidia = rs.getInt("CHILDREN");
                       address = rs.getString("ADDRESS");
                       salary = rs.getInt("SALARY");
                       years = rs.getInt("WORKING_YEARS");
                       did = rs.getInt("B_ID");
                       repo = rs.getInt("DAYSOFF");
                       name = rs.getString("NAME");
                       overtimes = rs.getInt("OVERTIMES");
                    }
                    
                    table = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
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
                            "<td>Children</td>" +"<td>"+paidia+"</td>"+"</tr>"+"<thead>"+"<tbody>"+"</table>";
                    
                } 
            }
            
            out.write(Html2());
            out.println("<br>");
            out.write(table);  
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
            Logger.getLogger(View_Profile_View.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(View_Profile_View.class.getName()).log(Level.SEVERE, null, ex);
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
