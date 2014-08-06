

<%@page import="pe.edu.upeu.application.model.Auto_Mostrar"%>
<%@page import="pe.edu.upeu.application.model.V_Ficha_Trab_Num_C"%>
<%@page import="pe.edu.upeu.application.model.Trabajador"%>
<jsp:useBean id="ListaridTrabajador" scope="application" class="java.util.ArrayList"/>
<jsp:useBean id="List_Auto_mostrar" scope="application" class="java.util.ArrayList"/>

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <script type="text/javascript" src="../../js/JQuery/jQuery.js"></script>
        <script type="text/javascript" src="../../js/JQuery/jquery.autoheight.js"></script>



        <title>Detalle de Trabajador</title>


        <script type="text/javascript" src="../../js/Js_Alerta/alertify.js"></script>
        <link rel="stylesheet" href="../../css/Css_Alerta/alertify.core.css" />
        <link rel="stylesheet" href="../../css/Css_Alerta/alertify.default.css" />
        <script type="text/javascript"  src="../../js/Js_Alerta/Alertas.js"></script>

        <link rel="stylesheet" type="text/css" href="../../css/Css_Menu_Desplegable/default.css" />
        <link rel="stylesheet" type="text/css" href="../../css/Css_Menu_Desplegable/component.css" />
        <script src="../../js/modernizr.custom.js"></script>

        <style type="text/css">
            body{

                margin-left: auto;
                margin-right: auto  ;
                width: 70%;
                // margin: 0;
            }
            .titulo{

                border-radius: 10px;
                background-color:#0c9ba0 ;
                width: 100%;
                padding: 0.5%;
                margin-top: 0.5%;
                font-family: arial;
                font-size: 130%;
                text-align: center;
                color: white;

            }
            .autoHeight{
                border-style:none;            
            }

            .td{
                font-weight: bold;
                text-align: right;
            }
            .info-det{
                margin: 5%;
            }

            .table-det{
                position: static;
            }
            .submit{
                //position: relative;

                background-color: #0575f4;
                color: white;
                width: 100%;

            }
            .submit:hover{
                background-color: #643771;
            }

        </style>
        <script type="text/javascript">
            $(document).ready(
                    function() {
                        $(".item-m").click(
                                function() {
                                    $("#titulo-c").text($(this).text());
                                }
                        );
                    }
            );

        </script>
    </head>
    <body >
        <%//if ($_REQUEST["CR"]=1){ %>
        <script type="text/javascript">
            // alerta_dt_ingresados();
        </script>
        <%//}%>
        <div class="container demo-2">	
            <!--<div class="main clearfix">-->
            <div class="main ">
                <div class="column ">
                    <div id="dl-menu" class="dl-menuwrapper">
                        <button class="dl-trigger">Open Menu</button>
                        <ul class="dl-menu">
                            <li class="active"><a  class="item-m" href="Datos_Generales.jsp?idtr=<?echo $idtr;?>" target="contenido">Datos Personales  </a></li>
                            <li><a class="item-m" href="Aspecto_Academico.jsp?idtr=<?echo $idtr;?>" target="contenido">Aspecto Academico</a></li>
                            <li><a class="item-m" href="Aspecto_Social.jsp?idtr=<?echo $idtr;?>" target="contenido" >Aspecto Social</a></li>
                            <li><a class="item-m" href="Familiar/Detalle_Familiar.jsp?idtr=<?echo $idtr;?>" target="contenido" >Familiares</a></li>
                            <li><a class="item-m" href="List_Dgp_Trabajador.jsp?idtr=<?echo $idtr;?>&iddgp=<? echo $iddetalle_dgp;?>" target="contenido">Requerimientos</a></li>
                            <li><a class="item-m" href="List_Doc_Trabajador.jsp?idtr=<?echo $idtr;?>" target="contenido">Documentos</a></li>
                            <li><a class="item-m" href="../Contrato/Detalle_Info_Contractual.jsp?idtr=<?echo $idtr;?>" target="contenido">Informacion Contractual</a></li>
                        </ul>
                    </div><!-- /dl-menuwrapper -->
                </div>
            </div>
        </div>
        <div class="container theme-showcase" >


            <div  class="titulo">Detalle del Trabajador</div> 
            <table style="background-color: white" class="table-det">


                <%
                    /*require_once '../Modelo/Modelo_Imagen.php';
                     $mod_f= new Modelo_Imagen();
                     $idf=$mod_f->LIST_FOTO_TRABAJADOR($idtr);
                     */
                %>

                <%
                    if (false) {
                        //if ($idf==null){%>
                <tr><td><img src="../../imagenes/avatar_default.jpg"  width="100"  height="100"></td>
                <a href="Sub_fotos.php?idtr=<? echo $idtr ;?>">Subir imagen</a>
                <%
                    }
                    //}else{%>
                <tr><td><img src="Foto.php?idf=<?echo $idf;?>"  width="100"  height="100"></td>
                        <%//}%>
                    <td>
                        <div >
                            <table class="info-det">
                                <%
                                    for (int index = 0; index < ListaridTrabajador.size(); index++) {
                                        V_Ficha_Trab_Num_C trb = new V_Ficha_Trab_Num_C();
                                        trb = (V_Ficha_Trab_Num_C) ListaridTrabajador.get(index);
                                %>
                                <tr><td class="td">Nombre :</td><td><%=trb.getNo_trabajador()%></td></tr>
                                <tr><td class="td">Apellido Paterno :</td><td><%=trb.getAp_paterno()%></td></tr>
                                <tr><td class="td">Apellido Materno :</td><td><%=trb.getAp_paterno()%></td></tr>
                                <tr><td class="td">Fecha de Nacimiento :</td><td><%=trb.getFe_nac()%></td></tr>
                                <%}%>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

            <div  class="titulo" id="titulo-c">Informacion Personal<%=List_Auto_mostrar.size()%></div> 

            <%     
              if  (List_Auto_mostrar.size()==1){  
                      for(int r=0;r<List_Auto_mostrar.size();r++){              
                          Auto_Mostrar a = new Auto_Mostrar();
                          a=(Auto_Mostrar)List_Auto_mostrar.get(r);
            %>
    
            <iframe name="contenido" id="contenido"  class="autoHeight" src="<%
            out.println(a.getDi_url());
            %>" width="100%" height="100%" ></iframe>
            <% }}else{ %>
             <iframe name="contenido" id="contenido"  class="autoHeight" src="Datos_Generales.jsp" width="100%" height="100%" ></iframe>
            <%}%>
        </div> 
        <div>
            <%
                String aut = request.getParameter("aut");
                HttpSession sesion = request.getSession(true);

                if (aut != null) {
                    if (aut.equals("1")) {

                        String idp = (String) sesion.getAttribute("p");
                        String iddgp = request.getParameter("dgp");
                        String cod = request.getParameter("c");
                        String iddrp = request.getParameter("drp");
                        String id_pasos = request.getParameter("pas");
                        String nropaso = request.getParameter("np");

            %>
            <center>
                <form class="form" action="../../autorizacion" method="post" > 
                    <table > 
                        <input type="hidden" name="IDDETALLE_DGP"  value="<%=iddgp%>"  >           
                        <input type="hidden" name="NROPASO" value="<%=nropaso%>"  >                
                        <input type="hidden" name="USUARIO_IP" >  
                        <input type="hidden" name="USER_CREACION" value=""  > 
                        <input type="hidden" name="COD" value="<%=cod%>"  >               
                        <input type="hidden" name="PUESTO_ID" value="<%=idp%>" >  
                        <input type="hidden" name="IDDETALLE_REQ_PROCESO" value="<%=iddrp%>"  >  
                        <input type="hidden" name="IDPASOS" value="<%=id_pasos%>"   >
                        <tr><td><input type="submit" name="opc"  class="submit" value="Aceptar"/></td></tr>
                    </table>
                </form>
                <form action="../../autorizacion" method="post">
                    <table>



                        <input type="hidden" name="IDDETALLE_DGP"  value="<%=iddgp%>"  >           
                        <input type="hidden" name="ESTADO" value="2" >                     
                        <input type="hidden" name="NROPASO" value="<%=nropaso%>"  >                
                        <input type="hidden" name="USUARIO_IP" >  
                        <input type="hidden" name="USER_CREACION" value=""  > 

                        <input type="hidden" name="COD" value="<%=cod%>"  >               
                        <input type="hidden" name="PUESTO_ID" value="<%=idp%>" >  
                        <input type="hidden" name="IDDETALLE_REQ_PROCESO" value="<%=iddrp%>"  >  
                        <input type="hidden" name="IDPASOS" value="<%=id_pasos%>" 
                               <tr><td><input type="submit" name="opc"  class="submit" value="Rechazar"/></td></tr>
                    </table>
                </form>   
            </center>
            <%}
                }%>
        </div>
        <script src="../../js/JQuery/jQuery.js"></script>
        <script src="../../js/Js_dlmenu/jquery.dlmenu.js"></script>
        <script>
            $(function() {
                $('#dl-menu').dlmenu({
                    animationClasses: {classin: 'dl-animate-in-2', classout: 'dl-animate-out-2'}
                });
            });
        </script>

    </body>
</html>
