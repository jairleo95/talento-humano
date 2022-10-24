 <%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.controller.util.DateFormat"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.config.factory.FactoryConnectionDB"%>
<%@page import="com.app.persistence.dao.TrabajadorDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceTrabajadorDAO"%>
<%@page import="com.app.persistence.dao.Tipo_DocumentoDAO"%>
<%@page import="com.app.domain.model.Tipo_Documento"%>
<%@page import="com.app.persistence.dao_imp.InterfaceTipo_DocumentoDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceListaDAO"%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="com.app.domain.model.Empleado"%>
<%@page import="javax.print.DocFlavor.STRING"%>
<%@page import="com.app.domain.model.Auto_Mostrar"%>
<%@page import="com.app.domain.model.V_Ficha_Trab_Num_C"%>
<%@page import="com.app.domain.model.Trabajador"%>
<jsp:useBean id="ListaridTrabajador" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Auto_mostrar" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="id_empleadox_ide" scope="session" class="java.util.ArrayList"/>
    <head>

        <link rel="stylesheet" type="text/css" href="js/shadowbox/shadowbox.css"/>
        <!--  <link rel="stylesheet" type="text/css" href="js/shadowbox/style.css"/>-->
        <style type="text/css">
            body{
                margin-left: auto;
                margin-right: auto  ;
                width: 95%;
            }
            .autoHeight{
                border-style:none;            
            }
            .ui-progressbar {
                position: relative;
            }
            .progress-label {
                position: absolute;
                left: 50%;
                top: 4px;
                font-weight: bold;
                text-shadow: 1px 1px 0 #fff;
            }
            .ui-widget-overlay {
                background: #AAA;
                height: 100%;
                z-index: 50;
                position: fixed;
                top: 0;
                left: 0;
            }
        </style>
    </head>

    
    <%          if (request.getParameter("ms") != null) {
            if (request.getParameter("ms").equals("ok")) {
    %>
    <body onload="closedthis2();">
        <%}
            if (request.getParameter("ms").equals("t")) {
        %>
    <body onload="closedthis();">
        <%}
        } else {%> 
    <body >
        <%}%>
        <%
            V_Ficha_Trab_Num_C t = new V_Ficha_Trab_Num_C();
            t = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(0);
            String idtr = t.getId_trabajador().trim();
            String aut = request.getParameter("aut");
            HttpSession sesion = request.getSession(true);
            String idp = (String) sesion.getAttribute("p");
            String idrol = (String) sesion.getAttribute("IDROL");
            String iddep = (String) sesion.getAttribute("DEPARTAMENTO_ID");
            String idtr_session = (String) sesion.getAttribute("IDTR");
            String cl = (String) sesion.getAttribute("CL");
            String user = (String) sesion.getAttribute("USER");
            String iddgp = request.getParameter("dgp");
            String cod = request.getParameter("c");
            String iddrp = request.getParameter("drp");
            String id_pasos = request.getParameter("pas");
            String nropaso = request.getParameter("np");
            String edit = request.getParameter("edit");
        %>
   
        <!--Begin Detalle Trabajador-->
        <div id="main" role="main" style="margin-left: 0px;">
            <div id="content" >
                <section id="widget-grid" class="">
                    <div class="row">
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div class="jarviswidget jarviswidget-color-darken slideInDown fast animated" id="wid-id-3" data-widget-editbutton="false" data-widget-colorbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false">
                                <header>
                                    <span class="widget-icon"> <i class="fa fa-user"></i> </span>
                                    <h2 class="font-md"><strong>Datos del</strong> <i>Trabajador</i></h2>				

                                </header>
                                <%                String hl = "";
                                    String academico = request.getParameter("academico");
                                    if (academico != null) {
                                        if (Boolean.valueOf(academico) == true) {
                                            hl = request.getParameter("hl");
                                %>
                                <div>
                                    <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal">
                                        Trabajador Nuevo
                                    </button>
                                    <button class="btn btn-default btn-sm btn_procesar" type="button" style="display:none">
                                        Procesar Carga Academica
                                    </button>
                                </div>
                                <%}
                                    }%>
                                <div class="row" style="padding-bottom: 10px;">
                                    <div class="col-md-4">
                                        <div class="media">
                                            <!--  upload foto --> 
                                            <form action=foto" method="POST" enctype="multipart/form-data" class="form-subir-foto">
                                                <input type="hidden" name="idtr" class="idtr" id="input-file" value="<%=t.getId_trabajador()%>">
                                                <input style="display:none" class="file-foto" type="file" name="archivo" required="">
                                            </form>
                                            <a class="avatar mustang-gallery pull-left" href="img/avatar_default.jpg" ><img src="img/avatar_default.jpg" class="borde" width="100" height="100" ></a>
                                            <div class="foto-user" style="display: none;">
                                            </div>
                                            <a class="ver_foto btn bg-color-purple txt-color-white btn-xs">Cambiar Foto</a>           
                                            <div class="media-body">
                                                <%
                                                    for (int index = 0; index < ListaridTrabajador.size(); index++) {
                                                        V_Ficha_Trab_Num_C trb = new V_Ficha_Trab_Num_C();
                                                        trb = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(index);
                                                        String nombres = trb.getNo_trabajador().toUpperCase() + " " + trb.getAp_paterno().toUpperCase();
                                                        String idtrab = trb.getId_trabajador();
                                                %>
                                                <button data-toggle="modal" data-target="#myModal" id="btn-mostrar" hidden="">asas</button>
                                                <strong>  Nombre : </strong><%=trb.getNo_trabajador().toUpperCase()%><br>
                                                <strong>Apellido Paterno :</strong> <%=trb.getAp_paterno().toUpperCase()%><br>
                                                <strong>Apellido Materno :</strong> <%=trb.getAp_materno().toUpperCase()%><br>
                                                <strong>Fecha de Nacimiento :</strong><%=DateFormat.toFormat5(trb.getFe_nac())%><br>
                                                <%if (idrol.trim().equals("ROL-0009")) {%>

                                                <strong>Tipo Documento: </strong>
                                                <%
                                                    InterfaceTipo_DocumentoDAO itd = new Tipo_DocumentoDAO();
                                                    for (int k = 0; k < itd.Listar_tipo_doc().size(); k++) {
                                                        Tipo_Documento td = new Tipo_Documento();
                                                        td = (Tipo_Documento) itd.Listar_tipo_doc().get(k);
                                                        if (trb.getTi_doc() != null) {
                                                            if (td.getId_tipo_doc_ident().trim().equals(trb.getTi_doc().trim())) {
                                                                out.print(td.getDe_tdoc_abreviada());
                                                            }
                                                        } else {
                                                            out.print("No Registrado");
                                                        }
                                                    }
                                                %><br>
                                                <strong>Numero de documento:</strong> <%=trb.getNu_doc()%><br>
                                                <%}%>
                                            </div>

                                        </div>

                                    </div>
                                    <%
                                        String ID_ROL = (String) sesion.getAttribute("IDROL");
                                    %>
                                    <!-- INICIO codigos aps y huella-->
                                    <input type="hidden"  class="idtr" value="<%=t.getId_trabajador()%>">
                                    <div class="col-md-4">
                                        <div class='row row_cod_aps'>
                                        </div>
                                        <div class='row row_cod_huella'>
                                        </div>
                                        <div class="row">
                                            <%if (ID_ROL.equals("ROL-0001")) {%>
                                            <div class="col-md-4"><strong>Descuento de Diezmo</strong></div>
                                            <div class="col-md-8 div_input_diezmo"></div>
                                            <%} else {%>
                                            <div class="col-md-8"><strong>Descuento de Diezmo</strong></div>
                                            <div class="col-md-4"><%=((trb.getEs_diezmo().equals("0")) ? "No" : "Si")%></div>
                                            <%}%>
                                        </div>
                                        <%if (iddgp != null) {
                                        %>
                                        <input type="hidden" class="dgp" value="<%=iddgp%>" >
                                        <div class='row smart-form col_procesar_asigFam'>

                                        </div>
                                        <div class='row smart-form col_procesar_sis'>

                                        </div>
                                        <%}%>
                                        <div class="row_afp_sp"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <% InterfaceTrabajadorDAO iTr = new TrabajadorDAO();
                                            int porcentaje = iTr.ShowPorcentageTrabajador(idtr);%>
                                        <center><strong>Completado: </strong>
                                            <div class="showPorcentage">
                                                <div class="easy-pie-chart txt-color-blue easyPieChart pcDatosCompTrabajador" data-percent="<%=porcentaje%>" data-pie-size="100"  >
                                                    <span class="percent percent-sign txt-color-blue font-lg semi-bold spDatosCompTrabajador"><%=porcentaje%></span>
                                                </div>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <!-- FIN DE codigos aps y huella-->
                                <!-- widget div-->
                                <div class="row" style="padding: 0px;">
                                    <ul  class="nav nav-tabs bordered tab_detalle_trabajador">
                                        <%
                                            if (List_Auto_mostrar.size() == 1 && aut != null) {
                                                for (int r = 0; r < List_Auto_mostrar.size(); r++) {
                                                    Auto_Mostrar a = new Auto_Mostrar();
                                                    a = (Auto_Mostrar) List_Auto_mostrar.get(r);
                                        %>
                                        <li  class="active" >
                                            <a href="<%=a.getDi_url() + "&iddgp=" + iddgp + "&idtr=" + trb.getId_trabajador()%>" target="myframe2"><i class="fa fa-file-text fa-gear"></i> Datos de Requerimientos</a>
                                        </li>
                                        <%}
                                            }%>
                                        <%if (iddep != null) {

                                                if ((iddep.equals("DPT-0019") || idrol.trim().equals("ROL-0012") || idrol.trim().equals("ROL-0005"))) {
                                        %>
                                        <li>
                                            <a href=contrato?idtr=<%=idtr%>&opc=Detalle_Contractual" target="myframe2"  ><i class="fa fa-file-text fa-gear"></i> Informaci�n Contractual </a>
                                        </li>
                                        <%}
                                            }%>
                                        <li >
                                            <a href="Datos_Generales.jsp?edit=<%=edit%>" target="myframe2"  ><i class="fa fa-male fa-gear"></i> Informaci�n General </a>
                                        </li>
                                        <li>
                                            <a href="Aspecto_Academico.jsp?edit=<%=edit%>" target="myframe2"><i class="fa fa-graduation-cap fa-gear"></i> Aspecto Acad�mico</a>
                                        </li>
                                        <li >
                                            <a href="trabajador?idtr=<%=idtr%>&opc=Listar_Asp_Social" target="myframe2"><i class="fa fa-home fa-gear"></i> Aspecto Social </a>
                                        </li>
                                        <li>
                                            <a href=familiar?idtr=<%=idtr%>&opc=Detalle_Familiar" target="myframe2"><i class="fa fa-group fa-gear"></i> Familiares </a>
                                        </li>
                                        <%if (!idrol.trim().equals("ROL-0013")) {
                                        %>
                                        <li >
                                            <a href="dgp?idtr=<%=idtr%>&opc=List_Dgp_Tr" target="myframe2"><i class="fa fa-file-o fa-gear"></i> Historial de Requerimientos </a>
                                        </li>
                                        <%}%>
                                        <li>
                                            <a href="trabajador?idtr=<%=idtr%>&opc=Documento_Trabajador" target="myframe2"><i class="fa fa-file fa-gear"></i> Documentaci�n </a>
                                        </li>

                                        <%if (idrol.trim().equals("ROL-0007") | ID_ROL.trim().equals("ROL-0001")) {
                                        %>
                                        <li >
                                            <a href=empleado?opc=Eva_Emp&idtr=<%=idtr%>" target="myframe2"><i class="fa fa-file-text fa-gear"></i> Evaluaci�n de Empleado</a>
                                        </li>
                                        <%}%>
                                        <%if (idtr.equals(idtr_session)) {

                                        %>
                                        <li>
                                            <%if (cl.trim().equals(user.trim())) {
                                            %>
                                            <a href="trabajador?opc=Form_Cambiar_Clave" target="myframe2" rel="" data-placement="top" data-original-title="<h1><b>One</b> <em>Really</em> big tip!</h1>" data-html="true" ><i class="fa fa-lock"></i> Contrase�a <span class="badge bg-color-red pull-right inbox-badge">�Cambiar!</span></a>
                                            <%} else {%>
                                            <a href="trabajador?opc=Form_Cambiar_Clave" target="myframe2"  ><i class="fa fa-lock"></i> Contrase�a</a>
                                            <%}%>
                                        </li>
                                        <%}%>
                                        <%if (idrol.trim().equals("ROL-0002") | ID_ROL.trim().equals("ROL-0001")) {
                                        %>
                                        <li>
                                            <a href=contrato?idtr=<%=idtr%>&opc=SI_CONNTRATO" target="myframe2"><i class="fa fa-male fa-gear"></i> Imprimir / Subir Contratos </a>
                                        </li>
                                        <%}%>
                                    </ul>

                                    <div id="myTabContent1" class="tab-content">
                                        <%  if (List_Auto_mostrar.size() == 1 && aut != null) {
                                                for (int r = 0; r < List_Auto_mostrar.size(); r++) {
                                                    Auto_Mostrar a = new Auto_Mostrar();
                                                    a = (Auto_Mostrar) List_Auto_mostrar.get(r);
                                        %>
                                        <iframe id="myframe2" name="myframe2" class="autoHeight" src="<%=a.getDi_url() + "&iddgp=" + iddgp + "&idtr=" + trb.getId_trabajador()%>" width="100%" height="100%" ></iframe>
                                            <%}
                                                List_Auto_mostrar.clear();
                                            } else {%>
                                        <iframe id="myframe2" name="myframe2" width="100%" height="800" scrolling="si"  frameborder="0" src="Datos_Generales.jsp?edit=<%=edit%>"></iframe>
                                            <%}%>
                                    </div>
                                </div>

                                <!-- end widget content -->


                                <!-- end widget div -->

                            </div>      
                        </article>
                    </div>
                </section>
            </div>
        </div>
        <!-- end TABS-->
        <div >
            <%

                if (aut != null) {
                    if (aut.equals("1")) {
            %>
            <center>
                <form class="form-aut" action=autorizacion" method="post" >
                    <table > 
                        <input type="hidden" name="IDDETALLE_DGP"  value="<%=iddgp%>"  >           
                        <input type="hidden" name="NROPASO" value="<%=nropaso%>"  >                
                        <input type="hidden" name="COD" value="<%=cod%>"  >               
                        <input type="hidden" name="PUESTO_ID" value="<%=idp%>" >  
                        <input type="hidden" name="IDDETALLE_REQ_PROCESO" value="<%=iddrp%>"  >  
                        <input type="hidden" name="IDPASOS" value="<%=id_pasos%>"   >
                        <input type="hidden" name="NOMBRES" value="<%=nombres%>"   >
                        <input type="hidden" name="IDTRAB" value="<%=idtrab%>"   >
                        <tr><td><input type="hidden" name="opc"  class="submit" value="Aceptar"/></td></tr>
                                <%
                                    if (idrol.trim().equals("ROL-0006")) {
                                        int vnc = Integer.parseInt(request.getParameter("vnc"));
                                        if (vnc > 0) {
                                %>
                        <button class="btn btn-labeled btn-success btn-autor" type="button">
                            <span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>PROCESAR  </button>
                                <%
                                    }
                                } else {%>

                        <!--INICIO DE  validacion-->
                        <div class="validacionBtnAutorizar"></div><br>

                        <!--fin validacion-->
                        <%}%>
                    </table>
                </form>

                <form class="form-rech" action=autorizacion" method="post">
                    <table>
                        <input type="hidden" name="IDDETALLE_DGP"  value="<%=iddgp%>"  >           
                        <input type="hidden" name="NROPASO" value="<%=nropaso%>"  >                
                        <input type="hidden" name="COD" value="<%=cod%>"  >               
                        <input type="hidden" name="PUESTO_ID" value="<%=idp%>" >  
                        <input type="hidden" name="IDDETALLE_REQ_PROCESO" value="<%=iddrp%>"  >  
                        <input type="hidden" name="IDPASOS" value="<%=id_pasos%>">
                        <input type="hidden" name="NOMBRES" value="<%=nombres%>"> 
                        <input type="hidden" name="IDTRAB" value="<%=idtrab%>">
                        <tr><td><input type="hidden" name="opc"  class="submit" value="Rechazar"/></td></tr>
                        <div class="modal fade" id="myModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close-form close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        <h4 class="modal-title" id="myModalLabel"> Motivo </h4>
                                    </div>
                                    <div class="modal-body">
                                        <legend>Comentario : </legend>
                                        <div class="area-coment" style="display: block;">
                                            <textarea rows="3" placeholder="Inserte Comentario" maxlength="200" required="" class="mensaje" name="comentario"></textarea>
                                            <div class="contador">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="submit" class="btn btn-primary btn-conti"> Continuar </button>
                                        <button type="button" class="btn btn-default" data-dismiss="modal" > Cancel </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <a data-toggle="modal" href="#myModal6" class="btn btn-labeled btn-danger"><span class="btn-label"><i class="glyphicon glyphicon-remove"></i></span>RECHAZAR</a>
                    </table>
                </form>   
            </center>
            <%}
                }
            %>
        </div>
        <%    if (academico != null) {%>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel">Carga Academica</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form_carga_academica">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="category"> Fecha de Inicio:</label>
                                        <input type="text" class="fecha form-control fe_desde_p frompicker" value="" name="DESDE" data-mask="99/99/9999" data-mask-placeholder= "_" required />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="category"> Fecha de Cese:</label>
                                        <input type="text" class="fecha1 form-control fe_hasta_p topicker" value="" name="HASTA" data-mask="99/99/9999" data-mask-placeholder= "_" required />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="category"> Tipo de Hora Pago:</label>
                                        <input type="text" class="form-control ti_hp_docente" value="25" placeholder="0.0"  name="TI_HORA_PAGO" required />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label for="category"> Horas Laborables:</label>
                                        <%
                                            if (hl != null) {
                                                if (Boolean.valueOf(academico) == true) {
                                        %>
                                        <input type="text" class="form-control hl_docente" value="0" name="HL" placeholder="0" required />
                                        <%} else {%>
                                        <input type="text" class="form-control hl_docente" value="0" name="HL" placeholder="0" required />
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <label for="category" > <strong>CUOTAS</strong></label>
                            <br>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <div class="cuota_docente">

                                        </div>
                                        <input type="hidden" name="PUESTO" value="PUT-000482" >
                                        <input type="hidden" name="REQ" value="REQ-0018">
                                        <input type="hidden" name="IDTR" value="<%=idtr%>" >
                                        <input type="hidden" name="eap" value="<%=request.getParameter("eap")%>" >
                                        <input type="hidden" name="facultad" value="<%=request.getParameter("facultad")%>" >
                                        <input type="hidden" name="dgp"  class="dgp" value="<%=request.getParameter("dgp")%>" >
                                        <input type="hidden" name="proceso" class="proceso" value="<%=request.getParameter("proceso")%>" >
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                    Cancelar
                                </button>
                                <button type="button" class="btn btn-primary btn_guardar_ca"  data-dismiss="modal">
                                    Guardar
                                </button>

                            </div>
                        </form>

                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
        <!-- /.modal -->

        <%}%>
        <%}%> <%}%>
        <div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-body">
                        <div class="text-center">
                            <H2>Tu Imagen fue rechazada por el Administrador !</H2>
                            <h4>Vuelve a subir tu imagen  nuevamente.</h4><br/>
                            <span style="font-size: 80px;" class="glyphicon glyphicon-remove"></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="div_dialog" ></div>
        <input  value="<%out.print(FactoryConnectionDB.url_archivos);%>" type="hidden" class="url_archivo"/> 
        <!-- #dialog-message -->
        <!--    PAGE RELATED PLUGIN(S) -->
        <script src="js/plugin/bootstrap-progressbar/bootstrap-progressbar.min.js"></script>
        <script src="js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
        <script src="js/plugin/fuelux/wizard/wizard.min.js"></script>
        <script src="js/plugin/jquery-form/jquery-form.min.js"></script>
        <script type="text/javascript" src="js/libs/jquery.autoheight.js"></script>
        <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
        <script type="text/javascript" src="js/shadowbox/shadowbox.js" ></script>
        <script  type="text/javascript" src="js/libs/jquery.session.js"></script>

        <script  type="text/javascript" src="js/businessLogic/Js_Formulario/Js_Form.js?"></script>
        <script type="text/javascript" src="js/businessLogic/Autorizacion/Js_Autorizacion.js"></script>
        <script type="text/javascript" src="js/Js_Academico/Js_Carga_Academica.js"></script>
        <script type="text/javascript" src="js/businessLogic/Foto/foto.js"></script>
        <script type="text/javascript" src="js/businessLogic/Trabajador/Js_Trabajador.js"></script>
    </body>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%> 
