<%@page import="com.app.persistence.dao_imp.InterfaceComentario_DGPDAO"%>
<%@page import="com.app.persistence.dao.Comentario_DGPDAO"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<jsp:useBean class="java.util.ArrayList" id="Det_Autorizacion" scope="session" />
<%@page import="com.app.domain.model.X_List_De_Autorizacion"%>
        <!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

        <%InterfaceComentario_DGPDAO cm = new Comentario_DGPDAO();
            HttpSession sesion = request.getSession();
            String rol = (String) sesion.getAttribute("IDROL");%>
        <!-- MAIN PANEL -->
        <div id="main" role="main" style="margin-left: 0px;">



            <!-- MAIN CONTENT -->
            <div id="content">


                <!-- widget grid -->
                <section id="widget-grid" class="">

                    <!-- row -->
                    <div class="row">

                        <!-- NEW WIDGET START -->
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">


                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget jarviswidget-color-yellow" id="wid-id-0" data-widget-editbutton="false">
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

                                <!-- widget div-->
                                <div>

                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body no-padding prueba" id="contenido">
                                        <table  class="table table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                                <tr>
                                                    <td >Paso <%=Det_Autorizacion.size()%></td>
                                                    <td data-hide="phone,tablet"><i class="fa fa-fw fa-calendar txt-color-blue hidden-md hidden-sm hidden-xs"></i> Detalle</td>
                                                    <td data-class="expand"><i class="fa fa-fw fa-user text-muted hidden-md hidden-sm hidden-xs"></i> Estado</td>
                                                    <td data-hide="phone"><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> Encargado</td>
                                                    <td>Cargo Encargado</td>
                                                    <td> Usuario</td>
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
                                                    <td ><%=a.getDe_pasos()%></td>
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
                                                    <td ><%
                                                        if (a.getUs_ap_mat() != null) {
                                                            out.println(a.getUs_ap_p().toUpperCase() + " " + a.getUs_ap_mat().toUpperCase() + " " + a.getUs_no_tr().toUpperCase());
                                                        } else {
                                                            out.println("No Registrado");
                                                        }
                                                        %></td> 
                                                    <td  ><%=a.getUs_no_puesto()%></td> 
                                                    <td  ><%=a.getNo_usuario()%></td> 
                                                    <td ><%=a.getUs_no_area()%></td> 
                                                    <td ><%=a.getUs_no_dep()%></td> 
                                                       <td><%=a.getFe_creacion()%></td>
                                                    <%} else {%>
                                                    <td colspan="7" style="text-align:center;">No definido</td>
                                                    <%}%>

                                                    <%
                                                        //if () {
                                                        if (a.getEs_autorizacion() != null) {

                                                            if (a.getEs_autorizacion().equals("2") & (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005")| rol.trim().equals("ROL-0001"))) {

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
                                    <!-- end widget content -->

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->
                        </article>
                        <!-- WIDGET END -->

                    </div>

                    <!-- end row -->

                    <!-- end row -->

                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
