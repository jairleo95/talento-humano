<%@page import="pe.edu.upeu.application.model.Tipo_Institucion"%>
<%@page import="pe.edu.upeu.application.model.Universidad"%>
<%@page import="pe.edu.upeu.application.model.Cuenta_Sueldo"%>
<%@page import="pe.edu.upeu.application.dao.Carrera_UniversidadDAO"%>
<%@page import="pe.edu.upeu.application.dao_imp.InterfaceCarrera_UniversidadDAO"%>
<%@page import="pe.edu.upeu.application.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="pe.edu.upeu.application.dao.ListaDAO"%>
<%@page import="pe.edu.upeu.application.dao_imp.InterfaceListaDAO"%>
<%@page import="pe.edu.upeu.application.model.V_Ficha_Trab_Num_C"%>
<%@page import="pe.edu.upeu.application.model.Trabajador"%>
<%@page import="pe.edu.upeu.application.model.Trabajador"%>
<jsp:useBean id="ListaridTrabajador" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="List_Cuenta_Sueldo" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="List_Universidad" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="List_tipo_institucion" scope="application" class="java.util.ArrayList"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link rel="stylesheet" type="text/css" media="screen" href="../../HTML_version/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="../../HTML_version/css/font-awesome.min.css">
        <script src=" ../../../../js1/jquery-1.11.1.min.js" type="text/javascript"></script>
        <title></title>

    </head>
    <body>


        <form align="center">
            <table class="table table-striped table-hover table-responsive">
                <%
                    InterfaceCarrera_UniversidadDAO icu = new Carrera_UniversidadDAO();
                    for (int index = 0; index < ListaridTrabajador.size(); index++) {
                        V_Ficha_Trab_Num_C trb = new V_Ficha_Trab_Num_C();
                        trb = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(index);

                %>   
                <tr><td class="text-info">Situacion Educativa:</td><td><%out.println(trb.getNo_s_educativa());%></td></tr>
                <tr><td class="text-info">I.E.Peruana:</td>
                    <td>
                        <%if (trb.getEs_inst_educ_peru() == null) {
                                out.print("no registrado");
                            } else {
                                if (trb.getEs_inst_educ_peru().trim().equals("1")) {
                                    out.print("Si");
                                } else {
                                    out.print("No");
                                }
                            }%>
                    </td>
                </tr>
                <tr><td class="text-info">Regimen.I.E:</td><td>
                        <%if (trb.getLi_reg_inst_educativa() == null) {
                                out.print("no registrado");
                            } else {
                                if (trb.getLi_reg_inst_educativa().trim().equals("1")) {
                                    out.print("P�blica");
                                } else {
                                    out.print("Privada");
                                }
                            }%>
                    </td></tr>
                <tr><td class="text-info">Tipo.I.E:</td>
                    <% if (trb.getNo_universidad() != null)
                            for (int y = 0; y < List_Universidad.size(); y++) {
                                Universidad un = new Universidad();
                                un = (Universidad) List_Universidad.get(y);
                                if (trb.getNo_universidad().trim().equals(un.getNo_universidad().trim())) {

                                    for (int r = 0; r < List_tipo_institucion.size(); r++) {
                                        Tipo_Institucion ti = new Tipo_Institucion();
                                        ti = (Tipo_Institucion) List_tipo_institucion.get(r);

                                        if (un.getId_tipo_institucion().trim().equals(ti.getId_tipo_institucion().trim())) {
                    %>
                    <td><%=ti.getNo_tipo_institucion()%></td>
                <%
                                    }
                                }
                                List_tipo_institucion.clear();
                            }
                        }

                %>
                </tr>
                <tr><td class="text-info">Centro de Estudios:</td>
                    <%if (trb.getNo_universidad() != null) {
                            for (int w = 0; w < List_Universidad.size(); w++) {
                                Universidad u = new Universidad();
                                u = (Universidad) List_Universidad.get(w);
                                if (u.getNo_universidad().trim().equals(trb.getNo_universidad().trim())) {
                    %>
                    <td><%=u.getNo_universidad()%></td>
                    <%}
                            }
                        }
                        List_Universidad.clear(); %>
                </tr>
                <!--<tr><td class="text-info">Grado Ac�demico:</td><td><%%></td></tr>-->
                <tr><td class="text-info">Carrera:</td><td><%if (trb.getNo_carrera() == null) {
                        out.print("NINGUNA");
                    } else {
                        out.print(trb.getNo_carrera());
                    }%></td></tr>
                <!--<tr><td class="text-info">Titulo Profesional:</td><td><%%></td></tr>-->
                <tr><td class="text-info">C�digo Universitario:</td><td><%if (trb.getCo_universitario() == null) {
                        out.print("no registrado");
                    } else {
                        out.print(trb.getCo_universitario());
                    }%></td></tr>
                <tr><td class="text-info">Tipo de Hora Referencial:</td><td><%=trb.getCa_tipo_hora_pago_refeerencial()%></td></tr>
                <tr><td class="text-info">A�o Egreso:</td><td><%if (trb.getDe_anno_egreso() == null) {
                        out.print("no registrado");
                    } else {
                        out.print(trb.getDe_anno_egreso());
                    }%></td></tr>
                <tr><td class="text-info">Otros Estudios:</td><td><%if (trb.getCm_otros_estudios() == null) {
                        out.print("NINGUNA");
                    } else {
                        out.print(trb.getCm_otros_estudios());
                    }%></td></tr>
                <tr><td class="text-info" colspan="2" >CUENTA SUELDO</td></tr>
                <%if (List_Cuenta_Sueldo.size() > 0) {%>
                <tr><td class="text-info" colspan="2" >CUENTA SUELDO</td></tr>
                <%for (int k = 0; k < List_Cuenta_Sueldo.size(); k++) {
                        Cuenta_Sueldo cs = new Cuenta_Sueldo();
                        cs = (Cuenta_Sueldo) List_Cuenta_Sueldo.get(k);
                %>
                <tr><td class="text-info">Banco:</td><td><%if (cs.getNo_banco() == null) {
                        out.print("no registrado");
                    } else {
                        out.print(cs.getNo_banco());
                    }%></td></tr>
                <tr><td class="text-info">Nro de Cuenta:</td><td><%if (cs.getNu_cuenta() == null) {
                        out.print("no registrado");
                    } else {
                        out.print(cs.getNu_cuenta());
                    }%></td></td></tr>
                <tr><td class="text-info">Nro de Cuenta Bancaria:</td><td><%if (cs.getNu_cuenta_banc() == null) {
                        out.print("no registrado");
                    } else {
                        out.print(cs.getNu_cuenta_banc());
                    }%></td></td></tr>
                <tr><td class="text-info">Genero Nro de Cuenta Bancaria:</td><td></td></tr>
                <tr>
                    <%}
                    } else {%>
                <tr><td class="text-info" colspan="2" style="color: black;" >no registrado</td></tr>
                <%}%>
                <%String IDTR = trb.getId_trabajador();%>
                <td colspan="2"><a class="btn btn-success" href="../../trabajador?opc=Editar_Asp_Acad&idtr=<%=IDTR%>">EDITAR</a></td>
                </tr>
                <%}%>
                <!-- <tr><td colspan="2"><input class=""  type="submit" value="Editar"></td></tr>-->
            </table>
        </form>

    </body>
</html>
<%} else {
        response.sendRedirect("/TALENTO_HUMANO/");
    }
%>