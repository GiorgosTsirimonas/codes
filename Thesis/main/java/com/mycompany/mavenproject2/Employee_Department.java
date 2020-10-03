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
import static com.mycompany.mavenproject2.css.Css_Calendar;
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
@WebServlet(name = "Employee_Department", urlPatterns = {"/Employee_Department"})
public class Employee_Department extends HttpServlet {

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
            String css = Css_Calendar();
            out.write(css);
            out.write("</style>");
            
            out.write("<script>function GotoView(id,choice){\n" +
                    "var d = id;\n" +
                    "var type;"+
                    "var type2 = \"employee\";"
                    + "type = \"employee;\"; "
                    +
                    "document.cookie = \"id\" + \"=\" + d;\n" +
                    "document.cookie = \"type\" + \"=\" + type;\n" +    
                    "document.cookie = \"type2\" + \"=\" + type2;\n" +  
                    "\n" +
                    "window.location.replace(\"View_Profile_View\");\n" +
                    "\n" +
                    "}</script>");
           
            out.println("<title>Servlet Employee_Department</title>");            
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
       
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            out.println(Employee_Nav());
            String name = CurrentUser.CurrentName,temp="",teststring ="",bachelor="",master="";
            int d_id=0,age=-1,over=-1,counter=0,years=0;
            ResultSet rs = stmt.executeQuery("SELECT D_ID  FROM APP.EMPLOYEE WHERE NAME = '" + name + "'");
            if (rs.next()){
                d_id = rs.getInt("D_ID");
            }
            ResultSet r = stmt.executeQuery("SELECT NAME,AGE,OVERTIMES,WORKING_YEARS  FROM APP.BOSS WHERE B_ID = " + d_id + " ");
            if (r.next()){
                temp = r.getString("NAME");
                age = r.getInt("AGE");
                over = r.getInt("OVERTIMES");
                years = r.getInt("WORKING_YEARS");
            }
         
            teststring = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +
                "<thead>" + 
                "<tr>"+
                    "<th>Name</th>"+
                    "<th>Type</th>" +
                    "<th>Age</th>" +
                    "<th>Working_Years</th>" +
                    "<th>Overtimes</th>" +
                    "<th>Bachelor</th>"+
                    "<th>Msc</th>" +
                "</tr>";
         
            int e_id;
            rs = stmt.executeQuery("SELECT NAME,AGE,OVERTIMES,WORKING_YEARS,BACHELOR,MSC,E_ID  FROM APP.EMPLOYEE WHERE D_ID = " + d_id + "");
            while (rs.next()){
             if (counter == 0){
                 teststring = teststring.concat("<tr>"+
                         "<td id=\\\"id\\\" value =\"+temp+\" onClick =\"GotoView("+d_id+","+1+")   \" border = \"0\">"+String.valueOf(temp)+"</td>"+
                         "<td>"+"Boss"+"</td>"+
                         "<td>"+String.valueOf(age)+"</td>"+  
                         "<td>"+String.valueOf(years)+"</td>"+
                         "<td>"+String.valueOf(over)+"</td>"+
                         "<td>"+String.valueOf(bachelor)+"</td>"+
                         "<td>"+String.valueOf(master)+"</td>");
                 counter++;
             }
             
             e_id = rs.getInt("E_ID");
             temp = rs.getString("NAME");
             age = rs.getInt("AGE");
             over = rs.getInt("OVERTIMES");
             years = rs.getInt("WORKING_YEARS");
             master = rs.getString("MSC");
             bachelor = rs.getString("BACHELOR");
             
             teststring = teststring.concat("<tr>"+
                        "<td id=\\\"id\\\" value =\"+temp+\" onClick =\"GotoView("+e_id+","+2+")   \" border = \"0\">"+String.valueOf(temp)+"</td>"+
                         "<td>"+"Employee"+"</td>"+
                         "<td>"+String.valueOf(age)+"</td>"+  
                         "<td>"+String.valueOf(years)+"</td>"+
                         "<td>"+String.valueOf(over)+"</td>"+
                         "<td>"+String.valueOf(bachelor)+"</td>"+
                         "<td>"+String.valueOf(master)+"</td>"+"</tr>");
            }
            teststring = teststring.concat("<thead>"+"<tbody>"+"</table>");
            out.write(Html2());
            out.write("<br>");
            out.write("<p>This is your department</p>");
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
            Logger.getLogger(Employee_Department.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Employee_Department.class.getName()).log(Level.SEVERE, null, ex);
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
