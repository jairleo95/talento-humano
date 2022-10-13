
<%@page import="com.app.config.globalProperties"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>

        <!-- MAIN PANEL -->
<div id="main" role="main" style="margin-left: 0px;">
            <!-- MAIN CONTENT -->
            <div id="content">
                <!-- widget grid -->
                <section id="widget-grid" class="">
                    <!-- end row -->
                    <div class="row">
                        <!-- NEW WIDGET START -->
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="alerta_dgp">
                            </div>
                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget jarviswidget-color-blue" id="wid-id-1" data-widget-editbutton="false"  data-widget-deletebutton="false">
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
                                    <h2>Procesar Requerimientos</h2>
                                </header>
                                <!-- widget div-->
                                <div>

                                    <!-- widget edit box -->
                                    <div class="jarviswidget-editbox">
                                        <!-- This area used as dropdown edit box -->

                                    </div>
                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body no-padding imprimir_tabla">

                                    </div>
                                    <!-- end widget content -->
                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->
                        </article>
                        <!-- WIDGET END -->
                    </div>
                    <div class="row">
                        <!-- NEW WIDGET START -->
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div id="alerta_dgp">
                            </div>
                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget jarviswidget-color-blue" id="wid-id-1" data-widget-editbutton="false"  data-widget-deletebutton="false">
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
                                    <h2>Requerimientos Procesados</h2>
                                </header>
                                <!-- widget div-->
                                <div>

                                    <!-- widget edit box -->
                                    <div class="jarviswidget-editbox">
                                        <!-- This area used as dropdown edit box -->

                                    </div>
                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body no-padding imprimir_tabla_aut">

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
                </section>
                <!-- end widget grid -->
            </div>
        </div>
    <script src="js/coment/coment.js" type="text/javascript"></script>
    <!--================================================== -->

    <script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
    
    <script src="js/businessLogic/Dgp/procesarRequerimiento.js?v=<%=globalProperties.VERSION_JS%>" type="text/javascript"></script>

<%
    } else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
