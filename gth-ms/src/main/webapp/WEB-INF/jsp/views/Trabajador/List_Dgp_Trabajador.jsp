<%@page import="com.app.controller.util.DateFormat"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>

<%@page import="com.app.domain.model.x_List_Id_Trab_Dgp"%>
<jsp:useBean id="LIST_ID_TRAB_DGP" class="java.util.ArrayList" scope="session"/>
<div id="main" role="main" style="margin-left: 0px;">
    <!-- MAIN CONTENT -->
    <div id="content">
        <div class="jarviswidget jarviswidget-color-darken slideInDown fast animated" id="wid-id-0" data-widget-editbutton="false">
            <header>
                <span class="widget-icon"> <i class="fa fa-list"></i> </span>
                <h2 class="font-md"><strong>Historial </strong> <i>Requerimientos</i></h2>
            </header>
            <div>
                <div class="widget-body no-padding">
                    <table id="dt_basic" class="table table-striped table-bordered table-hover" width="100%">
                        <thead>
                            <tr>
                                <th data-hide="phone">Nro</th>
                                <th data-class="phone,tablet">Fecha Desde</th>
                                <th data-hide="phone,tablet">Fecha Hasta</th>
                                <th data-hide="expand">Motivo</th>
                                <th data-hide="phone,tablet">Puesto</th>
                                <th data-hide="phone,tablet">Secci�n</th>
                                <th data-hide="phone,tablet">Area</th>
                                <th data-hide="phone,tablet">Estado</th>
                                <th data-hide="phone,tablet">Documentos</th>
                                <th data-hide="phone,tablet">Detalle</th>
                            </tr>
                        </thead>
                        <%
                            if (LIST_ID_TRAB_DGP.size() == 0) {%>

                        <%}%>

                        <tbody>
                            <%for (int i = 0; i < LIST_ID_TRAB_DGP.size(); i++) {
                                    x_List_Id_Trab_Dgp d = new x_List_Id_Trab_Dgp();
                                    d = (x_List_Id_Trab_Dgp) LIST_ID_TRAB_DGP.get(i);
                            %>
                            <tr>
                                <td ><%=i + 1%></td>
                                <td ><%=d.getFe_desde()%></td>
                                <td ><%=d.getFe_hasta()%></td>
                                <td ><%=d.getNo_req()%></td>
                                <td ><%=d.getNo_puesto()%></td>
                                <td ><%=d.getNo_seccion()%></td>

                                <td ><%=d.getNo_area()%></td>

                                <td >
                                    <%
                                        if (d.getEs_dgp() != null) {
                                            if (d.getEs_dgp().trim().equals("0")) {
                                    %>
                                    <a href="dgp?iddgp=<%=d.getId_dgp().trim()%>&opc=Seguimiento" class="btn btn-labeled btn-primary"> <span class="btn-label"><i class="glyphicon glyphicon-arrow-right"></i></span>En Proceso </a>
                                            <% }%>
                                            <%if (d.getEs_dgp().trim().equals("1")) {
                                            %>
                                    <a href="dgp?iddgp=<%=d.getId_dgp().trim()%>&opc=Seguimiento" class="btn btn-labeled btn-success"> <span class="btn-label"><i class="glyphicon glyphicon-ok"></i></span>Ver Proceso Terminado </a>
                                            <% }
                                            } else {%>
                                    <a href="dgp?iddgp=<%=d.getId_dgp().trim()%>&opc=Seguimiento" class="btn btn-labeled btn-danger"> <span class="btn-label"><i class="glyphicon glyphicon-thumbs-down"></i></span>Incompleto - Interrumpido</a>
                                            <%}%>
                                </td>
                                <td >

                                    <a href="documento?iddgp=<%=d.getId_dgp().trim()%>&idtr=<%=d.getId_trabajador().trim()%>&opc=Ver_Documento"class="btn btn-labeled btn-default"> <span class="btn-label"><i class="glyphicon glyphicon-chevron-right"></i></span>Ver Documentos </a>
                                </td>


                                <td>
                                    <a href="dgp?iddgp=<%=d.getId_dgp().trim()%>&idtr=<%=d.getId_trabajador().trim()%>&opc=Detalle" class="btn btn-labeled btn-info"> <span class="btn-label"><i class="glyphicon glyphicon-info-sign"></i></span>Ver Detalle</a>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>


                </div>


            </div>

        </div>





    </div>
    <!-- end widget div -->

</div>

<!-- PAGE RELATED PLUGIN(S) -->
<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

<script type = "text/javascript" >
    // DO NOT REMOVE : GLOBAL FUNCTIONS!
    $(document).ready(function () {
        pageSetUp();

        /* // DOM Position key index //

         l - Length changing (dropdown)
         f - Filtering input (search)
         t - The Table! (datatable)
         i - Information (records)
         p - Pagination (paging)
         r - pRocessing
         < and > - div elements
         <"#id" and > - div with an id
         <"class" and > - div with a class
         <"#id.class" and > - div with an id and class

         Also see: http://legacy.datatables.net/usage/features
         */

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

        /* END BASIC */
        // custom toolbar
        $("div.toolbar").html('<div class="text-right"><img src="../img/logo.png" alt="SmartAdmin" style="width: 111px; margin-top: 3px; margin-right: 10px;"></div>');

    });

</script>
</center>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>