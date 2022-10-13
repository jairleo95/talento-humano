<%@page import="com.app.config.globalProperties"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
        String ID_DEP = (String) sesion_1.getAttribute("DEPARTAMENTO_ID");
%>
<%@page import="com.app.config.factory.FactoryConnectionDB"%>
<%@page import="com.app.persistence.dao.AutorizacionDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceAutorizacionDAO"%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="com.app.persistence.dao_imp.InterfaceComentario_DGPDAO"%>
<%@page import="com.app.persistence.dao.Comentario_DGPDAO"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.app.persistence.dao.DgpDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceDgpDAO"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.app.domain.model.V_Es_Requerimiento"%>
<jsp:useBean class="java.util.ArrayList" id="Det_Autorizacion" scope="session" />
<%@page import="com.app.domain.model.X_List_De_Autorizacion"%>
<jsp:useBean class="java.util.ArrayList" id="LIST_DGP_PROCESO" scope="session" />
<link href='../../css/Css_Bootstrap/fileinput.css' media='all' rel='stylesheet' type='text/css' />


    <%
        if (request.getParameter("a") != null) {
            if (request.getParameter("a").equals("t")) {
    %>
    <body onload="closedthis();
          ">
        <%
            }
        } else {
        %>
    <body>
        <%}%>
        <!-- MAIN PANEL -->
        <div id="main" role="main" style="margin-left: 0px;">
            <!-- MAIN CONTENT -->
            <div id="content">

                <!-- widget grid -->
                <section id="widget-grid" class="slideInDown fast animated" >

                    <!-- row -->
                    <div class="row">

                        <!-- NEW WIDGET START -->
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget jarviswidget-color-white" id="wid-id-0"   data-widget-editbutton="false"

                                 data-widget-togglebutton="false"
                                 data-widget-deletebutton="false"
                                 data-widget-fullscreenbutton="false"

                                 data-widget-sortable="false"
                                 >
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
                                    <span class="widget-icon"> <i class="fa fa-user"></i> </span>
                                    <h2 class="font-md"><strong>Estado de </strong> <i>Requerimientos</i></h2>
                                    <ul id="widget-tab-1" class="nav nav-tabs pull-right">
                                        <li class="active">
                                            <a data-toggle="tab" href="#hr1"> <i class="fa fa-lg fa-arrow-circle-o-down"></i> <span class="hidden-mobile hidden-tablet"> DGP </span> </a>
                                        </li>
                                        <li>
                                            <a data-toggle="tab" href="#hr2"> <i class="fa fa-lg fa-arrow-circle-o-up"></i> <span class="hidden-mobile hidden-tablet"> Carga acad�mica</span> 
                                                <sup class="badge bg-color-red bounceIn animated badgeCAcademico" style="display: none" >0</sup></a>
                                        </li>

                                    </ul>	

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

                                        <div class="tab-content padding-10">
                                            <div class="tab-pane dae in active" id="hr1">
                                                <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th data-hide="phone">Nro</th>
                                                            <th>MES</th>
                                                            <th data-hide="phone,tablet" style="width: 5px">Accion</th>

                                                            <th data-class="expand">Nombre</th>
                                                            <th data-class="expand" style="width: 35%">Proceso</th>
                                                            <%if (ID_DEP.equals("DPT-0019") || ID_DEP.equals("DPT-0033")) {%>
                                                            <th data-hide="phone,tablet">Departamento</th>
                                                                <%}%>
                                                            <th >Estado</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                    <input type="hidden" value="<%=LIST_DGP_PROCESO.size()%>" class="tama�o"/>
                                                    <%
                                                        String dgp = "";
                                                        for (int i = 0; i < LIST_DGP_PROCESO.size(); i++) {
                                                            V_Es_Requerimiento r = new V_Es_Requerimiento();
                                                            r = (V_Es_Requerimiento) LIST_DGP_PROCESO.get(i);
                                                            dgp = r.getId_dgp();
                                                    %>

                                                    <tr>
                                                        <td><strong><%=i + 1%></strong></td>
                                                        <td><%=r.getMes_procesamiento()%></td>
                                                        <td>
                                                            <div class="btn-group">
                                                                <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                                                    <i class="fa fa-gear fa-lg"></i>
                                                                </button>
                                                                <ul class="dropdown-menu">
                                                                    <li><a href="dgp?iddgp=<%=r.getId_dgp().trim()%>&opc=User_Aut">Usuarios - Prox. Autorizaci�n</a></li>
                                                                    <!--<li><a href="dgp?iddgp=<%/*=r.getId_dgp().trim()*/%>&opc=Seguimiento">Ver Historial</a> </li>-->
                                                                    <li><a onclick="listHistory(<%=i%>)" data-toggle="modal" data-target="#ModalHisto">Ver Historial</a></li>
                                                                    <li><a   class="btnHorario"  data-valor="<%=r.getId_dgp()%>"  data-toggle="modal" data-target="#exampleModal">Ver Horario</a></li>

                                                                    <li><a href="" data-iddgp="<%=r.getId_dgp().trim()%>" data-idtr="<%=r.getId_trabajador().trim()%>" class="seeDocuments" >Ver Documentos</a></li>
                                                                    <li><a data-valor="<%=r.getId_dgp().trim()%>;<%=r.getId_trabajador().trim()%>;<%=r.getAp_paterno().toUpperCase() + " " + r.getAp_materno().toUpperCase() + " " + r.getNo_trabajador().toUpperCase()%>" class="click" data-toggle="modal" data-target="#myModal" data-backdrop="static" data-keyboard="false" onclick="sendAjaxComment('')" >Comentario</a></li>

<!--<li><a href=solicitud_requerimiento?iddgp=<%=r.getId_dgp().trim()%>&opc=Reg_List_Solicitud">Hacer Solicitud</a></li>
                                                                    -->
                                                                    <li class="divider"></li><li>
                                                                    <li><a href="dgp?iddgp=<%=r.getId_dgp().trim()%>&idtr=<%=r.getId_trabajador().trim()%>&opc=Detalle">Ver Requerimiento</a> </li>


                                                                    <input type="hidden" class="vHist<%=i%>" value="<%=r.getId_dgp().trim()%>">
                                                                </ul>
                                                            </div>

                                                        </td> 
                                                        <% if (r.getAr_foto() == null) {%>
                                                        <td><img class="user_avatar_<%=r.getId_trabajador()%>"  src="img/avatar_default.jpg"  width="30"  height="30">
                                                            <a style="margin-left: 3%;" href="trabajador?idtr=<%=r.getId_trabajador()%>&opc=list">
                                                                <strong><%=r.getAp_paterno().toUpperCase() + " " + r.getAp_materno().toUpperCase() + " " + r.getNo_trabajador().toUpperCase()%></strong></a>
                                                        </td>
                                                        <%} else {%>
                                                        <td>
                                                            <img class="user_avatar_<%=r.getId_trabajador()%>" src="<%=FactoryConnectionDB.url_archivos + "Fotos/" + r.getAr_foto()%>"  width="30"  height="30"><a href="trabajador?idtr=<%=r.getId_trabajador()%>&opc=list">
                                                                <strong><%=r.getAp_paterno().toUpperCase() + " " + r.getAp_materno().toUpperCase() + " " + r.getNo_trabajador().toUpperCase()%></strong></a> 
                                                        </td>
                                                        <%}%>
                                                        <td>
                                                            <%
                                                                String iddrp = r.getId_detalle_req_proceso();
                                                                String iddep = r.getId_departamento();
                                                            %>
                                                            <div class="new-progress prog_aut"  data-value="&dgp=<%=dgp%>&idrp=<%=iddrp%>&iddep=<%=iddep%>" >
                                                                <img src="img/ajax-loader/horizontal_fountain.gif" />
                                                            </div>

                                                        </td>
                                                        <%if (ID_DEP.equals("DPT-0019") || ID_DEP.equals("DPT-0033")) {%>
                                                        <td><%=r.getNo_dep()%></td>
                                                        <%} %>
                                                        <td>
                                                            <%if (r.getEs_dgp().equals("2")) {
                                                                    out.print(" <span class='label label-danger'>Fuera de Proceso</span>");
                                                                } else {
                                                                    out.print(" <span class='label label-primary'>En Proceso</span>");
                                                                }%>
                                                        </td>

                                                    </tr>
                                                    <% }
                                                        LIST_DGP_PROCESO.clear();
                                                    %>
                                                    </tbody>
                                                </table>


                                            </div>
                                            <div class="tab-pane fade" id="hr2">
                                                <table  class="table table-striped table-bordered table-hover tableEsCargaAcademica" width="100%">
                                                    <thead>
                                                        <tr>
                                                            <th data-hide="phone">Nro</th>
                                                            <th>MES</th>
                                                            <th style="width: 5px">Accion</th>
                                                            <th data-class="expand">Nombre</th>
                                                            <th data-class="expand" style="width: 35%">Proceso</th>
                                                                <%if (ID_DEP.equals("DPT-0019") || ID_DEP.equals("DPT-0033")) {%>
                                                            <th data-hide="phone,tablet">Departamento</th>
                                                                <%}%>
                                                            <th >Estado</th>
                                                        </tr>
                                                    </thead>
                                                </table>

                                            </div>

                                        </div>
                                    </div>
                                    <!-- end widget content -->

                                </div>
                                <!-- end widget div -->

                            </div>

                            <!-- end widget -->



                        </article>
                        <!-- WIDGET END -->

                    </div>
                    <div class="row"></div>

                    <!-- end row -->

                    <!-- end row -->

                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->
        <!-------------- Modal Comentarios ----------->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog" >
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close-form close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <div class="datos_trabajador text-left">
                        </div>
                        <h4 class="modal-title" id="myModalLabel">A�adir Comentario</h4>
                    </div>
                    <div class="modal-body">

                        <!--<button class="add-coment btn btn-primary btn-block">Add Comentario</button> -->
                        <div class="area-coment">
                            <form class="comentari-form" method="post">
                                <textarea class="mensaje"></textarea>
                                <p></p>
                                <input name="idDgp" class="idDgp" type="hidden" value="<%=dgp%>">
                                <div class="contador">
                            </form>
                        </div>
                    </div>

                    <div class="comentarios">
                        <div>
                            <legend>Comentarios </legend>
                            <div class="comentario-dgp"></div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="close-form btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" onclick="Registrar()" class="comet btn btn-success">Comentar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-----Modal------------------------------------------->

    <!-------------- Modal Historial ----------->

    <%InterfaceComentario_DGPDAO cm = new Comentario_DGPDAO();
        HttpSession sesion = request.getSession();
        String rol = (String) sesion.getAttribute("IDROL");%>

    <input type="hidden" id="rolse" value="<%=rol%>">
    <div class="modal fade" id="myModalHis" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style=" width:180%;margin-left:-40%;">
                <div class="modal-header">
                    <button type="button" class="close-form close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div class="text-left ">
                    </div><br>
                    <div class="jarviswidget jarviswidget-color-yellow" id="wid-id-0" data-widget-editbutton="false" style="margin-bottom:-5px;">
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
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2>Historial de Autorizaciones</h2>
                        </header>
                    </div>  
                </div> 

                <div class="modal-body">
                    <div id="contenido">
                        <div class="widget-body no-padding">
                            <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
                                <thead>
                                    <tr>
                                        <td>Paso <%=Det_Autorizacion.size()%></td>
                                        <td data-hide="phone,tablet"><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> Detalle</td>
                                        <td data-class="expand"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Estado</td>
                                        <td data-hide="phone"><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> Encargado</td>
                                        <td>Cargo Encargado</td>
                                        <td > Usuario</td>
                                        <td data-hide="phone,tablet"><i class="fa fa-fw fa-map-marker txt-color-blue hidden-md hidden-sm hidden-xs"></i> Area</td>
                                        <td data-hide="phone,tablet">Departamento</td>
                                        <td data-hide="phone,tablet">Fecha Autorizacion</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%for (int i = 0; i < Det_Autorizacion.size(); i++) {
                                            X_List_De_Autorizacion a = new X_List_De_Autorizacion();
                                            a = (X_List_De_Autorizacion) Det_Autorizacion.get(i);
                                    %>
                                    <tr>
                                        <td class="caji"><%=a.getNu_pasos()%></td>
                                        <td><%=a.getDe_pasos()%></td>
                                        <% if (a.getEs_autorizacion() != null) {%>
                                        <td class="caji" >
                                            <%
                                                if (a.getEs_autorizacion() != null) {

                                                    if (a.getEs_autorizacion().equals("1")) { %>

                                            <img src="img/Aprobado.png" width="20" height="20">
                                            <% }
                                                if (a.getEs_autorizacion().equals("2")) {
                                            %>
                                            <img src="img/Desaprobado.png" width="20" height="20">
                                            <% }
                                            } else {%>
                                            No registrado
                                            <%}%><input type="hidden" class="estado-aut" value="<%=a.getEs_autorizacion()%>"></td>
                                        <td><%
                                            if (a.getUs_ap_mat() != null) {
                                                out.println(a.getUs_ap_p().toUpperCase() + " " + a.getUs_ap_mat().toUpperCase() + " " + a.getUs_no_tr().toUpperCase());
                                            } else {
                                                out.println("No Registrado");
                                            }
                                            %></td> 
                                        <td><%=a.getUs_no_puesto()%></td> 
                                        <td><%=a.getNo_usuario()%></td> 
                                        <td><%=a.getUs_no_area()%></td> 
                                        <td><%=a.getUs_no_dep()%></td> 
                                        <td><%=a.getFe_creacion()%></td>
                                        <%} else {%>
                                        <td colspan="7" style="text-align:center;">No definido</td>
                                        <%}%>

                                        <%
                                            if (a.getEs_autorizacion() != null) {
                                                if (a.getEs_autorizacion().equals("2") & (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") | rol.trim().equals("ROL-0001"))) {

                                        %>


                                <div class="alert alert-danger alert-block">
                                    <a class="close" data-dismiss="alert" href="#">�</a>
                                    <h4 class="alert-heading">DGP fuera de Proceso!</h4>
                                    <p>El DGP se ha rechazado por uno de los Usuarios... <a href=autorizacion?opc=HDGP&iddgp=<%=a.getId_dgp().trim()%>&ID<%=a.getId_departamento()%>" class="btn btn-primary"><strong><i class="fa fa-arrow-circle-right"></i> Habilitar</strong></a>  <a href="dgp?opc=MODIFICAR REQUERIMIENTO&iddgp=<%=a.getId_dgp().trim()%>" class="btn btn-primary"><strong><i class="fa fa-pencil-square-o"></i> Editar DGP</strong></a> <a data-toggle="modal" href="#myModal6" class="btn btn-primary"><i class="glyphicon glyphicon-remove"></i> Ver Motivo</a>
                                    </p>
                                    <input type="hidden" class="id-autorizacion" value="<%=a.getId_autorizacion()%>">
                                </div>

                                <div class="modal fade" id="myModal6" tabindex="-1" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content" align="center">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                    &times;
                                                </button>
                                            </div>
                                            <div class="modal-body no-padding">
                                                <fieldset>
                                                    <section align="center">
                                                        <div class="row" >
                                                            <h1 class="h1" style="color:#218FDD ; font-size:20px;"><strong>MOTIVO</strong></h1>
                                                            <div class="col col-10"  >
                                                                <label class="input">
                                                                    <% if (a.getCm_comentario() != null & a.getId_autorizacion() != null & a.getId_dgp() != null) {
                                                                            String inf = "";
                                                                            inf = cm.Comentario_dgp_aut(a.getId_dgp(), a.getId_autorizacion());
                                                                            String info[] = inf.split("/");
                                                                    %>
                                                                    <P class="list_motivo"><%=info[0]%></P>
                                                                    <P class="list_motivo"><strong>Usuario:</strong><%=info[1]%></P>
                                                                    <P class="list_motivo"><strong>Fecha:</strong><%=info[2]%></P>
                                                                        <%} else {%>
                                                                    <p>No se Comento Rechazo</p>
                                                                    <%}%>
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </section>
                                                </fieldset>
                                                <footer align="center">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal" >
                                                        Cancel
                                                    </button>
                                                </footer>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <%}
                                        }
                                    }%>
                                </tr> 
                                </tbody>
                            </table>
                        </div>
                    </div>                         
                </div><!--cierra cuerpo modal-->


            </div>
        </div>
    </div>       

    <!-------------- Modal  ----------->
    <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">Horarios</h4>
                    <h6 class="tipoh hidden"></h6>
                </div>
                <div class="modal-body" style="height:550px;">
                    <div class="conTablas"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!-------------- Modal  ----------->
    <div class="modal fade modalDocument" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
        <div class="modal-dialog modal-lg"   >
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close-form close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modalTitle" ></h4>
                </div>
                <div class="modal-body listDocument">

                </div>
            </div>
        </div>
    </div>
    <!--================================================== -->

    <div class="modal fade col-lg-12 col-md-12 col-sm-12 col-xs-12" id="ModalHisto" tabindex="-1" role="dialog"  >
        <div class="modal-dialog" >
            <div class="modal-content col-lg-12 col-md-12 col-sm-12 col-xs-12" style="width:220%;margin-left: -60%;">
                <div class="modal-header">
                    <div class="contD"></div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-0" data-widget-editbutton="false" style="margin-bottom:-5px;">
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
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2>Historial de Autorizaciones</h2>
                        </header>
                    </div> 

                </div>
                <div class="modal-body">
                    <div class="contM"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade col-lg-12 col-md-12 col-sm-12 col-xs-12" id="myModalComentario" tabindex="-1" role="dialog"  >
        <div class="modal-dialog" >
            <div class="modal-content col-lg-12 col-md-12 col-sm-12 col-xs-12" style="width:100%;margin-left: 0%;">
                <div class="modal-header">
                    <h1 class="h1" style="color:#218FDD ;text-align: center; font-size:20px;"><strong>MOTIVO</strong></h1>

                </div>
                <div class="modal-body">
                    <div class="contMO"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                </div>
            </div>
        </div>
    </div>

    <!--BUSINESS LOGIC PLUGINS-->
    <script src="js/coment/comenth.js?v=<%=globalProperties.VERSION_JS%>" type="text/javascript"></script>
    <script src="js/businessLogic/Horario/horario.js?v=<%=globalProperties.VERSION_JS%>" type="text/javascript"></script>
    <script src='../../js/businessLogic/Trabajador/RegDocumentoDGP.js?v=<%=globalProperties.VERSION_JS%>' type='text/javascript'></script>
    <script src="js/businessLogic/Dgp/statusProcessDGP.js?v=<%=globalProperties.VERSION_JS%>" type="text/javascript"></script>

</body>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
