<%@page import="com.app.domain.model.Trabajador"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.domain.model.X_List_det_dgp"%>
<jsp:useBean id="List_Det_Dgp" scope="session" class="java.util.ArrayList"/>  
<jsp:useBean id="List_Trb_Mod_Rel" scope="session" class="java.util.ArrayList"/>  
            <!-- MAIN CONTENT -->
            <div id="content">
                <!-- widget grid -->
                <section id="widget-grid" class="">

                    <!-- row -->
                    <div class="row">

                        <!-- NEW WIDGET START -->
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

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
                                    <span class="widget-icon"> <i class="fa fa-list"></i> </span>
                                    <h2 class="font-md"><strong>Lista </strong> <i>Requerimientos</i></h2>
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
                                        <table id="dt_basic" class="table table-striped table-bordered table-hover" >
                                            <thead>
                                                <tr>
                                                    <th data-hide="phone">ID</th>
                                                    <th data-hide="phone">Mes</th>
                                                    <th data-hide="phone">A�o</th>
                                                    <th data-class="expand">Apellidos y Nmbres</th>
                                                    <th data-class="expand">Nro. Documento</th>
                                                    <th data-class="expand">C�digo APS</th>
                                                    <th>Motivo</th>
                                                    <th>Fecha Inicio</th>
                                                    <th data-hide="phone">Fecha Fin</th>
                                                    <th data-hide="phone,tablet">Sueldo</th>
                                                    <th data-hide="phone,tablet">Bono de Alimentos </th>
                                                    <th data-hide="phone,tablet">Bono por Funci�n </th>
                                                    <th data-hide="phone,tablet">Asig. Familiar</th>
                                                    <th data-hide="phone,tablet">Total Remun.</th>
                                                    <th data-hide="phone,tablet">Puesto</th>
                                                    <th data-hide="phone,tablet">Area</th>
                                                    <th data-hide="phone,tablet">Requerimiento</th>
                                                    <th data-hide="phone,tablet">Estado</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for (int i = 0; i < List_Det_Dgp.size(); i++) {
                                                        X_List_det_dgp x = new X_List_det_dgp();
                                                        x = (X_List_det_dgp) List_Det_Dgp.get(i); %>
                                                <tr>
                                                    <td><%out.print(i + 1);%></td>
                                                    <td><%=x.getMes_procesamiento()%></td>
                                                    <td><%=x.getAnno_procesamiento()%></td>
                                                    <td ><a href="trabajador?idtr=<%=x.getId_trabajador()%>&opc=list"><%=x.getAp_paterno().toUpperCase() + " " + x.getAp_materno().toUpperCase() + " " + x.getNo_trabajador().toUpperCase()%></a></td>
                                                   
                                                    <td><%=x.getNu_doc()%></td>
                                                    <td> <%
                                                        if (x.getCod_aps() == 0) {
                                                            out.print("");
                                                        } else {
                                                            out.print(x.getCod_aps());
                                                        }
                                                    %>
                                                   </td>
                                                    <td>  <%if (x.getLi_motivo().equals("2")) {
                                                            out.print("Renovaci�n");
                                                        } else if (x.getLi_motivo().equals("1")) {
                                                            out.print("Trabajador Nuevo");
                                                        }
                                                        %>
                                                    </td>   
                                                    <td><%=x.getFe_desde()%></td>
                                                    <td><%=x.getFe_hasta()%></td>
                                                    <td><%=x.getCa_sueldo()%></td>
                                                    <td><%=x.getCa_bono_alimentario()%></td>
                                                    <td><%=x.getCa_bonificacion_p()%></td>
                                                    <td><%=x.getCa_asig_familiar()%></td>
                                                    <td><%=x.getCa_sueldo() + x.getCa_bono_alimentario() + x.getCa_bonificacion_p() + x.getCa_asig_familiar()%></td>
                                                    <td><%=x.getNo_puesto()%></td>
                                                    <td><%=x.getNo_area()%></td>
                                                    <td><a href="dgp?iddgp=<%=x.getId_dgp()%>&idtr=<%=x.getId_trabajador()%>&opc=Detalle "><%=x.getNo_req()%></a></td>
                                                    <td><%
                                                        if (x.getEs_dgp().equals("1")) {
                                                            out.print("Terminado");
                                                        }
                                                        if (x.getEs_dgp().equals("0")) {
                                                            out.print("En Proceso");
                                                        }
                                                        %></td>
                                                </tr>
                                                <%}
                                                    List_Det_Dgp.clear();%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- end widget content -->
                                </div>
                            </div>
                            <!-- end widget div -->
                            <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false">
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
                                    <h2 class="font-md"><strong>Trabajadores </strong> <i>Modificados</i></h2>

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

                                        <table id="dt_modified_employees" class="table table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                                <tr>
                                                    <th data-hide="phone">ID</th>
                                                    <th data-class="expand">Nombres y Apellidos</th>
                                                    <th>DNI</th>
                                                    <th data-hide="phone">Religi�n</th>
                                                    <th data-hide="phone,tablet">E-mail</th>
                                                    <th data-hide="phone,tablet">Celular</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <% for (int i = 0; i < List_Trb_Mod_Rel.size(); i++) {
                                                        Trabajador e = new Trabajador();
                                                        e = (Trabajador) List_Trb_Mod_Rel.get(i);
                                                %>
                                                <tr>
                                                    <td><%out.print(i + 1);%></td>
                                                    <td>
                                                        <a href="trabajador?idtr=<%=e.getId_trabajador()%>&opc=list"><%=e.getNo_trabajador().toUpperCase() + " " + e.getAp_paterno().toUpperCase() + " " + e.getAp_materno().toUpperCase()%></a></td>
                                                    <td><%=e.getNu_doc_c()%></td>
                                                    <td>
                                                        <%
                                                            if (e.getLi_religion().trim().equals("1")) {
                                                                out.print("Adventista");
                                                            }
                                                            if (e.getLi_religion().trim().equals("2")) {
                                                                out.print("Cat�lico");
                                                            }
                                                            if (e.getLi_religion().trim().equals("3")) {
                                                                out.print("Otros");
                                                            }%>
                                                    </td>
                                                    <td><%=e.getDi_correo_personal()%></td>
                                                    <td align="center">
                                                        <%if (e.getCl_tra() == null) {
                                                                out.print("-");
                                                            }%>
                                                    </td>

                                                </tr>
                                                <%}%> 


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

                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

    <script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>


<script type="text/javascript">

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
        var responsiveHelper_datatable_tabletools = undefined;

        var breakpointDefinition = {
            /*tablet: 1024,
             phone: 480*/
        };

        $('#dt_basic').dataTable({
            // Tabletools options: 
            //   https://datatables.net/extensions/tabletools/button_options
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'T>r>" +
                    "t" +
                    "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
            "oTableTools": {
                "aButtons": [
                    "copy",
                    "csv",
                    "xls",
                    {
                        "sExtends": "pdf",
                        "sTitle": "Lista_Requerimientos_PDF",
                        "sPdfMessage": "Empleados PDF Export",
                        "sPdfSize": "letter"
                    },
                    {
                        "sExtends": "print",
                        "sMessage": "Generado por AlfaTeam <i>(presiona Esc para cerrar)</i>"
                    }
                ],
                "sSwfPath": "js/plugin/datatables/swf/copy_csv_xls_pdf.swf"
            },
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_datatable_tabletools) {
                    responsiveHelper_datatable_tabletools = new ResponsiveDatatablesHelper($('#dt_basic'), breakpointDefinition);
                }
            },
            "rowCallback": function (nRow) {
                responsiveHelper_datatable_tabletools.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                responsiveHelper_datatable_tabletools.respond();
            }
        });

        /* END BASIC */
        /* TABLETOOLS */

        /* END COLUMN SHOW - HIDE */
        var responsiveHelper_dt_modified_employees = undefined;
        /* TABLETOOLS */
        $('#dt_modified_employees').dataTable({
            "sDom": "<'dt-toolbar'<'col-xs-12 col-sm-6'f><'col-sm-6 col-xs-6 hidden-xs'T>r>" +
                    "t" +
                    "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-sm-6 col-xs-12'p>>",
            "oTableTools": {
                "aButtons": [
                    "copy",
                    "csv",
                    "xls",
                    {
                        "sExtends": "pdf",
                        "sTitle": "Requerimientos_PDF",
                        "sPdfMessage": "Requeriemintos PDF Export",
                        "sPdfSize": "letter"
                    },
                    {
                        "sExtends": "print",
                        "sMessage": "Generado por AlfaTeam <i>(presiona Esc para cerrar)</i>"
                    }
                ],
                "sSwfPath": "js/plugin/datatables/swf/copy_csv_xls_pdf.swf"
            },
            "autoWidth": true,
            "preDrawCallback": function () {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_dt_modified_employees) {
                    responsiveHelper_dt_modified_employees = new ResponsiveDatatablesHelper($('#dt_modified_employees'), breakpointDefinition);
                }
            },
            "rowCallback": function (nRow) {
                responsiveHelper_dt_modified_employees.createExpandIcon(nRow);
            },
            "drawCallback": function (oSettings) {
                responsiveHelper_dt_modified_employees.respond();
            }
        });

        /* END TABLETOOLS */


    });

        </script>


</html>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>                                                                                                                                        