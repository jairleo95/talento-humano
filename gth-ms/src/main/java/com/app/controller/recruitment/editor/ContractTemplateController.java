package com.app.controller.recruitment.editor;

import com.app.persistence.dao.DireccionDAO;
import com.app.persistence.dao.PlantillaContractualDAO;
import com.app.persistence.dao_imp.IDireccionDAO;
import com.app.persistence.dao_imp.IFormato_HorarioDAO;
import com.app.persistence.dao_imp.IContractTemplateDAO;
import com.app.config.factory.FactoryConnectionDB;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.Formato_HorarioDAO;

@RestController
@RequestMapping("templates")
public class ContractTemplateController {

    IContractTemplateDAO pl = new PlantillaContractualDAO();

    @GetMapping
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpSession sesion = request.getSession(true);

        Map<String, Object> rpta = new HashMap<String, Object>();
        String iduser = (String) sesion.getAttribute("IDUSER");
        String opc = request.getParameter("opc");
        String ubicacion = FactoryConnectionDB.url + "Formato/";

        if (iduser != null) {
            try {
                if (opc.equals("Listar")) {
                    String texto = "";
                    String imprimir = "";
                    String no_archivo = request.getParameter("id");
                    FileReader lector = new FileReader(ubicacion + no_archivo);
                    BufferedReader contenido = new BufferedReader(lector);
                    while ((texto = contenido.readLine()) != null) {
                        imprimir = imprimir + texto + "\n";
                    }
                    rpta.put("rpta", "1");
                    rpta.put("imprimir", imprimir);
                }
                if (opc.equals("Listar2")) {
                    String texto = "";
                    String imprimir = "";
                    String no_archivo = request.getParameter("id");
                    String no_arhivo_or = pl.List_pl_con_x_id(no_archivo);
                    FileReader lector = new FileReader(ubicacion + no_arhivo_or);
                    BufferedReader contenido = new BufferedReader(lector);
                    while ((texto = contenido.readLine()) != null) {
                        imprimir = imprimir + texto;
                    }
                    rpta.put("rpta", "1");
                    rpta.put("imprimir", imprimir);
                }
                if (opc.equals("List_Plamtillas")) {
                    String id_dir = request.getParameter("id_dir");
                    String id_dep = request.getParameter("id_dep");
                    String id_are = request.getParameter("id_are");
                    String id_sec = request.getParameter("sec");
                    String id_pu = request.getParameter("id_pu");
                    List<Map<String, ?>> lista = pl.List_PLant_x_sel(id_pu, id_sec, id_are, id_dep, id_dir);
                    rpta.put("rpta", "1");
                    rpta.put("lista", lista);
                }
            } catch (Exception e) {
                rpta.put("rpta", "-1");
                rpta.put("mensaje", e.getMessage());
            }
        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();

        IFormato_HorarioDAO f = new Formato_HorarioDAO();

        HttpSession sesion = request.getSession(true);
        IDireccionDAO dir = new DireccionDAO();
        String iduser = (String) sesion.getAttribute("IDUSER");
        String opc = request.getParameter("opc");
        String ubicacion = FactoryConnectionDB.url + "Formato/";

        if (iduser != null) {
            try {
                if (opc.equals("Asignar")) {
                    String DEP = request.getParameter("id_dep_asig");
                    String AREA = request.getParameter("id_are_asig");
                    String SEC = request.getParameter("id_sec_asig");
                    String DIR = request.getParameter("id_di_asig");
                    String PUES = request.getParameter("id_pu_asig");
                    String id = request.getParameter("id_pc");
                    pl.Insertar_pertenencia(id, DIR, DEP, AREA, SEC, PUES, iduser);
                    rpta.put("rpta", "1");
                }
                if (opc.equals("asignar")) {
                    sesion.setAttribute("Listar_Direccion_X", dir.Listar_Direccion());
                    //response.sendRedirect("views/Contrato/Formato_Plantilla/Reg_Formato_Plantilla.html");
                }
                if (opc.equals("Actualizar")) {
                    String texto_html = request.getParameter("valor");
                    String id = request.getParameter("id");
                    File archivo = new File(ubicacion + id);
                    if (archivo.exists()) {
                        FileWriter escribir = new FileWriter(archivo);
                        // aqui se hace un append al archivo existente
                        escribir.write(texto_html);
                        escribir.close();
                    } else {
                        FileWriter escribir = new FileWriter(archivo);
                        escribir.write(texto_html);
                        escribir.close();
                    }
                    rpta.put("rpta", "1");
                }
                if (opc.equals("UpdateNameFile")) {
                    String id = request.getParameter("id");
                    String nombre_pl = request.getParameter("nom_pl");
                    if (pl.Update_Name_File(id, nombre_pl)) {
                        rpta.put("rpta", "1");
                    } else {
                        rpta.put("rpta", "-1");
                    }
                }

                if (opc.equals("activar_pp")) {
                    String id_pp = request.getParameter("id_pp");
                    pl.Activar_pl_pu(id_pp, iduser);
                }
                if (opc.equals("Desactivar_pp")) {
                    String id_pp = request.getParameter("id_pp");
                    pl.Desactivar_pl_pu(id_pp, iduser);
                }
                if (opc.equals("Crear_Plantilla")) {
                    String texto_html = request.getParameter("valor");
                    String DEP = request.getParameter("id_dep_asig");
                    String AREA = request.getParameter("id_are_asig");
                    String SEC = request.getParameter("id_sec_asig");
                    String DIR = request.getParameter("id_di_asig");
                    String PUES = request.getParameter("id_pu_asig");
                    String no_pl = request.getParameter("no_pl");

                    pl.Crear_Plantilla(no_pl, iduser);
                    String id_pl = pl.ob_id_pl_max();
                    pl.Insertar_pertenencia(id_pl, DIR, DEP, AREA, SEC, PUES, iduser);
                    String no_arch = pl.Obt_no_arch();
                    File archivo = new File(ubicacion + no_arch);
                    FileWriter escribir = new FileWriter(archivo, true);
                    escribir.write(texto_html);
                    escribir.close();
                    rpta.put("rpta", "1");
                }
            } catch (Exception e) {
                rpta.put("rpta", "-1");
                rpta.put("mensaje", e.getMessage());
            }
        } else {
            rpta.put("rpta", "-1");

        }
        Gson gson = new Gson();
//        out.println(gson.toJson(rpta));
//        out.flush();
//        out.close();
        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
