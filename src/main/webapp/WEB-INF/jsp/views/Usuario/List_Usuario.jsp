<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.domain.model.V_Var_Usuario"%>
<jsp:useBean id="List_Usuario_var" scope="session" class="java.util.ArrayList"/>

<div id="main" role="main" style="margin-left: 0px;">
    <div id="content">
        <section id="widget-grid" class="">
            <div class="row">
                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div class="jarviswidget jarviswidget-color-blueDark" id="wid-id-3" data-widget-editbutton="false">
                        <header>
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2>Usuarios</h2>
                        </header>
                        <div>
                            <div class="jarviswidget-editbox">
                            </div>
                            <div class="widget-body no-padding">
                                <table id="datatable_tabletools" class="table table-striped table-bordered table-hover" width="100%">
                                    <thead>
                                        <tr>
                                            <th data-hide="phone">ID</th>
                                            <th>Accion</th>
                                            <th data-class="expand">Nombres y Apellidos</th>
                                            <th>Rol</th>
                                            <th>Puesto</th>
                                            <th>Seccion</th>
                                            <th>Area</th>
                                            <th>Departamento</th>
                                            <th>Usuario</th>
                                            <th>Clave</th>
                                            <th>Estado</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%for (int i = 0; i < List_Usuario_var.size(); i++) {
                                                V_Var_Usuario v = new V_Var_Usuario();
                                                v = (V_Var_Usuario) List_Usuario_var.get(i);
                                        %>
                                        <tr>
                                            <td><%=i + 1%></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                                        Accion <span class="caret"></span>
                                                    </button>
                                                    <ul class="dropdown-menu" role="menu">
                                                        <li><a class="modificar" href=Usuario?id_Usuaio=<%=v.getId_usuario()%>&opc=Mod_Usuario_con"  >Editar</a></li>
                                                        <li><a class="eliminar" id="<%=i%>">Eliminar<input class="btnId<%=i%>" type="hidden" value="<%=v.getId_usuario()%>"></a></li>
                                                    </ul>
                                                </div>
                                            </td>
                                            <td class="name"><a href="trabajador?idtr=<%=v.getId_trabajador() %>&opc=list"><%=v.getNo_trabajador() + " " + v.getAp_paterno() + " " + v.getAp_materno()%></a></td>
                                            <td><%=v.getNo_rol()%></td>
                                            <td><%=v.getNo_puesto()%></td>
                                            <td><%=v.getNo_seccion()%></td>
                                            <td><%=v.getNo_area()%></td>
                                            <td><%=v.getNo_dep()%></td>
                                            <td><%=v.getNo_usuario()%></td>
                                            <td><a href=Usuario?id_Usuaio=<%=v.getId_usuario()%>&opc=Modificar_clave_1"><%=v.getPw_usuario()%> </a></td>
                                            <% if (v.getEs_usuario().equals("1")) {
                                            %>
                                            <td><button class="desactivar btn btn-warning" id="<%=i%>">Desactivar</button></td>
                                            <%} else if (v.getEs_usuario().equals("0")) {%>
                                            <td><button class="activar btn btn-success" id="<%=i%>">Activar</button></td>
                                            <%}%>
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
            <!-- end row -->
        </section>
        <!-- end widget grid -->
    </div>
    <!-- END MAIN CONTENT -->
</div>
<!-- END MAIN PANEL -->

<script type="text/javascript">
    $(document).ready(function() {
        pageSetUp();
        var valorr;
        var idUser;
        /*
         * SmartAlerts
         */
        // With Callback

        $(".activar").click(function(e) {
            valorr = $(this).attr('id');
            idUser = $('.btnId' + valorr).val();
            $.SmartMessageBox({
                title: "Activar usuario!",
                content: "�Esta seguro de habilitar el usuario?",
                buttons: '[No][Si]'
            }, function(ButtonPressed) {
                if (ButtonPressed === "Si") {
                    $.post(Usuario", "id_Usuaio=" + idUser + "&opc=Activar_Usuario_con", function() {
                        $.smallBox({
                            title: "Activar Usuario",
                            content: "<i class='fa fa-clock-o'></i> <i>El Usuario ha sido activado </i>",
                            color: "#659265",
                            iconSmall: "fa fa-check fa-2x fadeInRight animated",
                            timeout: 4000
                        });
                        window.location.href = "";
                    });
                }
                if (ButtonPressed === "No") {
                    $.smallBox({
                        title: "Activar Usuario",
                        content: "<i class='fa fa-clock-o'></i> <i>Operacion Cancelada</i>",
                        color: "#C46A69",
                        iconSmall: "fa fa-times fa-2x fadeInRight animated",
                        timeout: 4000
                    });
                }

            });
            e.preventDefault();
        })
        $(".desactivar").click(function(e) {
            valorr = $(this).attr('id');
            idUser = $('.btnId' + valorr).val();
            $.SmartMessageBox({
                title: "Desactivar usuario!",
                content: "�Esta seguro de deshabilitar el usuario?",
                buttons: '[No][Si]'
            }, function(ButtonPressed) {
                if (ButtonPressed === "Si") {
                    $.post(Usuario", "id_Usuaio=" + idUser + "&opc=Desac_Usuario_con", function() {
                        $.smallBox({
                            title: "Desactivar Usuario",
                            content: "<i class='fa fa-clock-o'></i> <i>El Usuario ha sido desactivado </i>",
                            color: "#659265",
                            iconSmall: "fa fa-check fa-2x fadeInRight animated",
                            timeout: 4000
                        });
                        window.location.href = "";
                    });
                }
                if (ButtonPressed === "No") {
                    $.smallBox({
                        title: "Desactivar Usuario",
                        content: "<i class='fa fa-clock-o'></i> <i>Operacion Cancelada</i>",
                        color: "#C46A69",
                        iconSmall: "fa fa-times fa-2x fadeInRight animated",
                        timeout: 4000
                    });
                }

            });
            e.preventDefault();
        })
        $(".eliminar").click(function(e) {
            valorr = $(this).attr('id');
            idUser = $('.btnId' + valorr).val();
            $.SmartMessageBox({
                title: "Eliminar usuario!",
                content: "�Esta seguro de eliminar el usuario?",
                buttons: '[No][Si]'
            }, function(ButtonPressed) {
                if (ButtonPressed === "Si") {
                    $.post(Usuario", "id_usuario=" + idUser + "&opc=Eliminar_Usuario", function() {
                        $.smallBox({
                            title: "Eliminar Usuario",
                            content: "<i class='fa fa-clock-o'></i> <i>El Usuario ha sido eliminado </i>",
                            color: "#659265",
                            iconSmall: "fa fa-check fa-2x fadeInRight animated",
                            timeout: 4000
                        });
                        window.location.href = "";
                    });
                }
                if (ButtonPressed === "No") {
                    $.smallBox({
                        title: "Eliminar Usuario",
                        content: "<i class='fa fa-clock-o'></i> <i>Operacion Cancelada</i>",
                        color: "#C46A69",
                        iconSmall: "fa fa-times fa-2x fadeInRight animated",
                        timeout: 4000
                    });
                }

            });
            e.preventDefault();
        })

    });
</script>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
