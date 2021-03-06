package com.app.web.controller;

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
        return new ModelAndView("/views/index.html");
    }

    @RequestMapping(value = "/Requerimiento")
    public ModelAndView menu() {
        return new ModelAndView("/views/Dgp/Requerimiento.html");
    }
    @RequestMapping(value = "/Reporte_Carga_Academica")
    public ModelAndView Reporte_Carga_Academica() {
        return new ModelAndView("views/Academico/Carga_Academica/Rep_Carga_Academica.html");
    }
    @RequestMapping(value = "/Buscar_Trabajador")
    public ModelAndView fichaTrabajador() {
        return new ModelAndView("/views/Trabajador/Reg_Trabajador.html");
    }

    @RequestMapping(value = "/paso")
    public ModelAndView process() {
        return new ModelAndView("/views/Proceso/Mant_Proceso.html");
        //return new ModelAndView("/views/Proceso/Mant_Paso.html");
        ////return new ModelAndView("/views/Proceso/Menu_Mantenimiento.html");
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
        return new ModelAndView("/views/Trabajador/Detalle_Trabajador.html");
    }
    @RequestMapping(value = "/formato_plantilla")
    public ModelAndView formato_plantilla() {
        return new ModelAndView("/views/Contrato/Formato_Plantilla/Reg_Formato_Plantilla.html");
    }

    @RequestMapping(value = "/dgps/failed")
    public ModelAndView List_req_incompl() {
        return new ModelAndView("/views/Dgp/List_req_incompl.html");
    }
    @RequestMapping(value = "/person")
    public ModelAndView person() {
        return new ModelAndView("/views/Trabajador/Detalle_Trabajador.html");
    }
    @RequestMapping(value = "/durations")
    public ModelAndView durations() {
        return new ModelAndView("/views/Dgp/Plazo/Reg_Plazo_Dgp.html");
    }
    @RequestMapping(value = "/formato_horario")
    public ModelAndView schedule() {
        return new ModelAndView("/views/Formato_Horario/Detalle_Formato_Horario.html");
    }

    @RequestMapping(value = "/dgp")
    public ModelAndView dgp() {
        return new ModelAndView("/views/Dgp/Proceso_Dgp.html");
    }

    @RequestMapping(value = "/imbox")
    public ModelAndView imbox() {
        return new ModelAndView("/views/Dgp/Autorizar_Requerimiento.html");
    }

}