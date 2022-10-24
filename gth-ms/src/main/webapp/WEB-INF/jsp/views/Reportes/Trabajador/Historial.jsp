<%-- 
    Document   : Historial
    Created on : 14/04/2015, 10:05:59 AM
    Author     : Andres
--%>
    <div id="main" role="main" style="margin: 0px;">
        <div id="content" >
            <section id="widget-grid" class="">
                <div class="row">

                    <div class="well">
                        <form class="smart-form form_f">

                            <h1 class="text-center">Historial de Modificaciones <small>/ Trabajadores</small></h1><br>
                            <h1 class="text-left font-md semi-bold">Filtros:</h1><br>
                            <div class="row">
                                <div class="col col-xs-9">
                                    <section class="col col-sm-6">
                                        <label>Desde:</label>
                                        <label class="input"> <i class="icon-append fa fa-calendar"></i>
                                            <input type="text"  placeholder="Seleccionar Fecha" class="datepicker fe_desde" id="dtp1" data-dateformat='dd/mm/yy' name="fe_inicio">
                                        </label>
                                    </section>
                                    <section class="col col-sm-6">
                                        <label>Hasta:</label>
                                        <label class="input"> <i class="icon-append fa fa-calendar"></i>
                                            <input type="text"  placeholder="Seleccionar Fecha" class="datepicker fe_hasta" id="dtp2" data-dateformat='dd/mm/yy' name="fe_fin">
                                        </label>
                                    </section>
                                </div>
                                <div class="col col-xs-3">
                                    <center>
                                        <section class="col col-sm-12">
                                            <a class="btn btn-primary btn-circle btn-xl btnEnviar"><i class="glyphicon glyphicon-search"></i></a>
                                        </section>
                                    </center>
                                </div>


                            </div>
                        </form>


                    </div>

                </div>
                <div class="row">
                    <div class="well">
                        <div class="table-responsive cont_t">
                            <table class="tabla_t table table-bordered table-hover table-striped">
                                <thead>
                                    <tr>
                                        <th class="text-center semi-bold">Nro</th>
                                        <th class="text-center semi-bold">Trabajador</th>
                                        <th class="text-center semi-bold">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody class="tbodys">
                                </tbody>
                            </table>
                        </div>


                    </div>

                </div>
            </section>
        </div>
    </div>
<script type="text/javascript" src="js/libs/jquery.numeric.js"></script>
<script src="js/plugin/jquery-nestable/jquery.nestable.min.js"></script>
<script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
<script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
<script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
<script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $("#dtp1").datepicker({
            dateFormat: "dd/mm/yy",
            defaultDate: "+1d",
            prevText: '<<',
            nextText: '>>',
            numberOfMonths: 2,
            onClose: function (selectedDate) {
                $("#dtp2").datepicker("option", "minDate", selectedDate);
                $("#dtp2").datepicker("setDate", selectedDate);
            }
        });
        $("#dtp2").datepicker({
            dateFormat: "dd/mm/yy",
            defaultDate: "+1d",
            prevText: '<<',
            nextText: '>>',
            numberOfMonths: 2,
            onClose: function (selectedDate) {

                $("#dtp1").datepicker("option", "maxDate", selectedDate);
            }
        });
        $('.tabla_t').DataTable();
        $('.btnEnviar').click(function () {
            var data = 'fe_inicio='+$('.fe_desde').val()+'&fe_fin='+$('.fe_hasta').val()
            $.post("RHistorial", data, function (objJson) {
                var lista = objJson.lista;
                if (lista.length < 1) {
                    $.smallBox({
                        title: "Busqueda de Historial",
                        content: "<i class='fa fa-ban'></i> <i>No hay modificaciones en ese rango de fechas</i>",
                        color: "#dfb56c",
                        iconSmall: "bounce animated",
                        timeout: 4000
                    });
                    crear_t();
                    $('.tabla_t').DataTable();
                } else {
                    var t = "";
                    for (var i = 0; i < lista.length; i++) {
                        t += "<tr>";
                        t += "<td>" + (i + 1) + "</td>";
                        t += "<td>" + lista[i].no_tra + " " + lista[i].ap_pat + " " + lista[i].ap_mat + "</td>";
                        t += '<td>' + barra_acciones(lista[i].id_tra) + '</td></tr>';
                    }

                    crear_t();
                    $('.tbodys').append(t);
                    $('.tabla_t').DataTable();
                }
            });
            function crear_t() {
                var text = '<table class="tabla_t table table-bordered table-hover table-striped"><thead><tr><th class="text-center semi-bold">Nro</th>';
                text += '<th class="text-center semi-bold">Trabajador</th><th class="text-center semi-bold">Detalle</th></tr></thead><tbody class="tbodys">';
                text += '</tbody></table>';
                $('.cont_t').empty();
                $('.cont_t').append(text);
            }
            function barra_acciones(idtr) {
                var tex = '<center><div class="btn-group">';
                tex += '<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-expanded="false">';
                tex += 'Action <span class="caret"></span></button>';
                tex += '<ul class="dropdown-menu" role="menu">';
                tex += '<li><a href="RHistorial?opc=mod_tra&idtr=' + idtr + '">Detalle</a></li>';
                tex += '</ul>';
                tex += '</div></center>';
                return tex;
            }
        });
    });
</script>