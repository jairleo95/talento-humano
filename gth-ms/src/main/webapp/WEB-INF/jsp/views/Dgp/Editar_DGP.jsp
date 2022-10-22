
<%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.controller.util.DateFormat"%>
<%@page import="com.app.domain.model.V_Det_DGP"%>
<%@page import="com.app.domain.model.Cuenta_Sueldo"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {

%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.When"%>
<%@page import="com.app.domain.model.V_Ficha_Trab_Num_C"%>
<%@page import="com.app.domain.model.Requerimiento"%>
<%@page import="com.app.domain.model.V_Puesto_Direccion"%>
<%@page import="com.app.domain.model.Puesto"%>
<%@page import="com.app.domain.model.Trabajador"%>
<jsp:useBean id="Listar_Trabajador_id" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="LIST_ID_DGP" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Puesto" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Det_Puesto" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="Listar_Requerimiento" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="list_Cuenta_Sueldo" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="fecha_maxima_plazo" scope="session" class="java.lang.String"/>


<link rel="stylesheet" href="css/chosen.css"  type="text/css" >
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="css/businessLogic/Dgp/editDGP.css" rel="stylesheet" type="text/css"/>


<div id="main" role="main" style="margin: 0px;">


    <div id="content">
        <section id="widget-grid" class="">
            <div class="row">
                <form id="checkout-form" action=dgp"  novalidate="novalidate">
                    <!-- NEW COL START -->
                    <article class="col-sm-12 col-md-12 col-lg-6">

                        <div id="alerta_dgp">
                        </div>
                        <!-- Widget ID (each widget will need unique ID)-->
                        <div class="jarviswidget" id="wid-id-2" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-custombutton="false">
                            <!-- widget options:
                            usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

                            data-widget-colorbutton="false"
                            data-widget-editbutton="false"
                            data-widget-togglebutton="false"
                            data-widget-deletebutton="false"
                            data-widget-fullscreenbutton="false"
                            data-widget-custombutton="false"
                            data-widget-collapsed="true"
                            data-widget-sortable="false"

                            -->
                            <header>
                                <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                                <h2>Editar Requerimiento</h2>

                            </header>

                            <!-- widget div-->
                            <div>

                                <!-- widget edit box -->
                                <div class="jarviswidget-editbox">
                                    <!-- This area used as dropdown edit box -->

                                </div>
                                <!-- end widget edit box -->

                                <!-- widget content -->
                                <div class="widget-body no-padding">

                                    <div class="smart-form" id="form_dgp"  method="post" action=dgp">
                                        <header class="titulo_req">

                                            <div class="spacing">
                                                <center><h1 class="spacing" style="font-weight: bold; margin: 0px;  color: #005cac;"> Documento de Gesti�n de Personal</h1></center>
                                                <br>

                                            </div>
                                            <br>

                                        </header>
                                        <%for (int a = 0; a < LIST_ID_DGP.size(); a++) {
                                                V_Det_DGP dg = new V_Det_DGP();
                                                dg = (V_Det_DGP) LIST_ID_DGP.get(a);
                                        %>
                                        <fieldset>
                                            <%                                                        /*Temporal*/
                                                for (int i = 0; i < Listar_Trabajador_id.size(); i++) {
                                                    V_Ficha_Trab_Num_C tr = new V_Ficha_Trab_Num_C();
                                                    tr = (V_Ficha_Trab_Num_C) Listar_Trabajador_id.get(i);
                                            %>
                                            <div class="row">
                                                <section class="col col-6">
                                                    <label class="label" id="titu">Trabajador :</label>
                                                    <label class="input" style="color: red; font-weight: bold;">
                                                        <%=tr.getAp_paterno() + " " + tr.getAp_materno() + " " + tr.getNo_trabajador()%>
                                                        <input type="hidden" value="<%=tr.getId_trabajador()%>" name="IDDATOS_TRABAJADOR" class="id_tr input-xs idtr">
                                                        <%}%>
                                                    </label>
                                                </section>
                                            </div>
                                            <div class="row" >
                                                <section class="col col-4">
                                                    <label class="select" id="titu">
                                                        Motivo :<select name="MOTIVO" class="ant_policiales" required="" >
                                                            <option value="" >[Seleccione]</option>
                                                            <%if (dg.getLi_motivo().equals("1")) {%>
                                                            <option value="1" selected="">Trabajdor Nuevo</option>
                                                            <option value="2">Renovaci�n</option>
                                                            <%}
                                                                if (dg.getLi_motivo().equals("2")) {%>
                                                            <option value="1">Trabajdor Nuevo</option>
                                                            <option value="2" selected="">Renovaci�n</option>
                                                            <%}%>
                                                        </select>
                                                    </label>

                                                </section>
                                                <section class="col col-2" id="titu">MFL:
                                                    <label class="toggle" >
                                                        <%if (dg.getEs_mfl().trim().equals("0")) {%>
                                                        <input type="checkbox" value="1"  name="MFL" name="checkbox-toggle" >
                                                        <%}
                                                            if (dg.getEs_mfl().trim().equals("1")) {%>
                                                        <input type="checkbox" value="1"  name="MFL" name="checkbox-toggle" checked="">
                                                        <%}%>
                                                        <i data-swchon-text="SI" data-swchoff-text="NO"></i>
                                                    </label>
                                                </section>

                                                <section class="col col-2" id="titu">Presupuestado:
                                                    <label class="toggle"  >
                                                        <%if (dg.getEs_presupuestado().trim().equals("0")) {%>
                                                        <input type="checkbox" value="1"  name="ES_PRESUPUESTADO" name="checkbox-toggle" >
                                                        <%}
                                                            if (dg.getEs_presupuestado().trim().equals("1")) {%>
                                                        <input type="checkbox" value="1"  name="ES_PRESUPUESTADO" name="checkbox-toggle" checked="">
                                                        <%}%>
                                                        <i data-swchon-text="SI" data-swchoff-text="NO"></i>
                                                    </label>
                                                </section>
                                                <div class="div_input_diezmo col col-4">
                                                </div>
                                            </div>
                                            <div class="row">
                                                <section class="col col-4" >
                                                    <label class="label" id="titu"> Area:</label>
                                                    <label class="select">
                                                        <select data-placeholder="Seleccionar Area"  class="select-area"required="" >
                                                            <option value="">[Seleccione]</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-4">
                                                    <label class="label" id="titu"> Secci�n:</label>
                                                    <label class="select">
                                                        <select data-placeholder="Seleccionar Secci�n"  class="select-seccion"required="" >
                                                            <option value="">[Seleccione]</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-4">
                                                    <label class="label" id="titu"> Puesto:</label>
                                                    <label class="select">
                                                        <select data-placeholder="Seleccionar Puesto"  class="select-puesto" required="" name="IDPUESTO" >
                                                            <option value="">[Seleccione]</option>
                                                        </select>
                                                    </label>
                                                </section>
                                            </div>
                                            <section>
                                                <label class="label" id="titu">Requerimiento :</label>
                                                <label class="select">
                                                    <select name="IDREQUERIMIENTO"    disabled="" onchange="mostrarEditHorario()"  id="nom_req"  >
                                                        <option value="">[SELECCIONAR]</option>
                                                        <%
                                                            for (int index = 0; index < Listar_Requerimiento.size(); index++) {
                                                                Requerimiento r = new Requerimiento();
                                                                r = (Requerimiento) Listar_Requerimiento.get(index);
                                                                if (dg.getId_requerimiento().trim().equals(r.getId_requerimiento().trim())) {
                                                        %>
                                                        <option value="<%=r.getId_requerimiento()%>" selected=""  ><%=r.getNo_req()%></option>
                                                        <%} else {%>
                                                        <option value="<%=r.getId_requerimiento()%>"><%=r.getNo_req()%></option>
                                                        <%}
                                                            }%>
                                                    </select>
                                                </label>
                                            </section>
                                            <div class="info_1">
                                            </div>

                                            <div class="row">
                                                <section class="col col-6" >
                                                    <label class="input" id="titu">Fecha de Inicio :
                                                        <input type="text" name="FEC_DESDE"  required=""
                                                               class="frompicker"
                                                               value="<%=(dg.getFe_desde())%>" data-mask="99/99/9999" data-mask-placeholder= "_">
                                                    </label>
                                                </section>
                                                <section class="col col-6">
                                                    <label class="input"  id="titu">Fecha de Cese :
                                                        <input type="text" name="FEC_HASTA"  required=""  value="<%=(dg.getFe_hasta())%>" class="topicker"
                                                               data-mask="99/99/9999" data-mask-placeholder= "_">
                                                    </label>
                                                </section>
                                            </div>
                                            <%if (dg.getId_requerimiento().trim().equals("REG-0008")) {%>
                                            <%String es_cue_sue = request.getParameter("es_cs");%>
                                            <input type="hidden" name="ESTADO" value="<%=es_cue_sue%>">
                                            <%if (es_cue_sue.equals("0")) {%>
                                            <input type="hidden" name="ES_CUENTA_SUELDO" value="1" required="" />
                                            <div class="row">
                                                <section class="col col-3" name="">
                                                    <label class="select" id="titu">Cta Sueldo - Banco:
                                                        <select name="BANCO" id="banco" required="">
                                                            <option value="" selected="" disabled="" >[Selecione]</option>
                                                            <option value="0" >Ninguno</option>
                                                            <option value="1" >BBVA</option>
                                                            <option value="2" >BCP</option>
                                                            <option value="3" >Otros</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-3" id="no_cuen_otros">

                                                    <label class="input" id="titu">Nombre Banco :
                                                        <input type="text" name="BANCO_OTROS"  id="nu_cuen_otros" maxlength="30"   />
                                                    </label>

                                                </section>
                                                <section class="col col-4" id="no_cuen">

                                                    <label class="input" id="titu">Nro Cuenta :
                                                        <input type="text" name="CUENTA"  id="nu_cuen" maxlength="30" />
                                                    </label>

                                                </section>
                                                <section class="col col-4"  id="no_cuen_ban">

                                                    <label class="input" id="titu">Nro Cuenta Bancaria:
                                                        <input type="text" name="CUENTA_BANC" id="nu_cuen_ban">
                                                    </label>

                                                </section>

                                                <section class="col col-6" id="generar">
                                                    <p style="font-weight:bold;">Autorizo a la UPeU gestionar mi cuenta de sueldo en el BBVA Banco Continental, para tal efecto adjunto copia legible y vigente de mi DNI   </p>
                                                    <label class="checkbox" >
                                                        <input type="checkbox" name="GEN_NU_CUEN" id="subscription"  value="1" >
                                                        <i></i>Generar Nro de Cuenta Bancaria</label>
                                                </section>

                                            </div>
                                            <%} else { %>
                                            <%for (int i = 0; i < list_Cuenta_Sueldo.size(); i++) {
                                                    Cuenta_Sueldo cs = new Cuenta_Sueldo();
                                                    cs = (Cuenta_Sueldo) list_Cuenta_Sueldo.get(i);
                                            %>
                                            <div class="row">
                                                <section class="col col-3" name="">
                                                    <label class="select" id="titu" >Cta Sueldo - Banco:
                                                        <select name="BANCO"  required="" disabled="">
                                                            <%if (cs.getNo_banco().equals("0")) {%>
                                                            <option >Ninguno</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("1")) {%>
                                                            <option >BBVA</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("2")) {%>
                                                            <option >BCP</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("3")) {%>
                                                            <option >Otros</option>
                                                            <%}%>
                                                        </select>
                                                    </label>
                                                </section>
                                                <%if (cs.getNo_banco_otros() != null) {%>
                                                <section class="col col-3">
                                                    <label class="input" id="titu">Nombre Banco :
                                                        <input type="text" disabled="" value="<%=cs.getNo_banco_otros()%>" />
                                                    </label>
                                                </section>
                                                <%}
                                                    if (cs.getNu_cuenta() != null) {%>
                                                <section class="col col-4">
                                                    <label class="input" id="titu">Nro Cuenta :
                                                        <input type="text" disabled="" value="<%=cs.getNu_cuenta()%>" />
                                                    </label>
                                                </section>
                                                <%}
                                                    if (cs.getNu_cuenta_banc() != null) {%>
                                                <section class="col col-4">
                                                    <label class="input" id="titu">Nro Cuenta Bancaria:
                                                        <input type="text" disabled="" value="<%=cs.getNu_cuenta_banc()%>">
                                                    </label>
                                                </section>
                                                <%}
                                                    if (cs.getNo_banco().equals("0")) {%>
                                                <section class="col col-5" >

                                                    <p >Autorizo a la UPeU gestionar mi cuenta de sueldo en el BBVA Banco Continental, para tal efecto adjunto copia legible y vigente de mi DNI   </p>
                                                    <label class="checkbox" >
                                                        <%if (cs.getEs_gem_nu_cuenta().equals("1")) {%>
                                                        <input type="checkbox" name="GEN_NU_CUEN"  id="subscription"  value="1" checked="" disabled="">
                                                        <%} else {%>
                                                        <input type="checkbox" name="GEN_NU_CUEN" id="subscription"  value="0" disabled="">
                                                        <%}%>
                                                        <i></i>Generar Nro de Cuenta Bancaria</label>
                                                </section>
                                                <%}%>
                                            </div>
                                            <%}
                                                }%>
                                            <%}%>
                                            <div class="row">
                                                <section class="col col-3" >
                                                    <label class="input" id="titu">Sueldo :
                                                        <input type="text" name="SUELDO" required="" maxlength="13" value="<%=dg.getCa_sueldo()%>"  id="sueldo" >
                                                    </label>
                                                </section>
                                                <%if (dg.getId_requerimiento().trim().equals("REQ-0001") || dg.getId_requerimiento().trim().equals("REQ-0002") || dg.getId_requerimiento().trim().equals("REQ-0003") || dg.getId_requerimiento().trim().equals("REQ-0005")) {
                                                %>
                                                <section class="col col-3">
                                                    <label class="input"  id="titu">
                                                        Bono de Alimentos :<input type="text" maxlength="13"  value="<%=dg.getCa_bono_alimentario()%>" name="BONO_ALIMENTARIO"  id="bono_al">
                                                    </label>
                                                </section>
                                                <section class="col col-3">
                                                    <label class="input"  id="titu">
                                                        Bonificaion Puesto :<input type="text" maxlength="13"  value="<%=dg.getCa_bonificacion_p()%>" name="BONO_PUESTO"  id="bono_pu">
                                                    </label>
                                                </section>
                                                <section class="col col-3">
                                                    <label class="input"  id="titu">
                                                        BEV :<input type="text" name="BEV" maxlength="13" value="<%=dg.getDe_bev()%>" id="bev">
                                                    </label>
                                                </section>
                                                <section class="col col-3">
                                                    <label class="input"  id="titu">
                                                        Sueldo Total :<div id="suel_total" style="color: red;">0.0</div>
                                                    </label>
                                                </section>
                                            </div>
                                            <div  class="row" >
                                                <section class="col col-4">
                                                    <label class="select" id="titu">
                                                        Antecedentes Policiales :<select name="ANTECEDENTES_POLICIALES" class="ant_policiales" >
                                                            <option value="" >[Seleccione]</option>
                                                            <%if (dg.getDe_antecedentes_policiales() != null) {
                                                                    if (dg.getDe_antecedentes_policiales().equals("1")) {
                                                            %>
                                                            <option value="1" selected="">No</option>
                                                            <option value="2">Si</option>
                                                            <%}
                                                                if (dg.getDe_antecedentes_policiales().equals("2")) {%>
                                                            <option value="1" >No</option>
                                                            <option value="2" selected="">Si</option>
                                                            <%}
                                                            } else {%>
                                                            <option value="1" >No</option>
                                                            <option value="2">Si</option>
                                                            <%}%>
                                                        </select>
                                                    </label>

                                                </section>
                                                <section class="col col-4">

                                                    <label class="select" id="titu">
                                                        Certificado de Salud:
                                                        <select name="CERTIFICADO_SALUD" required=""  class="essalud">
                                                            <option value="">[Seleccione]</option>
                                                            <%if (dg.getEs_certificado_salud() != null) {
                                                                    if (dg.getEs_certificado_salud().equals("1")) {
                                                            %>
                                                            <option value="1" selected="" >Si</option>
                                                            <option value="0">No</option>
                                                            <%}
                                                                if (dg.getEs_certificado_salud().equals("0")) {%>
                                                            <option value="1">Si</option>
                                                            <option selected="" value="0">No</option>
                                                            <%}
                                                            } else {%>
                                                            <option value="1" >Si</option>
                                                            <option value="0">No</option>
                                                            <%}%>
                                                        </select>
                                                    </label>
                                                </section>
                                            </div>
                                            <%String es_cue_sue = request.getParameter("es_cs");%>
                                            <input type="hidden" name="ESTADO" value="<%=es_cue_sue%>">
                                            <%if (es_cue_sue.trim().equals("0")) {%>
                                            <input type="hidden" name="ES_CUENTA_SUELDO" value="1" />
                                            <div class="row">
                                                <section class="col col-3" name="">
                                                    <label class="select" id="titu">Cta Sueldo - Banco:
                                                        <select name="BANCO" id="banco" required="">
                                                            <option value="" selected="" disabled="" >[Selecione]</option>
                                                            <option value="0" >Ninguno</option>
                                                            <option value="1" >BBVA</option>
                                                            <option value="2" >BCP</option>
                                                            <option value="3" >Otros</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-3" id="no_cuen_otros">

                                                    <label class="input" id="titu">Nombre Banco :
                                                        <input type="text" name="BANCO_OTROS"  id="nu_cuen_otros" maxlength="30"   />
                                                    </label>

                                                </section>
                                                <section class="col col-4" id="no_cuen">

                                                    <label class="input" id="titu">Nro Cuenta :
                                                        <input type="text" name="CUENTA"  id="nu_cuen" maxlength="30" />
                                                    </label>

                                                </section>
                                                <section class="col col-4"  id="no_cuen_ban">

                                                    <label class="input" id="titu">Nro Cuenta Bancaria:
                                                        <input type="text" name="CUENTA_BANC" id="nu_cuen_ban">
                                                    </label>

                                                </section>

                                                <section class="col col-6" id="generar">
                                                    <p style="font-weight:bold;">Autorizo a la UPeU gestionar mi cuenta de sueldo en el BBVA Banco Continental, para tal efecto adjunto copia legible y vigente de mi DNI   </p>
                                                    <label class="checkbox" >
                                                        <input type="checkbox" name="GEN_NU_CUEN" id="subscription"  value="1" >
                                                        <i></i>Generar Nro de Cuenta Bancaria</label>
                                                </section>

                                            </div>
                                            <%} else {%>
                                            <%for (int i = 0; i < list_Cuenta_Sueldo.size(); i++) {
                                                    Cuenta_Sueldo cs = new Cuenta_Sueldo();
                                                    cs = (Cuenta_Sueldo) list_Cuenta_Sueldo.get(i);

                                            %>
                                            <div class="row">

                                                <section class="col col-3" name="">
                                                    <label class="select" id="titu" >Cta Sueldo - Banco:
                                                        <select name="BANCO"  required="" disabled="">
                                                            <%if (cs.getNo_banco().equals("0")) { %>
                                                            <option >Ninguno</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("1")) {%>
                                                            <option >BBVA</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("2")) { %>
                                                            <option >BCP</option>
                                                            <%}
                                                                if (cs.getNo_banco().equals("3")) { %>
                                                            <option >Otros</option>
                                                            <% }%>
                                                        </select>
                                                    </label>
                                                </section>
                                                <%if (cs.getNo_banco_otros() != null) {%>
                                                <section class="col col-3">
                                                    <label class="input" id="titu">Nombre Banco :
                                                        <input type="text" disabled="" value="<%=cs.getNo_banco_otros()%>"   />
                                                    </label>
                                                </section>
                                                <%}%>
                                                <%if (cs.getNu_cuenta() != null) {%>
                                                <section class="col col-4">
                                                    <label class="input" id="titu">Nro Cuenta :
                                                        <input type="text" disabled="" value="<%=cs.getNu_cuenta()%>"    />
                                                    </label>
                                                </section>
                                                <%}%>
                                                <%if (cs.getNu_cuenta_banc() != null) {%>
                                                <section class="col col-4">
                                                    <label class="input" id="titu">Nro Cuenta Bancaria:
                                                        <input type="text" disabled="" value="<%=cs.getNu_cuenta_banc()%>">
                                                    </label>
                                                </section>
                                                <%}
                                                    if (cs.getNo_banco().trim().equals("0")) {%>%>
                                                <section class="col col-5" >
                                                    <p >Autorizo a la UPeU gestionar mi cuenta de sueldo en el BBVA Banco Continental, para tal efecto adjunto copia legible y vigente de mi DNI   </p>
                                                    <label class="checkbox" >
                                                        <%if (cs.getEs_gem_nu_cuenta().trim().equals("1")) {%>
                                                        <input type="checkbox" name="GEN_NU_CUEN"  id="subscription"  value="1" checked="" disabled="">
                                                        <%} else {%>
                                                        <input type="checkbox" name="GEN_NU_CUEN" id="subscription"  value="0" disabled="">
                                                        <%}%>
                                                        <i></i>Generar Nro de Cuenta Bancaria</label>
                                                </section>
                                                <%}%>
                                            </div>
                                            <%}
                                                    }
                                                }%>
                                            <%if (dg.getId_requerimiento().equals("REQ-0010")) {%>
                                            <div class="">
                                                <section class="col col-4" >
                                                    <label class="input" id="titu"> RUC:
                                                        <input type="text" name="RUC" id="" maxlength="20" required="" >
                                                    </label>

                                                </section>
                                            </div>
                                            <div class="">
                                                <section class="col col-6" >
                                                    <label class="input" id="titu"> Domicilio Fiscal:
                                                        <input type="text" name="DOMICILIO_FISCAL" id="" required="" >
                                                    </label>
                                                </section>
                                                <%}%>
                                                <%if (dg.getId_requerimiento().equals("REQ-0010") || dg.getId_requerimiento().equals("REQ-0011")) {%>
                                                <section class="col col-6" >
                                                    <label class="input" id="titu"> Lugar de Servicio:
                                                        <input type="text" name="LUGAR_SERVICIO" id="" required="" value="<%=dg.getDe_lugar_servicio()%>">
                                                    </label>
                                                </section>
                                                <section class="col col-lg-12" >
                                                    <label class="textarea" id="titu" >Descripcion del Servicio
                                                        <textarea rows=4 name="DESCRIPCION_SERVICIO" value="<%=dg.getDe_servicio()%>"></textarea>
                                                    </label>
                                                </section>
                                            </div>
                                            <div class="pago_cuotas_1">
                                                <section class="col col-2">
                                                    <a type="button" class="btn btn-default btn-lg" id="btn_add" >Agregar</a>
                                                </section>
                                                <section class="col col-2" >
                                                    <label class="input" id="titu"> CUOTA:
                                                        <input type="text" name="CUOTA_1" id="cuota" required="" value="1" >
                                                    </label>
                                                </section>
                                                <section class="col col-4" >
                                                    <label class="input" id="titu">Fecha a Pagar :
                                                        <input type="date" name="FEC_PAGAR_1" id="datepicker" required="" >
                                                    </label>
                                                </section>
                                                <section class="col col-4" >
                                                    <label class="input" id="titu">Monto :
                                                        <input type="text" name="MONTO_1" required=""  value="0.0"  class="monto" >
                                                    </label>
                                                </section>
                                                <input type="hidden" value="1" name="ES_PERIODO" />
                                                <input type="text" value="1" name="CANT" class="cant" />
                                            </div>
                                            <%}%>

                                            <input type="hidden" value="1" name="numero" class="cant-input" />
                                            <code class="ver"></code>
                                            <input type="hidden" name="IDREQUERIMIENTO"  id="combito"  value="<%=dg.getId_requerimiento()%>">
                                            <div id="div_2" class="contenido" style="display: none">
                                                <table  class="table">
                                                    <tr><td class="td">Subvencion:</td><td><input type="text" name="SUBVENCION"  ></td></tr>
                                                    <tr><td class="td">Horario de Capacitacion:</td><td><input type="text" name="HORARIO_CAPACITACION"  ></td></tr>
                                                    <tr><td class="td">Horario de Refrigerio:</td><td><input type="text" name="HORARIO_REFRIGERIO"  ></td></tr>
                                                    <tr><td class="td">Dias de Capacitacion:</td><td><input type="text" name="DIAS_CAPACITACION" ></td></tr>
                                                </table>
                                            </div >

                                            <div id="div_3" class="contenido" style="display:none ">
                                                <table class="table">
                                                    <tr><td class="td">Monto del Honorario:</td><td><input type="text" name="MONTO_HONORARIO" ></td></tr>
                                                </table>
                                            </div>
                                            <input type="hidden" name="ID_DGP" value="<%=dg.getId_dgp()%>" class="iddgp">
                                            <input type="hidden" name="cant_actual_anti" class="can_centro_cos" value="<%=request.getParameter("can_cc")%>">
                                            <input type="hidden" name="cant_ingresada" class="cant-ing" value="0">
                                        </fieldset>

                                        <fieldset id="fila-agregar">
                                        </fieldset>
                                        <input type="hidden" class="dep_id" value="<%=dg.getId_departamento().trim()%>" />
                                        <input type="hidden" class="area_id" value="<%=dg.getId_area().trim()%>" />
                                        <input type="hidden" class="seccion_id" value="<%=dg.getId_seccion().trim()%>" />
                                        <input type="hidden" class="puesto_id" value="<%=dg.getId_puesto().trim()%>" />
                                        <input type="hidden" name="redirect" value="<%=request.getParameter("redirect")%>" />
                                        <%}%>
                                    </div>

                                </div>
                                <!-- end widget content -->

                            </div>
                            <!-- end widget div -->

                        </div>
                        <!-- end widget -->

                    </article>


                    <article class="col-sm-12 col-md-12 col-lg-6">

                        <!-- Widget ID (each widget will need unique ID)-->
                        <div class="jarviswidget" id="wid-id-1" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-custombutton="false" data-widget-deletebutton="false">
                            <!-- widget options:
                            usage: <div class="jarviswidget" id="wid-id-0" data-widget-editbutton="false">

                            data-widget-colorbutton="false"
                            data-widget-editbutton="false"
                            data-widget-togglebutton="false"
                            data-widget-deletebutton="false"
                            data-widget-fullscreenbutton="false"
                            data-widget-custombutton="false"
                            data-widget-collapsed="true"
                            data-widget-sortable="false"
                            -->
                            <header>
                                <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                                <h2>Registrar Horario</h2>

                            </header>

                            <!-- widget div-->
                            <div>

                                <!-- widget edit box -->
                                <div class="jarviswidget-editbox">
                                    <!-- This area used as dropdown edit box -->

                                </div>
                                <!-- end widget edit box -->

                                <!-- widget content -->
                                <div class="widget-body no-padding">

                                    <div class="smart-form" >
                                        <header class="titulo_req">

                                            <div class="spacing">
                                                <center><h1 class="spacing" style="font-weight: bold; margin: 0px;  color: #005cac;"> Horario</h1></center>
                                                <br>

                                            </div>
                                        </header>

                                        <fieldset>

                                            <section>
                                                <label class="label" id="titu">Tipo de Horario :</label>
                                                <label class="select">

                                                    <select id="horario" required="" name="HORARIO" >
                                                        <option value="">[Seleccione]</option>
                                                        <option value="0">Editable</option>

                                                    </select>

                                                </label>
                                            </section>
                                            <div class="row">
                                                <section class="col col-2">
                                                    <label class="select" id="titu">LUNES
                                                        <select id=select_lun >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-2">
                                                    <label class="select" id="titu">MARTES
                                                        <select id=select_mar >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-2" >
                                                    <label class="select" id="titu">MIERCOLES
                                                        <select id=select_mie  >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-2">
                                                    <label class="select" id="titu">JUEVES
                                                        <select id=select_jue  >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-2">
                                                    <label class="select" id="titu">VIERNES
                                                        <select id=select_vie  >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                                <section class="col col-2">
                                                    <label class="select" id="titu">SABADO
                                                        <select id=select_sab  >
                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>

                                                <section class="col col-2">
                                                    <label class="select" id="titu">DOMINGO
                                                        <select id=select_dom >

                                                            <option value="1">Habilitado</option>
                                                            <option value="2" selected="">Deshabilitado</option>
                                                        </select>
                                                    </label>
                                                </section>
                                            </div>
                                            <div class="input-desp dias_semana">
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table  id="show_lun" class="cont_lun table table-condensed table-bordered">
                                                                <tr class="c_header"><td style="text-align: center;" colspan="3">Lunes</td></tr>

                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_mar" class="cont_mar table table-condensed table-bordered">
                                                                <tr class="c_header" ><td style="text-align: center;"  colspan="3">Martes</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_mie" class="cont_mie table table-condensed table-bordered">
                                                                <tr class="c_header"><td style="text-align: center;" colspan="3">Miercoles</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_jue" class="cont_jue table table-condensed table-bordered">
                                                                <tr class="c_header"><td style="text-align: center;" colspan="3">Jueves</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_vie" class="cont_vie table table-condensed table-bordered">
                                                                <tr class="c_header"><td style="text-align: center;" colspan="3">Viernes</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_sab" class="cont_sab table table-condensed table-bordered">
                                                                <tr class="c_header"><td style="text-align: center;"  colspan="3">S�bado</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>
                                                <div class="cont">
                                                    <section class="col col-sm-4 col-md-4">
                                                        <div class="caja">
                                                            <table id="show_dom" class="cont_dom table table-condensed table-bordered" >
                                                                <tr class="c_header"><td  style="text-align: center;" colspan="3">Domingo</td></tr>
                                                            </table>
                                                        </div>
                                                    </section>
                                                </div>


                                            </div>
                                            <div  class="row" >

                                                <section class="col col-4">
                                                    <label class="input" id="titu">
                                                        <div class="h_total" style=" font-weight: bold;">Horas Totales : 00:00 horas</div>
                                                        <input  readonly="" type="text" name="h_total" class=" h_total" required="" max="48"/>
                                                    </label>
                                                </section>
                                            </div>
                                            <input  type="hidden" name="dep_actual" value="" class="dep_actual" />
                                            <input  type="hidden" name="estado_de_horario" value="" class="modificacion-total" />
                                            <input  type="hidden" name="id_det_hor" value="<%=request.getParameter("id_det_hor")%>" class="id_det_hor" />

                                        </fieldset>
                                        <footer>
                                            <div class="div_info">

                                            </div>
                                            <button type="submit" class="btn btn-primary btn-labeled">
                                                Modificar  <i class="fa fa-pencil"></i>
                                            </button>
                                            <button type="button" class="btn btn-default" onclick="window.history.back();">
                                                <i class="fa fa-arrow-circle-left"></i>  Regresar
                                            </button>
                                        </footer>
                                    </div>


                                </div>
                                <!-- end widget content -->

                            </div>
                            <!-- end widget div -->

                        </div>
                        <!-- end widget -->

                    </article>
                    <!-- END COL -->


                    <input type="hidden" name="opc"  class="submit" value="Modificar">
                </form>


            </div>

        </section>
    </div>
</div>

    <!-- PAGE RELATED PLUGIN(S) -->
    <script src="js/plugin/knob/jquery.knob.min.js"></script>
    <script src="js/plugin/jquery-form/jquery-form.min.js"></script>
    <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
    <script src="js/chosen.jquery.js" type="text/javascript"></script>

    <!--Funciones globales-->
    <script src="js/Js_Formulario/Js_Form.js" type="text/javascript"></script>
    <script src="js/businessLogic/CentroCosto/Js_centro_costo.js" type="text/javascript"></script>
    <script src="js/businessLogic/Plazo/Js_plazo_advertencia.js" type="text/javascript"></script>
    <script src="js/businessLogic/Dgp/editDGP.js" type="text/javascript"></script>

<%}%>
