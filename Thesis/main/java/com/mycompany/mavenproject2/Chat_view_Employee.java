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
import static com.mycompany.mavenproject2.css.Css_DWS;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giorgos tsirimonas
 */
@WebServlet(name = "Chat_view_Employee", urlPatterns = {"/Chat_view_Employee"})
public class Chat_view_Employee extends HttpServlet {

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
            out.println("<title>Servlet Employee_Home</title>");            
            out.println("</head>");
            out.println("<body>");
             
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String nav ="", type_sender = "", id_sender = "",id_receiver = "",type_receiver = "", name = "", table = "";
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            int not = 0;
            Cookie ck[]=request.getCookies();  
          
            for(int i=0;i<ck.length;i++){ 
               if (ck[i].getName().equals("sender_id")) {
                   id_sender = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("sender_type")) {
                   type_sender = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("receiver_id")) {
                   id_receiver = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }else if (ck[i].getName().equals("receiver_type")) {
                   type_receiver = ck[i].getValue();
                   ck[i].setMaxAge(0);
                }
            }  
            
            ResultSet r;
            if (type_receiver.equals("employee")){
                r = stmt.executeQuery("SELECT NAME FROM APP.EMPLOYEE WHERE E_ID  = "+id_receiver+"");
                if (r.next()){
                    name = r.getString("NAME");
                }
            }else{
                r = stmt.executeQuery("SELECT NAME FROM APP.BOSS WHERE B_ID  = "+id_receiver+"");
                if (r.next()){
                    name = r.getString("NAME");
                }
            }
            
            table ="<table border='3'  >\n" +"  <thead>\n" +"  <th>Chat</th>"; 
            r = stmt.executeQuery("SELECT MESSAGE,SENDER_NAME,RECEIVER_NAME,MAIL_ID FROM (SELECT * FROM APP.MAILS ORDER BY  MAIL_ID ASC)  as ordlist WHERE (SENDER_NAME = '"+name+"'  AND RECEIVER_NAME = '"+CurrentUser.CurrentName+"' OR SENDER_NAME = '"+CurrentUser.CurrentName+"'  AND RECEIVER_NAME = '"+name+"')");
            if (!r.next()){
                out.println(Employee_Nav());
                out.write(Html2());
                out.println("<br><br>");
                out.println("There is no message yet but you can create the first!");
                out.println("<form class=\"forma\"  action=\"Send_Message\">"+
                        "<input type=\"text\" class=\"message\" name=\"message\" placeholder=\"You message...\">"+
                        "<input type=\"hidden\" id=\"sender_name\" name=\"sender_name\" value="+CurrentUser.CurrentName+">"+
                        "<input type=\"hidden\" id=\"sender_type\" name=\"sender_type\" value="+type_sender+">"+
                        "<input type=\"hidden\" id=\"sender_id\" name=\"sender_id\" value="+String.valueOf(id_sender)+">"+
                        "<input type=\"hidden\" id=\"receiver_name\" name=\"receiver_name\" value="+name+">"+
                        "<input type=\"hidden\" id=\"receiver_type\" name=\"receiver_type\" value="+type_receiver+">"+
                        "<input type=\"hidden\" id=\"receiver_id\" name=\"receiver_id\" value="+String.valueOf(id_receiver)+">"+
                        "</form> ");
            }else{
                if (r != null){
                   if (r.getString("SENDER_NAME").equals(name)){
                        table = table.concat("<tr><td class = \"receiver\">"+r.getString("SENDER_NAME")+":   "+r.getString("MESSAGE")+"</td></tr>");
                    }else if (r.getString("SENDER_NAME").equals(CurrentUser.CurrentName)){
                        table = table.concat("<tr><td class = \"sender\">"+r.getString("MESSAGE")+"</td></tr>");
                    }
                }
                
                String message;
                while (r.next()){
                    if (r.getString("SENDER_NAME").equals(name)){
                        table = table.concat("<tr><td class = \"receiver\">"+r.getString("SENDER_NAME")+":   "+r.getString("MESSAGE")+"</td></tr>");
                    }else if (r.getString("SENDER_NAME").equals(CurrentUser.CurrentName)){
                        table = table.concat("<tr><td class = \"sender\">"+r.getString("MESSAGE")+"</td></tr>");
                    }
                }
                table = table.concat("<tr><td class=\"mhnyma\">"
                        + "<form class=\"forma\"  action=\"Send_Message\">"+
                        "<input type=\"text\" class=\"message\" name=\"message\" placeholder=\"You message...\">"+
                        "<input type=\"hidden\" id=\"sender_name\" name=\"sender_name\" value="+CurrentUser.CurrentName+">"+
                        "<input type=\"hidden\" id=\"sender_type\" name=\"sender_type\" value="+type_sender+">"+
                        "<input type=\"hidden\" id=\"sender_id\" name=\"sender_id\" value="+String.valueOf(id_sender)+">"+
                        "<input type=\"hidden\" id=\"receiver_name\" name=\"receiver_name\" value="+name+">"+
                        "<input type=\"hidden\" id=\"receiver_type\" name=\"receiver_type\" value="+type_receiver+">"+
                        "<input type=\"hidden\" id=\"receiver_id\" name=\"receiver_id\" value="+String.valueOf(id_receiver)+">"+
                            "</form> "
                        +"</td></tr> ");
                table = table.concat("</thead><tbody id=\"myTable\">");
                stmt.executeUpdate("UPDATE APP.MAILS SET SAW = "+0+"  WHERE ( RECEIVER_NAME = '"+CurrentUser.CurrentName+"' AND SENDER_NAME = '"+name+"' AND SAW = "+1+") ");
                
                out.println(Employee_Nav());
                out.write(Html2());
                out.println("<br><br>");
                out.println(table);
                
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
            Logger.getLogger(Chat_view_Employee.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Chat_view_Employee.class.getName()).log(Level.SEVERE, null, ex);
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
