<?
session_start();
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Fichas de Trabajador</title>
        
          <link rel="stylesheet" href="../Vistas/sort/style.css" />
            
        
        <link type="text/css" rel="stylesheet" href="../CSS/Reportes.css">
   
        <script type="text/javascript" src="../CSS/Mensajes/Alertas/alertify.js"></script>
        <link rel="stylesheet" href="../CSS/Mensajes/Alertas/alertify.core.css" />
        <link rel="stylesheet" href="../CSS/Mensajes/Alertas/alertify.default.css" />
        <script type="text/javascript"  src="../CSS/Mensajes/Alertas/Alertas.js"></script>
    
    </head>
    <SCRIPT LANGUAGE="JavaScript">
function checkIt(evt) {
    evt = (evt) ? evt : window.event
    var charCode = (evt.which) ? evt.which : evt.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        status = "This field accepts numbers only."
        return false
    }
    status = ""
    return true
}
</SCRIPT>
   
<body style="height: 1024px;">  
   
        <div class="spacing">
            <center><h1 class="spacing">Fichas de Trabajador</h1></center>
        </div>
    <center>
        <div class="container theme-showcase">
            <table  style="width: 80%;" >
                <tr>
                    <!--<td style="width: 800px;  " colspan="2"> Lista de Fichas (<? //echo $s; ?>)</td>-->
                    <td>Elaborar Ficha:</td><td><a href="Reg_Trabajador.php"    class="button blue" >Agregar Nueva Ficha</a></td>    
                <!--<button onclick="Hola();">Hola</button>
                -->
                </tr>
                <tr>
            </table>  
              <div>
                  <form method="post">
                      <table style="width: 80%; " >
                    <td>Nombres: <input type="text"  class="text-box"  name="nom"></td>
                    <td>Apellido Paterno: <input type="text"  class="text-box"  name="ape_pat"></td>
                    <td>Apellidos Materno: <input type="text"  class="text-box"  name="ape_mat"></td>
                    <td>DNI : <input type="text" class="text-box" onKeyPress="return checkIt(event)" name="dni"></td>
                    <td><input class="button blue"  type="submit" name="Buscar"  value="Buscar"></td>
                    <!--<td><input class="button blue"  type="submit" name="all"  value="Todos"></td>
                    -->
                    <td><a class="button blue"href="?"  >Cancelar</a></td>
                </tr>
              </table>
                </form>
                        </div>
            
            <? if (($_REQUEST["Buscar"]=="Buscar"&($_REQUEST["dni"]!=0   | trim($_REQUEST["nom"])!=''| trim($_REQUEST["ape_mat"])!='' | trim($_REQUEST["ape_pat"])!=''   )  ) | $_REQUEST["all"]=="Todos") {
                
              $dni=  $_REQUEST["dni"];
              $nom=$_REQUEST["nom"];
              $ape_p=$_REQUEST["ape_pat"];
              $ape_m=$_REQUEST["ape_mat"];
                
require '../Modelo/modelotrabajador.php';

$mtr = new modelotrabajador();
$listra = $mtr->ListarTrabajador($_SESSION["DEPARTAMENTO_ID"],$dni,$nom,$ape_p,$ape_m);
$s = count($listra);
?>
            <?  if ($s==0) {?>
            <h1>No se encontraron registros...</h1>
            <?}else{?>
            <table class="tinytable"   >
                <tr class="tab_cabe">
                    <td>Nro</td>
                    <td>Foto</td>
                    <td>Nombre</td>
                    <td>Carrera</td>
                    <td>Acciones</td>
                </tr>
                    <? for ($index = 0; $index < count($listra); $index++) { ?>
                    <tr>
                        <td><?echo $index+1; ?></td>         
                <?
            require_once '../Modelo/Modelo_Imagen.php';
            $mod_f= new Modelo_Imagen();
                    $idf=$mod_f->LIST_FOTO_TRABAJADOR($listra[$index][0]);
                          ?>
              
              <? if ($idf==null) {?>
            <td><img src="../Imagenes/avatar_default.jpg"  width="80"  height="80"></td>
              <?}else{?>
              <td><img src="Foto.php?idf=<?echo $idf;?>"  width="80"  height="80"></td>
              <?}?>
                        
                        <td><div ><a href="DetalleTrabajador.php?idtr=<? echo $listra[$index][0]; ?>"><? echo strtoupper($listra[$index]["1"] . ' ' . $listra[$index]["2"]. ' ' . $listra[$index]["3"]); ?></a></div></td>
                        <td> <? 
                          require_once '../Modelo/ModeloLista.php';
                          $md_l=new ModeloLista();
                          $lis_car=$md_l->LISTA_CARRERA();
                          for ($l = 0; $l < count($lis_car); $l++) {
                              if ($listra[$index][20]==$lis_car[$l][0]) {
                                  echo $lis_car[$l][1];
                              }
                          }
                         ?></td>
                        <td>
                        <?
                     require_once '../Modelo/ModeloDGP.php';
                     $mddgp= new ModeloDGP();
                     $num=$mddgp->VAL_TRA_DGP($listra[$index][0]);
                     
                     $n_v=$mddgp->VAL_OPC_DGP($listra[$index][0]);
                  //  echo $n_v;
                    if ($n_v>0) {?>
                       <a href="List_dgp_trabajador.php?idtr=<? echo $listra[$index][0];?>">Ver DGP's</a>
                    <?}else{?> 
                        <?if ($listra[$index][62]>0) {?>
                        <? if ($num[0][0]!=0) {?>
                         <a href="List_dgp_trabajador.php?idtr=<? echo $listra[$index][0];?>">Ver DGP's</a>
                            <?}?>
                        <a href="Info_Contractual.php?idtr=<? echo $listra[$index][0];?>">Ver Contratos</a>
                     <?}else{?>
                     <a href="Reg_dgp.php?idtr=<?echo $listra[$index][0];?>">Solicitar Contrataci�n</a>        
                     <?  if ($_SESSION["IDROL"]==6) {?>
                     <a href="Reg_Contrato.php?idtr=<?echo $listra[$index][0];?>">ELaborar Contrato</a>                       
                     <?}?>
                         <?}}?>
                        </td>
                 
                    </tr>
<? } ?>
            </table>
            <?}}?>
        </div>
    </center>
</body>

</html>