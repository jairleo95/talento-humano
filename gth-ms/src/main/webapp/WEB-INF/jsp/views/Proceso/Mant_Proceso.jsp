<%@page import="com.app.config.globalProperties"%>
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
    <!-- MAIN PANEL -->
    <div id="main" role="main" style="margin: 0px;">

        <!-- MAIN CONTENT -->
        <div id="content" >
            <!-- widget grid -->
            <section id="widget-grid" class="">
                <!-- row -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="well">
                            <select name="proceso"  id="select-proceso" class="form-control selectProceso">
                                <option value="">[Seleccione]</option>
                            </select>  
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-12 col-md-12 col-lg-12">
                        <div class="well">
                            <h2>Tareas</h2>
                            <div class="row">
                                <form class="smart-form form-paso">
                                    <div class="">
                                        <section class="col col-md-2">
                                            <label class="input">
                                                <input name="num" required=""  maxlength="3" class="num_paso input-sm" style="width: 100%"   placeholder="N�mero">
                                            </label>
                                        </section>
                                        <section class="col col-md-2">
                                            <label class="input">
                                                <input type="text" name="cod" class="co_paso input-sm"  maxlength="4" placeholder="C�digo" />
                                            </label>
                                        </section>

                                        <section class="col col-md-6">
                                            <label class="input">
                                                <input type="text"  name="desc" class="desc_paso input-sm"  placeholder="Descripci�n" maxlength="200"/>
                                            </label>
                                        </section>
                                        <section class="col col-md-2" >
                                            <button class="btn btn-primary btn-registrar btn-circle pull-left" type="submit" id="btn-registrar" name="Enviar" value="Registrar Paso" >
                                                <i class="fa fa-plus"></i></button>
                                            <button class="btn btn-danger btn_cancel_edit btn-circle pull-right" type="button" style="display:none "  ><i class="fa fa-remove"></i></button>
                                            <button type="button" class="btn btn-default btn-circle Generar pull-right" data-action="collapse-all">
                                                <i class="fa fa-refresh"></i>
                                            </button>
                                        </section>
                                    </div>
                                    <input type="hidden" name="id" class="id_p" value=""/>
                                    <input type="hidden" name="opc" class="opc" value="Registrar"/>
                                </form>
                            </div>
                            <div class="row">   
                                <div class="col col-xs-12 col-md-6">
                                    <div class="dd" id="nestable"  >
                                        <ol class="dd-list" >
                                        </ol>
                                    </div>
                                    <div class="TaskValues" ></div>
                                </div>
                                <div class="col col-xs-12 col-md-6">
                                    <form action="" method="post" class="smart-form form_puesto" novalidate="">
                                            <div class="row">
                                                <section class="col col-4">
                                                    <label>Direcci�n</label>
                                                    <label class="select">
                                                        <select class=" input-sm sl_dir" required="" ></select> 
                                                        <i></i></label>      
                                                </section>
                                                <section class="col col-4">
                                                    <label>Departamento:</label>
                                                    <label class="select">
                                                        <select class="input-sm sl_dep" required=""></select> 
                                                        <i></i></label>                                                    
                                                </section>
                                                <section class="col col-4">
                                                    <label>Area:</label>
                                                    <label class="select">
                                                        <select class="input-sm sl_area" required=""></select>  
                                                        <i></i></label>
                                                </section>
                                            </div>
                                            <div class="row">
                                                <section class="col col-4">
                                                    <label>Secci�n:</label>
                                                    <label class="select">
                                                        <select class="input-sm sl_sec" required=""></select> 
                                                        <i></i></label>
                                                </section>

                                                <section class="col col-4">
                                                    <label>Puesto:</label>
                                                    <label class="select">
                                                        <select name="id_pu" class="input-sm sl_puesto" required=""></select>
                                                        <i></i></label>

                                                </section>                                          
                                                <input type="hidden" value="" name="idpasos" class="id_pasos"  />
                                                <input type="hidden" value="" name="nun" class="num_p"  />
                                                <section class="col col-2" >
                                                    <label>Tipo</label>
                                                    <label class="select">
                                                        <select class="input-sm co_puesto" name="co_pasos">
                                                            <option value=""></option>
                                                            <option value="SECR">Secretaria de Area</option>
                                                            <option value="JEFE">Jefe de Area</option>
                                                        </select> 
                                                        <i></i></label>
                                                </section>
                                                <section class="col col-2">
                                                    <div style="margin-top: 5px;" >
                                                        <button type="button" class="btn btn-primary btn-agregar-p btn-circle btn-lg pull-right" style="margin: auto;">
                                                            <i class="fa fa-plus"></i> 
                                                        </button> 
                                                    </div>                                                            
                                                </section>
                                            </div>
                                    </form>
                                    <div class="custom-scroll table-responsive" style="height:320px; overflow-y: scroll;">
                                        <table class="table table-striped table-bordered table-hover" style="width: 98%;margin: auto; ">
                                            <thead>
                                                <tr>
                                                    <th data-class="expand">Puesto</th>
                                                    <th data-hide="phone,tablet">Area</th>
                                                    <th data-hide="phone,tablet">Departamento</th>
                                                    <th data-hide="phone,tablet">Direcci�n</th>
                                                    <th colspan="2">Acciones</th>
                                                </tr>
                                            </thead>
                                            <tbody class="tbody-puesto">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>


                        </div>
                    </div>



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
                                <h2>Requerimiento - Proceso</h2>				
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

                                    <form  class="smart-form formReqProceso" novalidate="novalidate">
                                        <fieldset>
                                            <div class="row">
                                                <section class="col col-2">
                                                    <select name="ti_planilla" class="form-control input-sm ti_planilla" required="" >
                                                        <option value="" >[ Tipo de Planilla]</option>
                                                    </select>
                                                </section>
                                                <section class="col col-2">
                                                    <select name="requerimiento" class="form-control input-sm req" required="" >
                                                        <option value="" >[Requerimiento]</option>
                                                    </select>

                                                </section>
                                                <section class="col col-3">
                                                    <select name="direccion" class="form-control  input-sm direccion" required="" >
                                                        <option value="" >[Direcci�n]</option>
                                                    </select>
                                                </section>
                                                <section class="col col-2">
                                                    <select name="departamento" class="form-control input-sm departamento"  >
                                                        <option value="" >[Departamento]</option>
                                                    </select>
                                                </section>
                                                <section class="col col-2">
                                                    <select name="area" class="form-control input-sm area"  >
                                                        <option value="" >[Area]</option>
                                                    </select>
                                                </section>
                                                <section class="col col-1">
                                                    <button type="button" class="btn btn-danger btn-circle btn-cancelar">
                                                        <i class="fa fa-remove" ></i>
                                                    </button>
                                                    <button type="submit" class="btn btn-primary btn-circle">
                                                        <i class="fa fa-plus" ></i>
                                                    </button>
                                                </section>
                                            </div>


                                        </fieldset>

                                    </form>
                                    <table 
                                        class="table table-striped table-bordered table-hover dtReqProceso"
                                        width="100%">
                                        <thead>
                                            <tr>
                                                <th data-hide="phone">Nro</th>
                                                <th>Tipo de Planilla</th>
                                                <th>Requerimiento</th>
                                                <th data-class="expand">Proceso</th>
                                                <th data-hide="phone">Direcci�n</th>
                                                <th data-hide="phone">Departamento</th>
                                                <th data-hide="phone">Area</th>
                                                <th>Estado</th>
                                                <th data-hide="phone,tablet" style="width: 5%">Acciones</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                                <!-- end widget content -->

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
        <!-- END MAIN CONTENT -->

    </div>
    <!-- END MAIN PANEL -->


    <!-- JQUERY VALIDATE -->
    <script src="js/plugin/jquery-validate/jquery.validate.min.js"></script>

    <!-- JQUERY MASKED INPUT -->
    <script src="js/plugin/masked-input/jquery.maskedinput.min.js"></script>

    <!-- JQUERY SELECT2 INPUT -->
    <script src="js/plugin/select2/select2.min.js"></script>

    <!-- JQUERY UI + Bootstrap Slider -->
    <script src="js/plugin/bootstrap-slider/bootstrap-slider.min.js"></script>

    <!-- browser msie issue fix -->
    <script src="js/plugin/msie-fix/jquery.mb.browser.min.js"></script>

    <!-- FastClick: For mobile devices -->
    <script src="js/plugin/fastclick/fastclick.min.js"></script>

    <!-- PAGE RELATED PLUGIN(S) -->
    <script src="js/businessLogic/Js_Formulario/Js_Form.js" type="text/javascript"></script>
    <script src="js/plugin/jquery-nestable/jquery.nestable.min.js"></script>
    <script src="js/plugin/datatables/jquery.dataTables.min.js"></script>
    <script src="js/plugin/datatables/dataTables.colVis.min.js"></script>
    <script src="js/plugin/datatables/dataTables.tableTools.min.js"></script>
    <script src="js/plugin/datatables/dataTables.bootstrap.min.js"></script>
    <script src="js/plugin/datatable-responsive/datatables.responsive.min.js"></script>

    <script src="js/businessLogic/Proceso/Proceso.js" type="text/javascript"></script>
    <script src="js/businessLogic/Proceso/ReqProceso.js" type="text/javascript"></script>
    <script src="js/businessLogic/Proceso/Task.js" type="text/javascript"></script>
    <script src="js/businessLogic/Proceso/TaskXJob.js" type="text/javascript"></script>
    <script src="js/businessLogic/Proceso/ConfigureProcess.js" type="text/javascript"></script>

</html>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>  