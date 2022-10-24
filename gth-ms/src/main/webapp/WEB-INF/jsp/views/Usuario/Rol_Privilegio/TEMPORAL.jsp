<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%-- 
    Document   : TEMPORAL
    Created on : 13-nov-2014, 20:54:31
    Author     : joserodrigo
--%>

<a href="Roles?opc=mat_rol" class="btn btn-primary btn-xl">Mantenimiento roles</a>
<a href="Privilegio?opc=Listar_Privilegio" class="btn btn-primary btn-xl">Mantenimiento Privilegios</a>
<a href="Privilegio?opc=Otorgar" class="btn btn-primary btn-xl">Otorgar Roles</a>
<!--<li ><a href="Mantenimiento_Privilegio.jsp">Otorgar Roles</a> </li>-->

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>