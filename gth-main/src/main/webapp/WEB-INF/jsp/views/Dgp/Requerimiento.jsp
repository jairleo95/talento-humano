<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<jsp:useBean class="java.util.ArrayList" id="LIST_DGP_PROCESO" scope="session" />

<link href="css/businessLogic/Dgp/Requerimiento.css" rel="stylesheet" type="text/css"/>
    <body class="">

        <!-- MAIN PANEL -->
        <div id="main" role="main" style="margin: 0px;">
            <!-- MAIN CONTENT -->
            <div id="content" style="margin: 0px;" >
                <center>
                    <div class="well">

                        <%String opc = request.getParameter("opc");
                        if (opc.equals("Planilla")) {%>

                        <section id="service" class="wow fadeInUp" style="margin: 0px; ">
                            <div class="container" style="margin: 0px; margin-top: 2%;">
                                <div class="row">
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=1"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Contrato - Tiempo Completo</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End first service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=2"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Contrato - Medio Tiempo</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 2nd service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href=Generar_Dgp.jsp?text=3><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Contrato - Tiempo Parcial</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 3rd service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=5"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Extranjero</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3" style="display: none">
                                        <div class="service-content last bounceIn animated">
                                            <a href="#."><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Tiempo Parcial (Trabajador Docente)</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=7"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Practicas Pre Profesionales</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End first service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=8"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Practicas Profesionales</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 2nd service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=9"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Convenio Laboral Juvenil</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 3rd service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=10"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Locaci�n de Servicios</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=11"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>No Domiciliado (Expositores extranjeros)</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=20"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>MFL - Medio Tiempo</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=21"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>MFL - Tiempo Completo</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=22"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Empleado</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                    <div class="col-md-3 col-sm-3">
                                        <div class="service-content last bounceIn animated">
                                            <a href="Generar_Dgp.jsp?text=23"><span class="service-icon"><i class="fa fa-file img-circle wow flipInX"></i></span></a>
                                            <h3>Misionero</h3>
                                            <p>

                                            </p>
                                        </div>
                                    </div><!--/ End 4th service -->
                                </div>
                            </div>
                        </section><!-- Service box end -->
                        <%}%>
                        <!-- end widget grid -->
                    </div>
                </center>
            </div>
            <!-- END MAIN CONTENT -->
        </div>
        <!-- END MAIN PANEL -->
    </body>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
