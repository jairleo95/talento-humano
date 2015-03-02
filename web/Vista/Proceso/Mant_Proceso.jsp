<%@page import="pe.edu.upeu.application.model.Usuario"%>
<%
    HttpSession sesion_1 = request.getSession();
    String id_user_1 = (String) sesion_1.getAttribute("IDUSER");
    if (id_user_1 != null) {
%>
<%-- 
    Document   : Prueba_Nestable_List
    Created on : 07-ene-2015, 15:23:08
    Author     : ALFA 3
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
    <head>
        <meta charset="utf-8">
        <!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

        <title> Mantenimiento de Proceso </title>
        <meta name="description" content="">
        <meta name="author" content="">

        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

        <!-- Basic Styles -->
        <link rel="stylesheet" type="text/css" media="screen" href="../../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="../../css/font-awesome.min.css">

        <!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
        <link rel="stylesheet" type="text/css" media="screen" href="../../css/smartadmin-production.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="../../css/smartadmin-skins.min.css">

        <!-- SmartAdmin RTL Support is under construction
                 This RTL CSS will be released in version 1.5
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-rtl.min.css"> -->

        <!-- We recommend you use "your_style.css" to override SmartAdmin
             specific styles this will also ensure you retrain your customization with each SmartAdmin update.
        <link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css"> -->

        <!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
        <link rel="stylesheet" type="text/css" media="screen" href="../../css/demo.min.css">

        <!-- FAVICONS -->
        <link rel="shortcut icon" href="../../img/favicon/favicon.ico" type="image/x-icon">
        <link rel="icon" href="../../img/favicon/favicon.ico" type="image/x-icon">

        <!-- GOOGLE FONT -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

        <!-- Specifying a Webpage Icon for Web Clip 
                 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
        <link rel="apple-touch-icon" href="../../img/splash/sptouch-icon-iphone.png">
        <link rel="apple-touch-icon" sizes="76x76" href="../../img/splash/touch-icon-ipad.png">
        <link rel="apple-touch-icon" sizes="120x120" href="../../img/splash/touch-icon-iphone-retina.png">
        <link rel="apple-touch-icon" sizes="152x152" href="../../img/splash/touch-icon-ipad-retina.png">

        <!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">

        <!-- Startup image for web apps -->
        <link rel="apple-touch-startup-image" href="../../img/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
        <link rel="apple-touch-startup-image" href="../../img/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
        <link rel="apple-touch-startup-image" href="../../img/splash/iphone.png" media="screen and (max-device-width: 320px)">

    </head>
    <body class="">
        <!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->

        <!-- HEADER -->
        <header>

        </header>
        <!-- END HEADER -->

        <!-- Left panel : Navigation area -->
        <!-- Note: This width of the aside area can be adjusted through LESS variables -->

        <!-- END NAVIGATION -->

        <!-- MAIN PANEL -->
        <div id="main" role="main" style="margin: 0px;">

            <!-- MAIN CONTENT -->
            <div id="content" >



                <!-- widget grid -->
                <section id="widget-grid" class="">

                    <!-- row -->
                    <div class="row">
                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <div class="jarviswidget jarviswidget-sortable jarviswidget-color-blueDark" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false" role="widget" style="">
                                <header role="heading">
                                    <div class="jarviswidget-ctrls" role="menu">
                                        <a href="javascript:void(0);" class="button-icon jarviswidget-toggle-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Collapse">
                                            <i class="fa fa-minus "></i>
                                        </a>
                                        <a href="javascript:void(0);" class="button-icon jarviswidget-fullscreen-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Fullscreen">
                                            <i class="fa fa-expand "></i></a>
                                        <a href="javascript:void(0);" class="button-icon jarviswidget-delete-btn" rel="tooltip" title="" data-placement="bottom" data-original-title="Delete">
                                            <i class="fa fa-times"></i></a>
                                    </div>

                                    <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                                    <h2>Mantenimiento de Pasos</h2>				
                                    <span class="jarviswidget-loader"><i class="fa fa-refresh fa-spin"></i></span></header>
                                <div>
                                    <div class="jarviswidget-editbox">
                                    </div>
                                    <div class="widget-body">
                                        <form class="smart-form">
                                            <fieldset>
                                                <div class="row" style="float: left;">
                                                    <div class="row">
                                                        <section class="col col-12">
                                                            <label class="input">
                                                                <input name="num" required=""  maxlength="3" class="num_paso" maxlength="6" placeholder="Numero de Paso">
                                                            </label>
                                                        </section>
                                                        <section class="col col-12">
                                                            <label class="input">
                                                                <input type="text" name="cod" class="co_paso" required=""maxlength="6" placeholder="Codigo" />
                                                            </label>
                                                        </section>
                                                    </div>
                                                    <div class="row">
                                                        <section class="col col-12">
                                                            <label class="select">
                                                                <select name="proceso" required=""  id="select-proceso">
                                                                    <option value="">[Seleccione Proceso]</option>
                                                                </select>
                                                                <i></i></label>
                                                        </section>
                                                    </div>
                                                </div>
                                                <div class="row" style="float: right;">
                                                    <section class="col col-12">
                                                        <label class="input">
                                                            <textarea type="text" name="desc" required="" maxlength="300" rows="5" cols="50" class="desc_paso" placeholder="Descripcion" ></textarea>
                                                        </label>
                                                    </section>
                                                </div>
                                            </fieldset>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </article>
                        <!-- NEW WIDGET START -->
                        <article class="col-sm-12">

                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget well" id="wid-id-0">
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

                                <!-- widget div-->
                                <div>

                                    <div class="row">

                                        <h1>Mantenimiento de Pasos</h1>

                                        <form  method="post" id="form-paso" class="form_paso" >
                                            <table class="table table-bordered">
                                                <tr ><td >Descripción :<td><textarea type="text" name="desc" required="" maxlength="300" rows="5" cols="50" class="desc_paso" ></textarea></td></tr>
                                                <tr><td>Numero Paso :<td><input name="num" required=""  maxlength="3" class="num_paso" maxlength="6"> </td></tr>
                                                <tr><td>Código:<td><input type="text" name="cod" class="co_paso" required=""maxlength="6"  /></td></tr>
                                                <tr><td>Proceso:<td>
                                                        <select name="proceso" required=""  id="select-proceso">
                                                            <option value="">[SELECCIONE]</option>
                                                        </select></td></tr>
                                                <input type="hidden" name="opc" value="Registrar"  class="opc"/>
                                                <input type="hidden" name="id" value=""  class="id_p"/>
                                                <tr><td><input type="submit" id="btn-registrar" name="Enviar" value="Registrar Paso" /></td></tr>
                                                <tr><td><button type="button" class="btn_cancel_edit" style="display:none">Cancelar</button></td></tr>
                                            </table>
                                        </form>
                                        <h2>Lista de pasos Deshabilitados :<strong><label class="lb-list_pasos"></label></strong> </h2>
                                        <table class="table table-bordered" border='1'>
                                            <thead>
                                                <tr>
                                                    <td>Nro</td>
                                                    <td>Descripción</td>
                                                    <td>Número</td>
                                                    <td>Codigo</td>
                                                    <td>Proceso</td>
                                                    <td>Editar</td>

                                                </tr>
                                            </thead>

                                            <tbody  class="tbodys" style=" display: none;">
                                            </tbody>

                                        </table>

                                    </div>
                                    <!-- end widget edit box -->

                                    <!-- widget content -->
                                    <div class="widget-body">





                                        <div class="row">


                                            <div class="col-sm-6 col-lg-12">

                                                <h2>Lista de pasos Habilitados :<strong><label class="lb-list_pasos"></label></strong> </h2>

                                                <button type="button" class="btn btn-default Generar" data-action="collapse-all">
                                                    Generar Pasos
                                                </button>
                                                <br><style>
                                                    .div{
                                                        border-style: solid;
                                                        border-color: #ff0000 #0000ff;
                                                    }
                                                    label{
                                                        margin-left: 10px;
                                                    }
                                                </style>

                                                <div class="dd" id="nestable" >
                                                    <ol class="dd-list" style="width: 1024px;" >



                                                    </ol>
                                                </div>

                                            </div>

                                        </div>
                                        <div class="row" >
                                            <h2>Lista de puestos :<strong><label class="lb-list_puesto"></label></strong> </h2>
                                            <form action="" method="post" class="form_puesto" style="display:none;">
                                                <label>Dirección</label><select class="sl_dir" required="" ></select>
                                                <label>Departamento:</label><select class="sl_dep" required=""></select>
                                                <label>Area:</label><select class="sl_area" required=""></select>
                                                <label>Sección:</label><select class="sl_sec" required=""></select>
                                                <label>Puesto:</label><select name="id_pu" class="sl_puesto" required=""></select>
                                                <input type="hidden" value="" name="idpasos" class="id_pasos"  />
                                                <input type="hidden" value="" name="nun" class="num_p"  />
                                                <br>
                                                Código Puesto :<select class="co_puesto" name="co_pasos">
                                                    <option value=""></option>
                                                    <option value="SECR">Secretaria de Area</option>
                                                    <option value="JEFE">Jefe de Area</option>
                                                </select>
                                                <br>
                                                <button type="button" class="btn-agregar-p">
                                                    Agregar Puesto
                                                </button>
                                            </form>
                                            <table class="table table-bordered" border="1">
                                                <thead>
                                                    <tr>
                                                        <th>Puesto</th>
                                                        <th>Area</th>
                                                        <th>Departamento</th>
                                                        <th>Dirección</th>
                                                        <th>Estado</th>
                                                        <th>Acciones</th>
                                                    </tr></thead>
                                                <tbody class="tbody-puesto">

                                                </tbody>
                                            </table>


                                        </div>

                                    </div>
                                    <!-- end widget content -->

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->

                        </article>
                        <!-- WIDGET END -->

                    </div>

                    <!-- end row -->


                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->



        <!-- SHORTCUT AREA : With large tiles (activated via clicking user name tag)
        Note: These tiles are completely responsive,
        you can add as many as you like
        -->

        <!-- END SHORTCUT AREA -->

        <!--================================================== -->

        <!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
        <script data-pace-options='{ "restartOnRequestAfter": true }' src="js/plugin/pace/pace.min.js"></script>

        <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
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

        <!-- IMPORTANT: APP CONFIG -->
        <script src="../../js/app.config.js"></script>

        <!-- JS TOUCH : include this plugin for mobile drag / drop touch events-->
        <script src="../../js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script> 

        <!-- BOOTSTRAP JS -->
        <script src="../../js/bootstrap/bootstrap.min.js"></script>

        <!-- CUSTOM NOTIFICATION -->
        <script src="../../js/notification/SmartNotification.min.js"></script>

        <!-- JARVIS WIDGETS -->
        <script src="../../js/smartwidgets/jarvis.widget.min.js"></script>

        <!-- EASY PIE CHARTS -->
        <script src="../../js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js"></script>

        <!-- SPARKLINES -->
        <script src="../../js/plugin/sparkline/jquery.sparkline.min.js"></script>

        <!-- JQUERY VALIDATE -->
        <script src="../../js/plugin/jquery-validate/jquery.validate.min.js"></script>

        <!-- JQUERY MASKED INPUT -->
        <script src="../../js/plugin/masked-input/jquery.maskedinput.min.js"></script>

        <!-- JQUERY SELECT2 INPUT -->
        <script src="../../js/plugin/select2/select2.min.js"></script>

        <!-- JQUERY UI + Bootstrap Slider -->
        <script src="../../js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

        <!-- browser msie issue fix -->
        <script src="../../js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

        <!-- FastClick: For mobile devices -->
        <script src="../../js/plugin/fastclick/fastclick.min.js"></script>

        <!--[if IE 8]>

        <h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

        <![endif]-->

        <!-- Demo purpose only -->
        <script src="../../js/demo.min.js"></script>

        <!-- MAIN APP JS FILE -->
        <script src="../../js/app.min.js"></script>

        <!-- ENHANCEMENT PLUGINS : NOT A REQUIREMENT -->
        <!-- Voice command : plugin -->
        <script src="../../js/speech/voicecommand.min.js"></script>

        <!-- PAGE RELATED PLUGIN(S) -->
        <script src="../../js/plugin/jquery-nestable/jquery.nestable.min.js"></script>


        <script type="text/javascript">

            // DO NOT REMOVE : GLOBAL FUNCTIONS!
            function  list_put_id(d, valor) {


                $.post("../../Direccion_Puesto", "opc=" + "Listar_pu_id" + "&" + "id=" + valor, function(objJson) {
                    d.empty();
                    d.append("<option value='' > [SELECCIONE] </option>");
                    var list = objJson.lista;
                    if (list.length !== 0) {
                        for (var i = 0; i < list.length; i++) {
                            d.append('<option value="' + list[i].id + '">' + list[i].nombre + '</option>');
                        }
                    } else {
                        d.append("<option value='0' > [] </option>");
                    }
                });
            }

            function  lis_dir_id(d, valor) {


                $.post("../../Direccion_Puesto", "opc=Listar_dir_dep&" + "id=" + valor, function(objJson) {
                    d.empty();
                    d.append("<option value='' > [SELECCIONE] </option>");
                    if (objJson.rpta == -1) {
                        alert(objJson.mensaje);
                        return;
                    }
                    var list = objJson.lista;
                    if (list.length !== 0) {
                        for (var i = 0; i < list.length; i++) {
                            d.append('<option value="' + list[i].id + '">' + list[i].nombre + '</option>');
                        }
                    } else {
                        d.append("<option value='' > [] </option>");
                    }
                });
            }
            function list_area_id(c, valor) {


                $.post("../../Direccion_Puesto", "opc=Listar_area&" + "id_dep=" + valor, function(objJson) {
                    c.empty();
                    c.append("<option value='' > [SELECCIONE] </option>");
                    if (objJson.rpta == -1) {
                        alert(objJson.mensaje);
                        return;
                    }
                    var list = objJson.lista;
                    if (list.length !== 0) {
                        for (var i = 0; i < list.length; i++) {
                            c.append('<option value="' + list[i].id + '">' + list[i].nom + '</option>');
                        }
                    } else {
                        c.append("<option value='' > [] </option>");
                    }
                });
            }
            function list_sec_id(d, valor) {
                $.post("../../Direccion_Puesto", "opc=Listar_sec&" + "id_are=" + valor, function(objJson) {
                    d.empty();
                    d.append("<option value='' > [SELECCIONE] </option>");
                    if (objJson.rpta == -1) {
                        alert(objJson.mensaje);
                        return;
                    }
                    var list = objJson.lista;
                    if (list.length !== 0) {
                        for (var i = 0; i < list.length; i++) {
                            d.append('<option value="' + list[i].id + '">' + list[i].nom + '</option>');
                        }
                    } else {
                        d.append("<option value='' > [no hay] </option>");
                    }
                });
            }
            function list_dir(c) {
                $.post("../../Direccion_Puesto", "opc=Listar_direccion", function(objJson) {
                    c.empty();
                    c.append("<option value='' > [SELECCIONE] </option>");
                    if (objJson.rpta == -1) {
                        alert(objJson.mensaje);
                        return;
                    }
                    var list = objJson.lista;
                    if (list.length !== 0) {
                        for (var i = 0; i < list.length; i++) {
                            c.append('<option value="' + list[i].id + '">' + list[i].nombre + '</option>');
                        }
                    } else {
                        c.append("<option value='0' > [] </option>");
                    }
                });
            }

            function list_puesto(num) {
                $(".lb-list_puesto").text($(".det_p_" + num).val());
                var tbody_p = $(".tbody-puesto");
                var texto = "";
                $(".id_pasos").val($(".id_paso" + num).val());
                $.post("../../paso", "opc=Paso_Puesto&id=" + $(".id_paso" + num).val(), function(objJson) {
                    if (objJson.rpta == -1) {
                        alert(objJson.mensaje);
                        return;
                    }
                    tbody_p.empty();
                    var lista = objJson.lista;
                    for (var h = 0; h < lista.length; h++) {
                        texto += "<tr>";
                        texto += "<td>" + lista[h].puesto + "</td>";
                        texto += "<td>" + lista[h].area + "</td>";
                        texto += "<td>" + lista[h].dep + "</td>";
                        texto += "<td>" + lista[h].direccion + "</td>";
                        if (lista[h].estado == '0') {
                            texto += "<td>Deshabilitado</td>";
                        } else if (lista[h].estado == '1') {
                            texto += "<td>Habilitado</td>";
                        }


                        texto += "<td><button type='button' value='" + h + "' class='btn-eliminar_puesto'>Eliminar</button>";
                        texto += "<input type='hidden' class='iddp" + h + "' value='" + lista[h].idpp + "' />";
                        if (lista[h].estado == '1') {
                            texto += "<button class='btn_deshabilitar_p' value='" + h + "'>Deshabilitar</button></td>";
                        } else {
                            texto += "<button class='btn_habilitar_p' value='" + h + "'>Habilitar</button></td>";
                        }
                        texto += "</tr>";
                    }
                    tbody_p.append(texto);
                    texto = "";
                    $(".form_puesto").show();
                    $(".btn-eliminar_puesto").click(
                            function() {
                                if (confirm("¿Esta Seguro de Eliminar?")) {
                                    alert("opc=Eliminar_PP&id=" + $(".iddp" + $(this).val()).val());
                                    $.post("../../paso", "opc=Eliminar_PP&id=" + $(".iddp" + $(this).val()).val(), function(objJson) {

                                    });
                                } else {

                                }
                            }
                    );
                    $(".btn_deshabilitar_p").click(function() {
                        $.post("../../paso", "opc=actualizar_estado&id=" + $(".iddp" + $(this).val()).val() + "&estado=0", function(objJson) {

                            if (objJson.rpta == -1) {
                                alert(objJson.mensaje);
                                return;
                            } else {

                                alert("El puesto para este paso se ha deshabilitado correctamente...");
                                list_puesto($(".num_p").val());
                            }
                        }
                        );

                    });
                    $(".btn_habilitar_p").click(function() {
                        $.post("../../paso", "opc=actualizar_estado&id=" + $(".iddp" + $(this).val()).val() + "&estado=1", function(objJson) {
                            if (objJson.rpta == -1) {
                                alert(objJson.mensaje);
                                return;
                            } else {

                                alert("El puesto para este paso se ha habilitado correctamente...");
                                list_puesto($(".num_p").val());
                            }
                        });
                    });

                });
            }
            $(document).ready(function() {
                $(".btn_cancel_edit").click(function() {
                    $("#btn-registrar").val("Registrar Paso");
                    $(".opc").val("Registrar");
                    $(".form_paso")[0].reset();
                    $(this).hide();
                });
                $(".form_puesto").hide();
                list_dir($(".sl_dir"));
                $(".sl_dir").change(function() {
                    lis_dir_id($(".sl_dep"), $(this).val());
                });
                $(".sl_dep").change(function() {
                    list_area_id($(".sl_area"), $(this).val());
                });
                $(".sl_area").change(function() {
                    list_sec_id($(".sl_sec"), $(this).val());
                });
                $(".sl_sec").change(function() {
                    list_put_id($(".sl_puesto"), $(this).val());
                });
                var num = 1;
                listar_Proceso();
                Listar_Paso($("#select-proceso").val());
                $("#btn-registrar").click(
                        function() {
                            var pr = $("#select-proceso").val();
                            $.post("../../paso", $("#form-paso").serialize(), function(objJson) {
                                if (objJson.rpta == -1) {
                                    alert(objJson.mensaje);
                                    return;
                                }
                                Listar_Paso(pr);
                            });
                            $("#btn-registrar").val("Registrar Paso");
                            $(".opc").val("Registrar");
                            $("#form-paso")[0].reset();
                            return false;
                        }
                );
                function listar_Proceso() {
                    var s = $("#select-proceso");
                    $.post("../../Proceso", "opc=Listar", function(objJson) {
                        s.empty();
                        var lista = objJson.lista;
                        s.append("<option value='' selected >[SELECCIONE]</option>");
                        for (var j = 0; j < lista.length; j++) {
                            s.append("<option value='" + lista[j].id + "' > " + lista[j].nombre + "</option>");
                        }
                    });
                }
                $("#select-proceso").change(
                        function() {
                            $(".lb-list_pasos").text($(this).find(":selected").text());
                            Listar_Paso($(this).val());
                            $(".form_puesto").hide();
                            $(".tbody-puesto").empty();
                            // alert($(this).val());
                        });
                $(".btn-agregar-p").click(function() {
                    if ($(".form_puesto").valid()) {
                        $.ajax({
                            url: "../../Direccion_Puesto",
                            data: $(".form_puesto").serialize() + "&opc=Reg_puesto_paso"
                        }).done(function() {
                            list_puesto($(".num_p").val());
                            alert("¡Registrado Exitosamente!");
                        }).fail(function(objJson) {
                            alert(objJson.mensaje);
                        });
                    } else {
                        alert("Completar campos requeridos...");
                    }

                    ///alert($(".sl_puesto").val());
                });
                var b = $(".tbodys");
                var c = $(".dd-list");
                function Listar_Paso(proceso) {
                    var txt_append = "";
                    $.post("../../paso", "opc=Listar_habilitados&proceso=" + proceso, function(objJson) {
                        b.empty();
                        c.empty();
                        var lista = objJson.lista;
                        if (objJson.rpta == -1) {
                            alert(objJson.mensaje);
                            return;
                        }
                        for (var i = 0; i < lista.length; i++) {
                            b.append("<tr class='editar-tr" + i + "' >");
                            b.append("<td >" + (i + 1) + "</td>");
                            b.append("<td class='td_det" + i + "'>" + lista[i].det + "</td>");
                            b.append("<td class='td_num" + i + "'><label>" + lista[i].num + "</label></td>");
                            b.append("<td  class='td_co" + i + "'><label>" + lista[i].co + "</label></td>");
                            b.append("<td class='td_id_pro" + i + "' >" + lista[i].proceso_id + "</td>");
                            //b.append("<input type='text' name='id' value='" + lista[i].id + "'  class='id_paso" + i + "'/>");
                            b.append("</tr>");
                        }
                        for (var i = 0; i < lista.length; i++) {
                            txt_append += '<li class="dd-item dd3-item"  ><div class="dd-handle dd3-handle">Drag</div><div class="dd3-content"><label class="item_req item_' + (i + 1) + '">' + lista[i].num + ' </label> ' +
                                    '<div class="pull-right"><button class="btn btn-success btn-cargar-puesto" value="' + i + '" > Ver Puestos</button></div>' +
                                    '<div class="pull-right"><button class="btn btn-primary btn-editar" value="' + i + '" > Editar</button></div>' +
                                    '<div class="pull-right"><button class="btn btn-primary btn-eliminar" value="' + i + '" > Eliminar</button></div>';
                            if (typeof (lista[i].det) === "undefined") {
                                txt_append += '<div class="pull-right"><label ></label></div>';
                            } else {

                                txt_append += '<div class="pull-right"><label >' + lista[i].det + '</label></div>';
                            }

                            // '<div class="pull-right"><label >' + lista[i].co + '</label></div>' +
                            txt_append += '<input type="hidden"  value="' + lista[i].det + '"  class="det_p_' + i + '"/>' +
                                    '<input type="hidden" name="id" value="' + lista[i].id + '"  class="id_paso' + i + '"/>' + '</div> </li>';
                            txt_append += '<input  type="hidden" value="' + lista[i].det + '" class="inp_det_' + i + '" />';
                            txt_append += '<input type="hidden" value="' + lista[i].num + '" class="inp_num_' + i + '" />';
                            txt_append += '<input type="hidden" value="' + lista[i].co + '" class="inp_co_' + i + '" />';
                            txt_append += '<input type="hidden" value="' + lista[i].proceso_id + '" class="inp_pro_id_' + i + '" />';
                        }
                        c.append(txt_append);
                        txt_append = "";
                        $(".btn-cargar-puesto").click(function() {
                            var num = $(this).val();
                            $(".id_pasos_puesto").val($(".id_paso" + num).val());
                            list_puesto(num);
                            $(".num_p").val($(this).val());
                        });
                        $(".btn-editar").click(function() {
                            $(".btn_cancel_edit").show();
                            $(".desc_paso").val($(".td_det" + $(this).val()).text());
                            $(".num_paso").val($(".td_num" + $(this).val()).text());
                            $(".co_paso").val($(".td_co" + $(this).val()).text());
                            $("#select-proceso").val($(".td_id_pro" + $(this).val()).text());
                            $(".id_p").val($(".id_paso" + $(this).val()).val());
                            $("#btn-registrar").val("Modificar");
                            $(".opc").val("Modificar");
                        });
                        $(".btn-eliminar").click(
                                function() {
                                    var pr_e = $("#select-proceso").val();
                                    if (confirm("¿Esta Seguro de Eliminar?")) {
                                        $.post("../../paso", "opc=Eliminar&paso=" + $(".id_paso" + $(this).val()).val(), function() {
                                            Listar_Paso(pr_e);
                                        });
                                        //alert($(".id_paso" + $(this).val()).val());
                                    } else {

                                    }
                                }
                        );

                    });
                }
                $(".Generar").click(function() {
                    //var num = $(".tbodys tr").size();
                    var num = 1;
                    var url = "";


                    $.each($(".item_req"), function() {
                        $(this).text("P" + num);
                        num++;
                    });
                    for (var s = 1; s <= $(".tbodys tr").size(); s++) {
                        url += "id" + s + "=" + $(".id_paso" + (s - 1)).val() + "&numero" + s + "=" + $(".item_" + s).text() + "&";
                    }
                    url = url.substring(0, url.length - 1);
                    $.post("../../paso", "opc=Update_nu_paso&num=" + num + "&" + url, function(objJson) {
                        if (objJson.rpta == -1) {
                            alert(objJson.mensaje);
                            return;
                        }
                    });
                    alert(url);
                    url = "";
                    num = 1;

                    /* for (var f = 0; f < num; f++) {
                     //alert($(".id_paso" + f).val() + " - " + $(".item_"+f).text());
                     
                     $.ajax({
                     url:"../../paso",
                     data: "opc=Update_nu_paso&nu_paso=" + "P" + (f + 1) + "&paso=" + $(".id_paso" + f).val()
                     
                     }).done(function(){
                     alert( "opc=Update_nu_paso&nu_paso=" + "P" + (f + 1) + "&paso=" + $(".id_paso" + f).val());   
                     });
                     /* $.post("../../paso", "opc=Update_nu_paso&nu_paso=" + "P" + (f + 1) + "&paso=" + $(".id_paso" + f).val(), function (objJson) {
                     
                     });*/
                    // }*/

                    Listar_Paso($("#select-proceso").val());
                });
                pageSetUp();
                // PAGE RELATED SCRIPTS

                var updateOutput = function(e) {
                    var list = e.length ? e : $(e.target), output = list.data('output');
                    if (window.JSON) {
                        output.val(window.JSON.stringify(list.nestable('serialize')));
                        //, null, 2));
                    } else {
                        output.val('JSON browser support required for this demo.');
                    }
                };
                // activate Nestable for list 1
                $('#nestable').nestable({
                    group: 1
                }).on('change', updateOutput);
                // activate Nestable for list 2
                $('#nestable2').nestable({
                    group: 1
                }).on('change', updateOutput);
                // output initial serialised data
                updateOutput($('#nestable').data('output', $('#nestable-output')));
                updateOutput($('#nestable2').data('output', $('#nestable2-output')));
                $('#nestable-menu').on('click', function(e) {
                    var target = $(e.target), action = target.data('action');
                    if (action === 'expand-all') {
                        $('.dd').nestable('expandAll');
                    }
                    if (action === 'collapse-all') {
                        $('.dd').nestable('collapseAll');
                    }
                });
                $('#nestable3').nestable();
            })

        </script>

        <!-- Your GOOGLE ANALYTICS CODE Below -->
        <script type="text/javascript">
            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-XXXXXXXX-X']);
            _gaq.push(['_trackPageview']);
            (function() {
                var ga = document.createElement('script');
                ga.type = 'text/javascript';
                ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0];
                s.parentNode.insertBefore(ga, s);
            })();

        </script>

    </body>

</html>
<%} else {
        response.sendRedirect("/TALENTO_HUMANO/");
    }
%>