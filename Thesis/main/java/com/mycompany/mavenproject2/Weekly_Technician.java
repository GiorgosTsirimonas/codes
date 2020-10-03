/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Check_Day.dayofweek;
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
@WebServlet(name = "Weekly_Technician", urlPatterns = {"/Weekly_Technician"})
public class Weekly_Technician extends HttpServlet {

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
                    "var type = \"technician\";"+
            "	document.cookie = \"id\" + \"=\" + d;\n" +
             "	document.cookie = \"type\" + \"=\" + type;\n" +       
            "\n" +
            "	window.location.replace(\"View_Profile\");\n" +
            "	\n" +
            "}</script>");
            
            out.println("<title>Servlet Weekly_Technician</title>");            
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
          
            out.println(Boss_Nav());
            int e_id=0;
            ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            if (r.next()){
                 e_id = r.getInt("B_ID");
            }  
            Calendar calendarios = Calendar.getInstance(); 
            int day = calendarios.get(Calendar.DATE), month = calendarios.get(Calendar.MONTH) + 1, year =calendarios.get(Calendar.YEAR);
            String weeklytec="Weekly_Technician", teststring, first=null,second=null,third=null,forth=null,dayoff ="dayoff", type="TECHNICIAN";
            int akr = dayofweek(day,month,year);
            int i=day,j=0, temp, overtime=0;;
            while (akr != 0){
                akr--;
                i--;
            }
            i++; 
            String days[]=new String[]{ "Su","Mo","Tu","We","Th","Fr","Sa" };
            teststring = "<table border='3'   class=\"table table-hover table-blue\" >" +"<thead>"  +
                          "<tr>"+
                               "<th>Day and Date</th>"+
                               "<th>Name</th>"+
                               "<th>8-10</th>"+
                               "<th>10-12</th>"+
                               "<th>12-14</th>"+
                               "<th>14-16</th>"+
                               "<th>Overtime</th>"+
                               "<th>Add/Update</th>" +
                               "<th>Dayoff/Recall</th>" +
                           "</tr>";
            String names[]=new String[50];
            int ari8moi[]=new int[50];
            ResultSet rs;
            rs = stmt.executeQuery("SELECT NAME,T_ID FROM APP.TECHNICIAN WHERE D_ID  = "+e_id+"");
            int p=0,te=0;
            while (rs.next()){ 
                ari8moi[p]=rs.getInt("T_ID");
                names[p] = rs.getString("NAME");
                p++;
            }
            while (te<5){
                j=0;
                while (j<p){
                    first = " ";
                    second = "";
                    third = "";
                    forth = "";
                    overtime = -1;
                    rs = stmt.executeQuery("SELECT * FROM APP.DAY WHERE (ID  = "+ari8moi[j]+" AND DAY_NUM = "+i+" AND MONTH_NUM = "+month+" AND PERSON_TYPE= '"+type+"')");
                    if (rs.next()){
                        first = rs.getString("FIRST_2");
                        second = rs.getString("SECOND_2");
                        third = rs.getString("THIRD_2");
                        forth = rs.getString("FORTH_2");
                        overtime = rs.getInt("OVERTIME");
                    }
                    if (!(first.equalsIgnoreCase(dayoff))){
                        if ((first != null || first != " ") && overtime != -1){
                            teststring = teststring.concat("<tbody id=\"myTable\">"+"<tr>"+
                               "<td>"+days[te+1]+" "+i+" "+month+"</td>"+
                               "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                               "<td>"+first+"</td>"+
                               "<td>"+second+"</td>"+
                               "<td>"+third+"</td>"+
                               "<td>"+forth+"</td>"+
                               "<td>"+overtime+"</td>");

                            teststring = teststring.concat( "<td>"+"<form action=\"Day_Alter\" method=\"POST\">"+
                               "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                               "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                               "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                               "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                               "<input type=\"hidden\" id=\"first\" name=\"first\" value="+first+">"+
                               "<input type=\"hidden\" id=\"second\" name=\"second\" value="+second+">"+
                               "<input type=\"hidden\" id=\"third\" name=\"third\" value="+third+">"+
                               "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+forth+">"+
                               "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                               "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weeklytec+">"+ 
                               " <input type=\"submit\" value=\"UpDate\" />"+
                            "</form>"+"</td>");
                               
                            teststring = teststring.concat("<td>"+
                                "<form action=\"Dayoff\" method=\"POST\">"+
                                           "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"over\" name=\"over\" value="+String.valueOf(overtime)+">"+
                                           "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                           "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                           "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weeklytec+">"+ 
                                           "<input type=\"submit\" value=\"Dayoff\" />"+
                                "</form>"+"</td>"+
                           "</tr>");
                        }else{
                            teststring = teststring.concat("<tbody id=\"myTable\">"+ "<tr>"+
                               "<td>"+days[te+1]+" "+i+" "+month+"</td>"+
                               "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                               "<td>"+first+"</td>"+
                               "<td>"+second+"</td>"+
                               "<td>"+third+"</td>"+
                               "<td>"+forth+"</td>"+
                               "<td>"+""+"</td>"+"<td>"+"<form action=\"Day_Single\" method=\"POST\">"+
                               "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                               "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                               "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                               "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weeklytec+">"+ 
                               " <input type=\"submit\" value=\"Add\" />"+
                            "</form>"+"</td>");

                            teststring = teststring.concat("<td>"+
                            "<form action=\"Dayoff\" method=\"POST\">"+
                                           "<input type=\"hidden\" id=\"first\" name=\"first\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"second\" name=\"second\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"third\" name=\"third\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"forth\" name=\"forth\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"over\" name=\"over\" value="+dayoff+">"+
                                           "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                           "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                           "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                                           "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weeklytec+">"+ 
                                       " <input type=\"submit\" value=\"Dayoff\" />"+
                                        "</form>"+"</td>"+
                            "</tr>");
                        }
                   }else{
                         teststring = teststring.concat("<tbody id=\"myTable\">"+"<tr>"+
                               "<td>"+days[te+1]+" "+i+" "+month+"</td>"+
                               "<td id=\\\"id\\\" value =\"+name+\" onClick =\"GotoView("+ari8moi[j]+")   \" border = \"0\">"+String.valueOf(names[j])+"</td>"+
                               "<td>"+first+"</td>"+
                               "<td>"+second+"</td>"+
                               "<td>"+third+"</td>"+
                               "<td>"+forth+"</td>"+
                               "<td>"+overtime+"</td>");
                        teststring = teststring.concat("<td>"+" "+"</td>"+"<td>"+
                               "<form action=\"Recall\" method=\"POST\">"+
                                    "<input type=\"hidden\" id=\"day\" name=\"day\" value="+String.valueOf(i)+">"+
                                    "<input type=\"hidden\" id=\"month\" name=\"month\" value="+String.valueOf(month)+">"+
                                    "<input type=\"hidden\" id=\"e_id\" name=\"e_id\" value="+String.valueOf(ari8moi[j])+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"type\" value="+String.valueOf(type)+">"+
                                    "<input type=\"hidden\" id=\"type\" name=\"direction\" value="+weeklytec+">"+ 
		                    " <input type=\"submit\" value=\"Recall\" />"+
                                "</form>"+
                                "</td>"+                   
                        "</tr>");
                    }
                   j++;
                    
                }
                i++;
                te++;
            }
            
            out.write(Html2());
            teststring=teststring.concat("<thead>"+ "<tbody>"+"</table>");
            out.println("<br>");
            out.println("<p>This is technicians weekly programm<p>");
            out.write(
            "  <input class=\"form-control\" id=\"myInput\" type=\"text\" placeholder=\"Search..\">\n" +
            "  <br>");
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
            Logger.getLogger(Weekly_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Weekly_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
