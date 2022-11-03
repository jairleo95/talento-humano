
<%@page import="com.app.domain.model.V_Es_Req_Incompleto"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.domain.model.Usuario"%>
<jsp:useBean class="java.util.ArrayList" id="List_Incomplet" scope="session" />

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
                            <div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false">
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
                                    <h2>Requerimientos Inconclusos</h2>

                                </header>

                                <!-- widget div-->
                                <div>

                                    <!-- widget edit box -->
                                    <div class="jarviswidget-editbox">
                                        <!-- This area used as dropdown edit box -->

                                    </div>
                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body">

                                        <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
                                            <thead>			                
                                                <tr>
                                                    <th data-hide="phone">Nro</th>
                                                    <th data-hide="phone"><i class="fa fa-fw fa-phone text-muted hidden-md hidden-sm hidden-xs"></i> Acciones</th>
                                                    <th>Nombre</th>
                                                    <th>Departamento</th>
                                                    <th>Area</th>
                                                    <th>Secci�n</th>
                                                    <th>Puesto</th>
                                                    <th>Nombre requerimiento</th>
                                                    <th>Fecha Creaci�n</th>

                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for (int i = 0; i < List_Incomplet.size(); i++) {
                                                        V_Es_Req_Incompleto r = new V_Es_Req_Incompleto();
                                                        r = (V_Es_Req_Incompleto) List_Incomplet.get(i);
                                                %>
                                                <tr>
                                                    <td><strong><%=i + 1%></strong></td>
                                                    <td>
                                                        <div class="btn-group">
                                                            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                                                Accion <span class="caret"></span>
                                                            </button>
                                                            <ul class="dropdown-menu">
                                                                <li><a href="documento?iddgp=<%=r.getId_dgp().trim()%>&idtr=<%=r.getId_trabajador().trim()%>&opc=ReqIncompletoNextStep">Continuar registro</a></li>

                                                                <li class="divider"></li>
                                                                <li>
                                                                <li><a href="dgp?iddgp=<%=r.getId_dgp().trim()%>&idtr=<%=r.getId_trabajador().trim()%>&opc=Detalle">Ver Requerimiento</a> </li>

                                                            </ul>
                                                        </div>
                                                    </td> 
                                                    <% if (r.getAr_foto() == null) {%>
                                                    <td><img src="img/avatar_default.jpg"  width="30"  height="30">
                                                        <a style="margin-left: 3%;" href="trabajador?idtr=<%=r.getId_trabajador()%>&opc=list"> <strong><%=r.getAp_paterno().toUpperCase() + " " + r.getAp_materno().toUpperCase() + " " + r.getNo_trabajador().toUpperCase()%></strong></a></td>
                                                        <% } else {%>
                                                    <td><img src=Archivo/Fotos/<%=r.getAr_foto()%>"  width="40"  height="40">
                                                        <a href="trabajador?idtr=<%=r.getId_trabajador()%>&opc=list"> <strong><%=r.getAp_paterno().toUpperCase() + " " + r.getAp_materno().toUpperCase() + " " + r.getNo_trabajador().toUpperCase()%></strong></a></td>
                                                        <% }%>
                                                    <td> <%=r.getNo_dep()%></td>
                                                    <td> <%=r.getNo_area()%></td>
                                                    <td> <%=r.getNo_seccion()%></td>
                                                    <td> <%=r.getNo_puesto()%></td>
                                                    <td> <%=r.getNo_req()%></td>
                                                    <td> <strong><%=r.getFe_creacion()%></strong></td>
                                                </tr>
                                                <% }
                                                    List_Incomplet.clear();%>


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

    <!-- PAGE RELATED PLUGIN(S) -->
    <script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

    <script type="text/javascript">

        // DO NOT REMOVE : GLOBAL FUNCTIONS!

        $(document).ready(function() {

            pageSetUp();

            /* BASIC ;*/
            var responsiveHelper_dt_basic = undefined;

            var breakpointDefinition = {
                tablet: 1024,
                phone: 480
            };

            $('#dt_basic').dataTable({
                "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                        "t" +
                        "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
                "autoWidth": true,
                "preDrawCallback": function() {
                    // Initialize the responsive datatables helper once.
                    if (!responsiveHelper_dt_basic) {
                        responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($('#dt_basic'), breakpointDefinition);
                    }
                },
                "rowCallback": function(nRow) {
                    responsiveHelper_dt_basic.createExpandIcon(nRow);
                },
                "drawCallback": function(oSettings) {
                    responsiveHelper_dt_basic.respond();
                }
            });

            /* END BASIC */
        })

    </script>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>