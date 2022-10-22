<%@page import="com.app.controller.util.DateFormat"%>
<%@page import="com.app.domain.model.Tipo_Documento"%>
<%@page import="com.app.persistence.dao.Tipo_DocumentoDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceTipo_DocumentoDAO"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.domain.model.Datos_Hijo_Trabajador"%>
<jsp:useBean id="LISTA_HIJO" scope="session" class="java.util.ArrayList"/>
<%
    HttpSession sesion2 = request.getSession(true);
    String rol1 = (String) sesion2.getAttribute("IDROL");
%>
<!-- widget div-->
<div>

    <!-- widget edit box -->
    <div class="jarviswidget-editbox">
        <!-- This area used as dropdown edit box -->

    </div>
    <!-- end widget edit box -->

    <!-- widget content -->
    <div class="widget-body no-padding">

        <table id="datatable_tabletools" class="table table-striped table-bordered table-hover" width="100%" style="margin-left:0.5%;" >
            <thead>
                <tr>
                    <th data-hide="phone">Nro</th>
                    <th data-class="expand">Apellidos y Nombres</th>
                    <th data-hide="phone">Fecha de nacimiento</th>
                    <th data-hide="phone">Sexo</th>
                    <th data-hide="phone,tablet">Tipo de Documento</th>
                    <th data-hide="phone,tablet">Nro Documento</th>
                    <th data-hide="phone,tablet">Essalud</th>
                    <th data-hide="phone,tablet">Est.Nivel Superior</th>
                    <th data-hide="phone,tablet" colspan="2">Opciones</th>
                </tr>

            </thead>
            <tbody>
                <%
                    InterfaceTipo_DocumentoDAO itd = new Tipo_DocumentoDAO();
                    for (int i = 0; i < LISTA_HIJO.size(); i++) {
                        Datos_Hijo_Trabajador h = new Datos_Hijo_Trabajador();
                        h = (Datos_Hijo_Trabajador) LISTA_HIJO.get(i);
                        if (h.getEs_datos_hijo_trabajador().trim().equals("1")) {
                %>
                <tr>
                    <td><%out.print(i + 1);%></td>
                    <td><%=h.getAp_paterno() + " " + h.getAp_materno() + " " + h.getNo_hijo_trabajador()%></td>
                    <td><%=DateFormat.toFormat5(h.getFe_nacimiento())%></td>
                    <td>
                        <%
                            if (h.getEs_sexo().trim().equals("M")) {
                                out.println("Masculino");
                            }
                            if (h.getEs_sexo().trim().equals("F")) {
                                out.println("Femenino");
                            }
                        %>
                    </td>
                    <td>
                        <%
                            for (int j = 0; j < itd.Listar_tipo_doc().size(); j++) {
                                Tipo_Documento td = new Tipo_Documento();
                                td = (Tipo_Documento) itd.Listar_tipo_doc().get(j);
                                if (h.getEs_tipo_doc().trim().equals(td.getId_tipo_doc_ident().trim())) {
                                    out.print(td.getDe_tdoc_abreviada());
                                }
                            }

                        %>
                    </td>
                    <td><%=h.getNu_doc()%></td>


                    <td>
                        <%
                            if (h.getEs_inscripcion_vig_essalud().trim().equals("1")) {
                                out.println("Si");
                            }
                            if (h.getEs_inscripcion_vig_essalud().trim().equals("0")) {
                                out.println("No");
                            }
                        %>
                    </td>
                    <td>
                        <%if (h.getEs_estudio_niv_superior() != null) {
                                if (h.getEs_estudio_niv_superior().trim().equals("1")) {
                                    out.println("Si");
                                }
                                if (h.getEs_estudio_niv_superior().trim().equals("0")) {
                                    out.println("No");
                                }
                            } else {
                                out.print("No");
                            }
                        %>
                    </td>
                    <td>

                        <% if (rol1.trim().equals("ROL-0002") | rol1.trim().equals("ROL-0005") | rol1.trim().equals("ROL-0001") | rol1.trim().equals("ROL-0013")) {%>

                        <a href="familiar?idhijo=<%=h.getId_datos_hijos_trabajador()%>&idtr=<%=h.getId_trabajador()%>&opc=modificar" >
                            <img src="img/lapiz.png" alt="" width="25px" height="25px"/></a>

                    </td>
                    <td>

                <a href="familiar?idhijo=<%=h.getId_datos_hijos_trabajador()%>&idtr=<%=h.getId_trabajador()%>&opc=eliminar"
                   class="eliminar" >
                    <img src="img/eliminar.png" alt=""   width="25px" height="25px"/></a>

            <%}%>
            </tr>
             <input type="hidden" class="idhijo" value="<%=h.getId_datos_hijos_trabajador()%>">
            <input type="hidden" class="idtr" value="<%=h.getId_trabajador()%>">
                <% }
                    }
                %>


            </tbody>
        </table>


    </div>
    <!-- end widget content -->

</div>
<!-- end widget div -->

<script type="text/javascript">
    $(document).ready(function() {

        pageSetUp();

        /* TABLETOOLS */
        $('#datatable_tabletools').dataTable({
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
                        "sTitle": "Requerimientos_PDF",
                        "sPdfMessage": "Requeriemintos PDF Export",
                        "sPdfSize": "letter"
                    },
                    {
                        "sExtends": "print",
                        "sMessage": "Generado por AlfaTeam <i>(presiona Esc para cerrar)</i>"
                    }
                ],
                "sSwfPath": js/plugin/datatables/swf/copy_csv_xls_pdf.swf"
            },
            "autoWidth": true,
            "preDrawCallback": function() {
                // Initialize the responsive datatables helper once.
                if (!responsiveHelper_datatable_tabletools) {
                    responsiveHelper_datatable_tabletools = new ResponsiveDatatablesHelper($('#datatable_tabletools'), breakpointDefinition);
                }
            },
            "rowCallback": function(nRow) {
                responsiveHelper_datatable_tabletools.createExpandIcon(nRow);
            },
            "drawCallback": function(oSettings) {
                responsiveHelper_datatable_tabletools.respond();
            }
        });

        /* END TABLETOOLS */
    })
</script>
<%} else {
    out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
   }
%>