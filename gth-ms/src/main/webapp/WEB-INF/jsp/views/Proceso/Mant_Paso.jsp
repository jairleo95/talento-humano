<%@page import="com.app.domain.model.Usuario"%>
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

                        <!-- NEW COL START -->
                        <article class="col-sm-12 col-md-12 col-lg-12" >

                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget" id="wid-id-1" data-widget-editbutton="false" data-widget-custombutton="false">
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
                                    <span class="widget-icon"> <i class="fa fa-edit"></i> </span>
                                    <h2>Mantenimiento de Procesos</h2>				
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

                                        <form id="checkout-form" class="smart-form FormProcess" novalidate="novalidate">

                                            <fieldset>
                                                <div class="row">
                                                    <section class="col col-4">
                                                        <label class="input"> <i class="icon-prepend fa fa-user"></i>
                                                            <input type="text" id="nom_pro" name="proceso" placeholder="Nombre Proceso">
                                                        </label>
                                                    </section>
                                                    <section class="col col-8">
                                                        <label class="input"> <i class="icon-prepend fa fa-user"></i>
                                                            <input type="text" id="desc_pro" name="detalle" placeholder="Detalle Proceso">
                                                        </label>
                                                    </section>
                                                </div>

                                            </fieldset>



                                            <footer>
                                                <button type="button" class="btn btn-success btnUpdProcess">
                                                    Actuallizar
                                                </button>
                                                <input type="hidden" id="opid" value="">
                                                <button type="button" class="btn btn-danger cancelbtn">
                                                    Cancelar
                                                </button>
                                                <button type="button" class="btn btn-primary btnAddProcess">
                                                    Agregar
                                                </button>

                                            </footer>
                                        </form>
                                        <article class="col-xs-12 col-sm-12 col-md-12 col-lg-6 col-lg-offset-3">
                                            <div class="jarviswidget jarviswidget-color-greenDark" id="wid-id-2" data-widget-editbutton="false">
                                                <header>
                                                    <span class="widget-icon"> <i class="fa fa-table"></i> </span>
                                                    <h2>Procesos del Sistema<strong><label class="lb-list_pasos"></label></strong> </h2>
                                                </header>
                                                <div>
                                                    <div class="jarviswidget-editbox">
                                                    </div>
                                                    <div class="widget-body">
                                                        <div class="table-responsive">
                                                            <table class="table table-bordered">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Nombre</th>
                                                                        <th>Descripci�n</th>
                                                                        <th>Acciones</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody class="tbodyp">
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </article>
                                    </div>
                                    <!-- end widget content -->

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->

                        </article>
                        <!-- END COL -->

                    </div>

                    <!-- end row -->


                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->


<script type="text/javascript">
        function statupdate(id, obj) {
            var datos;
            if (obj.is(":checked")) {
                datos = {
                    id: id,
                    es: 1
                };
            } else {
                datos = {
                    id: id,
                    es: 0
                };
            }
            $.ajax({
                async: false,
                url: '../../Proceso?opc=statupdate',
                data: datos,
                type: 'POST'
            });
        }
        function listar_Proceso() {
            var s = $(".tbodyp");
            $.post(Proceso", "opc=ListarT", function (objJson) {
                s.empty();
                var lista = objJson.lista;
                var text;
                for (var j = 0; j < lista.length; j++) {
                    if (lista[j].desc === undefined) {
                        lista[j].desc = "Sin Datos";
                    }
                    text = "<tr>";
                    text += "<td class='td_nom" + lista[j].id + "'>" + lista[j].nombre + "</td>";
                    text += "<td class='td_desc" + lista[j].id + "'>" + lista[j].desc + "</td>";
                    text += "<td><div class='smart-form'><div class='pull-right col-xs-6 col-sm-6 col-md-6 col-lg-6'><button class='btn btn-success btn-sm btn-editar' value='" + lista[j].id + "' ><i class='fa fa-pencil' ></i></button></div>";
                    var info = 'statupdate("' + lista[j].id + '",$(this))';
                    if (lista[j].es == 1) {
                        text += "<div class='pull-left col-xs-6 col-sm-6 col-md-6 col-lg-6'><label class='toggle'>" +
                                "<input type='checkbox' onchange='" + info + "' name='checkbox-toggle' checked='checked'>" +
                                "<i data-swchon-text='ACTIVO' data-swchoff-text='INACTIVO'></i></label></div></div></td>";
                    } else {
                        text += "<div class='pull-left col-xs-6 col-sm-6 col-md-6 col-lg-6'><label class='toggle'>" +
                                "<input type='checkbox' onchange='" + info + "' name='checkbox-toggle'>" +
                                "<i data-swchon-text='ACTIVO' data-swchoff-text='INACTIVO'></i></label></div></div></td>";
                    }
                    text += "</tr>";
                    s.append(text);
                    $(".btn-editar").click(function () {
                        var id = $(this).val();
                        console.log("id: " + id);
                        $("#opid").val(id);
                        $("#nom_pro").val($(".td_nom" + id).text());
                        $("#desc_pro").val($(".td_desc" + id).text());
                        $(".btnAddProcess").hide();
                        $(".btnUpdProcess").show();
                        $(".cancelbtn").show();
                    });
                }
            });
        }
        $(document).ready(function () {
            $(".cancelbtn").hide();
            $(".btnUpdProcess").hide();
            listar_Proceso();
            $(".btnUpdProcess").click(function () {
                var nom = $("#nom_pro").val();
                var desc = $("#desc_pro").val();
                var id = $("#opid").val();
                $.post(Proceso", {
                    opc: "Modificar",
                    nom: nom,
                    desc: desc,
                    id: id
                }, function () {
                    listar_Proceso();
                    $(".cancelbtn").hide();
                    $(".btnUpdProcess").hide();
                    $("#opid").val("");
                    $(".btnAddProcess").show();
                    $("#nom_pro").val("");
                    $("#desc_pro").val("");
                });
            });
            $(".btnAddProcess").click(function () {
                var nom = $("#nom_pro").val();
                var desc = $("#desc_pro").val();
                $.post(Proceso", {
                    opc: "Registrar",
                    nom: nom,
                    desc: desc
                }, function (x) {
                    var rpta = x.rpta;
                    if (rpta == "1") {
                        listar_Proceso();
                        $("#nom_pro").val("");
                        $("#desc_pro").val("");
                    }else if(rpta=="-1"){
                        alert("Error al crear un nuevo proceso");
                    }
                });
            });
        });
</script>

<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>