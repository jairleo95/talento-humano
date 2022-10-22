<%-- 
    Document   : List_Funciones
    Created on : 29-ene-2015, 7:04:40
    Author     : joserodrigo
--%>
<%@page import="com.app.config.globalProperties"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.app.domain.model.Funciones"%>
<jsp:useBean id="Listar_funciones" scope="session" class="java.util.ArrayList"/>

<link href="css/businessLogic/Funciones/funciones.css" rel="stylesheet" type="text/css"/>
    <h1>Funciones</h1>
    <table class=" tabla table table-bordered"> 
        <thead>
            <tr>
                <td>N�</td>
                <td>Detalle Funcion</td>
                <td>Estado</td>
                <td>Puesto</td>
                <td>Tipo</td>
                <td colspan="2"><center>Opcion</center></td>
</tr>
</thead>
<tbody class="tbodys"></tbody>                    
</table>
<script src="js/businessLogic/Funciones/funciones.js" type="text/javascript"></script>