package com.app.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by JairL on 10/25/2019.
 */
@RestController
public class MainController {

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/Requerimiento")
    public ModelAndView menu() {
        return new ModelAndView("/views/Dgp/Requerimiento.html");
    }
    @RequestMapping(value = "/Reporte_Carga_Academica")
    public ModelAndView Reporte_Carga_Academica() {
        return new ModelAndView("Academico/Carga_Academica/Rep_Carga_Academica");
    }
    @RequestMapping(value = "/Buscar_Trabajador")
    public ModelAndView fichaTrabajador() {
        return new ModelAndView("/views/Trabajador/Reg_Trabajador.html");
    }

    @RequestMapping(value = "/paso")
    public ModelAndView process() {
        return new ModelAndView("/views/Proceso/Mant_Proceso.html");
        //return new ModelAndView("/views/Proceso/Mant_Paso.html");
        //return new ModelAndView("/views/Proceso/Menu_Mantenimiento.html");
    }

    @RequestMapping(value = "/Mant_Paso")
    public ModelAndView step() {
        return new ModelAndView("/views/Proceso/Mant_Paso.html");
    }
    @RequestMapping(value = "/Mant_Proceso")
    public ModelAndView processMant() {
        return new ModelAndView("/views/Proceso/Mant_Proceso.html");
    }

    @RequestMapping(value = "/Detalle_Trabajador")
    public ModelAndView workerDetails() {
        return new ModelAndView("Trabajador/Detalle_Trabajador");
    }
    @RequestMapping(value = "/formato_plantilla")
    public ModelAndView formato_plantilla() {
        return new ModelAndView("/views/Contrato/Formato_Plantilla/Reg_Formato_Plantilla.html");
    }

    @RequestMapping(value = "/List_req_incompl")
    public ModelAndView List_req_incompl() {
        return new ModelAndView("Dgp/List_req_incompl");
    }
    @RequestMapping(value = "/List_Dgp")
    public ModelAndView List_Dgp() {
        return new ModelAndView("Dgp/List_Dgp");
    }
    @RequestMapping(value = "/person")
    public ModelAndView person() {
        return new ModelAndView("Trabajador/Detalle_Trabajador");
    }
    @RequestMapping(value = "/durations")
    public ModelAndView durations() {
        return new ModelAndView("/views/Dgp/Plazo/Reg_Plazo_Dgp.html");
    }
    @RequestMapping(value = "/formato_horario")
    public ModelAndView schedule() {
        return new ModelAndView("/views/Formato_Horario/Detalle_Formato_Horario.html");
    }

    @RequestMapping(value = "/Proceso_Dgp")
    public ModelAndView dgp() {
        return new ModelAndView("/Dgp/Proceso_Dgp");
    }

    @RequestMapping(value = "/imbox")
    public ModelAndView imbox() {
        return new ModelAndView("/Dgp/Autorizar_Requerimiento");
    }
    @RequestMapping(value = "/generate")
    public ModelAndView generateDGP() {
        return new ModelAndView("/views/Dgp/Generar_Dgp.html");
    }
    @RequestMapping(value = "/Reg_Dgp")
    public ModelAndView regDGP() {
        return new ModelAndView("/views/Dgp/Reg_Dgp.html");
    }
    @RequestMapping(value = "/Reg_Usuario")
    public ModelAndView Reg_Usuario() {
        return new ModelAndView("/views/Usuario/Reg_Usuario.html");
    }
    @RequestMapping(value = "/Filtro_Empleado")
    public ModelAndView Filtro_Empleado() {
        return new ModelAndView("Empleado/Filtro_Empleado");
    }
    @RequestMapping(value = "/Procesar_Req")
    public ModelAndView Procesar_Req() {
        return new ModelAndView("Dgp/Procesar_Req");
    }
    @RequestMapping(value = "/Autorizar_Carga_Academica")
    public ModelAndView Autorizar_Carga_Academica() {
        return new ModelAndView("Academico/Autorizar_Carga_Academica");
    }

    @RequestMapping(value = "/Validar_Foto")
    public ModelAndView Validar_Foto() {
        return new ModelAndView("Reportes/Validar_Foto/validar_foto");
    }
    @RequestMapping(value = "/Gen_Contrato_CE")
    public ModelAndView Gen_Contrato_CE() {
        return new ModelAndView("Contrato/Gen_Contrato_CE");
    }
    @RequestMapping(value = "/Filtro_Contrato_CE")
    public ModelAndView Filtro_Contrato_CE() {
        return new ModelAndView("Contrato/Filtro_Contrato_CE");
    }
    @RequestMapping(value = "/hist_tra")
    public ModelAndView Historial() {
        return new ModelAndView("Reportes/Trabajador/Historial");
    }
    @RequestMapping(value = "/Historial_Est_Civil")
    public ModelAndView Historial_Est_Civil() {
        return new ModelAndView("Reportes/Trabajador/Historial_Est_Civil");
    }
    @RequestMapping(value = "/Reg_Contrato")
    public ModelAndView Reg_Contrato() {
        return new ModelAndView("Contrato/Reg_Contrato");
    }
    @RequestMapping(value = "/Detalle_Info_Contractualq")
    public ModelAndView Detalle_Info_Contractualq() {
        return new ModelAndView("Contrato/Detalle_Info_Contractualq");
    }
    @RequestMapping(value = "/Detalle_Dgp")
    public ModelAndView Detalle_Dgp() {
        return new ModelAndView("Dgp/Detalle_Dgp");
    }
    @RequestMapping(value = "/Reg_Documento")
    public ModelAndView Reg_Documento() {
        return new ModelAndView("Dgp/Documento/Reg_Documento");
    }
    @RequestMapping(value = "/Busc_Contrato")
    public ModelAndView Busc_Contrato() {
        return new ModelAndView("Contrato/Busc_Contrato");
    }
    @RequestMapping(value = "/MantCCosto")
    public ModelAndView MantCCosto() {
        return new ModelAndView("CCosto/MantCCosto");
    }


}