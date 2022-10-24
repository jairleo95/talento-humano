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
        <h2><b>PADRES</b></h2>

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
                    <b>DATOS DE LOS PADRES</b>
                </header>
                <fieldset>
                    <div class="row">

                        <section class="col col-6">
                            <label class="input">Apellidos y Nombres del Padre :
                                <input type="text" name="APELLIDOS_NOMBRES_PADRE" class="padre" placeholder="Apellidos y Nombres del Padre" required="" id="as"  maxlength="70">
                            </label>
                        </section>
                        <section class="col col-6">
                            <label class="input">Apellidos y Nombres de la Madre :
                                <input type="text" name="APELLIDOS_NOMBRES_MADRE" class="madre" placeholder="Apellidos y Nombres de la Madre" required=""  maxlength="70">
                            </label>
                        </section>

                    </div>


                </fieldset>
                <%
                    String opc = request.getParameter("opc");
                    if (opc != null) {
                        if (opc.equals("Mod")) {
                %>
                <input type="hidden" name="opc"   class="submit" value="Modificar_Padre_madre">
                <%
                    }
                } else {
                %>
                <input type="hidden" name="opc"   class="submit" value="Registrar Padres">
                <%}%>
                <footer>
                    <input type="hidden" name="idtr"   value="<%=request.getParameter("idtr")%>" class="text-box idtr" >
                    <button type="submit" class="btn btn-primary btn_submit" name="opc">
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



</center><br><br>

    <!-- PAGE RELATED PLUGIN(S) -->
    <script src="js/plugin/jquery-form/jquery-form.min.js"></script>

    <script type="text/javascript">

            // DO NOT REMOVE : GLOBAL FUNCTIONS!

            var opc = $(".submit").val();
            if (opc === 'Modificar_Padre_madre') {
                $.post("familiar", "opc=List_Padre&id=" + $(".idtr").val(), function(objJson) {
                    var lista = objJson.lista;
                    $(".padre").val(lista[0].padre);
                    $(".madre").val(lista[0].madre);
                });
                $('.btn_submit').text("Modificar");
            }
            $(document).ready(function() {

                pageSetUp();


                var $checkoutForm = $('#checkout-form').validate({
                    // Rules for form validation
                    rules: {
                        fname: {
                            required: true
                        },
                        lname: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        phone: {
                            required: true
                        },
                        country: {
                            required: true
                        },
                        city: {
                            required: true
                        },
                        code: {
                            required: true,
                            digits: true
                        },
                        address: {
                            required: true
                        },
                        name: {
                            required: true
                        },
                        card: {
                            required: true,
                            creditcard: true
                        },
                        cvv: {
                            required: true,
                            digits: true
                        },
                        month: {
                            required: true
                        },
                        year: {
                            required: true,
                            digits: true
                        }
                    },
                    // Messages for form validation
                    messages: {
                        fname: {
                            required: 'Please enter your first name'
                        },
                        lname: {
                            required: 'Please enter your last name'
                        },
                        email: {
                            required: 'Please enter your email address',
                            email: 'Please enter a VALID email address'
                        },
                        phone: {
                            required: 'Please enter your phone number'
                        },
                        country: {
                            required: 'Please select your country'
                        },
                        city: {
                            required: 'Please enter your city'
                        },
                        code: {
                            required: 'Please enter code',
                            digits: 'Digits only please'
                        },
                        address: {
                            required: 'Please enter your full address'
                        },
                        name: {
                            required: 'Please enter name on your card'
                        },
                        card: {
                            required: 'Please enter your card number'
                        },
                        cvv: {
                            required: 'Enter CVV2',
                            digits: 'Digits only'
                        },
                        month: {
                            required: 'Select month'
                        },
                        year: {
                            required: 'Enter year',
                            digits: 'Digits only please'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $registerForm = $("#smart-form-register").validate({
                    // Rules for form validation
                    rules: {
                        username: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        password: {
                            required: true,
                            minlength: 3,
                            maxlength: 20
                        },
                        passwordConfirm: {
                            required: true,
                            minlength: 3,
                            maxlength: 20,
                            equalTo: '#password'
                        },
                        firstname: {
                            required: true
                        },
                        lastname: {
                            required: true
                        },
                        gender: {
                            required: true
                        },
                        terms: {
                            required: true
                        }
                    },
                    // Messages for form validation
                    messages: {
                        login: {
                            required: 'Please enter your login'
                        },
                        email: {
                            required: 'Please enter your email address',
                            email: 'Please enter a VALID email address'
                        },
                        password: {
                            required: 'Please enter your password'
                        },
                        passwordConfirm: {
                            required: 'Please enter your password one more time',
                            equalTo: 'Please enter the same password as above'
                        },
                        firstname: {
                            required: 'Please select your first name'
                        },
                        lastname: {
                            required: 'Please select your last name'
                        },
                        gender: {
                            required: 'Please select your gender'
                        },
                        terms: {
                            required: 'You must agree with Terms and Conditions'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $reviewForm = $("#review-form").validate({
                    // Rules for form validation
                    rules: {
                        name: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        review: {
                            required: true,
                            minlength: 20
                        },
                        quality: {
                            required: true
                        },
                        reliability: {
                            required: true
                        },
                        overall: {
                            required: true
                        }
                    },
                    // Messages for form validation
                    messages: {
                        name: {
                            required: 'Please enter your name'
                        },
                        email: {
                            required: 'Please enter your email address',
                            email: '<i class="fa fa-warning"></i><strong>Please enter a VALID email addres</strong>'
                        },
                        review: {
                            required: 'Please enter your review'
                        },
                        quality: {
                            required: 'Please rate quality of the product'
                        },
                        reliability: {
                            required: 'Please rate reliability of the product'
                        },
                        overall: {
                            required: 'Please rate the product'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $commentForm = $("#comment-form").validate({
                    // Rules for form validation
                    rules: {
                        name: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        url: {
                            url: true
                        },
                        comment: {
                            required: true
                        }
                    },
                    // Messages for form validation
                    messages: {
                        name: {
                            required: 'Enter your name',
                        },
                        email: {
                            required: 'Enter your email address',
                            email: 'Enter a VALID email'
                        },
                        url: {
                            email: 'Enter a VALID url'
                        },
                        comment: {
                            required: 'Please enter your comment'
                        }
                    },
                    // Ajax form submition
                    submitHandler: function(form) {
                        $(form).ajaxSubmit({
                            success: function() {
                                $("#comment-form").addClass('submited');
                            }
                        });
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $contactForm = $("#contact-form").validate({
                    // Rules for form validation
                    rules: {
                        name: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        message: {
                            required: true,
                            minlength: 10
                        }
                    },
                    // Messages for form validation
                    messages: {
                        name: {
                            required: 'Please enter your name',
                        },
                        email: {
                            required: 'Please enter your email address',
                            email: 'Please enter a VALID email address'
                        },
                        message: {
                            required: 'Please enter your message'
                        }
                    },
                    // Ajax form submition
                    submitHandler: function(form) {
                        $(form).ajaxSubmit({
                            success: function() {
                                $("#contact-form").addClass('submited');
                            }
                        });
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $loginForm = $("#login-form").validate({
                    // Rules for form validation
                    rules: {
                        email: {
                            required: true,
                            email: true
                        },
                        password: {
                            required: true,
                            minlength: 3,
                            maxlength: 20
                        }
                    },
                    // Messages for form validation
                    messages: {
                        email: {
                            required: 'Please enter your email address',
                            email: 'Please enter a VALID email address'
                        },
                        password: {
                            required: 'Please enter your password'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                var $orderForm = $("#order-form").validate({
                    // Rules for form validation
                    rules: {
                        name: {
                            required: true
                        },
                        email: {
                            required: true,
                            email: true
                        },
                        phone: {
                            required: true
                        },
                        interested: {
                            required: true
                        },
                        budget: {
                            required: true
                        }
                    },
                    // Messages for form validation
                    messages: {
                        name: {
                            required: 'Please enter your name'
                        },
                        email: {
                            required: 'Please enter your email address',
                            email: 'Please enter a VALID email address'
                        },
                        phone: {
                            required: 'Please enter your phone number'
                        },
                        interested: {
                            required: 'Please select interested service'
                        },
                        budget: {
                            required: 'Please select your budget'
                        }
                    },
                    // Do not change code below
                    errorPlacement: function(error, element) {
                        error.insertAfter(element.parent());
                    }
                });

                // START AND FINISH DATE
                $('#startdate').datepicker({
                    dateFormat: 'dd.mm.yy',
                    prevText: '<i class="fa fa-chevron-left"></i>',
                    nextText: '<i class="fa fa-chevron-right"></i>',
                    onSelect: function(selectedDate) {
                        $('#finishdate').datepicker('option', 'minDate', selectedDate);
                    }
                });

                $('#finishdate').datepicker({
                    dateFormat: 'dd.mm.yy',
                    prevText: '<i class="fa fa-chevron-left"></i>',
                    nextText: '<i class="fa fa-chevron-right"></i>',
                    onSelect: function(selectedDate) {
                        $('#startdate').datepicker('option', 'maxDate', selectedDate);
                    }
                });



            })

    </script>
    <script>
        $(document).ready(function() {
            var b = $(".tbodys");

            $("#btnfiltrar").click(
                    function() {


                        $.post("ajax/Ajax_Conyugue/Ajax_Busc_Conyug.jsp", $("#frm_filtro").serialize(), function(objJson) {
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

        }
        );

</script>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>