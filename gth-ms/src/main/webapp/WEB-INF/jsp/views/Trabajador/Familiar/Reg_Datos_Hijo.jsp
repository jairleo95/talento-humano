<%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.domain.model.Tipo_Documento"%>
<%@page import="com.app.persistence.dao.Tipo_DocumentoDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceTipo_DocumentoDAO"%>
<%    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%InterfaceTipo_DocumentoDAO idoc = new Tipo_DocumentoDAO();
    String rol = (String) sesion.getAttribute("IDROL");
%>
<center>
<!--begin widget-->
<div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">

    <header>
        <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
        <h2><b>HIJOS</b></h2>

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

            <form action=../familiar" id="formRegDatosHijos" class="smart-form"  >

                <header>
                    <b>HIJOS</b>
                </header>

                <fieldset>
                    <div class="row">
                        <section class="col col-4">
                            <label class="input"> <i class="icon-prepend fa fa-user"></i>
                                <input type="text" name="APELLIDO_P" placeholder="Apellido Paterno" required="">
                            </label>
                        </section>
                        <section class="col col-4">
                            <label class="input"> <i class="icon-prepend fa fa-user"></i>
                                <input type="text" name="APELLIDO_M" placeholder="Apellido Materno" required="">
                            </label>
                        </section>
                        <section class="col col-4">
                            <label class="input"> <i class="icon-prepend fa fa-user"></i>
                                <input type="text" name="NOMBRE" placeholder="Nombres" required="">
                            </label>
                        </section>
                        <section class="col col-4">
                            <label class="input"> <i class="icon-append fa fa-calendar"></i>
                                <input type="text" name="FECHA_NAC"  class="datePickerInput" required="" data-mask="99/99/9999" data-mask-placeholder= "_">
                            </label>
                        </section>
                        <section class="col col-4">
                            <label class="select">
                                <select name="SEXO" required="" >
                                    <option value="" selected="" >[Sexo]</option>
                                    <option value="M">Masculino</option>
                                    <option value="F">Femenino</option>

                                </select>
                                <i></i> </label>
                        </section>
                        <section class="col col-4">
                            <label class="select">
                                <select name="TIPO_DOC_ID" required="" class="ti_doc">
                                    <option value="" selected="">[Tipo de Documento]</option>
                                    <%for (int i = 0; i < idoc.Listar_tipo_doc().size(); i++) {
                                            Tipo_Documento t = new Tipo_Documento();
                                            t = (Tipo_Documento) idoc.Listar_tipo_doc().get(i);
                                    %>
                                    <option value="<%=t.getId_tipo_doc_ident().trim()%>"><%=t.getDe_tdoc_abreviada()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                                <i></i> </label>
                        </section>
                        <section class="col col-4">
                            <label class="input">
                                <input type="text" name="NRO_DOC" placeholder="Nro de Documento" maxlength="10" required="" class="nu_doc">
                            </label>
                        </section>
                        <section class="col col-4">
                            <label class="select">
                                <select name="INSCRIPCION_VIG_ESSALUD" required="">
                                    <option value="" selected="" >[Inscripci�n Vigente en Essalud]</option>
                                    <option value="1">Si</option>
                                    <option value="0">No</option>
                                </select>
                                <i></i> </label>
                        </section>
                        <section class="col col-4">
                            <label class="select">
                                <select name="ESTUD_NIV_SUPERIOR" required="">
                                    <option value="" selected="" >[Estudio de Nivel Superior]</option>
                                    <option value="1">Si</option>
                                    <option value="0">No</option>
                                </select>
                                <i></i> </label>
                        </section>
                    </div>
                </fieldset>
                <input type="hidden" name="opc"  value="REGISTRAR HIJO">
                <input type="hidden" name="idtr" class="idtr" value="<%=request.getParameter("idtr")%>"  >
                <% Boolean accesToEditDataHijos = false;
                    accesToEditDataHijos = (rol.trim().equals("ROL-0002") | rol.trim().equals("ROL-0005") | rol.trim().equals("ROL-0001") | rol.trim().equals("ROL-0013"));%>
                <input type="hidden" class="accesToEditDataHijos" value="<%=accesToEditDataHijos%>"/>
                <footer>
                    <button type="submit" class="btn btn-primary" >
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
<div class="listar_hijos"></div>

<!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT -->
<!-- Voice command : plugin -->
<script src="js/speech/voicecommand.min.js"></script>
<!-- PAGE RELATED PLUGIN(S) -->
<script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
<script src="js/plugin/jquery-form/jquery-form.min.js"></script>
<script src="js/businessLogic/Js_Formulario/Js_Form.js"></script>
<script src="js/businessLogic/Hijos/regDatosHijos.js"></script>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
