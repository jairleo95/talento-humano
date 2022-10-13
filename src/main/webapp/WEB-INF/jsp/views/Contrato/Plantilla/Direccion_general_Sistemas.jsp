<%@page import="com.app.domain.model.Usuario"%>
<%
    HttpSession sesion = request.getSession();
    String id_user = (String) sesion.getAttribute("IDUSER");
    if (id_user != null) {
%>
<%@page import="com.app.persistence.dao.ListaDAO"%>
<%@page import="com.app.persistence.dao_imp.InterfaceListaDAO"%>
<%@page import="com.app.domain.model.X_List_dat_tr_plantilla"%>
<jsp:useBean id="LIST_DAT_TR_PLANTILLA" scope="session" class="java.util.ArrayList"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">

<html>
    <!-- <?
     require_once '../Modelo/modelotrabajador.php';
     $mdtr=new modelotrabajador();
     $id=$_REQUEST["idc"];
    // echo $id;
     $list=$mdtr->LIST_DAT_TR_PLANTILLA($id);
     ?>-->
    <head>

        <link rel="stylesheet" type="text/css" href="../CSS/estilo_impresion.css"  media="print">
        <title></title>
        <style  type="text/css" >
            .text{
                font-size: 18px;
                width: 1024px;
                margin-left:auto;
                margin-right:auto;
                text-align: justify;
                padding: 30px;
            }

            .imprimirTexto{
                background-image:url(../img/print.png);
                width: 222px;
                height: 100px;


            }
        </style>
        <script >
            function imprSelec(nombre)
            {
                var ficha = document.getElementById(nombre);
                var ventimp = window.open(' ', 'Impresion');
                ventimp.document.write('<head><title></title><link rel="stylesheet" type="text/css" href="../CSS/estilo_impresion.css"  media="print"/></head>');
                ventimp.document.write(ficha.innerHTML);
                ventimp.document.close();
                ventimp.print( );
                ventimp.close();
            }

        </script>
    </head>
    <body>

        <br>
        <br>
        <br>
        <div id="muestra">

            <%
                for (int i = 0; i < LIST_DAT_TR_PLANTILLA.size(); i++) {
                    X_List_dat_tr_plantilla p = new X_List_dat_tr_plantilla();
                    p = (X_List_dat_tr_plantilla) LIST_DAT_TR_PLANTILLA.get(i);
            %>
            <div class="text">
                <center>  <h2> CONTRATO DE TRABAJO A PLAZO FIJO Y BAJO MODALIDAD</h2></center> 
                <p>Conste por el presente documento el contrato de trabajo a plazo fijo y sujeto a modalidad por 

                    Servicio Espec�fico, conforme lo dispone el Art. 63 del T.U.O del D. Leg. 728, Ley de Productividad y 

                    Competitividad Laboral (D.S. N� 003-97-TR), que celebran de una parte la <strong>UNIVERSIDAD PERUANA 

                        UNI�N</strong>, con RUC N� 20138122256, con domicilio en Villa Uni�n s/n, altura del Km. 19 de la carretera 

                    central, �a�a, Lurigancho-Chosica, a quien se le denominar� <strong>"EMPLEADOR"</strong>, representada por su 

                    Apoderado Mg. Julio Cesar Rengifo Pe�a, con DNI No. 06690086,; y de la otra parte <strong><%=p.getAp_paterno().toUpperCase() + " " + p.getNo_trabajador().toUpperCase() + " " + p.getAp_materno().toUpperCase()%></strong> , con DNI N�(<strong><%=p.getNu_doc()%></strong>), domiciliado(a) en (<strong>
                        <%                InterfaceListaDAO l = new ListaDAO();
                            for (int b = 0; b < l.List_Dom_D1_Id().size(); b++) {
                                if (p.getLi_di_dom_a_d1().trim().equals(b + 1 + "")) {
                                    out.println(l.List_Dom_D1_Id().get(b));
                                }

                            }

                            if (p.getLi_di_dom_a_d3().trim().equals("1")) {
                                out.println(" " + p.getDi_dom_a_d2() + " Numero");
                            }
                            if (p.getLi_di_dom_a_d3().trim().equals("2")) {
                                out.println(" " + p.getDi_dom_a_d2() + " Lote");
                            }
                            if (p.getLi_di_dom_a_d3().trim().equals("3")) {
                                out.println(" " + p.getDi_dom_a_d2() + " S/N");
                            }

                            for (int c = 0; c < l.List_Dom_D5_Id().size(); c++) {
                                if (p.getLi_di_dom_a_d5().trim().equals(c + 1 + "")) {
                                    out.println(" " + p.getDi_dom_a_d4() + " " + l.List_Dom_D5_Id().get(c));
                                }

                            }
                            out.println(" " + p.getDi_dom_a_d6());

                            %></strong>),<%=p.getNo_ub_distrito()%><strong> </strong>, a quien se le denominar� el (la) <strong>"TRABAJADOR?(A)</strong>, en los t�rminos y condiciones 

                    siguientes:</p>

                <p><strong>PRIMERA:</strong> EL EMPLEADOR tiene por actividad principal la educaci�n en todos sus niveles.

                    El (la)TRABAJADOR (A) conoce que la Universidad Peruana Uni�n es parte integrante del sistema 

                    educativo Adventista, normado y regulada bajo pr�cticas, costumbres, principios �ticos morales, por lo 

                    que acepta respetar, observar y cumplir estos principios practicados por esta instituci�n, dentro y fuera 

                    del Campus Universitario.</p>

                <p><strong>SEGUNDA:</strong> El EMPLEADOR requiere de personal temporal en las �reas de la <strong><%=p.getNo_dep()%></strong>, con el objeto de dise�ar detalladamente los requerimientos del sistema acad�mico, 

                    programando nuevos m�dulos, reportes o actualizaciones de m�dulos, dise�ar detalladamente los 

                    requerimientos de cada proyecto asignado, programando adecuadamente los m�dulos del sistema, 

                    apoyar en el mantenimiento de los recursos inform�ticos o brindar soporte t�cnico especializado a 

                    los equipos y sistemas solventando problemas o requerimientos de los usuarios; en consecuencia, el 

                    EMPLEADOR ha visto por conveniente contratar al TRABAJADOR (A), bajo la modalidad de servicio 

                    espec�fico.

                <p><strong>TERCERA:</strong> EL EMPLEADOR de acuerdo con la condici�n expresada en la segunda cl�usula, contrata al 

                    (la) TRABAJADOR (A) para que se desempe�e como <strong><%=p.getNo_puesto()%></strong>.</p> 

                <p><strong>CUARTA:</strong> EL EMPLEAOR en virtud del presente documento, contrata, al (la) TRABAJADOR (A) desde el 

                    <strong><%=p.getFe_desde()%></strong> hasta el <strong><%=p.getFe_hasta()%></strong>.</p>

                <p><strong>QUINTA:</strong> EL (la) TRABAJADOR (A) percibir� una remuneraci�n mensual de S/ <strong><%=p.getCa_sueldo()%></strong>. 

                    (Nuevos Soles), como retribuci�n por las labores realizadas en la jornada y horario laboral establecido por 

                    el ?EMPLEADOR?, la que ser� de 48 horas semanales.<p>

                <p><strong>SEXTA:</strong> EL (la) TRABAJADOR (A) se obliga a prestar sus servicios con eficiencia, puntualidad y bajo 

                    principio de subordinaci�n, cumplir con las directivas de sus jefes y de su Centro de Labor y con lo que 

                    dispone El Reglamento General Interno de Trabajo, Reglamento de Trabajo en Sobretiempo, Reglamento 

                    de Seguridad y Salud en el Trabajo, Recomendaciones sobre Seguridad y Salud en el Trabajo y dem�s 

                    pol�ticas, que por su funci�n le son entregados a la suscripci�n del presente contrato, caso contrario se 

                    resolver� el presente contrato conforme las normas laborales vigentes.</p>

                <p><strong>S�PTIMA:</strong> EL (LA) TRABAJADOR (A) conoce y acepta que al t�rmino del plazo fijado en la cl�usula 

                    CUARTA del presente contrato, �ste quedar� extinguido autom�ticamente sin obligaci�n de aviso previo 

                    o comunicaci�n alguna. EL EMPLEADOR sin perjuicio de lo indicado en las cl�usulas anteriores, podr� 

                    dar por terminado el presente contrato si EL (la) TRABAJADOR (A) incurre en alguna de las causales de 

                    despido conforme a las leyes vigentes.</p>

                <p><strong>OCTAVA:</strong> EL (LA) TRABAJADOR (A), en cualquier momento, podr� dar por terminada la relaci�n laboral 

                    en forma expresa, con la sola limitaci�n de presentar su carta de renuncia con 30 d�as de anticipaci�n.</p>

                <p><strong>NOVENA:</strong> En todo lo no previsto en el presente contrato se aplicar� la Ley vigente de nuestro pa�s.

                    Estando de acuerdo en todo el contenido del presente contrato, firmamos en se�al de conformidad, en 

                    �a�a, Chosica, Lima, al <strong><%=p.getFecha_actual()%></strong>.

                </p>
                <br>
                <div  ><table  style="width: 100%;  height: 50px; "><tr><td align="center">______________________________<br><br>EMPLEADOR</td>
                        <td align="center">_______________________________<br><br>TRABAJADOR(A)</td></tr></table></div>

            </div>     
            <%
                }%>
        </div>
        <center>

            <td align="center"><a href="javascript:imprSelec('muestra')" id="imprime" class="imprimirTexto">Imprimir Plantilla</a></td>
        </center>

        <!-- 
 <input type="button" name="imprimir" value="Imprimir" onclick="window.print();">-->
    </body>

</html>
<%} else {
        out.print("<script> window.parent.location.href = '/TALENTO_HUMANO/';</script>");
    }
%>