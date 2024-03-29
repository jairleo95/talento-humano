<%@page import="com.app.config.globalProperties"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="com.app.domain.model.Tipo_Contrato"%>
<%@page import="com.app.domain.model.Funciones"%>
<%@page import="com.app.domain.model.Grupo_Ocupaciones"%>
<%@page import="com.app.domain.model.Sub_Modalidad"%>
<%@page import="com.app.domain.model.Modalidad"%>
<%@page import="com.app.domain.model.Regimen_Laboral"%>
<%@page import="com.app.domain.model.Centro_Costos"%>
<%@page import="com.app.persistence.dao.ListaDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceListaDAO"%>
<%@page import="com.app.persistence.dao.ContratoDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceContratoDAO"%>
<%@page import="com.app.domain.model.Usuario"%>
<%@page import="com.app.domain.model.X_List_Plantilla"%>
<%@page import="com.app.persistence.dao.PlantillaDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfacePlantillaDAO"%>
<%@page import="com.app.domain.model.X_List_Id_Contrato_DGP"%>

<jsp:useBean id="List_contra_x_idcto" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Situacion_Actual" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Planilla" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="list_Condicion_contrato" scope="session" class="java.util.HashMap"/>
<jsp:useBean id="List_tipo_contrato" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="list_reg_labo" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Usuario" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="Lis_c_c_id_contr" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_Anno_trabajador" scope="session" class="java.util.ArrayList"/>
<jsp:useBean id="List_x_fun_x_idpu" scope="session" class="java.util.ArrayList"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Informaci�n Contractual</title>
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <!-- Basic Styles -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/font-awesome.min.css">

        <!-- SmartAdmin Styles : Please note (smartadmin-production.css) was created using LESS variables -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production-plugins.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-production.min.css">
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-skins.min.css">

        <!-- SmartAdmin RTL Support is under construction
                 This RTL CSS will be released in version 1.5
        <link rel="stylesheet" type="text/css" media="screen" href="css/smartadmin-rtl.min.css"> -->

        <!-- We recommend you use "your_style.css" to override SmartAdmin
             specific styles this will also ensure you retrain your customization with each SmartAdmin update.
        <link rel="stylesheet" type="text/css" media="screen" href="css/your_style.css"> -->

        <!-- Demo purpose only: goes with demo.js, you can delete this css when designing your own WebApp -->
        <link rel="stylesheet" type="text/css" media="screen" href="css/demo.min.css">

        <!-- FAVICONS -->
        <link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
        <link rel="icon" href="img/favicon/favicon.ico" type="image/x-icon">

        <!-- GOOGLE FONT -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,300,400,700">

        <!-- Specifying a Webpage Icon for Web Clip 
                 Ref: https://developer.apple.com/library/ios/documentation/AppleApplications/Reference/SafariWebContent/ConfiguringWebApplications/ConfiguringWebApplications.html -->
        <link rel="apple-touch-icon" href="img/splash/sptouch-icon-iphone.png">
        <link rel="apple-touch-icon" sizes="76x76" href="img/splash/touch-icon-ipad.png">
        <link rel="apple-touch-icon" sizes="120x120" href="img/splash/touch-icon-iphone-retina.png">
        <link rel="apple-touch-icon" sizes="152x152" href="img/splash/touch-icon-ipad-retina.png">

        <!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">

        <!-- Startup image for web apps -->
        <link rel="apple-touch-startup-image" href="img/splash/ipad-landscape.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:landscape)">
        <link rel="apple-touch-startup-image" href="img/splash/ipad-portrait.png" media="screen and (min-device-width: 481px) and (max-device-width: 1024px) and (orientation:portrait)">
        <link rel="apple-touch-startup-image" href="img/splash/iphone.png" media="screen and (max-device-width: 320px)">
    </head>
    <body class="slideInRight fast animated">
        <%
            String ID_CTO = request.getParameter("id_cto");
            if (List_contra_x_idcto.size() == 0) {%>
        <h3>Aun no se ha hecho Contrato.</h3>
        <%
        } else {%>

        <div>
            <%
                HttpSession Sesion = request.getSession(true);
                String idrol = (String) Sesion.getAttribute("IDROL");
                InterfaceContratoDAO oContrato = new ContratoDAO();
            %>
            <%for (int b = 0; b < List_contra_x_idcto.size(); b++) {
                    X_List_Id_Contrato_DGP n = new X_List_Id_Contrato_DGP();
                    n = (X_List_Id_Contrato_DGP) List_contra_x_idcto.get(b);
            %>
            <input type="hidden" name="id_con" class="id_contrato" value="<%=n.getId_contrato()%>"> 
            <input type="hidden"  class="idc" value="<%=n.getId_contrato()%>">

            <%if (idrol.trim().equals("ROL-0006") || idrol.trim().equals("ROL-0001")) {
            %>
            <div class="well">
                <div class="row">
                    <div class="col col-md-4">
                        <div class="col col-md-4"> <label class="txt-color-darken">Contratos:</label></div>
                        <div class="col col-md-8"> <div class="SelectorListaContrato">
                            </div></div>
                        <input type="hidden" name="idtr" class="idtr"  value="<%=request.getParameter("idtr")%>">
                    </div>
                    <div class="col col-md-4">
                        <div class="col col-md-8"><div class="smart-form txt-color-darken">�Decidir la secretaria de departamento o area lo suba e imprima el contrato?</div></div>
                        <div class="col col-md-4 smart-form"><label class='toggle'><input type='checkbox' value='1'  class='ck_habilitar_is'  name='estado' name='checkbox-toggle' <%
                            if (Integer.parseInt(n.getEs_secre_is()) == 1) {
                                out.print("checked");
                            } %> ><i data-swchon-text='SI' data-swchoff-text='NO'></i></label></div>
                        <%}%> 
                    </div>

                    <div class="col col-md-4">

                        <div class="pull-right">
                            <%if (Integer.parseInt(n.getEs_secre_is()) == 2 && idrol.trim().equals("ROL-0002")) {%>

                            <%} else if (!idrol.trim().equals("ROL-0009")) {%>
                            <a class="btn btn-labeled btn-primary" href=plantilla_contractual?opc=Imprimir&id_plan_contr=<%=n.getId_plantilla_contractual()%>&idtraba=<%=n.getId_trabajador()%>&id_con=<%=n.getId_contrato()%>&puesto=<%=n.getId_puesto()%>"><span class="btn-label"><i class="fa fa-print"></i></span>Imprimir</a>

                            <%}%>

                            <%if (idrol.trim().equals("ROL-0006") || idrol.trim().equals("ROL-0002") || idrol.trim().equals("ROL-0001")) {
                            %> 

                            <%if (Integer.parseInt(n.getEs_secre_is()) == 2 && idrol.trim().equals("ROL-0002")) {
                            %>
                            <%} else {%>
                            <a class="btn btn-labeled btn-primary " href=contrato?opc=Subir_Contrato2&idc=<%=n.getId_contrato()%>" > <span class="btn-label"><i class="fa fa-cloud-upload"></i></span>Subir</a>
                                    <%}%>

                            <%}
                                if (/*idrol.trim().equals("ROL-0006") || */idrol.trim().equals("ROL-0007") || /*idrol.trim().equals("ROL-0009") || */ idrol.trim().equals("ROL-0001")) {%>
                            <%if (idrol.trim().equals("ROL-0006")) {
                                    //validar si puede editar contrato
                                    if (oContrato.validar_editar_contrato(id_user, ID_CTO)) {
                            %>
                            <a class="btn btn-labeled btn-primary" href=contrato?opc=Editar&idc=<%=n.getId_contrato()%>&idtr=<%=request.getParameter("idtr")%>&id_dg=<%=n.getId_dgp()%>" > <span class="btn-label"><i class="fa fa-pencil-square-o"></i></span>Editar</a>

                            <%
                                }
                            } else {%>
                            <a class="btn btn-labeled btn-primary" href=contrato?opc=Editar&idc=<%=n.getId_contrato()%>&idtr=<%=request.getParameter("idtr")%>&id_dg=<%=n.getId_dgp()%>" > <span class="btn-label"><i class="fa fa-pencil-square-o"></i></span>Editar</a>
                                    <%}
                                        }%>
                        </div>
                    </div>
                </div>
                <br>
                <p class="alert alert-info"><i class="fa fa-info"></i> �Una vez procesado la informacion usted ya no podra editar este contrato!</p>
            </div>
            <% for (int p = 0; p < List_contra_x_idcto.size(); p++) {%>
            <table class="table table-bordered table-hover table-striped">
                <tr><td class="text-info">Fecha de Inicio: </td><td colspan="2"><%=n.getFe_desde()%></td><td class="text-info" colspan="2">Fecha de Cese:</td><td  colspan="2"><%if (n.getFe_hasta() != null) {
                        out.print(n.getFe_hasta());
                    } else {
                        out.print("No definido");
                    }%></td></tr>
                <tr><td class="text-info">Direcci�n:</td><td colspan="6"  ><p><%=n.getNo_direccion()%> </p></td></tr>
                <tr><td class="text-info">Departamento:</td><td colspan="6"  ><p><%=n.getNo_dep()%> </p></td></tr>
                <tr><td class="text-info">Area:</td><td colspan="6"  ><p><%=n.getNo_area()%> </td></tr>
                <tr><td class="text-info">Secci�n:</td><td colspan="6"  ><p><%=n.getNo_seccion()%> </p></td></tr>
                <tr><td class="text-info">Puesto:</td><td colspan="6"  ><p><%=n.getNo_puesto()%></p><input type="hidden" class="id_pu" value="<%=n.getId_puesto()%>" name="puesto"></td> </tr>
                            <%
                                if (Lis_c_c_id_contr.size() > 0) {
                                    double cantidad = 0.0;
                                    for (int q = 0; q < Lis_c_c_id_contr.size(); q++) {
                                        Centro_Costos cc = new Centro_Costos();
                                        cc = (Centro_Costos) Lis_c_c_id_contr.get(q);

                                        cantidad = Double.parseDouble(cc.getCa_porcentaje()) + cantidad;
                            %>
                <tr>
                    <td class="text-info ">Centro costo N�<%=q + 1%>:</td>
                    <td colspan="2"  ><p><%=cc.getDe_centro_costo() + " - " + cc.getCo_centro_costo()%></p></td>
                    <td class="text-info " colspan="2">Porcentaje</td>
                    <td colspan="2"><p><%=cc.getCa_porcentaje()%> %</p> </td></tr><%}%>
                <tr><td class="text-info ">Total Porcentaje:</td>
                    <td colspan="6"  ><p><%=cantidad%> %</p></td>
                </tr><%} else {%>
                <tr><td class="text-info ">Centro costo </td><td colspan="6"  ><p>No tiene</p></td>
                </tr><%}%>

                <tr><td class="text-info ">Condici�n:</td> <td colspan="6"  ><strong style="color:#b71c1c;"><%
                    if (n.getLi_condicion() != null) {
                        System.out.println("hola "+n.getLi_condicion().trim());
                        out.println(list_Condicion_contrato.get(n.getLi_condicion().trim()));
                    } else {
                        out.print("No tiene");
                    }%> </td></tr>
                <tr><td class="text-info ">Sueldo:</td><td>S/.<%=n.getCa_sueldo()%></td><td class="text-info " colspan="1">Reintegro:</td><td colspan="1">S/.<%=n.getCa_reintegro()%></td><td class="text-info " colspan="2">Bono Alimentario:</td><td  >S/.<%=n.getCa_bono_alimento()%> </td></tr>
                <tr><td class="text-info ">Bev:</td><td   colspan="6">S/.<%if (n.getCa_bev() != null) {
                        out.print(n.getCa_bev());
                    } else {
                        out.print("0");
                    }%> </td></tr>
                <tr><td class="text-info ">Bonificacion Puesto:</td><td   colspan="6">S/.<%if (n.getCa_bonificacion_p() != null) {
                        out.print(n.getCa_bonificacion_p());
                    } else {
                        out.print("--");
                    }%> </td></tr>
                <tr><td class="text-info ">Asignaci�n Familiar:</td><td  colspan="6"><%= "S/." + n.getCa_asig_familiar()%> </td></tr>

                <tr><td class="text-info ">Sueldo Total:</td><td  colspan="6">S/.<%if (n.getCa_sueldo_total() != null) {
                        out.print(n.getCa_sueldo_total());
                    } else {
                        out.print("0");
                    }%> </td></tr>
                <tr>
                    <td class="text-info ">Religion:</td><td  colspan="6"><strong class="text-danger"><%if (n.getLi_religion().equals("1")) {
                            out.print("Adventista");
                        } else if (n.getLi_religion().equals("2")) {
                            out.print("Cat�lico");
                        } else if (n.getLi_religion().equals("3")) {
                            out.print("Otro");
                        } else if (n.getLi_religion() == null) {
                            out.print("Ninguna");
                        }%></td>
                </tr>
                <tr><td class="text-info ">Tipo Pago Horas:</td><td   colspan="6"><%
                    if (n.getTi_hora_pago() != null) {
                        out.print(n.getTi_hora_pago());
                    } else {
                        out.print("--");
                    }

                        %> </td></tr>

                <tr><td class="text-info ">Regimen Laboral Mintra:</td>
                    <%                        if (n.getId_regimen_laboral() != null) {
                            for (int q = 0; q < list_reg_labo.size(); q++) {
                                Regimen_Laboral re = new Regimen_Laboral();
                                re = (Regimen_Laboral) list_reg_labo.get(q);
                                if (n.getId_regimen_laboral().equals(re.getId_regimen_laboral())) {%>
                    <td colspan="6"><%=re.getDe_regimen_l()%> </td>
                    <%}
                        }
                    } else {%>
                    <td   colspan="6">No definido</td> 
                    <%}%>
                </tr>
                <tr><td class="text-info ">Modalidad:</td>

                    <td   colspan="6"><%
                        if (n.getDe_modalidad() != null) {
                            out.print(n.getDe_modalidad());
                        } else {
                            out.print("No registrado");
                        }
                        %> </td>

                </tr>
                <tr><td class="text-info ">Sub_Modalidad:</td>

                    <td   colspan="6"><%
                        if (n.getDe_sub_modalidad() != null) {
                            out.print(n.getDe_sub_modalidad());
                        } else {
                            out.print("No registrado");
                        }
                        %> </td>

                </tr>
                <tr><td class="text-info ">Tipo de Contratacion:</td><td   colspan="6"><%
                    if (n.getEs_ti_contratacion() != null) {
                        if (n.getEs_ti_contratacion().trim().equals("I")) {
                            out.println("INICIO");
                        }
                        if (n.getEs_ti_contratacion().trim().equals("R")) {
                            out.println("RENOVACION");
                        }
                    } else {
                        out.print("NO DEFINIDO");
                    }%> </td></tr> 
                <tr><td class="text-info ">Codigo de Grupo de Ocupaciones:</td>

                    <td   colspan="6"><%
                        if (n.getDe_grupo_ocupacion() != null) {
                            out.print(n.getDe_grupo_ocupacion());
                        } else {
                            out.print("No registrado");
                        }
                        %> </td>

                </tr>
                <tr><td class="text-info "> Fecha de Suscripcion:</td><td   colspan="6"><%
                    if (n.getFe_suscripcion() != null) {
                        out.print(n.getFe_suscripcion());
                    } else {
                        out.print("No Registrado");
                    }%> </td></tr>
                <tr><td class="text-info ">Tipo moneda de pago:</td><td   colspan="6"><%
                    if (n.getCo_ti_moneda() != null) {
                        if (n.getCo_ti_moneda().trim().equals("01")) {
                            out.println("Soles");
                        }
                        if (n.getCo_ti_moneda().trim().equals("02")) {
                            out.println("Dolares");
                        }
                        if (n.getCo_ti_moneda().trim().equals("03")) {
                            out.println("Euros");
                        }
                    } else {
                        out.print("No registrado");
                    }%> </td></tr> 
                <tr><td class="text-info"> Situacion Especial:</td><td colspan="6"><%=n.getDeSituacionEspecial()%></td></tr>
                <tr><td class="text-info"> Tipo de Remuneraci�n Variable:</td><td colspan="6">
                        <%
                            if (n.getCo_ti_rem_variab() != null) {
                                if (n.getCo_ti_rem_variab().trim().equals("1")) {
                                    out.print("Destajo");
                                }
                                if (n.getCo_ti_rem_variab().trim().equals("2")) {
                                    out.print("Comisiones");
                                }
                                if (n.getCo_ti_rem_variab().trim().equals("3")) {
                                    out.print("Ninguno");
                                }

                            } else {
                                out.print("No registrado");
                            }
                        %> </td></tr>

                <tr><td class="text-info"><label>Horario</label> <a   rel="tooltip" data-placement="top" data-original-title="Ver Horario" class="btnHorario btn btn-primary btn-sm btn-circle pull-right" data-valor="<%=n.getId_dgp()%>"  data-toggle="modal" data-target="#exampleModal"><i class="fa fa-info"></i></a></td><td class="text-info">Semanal:</td><td><%=n.getHo_semana()%> 

                    </td>
                    <td class="text-info">Mensual:</td><td><%=n.getNu_horas_lab() + " h"%></td><td class="text-info">Dias:</td><td><%=n.getDia_contrato() + " d"%></td></tr>
                <tr><td class="text-info">Tipo de Trabajador:</td><td   colspan="6"><%
                    if (n.getTi_trabajador() != null) {
                        if (n.getTi_trabajador().trim().equals("1")) {
                            out.println("Empleado");
                        }
                        if (n.getTi_trabajador().trim().equals("2")) {
                            out.println("Obrero");
                        }
                    } else {
                        out.print("No registrado");
                    }%> </td></tr>   
                <tr><td class="text-info">R�gimen Laboral:</td><td   colspan="6">
                        <%if (n.getLi_regimen_laboral() != null) {
                                if (n.getLi_regimen_laboral().trim().equals("1")) {
                                    out.println("Privado");
                                }
                                if (n.getLi_regimen_laboral().trim().equals("2")) {
                                    out.println("Publico");
                                }
                            } else {
                                out.print("No registrado");
                            }%> </td></tr>   
                <tr><td class="text-info">Discapacidad:</td><td  colspan="6"><%
                    if (n.getEs_discapacidad() != null) {
                        if (n.getEs_discapacidad().equals("1")) {
                            out.println("No");
                        }
                        if (n.getEs_discapacidad().equals("2")) {
                            out.println("Si");
                        }
                    } else {
                        out.println("No Ingresado ");
                    }%> 
                    </td></tr>   
                <tr><td class="text-info">Tipo de Contrato:</td><td  colspan="6">
                        <%if (n.getTi_contrato() != null) {
                                for (int k = 0; k < List_tipo_contrato.size(); k++) {
                                    Tipo_Contrato tc = new Tipo_Contrato();
                                    tc = (Tipo_Contrato) List_tipo_contrato.get(k);
                                    if (n.getTi_contrato().trim().equals(tc.getId_tipo_contrato())) {
                                        out.print(tc.getDe_ti_contrato());
                                    }
                                }
                            } else {
                                out.print("No definido");
                            }%> 
                    </td></tr>   
                <tr><td class="text-info">Tipo de Convenio:</td><td  colspan="6"><%
                    if (n.getLi_tipo_convenio() != null) {
                        if (n.getLi_tipo_convenio().trim().equals("1")) {
                            out.println("CLJ");
                        }
                        if (n.getLi_tipo_convenio().trim().equals("2")) {
                            out.println("PPP");
                        }
                        if (n.getLi_tipo_convenio().trim().equals("3")) {
                            out.println("PP");
                        }
                    } else {
                        out.println("NO DEFINIDO");

                    }
                        %> </td></tr>   
                <tr><td class="text-info">�Firmo contrato?:</td><td  colspan="6"><%
                    if (idrol.trim().equals("ROL-0006") & n.getEs_firmo_contrato() == null) {%>
                        <a href=contrato?fc=s&idc=<%=n.getId_trabajador()%>"  class="boton">SI</a>o<a href="" class="boton">NO</a>
                        <%} else if (n.getEs_firmo_contrato() != null) {
                                if (n.getEs_firmo_contrato().equals("1")) {
                                    out.println("Si");
                                }
                                if (n.getEs_firmo_contrato().equals("2")) {
                                    out.println("No");
                                }
                                if (n.getEs_firmo_contrato().equals("0")) {
                                    out.println("Por confirmar");
                                }
                            } else {
                                out.println("Por confirmar");
                            }
                        %></td></tr>

                <!--  <tr><td>Nro. de Contrato:</td><td><?/* echo $list_rhc[$index][39];*/?> </td></tr>   -->
                <tr><td class="text-info">Observaciones:</td><td  colspan="6"><%
                    if (n.getDe_observacion() != null) {
                        out.print(n.getDe_observacion());
                    } else {
                        out.print("No registrado");
                    }%> </td></tr>   
                <tr><td class="text-info">R�gimen Pensionario:</td><td  colspan="6"><%
                    if (n.getLi_regimen_pensionario() != null) {
                        if (n.getLi_regimen_pensionario().trim().equals("1")) {
                            out.println("Privado");
                        }
                        if (n.getLi_regimen_pensionario().trim().equals("2")) {
                            out.println("SNP");
                        }
                    } else {
                        out.println("No registrado");
                    }
                        %> </td></tr>   

                <%if (false) {
                %>
                <tr><td class="text-info">Situacion Actual:</td><td  colspan="6"><%
                    if (n.getEs_contrato() != null) {
                        if (n.getEs_contrato().trim().equals("1")) {
                            out.print("Activo");
                        } else {
                            out.print("Termino Contrato");
                        }
                    }
                        %> </td></tr>   
                        <%}%>
                <tr><td class="text-info">Filial donde Trabaja:</td><td  colspan="6"><%
                    if (n.getId_filial().trim().equals(1 + "")) {
                        out.println("Lima");%>
                        <% }
                            if (n.getId_filial().trim().equals(2 + "")) {
                                out.println("Juliaca");%>
                        <%}
                            if (n.getId_filial().trim().equals(3 + "")) {
                                out.println("Tarapoto");%>
                        <%}
                        %> </td></tr>
                        <%if (false) {
                        %>
                <tr><td class="text-info">Fecha de Cese:</td><td class="  " colspan="6">
                        <% if (n.getFe_cese() != null) {
                                out.print(n.getFe_cese());
                            } else {
                                out.print("NO DEFINIDO");
                            }%> </td></tr> 
                        <%}%>
                        <%if (List_x_fun_x_idpu.size() > 0) {%>
                <tr><td class="text-info" colspan="8" style="text-align:center;">Funciones</td></tr>
                <%for (int fu = 0; fu < List_x_fun_x_idpu.size(); fu++) {
                        Funciones fun = new Funciones();
                        fun = (Funciones) List_x_fun_x_idpu.get(fu);
                %>
                <tr><td class="text-info">Funcion N�<%=fu + 1%>:</td><td  colspan="6"><%=fun.getDe_funcion()%></td></tr>   <%}%>
                    <%}%>


                <tr><%if (n.getUs_modif() != null && n.getFe_modif() != null) {%>
                    <td class="text-info ">Modificado por:</td>
                    <td><%for (int f = 0; f < List_Usuario.size(); f++) {
                            Usuario u = new Usuario();
                            u = (Usuario) List_Usuario.get(f);
                            if (n.getUs_modif().trim().equals(u.getId_usuario().trim())) {
                                out.println(u.getNo_usuario());%>
                        <%}%>
                    </td>
                    <%}
                    } else if (n.getUs_creacion() != null && n.getFe_creacion() != null) {%>
                    <td class="text-danger text-info">Creado por:</td>
                    <%if (n.getUs_creacion() != null) {
                            for (int f = 0; f < List_Usuario.size(); f++) {
                                Usuario u = new Usuario();
                                u = (Usuario) List_Usuario.get(f);
                                if (n.getUs_creacion().equals(u.getId_usuario())) {%>
                    <td class="text-info" colspan="6"><%=u.getNo_usuario()%></td>
                    <%}
                        }
                    } else {%>
                    <td>No Ingresado</td>
                    <%}%>
                    <%}%>

                    <%}%>
                </tr>
            </table>

        </div>
        <%}
        %>
        <%}%>
        <!-------------- Modal  ----------->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Horarios</h4>
                        <h6 class="tipoh hidden"></h6>
                    </div>
                    <div class="modal-body" style="height:550px;">
                        <div class="conTablas"></div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script>
        if (!window.jQuery) {
            document.write('<script src="js/libs/jquery-2.1.1.min.js"><\/script>');
        }
    </script>
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
    <script>
        if (!window.jQuery.ui) {
            document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
        }
    </script>
    <!-- IMPORTANT: APP CONFIG -->
    <script src="js/app.config.js"></script>
    <!-- JS TOUCH : include this plugin for mobile drag / drop touch events-->
    <script src="js/plugin/jquery-touch/jquery.ui.touch-punch.min.js"></script>

    <!-- BOOTSTRAP JS -->
    <script src="js/bootstrap/bootstrap.min.js"></script>

    <!-- CUSTOM NOTIFICATION -->
    <script src="js/notification/SmartNotification.min.js"></script>

    <script src="js/businessLogic/Horario/horario.js" type="text/javascript"></script>
    <script src="js/businessLogic/Contrato/detalleInfoContractual.js" type="text/javascript"></script>

    <%} else {
            out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
        }%>
