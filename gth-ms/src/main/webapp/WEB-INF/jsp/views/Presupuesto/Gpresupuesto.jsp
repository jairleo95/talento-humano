<%-- 
    Document   : Gpresupuesto
    Created on : 24/06/2017, 09:37:35 PM
    Author     : Leandro Burgos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
        <link type="text/css" rel="stylesheet" href="css/Css_Reporte/Reportes.css">
        <link type="text/css" rel="stylesheet" href="css/Css_Formulario/form.css">
        <link href="js/plugin/pfnotify/pnotify.custom.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/datatable.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="col-lg-2 col-md-2">
            <div class="list-group"><br/><br/>
                <button type="button" id="v2" onclick="changeOption(this.id)" class="list-group-item us" >Actualizar Presupuesto</button>
                <button type="button" id="v1" onclick="changeOption(this.id)" class="list-group-item us active">Asignar Presupuesto</button>
                <button type="button" id="v3" onclick="changeOption(this.id)" class="list-group-item us">Historial de Presupuesto</button>
            </div>
        </div>
        <div id="vcont" class="col-md-9 col-lg-9">            
        </div>        
        <input type="hidden" id="vopt" value="1">
        <input type="hidden" id="id_cc" value="1">
        <input type="hidden" id="tipo_p" value="0">


<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
<script src="ajax/ajax.google.min.js"></script>
<script src="js/bootstrap/bootstrap.min.js" type="text/javascript"></script>
<script src="js/plugin/pfnotify/pnotify.custom.min.js" type="text/javascript"></script>
<script src="js/plugin/datatables/datatable.js" type="text/javascript"></script>
<script src="LogicPresup.js" type="text/javascript"></script>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
