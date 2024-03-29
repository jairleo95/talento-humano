<%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="com.app.domain.model.V_Horario"%>
<%@page import="com.app.domain.model.Detalle_Horario"%>
<%@page import="java.util.List"%>
<%@page import="com.app.persistence.dao.HorarioDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceHorarioDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceListaDAO"%>
<%@page import="com.app.persistence.dao.ListaDAO"%>
<%@page import="com.app.web.controller.CHorario"%>
<jsp:useBean id="List_V_Horario" scope="session" class="java.util.ArrayList"/>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalle Horario</title>
        <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">
        <!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-skins.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/demo.min.css">
        <!-- #FAVICONS -->
        <link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
        <link rel="icon" href="img/favicon/favicon.ico" type="image/x-icon">
        <!-- #GOOGLE FONT -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">
        <link href="css/businessLogic/Horario/detalleHorario.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>

        <div class="well">
            <div class="row">
                <section class="col col-xs-12">
                    <center>
                        <a class="pull-left btn btn-primary btn-circle btn-lg"onclick="history.back()"><i class="glyphicon glyphicon-arrow-left"></i></a>  
                        <h1 class=" page-title">Horario</h1>
                    </center>
                </section>
            </div>
        </div>
        <div class="cont">
            <%
                InterfaceListaDAO l = new ListaDAO();
                if (List_V_Horario.size() == 0) {
                    out.print("<center><h3>Tipo de Horario:&nbsp;<strong>No sujeto a Fiscaslizaci�n</strong></h3></center><hr>");
                } else {
                    V_Horario h = new V_Horario();
                    h = (V_Horario) List_V_Horario.get(0);
                    out.print("<center><h3>Tipo de Horario:&nbsp; <strong>" + h.getNo_ti_horario() + "</strong></h3></center><hr>");
                }
                for (int i = 0; i < l.List_H().length; i++) {
                    int g = 0;

                    for (int s = 0; s < List_V_Horario.size(); s++) {
                        V_Horario h = new V_Horario();
                        h = (V_Horario) List_V_Horario.get(s);
                        if (h.getDia_horario().trim().equals(l.List_H()[i][0])) {
                            if (g == 0) {%>

            <section class="col col-sm-4 col-md-4">
                <div class="caja">
                    <table class="table table-condensed table-bordered">
                        <tr class="c_header"><td colspan="3"  style="text-align: center;"><% out.println(l.List_H()[i][1]);%></td></tr>   
                            <%}%>
                        <tr>
                            <%if (h.getHo_desde().equals("00:00") && h.getHo_hasta().equals("00:00")) {%>
                            <%} else {%>
                            <td class="text-center">Turno <%=g + 1%></td>
                            <td class="text-center"><%out.println(h.getHo_desde()); %> </td>
                            <td class="text-center"><%out.println(h.getHo_hasta());%></td>
                            <%}%>
                        </tr>
                        <% g++; %>
                        <%}%>
                        <%}%>
                    </table>
                </div>
            </section>
            <%}%>
        </div>
        <%  if (request.getParameter("P2") != null) {
                if (request.getParameter("P2").equals("1")) {%>
        <a href="dgp?iddgp=<%=request.getParameter("iddgp")%>&idtr=<%=request.getParameter("idtr")%>&opc=rd">Continuar</a>
        <%}
            }%>
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
        <script src="js/app.config.js"></script>
        <script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script>
        <script src="js/bootstrap/bootstrap.min.js"></script>
        <script src="js/notification/SmartNotification.min.js"></script>
        <script src="js/smartwidgets/jarvis.widget.min.js"></script>
        <script src="js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>
        <script src="js/plugin/sparkline/jquery.sparkline.min.js"></script>
        <script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>
        <script src="js/plugin/masked-input/jquery.maskedinput.min.js"></script>
        <script src="js/plugin/select2/select2.min.js"></script>
        <script src="js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>
        <script src="js/plugin/msie-fix/jquery.mb.browser.min.js"></script>
        <script src="js/plugin/fastclick/fastclick.min.js"></script>
        <!--[if IE 8]>
        
        <h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>
        
        <![endif]-->

        <!-- Demo purpose only -->
        <script src="js/demo.min.js"></script>
        <script src="js/app.min.js"></script>
        <script src="js/speech/voicecommand.min.js"></script>
        <script src="js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
        <script src="js/plugin/fuelux/wizard/wizard.min.js"></script>
        <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
        <script src="js/plugin/jquery-nestable/jquery.nestable.min.js"></script>
        <script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
        <script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
        <script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
        <script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
        <script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

        <script src="js/businessLogic/Horario/detalleHorario.js" type="text/javascript"></script>
    </body>
</html>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>