<%-- 
    Document   : GREN_REBNAME_ID
    Created on : 16-jul-2014, 10:38:20
    Author     : Alfa.sistemas
--%>

<%@page import="java.sql.Statement"%>
<%@page import="lib.Conexion"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ResultSet rs= null;
Connection cx=  Conexion.getConex();
Statement stmt=  null;

stmt= cx.createStatement();
rs=stmt.executeQuery("select  'PAS-' ||lpad(trim(ID_PASOS),6,'0'),trim(ID_PASOS),ID_PASOS  from RHTC_PASOS");

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
            <table>

           <%  while(rs.next()){%>
           <tr>
               <% //stmt.executeQuery("update RHTC_HORARIO set ID_HORARIO='"+rs.getString(1)+"' where trim(ID_HORARIO) ='"+rs.getString(2)+"'");%>
               <td><%//=rs.getString(1)
               out.print("update RHTC_PASOS set ID_PASOS='"+rs.getString(1)+"' where trim(ID_PASOS) ='"+rs.getString(2)+"';");
               %></td>
               
           </tr>
           <%}%>
       </table>
   
    </body>
</html>
