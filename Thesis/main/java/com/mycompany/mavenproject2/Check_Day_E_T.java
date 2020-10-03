/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Check_Day.ReachAbleDate;
import static com.mycompany.mavenproject2.Check_Day.dayofweek;
import static com.mycompany.mavenproject2.Functions.Employee_Nav;
import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Html3;
import static com.mycompany.mavenproject2.Functions.Techn_Nav;
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
@WebServlet(name = "Check_Day_E_T", urlPatterns = {"/Check_Day_E_T"})
public class Check_Day_E_T extends HttpServlet {

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
"	var d = id;\n" +
                   "var type;"+
                   "var type2;"
                   + "if (choice == 1){"
                   + "type = \"employee\";"
                   + "type2 = \"employee\";"
                   + "}else{"
                   + "type = \"technician;\"; "
                   + "type2 = \"technician;\"; "
                   + "}"+
"	document.cookie = \"id\" + \"=\" + d;\n" +
 "	document.cookie = \"type\" + \"=\" + type;\n" +    
 "      document.cookie = \"type2\" + \"=\" + type2;\n" +  
"\n" +
"	window.location.replace(\"View_Profile_View\");\n" +
"	\n" +
"}</script>");
           
            out.println("<title>Servlet Check_Day_E_T</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String day = request.getParameter("day"), month = request.getParameter("month"), year = request.getParameter("year"), boss = "BOSS", dayoff = "dayoff";
            int xronia = Integer.parseInt(year), mera = Integer.parseInt(day), minas;
            Calendar calendar = Calendar.getInstance();
            minas = calendar.get(Calendar.MONTH) + 1;
            int akr = dayofweek(mera,minas,xronia);
           
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            boolean a;
            a = ReachAbleDate(mera,minas);
            if (a == false){
                out.println("The day is not correct");
            }else{
                if (akr == 0 || akr == 6){
                    out.println("<p>give a valid day<p>");
                }else{
                    int id;
                    String type = request.getParameter("type");
                    
                    if (type.equals("EMPLOYEE")){
                        
                        out.println(Employee_Nav());
                        out.write(Html2());
                        out.println("<p>This is the programm</p>");            
                        
                        id=0;
                        ResultSet r = stmt.executeQuery("SELECT D_ID FROM APP.EMPLOYEE WHERE NAME  = '"+CurrentUser.CurrentName+"'");
                        if (r.next()) {
                                    id = r.getInt("D_ID");
                        }
                        ResultSet rs;
                        int e_id=0,i=0,j=0,overtime;
                        String names[]=new String[50];
                        int ari8moi[]=new int[50];
                        String name="",first="",second="",third="",forth="";
                        type="EMPLOYEE";
                        rs = stmt.executeQuery("SELECT NAME,E_ID FROM APP.EMPLOYEE WHERE D_ID  = "+id+"");
                        while (rs.next()){ 
                            ari8moi[i]=rs.getInt("E_ID");
                            names[i] = rs.getString("NAME");
                            i++;
                        }
                        String teststring="<table border='3' class=\"table table-hover table-blue\" >" +"<thead>" +
                          "<tr>"+
                               "<th>Name</th>"+
                               "<th>E_ID</th>"+
                               "<th>8-10</th>"+
                               "<th>10-12</th>"+
                               "<th>12-14</th>" +
                               "<th>14-16</th>" +
                               "<th>Overtime</th>" +
                            "</tr>";
                        while (j<i){
                            String typos="employee";
                            r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+mera+" AND MONTH_NUM = "+minas+" AND PERSON_TYPE = '" +type+"' )");
                            if (r.next() == false){
                               teststring = teststring.concat("<tr>"+
                                "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+1+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                "<td>"+ari8moi[j]+"</td>"+
                                "<td>"+""+"</td>"+
                                "<td>"+""+"</td>"+
                                "<td>"+""+"</td>" +
                                "<td>"+""+"</td>" +
                                "<td>"+""+"</td>" +"</tr>");
                            }else{
                                first = r.getString("FIRST_2");
                                second = r.getString("SECOND_2");
                                third = r.getString("THIRD_2");
                                forth = r.getString("FORTH_2");
                                overtime = r.getInt("OVERTIME");
                                teststring = teststring.concat("<tr>"+
                                    "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+1+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                    "<td>"+ari8moi[j]+"</td>"+
                                    "<td>"+first+"</td>"+
                                    "<td>"+second+"</td>"+
                                    "<td>"+third+"</td>" +
                                    "<td>"+forth+"</td>" +
                                    "<td>"+overtime+"</td>"+"</tr>");
                            }    
                        j++;
                        }
                        out.write(teststring);
                }else{///////////////
                    
                    out.println(Techn_Nav());
                    out.write(Html2());
                    out.println("<br></br>");           
                    id=0;
                    ResultSet r = stmt.executeQuery("SELECT D_ID FROM APP.TECHNICIAN WHERE NAME  = '"+CurrentUser.CurrentName+"'");
                    if (r.next()) {
                        id = r.getInt("D_ID");
                    }
                    ResultSet rs;
                    int e_id=0,i=0,j=0,overtime;
                    String names[]=new String[50];
                    int ari8moi[]=new int[50];
                    String name="",first="",second="",third="",forth="";
                    type="TECHNICIAN";
                    rs = stmt.executeQuery("SELECT NAME,T_ID FROM APP.TECHNICIAN WHERE D_ID  = "+id+"");
                    while (rs.next()){ 
                        ari8moi[i]=rs.getInt("T_ID");
                        names[i] = rs.getString("NAME");
                        i++;
                    }
                    String teststring="<table border='3' class=\"table table-hover table-blue\" >" +"<thead>" +
                        "<tr>"+
                            "<th>Name</th>"+
                            "<th>E_ID</th>"+
                            "<th>8-10</th>"+
                            "<th>10-12</th>"+
                            "<th>12-14</th>" +
                            "<th>14-16</th>" +
                            "<th>Overtime</th>" +
                        "</tr>";
                        while (j<i){
                            r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+mera+" AND MONTH_NUM = "+minas+" AND PERSON_TYPE = '" +type+"' )");
                            if (r.next() == false){
                               teststring = teststring.concat("<tr>"+
                                "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+2+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                "<td>"+ari8moi[j]+"</td>"+
                                "<td>"+""+"</td>"+
                                "<td>"+""+"</td>"+
                                "<td>"+""+"</td>" +
                                "<td>"+""+"</td>" +
                                "<td>"+""+"</td>" +"</tr>");
                            }else{
                                first = r.getString("FIRST_2");
                                second = r.getString("SECOND_2");
                                third = r.getString("THIRD_2");
                                forth = r.getString("FORTH_2");
                                overtime = r.getInt("OVERTIME");
                                teststring = teststring.concat("<tr>"+
                                "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+1+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                "<td>"+ari8moi[j]+"</td>"+
                                "<td>"+first+"</td>"+
                                "<td>"+second+"</td>"+
                                "<td>"+third+"</td>" +
                                "<td>"+forth+"</td>" +
                                "<td>"+overtime+"</td>"+"</tr>");
                            }   
                        j++;
                    }
                        teststring=teststring.concat("<thead>"+ "<tbody>"+"</table>");
                        out.write(teststring);        
                }
            }
        }
                        
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
            Logger.getLogger(Check_Day_E_T.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Check_Day_E_T.class.getName()).log(Level.SEVERE, null, ex);
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
