<?
session_start();
if (isset($_SESSION['IDUSER'])) {
    $iddep = $_SESSION["IDDEPARTAMENTO"];
    ?>
    <!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
            <title>Contratos</title>  
            <link rel="stylesheet" href="../Vistas/sort/style.css" />
            <script type="text/javascript" src="../JS/jquery-1.7.2.min.js"></script>
            <script type="text/javascript" src="../JS/jquery-ui-1.8.16.custom.min.js"></script>
            <script type="text/javascript" src="../JS/js_contratacion.js"></script>
            <link type="text/css" rel="stylesheet" href="../CSS/Reportes.css">
            <link type="text/css" rel="stylesheet" href="../CSS/form.css">
        </head>
        <body class="body" style="height: 1024px;">  
            <div class="spacing">
                <center><h1 class="spacing">Contratos</h1></center>
            </div> 
        <center>
            <div id="content">
                <div class="filtro">
                    <form id="frm_filtro" method="post" action="">
                        <table id="tab"   >
                            <tr>
                                <td class="td-form">Fecha de Contrataci�n  del: </td>
                                <td><input type="date" name="del"  size="15" class="text-box" /></td>
                                <td class="td-form">al</td> 
                                <td><input type="date" name="al" size="15" class="text-box" /></td>
                                <!--<input type="hidden" name="iddep" value="<? //echo $_SESSION["DEPARTAMENTO_ID"];  ?>">
                                -->
                            </tr>
                            <tr>
                                <td class="td-form">Nombres y Apellidos:</td><td><input type="text" name="nom_ape"  class="text-box"  size="25" /></td>
                            </tr>
                            <tr>
                                <td class="td-form">Area:</td><td><select name="area" class="text-box">
                                        <option value="0"></option>
                                        <?
                                        require '../Modelo/ModeloRH_Area.php';
                                        $md_a = new ModelorRH_Area();
                                        $list_a = $md_a->LISTA_RH_AREA_ID($_SESSION["DEPARTAMENTO_ID"]);
                                        for ($u = 0; $u < count($list_a); $u++) {
                                            ?>
                                            <option value="<? echo $list_a[$u][0]; ?>"><? echo $list_a[$u][1]; ?></option>
                                        <? } ?>
                                    </select></td>    
                                <td class="td-form">Puesto:</td><td><input type="text" name="puesto" size="25"  class="text-box" /></td>
                            </tr>
                            <tr>
                                <td class="td-form">Sueldo:</td><td><input type="text" name="sueldo" size="25"  class="text-box" /></td>
                                <td class="td-form">Fecha Inicio:</td><td><input type="date" name="fec_i" size="25" class="text-box" /></td>
                            </tr>
                            <tr>
                                <td class="td-form">Fecha Fin:</td><td><input type="date" name="fec_f" size="25"  class="text-box"/></td>

                                <td class="td-form">Secci�n:</td>
                                <td><input type="text" name="seccion" class="text-box"></td>

                                <td><button type="button" id="btnfiltrar" class="button blue">Buscar</button></td>
                                <td><a href="javascript:;" id="btncancel"  class="button blue">Cancelar</a></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <br>
                <br>
                <div class="container">
                    <table   id="data" class="tinytable" >

                        <thead class="tab_cabe">
                            <tr class="tr">
                                <td style="width: 100%;" ><span title="NOMBRE_P">Nombres y Apellidos</span></td>
                                <td><span title="FEC_DESDE">Fecha Desde</span></td>
                                <td><span title="FEC_HASTA">Fecha Hasta</span></td>
                                <td><span title="AREA">Area</span></td>
                                <td><span title="SECCION">Secci�n</span></td>
                                <td ><span title="PUESTO">Puesto</span></td>
                                <td><span title="SUELDO">Sueldo</span></td>
                                <td><span title="FECHA_CREACION">Fecha de Contrataci�n</span></td>
                                <td><span title="">Contrato</span></td>
                            </tr>
                        </thead>

                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </center>

    </body>


    </html>
    <?php
} else {
    echo"
		<script type='text/javascript'>
		window.location= 'http://localhost/SysDGP/';
		</script >
	";
}