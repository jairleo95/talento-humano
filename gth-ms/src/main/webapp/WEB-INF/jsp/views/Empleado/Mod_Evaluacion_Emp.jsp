<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%-- 
    Document   : Mod_Evaluacion_Emp
    Created on : 09/01/2015, 10:04:44 AM
    Author     : Alex
--%>

<%@page import="com.app.domain.model.Evaluacion_Emp"%>

<jsp:useBean id="LIST_EVALUACION" scope="session" class="java.util.ArrayList"/>

<link rel="stylesheet" href="css/Css_Formulario/form.css"  type="text/css" >

<style>
    #sec{
        margin-left:33%;
    }
    option{
        font-weight: bold;
    }
    button{
        width: 150px;
    }
</style>
    <center>
        <!--begin widget-->
        <div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">

            <header>
                <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                <h2><b>MODIFICAR EVALUACION</b></h2>				

            </header>

            <!-- widget div-->
            <div >

                <!-- widget edit box -->
                <div class="jarviswidget-editbox">
                    <!-- This area used as dropdown edit box -->

                </div>
                <!-- end widget edit box -->

                <!-- widget content -->
                <div class="widget-body no-padding">

                    <form action=empleado" id="checkout-form" class="smart-form" novalidate="novalidate">

                        <header>
                            <b>�El Empleado Es Jefe?</b>
                        </header>
                        <%
                           for (int i = 0; i < LIST_EVALUACION.size(); i++) {
                        Evaluacion_Emp e = new Evaluacion_Emp();
                        e = (Evaluacion_Emp) LIST_EVALUACION.get(i);

                        %>

                        <fieldset >
                            <center>
                                <section class="col col-4" id="sec">
                                    <label class="select" >
                                        <select name="RE_EVALUACION" required="" class="input-lg">
                                            <%
                                                if (e.getRe_evaluacion().equals("0")) {
                                            %>
                                            <option value="" disabled="">[SELECCIONE]</option>
                                            <option value="0" selected="">No es Jefe</option>
                                            <option value="1">Jefe de Secci�n</option>
                                            <option value="2">Jefe de Area</option>
                                            <option value="3">Jefe de Departamento</option>
                                            <option value="4">Jefe de Direcci�n</option>
                                            <% }
                                                if (e.getRe_evaluacion().equals("1")) {
                                            %>
                                            <option value="" disabled="">[SELECCIONE]</option>
                                            <option value="0">No es Jefe</option>
                                            <option value="1" selected="">Jefe de Secci�n</option>
                                            <option value="2">Jefe de Area</option>
                                            <option value="3">Jefe de Departamento</option>
                                            <option value="4">Jefe de Direcci�n</option>
                                            <% }
                                                if (e.getRe_evaluacion().equals("2")) {
                                            %>
                                            <option value="" disabled="">[SELECCIONE]</option>
                                            <option value="0">No es Jefe</option>
                                            <option value="1">Jefe de Secci�n</option>
                                            <option value="2" selected="">Jefe de Area</option>
                                            <option value="3">Jefe de Departamento</option>
                                            <option value="4">Jefe de Direcci�n</option>
                                            <% }
                                                if (e.getRe_evaluacion().equals("3")) {
                                            %>
                                            <option value="" disabled="">[SELECCIONE]</option>
                                            <option value="0">No es Jefe</option>
                                            <option value="1">Jefe de Secci�n</option>
                                            <option value="2">Jefe de Area</option>
                                            <option value="3" selected="">Jefe de Departamento</option>
                                            <option value="4">Jefe de Direcci�n</option>
                                            <% }
                                                if (e.getRe_evaluacion().equals("4")) {
                                            %>
                                            <option value="" disabled="">[SELECCIONE]</option>
                                            <option value="0">No es Jefe</option>
                                            <option value="1">Jefe de Secci�n</option>
                                            <option value="2">Jefe de Area</option>
                                            <option value="3">Jefe de Departamento</option>
                                            <option value="4" selected="">Jefe de Direcci�n</option>
                                            <%}%>

                                        </select> 
                                        <i></i> 
                                    </label>
                                </section>
                            </center>
                        </fieldset>

                        <input type="hidden" value="modificar" name="opc"/>
                        <input type="hidden" name="ID_EMPLEADO" value="<%=request.getParameter("idemp")%>"  >
                        <footer>
                            <button type="submit" class="btn btn-primary" name="opc">
                                Modificar
                            </button>
                        </footer>
                        <%}%>
                    </form>

                </div>
                <!-- end widget content -->

            </div>
            <!-- end widget div -->

        </div>
        <!-- end widget -->
    </body>
</html>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>