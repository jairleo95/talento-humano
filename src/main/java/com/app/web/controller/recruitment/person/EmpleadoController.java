/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.person;

import com.app.dao_imp.InterfaceEmpleadoDAO;
import com.app.model.page.Datatable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.EmpleadoDAO;

/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("empleado")
public class EmpleadoController {

    InterfaceEmpleadoDAO Iem = new EmpleadoDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {
        Map<String, Object> rpta = new HashMap<String, Object>();

        HttpSession sesion = request.getSession(true);
        String idrol = (String) sesion.getAttribute("IDROL");
        String opc = request.getParameter("opc");

        try {

            if (opc.equals("Eva_Emp")) {
                String ID_Trabajador = request.getParameter("idtr");
                String ID_Empleado = Iem.ID_Empleado(ID_Trabajador);
                String estado = Iem.ES_Empleado(ID_Empleado);
                sesion.setAttribute("LIST_EVALUACION", Iem.Listar_Evaluacion_Emp(ID_Empleado));
                if (estado != null) {
                    if (estado.equals("1")) {
                        ///response.sendRedirect("views/Empleado/List_Evaluacion_Emp.html?idemp=" + ID_Empleado);
                    } else {
                        ///response.sendRedirect("views/Empleado/Evaluacion_Empleado.html?idtr=" + ID_Trabajador);
                    }
                } else {
                    ///response.sendRedirect("views/Empleado/Evaluacion_Empleado.html?idtr=" + ID_Trabajador);
                }

            }
            if (opc.equals("Reg_Evaluar_Emp")) {
                String ID_EVALUACION_EMP = null;
                String ES_EVALUACION = request.getParameter("ESTADO");
                String RE_EVALUACION = request.getParameter("RE_EVALUACION");
                String ID_TRABAJADOR = request.getParameter("ID_TRABAJADOR");
                String ID_EMPLEADO = Iem.ID_Empleado(ID_TRABAJADOR);
                Iem.Insert_Evaluacion_Emp(ID_EVALUACION_EMP, ES_EVALUACION, RE_EVALUACION, ID_EMPLEADO);
                sesion.setAttribute("LIST_EVALUACION", Iem.Listar_Evaluacion_Emp(ID_EMPLEADO));
                ///response.sendRedirect("views/Empleado/List_Evaluacion_Emp.html?idemp=" + ID_EMPLEADO);
            }
            if (opc.equals("Editar")) {
                String ID_EMP = request.getParameter("idemp");
                sesion.setAttribute("LIST_EVALUACION", Iem.Listar_Evaluacion_Emp(ID_EMP));
                ///response.sendRedirect("views/Empleado/Mod_Evaluacion_Emp.html?idemp=" + ID_EMP);
            }

            if (opc.equals("modificar")) {
                String RE_EVALUACION = request.getParameter("RE_EVALUACION");
                String ID_EMPLEADO = request.getParameter("ID_EMPLEADO");
                Iem.Mod_Evaluacion_emp(RE_EVALUACION, ID_EMPLEADO);
                sesion.setAttribute("LIST_EVALUACION", Iem.Listar_Evaluacion_Emp(ID_EMPLEADO));
                ///response.sendRedirect("views/Empleado/List_Evaluacion_Emp.html?idemp=" + ID_EMPLEADO);

            }

            if (opc.equals("Reporte")) {
                ///response.sendRedirect("views/Empleado/Filtro_Empleado.html");
            }
            if (opc.equals("getAllEmployees")) {
                Datatable datatable = new Datatable();
                datatable.setPageSize(Integer.parseInt(request.getParameter("length")));
                datatable.setPageNumber(((Integer.parseInt(request.getParameter("start")) / datatable.getPageSize()) + 1));
                datatable.setDraw(Integer.parseInt(request.getParameter("draw")));

                String direccion = "";
                String departamento = "";
                String area = "";
                String seccion = "";
                if (idrol.trim().equals("ROL-0001")) {
                    direccion = request.getParameter("direccion");
                    departamento = request.getParameter("departamento");
                    area = request.getParameter("area");
                    seccion = request.getParameter("seccion");
                } else {
                    departamento = (String) sesion.getAttribute("DEPARTAMENTO_ID");
                }
                if (direccion == null) {
                    direccion = "";
                }
                if (departamento == null) {
                    departamento = "";
                }
                if (area == null) {
                    area = "";
                }
                if (seccion == null) {
                    seccion = "";
                }
                datatable = Iem.getAllEmployees(datatable, direccion, departamento, area, seccion);
                //   if (idrol.trim().equals("ROL-0001")) {
                rpta.put("data", datatable);
                rpta.put("recordsTotal", datatable.getRecordsTotal());
                rpta.put("recordsFiltered", datatable.getRecordsTotal());
                // } else {
                //   rpta.put("data", Iem.Listar_Empleado(iddepa));
                // }
                rpta.put("draw", datatable.getDraw());
            }

            if (opc.equals("validar_aps")) {
                String co_aps = request.getParameter("co_aps");
                int aps = Iem.val_aps(co_aps);
                rpta.put("rpta", "1");
                rpta.put("aps", aps);
            }
            if (opc.equals("validar_huella")) {
                String co_hue = request.getParameter("co_hue");
                int huella = Iem.val_huella(co_hue);
                rpta.put("rpta", "1");
                rpta.put("huella", huella);
            }
            if ("reg_huella".equals(opc)) {
                String idtr = request.getParameter("idtr");
                int cod_huella = Integer.parseInt(request.getParameter("cod"));
                Iem.Reg_cod_huella(idtr, cod_huella);
                rpta.put("rpta", "1");
            }
            if (opc.equals("ShowHuella")) {

                String idtr = request.getParameter("idtr");
                String html = "";
                List<Map<String, ?>> Lista = Iem.List_co_huella(idtr);
                if (Lista.size() > 0) {
                    String codigo = "";
                    Map<String, ?> x = Lista.get(0);
                    codigo = String.valueOf(x.get("codigo_huella"));
                    if (idrol.trim().equals("ROL-0001")) {
                        html = "                        <div class='form-group'>"
                                + "                          <div class='col-md-4' >"
                                + "                             <label class='control-label' for='prepend'> <strong>  Código de Huella Digital:" + "</strong></label>"
                                + "                          </div>"
                                + "                            <div class='col-md-8'>"
                                + "                                <div class='input-group input-group-sm'>"
                                + "                                    <div class='icon-addon addon-sm'>"
                                + "                                        <input type='text' value='" + ((!codigo.equals("null")) ? codigo : "") + "' placeholder='Huella Digital' maxlength='6' class='form-control textCodHuella'>"
                                + "                                        <label for='email' class='glyphicon glyphicon-info-sign' rel='tooltip' title='Escribir código de Huella Digital'></label>"
                                + "                                    </div>"
                                + "                                    <span class='input-group-btn'>"
                                + "                                        <button class='btn btn-default btnHuellaDigital' type='button' ><i class='glyphicon glyphicon-ok'></i></button>"
                                + "                                    </span>"
                                + "                                </div>"
                                + "                            </div>"
                                + "                        </div>";
                    } else {
                        html = "                        <div class='form-group'>"
                                + "                           <div class='col-md-8'> <label class='control-label' for='prepend'> <strong>  Código de Huella Digital: </strong></label></div>"
                                + "                       <div class='col-md-4'> " + ((!codigo.equals("null")) ? codigo : " No registrado") + "</div> "
                                + "                 </div>";
                    }
                } else {
                    html = "                        <div class='form-group'>"
                            + "                            <div class='col-md-8'> <label class='control-label col-md-4' for='prepend'> <strong>  Código de Huella Digital: </strong></label></div>"
                            + "                        <div class='col-md-4'>" + (" No registrado") + "</div>"
                            + "                      </div>";
                }

                rpta.put("value", html);
                rpta.put("rpta", "1");
            }
            if (opc.equals("ShowAPS")) {

                String idtr = request.getParameter("idtr");
                String html = "";
                List<Map<String, ?>> Lista = Iem.List_co_aps(idtr);
                if (Lista.size() > 0) {
                    String codigo = "";
                    Map<String, ?> x = Lista.get(0);
                    codigo = String.valueOf(x.get("aps"));
                    if (idrol.trim().equals("ROL-0001") | idrol.equals("ROL-0009")) {
                        html = "                        <div class='form-group'>"
                                + "                             <div class='col-md-4'>"
                                + "                               <label class='control-label' for='prepend'> <strong>  Código APS:" + "</strong></label>"
                                + "                             </div>                      "
                                + "                          <div class='col-md-8'>"
                                + "                                <div class='input-group input-group-sm'>"
                                + "                                    <div class='icon-addon addon-sm'>"
                                + "                                        <input type='text' value='" + ((!codigo.equals("null")) ? codigo : "") + "' placeholder='APS' maxlength='8' class='form-control txtCodigoAPS'>"
                                + "                                        <label for='email' class='glyphicon glyphicon-info-sign' rel='tooltip' title='Escribir código de APS'></label>"
                                + "                                    </div>"
                                + "                                    <span class='input-group-btn'>"
                                + "                                        <button class='btn btn-default btnCodigoAPS' type='button' rel='tooltip' title='Actualizar' ><i class='glyphicon glyphicon-ok'></i></button>"
                                + "                                    </span>"
                                + "                                </div>"
                                + "                            </div>"
                                + "                        </div>";
                    } else {
                        html = "                        <div class='form-group'>"
                                + "                          <div class='col-md-8'><label class='control-label' for='prepend'> <strong>  Código APS: </strong></label> </div>"
                                + "                     <div class='col-md-4' >" + ((!codigo.equals("null")) ? codigo : " No registrado") + "</div>"
                                + "                        </div>";
                    }
                } else {
                    html = "                        <div class='form-group'>"
                            + "                          <div class='col-md-8'>  <label class='control-label' for='prepend'> <strong>  Código APS: </strong></label></div>"
                            + "                      <div class='col-md-4' >" + (" No registrado") + "</div>"
                            + " </div>";
                }

                rpta.put("value", html);
                rpta.put("rpta", "1");
            }
            if ("reg_aps".equals(opc)) {
                String idtr = request.getParameter("idtr");
                int co_aps = Integer.parseInt(request.getParameter("cod"));
                Iem.Reg_aps(idtr, co_aps);
                rpta.put("rpta", "1");
            }
            if (opc.equals("getAllEmployeesWithOutUserAccount")) {
                rpta.put("data", Iem.getAllEmployeesWithOutUserAccount());
            }
            rpta.put("status", true);

        } catch (Exception e) {
            rpta.put("status", false);
            rpta.put("rpta", "-1");

            rpta.put("message", e.getMessage());
            rpta.put("mensaje", e.getMessage());
        }
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

}
