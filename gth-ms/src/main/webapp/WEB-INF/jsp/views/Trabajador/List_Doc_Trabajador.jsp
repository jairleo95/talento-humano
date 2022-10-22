

<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    String rol = (String) sesion_1.getAttribute("IDROL");
    InterfaceDocumentoDAO doc_ = new DocumentoDAO();
    if (id_user_1 != null) {
%>
<%@page import="com.app.persistence.dao_imp.InterfaceDocumentoDAO"%>
<%@page import="com.app.domain.model.Lis_Doc_tra"%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="com.app.persistence.dao.DocumentoDAO"%>
<%@page import="com.app.domain.model.V_Documento_Trabajador"%>
<jsp:useBean id="Lis_doc_trabajador_hab" scope="session" class="java.util.ArrayList"/>
<center>
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


                    <div class="jarviswidget jarviswidget-color-blueDark slideInDown fast animated" id="wid-id-1"        data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false"   data-widget-deletebutton="false" data-widget-colorbutton="false">
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
                            <span class="widget-icon"> <i class="fa fa-list"></i> </span>
                            <h2 class="font-md"><strong>Documentos </strong> <i>Trabajador</i></h2>
                            <div class="widget-toolbar">
                                <div class="btn-group">


                                </div>
                            </div>



                        </header>

                        <!-- widget div-->
                        <div >

                            <!-- This area used as dropdown edit box -->



                            <!-- end widget edit box -->

                            <!-- widget content -->
                            <div class="widget-body no-padding">
                                <div class="widget-body-toolbar">

                                    <div class="row">

                                        <div class="col-xs-9 col-sm-5 col-md-5 col-lg-5">

                                        </div>
                                        <div class="col-xs-3 col-sm-7 col-md-7 col-lg-7 text-right">
                                            <%if (!rol.trim().equals("ROL-0013")) {%>

                                            <a href="documento?idtr=<%=request.getParameter("idtr")%>&opc=Ver_Documento" class="btn btn-labeled btn-primary"> <span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span><%
                                                if (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") | rol.trim().equals("ROL-0003") | rol.trim().equals("ROL-0007") | rol.trim().equals("ROL-0001") | rol.trim().equals("ROL-0012")) {
                                                    out.print("Registrar Documentos");
                                                } else {
                                                    out.print(" Visualizar Documentos");
                                                }%>  </a>      <%}%>
                                        </div>

                                    </div>
                                </div>
                                <table id="dt_basic" class="table table-striped table-bordered table-hover"  width="100%">
                                    <thead>
                                        <tr>
                                            <th data-hide="phone" >Nro</th>
                                            <th data-class="expand" >Nombres </th>
                                            <th data-hide="phone,tablet" >Documento</th>
                                            <th data-hide="phone" >Descripci�n</th>
                                        </tr>
                                    </thead>
                                    <tbody>

                                        <%  if (Lis_doc_trabajador_hab.size() == 0) {%>
                                        <%} else {%>
                                        <%for (int i = 0; i < Lis_doc_trabajador_hab.size(); i++) {
                                                Lis_Doc_tra d = new Lis_Doc_tra();
                                                d = (Lis_Doc_tra) Lis_doc_trabajador_hab.get(i);
                                        %>
                                        <tr>
                                            <td ><%=i + 1%></td>
                                            <td><%=d.getNo_documento()%></td>
                                            <td ><%out.print(doc_.List_file_url2(d.getId_documento_adjunto().trim()));%></td>
                                            <%if (d.getDe_documento_adjunto() != null) {
                                            %>
                                            <td ><%=d.getDe_documento_adjunto()%></td>
                                            <%} else {%>
                                            <td >No Registrado</td>
                                            <%}%>
                                        </tr>
                                        <% }
                                            }%>
                                    </tbody>
                                </table>


                            </div>
                            <!-- end widget content -->

                        </div>
                        <!-- end widget div -->


                    </div>
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
<!--================================================== -->


        <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script>
            if (!window.jQuery) {
                document.write('<script src="js/libs/jquery-2.0.2.min.js"><\/script>');
            }
        </script>

        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script>
            if (!window.jQuery.ui) {
                document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
            }
        </script>

<!--         PAGE RELATED PLUGIN(S)-->
<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script src="js/plugin/flot/jquery.flot.tooltip.min.js"></script>


<script type="text/javascript" src="js/shadowbox/demo.js"></script>
<script type="text/javascript" src="js/shadowbox/shadowbox.js"></script>
<script type="text/javascript">
    Shadowbox.init({
        overlayOpacity: 0.8
    }, setupDemos);
    $(document).ready(function () {
        pageSetUp();
        /* BASIC ;*/
        var responsiveHelper_dt_basic = undefined;
        var responsiveHelper_datatable_fixed_column = undefined;
        var responsiveHelper_datatable_col_reorder = undefined;
        var responsiveHelper_datatable_tabletools = undefined;

        var breakpointDefinition = {
            tablet: 1024,
            phone: 480
        };

        $('#dt_basic').dataTable({
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-12 hidden-xs'l>r>" +
                    "t" +
                    "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_dt_basic) {
                    responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($('#dt_basic'), breakpointDefinition);
                }
            },
            "rowCallback": function (nRow) {
                responsiveHelper_dt_basic.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                responsiveHelper_dt_basic.respond();
            }
        });


    });
</script>


</center>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>