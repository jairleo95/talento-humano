<%    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="com.app.model.V_List_Empleado"%>
<%@page import="com.app.model.Rol"%>
<jsp:useBean id="Listar_Emp" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Rol" scope="session" class="java.util.ArrayList"/>
<div class="row">
    <center>
        <h1>Mantenimiento a Usuarios de  Personal</h1>
        <a href="ListaUsuario.jsp">New</a>
        <hr>
    </center>
    <div class="contenedor">
        <article class="col-sm-12">
            <div id="wid-id-0" class="jarviswidget" role="widget">
                <div>
                    <div class="jarviswidget-editbox">
                    </div>
                    <div class="widget-body no-padding ">
                        <form class="smart-form" action="">
                            <fieldset>
                                <h2>Registro de Usuarios </h2>
                            </fieldset>
                            <fieldset>
                                <section class="col col-3">
                                    <label class="label">Empleado:</label>
                                    <label class="select">
                                        <select class="form-control" name="IDEMPLEADO">
                                            <option value="">[Seleccione]</option>
                                            <%for (int i = 0; i < Listar_Emp.size(); i++) {
                                                V_List_Empleado ve = new V_List_Empleado();
                                                ve = (V_List_Empleado) Listar_Emp.get(i);
                                            %>
                                            <option value="<%=ve.getId_empleado()%>"><%=ve.getNo_trabajador()%><%="     "%><%=ve.getAp_paterno()%><%="    "%><%=ve.getAp_materno()%></option>
                                            <%}%>
                                        </select>
                                        <i></i> </label>
                                </section>
                                <section class="col col-3">
                                    <label class="label">Rol:</label>
                                    <label class="select">
                                        <select class="form-control" name="IDROLES" >
                                            <option value="">[Seleccione]</option>
                                            <%for (int i = 0; i < List_Rol.size(); i++) {
                                                Rol r = new Rol();
                                                r = (Rol) List_Rol.get(i);
                                            %>
                                            <option value="<%=r.getId_rol()%>"><%=r.getNo_rol()%></option>
                                            <%}%>
                                        </select>
                                        <i></i> </label>
                                </section>
                                <section class="col col-3">
                                    <label class="label">Usuario:</label>
                                    <label class="input">
                                        <input type="text" required="" name="USUARIO"  >
                                        <i></i> </label>
                                </section>
                                <section class="col col-3">
                                    <label class="label">Clave:</label>
                                    <label class="input">
                                        <input type="password"  required="" name="CLAVE" >
                                        <i></i> </label>
                                </section>
                            </fieldset>
                            <footer>
                                <input type="submit" name="opc" class="btn btn-primary btn-sm" value="Registrar Usuario">
                                <input type="reset" class="btn btn-default btn-sm" value="Limpiar">
                            </footer>
                        </form>
                    </div>
                </div>
            </div>
        </article>
    </div>
</div>
<%@include file="List_Usuario.jsp" %>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>