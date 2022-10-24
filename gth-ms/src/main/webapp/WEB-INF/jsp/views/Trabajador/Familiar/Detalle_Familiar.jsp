
<%@page import="com.app.controller.util.DateFormat"%>
<%@page import="com.app.domain.model.Tipo_Documento"%>
<%@page import="com.app.domain.model.V_Ficha_Trab_Num_C"%>
<%    HttpSession sesion_ = request.getSession();
String id_user_ = (String) sesion_.getAttribute("IDUSER");
if (id_user_ != null) {
%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="com.app.domain.model.Padre_Madre_Conyugue"%>
<jsp:useBean id="LISTA_HIJOS" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="ListaridTrabajador" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="Lista_Tipo_Doc" scope="session" class="java.util.ArrayList"/>
<center>
<%
    HttpSession sesion = request.getSession(true);
    String rol = (String) sesion.getAttribute("IDROL");
    for (int q = 0; q < ListaridTrabajador.size(); q++) {
        V_Ficha_Trab_Num_C tr = new V_Ficha_Trab_Num_C();
        tr = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(q);
%>
<form  action=../familiar">
    <div>
        <table class="table table-striped table-bordered table-hover" >
            <tr><td colspan="2"><h4>Datos de Padre y Madre</h4></td></tr>

            <tr><td class="text-info">Nombres y Apellidos del padre:</td><td class="text-info-left"><%if (tr.getAp_nombres_padre() != null) {
                    out.print(tr.getAp_nombres_padre());
                } else {
                    out.print("No registrado");
                }
                    %></td></tr>
            <tr><td class="text-info">Nombres y Apellidos de la Madre :</td><td class="text-info-left"><%if (tr.getAp_nombres_madre() != null) {
                    out.print(tr.getAp_nombres_madre());
                } else {
                    out.print("No registrado");
                }
                    %></td></tr>
                    <%if (tr.getAp_nombres_madre() == null || tr.getAp_nombres_padre() == null) {%>
                    <%  if (rol.trim().equals("ROL-0002") || rol.trim().equals("ROL-0005") || rol.trim().equals("ROL-0001") | rol.trim().equals("ROL-0013")) {%>
            <tr><td colspan="2"> <a class="btn btn-primary" href="Reg_Padres.jsp?idtr=<%=request.getParameter("idtr")%>">Registrar Padres</a></td></tr>
            <%}%>
            <%} else {%>
            <tr><td colspan="2"> <a class="btn btn-primary" href="familiar?opc=Modificar_Padres&idtr=<%=request.getParameter("idtr")%>">Modificar Padres</a></td></tr>
            <%}%>
        </table>
    </div>



    <table  class="table table-striped table-bordered table-hover"  >
        <tr><td colspan="2"><div class="title"><h4>Datos de C�nyuge</h4></div></td></tr>
        <%if (tr.getAp_nombres_c() != null) {
        %>
        <tr><td class="text-info">Trabaja en UPeU:</td><td class="text-info-left">
                <%
                    if (tr.getEs_trabaja_upeu_c().trim().equals("1")) {
                        out.println("Si");%>
                <%if (tr.getId_conyugue() != null) {
                %>
                <%
                    }
                %>
                <%
                    }
                    if (tr.getEs_trabaja_upeu_c().trim().equals("0")) {
                        out.println("No");
                    }
                %>
            </td></tr>
        <tr><td class="text-info">Apellidos y Nombres:</td><td class="text-info-left"><%if (tr.getAp_nombres_c() != null) {
                out.print(tr.getAp_nombres_c());
            } else {
                out.print("No registrado");
            }
                %></td></tr>
        <tr><td class="text-info">Fecha de Nacimiento:</td><td class="text-info-left"><%if (tr.getFe_nac_c() != null) {
                out.print(DateFormat.toFormat5(tr.getFe_nac_c()));
            } else {
                out.print("No registrado");
            }
                %></td></tr>
        <tr ><td class="text-info">Tipo Documento:</td><td class="text-info-left">
                <%if (tr.getId_tipo_doc_c() != null) {
                        for (int h = 0; h < Lista_Tipo_Doc.size(); h++) {
                            Tipo_Documento td = new Tipo_Documento();
                            td = (Tipo_Documento) Lista_Tipo_Doc.get(h);
                            if (tr.getId_tipo_doc_c().trim().equals(td.getId_tipo_doc_ident().trim())) {
                                out.print(td.getDe_tdoc_abreviada());
                            }
                        }
                    } else {
                        out.print("no registrado");
                    }
                %>
            </td></tr>
        <tr><td class="text-info">Nro Documento:</td><td class="text-info-left"><%if (tr.getNu_doc_c() != null) {
                out.println(tr.getNu_doc_c());
            } else {
                out.print("No registrado");
            }
                %></td></tr>
        <tr><td class="text-info">Inscripcion Vigente en EsSalud:</td><td class="text-info-left">
                <%
                    if (tr.getLi_inscripcion_vig_essalud_c() != null) {

                        if (tr.getLi_inscripcion_vig_essalud_c().trim().equals("1")) {

                            out.println("Si");
                        }
                        if (tr.getLi_inscripcion_vig_essalud_c().trim().equals("0")) {

                            out.println("No");
                        }
                    } else {
                        out.println("No registrado");
                    }
                %>
            </td></tr>

        <%} else{%>
        <tr><td colspan="2"> <label>Aun no se ha registrado los datos del c�nyugue</label><br></td></tr>
                <%  if (rol.trim().equals("ROL-0002") || rol.trim().equals("ROL-0005") || rol.trim().equals("ROL-0001") || rol.trim().equals("ROL-0013")) {%>
        <tr><td colspan="2"> <a class="btn btn-success" href=Reg_Conyugue.jsp?idtr=<%=request.getParameter("idtr")%>">Registrar C�nyugue</a></td></tr>
        <%}%>
        <%}%>
        <%//if (tr.getAp_nombres_c() != null && tr.getAp_nombres_madre() != null && tr.getAp_nombres_padre() != null) {%>
        <%if (tr.getAp_nombres_c() != null ) {%>
        <input type="hidden" name="idtra" value="<%=tr.getId_trabajador().trim()%>">
        <input type="hidden" name="opc" value="Editar_Familiar">
        <tr><td colspan="2"><input class="btn btn-primary" type="submit" value="Modificar C�nyugue"></td></tr>
                <%}%>

    </table>
</form>

</center>
<br>
<center>

<%        if (LISTA_HIJOS.size() != 0) {
        //        if (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") || rol.trim().equals("ROL-0001") || rol.trim().equals("ROL-0013")) {
        if (true) {
%>
<div class="listar_hijos"></div>
<a href="Reg_Datos_Hijo.jsp?idtr=<%=request.getParameter("idtr")%>" class="btn btn-primary">Registrar un hijo</a>
<%}%>
<%} else{%>

<span>No se ha registrado ningun Hijo(a)</span><br>

<%
    //if (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") || rol.trim().equals("ROL-0001") || rol.trim().equals("ROL-0013")) {
    if (true) {
%>
<a href="Reg_Datos_Hijo.jsp?idtr=<%=request.getParameter("idtr")%>" class="btn btn-primary">Registrar Hijos</a>
<%}%>
</center>
<%}
}%>

<!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT -->
<!-- Voice command : plugin -->
<script type="text/javascript">
$(document).ready(
        function () {
            pageSetUp();
            $.post("familiar", "opc=Listar_Hijo_id_tr&idtr=<%=request.getParameter("idtr")%>", function (objJson) {
                var texto_html = '';
                var div = $(".listar_hijos");
                if (objJson.rpta == -1) {
                    alert(objJson.mensaje);
                    return;
                } else {
                    var lista = objJson.lista;
                    if (lista.length == 0) {
                    } else {
                        texto_html += ' <table id="datatable_tabletools" class="table table-striped table-bordered table-hover" width="100%" style="margin-left:0.5%;" >'
                                + '<thead><tr><th data-hide="phone">Nro</th><th data-class="expand">Apellidos y Nombres</th><th data-hide="phone">Fecha de nacimiento</th>'
                                + '<th data-hide="phone">Sexo</th><th data-hide="phone,tablet">Tipo de Documento</th><th data-hide="phone,tablet">Nro Documento</th>'
                                + '<th data-hide="phone,tablet">Essalud</th><th data-hide="phone,tablet">Est.Nivel Superior</th><th data-hide="phone,tablet" colspan="2">Opciones</th> </tr></thead>';
                        texto_html += '<tbody>';
                        for (var i = 0; i < lista.length; i++) {
                            texto_html += '<tr>'
                                    + '  <td>' + (i + 1) + '</td>'
                                    + '<td >' + lista[i].ap_p + ' ' + lista[i].ap_m + ' ' + lista[i].no_hijo + '</td>'
                                    + ' <td >' + lista[i].fe_nac + '</td>'
                                    + '<td >' + lista[i].sexo + '</td>'
                                    + '<td>' + lista[i].ti_doc + '</td>'
                                    + '<td >' + lista[i].nu_doc + '</td>'
                                    + '<td >' + lista[i].essalud + '</td>'
                                    + '<td >' + lista[i].superior + '</td>'
                                    + '<td >';
<% if (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") | rol.trim().equals("ROL-0001") | rol.trim().equals("ROL-0013")) {%>

                            texto_html += '<a href = ../familiar?idhijo=' + lista[i].idh + '&idtr=' + lista[i].idtr + '&opc=modificar" class="btn btn-success btn-sm">'
                                    + ' <i class="fa fa-pencil"></i></a>'
                                    + ' <button type="button" class = "btn btn-danger btn-sm eliminar" value=../familiar?idhijo=' + lista[i].idh + '&idtr=' + lista[i].idtr + '&opc=eliminar">'
                                    + ' <i class="fa fa-times"></i> </button>';

<%}%>
                            texto_html += '</td></tr>';
                        }
                        texto_html += '</tbody></table>';
                        div.append(texto_html);
                    }
                }
                $(".eliminar").click(function () {
                    var url = $(this).val();
                    $.SmartMessageBox({
                        title: "Eliminar Hijo!",
                        content: "�Esta seguro que desea eliminar un hijo?",
                        buttons: '[No][Yes]'
                    }, function (ButtonPressed) {
                        if (ButtonPressed === "Yes") {
                            window.location.href = url;

                        }
                        if (ButtonPressed === "No") {
                            return false;
                        }

                    });
                    return false;
                });
            });
        }
);
</script>
<%} else {
    out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
}
%>
