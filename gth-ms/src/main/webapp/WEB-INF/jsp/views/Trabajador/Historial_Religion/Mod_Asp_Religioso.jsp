<%-- 
    Document   : Mod_Asp_Religioso
    Created on : 28/01/2015, 02:18:06 PM
    Author     : Alex
--%>

<%@page import="com.app.domain.model.V_Ficha_Trab_Num_C"%>

<jsp:useBean id="ListaridTrabajador" scope="session" class="java.util.ArrayList"/>
    <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>

    <style>
        label{
            font-weight: bold;
        }
    </style>
    <form align="center" action=../trabajador"  >

        <%for (int index = 0; index < ListaridTrabajador.size(); index++) {
                V_Ficha_Trab_Num_C trb = new V_Ficha_Trab_Num_C();
                trb = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(index);
        %>

        <%}%>
    </form>

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
                                        <h2>MODIFICAR ASPECTO RELIGIOSO</h2>
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

                                        <div class="row">
                                            <form action=../trabajador"  >
                                                <%for (int index = 0; index < ListaridTrabajador.size(); index++) {
                                                        V_Ficha_Trab_Num_C trb = new V_Ficha_Trab_Num_C();
                                                        trb = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(index);
                                                %>  

                                                <div id="bootstrap-wizard-1" class="col-sm-12">

                                                    <div class="tab-content">
                                                        <div class="tab-pane active" id="tab1">
                                                            <div class="row" >

                                                                <div class="col-sm-4">

                                                                    <div class="form-group">
                                                                        <label>Religi�n:</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <select name="RELIGION"   required=""   class="form-control input-group-sm" id="reli">
                                                                                <option value="" disabled="" selected="">[SELECCIONE]</option> 
                                                                                <%if (trb.getLi_religion() != null) {
                                                                                        if (trb.getLi_religion().equals("1")) {%>
                                                                                <option value="1" selected="">Adventista</option> 
                                                                                <option value="2" >Catolico</option> 
                                                                                <option value="3" >�tros</option> 
                                                                                <%}
                                                                                    if (trb.getLi_religion().equals("2")) {%>
                                                                                <option value="1" >Adventida</option> 
                                                                                <option value="2" selected="">Catolico</option> 
                                                                                <option value="3" >�tros</option> 
                                                                                <%}
                                                                                    if (trb.getLi_religion().equals("3")) {%>
                                                                                <option value="1" >Adventida</option> 
                                                                                <option value="2" >Catolico</option> 
                                                                                <option value="3" selected="">�tros</option> 
                                                                                <%}
                                                                                } else {%>
                                                                                <option value="1" >Adventida</option> 
                                                                                <option value="2" >Catolico</option> 
                                                                                <option value="3" >�tros</option> 
                                                                                <%}%>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-4">
                                                                    <div class="form-group">
                                                                        <label>Nombre de la Iglesia:</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <input class="form-control input-group-sm" value="<%if (trb.getNo_iglesia() != null) {
                                                                                    out.print(trb.getNo_iglesia());
                                                                                } else {
                                                                                    out.print("");
                                                                                }%>" type="text" name="IGLESIA" id="igle" >

                                                                        </div>
                                                                    </div>
                                                                </div>     
                                                                <div class="col-sm-4">
                                                                    <div class="form-group">
                                                                        <label>Cargo en la Iglesia:</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <input class="form-control input-group-sm" value="<%if (trb.getDe_cargo() != null) {
                                                                                    out.print(trb.getDe_cargo());
                                                                                } else {
                                                                                    out.print("");
                                                                            }%>" type="text" name="CARGO"  id="ca_igle" maxlength="100">

                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-4">
                                                                    <div class="form-group">
                                                                        <label>Autoridad:</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <select name="AUTORIDAD"   required=""   class="form-control input-group-sm">
                                                                                <option value=""  selected="">[SELECCIONE]</option> 
                                                                                <% if (trb.getLi_autoridad() != null) {
                                                                                        if (trb.getLi_autoridad().trim().equals("1")) {%>
                                                                                <option value="1" selected="">Pastor</option> 
                                                                                <option value="2" >Primer Anciano</option> 
                                                                                <option value="3" >Sacerdote</option> 
                                                                                <%}
                                                                                    if (trb.getLi_autoridad().trim().equals("2")) {%>
                                                                                <option value="1" >Pastor</option> 
                                                                                <option value="2" selected="">Primer Anciano</option> 
                                                                                <option value="3" >Sacerdote</option> 
                                                                                <%}
                                                                                    if (trb.getLi_autoridad().trim().equals("3")) {%>
                                                                                <option value="1" >Pastor</option> 
                                                                                <option value="2" >Primer Anciano</option> 
                                                                                <option value="3" selected="">Sacerdote</option> 
                                                                                <%}
                                                                                } else {%>
                                                                                <option value="" selected="" >[SELECCIONE]</option>
                                                                                <option value="1" >Pastor</option> 
                                                                                <option value="2" >Primer Anciano</option> 
                                                                                <option value="3" >Sacerdote</option> 
                                                                                <%}%>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-4">
                                                                    <div class="form-group">
                                                                        <label>Nombre y Apellidos (Autoridad):</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <input class="form-control input-group-sm" value="<%if (trb.getNo_ap_autoridad() != null) {
                                                                                    out.print(trb.getNo_ap_autoridad());
                                                                                } else {
                                                                                    out.print("");
                                                                                }%>" type="text" name="AUT_APELLIDOSNOMBRES"  required="" id="no_ape" >

                                                                        </div>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-4">
                                                                    <div class="form-group">
                                                                        <label>Telefono/Celular:</label>
                                                                        <div class="input-group">
                                                                            <span class="input-group-addon"><i class="fa fa-user fa-lg fa-fw"></i></span>
                                                                            <input class="form-control input-group-sm" value="<%if (trb.getCl_autoridad() != null) {
                                                                                    out.print(trb.getCl_autoridad());
                                                                                } else {
                                                                                    out.print("");
                                                                                }%>" type="text" name="AUT_CELULAR" id="te_ce" >

                                                                        </div>
                                                                    </div>
                                                                </div>



                                                            </div>

                                                        </div>
                                                        <%String idtr = request.getParameter("idtr");%>
                                                        <input type="hidden" value="<%=idtr%>" name="idtr">
                                                        <input type="hidden" value="Modificar_Asp_Rel" name="opc">
                                                        <center>
                                                            <button type="submit" class="btn btn-labeled btn-info" name="opc">
                                                                <span class="btn-label">
                                                                    <i class="glyphicon glyphicon-pencil"></i>
                                                                </span>Modificar
                                                            </button>
                                                        </center>
                                                    </div>
                                                </div>

                                                <%
                                                    }%>
                                            </form>
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

    <!-- PAGE RELATED PLUGIN(S) -->
    <script src="js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
    <script src="js/plugin/fuelux/wizard/wizard.min.js"></script>
    <script type="text/javascript" src="js/libs/jquery.numeric.js"></script>


    <script type="text/javascript">

        // DO NOT REMOVE : GLOBAL FUNCTIONS!

        $(document).ready(function() {

            pageSetUp();




            var $validator = $("#wizard-1").validate({
                rules: {
                    email: {
                        required: true,
                        email: "Your email address must be in the format of name@domain.com"
                    },
                    FECHA_NAC: {
                        required: true,
                        val_fecha: true
                    }
                    ,
                    FECHA_NAC_H: {
                        val_fecha: true
                    }
                    ,
                    fname: {
                        required: true
                    },
                    lname: {
                        required: true
                    },
                    country: {
                        required: true
                    },
                    city: {
                        required: true
                    },
                    postal: {
                        required: true,
                        minlength: 4
                    },
                    wphone: {
                        required: true,
                        minlength: 10
                    },
                    hphone: {
                        required: true,
                        minlength: 10
                    }
                },
                messages: {
                    fname: "Please specify your First name",
                    lname: "Please specify your Last name",
                    email: {
                        required: "We need your email address to contact you",
                        email: "Your email address must be in the format of name@domain.com"
                    }
                },
                highlight: function(element) {
                    $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                },
                unhighlight: function(element) {
                    $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                },
                errorElement: 'span',
                errorClass: 'help-block',
                errorPlacement: function(error, element) {
                    if (element.parent('.input-group').length) {
                        error.insertAfter(element.parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
            jQuery.validator.addMethod("val_fecha", function(value, element) {
                var d = value.split("-");
                return this.optional(element) || String(parseInt(d[0])).length == 4;
            }, "�Fecha ingresada invalida!");

            $('#bootstrap-wizard-1').bootstrapWizard({
                'tabClass': 'form-wizard',
                'onNext': function(tab, navigation, index) {
                    var $valid = $("#wizard-1").valid();
                    if (!$valid) {
                        $validator.focusInvalid();
                        return false;
                    } else {
                        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).addClass(
                                'complete');
                        $('#bootstrap-wizard-1').find('.form-wizard').children('li').eq(index - 1).find('.step')
                                .html('<i class="fa fa-check"></i>');
                    }
                }
            });


            // fuelux wizard
            var wizard = $('.wizard').wizard();

            wizard.on('finished', function(e, data) {
                //$("#fuelux-wizard").submit();
                //console.log("submitted!");
                $.smallBox({
                    title: "Congratulations! Your form was submitted",
                    content: "<i class='fa fa-clock-o'></i> <i>1 seconds ago...</i>",
                    color: "#5F895F",
                    iconSmall: "fa fa-check bounce animated",
                    timeout: 4000
                });

            });


        })

       </script>


<!--Solo numeros -->
<script type="text/javascript">
    $("#docs, .doc_c").numeric();
    $("#doc, .doc_c").numeric(false, function() {
        alert("Solo Numeros Enteros");
        this.value = "";
        this.focus();
    });
    $(".positive").numeric({negative: false}, function() {
        alert("No negative values");
        this.value = "";
        this.focus();
    });
    $(".positive-integer").numeric({decimal: false, negative: false}, function() {
        alert("Positive integers only");
        this.value = "";
        this.focus();
    });

    $("#remove").click(
            function(e)
            {
                e.preventDefault();
                $(".numeric,.integer,.positive").removeNumeric();
            }
    );
</script>

<script>
    $(document).ready(
            function() {
                $("#reli").change(
                        function() {
                            iglesia($(this).val());
                        }
                );
            });
    function iglesia(igle) {
        if (igle == '1') {
            $("#igle").attr("required", "required")
            $("#igle").val("");
            $("#ca_igle").val("");
            $("#no_ape").val("");
            $("#no_ape").attr("required","re")
            $("#te_ce").val("");
        }
        if (banco == '2') {
            $("#igle").removeAttr("required", "required")
            $("#igle").val("");
            $("#ca_igle").val("");
            $("#no_ape").val("");
            $("#no_ape").removeAttr("required","re")
            $("#te_ce").val("");
        }
        if (banco == '3') {
            $("#igle").removeAttr("required", "required")
            $("#no_ape").removeAttr()("required","re")

        }
    }
</script>

