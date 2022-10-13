<%@page import="com.app.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%@page import="com.app.model.X_User_dgp"%>
<jsp:useBean class="java.util.ArrayList" id="USER_DGP" scope="session"/>

        <!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

        <%HttpSession sesion = request.getSession();
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
                            <div class="jarviswidget jarviswidget-color-teal" id="wid-id-0" data-widget-editbutton="false">
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
                                    <h2>Proximos usuarios a intervenir</h2>

                                </header>

                                <!-- widget div-->
                                <div>

                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body no-padding">

                                        <table class="table table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                                <tr>

                                                    <th data-class="expand"><i class="text-muted hidden-md hidden-sm hidden-xs"></i> Nro. Paso</th>
                                                    <th ><i class="text-muted hidden-md hidden-sm hidden-xs"></i> Usuarios</th>
                                                    <th data-hide="phone">Nombres y Apellidos</th>
                                                    <th data-hide="phone,tablet"><i class=" txt-color-blue hidden-md hidden-sm hidden-xs"></i> Puesto</th>
                                                    <th data-hide="phone,tablet">Departamento</th>
                                                    <th data-hide="phone,tablet"><i class="txt-color-blue hidden-md hidden-sm hidden-xs"></i> Descripcion</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for (int i = 0; i < USER_DGP.size(); i++) {
                                                        X_User_dgp u = new X_User_dgp();
                                                        u = (X_User_dgp) USER_DGP.get(i);
                                                %> <tr>
                                                    <td class=""><%=u.getNu_pasos()%></td>
                                                    <td class=""><%=u.getNo_usuario()%></td>
                                                    <td class=""><%=u.getAp_paterno() + " " + u.getAp_materno() + " " + u.getNo_trabajador()%></td>
                                                    <td class=""><%=u.getNo_puesto()%></td>
                                                    <td class=""><%=u.getNo_dep()%></td>
                                                    <td class=""><%=u.getDe_pasos()%></td>
                                                </tr>
                                                <%}%>

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