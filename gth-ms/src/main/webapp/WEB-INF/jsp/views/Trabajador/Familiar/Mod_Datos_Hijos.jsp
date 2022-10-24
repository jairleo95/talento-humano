<%@page import="com.app.controller.util.DateFormat"%>
<%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.domain.model.Tipo_Documento"%>
<%    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="com.app.domain.model.Datos_Hijo_Trabajador"%>
<jsp:useBean id="Lista_hijo_individual" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="Listar_tipo_doc" scope="session" class="java.util.ArrayList"/>
<style>
    #btn-duplicar{
        margin: 1%;
        margin-left:  6%;
    }
    label{
        font-weight: bold;
    }
</style>
<div id="main" role="main" style="margin-left: 0px;">
    <!-- MAIN CONTENT -->
    <div id="content">
        <!-- widget grid -->
        <section id="widget-grid" class="" >
            <!-- row -->
            <div class="row">

                <!-- NEW WIDGET START -->
                <article class="col-sm-12 col-md-12 col-lg-6" style="width: 95%">

                    <!-- Widget ID (each widget will need unique ID)-->
                    <div class="jarviswidget jarviswidget-color-darken" id="wid-id-0" data-widget-editbutton="false" data-widget-deletebutton="false"  >
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
                            <center>
                                <span class="widget-icon"> <i class="fa fa-male"></i> </span>
                                <h2>MODIFICAR DATOS DEL HIJO</h2>
                            </center>

                        </header>

                        <!-- widget div-->
                        <div>

                            <!-- widget edit box -->
                            <div class="jarviswidget-editbox">
                                <!-- This area used as dropdown edit box -->

                            </div>
                            <!-- end widget edit box -->

                            <!-- widget content -->
                            <div class="widget-body">

                                <div class="row" >
                                    <form id="wizard-1" action=../familiar">
                                        <%
                                            String rol = (String) sesion.getAttribute("IDROL");
                                            for (int i = 0; i < Lista_hijo_individual.size(); i++) {
                                                Datos_Hijo_Trabajador d = new Datos_Hijo_Trabajador();
                                                d = (Datos_Hijo_Trabajador) Lista_hijo_individual.get(i);
                                        %>

                                        <div id="bootstrap-wizard-1" class="col-sm-12">
                                            <div class="tab-content">
                                                <div class="row">

                                                    <div class="" >
                                                        <div class="col-sm-4">

                                                            <div class="form-group">
                                                                <label >Apellido Paterno:</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                    <input type="text" name="APELLIDO_P" value="<%=d.getAp_paterno()%>" required="" id="fname"   class="form-control input-group-sm"  onblur="this.value = this.value.toUpperCase()" maxlength="50" >

                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="col-sm-4">
                                                            <div class="form-group">
                                                                <label >Apellido Materno:</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                    <input type="text" name="APELLIDO_M" value="<%=d.getAp_materno()%>" required=""  id="lname"  class="form-control input-group-sm" onblur="this.value = this.value.toUpperCase()" maxlength="50" >

                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-sm-4">
                                                            <div class="form-group">
                                                                <label>Nombre(s):</label>
                                                                <div class="input-group">
                                                                    <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                    <input type="text" name="NOMBRE" value="<%=d.getNo_hijo_trabajador()%>" required=""  id="lname"  class="form-control input-group-sm"  onblur="this.value = this.value.toUpperCase()" maxlength="50" >

                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Fecha de Nacimiento:</label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-child fa-lg fa-fw"></i><label class="edad"></label></span>
                                                                <input type="text" name="FECHA_NAC" required=""
                                                                       <%if (d.getFe_nacimiento() != null) {%>
                                                                       value="<%=DateFormat.toFormat4(d.getFe_nacimiento())%>"
                                                                       <%} else {%>

                                                                       <%}%>
                                                                       class="form-control input-group-sm datePickerInput"  data-mask="99/99/9999" data-mask-placeholder= "_" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Sexo:</label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-flag fa-lg fa-fw"></i></span>
                                                                <select name="SEXO" class="form-control input-group-sm"   required="" >
                                                                    <%
                                                                        if (d.getEs_sexo() != null) {
                                                                            if (d.getEs_sexo().equals("M")) {
                                                                    %>
                                                                    <option value="M" selected="">Masculino</option>
                                                                    <option value="F" >Femennino</option>
                                                                    <%}
                                                                    %>
                                                                    <%
                                                                        if (d.getEs_sexo().equals("F")) {
                                                                    %>
                                                                    <option value="M" >Masculino</option>
                                                                    <option value="F" selected="">Femennino</option>
                                                                    <%}
                                                                    } else {%>
                                                                    <option value="" >[SELECCIONE]</option>
                                                                    <option value="M" >Masculino</option>
                                                                    <option value="F" >Femennino</option>
                                                                    <%}
                                                                    %>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Tipo Documento:</label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-flag fa-lg fa-fw"></i></span>
                                                                <select  name="TIPO_DOC_ID" class="form-control input-group-sm ti_doc"   required="" >
                                                                    <option value="">[SELECCIONE]</option>
                                                                    <%
                                                                        for (int h = 0; h < Listar_tipo_doc.size(); h++) {
                                                                            Tipo_Documento tdoc = new Tipo_Documento();
                                                                            tdoc = (Tipo_Documento) Listar_tipo_doc.get(h);
                                                                            if (d.getEs_tipo_doc().trim().equals(tdoc.getId_tipo_doc_ident().trim())) {
                                                                    %>
                                                                    <option value="<%=tdoc.getId_tipo_doc_ident().trim()%>" selected="" ><%=tdoc.getDe_tdoc_abreviada()%></option>
                                                                    <%} else {%>
                                                                    <option value="<%=tdoc.getId_tipo_doc_ident().trim()%>"><%=tdoc.getDe_tdoc_abreviada()%></option>
                                                                    <%}
                                                                        }%>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Numero de Documento: </label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-child fa-lg fa-fw"></i><label class="edad"></label></span>
                                                                <input  name="NRO_DOC"  required="" value="<%= d.getNu_doc().trim()%>"  class="form-control input-group-sm nu_doc" >
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Inscripci�n Vigiente Essalud: </label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-flag fa-lg fa-fw"></i></span>
                                                                <select  name="INSCRIPCION_VIG_ESSALUD" class="form-control input-group-sm"   required="" >
                                                                    <%if (d.getEs_inscripcion_vig_essalud() != null) {
                                                                            if (d.getEs_inscripcion_vig_essalud().equals("1")) {
                                                                    %>
                                                                    <option value="1" selected="">Si</option>
                                                                    <option value="0">No</option>
                                                                    <%}
                                                                    %>
                                                                    <%
                                                                        if (d.getEs_inscripcion_vig_essalud().equals("0")) {
                                                                    %>
                                                                    <option value="1">Si</option>
                                                                    <option value="0" selected="">No</option>
                                                                    <%}
                                                                    } else {%>
                                                                    <option value="" >[SELECCIONE]</option>
                                                                    <option value="1">Si</option>
                                                                    <option value="0">No</option>
                                                                    <%}
                                                                    %>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-4">
                                                        <div class="form-group">
                                                            <label>Estudiante de Nivel Superior: </label>
                                                            <div class="input-group">
                                                                <span class="input-group-addon"><i class="fa fa-flag fa-lg fa-fw"></i></span>
                                                                <select  name="ESTUD_NIV_SUPERIOR" class="form-control input-group-sm"   required="" >
                                                                    <%if (d.getEs_estudio_niv_superior() != null) {
                                                                            if (d.getEs_estudio_niv_superior().equals("1")) {
                                                                    %>
                                                                    <option  value="1" selected="">Si</option>
                                                                    <option  value="0">No</option>
                                                                    <%}
                                                                    %>
                                                                    <%
                                                                        if (d.getEs_estudio_niv_superior().equals("0")) {
                                                                    %>
                                                                    <option  value="1" selected="">Si</option>
                                                                    <option  value="0">No</option>
                                                                    <%}
                                                                    } else {
                                                                    %>
                                                                    <option value="" >[SELECCIONE]</option>
                                                                    <option  value="1" >Si</option>
                                                                    <option  value="0">No</option
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>

                                            <input type="hidden" name="idhijo" value="<%= d.getId_datos_hijos_trabajador()%>">
                                            <input type="hidden" name="idtr" class="idtr" value="<%= d.getId_trabajador()%>">
                                            <input type="hidden" name="opc" value="MODIFICAR HIJO">
                                            <% Boolean accesToEditDataHijos = false;
                                                accesToEditDataHijos = (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") | rol.trim().equals("ROL-0001") | rol.trim().equals("ROL-0013"));%>
                                            <input type="hidden" class="accesToEditDataHijos" value="<%=accesToEditDataHijos%>"/>

                                            <%}%>



                                        </div>
                                        <center>
                                            <button type="submit" class="btn btn-labeled btn-info" name="opc">
                                                <span class="btn-label">
                                                    <i class="glyphicon glyphicon-pencil"></i>
                                                </span>Modificar
                                            </button>
                                        </center>
                                        <!-- end widget content -->
                                    </form>

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->

                            </article>
                            <!-- WIDGET END -->
                            <div class="listar_hijos"></div>

                        </div>

                        <!-- end row -->

                        </section>
                        <!-- end widget grid -->


                    </div>
                    <!-- END MAIN CONTENT -->

            </div>
    <!-- PAGE RELATED PLUGIN(S)
    <script src="js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
    <script src="js/plugin/fuelux/wizard/wizard.min.js"></script>-->
    <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
    <script src="js/businessLogic/Js_Formulario/Js_Form.js" type="text/javascript"></script>
    <script src="js/businessLogic/Hijos/modDatosHijos.js" type="text/javascript"></script>
    </body>
    </html>
    <%} else {
            out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
        }
    %>
