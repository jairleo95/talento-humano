<%@page import="com.app.domain.model.V_Solicitud_Requerimiento"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<link href="css/your_style.css" rel="stylesheet" type="text/css"/>
<style>
    .noti{
        width:40px;
        height: 40px;
        border-radius:50%;
        background: #e74c3c;
        color: white;
        font-size: 20px;
        padding-left:6px;
        padding-top: 9px;
        margin-left: 230px;
        position:fixed;
        z-index: 5;
    }
    .pre-img{
        width:50px;
        height: 50px;
        border-radius: 50%;
    }
    .modal-dialog {
        top: 20%;
        width: 100%;
        position: absolute;
    }
    .modal-content {
        border-radius: 0px;
        border: none;
        top: 40%;
    }
    .modal-body {
        background-color: #fff;

        color: black;
    }
</style>
        <div class="col-lg-offset-1 col-md-offset-1">
            <!-- widget grid -->
            <section id="widget-grid" class="">

                <!-- row -->
                <div class="row">
                    <article class="col-sm-11">
                        <!-- new widget -->
                        <div class="jarviswidget" id="wid-id-0" data-widget-togglebutton="false" data-widget-editbutton="false" data-widget-fullscreenbutton="false" data-widget-colorbutton="false" data-widget-deletebutton="false">

                            <header>
                                <span class="widget-icon"> <i class="glyphicon glyphicon-stats txt-color-darken"></i> </span>
                                <h2>Validar Fotos</h2>

                                <ul class="nav nav-tabs pull-right in" id="myTab"> 
                                    <li class="active">
                                        <a data-toggle="tab" href="#s1"><i class="fa fa-check-circle"></i> <span class="hidden-mobile hidden-tablet">Fotos Para Validar</span></a>

                                    </li>

                                    <li>
                                        <a data-toggle="tab" href="#s2"><i class="fa fa-check"></i> <span class="hidden-mobile hidden-tablet">Fotos Validadas</span></a>
                                    </li>
                                </ul>

                            </header>

                            <!-- widget div-->
                            <div class="">
                                <!-- end widget edit box -->

                                <div class="widget-body">
                                    <!-- content -->
                                    <div id="myTabContent" class="tab-content">
                                        <div class="tab-pane fade active in" id="s1">
                                            <table id="example-table" class="table table-striped" >
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Apellido Paterno</th>
                                                        <th>Apellido Materno</th>
                                                        <th>Nombre</th>
                                                        <th>Foto</th>

                                                    </tr>
                                                </thead>
                                            </table>

                                        </div>
                                        <!-- end s1 tab pane -->

                                        <div class="tab-pane fade" id="s2">
                                            <table id="example-table2" class="table table-condensed" >
                                                <thead>
                                                    <tr>
                                                        <th>DNI</th>
                                                        <th>Apellido Paterno</th>
                                                        <th>Apellido Materno</th>
                                                        <th>Nombre</th>
                                                        <th>Foto</th>
                                                    </tr>
                                                </thead>
                                            </table>

                                        </div>

                                    </div>

                                    <!-- end content -->
                                </div>

                            </div>
                            <!-- end widget div -->
                        </div>
                        <!-- end widget -->

                    </article>
                </div>

                <!-- end row -->

            </section>
            <!-- end widget grid -->
        </div>

        <div class="modal-foto modal fade  bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">

                    <div class="modal-body text-center">

                        <h1 class="namet"></h1>
                        <span class="badge noti"></span>
                        <div class="foto-tr">
                            <img style= "width: 400px; height:  300px;" class="imagental img-thumbnail"/>
                        </div>

                        <div class="Validar">
                            <small>Validar la Foto  ? </small>
                            <form>
                                <input class="id-tr" type="hidden" >
                                <input type="hidden" value="">
                                <button class="acep btn btn-success btn-md"><samp class="fa fa-check"></samp> Aceptar</button>
                                <button class="recha btn btn-danger btn-md"><samp class="fa fa-ban"></samp> Rechazar</button>
                            </form>
                        </div>
                    </div>
                </div>
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
        var term;
        $(document).ready(function () {
            DataT("0", "#example-table");
            DataT("2", "#example-table2");

        });

        function DataT(p, tab) {

            $(tab).DataTable({
                "autoWidth": true,
                "ajax": {
                    "url": "validar_foto?opc=getFotos&estado=" + p,
                    "type": "POST",
                    "dataSrc": "Fotos_NoVal"
                },
                "columns": [
                    {"data": "NU_DOC"},
                    {"data": "AP_PATERNO"},
                    {"data": "AP_MATERNO"},
                    {"data": "NO_TRABAJADOR"}
                ],
                "createdRow": function (row, data, index) {
                    $(row).append('<td><img class="pre-img" src="Archivo/Fotos/' + data.AR_FOTO + '" /></td>');
                    if (data.EFOTO == "1") {
                        $(row).addClass("success");
                    } else if (data.EFOTO == "2") {
                        $(row).addClass("danger");
                    }
                    $(row).click(function () {
                        if (data.EFOTO == "0") {
                            $('.Validar').show(200);
                            $('.modal-foto').modal('show');
                            $('.imagental').attr("src", "Archivo/Fotos/" + data.AR_FOTO);
                            $('.namet').text(data.NO_TRABAJADOR + ' ' + data.AP_PATERNO + ' ' + data.AP_MATERNO);
                            $('.id-tr').val(data.ID_TRABAJADOR);
                        } else {
                            $('.Validar').hide();
                            $('.modal-foto').modal('show');
                            $('.imagental').attr("src", "Archivo/Fotos/" + data.AR_FOTO);
                            $('.namet').text(data.NO_TRABAJADOR + ' ' + data.AP_PATERNO + ' ' + data.AP_MATERNO);

                        }

                    });

                }

            });

        }

        $('.acep').click(function () {
            var idtr = $('.id-tr').val();
            $.ajax({
                type: 'POST',
                url: 'validar_foto',
                data: 'opc=validar&idtr=' + idtr + '&estado=1',
                success: function (r) {

                }
            });

        });

        $('.recha').click(function () {
            var idtr = $('.id-tr').val();
            $.ajax({
                type: 'POST',
                url: 'validar_foto',
                data: 'opc=validar&idtr=' + idtr + '&estado=2',
                success: function (r) {
                }
            });
        });

    </script>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
