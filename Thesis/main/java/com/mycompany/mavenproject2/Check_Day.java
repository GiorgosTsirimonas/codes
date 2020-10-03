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
import static com.mycompany.mavenproject2.css.Css_Calendar;
import static com.mycompany.mavenproject2.js.Search;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
@WebServlet(name = "Check_Day", urlPatterns = {"/Check_Day"})
public class Check_Day extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    public static int dayofweek(int d, int m, int y) 
{ 
    int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 }; 
    y -= (m < 3) ? 1 : 0; 
    return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7; 
} 
    
    public static boolean ReachAbleDate(int mera,int minas){
        if (minas == 1 || minas == 3 || minas == 5 || minas == 7 || minas == 9 || minas == 11){
            if (mera > 30){
                return false;
            }else{
                return true;
            }
        }else if (minas == 2){
            if (mera >28){
                return false;
            }
        }else{
            return true;
        }
        return true;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
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
            out.write(Search());
           
           out.write("<script>function GotoView(id,typos){\n" +
            "var d = id;\n" +
            "var type = \"employee\";"+
            "if (typos == 1){ type = \"technician\"; }"+
                "document.cookie = \"id\" + \"=\" + d;\n" +
                "document.cookie = \"type\" + \"=\" + type;\n" +       
            "\n" +
            "window.location.replace(\"View_Profile\");\n" +
            "\n" +
            "}</script>");
           
            out.println("<title>Servlet Weekly_Employees</title>");            
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
            
            
            String day = request.getParameter("day"), month = request.getParameter("month"), year = request.getParameter("year");
            int xronia = Integer.parseInt(year),  mera = Integer.parseInt(day), minas;
           
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
            String boss = "BOSS", dayoff = "dayoff";
        
            out.println(Boss_Nav());
            out.write(Html2());
            boolean a;
            a = ReachAbleDate(mera,minas);
            if (a == false){
                out.println("<p>not a correct day</p>");
            }else{
                if (akr == 0 || akr == 6){
                    out.println("<p>give a valid day</p>");
                }else{
                    out.println("<p>This is the programm</p>");
                    int id;
                    String type = request.getParameter("type");
                    if (type.equals("BOSS")){
                        ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
                        if (r.next() != false) {
                            id = r.getInt("B_ID"); 
                            r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+id+" AND DAY_NUM = "+mera+" AND MONTH_NUM = "+minas+" AND PERSON_TYPE = '"+boss+"')");
                            if (r.next() != false ){
                                String first = r.getString("FIRST_2");
                                String second = r.getString("SECOND_2");
                                String third = r.getString("THIRD_2");
                                String forth = r.getString("FORTH_2");
                                String over = r.getString("OVERTIME");
                                String teststring="";
                                if (!(first.equalsIgnoreCase(dayoff))){
                                    teststring="<form action=\"Day_Alter\" method=\"POST\">";
                                    teststring = teststring.concat("<table border='3' class=\"table table-hover table-blue\" >" +"<thead>" +
                                        "<tr>"+
                                            "<td>8-10</td>"+"<td>"+first+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td>10-12</td>"+"<td>"+second+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td>12-14</td>"+"<td>"+third+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td>14-16</td>" +"<td>"+forth+"</td>"+
                                        "</tr>"+
                                        "<tr>"+
                                            "<td>Overtime</td>" +"<td>"+Integer.parseInt(over)+"</td>"+
                                        "</tr>"+
                                            "<td>Update</td>"+"<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+          
                                                  "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                                  "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                                  "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                                  "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                                  "<input type=\"hidden\" id=\"over\" name=\"over\" value="+over+">"+
                                                  "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                  "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                  "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                                  "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                                  "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Monthly"+">"+ 
                                               " <input type=\"submit\" value=\"UpDate\" />"+"</form>"+
                                            "</td>"+ 
                                       "</tr>");
                                                   
                                        teststring = teststring.concat("<td>Dayoff</td>"+"<td>"+
                                            "<form action=\"Dayoff\" method=\"POST\">"+
                                                   "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                                   "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                                   "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                                   "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                                   "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                                   "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                   "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                   "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                                   "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                                   "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Monthly"+">"+ 
                                                    " <input type=\"submit\" value=\"Dayoff\" />"+
                                                     "</form>"+
                                                    "</td>"+     
                                                     "</tr>"+"<thead>"+ "<tbody>"+"</table>");
                                                   out.write(teststring);
                                        }else{
                                            teststring = teststring.concat("<table border='3' class=\"table table-hover table-blue\" >" +"<thead>" +  
                                                "<tr>"+
                                                 "<td>8-10</td>"+"<td>"+first+"</td>"+
                                                "</tr>"+
                                             "<tr>"+
                                                 "<td>10-12</td>"+"<td>"+second+"</td>"+
                                              "</tr>"+
                                              "<tr>"+
                                                 "<td>12-14</td>"+"<td>"+third+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                           "<td>14-16</td>" +"<td>"+forth+"</td>"+
                                                "</tr>"+
                                                "<tr>"+
                                                           "<td>Overtime</td>" +"<td>"+Integer.parseInt(over)+"</td>"+
                                                "</tr>"+
                                                "<tr>");
                                            teststring = teststring.concat("<td>Recall</td>"+"<td>"+
                                                "<form action=\"Recall\" method=\"POST\">"+
                                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Monthly"+">"+ 
                                                      " <input type=\"submit\" value=\"Recall\" />"+
                                                       "</form>"+
                                             "</td>"+      
                                            "</tr>"+"<thead>"+ "<tbody>"+"</table>");
                                            out.write(teststring);
                                        }
                                    }else{
                                        out.println("<p>Nothing for today,but you can add</p>");
                                        String h,teststring;
                                        h="<form action=\"Day_Single\" method=\"POST\">"+          
                                           "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                           "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                           "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Monthly"+">"+ 
                                        " <input type=\"submit\" value=\"Make\" />"+"</form>";
                                        out.write(h);
                                        teststring = ("<td>"+
                                            "<form action=\"Dayoff\" method=\"POST\">"+
                                               "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                               "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                               "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                               "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                               "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                               "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                               "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                               "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(id)+">"+
                                               "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(boss)+">"+
                                               "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Boss_Monthly"+">"+ 
                                                "<input type=\"submit\" value=\"Dayoff\" />"+
                                            "</form>"+
                                            "</td>"+                   
                                            "</tr>"+"<thead>"+ "<tbody>"+"</table>");
                                        out.write(teststring);       
                                }
                            }else{
                                out.println("no boss");
                            }
                    }else if (type.equals("EMPLOYEE")){
                        id=0;
                        ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
                        if (r.next()) {
                                    id = r.getInt("B_ID");
                        }
                        
                        int typos= 0, e_id=0, i=0, j=0, overtime;
                        ResultSet rs;
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
                               "<th>Add/Update</th>" +
                               "<th>Dayoff/Recall</th>" +
                            "</tr>";
                        while (j<i){
                            r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+mera+" AND MONTH_NUM = "+minas+" AND PERSON_TYPE = '" +type+"' )");
                           if (r.next() == false){
                                teststring = teststring.concat("<tr>"+
                                    "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+typos+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                     "<td>"+ari8moi[j]+"</td>"+
                                    "<td>"+""+"</td>"+
                                    "<td>"+""+"</td>"+
                                    "<td>"+""+"</td>" +
                                    "<td>"+""+"</td>" +
                                    "<td>"+""+"</td>" +
                                    "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                     "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                     "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                     "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                     "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                     "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                     " <input type=\"submit\" value=\"Add\" />"+
                                "</form>"+"</td>");
                                
                                teststring = teststring.concat("<td>"+
                                        "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                        " <input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+"</td>"+
                                    "</tr>");
                            }else{
                
                                first = r.getString("FIRST_2");
                                second = r.getString("SECOND_2");
                                third = r.getString("THIRD_2");
                                forth = r.getString("FORTH_2");
                                overtime = r.getInt("OVERTIME");
                                teststring = teststring.concat("<tr>"+
                                    "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+typos+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                    "<td>"+ari8moi[j]+"</td>"+
                                    "<td>"+first+"</td>"+
                                    "<td>"+second+"</td>"+
                                    "<td>"+third+"</td>" +
                                    "<td>"+forth+"</td>" +
                                    "<td>"+overtime+"</td>");
                                if (!(first.equalsIgnoreCase(dayoff))){
                                    if (first != null){
                                        teststring = teststring.concat( "<tdUpdate</td>"+"<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+
                                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                            "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                            "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                            "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                            "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                            "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                            " <input type=\"submit\" value=\"UpDate\" />"+
                                        "</form>"+"</td>");
                                        
                                        teststring = teststring.concat( "<tdUpdate</td>"+"<td>"+
                                            "<form action=\"Dayoff\" method=\"POST\">"+
                                                "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                                "<input type=\"submit\" value=\"Dayoff\" />"+
                                            "</form>"+"</td>"+
                                            "</tr>");
                                        
                                     }else{
                                        teststring = teststring.concat( "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                                " <input type=\"submit\" value=\"Add\" />"
                                            +"</form>"+"</td>");
                                        
                                        teststring = teststring.concat( "</form>"+"</td>"+"<td>"+
                                        "<form action=\"Dayoff\" method=\"POST\">"+
                                            "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                            "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                            "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                            "<input type=\"submit\" value=\"Dayoff\" />"+
                                         "</form>"+"</td>"+
                                        "</tr>");
                                      }
                                }else{
                                    teststring = teststring.concat("<td>"+" "+"</td>"+"<td>"+
                                     "<form action=\"Recall\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Employees"+">"+ 
                                        "<input type=\"submit\" value=\"Recall\" />"+
                                     "</form>"+
                                     "</td>"+                   
                                     "</tr>");
                                }
                            }
                        j++;
                    }
                        teststring = teststring.concat("<thead>"+ "<tbody>"+"</table>");
                        out.write(teststring);
                }else{  ////////////////////////technician
                    
                        id=0;
                        ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
                        if (r.next()) {
                                    id = r.getInt("B_ID");
                        }
                        
                        ResultSet rs;
                        int e_id=0, i=0, j=0, overtime;
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
                        int typos = 1;
                        String teststring="<table border='3' class=\"table table-hover table-blue\" >" +"<thead>" +
                          "<tr>"+
                               "<th>Name</th>"+
                               "<th>E_ID</th>"+
                               "<th>8-10</th>"+
                               "<th>10-12</th>"+
                               "<th>12-14</th>" +
                               "<th>14-16</th>" +
                               "<th>Overtime</th>" +
                               "<th>Add/Update</th>" +
                               "<th>Dayoff/Recall</th>" +
                            "</tr>";
                        while (j<i){
                            r = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+mera+" AND MONTH_NUM = "+minas+" AND PERSON_TYPE = '" +type+"' )");
                           if (r.next() == false){
                                teststring = teststring.concat("<tr>"+
                                   "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+typos+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                                   "<td>"+ari8moi[j]+"</td>"+
                                   "<td>"+""+"</td>"+
                                   "<td>"+""+"</td>"+
                                   "<td>"+""+"</td>" +
                                   "<td>"+""+"</td>" +
                                   "<td>"+""+"</td>" +
                                   "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                        "<input type=\"submit\" value=\"Add\" />"+
                                   "</form>"+"</td>");
                                
                                teststring = teststring.concat("<td>"+
                                    "<form action=\"Dayoff\" method=\"POST\">"+
                                        "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                        "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                        "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                        "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                        "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                        "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                        " <input type=\"submit\" value=\"Dayoff\" />"+
                                    "</form>"+"</td>"+
                                    "</tr>");
                            }else{
                
                                first = r.getString("FIRST_2");
                                second = r.getString("SECOND_2");
                                third = r.getString("THIRD_2");
                                forth = r.getString("FORTH_2");
                                overtime = r.getInt("OVERTIME");
                                teststring = teststring.concat("<tr>"+
                                "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+","+typos+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
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
                                            "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                            "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                            "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                                            "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                                            "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                                            "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                                            "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                            "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                            " <input type=\"submit\" value=\"UpDate\" />"+
                                        "</form>"+"</td>");
                                        
                                         teststring = teststring.concat( "<td>"+
                                            "<form action=\"Dayoff\" method=\"POST\">"+
                                                "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+              
                                                "<input type=\"submit\" value=\"Dayoff\" />"+
                                            "</form>"+"</td>"+
                                            "</tr>");
                                        
                                     }else{
                                        teststring = teststring.concat( "<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                                " <input type=\"submit\" value=\"Add\" />"+
                                            "</form>"+"</td>");
                                        
                                        teststring = teststring.concat( "</form>"+"</td>"+"<td>"+
                                            "<form action=\"Dayoff\" method=\"POST\">"+
                                                "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                                "<input type=\"hidden\" id=\"over\" name=\"over\" value="+0+">"+
                                                "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                                "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                                "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                                " <input type=\"submit\" value=\"Dayoff\" />"+
                                             "</form>"+"</td>"+
                                             "</tr>");
                                      }
                                }else{
                                    teststring = teststring.concat("<td>"+" "+"</td>"+"<td>"+
                                        "<form action=\"Recall\" method=\"POST\">"+
                                           "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(mera)+">"+
                                           "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(minas)+">"+
                                           "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+"Monthly_Technicians"+">"+ 
                                           "<input type=\"submit\" value=\"Recall\" />"+
                                        "</form>"+
                                        "</td>"+                   
                                        "</tr>");
                                }
                            }
                        j++;
                    }
                        teststring = teststring.concat("<thead>"+ "<tbody>"+"</table>");
                        out.write(teststring);  
                }   
                    }
                }
            out.write(Html3());      
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
            Logger.getLogger(Check_Day.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Check_Day.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Check_Day.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Check_Day.class.getName()).log(Level.SEVERE, null, ex);
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
