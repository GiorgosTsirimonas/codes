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
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
@WebServlet(name = "Chat", urlPatterns = {"/Chat"})
public class Chat extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    
    public static Map< String,String> MapEmployees =   new HashMap<String,String>(); 
    public static Map< String,String> MapTechn =   new HashMap<String,String>();
    
    static int Push(String name, String type) throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM APP.MAILS WHERE ( SENDER_NAME = '"+name+"' AND SAW = "+1+" AND RECEIVER_NAME  = '"+CurrentUser.CurrentName+"')");
        String temp2 = "";
        int counter = 0;
        if (rs.next()){
            counter++;
        }
        conn.close();
        stmt.close();
        return counter;
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
            
            out.write(Search());
            out.write("<script>function NewMessage(id,id2,type2){\n" +
                      "console.log(id,id2,type2);"+
                      "let type = \"boss\";"+
                      "let temp = \"employee\";"+
                      "let temp1 = \"technician\";"+
                      "	document.cookie = \"sender_id\" + \"=\" + id;\n" +
                      "	document.cookie = \"receiver_id\" + \"=\" + id2;\n" +
                      "	document.cookie = \"sender_type\" + \"=\" + type;\n" +
                      "if (type2 == 1){"
                      + "document.cookie = \"receiver_type\" + \"=\" + temp;}"+
                      "else{ document.cookie = \"receiver_type\" + \"=\" + temp1;}"+
                      "	window.location.replace(\"Chat_view\");\n" +
            "}</script>"); 
            
            out.println("<title>Chat</title>");
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
            HashMap<String,Integer> employees = new HashMap<String,Integer>();
            HashMap<String,Integer> technicians = new HashMap<String,Integer>();
            int boss_id = -1, counter = 0, et_id=-1;
            String type1 = "employee", type2 = "technician", teststring = "", table="<table border='3'  class=\"table table-hover table-blue\" >" +"<thead>"+"<tbody id=\"myTable\">";
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            table ="<table border='3'  >\n" +"  <thead>\n" +"  <tr>\n" +" <th>Name</th>\n" +"<th>Type</th>\n" +"<th>Message</th>\n" + 
                "  </tr>\n"+"</thead><tbody id=\"myTable\">";
            ResultSet r = stmt.executeQuery("SELECT B_ID FROM APP.BOSS WHERE NAME  = '"+CurrentUser.CurrentName+"'");
            if (r.next()){
                 boss_id = r.getInt("B_ID");
            }
            String temp="",temp1="",temp2="";
            r = stmt.executeQuery("SELECT NAME,E_ID FROM APP.EMPLOYEE WHERE D_ID  = "+boss_id+"");
            int result = 0;

            while (r.next()){    
                et_id = r.getInt("E_ID");
                result = Push(r.getString("NAME"), "EMPLOYEE");
                if (result == 1){
                    table = table.concat("<tr><td id =\"first\" >"+r.getString("NAME")+"   </td><td>Employee</td><td   onClick =\"NewMessage("+boss_id+","+et_id+","+1+")\">"+"<span>New message!</span>"+"</td>"
                        + "</tr>");
                }else{
                   employees.put(r.getString("NAME"),et_id); 
                }
            }
            
            //technicians
            r = stmt.executeQuery("SELECT NAME,T_ID FROM APP.TECHNICIAN WHERE D_ID  = "+boss_id+"");
                        
            while (r.next()){    
                et_id = r.getInt("T_ID");
                result = Push(r.getString("NAME"), "TECHNICIAN");
                if (result == 1){
                   table = table.concat("<tr><td>"+r.getString("NAME")+"</td><td>Technician</td><td onClick =\"NewMessage("+boss_id+","+et_id+","+2+")\">"+"<span>New message!</span>"+"</td></tr>");
                }else{
                   technicians.put(r.getString("NAME"),et_id);  
                }
            }
            
            Iterator it = employees.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                table = table.concat("<tr><td id =\"first\" >"+pair.getKey()+"   </td><td>Employee</td><td   onClick =\"NewMessage("+boss_id+","+pair.getValue()+","+1+")\">"+"View"+"</td>"
                        + "</tr>");
                it.remove(); // avoids a ConcurrentModificationException
            }
           
            Iterator its = technicians.entrySet().iterator();
            while (its.hasNext()) {
                Map.Entry pair = (Map.Entry)its.next();
                table = table.concat("<tr><td id =\"first\" >"+pair.getKey()+"   </td><td>Technician</td><td   onClick =\"NewMessage("+boss_id+","+pair.getValue()+","+2+")\">"+"View"+"</td>"
                        + "</tr>");
                its.remove(); // avoids a ConcurrentModificationException
            }
           
            out.write(Html2());
            out.write("<br>");
            out.write(
            "  <input class=\"form-control\" id=\"myInput\" type=\"text\" placeholder=\"Search..\">\n" 
            );
            table = table.concat("<thead>"+   "<tbody>"+"</table>");
            out.println("<br>");
            out.println(table);
            out.println(Html3());
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
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
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
