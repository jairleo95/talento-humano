<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%-- 
    Document   : Evaluacion_Empleado
    Created on : 05-dic-2014, 15:44:19
    Author     : ALFA 3
--%>
 <link href="css/businessLogic/Empleado/evaluacionEmpleado.css" rel="stylesheet" type="text/css"/>
    <center>
        <!--begin widget-->
        <div class="jarviswidget slideInDown fast animated" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">

            <header>
                <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                <h2><b>Evaluar Empleado</b></h2>				

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

                        <fieldset >
                            <center>
                                <section class="col col-4" id="sec">
                                    <label class="select" >
                                        <select name="RE_EVALUACION" required="" class="input-sm">
                                            <option value="" selected=""  disabled="">[Seleccione]</option>
                                            <option value="0">No es Jefe</option>
                                            <option value="1">Jefe de Secci�n</option>
                                            <option value="2">Jefe de Area</option>
                                            <option value="3">Jefe de Departamento</option>
                                            <option value="4">Jefe de Direcci�n</option>

                                        </select> 
                                        <i></i> 
                                    </label>
                                </section>
                            </center>
                        </fieldset>

                        <input type="hidden" value="Reg_Evaluar_Emp" name="opc"/>
                        <input type="hidden" value="1" name="ESTADO"/>
                        <input type="hidden" name="ID_TRABAJADOR" value="<%=request.getParameter("idtr")%>"  >
                        <footer>
                            <button type="submit" class="btn btn-primary" name="opc">
                                Registrar
                            </button>
                        </footer>
                    </form>

                </div>
                <!-- end widget content -->

            </div>
            <!-- end widget div -->

        </div>
        <!-- end widget -->

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>