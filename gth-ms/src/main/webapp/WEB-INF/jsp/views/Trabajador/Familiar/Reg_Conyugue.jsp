<%@page import="com.app.config.globalProperties"%>
<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<center>
<div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">

    <header>
        <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
        <h2><b>DATOS DEL CONYUGUE</b></h2>

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

            <form action=../familiar" id="checkout-form" class="smart-form" novalidate="novalidate">

                <header>
                    <b>DATOS DEL CONYUGUE</b>
                </header>
                <fieldset>
                    <div class="row">

                        <section class="col col-4">
                            <label class="select">
                                <select name="TRABAJA_UPEU_CONYUGUE" required="" class="select-conyugue">
                                    <option value="" selected=""  disabled="">�Trabaja Upeu Conyugue?</option>
                                    <option value="1">Si</option>
                                    <option value="0">No</option>

                                </select>
                                <i></i> </label>
                        </section>

                        <section class="col col-4">
                            <label class="input">
                                <input type="text" name="APELLIDO_NOMBRES_CONYUGUE" placeholder="Apellidos y Nombres" required="" class="nom_c" maxlength="70">
                            </label>
                        </section>

                        <section class="col col-4">
                            <label class="input"> <i class="icon-append fa fa-calendar"></i>
                                <input type="date" name="FECHA_NAC_CONYUGUE" class="date f_nac" required="">
                            </label>
                        </section>
                    </div>
                    <div class="row">
                        <section class="col col-4">
                            <label class="select">
                                <select name="TIPO_DOC_ID" required="" class="ti_documento">
                                    <option value="" selected="" disabled="">Tipo de Documento</option>
                                    <option value="1" selected="selected" >DNI</option>
                                    <option value="2">Carn� de Extranjeria</option>
                                    <option value="3">Pasaporte</option>
                                </select>
                                <i></i> </label>
                        </section>
                        <section class="col col-4">
                            <label class="input">
                                <input type="text" name="NRO_DOC" placeholder="Nro de Documento" maxlength="10" class="num_doc" required="">
                            </label>
                        </section>
                        <input type="hidden" class="cony"  name="CONYUGUE"/>

                        <section class="col col-4">
                            <label class="select">
                                <select name="INSCRIPCION_VIG_ESSALUD" required="">
                                    <option value="" selected="" disabled="">Inscripci�n Vigente en Essalud</option>
                                    <option value="1">Si</option>
                                    <option value="0">No</option>

                                </select>
                                <i></i> </label>
                        </section>
                    </div>

                </fieldset>
                <input type="hidden" name="opc"  class="submit" value="Registrar Conyugue">
                <footer>
                    <input type="hidden" name="idtr"  value="<%=request.getParameter("idtr")%>" class="text-box" >
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



<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">Encontrar Conyugue</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div id="contenido">
                        <div >

                            <form class="form-inline" id="frm_filtro" method="post" name="formulario"  >

                                <div class="row">
                                    <div class="form-group" >
                                        <label class="control-label" >Nombres</label><br>
                                        <input type="text"  class="form-control"  name="nom" maxlength="80" >
                                    </div>
                                    <div class="form-group" >
                                        <label class="control-label" >Apellido Paterno</label><br>
                                        <input type="text"  class="form-control"  name="ap_pa" maxlength="80">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group">
                                        <label class="control-label" >Apellido Materno</label><br>
                                        <input type="text"  class="form-control"  name="ap_ma" maxlength="80" >
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label" >DNI:</label><br>
                                        <input type="text"  class="form-control"  onKeyPress="return checkIt(event)"   name="dni" maxlength="8">
                                    </div>
                                </div>

                                <div class="row">

                                    <div class="form-group">
                                        <button type="button" class="btn btn-primary" id="btnfiltrar" >Buscar</button>
                                    </div>
                                    <div class="form-group">
                                        <a href="javascript:;"  id="btncancel" class="btn btn-primary" >Cancelar</a>
                                    </div>

                                </div>

                            </form>

                        </div>

                        <hr/>

                        <table  id="data"  >
                            <thead class="tab_cabe">
                                <tr>
                                    <td><span title="NOMBRE_AP">Nombres y Apellidos</span></td>
                                    <td><span  >DNI</span></td>
                                    <td></td>

                                </tr>
                            </thead>

                            <tbody class="tbodys">
                            </tbody>
                        </table>

                    </div>
                </div>



            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default btn-salir-busc"  data-dismiss="modal">Salir</button>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<button  data-toggle="modal" data-target="#myModal" id="btn-mostrar" hidden="">
    Launch demo modal
</button>


    <!-- PAGE RELATED PLUGIN(S) -->
       
    <script src="js/plugin/jquery-form/jquery-form.min.js"></script>
    <script src="js/Js_Formulario/Js_Form.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>

    <script type="text/javascript">
    // DO NOT REMOVE : GLOBAL FUNCTIONS!
    $(document).ready(function() {
        pageSetUp();
         $(".num_doc").numeric(false);
        $(".ti_documento").change(function() {
            $(".num_doc").val("");
            if ($(this).val() == 1) {
                $(".num_doc").numeric(false);
                $(".num_doc").attr("maxlength", "8");
                $(".num_doc").attr("minlength", "8");
            } else {
                $(".num_doc").removeNumeric();
                $(".num_doc").attr("maxlength", "10");
                $(".num_doc").removeAttr("minlength");
            }
            $(".num_doc").focus();
        });
        $('#checkout-form').validate({
            // Rules for form validation
            rules: {
                FECHA_NAC_CONYUGUE: {
                    val_fecha: true,
                    val_edad: true
                }
            },
            // Do not change code below
            errorPlacement: function(error, element) {
                error.insertAfter(element.parent());
            }
        });
        jQuery.validator.addMethod("val_fecha", function(value, element) {
            var d = value.split("-");
            return this.optional(element) || String(parseInt(d[0])).length == 4;
        }, "�Fecha ingresada invalida!");
        jQuery.validator.addMethod("val_edad", function(value, element) {
            return this.optional(element) || calcular_edad(value) > 0;
        }, "�La edad debe ser mayor a cero!");

        var b = $(".tbodys");
        $("#btnfiltrar").click(
                function() {
                    $.post(../ajax/Ajax_Conyugue/Ajax_Busc_Conyug.jsp", $("#frm_filtro").serialize(), function(objJson) {
                        b.empty();
                        var list = objJson.lista;
                        for (var i = 0; i < list.length; i++) {
                            b.append("<tr>");
                            b.append("<td>" + list[i].NOM + " " + list[i].AP_PA + " " + list[i].AP_MA + "</td>");
                            b.append("<td>" + list[i].NU_DOC + "</td>");
                            b.append("<input type ='hidden' class='trab_" + i + "' value='" + list[i].ID_TRAB + "' />");
                            b.append("<input type ='hidden' class='nac_" + i + "' value='" + list[i].NAC + "' />");
                            b.append("<input type ='hidden' class='dni_" + i + "' value='" + list[i].NU_DOC + "' />");
                            b.append("<input type ='hidden' class='tipo_" + i + "' value='" + list[i].TIPO + "' />");
                            b.append("<input type ='hidden' class='nom_ape_" + i + "' value='" + list[i].NOM + " " + list[i].AP_PA + " " + list[i].AP_MA + "' />");
                            b.append('<td><button type="button" class="btn btn-primary btn-add-conyugue" value="' + i + '" data-dismiss="modal">Agregar</button></td>');
                            b.append("</tr>");
                        }
                        $(".btn-add-conyugue").click(function() {
                            var v = $(this).val();
                            $(".nom_c").val($(".nom_ape_" + v).val());
                            $(".f_nac").val($(".nac_" + v).val());
                            $(".ti_documento").val($(".tipo_" + v).val());
                            $(".num_doc").val($(".dni_" + v).val());
                            $(".cony").val($(".trab_" + v).val());
                            //$(".select-conyugue").val("1");
                        });
                    }
                    );
                });
        $(".btn-salir-busc, .close").click(function() {
            $(".select-conyugue").val("0");
        });
        $(".select-conyugue").change(function() {
            if ($(this).val() == "1") {
                $("#btn-mostrar").click();
            }
            if ($(this).val() == "0") {
                $(".nom_c").val("");
                $(".f_nac").val("");
                $(".ti_documento").val("");
                $(".num_doc").val("");
                $(".cony").val("");
            }
        }
        );
        $("#btncancel").click(
                function() {
                    document.formulario.reset();
                    b.empty();
                    html = '<tr><td colspan="8" align="center">Haga la busqueda por algunos de los filtros...</td></tr>'
                    $(".tbodys").html(html);
                }
        );

    });
    </script>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>