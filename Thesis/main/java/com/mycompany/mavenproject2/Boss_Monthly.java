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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Boss_Monthly", urlPatterns = {"/Boss_Monthly"})
public class Boss_Monthly extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
   public static String RetMonth(){
            Calendar calendar = Calendar.getInstance(); 
            if (calendar.get(Calendar.MONTH) == 0){
                return "January";
            }else if (calendar.get(Calendar.MONTH) == 1){
                return "February";
            }else if (calendar.get(Calendar.MONTH) == 2){
                return "March";
            }else if (calendar.get(Calendar.MONTH) == 3){
                return "April";
            }else if (calendar.get(Calendar.MONTH) == 4){
                return "May";
            }else if (calendar.get(Calendar.MONTH) == 5){
                return "June";
            }else if (calendar.get(Calendar.MONTH) == 6){
                return "July";
            }else if (calendar.get(Calendar.MONTH) == 7){
                return "August";
            }else if (calendar.get(Calendar.MONTH) == 8){
                return "September";
            }else if (calendar.get(Calendar.MONTH) == 9){
                return "October";
            }else if (calendar.get(Calendar.MONTH) == 10){
                return "November";
            }else{
                return "December";
            }
    } 
    
    public static int FirstDay(){
        Calendar calendar = Calendar.getInstance(); 
        int currentday = calendar.get(Calendar.DAY_OF_WEEK);
        String days[]=new String[]{ "Su","Mo","Tu","We","Th","Fr","Sa" };
        int calday = calendar.get(Calendar.DATE);
        while (calday != 1){
            calday--;//until it gets to the 1st of month
            currentday--;
            if (currentday == -1){
                currentday = 6;
            }
        }
        int i=0;
        int result;
        while (i != currentday){
            i++;
        }
        if (i == 0){
            i = 6;
           result = i;
       }else{
            
            result = i-1;
       }
       return result;
    }  
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(Html());
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            CurrentUser.Ses=1;
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            }

            out.write(" <link href=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"//code.jquery.com/jquery-1.11.1.min.js\"></script>");
            out.write("<style>");
            String css = Css_Calendar();
            out.write(css);
            out.write("</style>");
            out.println("<title>Servlet Boss_Monthly</title>");  
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
            Calendar calendar = Calendar.getInstance(); 
            String CurrentMonth = RetMonth();
            String teststring;
            int FirstDay = FirstDay();
            int prevlast=-1;
            if (FirstDay  == 0){
                prevlast = 6;
            }else{
                prevlast = FirstDay -1;
            }
         
            String output = "<table border='3' id=\"lol\" class=\"table table-hover table-blue\" >" +"<thead>";
            output = output.concat("<tr>"+  "<th>Mo</th>"+
		  "<th>Tu</th>"+
		  "<th>We</th>"+
		  "<th>Th</th>"+
		  "<th>Fr</th>"+
		  "<th>Sa</th>"+
		  "<th>Su</th>"+
		"</tr>"+"<tr>");
            int minas = (calendar.get(Calendar.MONTH) ),i=1,counter=1;
            while(counter != FirstDay){
               output = output.concat("<td>"+" "+"</td>");
               counter++;
            }
            if ((minas == 0) || (minas == 2) || (minas == 4) || (minas == 6) || (minas == 7) || (minas == 9) || (minas == 11)){
                while(i<32){
                    if (counter == 8){
                        output =output.concat("</tr>");
                        counter = 1;
                    }else if (counter == 0){
                        output =output.concat("<tr>");
                    }
                    if (i == calendar.get(Calendar.DATE) ){
                        output = output.concat("<td bgcolor=\"green\">"+i+"</td>");
                    }else{
                        output = output.concat("<td>"+i+"</td>");
                    }
                    i++;
                    counter++;
                }
            }else if (minas == 1){
                 while(i<29){
                     if (counter == 8){
                        output =output.concat("</tr>");
                        counter = 1;
                    }else if (counter == 0){
                        output =output.concat("<tr>");
                    }
                    if (i == calendar.get(Calendar.DATE) ){
                        output = output.concat("<td><span class=\"active\">"+i+"</span></td>");
                    }else{
                        output = output.concat("<td>"+i+"</td>");
                    }
                    i++;
                    counter++;
                }
            }else{
                 while(i<31){
                     if (counter == 8){
                        output =output.concat("</tr>");
                        counter = 1;
                    }else if (counter == 0){
                        output =output.concat("<tr>");
                    }
                    if (i == calendar.get(Calendar.DATE) ){
                        output = output.concat("<td><span class=\"active\">"+i+"</span></td>");
                    }else{
                        output = output.concat("<td>"+i+"</td>");
                    }
                    i++;
                    counter++;
                }
            }
            while (counter != 8){
                output = output.concat("<td>"+" "+"</td>");
                counter++;
            }
            
            String type="BOSS", h = String.valueOf(calendar.get(Calendar.YEAR));
            teststring = "<form action=\"Check_Day\" method=\"POST\">" +
            "  <br>You can search directive for a specific day.<br>\n" +                 
            "<select name=\"day\" id=\"day\" >"+
                    "<option value=1>1</option>"+
                    "<option value=2>2</option>"+
                    "<option value=3>3</option>"+
                    "<option value=4>4</option>"+
                    "<option value=5>5</option>"+
                    "<option value=6>6</option>"+
                    "<option value=7>7</option>"+
                    "<option value=8>8</option>"+
                    "<option value=9>9</option>"+
                    "<option value=10>10</option>"+
                    "<option value=11>11</option>"+
                    "<option value=12>12</option>"+
                    "<option value=13>13</option>"+
                    "<option value=14>14</option>"+
                    "<option value=15>15</option>"+
                    "<option value=16>16</option>"+
                    "<option value=17>17</option>"+
                    "<option value=18>18</option>"+
                    "<option value=19>19</option>"+
                    "<option value=20>20</option>"+
                    "<option value=21>21</option>"+
                    "<option value=22>22</option>"+
                    "<option value=23>23</option>"+
                    "<option value=24>24</option>"+
                    "<option value=25>25</option>"+
                    "<option value=26>26</option>"+
                    "<option value=27>27</option>"+
                    "<option value=28>28</option>"+
                    "<option value=29>29</option>"+
                    "<option value=30>30</option>"+
                    "<option value=31>31</option>"+
                    "</select>"+
                    "<input type=\"hidden\" id=\"year\" name=\"year\" value="+h+">"+
                    "<input type=\"hidden\" id=\"type\" name=\"type\" value="+type+">"+
                    "<input type=\"submit\">\n" +
            "</form>";

            output = output.concat("</tr>"+"<thead>"+   "</tbody>"+"</table>");
            out.write(Html2());
            out.write("<br>");
            out.write("<p>"+CurrentMonth+"</p>");
            out.write(output);
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
           Logger.getLogger(Boss_Monthly.class.getName()).log(Level.SEVERE, null, ex);
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
           Logger.getLogger(Boss_Monthly.class.getName()).log(Level.SEVERE, null, ex);
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
