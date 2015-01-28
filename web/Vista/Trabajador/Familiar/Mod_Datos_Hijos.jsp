<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="pe.edu.upeu.application.web.controller.CConversion"%>
<%@page import="pe.edu.upeu.application.model.Datos_Hijo_Trabajador"%>

<jsp:useBean id="Lista_hijo_individual" scope="application" class="java.util.ArrayList"/>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="windows-1252">
        <title></title>
        <link rel="stylesheet" href="../../../css/Css_Formulario/form.css"  type="text/css" > 
    </head>
    <body>
    <center>
        <label class="title">HIJOS</label>
        <form class="form" action="../../../familiar"> 
            <table class="table" >        
                <% 
                for (int i = 0; i < Lista_hijo_individual.size(); i++) {
                        Datos_Hijo_Trabajador d = new Datos_Hijo_Trabajador();
                        d = (Datos_Hijo_Trabajador) Lista_hijo_individual.get(i);
                %>
                <input type="hidden" name="idhijo" value="<%= d.getId_datos_hijos_trabajador()%>">
                <input type="hidden" name="idtr" value="<%= d.getId_trabajador()%>">
                <tr><td>Apellido Paterno:</td><td><input type="text" name="APELLIDO_P" class="text-box" value="<%= d.getAp_paterno()%>" required="" maxlength="30"></td></tr>      
                <tr><td>Apellido Materno:</td><td><input type="text" name="APELLIDO_M" class="text-box"  value="<%= d.getAp_materno()%>" maxlength="30"></td></tr>    
                <tr><td>Nombres:</td><td><input type="text" name="NOMBRE" class="text-box" value="<%= d.getNo_hijo_trabajador()%>" required="" maxlength="30"></td></tr>   
                <tr><td>Fecha de Nacimiento:</td><td><input type="date" name="FECHA_NAC" class="text-box" value="<%= d.getFe_nacimiento()%>" required=""></td></tr>   
                <tr><td>Sexo:</td><td>
                        <select name="SEXO" class="text-box" required="">
                            <%
                                if (d.getEs_sexo().equals("M")) {
                            %>
                            <option value="M" selected="">Masculino</option>
                            <option value="F" >Femennino</option>
                            <%}
                            %>
                            <%
                                if (d.getEs_sexo().equals("F")) {
                            %>
                            <option value="M" >Masculino</option>     
                            <option value="F" selected="">Femennino</option>

                            <%}
                            %>

                        </select> </td> </tr>    
                <tr><td>Tipo de Documento</td><td>
                        <select name="TIPO_DOC_ID" class="text-box" required="">
                            <option value=""></option>
                            <%
                                if (d.getEs_tipo_doc().equals("1")) {
                            %>
                            <option value="1" selected="">DNI</option>
                            <option value="2">Partida</option>
                             <%}
                            %>
                             <%
                                if (d.getEs_tipo_doc().equals("2")) {
                            %>
                             <option value="1">DNI</option>
                             <option value="2" selected="">Partida</option>
                             <%}
                            %>
                        </select>
                    </td></tr>     

                <tr><td>Numero de Documento:</td><td><input type="text" name="NRO_DOC" value="<%= d.getNu_doc()%>" class="text-box" maxlength="20"></td></tr>      
                <!--<tr><td>Presenta documento:</td><td>
                    <select name="PRESENTA_DOCUMENTO" class="text-box">
                        <option value=""></option>
                        <option value="1">Si</option>
                        <option value="2">No</option>
                    </select>
                </td></tr>  -->
                <tr><td>Inscripción Vigente en Essalud:</td><td>
                        <select  name="INSCRIPCION_VIG_ESSALUD" required="" class="text-box" >
                            <option value=""></option>
                             <%
                                if (d.getEs_inscripcion_vig_essalud().equals("1")) {
                            %>
                            <option value="1" selected="">Si</option>
                            <option value="0">No</option>
                             <%}
                            %>
                             <%
                                if (d.getEs_inscripcion_vig_essalud().equals("0")) {
                            %>
                             <option value="1">Si</option>
                             <option value="0" selected="">No</option>
                             <%}
                            %>
                        </select></td></tr> 
 
                
                <tr><td>Estudiante de Nivel Superior:</td><td>
                        <select name="ESTUD_NIV_SUPERIOR" class="text-box">
                            <option  value=""></option>
                             <%
                                if (d.getEs_estudio_niv_superior().equals("1")) {
                            %>
                            <option  value="1" selected="">Si</option>
                            <option  value="0">No</option>
                             <%}
                            %>
                             <%
                                if (d.getEs_estudio_niv_superior().equals("0")) {
                            %>
                            <option  value="1" selected="">Si</option>
                            <option  value="0">No</option>
                             <%}
                            %>
                        </select>
                    </td></tr>  
                    <input type="hidden" name="usrmodi" value="" class="submit">
                <tr><td colspan="2"><input type="submit" name="opc"  class="submit" value="MODIFICAR HIJO"></td></tr>

                <%}%></table></form></center><br><br>

</body>
</html>
<%@include  file="List_Hijo.jsp"%>
<%} else {
        response.sendRedirect("/TALENTO_HUMANO/");
    }
%>