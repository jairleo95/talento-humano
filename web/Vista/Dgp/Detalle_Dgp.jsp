

<%@page import="pe.edu.upeu.application.dao.UsuarioDAO"%>
<%@page import="pe.edu.upeu.application.dao_imp.InterfaceUsuarioDAO"%>
<%@page import="pe.edu.upeu.application.model.Usuario"%>
<%@page import="pe.edu.upeu.application.model.V_Det_DGP"%>
<%@page import="pe.edu.upeu.application.model.X_List_id_dgp"%>
<%@page import="java.util.List"%>
<%@page import="pe.edu.upeu.application.dao.DgpDAO"%>
<%@page import="pe.edu.upeu.application.dao_imp.InterfaceDgpDAO"%>
<jsp:useBean id="LIST_ID_DGP" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="VALIDAR_DGP_CONTR" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="LIST_ID_USER" scope="application" class="java.util.ArrayList"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <link href="../../css/Css_Bootstrap/bootstrap.css" rel="stylesheet">
        <link href="../../css/Css_Bootstrap/bootstrap-theme.min.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="../../css/Css_Detalle/CSS_DETALLE.css">  
        <link rel="stylesheet" type="text/css" media="screen" href="../../HTML_version/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="../../HTML_version/css/font-awesome.min.css">
        <title>Detalle DGP</title>

        <style type="text/css">


            table{
                font-weight:bold ;
                width: 50px;
               font-size: 130%;
               font-color: black;
            }
            
            .text-info{
                text-align: left;
                
            }
            /* div{
                 font-family:verdana;
                 padding:2%;
                 border-radius:10px;
                 border:10px solid #BDCACF;  
                 width: 50%;
                 margin-left: 2%;
                 margin-right: 2%;
                 margin-top: 2%;
             }*/


            body{

                margin-left: auto;
                margin-right: auto  ;
                //width: 45%;
                // margin: 0;
            }
            .titulo{


                background-color: #474747;
                padding: 0.5%;

                font-family: arial;
                font-size: 130%;
                text-align: center;
                color: white;
            }
            .ct{
             text-align: center;   
            }
                
            .header{


                background-color: #474747;
                padding: 0%;

                font-family: arial;
                font-size: 130%;
                text-align: center;
                color: white;

            }

                }
            </style>
        </head>
        <body><center>
            <div >

                <form action="../../dgp">


                    <table class="table table-hover table-striped  table-responsive">
                        <%
                            String iddgp = "";
                            HttpSession sesion = request.getSession(true);
                            String idrol = (String) sesion.getAttribute("IDROL");%>

                        <% for (int i = 0; i < LIST_ID_DGP.size(); i++) {
                                V_Det_DGP d = new V_Det_DGP();
                                d = (V_Det_DGP) LIST_ID_DGP.get(i);
                                iddgp = d.getId_dgp();
                        %>

                        <label style="color: black; //font-family: cursive;"><h2><%=d.getNo_req()%></h2></label>
                        <tr><td class="text-info table-bordered" style="text-align:align;">Fecha Desde:</td><td><%=d.getFe_desde()%></td></tr>
                        <tr ><td class="text-info table-bordered">Fecha Hasta:</td><td><%=d.getFe_hasta()%></td></tr>
                        <tr><td class="text-info table-bordered">Sueldo : S/.</td><td><%=d.getCa_sueldo()%></td></tr>
                        <tr><td class="text-info table-bordered">Bono Alimentario : S/.</td><td><%=d.getCa_bono_alimentario()%></td></tr>
                        <!-- <tr><td class="td-det">Dias de Trabajo:</td><td><?//echo $list[$index][4];?></td></tr>
                         <tr><td class="td-det">Horario:</td><td><?//echo $list[$index][5];?></td></tr>
                        -->
                        <tr><td Class="text-info table-bordered">Puesto:</td><td><%=d.getNo_puesto()%></td></tr>
                        <tr><td class="text-info table-bordered">Horario:</td><td><a href="../../horario?iddgp=<%=d.getId_dgp()%>&opc=Listar ">Ver Horario</a></td></tr>
                        <input type="hidden" name="iddgp" value="<%=d.getId_dgp().trim()%>">
                        <input type="hidden" name="idreq" value="<%=d.getId_requerimiento().trim()%>">
                        </tr>
                        <tr><td class="text-info table-bordered">BEV: </td><td><%=d.getDe_bev()%></td></tr>
                        <tr><td class="text-info table-bordered">Centro de Costos: </td><td><%=d.getCa_centro_costos()%></td></tr>
                        <%if (d.getDe_antecedentes_policiales().equals("1")) {%>
                        <tr><td class="text-info table-bordered">Antecedentes Policiales: </td><td>No</td></tr>
                        <%} else {%>
                        <tr><td class="text-info table-bordered">Antecedentes Policiales: </td><td>Si</td></tr>
                        <%}%> 
                        <tr><td class="text-info table-bordered">Certificado de Salud: </td><td><%=d.getDe_certificado_salud()%></td></tr>
                        <tr style="color: red;"><td class="text-info table-bordered" >Sueldo Total : S/.</td><td><%=(d.getCa_sueldo() + d.getCa_bono_alimentario() + d.getDe_bev())%></td></tr>
                        <tr style="color: red;"><td class="text-info table-bordered" >Documentos Adjuntos : </td><td><a href="../../documento?iddgp=<%=d.getId_dgp().trim()%>&idtr=<%=d.getId_trabajador().trim()%>&opc=Ver_Documento">Ver Documentos</a></td></tr>

                        <%if (d.getNo_banco().equals("0")) {%>
                        <tr><td class="text-info table-bordered">Banco: </td><td>Ninguno</td></tr>
                        <%}
                            if (d.getNo_banco().equals("1")) {%>
                        <tr><td class="text-info table-bordered">Banco: </td><td>BBVA</td></tr>
                        <%}
                            if (d.getNo_banco().equals("2")) {%>
                        <tr><td class="text-info table-bordered">Banco: </td><td>BCP</td></tr>
                        <%}
                            if (d.getNo_banco().equals("3")) {%>
                        <tr><td class="text-info table-bordered">Banco: </td><td>Otros</td></tr>
                        <%}%>

                        <%if (d.getNo_banco_otros() != null) {%>
                        <tr><td class="text-info table-bordered">Nombre Banco</td><td><%=d.getNo_banco_otros()%></td></tr>
                        <%}%>
                        <%if (d.getNu_cuenta() != null) {%>
                        <tr><td class="text-info table-bordered">N� Cuenta: </td><td><%=d.getNu_cuenta()%></td></tr>
                        <%}%>
                        <%if (d.getNu_cuenta_banc() != null) {%>
                        <tr><td class="text-info table-bordered">N� Cuenta Bancaria: </td><td><%=d.getNu_cuenta_banc()%></td></tr>
                        <%}%>
                        <%if (d.getEs_gen_nu_cuenta().trim().equals("1")) {%>
                        <tr><td class="text-info table-bordered">N� Cuenta Generado por RRHH </td><td>Si</td></tr>
                        <%} else {%>
                        <tr><td class="text-info table-bordered">N� Cuenta Generado por RRHH </td><td>No</td></tr>
                        <%}%>
                        <!--<tr><td class="td-det">Ruc:</td><td><?echo $list[$index][9];?></td></tr>
                        <tr><td class="td-det">Lugar de Servicio:</td><td><?echo $list[$index][10];?></td></tr>
                        <tr><td class="td-det">Periodo de Pago:</td><td><?echo $list[$index][12];?></td></tr>
                        <tr><td class="td-det">Horario de Capacitaci�n:</td><td><?echo $list[$index][15];?></td></tr>
                        <tr><td class="td-det">Horario de Refrigerio:</td><td><?echo $list[$index][16];?></td></tr>
                        <tr><td class="td-det">Dias Capacitaci�n:</td><td><?echo $list[$index][17];?></td></tr>
                        <tr><td class="td-det">Subvenci�n:</td><td><?echo $list[$index][14];?></td></tr>
                        ->
                        
           
                        <!--<tr><td colspan="2"></td><td><input class="btn btn-success" style="width: 100px" type="submit" value="Editar"></td>
                        -->               



                        <%
                            String num = request.getParameter("num");
                            if (d.getEs_dgp() != null) {
                                if (num.equals("0") & d.getEs_dgp().equals("0") & idrol.trim().equals("ROL-0006")) {

                        %>

                        <td  colspan="2" class="ct"><a href="../../contrato?iddgp=<%=d.getId_dgp().trim()%>&idtr=<%=d.getId_trabajador().trim()%>&opc=enviar">Hacer Contrato</a></td></tr>
                        <%}
                            if (d.getEs_dgp().equals("1") & num.equals("0") & !"ROL-0006".equals(idrol.trim())) {%>
                    <td colspan="2" class="ct"><a href="">Ver Contrato</a></td></tr>
                        <%}
                            }%>

                        <tr style="color: red;">
                            <% InterfaceUsuarioDAO us = new UsuarioDAO();
                                Usuario u = new Usuario();
                                if (d.getUs_modif() != null) { %>
                            <td class="text-info table-bordered">Modificado por:</td>
                            <td><%
                                for (int j = 0; j < us.List_ID_User(d.getUs_modif()).size(); j++) {

                                    u = (Usuario) LIST_ID_USER.get(j);
                                    out.print(u.getId_rol());
                                }%>
                            </td>
                            <%}%>

                            <% if (d.getUs_creacion() != null) {%>
                            <td class="text-info table-bordered">Creado por:</td><td><%
                                //$list_us=$mdu->LIST_ID_USER($list[$index][19]);
                                for (int k = 0; k < us.List_ID_User(d.getUs_creacion()).size(); k++) {
                                    out.print(u.getId_rol());
                                }
                                %></td>
                                <%}%>
                        </tr>
                        <%}%> 






                        <input type="hidden" name="idtr" value="<%=request.getParameter("idtr")%>">
                        <input type="hidden" name="opc" value="MODIFICAR REQUERIMIENTO">   
                        <% if (idrol.trim().equals("ROL-0002") | idrol.trim().equals("ROL-0005") | idrol.trim().equals("ROL-0003")) { %>
                        <tr><td><input type="submit"  value="Modificar"></td><td></td></tr><%}%>
                </form>

                <% if (request.getParameter("opc") != null) {
                        if (request.getParameter("opc").equals("reg_doc")) {

                %>

                <tr><td colspan="2"><h3 style="text-align: center;">Enviar Requerimiento</h3> 
                        <form action="../../dgp" method="get">
                            <input  type="hidden" value="<%=iddgp%>" name="iddgp">
                            <input type="hidden" value="Terminar" name="opc">
                            <footer>
                                <button type="submit" class="btn btn-success">
                                    TERMINAR
                                </button>
                            </footer>
                        </form></td></tr>

                <%}
                    }%>
            </div>
        </center>
    </body>
</html>
