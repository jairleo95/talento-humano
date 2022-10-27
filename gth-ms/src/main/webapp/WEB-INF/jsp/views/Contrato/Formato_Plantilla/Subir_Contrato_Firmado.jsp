
<%@page import="com.app.persistence.dao.DocumentoDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceDocumentoDAO"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>

        <link rel="stylesheet" type="text/css" href="js/shadowbox/shadowbox.css"/>
        <link rel="stylesheet" type="text/css" href="js/shadowbox/style.css"/>
        <script type="text/javascript" src="js/shadowbox/shadowbox.js"></script>
        <script type="text/javascript">
            Shadowbox.init({
                // a darker overlay looks better on this particular site
                overlayOpacity: 0.8
                        // setupDemos is defined in assets/demo.js
            }, setupDemos);</script>
        <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/Css_Bootstrap/fileinput.css" media="all" rel="stylesheet" type="text/css" />
    <%  //String num = request.getParameter("num");
        // int num_doc = Integer.parseInt(num);
        InterfaceDocumentoDAO doc_ = new DocumentoDAO();
        String id_con = "";
    %>

        <%          //  HttpSession sesion_1 = request.getSession(true);
            //String rol = (String) sesion_1.getAttribute("IDROL");
            //int n_nac = Integer.parseInt(request.getParameter("n_nac"));
            // int num_ad = Integer.parseInt(request.getParameter("num_ad"));
            // String id_ctr = "";
            //String id_dgp = "";
        %>
        <!-- possible classes: minified, fixed-ribbon, fixed-header, fixed-width-->
        <!-- MAIN PANEL -->
        <div id="main" role="main" style="margin-left:0px">



            <!-- MAIN CONTENT -->
            <div id="content">

                <!-- widget grid -->
                <section id="widget-grid" class="">

                    <!-- row -->
                    <div class="row">

                        <!-- NEW WIDGET START -->
                        <article class="col-sm-12">



                            <!-- Widget ID (each widget will need unique ID)-->
                            <div class="jarviswidget jarviswidget-color-blueLight" id="wid-id-0" data-widget-editbutton="false">
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
                                    <span class="widget-icon"> <i class="fa fa-cloud"></i> </span>
                                    <h2>Subir Contrato  Firmado </h2>

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

                                        <form action=../doc_adjunto" method="post" enctype="multipart/form-data" class="dropzone" class="smart-form" >
                                            <table id="datatable_tabletools" class="table table-bordered table-hover table-responsive" width="100%">
                                                <tr align="center"> <td style="border:1px solid #CC3C3C;">
                                                        SUBIR CONTRATO FIRMADO
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <%int can = Integer.parseInt(request.getParameter("coun_doc"));
                                                        if (can > 0) {
                                                    %>
                                                    <td class="caji3">
                                                        <% id_con = request.getParameter("idc");
                                                             out.print(doc_.List_file_url3(id_con));
                                                              %>      

                                                    </td>
                                               
                                                    <%} else {%>
                                                    <td class="caji3">
                                                        <input type="hidden" class="idc" name="idc" value="<%=request.getParameter("idc")%>" style="">

                                                        <div class="form-group" >
                                                            <input id="file-5" class="file" type="file" data-preview-file-type="any" data-upload-url="#" name="archivo" multiple="">
                                                        </div>   
                                                    </td>
                                                    <%}%>
                                                </tr>
                                            </table>
                                            <button type="submit" class="btn btn-primary" style="align:center;"><i class="fa fa-upload"></i>Registrar</button>
                                            <button type="button" class="btn btn-primary" onclick="window.history.back()"><i class="fa fa-backward"></i>&nbsp;Regresar</button>
                                            <%
                                                if (can > 0) {
                                            %>
                                            <a class="btn btn-primary" href="doc_adjunto?opc=Eliminar&idc=<%=id_con%>"><I class="fa fa-tach"></I>Eliminar</a>
                                                    <%} else {%>
                                                    <%}%>
                                        </form>

                                    </div>

                                </div>
                                <!-- end widget div -->

                            </div>
                            <!-- end widget -->

                        </article>
                        <!-- WIDGET END -->

                    </div>

                    <!-- end row -->

                    <!-- row -->

                    <!-- end row -->

                </section>
                <!-- end widget grid -->

            </div>
            <!-- END MAIN CONTENT -->

        </div>
        <!-- END MAIN PANEL -->



        <!-- SHORTCUT AREA : With large tiles (activated via clicking user name tag)
        Note: These tiles are completely responsive,
        you can add as many as you like
        -->

        <!-- END SHORTCUT AREA -->

        <!--================================================== -->

        <!-- PACE LOADER - turn this on if you want ajax loading to show (caution: uses lots of memory on iDevices)-->
        <script data-pace-options='{ "restartOnRequestAfter": true }' src="js/plugin/pace/pace.min.js"></script>

        <!-- Link to Google CDN's jQuery + jQueryUI; fall back to local -->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script>
            if (!window.jQuery) {
                document.write('<script src="js/libs/jquery-2.0.2.min.js"><\/script>');
            }
        </script>

        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
        <script>
            if (!window.jQuery.ui) {
                document.write('<script src="js/libs/jquery-ui-1.10.3.min.js"><\/script>');
            }
        </script>

        <script src="js/bootstrap/fileinput.js" type="text/javascript"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="js/notification/SmartNotification.min.js"></script>
        <script type="text/javascript">
            function closedthis() {
                $.smallBox({
                    title: "�Documento registrada correctamente!",
                    content: "ya puede visualizar el documento",
                    color: "#739E73",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 6000
                });
            }
            function closedthis2() {
                $.smallBox({
                    title: "�Documento eliminado correctamente!",
                    content: "ya puede ver el cambio",
                    color: "#739E73",
                    iconSmall: "fa fa-check fa-2x fadeInRight animated",
                    timeout: 6000
                });
            }

            $(document).ready(function() {

                pageSetUp();
                $.sound_path = "sound/", $.sound_on = !0, jQuery(document).ready(function() {
                    $("body").append("<div id='divSmallBoxes'></div>"), $("body").append("<div id='divMiniIcons'></div><div id='divbigBoxes'></div>")
                });


                $.each($(".file"), function(i) {

                    if ((i + 1) == 0) {
                        $(".btn_reg_doc").hide();
                        alert();
                    } else {
                        $(".btn_reg_doc").show();
                    }

                });
                $(".DD").change(function() {

                    $(".div-holi").text($(".DD").val());
                });
                $(".elimi").click(function() {
                    var msg = confirm('�Est� seguro de eliminar?');
                    if (msg == true) {
                        return true;
                    } else {
                        return false;
                    }
                });
                $(".btn_eliminar").click(){

                }
            })

        </script>
        <script>

            $("#file-0").fileinput({
                'allowedFileExtensions': ['jpg', 'png', 'gif'],
            });
            $(".file").fileinput({
                // initialPreview: ["<img src='Desert.jpg' class='file-preview-image'>", "<img src='Jellyfish.jpg' class='file-preview-image'>"],
                /*   initialPreviewConfig: [
                 {caption: 'Desert.jpg', width: '120px', url: '#'},
                 {caption: 'Jellyfish.jpg', width: '120px', url: '#'},
                 ],*/
                //uploadUrl: '#',

                showUpload: false,
                layoutTemplates: {
                    main1: "{preview}\n" +
                            "<div class=\'input-group {class}\'>\n" +
                            "   <div class=\'input-group-btn\' >\n" +
                            "       {browse}\n" +
                            "       {upload}\n" +
                            "       {remove}\n" +
                            "   </div>\n" +
                            "   {caption}\n" +
                            "</div>"},
                allowedFileExtensions: ['jpg', 'png', 'gif', 'pdf', 'docx', 'doc'],
                overwriteInitial: false,
                maxFileSize: 500,
                maxFilesNum: 10,
                //allowedFileTypes: ['image', 'video', 'flash'],
                slugCallback: function(filename) {
                    return filename.replace('(', '_').replace(']', '_');
                }
            });
            /*
             $(".file").on('fileselect', function(event, n, l) {
             alert('File Selected. Name: ' + l + ', Num: ' + n);
             });
             */
            $("#file-3").fileinput({
                showUpload: false,
                showCaption: false,
                browseClass: "btn btn-primary btn-lg",
                fileType: "any"
            });
            $("#file-4").fileinput({
                uploadExtraData: [
                    {kvId: '10'}
                ],
            });
            $(".btn-warning").on('click', function() {
                if ($('#file-4').attr('disabled')) {
                    $('#file-4').fileinput('enable');
                } else {
                    $('#file-4').fileinput('disable');
                }
            });
            $(".btn-info").on('click', function() {
                $('#file-4').fileinput('refresh', {previewClass: 'bg-info'});
            });
            /*
             $('#file-4').on('fileselectnone', function() {
             alert('Huh! You selected no files.');
             });
             $('#file-4').on('filebrowse', function() {
             alert('File browse clicked for #file-4');
             });
             */
            $(document).ready(function() {
                $("#test-upload").fileinput({
                    'showPreview': false,
                    'allowedFileExtensions': ['jpg', 'png', 'gif'],
                    'elErrorContainer': '#errorBlock'
                });
                /*
                 $("#test-upload").on('fileloaded', function(event, file, previewId, index) {
                 alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
                 });
                 */
            });</script>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>
