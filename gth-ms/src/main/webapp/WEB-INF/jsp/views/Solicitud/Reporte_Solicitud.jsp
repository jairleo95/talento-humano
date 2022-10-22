<%@page import="com.app.domain.model.V_Solicitud_Requerimiento"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>

<body class="body"  >
<!-- MAIN PANEL -->
<div id="main" role="main" style="margin-left: 0px;">
    <!-- MAIN CONTENT -->
    <div id="content">
        <!-- widget grid -->
        <section id="widget-grid" class="">
            <div class="row">
                <!-- NEW WIDGET START -->
                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div id="alerta_dgp">
                    </div>
                    <!-- Widget ID (each widget will need unique ID)-->
                    <div class="jarviswidget jarviswidget-color-greenDark" id="wid-id-0" data-widget-editbutton="false">
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
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2>Solicitudes Pendientes</h2>
                        </header>
                        <!-- widget div-->
                        <div>
                            <!-- widget edit box -->
                            <div class="jarviswidget-editbox">
                                <!-- This area used as dropdown edit box -->
                            </div>
                            <!-- end widget edit box -->

                            <!-- widget content -->
                            <div class="widget-body no-padding">

                                <table  class="table table-striped table-bordered table-hover table_list_sol" width="100%">
                                    <thead><tr><th><strong>Nro</strong></th><th>Acciones</th><th data-class="expand"><strong>Apellidos y Nombres</strong></th><th data-hide="phone,tablet" ><strong>Departamento</strong></th><th data-class="expand" ><strong>Area</strong></th><th data-hide="phone,tablet"><strong>Puesto</strong></th><th data-hide="phone,tablet"><strong>Fecha Inicio</strong></th><th data-hide="phone,tablet"><strong>Fecha Cese</strong></th><th  data-hide="phone,tablet">Fecha Solicitud</th><th  data-hide="phone,tablet">Estado</th></tr></thead>
                                    <tbody class="tbody_list_solicitud">

                                    </tbody>
                                </table>
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
            <div class="row">

                <!-- NEW WIDGET START -->
                <article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div id="alerta_dgp">
                    </div>
                    <!-- Widget ID (each widget will need unique ID)-->
                    <div class="jarviswidget jarviswidget-color-red" id="wid-id-1" data-widget-editbutton="false">
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
                            <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                            <h2>Solicitudes Autorizados</h2>
                        </header>
                        <!-- widget div-->
                        <div>
                            <!-- widget edit box -->
                            <div class="jarviswidget-editbox">
                                <!-- This area used as dropdown edit box -->
                            </div>
                            <!-- end widget edit box -->

                            <!-- widget content -->
                            <div class="widget-body no-padding">

                                <table  class="table table-striped table-bordered table-hover table_sol_aut" width="100%">
                                    <thead><tr><th><strong>Nro</strong></th><th>Acciones</th><th data-class="expand"><strong>Apellidos y Nombres</strong></th><th data-hide="phone,tablet" ><strong>Departamento</strong></th><th data-class="expand" ><strong>Area</strong></th><th data-hide="phone,tablet"><strong>Puesto</strong></th><th data-hide="phone,tablet"><strong>Fecha Inicio</strong></th><th data-hide="phone,tablet"><strong>Fecha Cese</strong></th><th  data-hide="phone,tablet">Fecha Solicitud</th><th  data-hide="phone,tablet">Estado</th></tr></thead>
                                    <tbody class="tbody_Sol_Aut">
                                    </tbody>
                                </table>
                            </div>
                            <!-- end widget content -->
                        </div>
                        <!-- end widget div -->
                    </div>
                    <!-- end widget -->
                </article>
                <!-- WIDGET END -->
            </div>
        </section>
        <!-- end widget grid -->


        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title" id="myModalLabel"><span class="btn-label"><i class="fa fa-envelope"></i></span> <strong>Solicitud de Requerimientos Fuera de Plazo</strong></h4>
                    </div>
                    <div class="modal-body">
                        <div class="div_load"></div>
                        <table class="table table-hover table-striped  table-responsive tabla_detalle_sol" ></table>

                        <form class="smart-form comentario">Comentario:<label class="textarea"><textarea rows="3"  required="" name="comentario" placeholder="Informaci�n adicional"></textarea></label></form>
                        <input  type="hidden" value="" class="data_procesar" />
                    </div>
                    <div class="modal-footer foot_sol">

                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </div>
</div>
<!-- PAGE RELATED PLUGIN(S) -->
<script src="js/plugin/jquery-form/jquery-form.min.js"></script>
<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
<script type="text/javascript">
    function listar_det_sol(valor, tipo) {
        console.log("enter to function listar_det_sol")
        console.log("Tipo:"+tipo)
        var tb = $(".tabla_detalle_sol");
        tb.empty();
        var texto_html = '';
        $(".foot_sol").empty();
        if (tipo === "2") {
            $(".comentario").hide();
        } else {
            $(".comentario").show();
        }
        $(".div_load").append('<img src="img/load.gif" class="img-responsive center-block"/>');
        $.post(solicitud_requerimiento", "opc=Ver_Solicitud&id=" + valor, function (objJson) {
            tb.empty();
            $(".div_load").empty();
            if (objJson.rpta === -1) {
                alert(objJson.mensaje);
                return;
            }
            var lista = objJson.lista;
            for (var i = 0; i < lista.length; i++) {
                texto_html += '<tr><td colspan="2" class="text-info table-bordered"><i class="fa fa-file"></i> REQUERIMIENTO : ' + lista[i].req + '</td></tr>';
                texto_html += '<tr><td>Apellidos y Nombres</td><td>' + lista[i].ap_p + ' ' + lista[i].ap_m + ' ' + lista[i].nombre + '</td></tr>';
                if (lista[i].ti_plazo === '1') {
                    texto_html += '<tr><td>Tipo de Plazo</td><td>Inicio de Contrato</td></tr>';
                }
                if (lista[i].ti_plazo === '2') {
                    texto_html += '<tr><td>Tipo de Plazo</td><td>Ingreso a planilla</td></tr>';
                }
                texto_html += '<tr><td>Nombre de Plazo</td><td>' + lista[i].plazo + '</td></tr>';
                texto_html += '<tr><td>Detalle de Plazo</td><td>' + lista[i].detalle_plazo + '</td></tr>';
                if (lista[i].ti_plazo === '2') {
                    texto_html += ' <tr class="success"><td>Mes de ingreso solicitado : </td><td>' + lista[i].mes + '</td></tr>';
                } else {
                    texto_html += ' <tr class="success"><td>Fecha de inicio de contrato solicitado : </td><td>' + lista[i].fecha_plazo + '</td></tr>';
                }
                texto_html += '<tr><td>Motivo de solicitud</td><td>' + lista[i].solicitud + '</td></tr>';
                if (lista[i].es_aut === '1') {
                    texto_html += '<tr><td>Estado de solicitud</td><td>Autorizado</td></tr>';

                }
                if (lista[i].es_aut === '0') {
                    texto_html += '<tr><td>Estado de solicitud</td><td>Sin Autorizar</td></tr>';
                    $(".foot_sol").empty();
                    $(".foot_sol").append(objJson.permisos);
                }
                if (tipo === '2') {
                    texto_html += '<tr><td>Comentario :</td><td>' + lista[i].comentario + '</td></tr>';
                }
                console.log("id_solicitud:" + lista[i].id);
                $(".data_procesar").val("&id=" + lista[i].id + "&tipo=" + lista[i].ti_plazo + "&fecha=" + lista[i].fecha_plazo);

            }
            texto_html += '';
            tb.append(texto_html);
            texto_html = "";
            $(".btn_procesar_sol").click(function () {
                if ($(".comentario").valid() === true) {
                    $.SmartMessageBox({
                        title: "�Advertencia!",
                        content: "�Esta seguro de procesar esta solicitud?",
                        buttons: '[No][Si]'
                    }, function (ButtonPressed) {
                        if (ButtonPressed === "Si") {
                            $.ajax({
                                url: "solicitud_requerimiento",
                                data: "opc=Procesar_Solicitud" + $(".data_procesar").val() + "&" + $(".comentario").serialize(),
                                type: "post"
                            }).done(function () {
                                $(".btn_cancel_form").click();
                                $(".comentario")[0].reset();
                                //add_tables("1");
                                // add_tables("2");
                                listar_solicitudes("1");
                                listar_solicitudes("2");
                                $.smallBox({
                                    title: "�Procesado con exito!",
                                    content: "<i class='fa fa-clock-o'></i> <i>Se ha procesado la solicituid correctamente...</i>",
                                    color: "#659265",
                                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                                    timeout: 4000
                                });

                            });
                        }
                        if (ButtonPressed === "No") {
                        }
                    });
                }
            });
        });
    }
    function listar_solicitudes(tipo) {

        var t_body;
        var text_html = '';
        var table_sol;

        var url = "";
        if (tipo === "1") {
            table_sol = $(".table_list_sol");
            t_body = $(".tbody_list_solicitud");
            url = "opc=Listar_Sol_Pendientes";
        } else if (tipo === "2") {
            url = "opc=Listar_Sol_Aut";
            t_body = $(".tbody_Sol_Aut");
            table_sol = $(".table_sol_aut");
        }
        t_body.empty();
        t_body.append('<tr><td colspan="10"><img src="img/ajax-loader.gif" class="img-responsive center-block" /></td> </tr>');
        $.post(solicitud_requerimiento", url, function (objJson) {
            if (objJson.rpta === -1) {
                alert(objJson.mensaje);
                return;
            } else {
               t_body.empty();
                var lista = objJson.lista;
                for (var g = 0; g < lista.length; g++) {
                    text_html += '<tr>';
                    text_html += '<td>' + (g + 1) + '</td>';
                    text_html += '<td><div class="btn-group"> <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Accion <span class="caret"></span></button><ul class="dropdown-menu"><li><a href="dgp?iddgp=' + lista[g].id_dgp + '&idtr=' + lista[g].id_trabajador + '&opc=Detalle"> Ver Requerimiento</a></li>';
                    text_html += '<li><a href="documento?iddgp=' + lista[g].id_dgp + '&idtr=' + lista[g].id_trabajador + '&opc=ReqIncompletoNextStep">Continuar registro</a></li>  <li class="divider"></li>';

                    //if (tipo === "1") {
                       // text_html += '<li><button class="btn btn-primary btn-labeled btn_sol" data-toggle="modal" type="button" data-target="#myModal" value="' + lista[g].id_solicitud_dgp + '"><span class="btn-label"><i class="fa fa-envelope"></i></span> Ver Solicitud</button></li></ul></div></td>';

                    //} else {
                        text_html += '<li><button class="btn btn-primary btn-labeled btn_sol_aut'+tipo+'" data-value="'+tipo+'" data-toggle="modal" type="button" data-target="#myModal" value="' + lista[g].id_solicitud_dgp + '"><span class="btn-label"><i class="fa fa-envelope"></i></span> Ver Solicitud</button></li></ul></div></td>';
                   // }
                    text_html += '<td><a href="trabajador?idtr=' + lista[g].id_trabajador + '&opc=list"><strong>' + lista[g].ap_paterno + ' ' + lista[g].ap_materno + ' ' + lista[g].no_trabajador + '</strong></a></td>';
                    text_html += '<td>' + lista[g].no_dep + '</td>';
                    text_html += '<td>' + lista[g].no_area + '</td>';
                    text_html += '<td>' + lista[g].no_puesto + '</td>';
                    text_html += '<td>' + lista[g].fe_desde + '</td>';
                    text_html += '<td>' + lista[g].fe_hasta + '</td>';
                    text_html += '<td>' + lista[g].fe_creacion + '</td>';
                    if (lista[g].es_autorizar === '0') {
                        text_html += '<td>Sin Autorizar</td>';
                    } else {
                        text_html += '<td>Autorizado</td>';
                    }
                    text_html += '</tr>';
                }
          t_body.append(text_html);
                text_html = '';

                $(".btn_sol_aut"+tipo).click(function () {
                    console.log("valor btn sol aut:" + $(this).val());
                    console.log("data-value:" + $(this).data("value"));
                    listar_det_sol($(this).val(), $(this).data("value"));
                });
               /* $(".btn_sol").click(function () {
                    console.log("valor btn sol:" + $(this).val());
                    listar_det_sol($(this).val(), "1");
                });*/

                if ($.fn.dataTable.isDataTable(table_sol)) {
                    console.log("destrpy datatable!")
                   //$.fn.dataTable.isDataTable(table_sol).destroy();
                } else {
                var tablas = table_sol.DataTable({searching: true, paging: true});

                    // var rows = tablas.fnGetNodes();

                    /* //for (var i = 0; i < rows.length; i++) {
                     var obj = $(rows[i]).find(".btn_sol_aut");
                     var obj2 = $(rows[i]).find(".btn_sol");
                     if (tipo === "1") {
                     obj2.click(function () {
                     listar_det_sol($(this).val(), tipo, tablas);
                     });
                     } else {
                     obj.click(function () {
                     listar_det_sol($(this).val(), "2", tablas);
                     });
                     }*/
                    // }

                }

            }

        });

    }


    $(document).ready(function () {
        pageSetUp();
        $.sound_path = sound/", $.sound_on = !0, jQuery(document).ready(function () {
            $("body").append("<div id='divSmallBoxes'></div>"), $("body").append("<div id='divMiniIcons'></div><div id='divbigBoxes'></div>")
        });
        //   $(".cod_aps").numeric();
        listar_solicitudes("1");
        listar_solicitudes("2");
    });
</script>
<%} else {
    out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
}
%>
