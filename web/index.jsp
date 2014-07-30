<%-- 
    Document   : index
    Created on : 07-jul-2014, 10:52:37
    Author     : Alfa.sistemas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>.: RRHH :.</title>


        <link rel="stylesheet" href="css/Css_Logueo/styleN.css" type="text/css" />
        <link rel="stylesheet" href="css/Css_Alerta/Mensaje.css" type="text/css" />
        <script language="JavaScript" type="text/javascript">
            javascript:window.history.forward(1); //Esto es para cuando le pulse al botón de Atrás
            javascript:window.history.back(1); //Esto para cuando le pulse al botón de Adelante

        </script>
        <script type="text/javascript" src="js/JQuery/jQuery.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                setTimeout(function() {
                    $(".mensajes").fadeOut(800).fadeIn(800).fadeOut(500).fadeIn(500).fadeOut(300);
                }, 3000);
            });
        </script>
        <script type="text/javascript" src="js/Js_Alerta/alertify.js"></script>
        <link rel="stylesheet" href="css/Css_Alerta/alertify.core.css" />
        <link rel="stylesheet" href="css/Css_Alerta/alertify.default.css" />




        <script>
            function chk_ajax_login_with_php() {

                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
                if (username != "" & password != "") {



                    var params = "username=" + username + "&password=" + password + "&opc=ingresar";
                    var url = "src/ControlUsuario";
                    $.ajax({
                        type: 'POST',
                        url: url,
                        dataType: 'html',
                        data: params,
                        beforeSend: function() {
                            //  document.getElementById("status").innerHTML = 'esperando...';
                        },
                        complete: function() {

                        },
                        success: function(html) {
                            //document.getElementById("status").innerHTML= html;
                            //  window.location = "vistas/Principal.php"
                            if (html == 1) {
                                error();
                                //window.location = "vistas/Principal.php"

                            }
                            else {
                                error();
                            }

                        }
                    });

                }
            }
        </script>

        <script type="text/javascript"  src="js/Js_Alerta/Alertas.js"></script>



        <link rel="stylesheet" href="validator-master/fv.css" type="text/css" />
    </head>
    <body>
        <div id="container">
            <form  action="menu" method="POST">
                <div class="login ">Login</div>
                <div class="username-text">Usuario:</div>
                <div class="password-text">Clave:</div>

                <div class="username-field item">
                    <input type="text" name="username"   id="username"  autocomplete="off"    />
                    <div class='tooltip help'>
                        <span>?</span>
                        <div class='content'>
                            <b></b>
                            <p>Digite su usuario</p>
                        </div>
                    </div>
                </div>

                <input type="hidden" value="ingresar" name="opc" />
                <div class="password-field item">
                    <input type="password"  id="password" required="" name="clave" />
                </div>


                <div style="padding-left: 420px;"><input type="submit" name="submit" class="submit" value="IR"  onclick='chk_ajax_login_with_php();'/></div>
                <div id='status'></div>
            </form>
        </div>            
        <div id="footer">
            Universidad Peruana Unión - Recursos Humanos © 2014 
        </div>
    </body>
    <script src="js/JQuery/jQuery.js"></script>
    <script src="js/Js_Validar/multifield.js"></script>

    <script src = "js/Js_Validar/validator.js" ></script>

</html>