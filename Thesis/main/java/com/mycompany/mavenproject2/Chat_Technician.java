/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject2;

import static com.mycompany.mavenproject2.Chat.Push;
import static com.mycompany.mavenproject2.Functions.Html;
import static com.mycompany.mavenproject2.Functions.Html2;
import static com.mycompany.mavenproject2.Functions.Techn_Nav;
import static com.mycompany.mavenproject2.css.Css_DWS;
import static com.mycompany.mavenproject2.js.Search;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
@WebServlet(name = "Chat_Technician", urlPatterns = {"/Chat_Technician"})
public class Chat_Technician extends HttpServlet {

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
            out.write("<script>function NewMessage(id,id2,type2){\n" +
                      "console.log(id,id2,type2);"+
                      "let type = \"technician\";"+
                      "let temp = \"technician\";"+
                      "let temp1 = \"boss\";"+
                      "	document.cookie = \"sender_id\" + \"=\" + id;\n" +
                      "	document.cookie = \"receiver_id\" + \"=\" + id2;\n" +
                      "	document.cookie = \"sender_type\" + \"=\" + type;\n" +
                      "if (type2 == 1){"
                      + "document.cookie = \"receiver_type\" + \"=\" + temp;}"+
                      "else{ document.cookie = \"receiver_type\" + \"=\" + temp1;}"+
                      "	window.location.replace(\"Chat_view_Technician\");\n" +
            "}</script>"); 
            
            out.println("<title>Servlet Technician_Home</title>");            
            out.println("</head>");
            out.println("<body>");
         
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Insert_Boss.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/BigCorporation", "app", "app");
            Statement stmt = conn.createStatement();
            out.println(Techn_Nav());
            
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Cache-Control","no-store");
            response.setHeader("Pragma","no-cache");
            response.setDateHeader ("Expires", 0);
            
            if (CurrentUser.CurrentName.equals(" ")){
               response.sendRedirect("login.html");
            } 
            
            int e_id = 0, d_id = 0, other_id = 0, result = 0;
            String type = "TECHNICIAN", dayoff = "dayoff", first = "", second = "", third = "", forth = "", over = "", name = "";
              
            ResultSet r = stmt.executeQuery("SELECT T_ID,D_ID FROM APP.TECHNICIAN WHERE (NAME  = '"+CurrentUser.CurrentName+"' )");
            if (r.next()){
                d_id = r.getInt("D_ID"); 
                e_id = r.getInt("T_ID");
            }
            
            Map< String,Integer> techn =   new HashMap<String,Integer>();
            String type1 = "technician", type2 = "boss", teststring = "", table="";
            table =  table ="<table border='3'  >\n" +"  <thead>\n" +"  <tr>\n" +" <th>Name</th>\n" +"<th>Type</th>\n" +"<th>Message</th>\n" + //"<th id =\"tr_hide\" ></th>"+
                "  </tr>\n"+"</thead><tbody id=\"myTable\">";
            
            r = stmt.executeQuery("SELECT NAME,T_ID FROM APP.TECHNICIAN WHERE D_ID  = "+d_id+"");
            while (r.next()){    
                if (!r.getString("NAME").equals(CurrentUser.CurrentName)){
                    other_id = r.getInt("T_ID");
                    result = Push(r.getString("NAME"), "TECHNICIAN");
                    if (result == 1){
                      table = table.concat("<tr><td id =\"first\" >"+r.getString("NAME")+"   </td><td>Technician</td><td   onClick =\"NewMessage("+e_id+","+other_id+","+1+")\">"+"New message!"+"</td>"
                        + "</tr>");  
                    }else{
                       techn.put(r.getString("NAME"), other_id); 
                    }
                }
            }
          
            r = stmt.executeQuery("SELECT NAME FROM APP.BOSS WHERE B_ID  = "+d_id+" ");
            if (r.next()){
                name = r.getString("NAME");
            }
            
            result = Push(name, "BOSS");
            String show= "View";
            if (result != 0){
                show = "New message!";
            }
            table = table.concat("<tr><td id =\"first\" >"+r.getString("NAME")+"</td><td>Boss</td><td   onClick =\"NewMessage("+e_id+","+d_id+","+2+")\">"+show+"</td>"+ "</tr>");
           
            Iterator it = techn.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                table = table.concat("<tr><td id =\"first\" >"+pair.getKey()+" </td><td>Technician</td><td   onClick =\"NewMessage("+e_id+","+pair.getValue()+","+1+")\">"+"View"+"</td>"
                        + "</tr>");
                it.remove(); // avoids a ConcurrentModificationException
            }
            
            out.write(Html2());
            out.println("<br></br>");
            out.write(
                "<input class=\"form-control\" id=\"myInput\" type=\"text\" placeholder=\"Search..\">\n");
            table = table.concat("<thead>"+   "<tbody>"+"</table>");
            out.println("<br>");
            out.println(table);
            out.println("</body>");
            out.println("</html>");
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
            Logger.getLogger(Chat_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Chat_Technician.class.getName()).log(Level.SEVERE, null, ex);
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
