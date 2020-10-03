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
@WebServlet(name = "Staff", urlPatterns = {"/Staff"})
public class Staff extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static String table1;
    
    public void Decide(int id,int day,int month,String type) throws SQLException{
        ResultSet task;
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
        String first="",second="",third="",forth="",dayoff="dayoff";
        int overtime=0;
           
        Statement state = conn.createStatement();
        task = state.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+id+" AND DAY_NUM = "+day+" AND MONTH_NUM = "+month+" AND PERSON_TYPE = '" +"EMPLOYEE"+"' )");
        if (task.next() == false){
                      table1 = table1.concat( "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		            " <input type=\"submit\" value=\"Assign\" />"+"</form>"+"</td>"+"<td>"+
                         "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                    " <input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>"+"</td>"+"</tr>");
                      task.close();
                 }else{
                    first = task.getString("FIRST_2");
                    second = task.getString("SECOND_2");
                    third = task.getString("THIRD_2");
                    forth = task.getString("FORTH_2");
                    overtime = task.getInt("OVERTIME");
                    if (!(first.equalsIgnoreCase(dayoff))){
                            table1 = table1.concat( "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+
                                               "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                               "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                               "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                               "<input type=\"hidden\" id=\"type\" name=\"type\" value="+"EMPLOYEE"+">"+
                                               "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                               "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                               "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                               "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                               "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                               "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
                                               " <input type=\"submit\" value=\"UpDate\" />"+
                                               "</form>"+"</td>"+"<td>"+ "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                    " <input type=\"submit\" value=\"Dayoff\" />"+
                                     "</form>"+"</td>"+"</tr>");
                    }else{
                        table1 = table1.concat( "<td>"+ " "+"</td>"+"<td>"+ "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(day)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Staff"+">"+ 
		                        " <input type=\"submit\" value=\"Recall\" />"+
                                        "</form>"+
                                         "</td>"+                   
                                       "</tr>");
                    }
                     task.close();
                 }
         conn.close();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
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
            String css = Css_DWS();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet Staff</title>");      
            
            out.write(Search());
            out.write("<script>function GotoView(id){\n" +
                        "var d = id;\n" +
                        "var type = \"employee\";"+
                        "document.cookie = \"id\" + \"=\" + d;\n" +
                        "document.cookie = \"type\" + \"=\" + type;" +       
                        "\n" +
                        "window.location.replace(\"View_Profile\");" +
                "}</script>");
            
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
            String onoma = CurrentUser.CurrentName;
            ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME = '" + onoma + "'");
            int boss=-1;
             
            if (r.next()) {
                boss = r.getInt("B_ID");
            }
           
            String name, address, bachelor, master, Delete = "Delete", Edit ="Edit", teststring ="",first="",second="",third="",forth="";
            int id ,daysoff, overtimes, age, salary, children, working_years, em, i = 0, counter = 0,overtime=0;
            int pin[] = new int[10];
             
            ResultSet rs = stmt.executeQuery("SELECT * FROM APP.EMPLOYEE WHERE D_ID = " + boss + "");
            Calendar calendarios = Calendar.getInstance();
            int day = calendarios.get(Calendar.DATE);
            int month = calendarios.get(Calendar.MONTH) + 1;
            int year =calendarios.get(Calendar.YEAR);
           
            table1="<table border='3'  class=\"table table-hover table-blue\" >" +"<thead>"+
                          "<tr>"+
                               "<th>Name</th>"+
                               "<th>E_ID</th>" +
                               "<th>D_ID</th>" +
                               "<th>Salary</th>" +
                                "<th>Working_Years</th>" +
                                "<th>Daysoff</th>" +
                                "<th>Overtimes</th>" +
                                "<th>Bachelor</th>" +
                                "<th>Msc</th>" +
                                "<th>Address</th>" +
                               "<th>Age</th>" +
                                "<th>Children</th>" +
                                "<th>Delete</th>"+
                               "<th>Edit</th>"+
                               "<th>Assign/Update</th>"+
                               "<th>Dayoff/Recall</th>"+
                          "</tr>";
            
            while (rs.next()) {
                em = rs.getInt("E_ID");
                name = rs.getString("NAME");
                address = rs.getString("ADDRESS");
                bachelor = rs.getString("BACHELOR");
                master = rs.getString("MSC");
                id = rs.getInt("D_ID");
                daysoff = rs.getInt("DAYSOFF");
                overtimes = rs.getInt("OVERTIMES");
                age = rs.getInt("AGE");
                salary = rs.getInt("SALARY");
                children = rs.getInt("CHILDREN");
                working_years = rs.getInt("WORKING_YEARS");
                 
                table1= table1.concat("<tbody id=\"myTable\">"+   "<tr>"+
                        "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+em+")   \" border = \"0\">"+String.valueOf(name)+"</td>"+
                        "<td> "+String.valueOf(em)+"</td>"+
                        "<td>"+String.valueOf(id)+"</td>"+
                        "<td>"+String.valueOf(salary)+"</td>"+
                        "<td>"+String.valueOf(working_years)+"</td>"+
                        "<td>"+String.valueOf(daysoff)+"</td>"+
                        "<td>"+String.valueOf(overtimes)+"</td>"+
                        "<td>"+String.valueOf(bachelor)+"</td>"+
                        "<td>"+String.valueOf(master)+"</td>"+
                        "<td>"+String.valueOf(address)+"</td>"+
                        "<td>"+String.valueOf(age)+"</td>"+
                        "<td>"+String.valueOf(children)+"</td>"+
                        "<td>"+"<form action=\"Delete_Employee\" method=\"POST\">"+ 
                        "<input type=\"hidden\" id=\"name\" name=\"name\" value="+String.valueOf(em)+">"+
                        " <input type=\"submit\" value=\"Delete\" />"+
                        "</form>"+"</td>"+
                            "<td>"+"<form action=\"Edit\" method=\"POST\">"+
                            "<input type=\"hidden\" id=\"name\" name=\"name\" value="+String.valueOf(em)+">"+
                        "<input type=\"submit\" value=\"Edit\" />"+
                        "</form>"+"</td>");

                Decide(em,day,month,"EMPLOYEE");
                i++;
            }
            table1=table1.concat( "<thead>"+   "<tbody>"+"</table>");
            out.write(Html2());
            out.println("<br>");
            out.println("<p>These are your employees</p>");   
            out.write(
            "  <input class=\"form-control\" id=\"myInput\" type=\"text\" placeholder=\"Search..\">\n" +
            "  <br>");
            out.write(table1);
            out.println("</div>");
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
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Staff.class.getName()).log(Level.SEVERE, null, ex);
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
