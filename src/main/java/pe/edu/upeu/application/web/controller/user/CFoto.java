package pe.edu.upeu.application.web.controller.user;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upeu.application.dao.Fotos_TrabajadorDAO;
import pe.edu.upeu.application.dao.TrabajadorDAO;
import pe.edu.upeu.application.dao_imp.InterfaceFotos_TrabajadorDAO;
import pe.edu.upeu.application.dao_imp.InterfaceTrabajadorDAO;
import pe.edu.upeu.application.factory.FactoryConnectionDB;
import pe.edu.upeu.application.model.Renombrar;

/**
 *
 * @author ALFA 3
 */

@RestController
@RequestMapping("foto")
public class CFoto  {

    InterfaceFotos_TrabajadorDAO foto = new Fotos_TrabajadorDAO();
    @Autowired
    ServletContext context;

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        String opc = request.getParameter("opc");
        String idtra = request.getParameter("idtra");
        String tipo = request.getParameter("tipo");

        // InterfaceTrabajadorDAO tr = new TrabajadorDAO();
        String ubicacion = "";
        Map<String, Object> rpta = new HashMap<String, Object>();

        if (opc != null) {
            rpta.put("rpta", foto.Fotos_usuario(idtra.trim(), tipo));
        } else {

            try {
                // ubicacion = getServletConfig().getServletContext().getRealPath("/") +"views/Usuario/Fotos/";
                ubicacion = FactoryConnectionDB.url + "Fotos/";
                // ubicacion = getServletContext().getRealPath(".").substring(0, getServletContext().getRealPath(".").length() - 11) + "web\\views\\Usuario\\Fotos";
                DiskFileItemFactory f = new DiskFileItemFactory();
                f.setSizeThreshold(1024);
                f.setRepository(new File(ubicacion));
                ServletFileUpload upload = new ServletFileUpload(f);

                List<FileItem> p = upload.parseRequest(request);
                String idtr = null;
                String nombre_archivo = null;
                String no_original = null;
                String tipo_archivo = null;
                long sizeInBytes = 0;
                Iterator it = p.iterator();
                while (it.hasNext()) {
                    FileItem item = (FileItem) it.next();
                    if (item.isFormField()) {
                        String nombre = item.getFieldName();
                        String valor = item.getString();
                        if (nombre.equals("idtr") & idtr == null) {
                            idtr = valor;
                        }
                    } else {
                        String fieldName = item.getFieldName();
                        sizeInBytes = item.getSize();
                        tipo_archivo = item.getContentType();
                        Calendar fecha = new GregorianCalendar();
                        int hora = fecha.get(Calendar.HOUR_OF_DAY);
                        int min = fecha.get(Calendar.MINUTE);
                        int sec = fecha.get(Calendar.SECOND);
                        if (fieldName.equals("archivo")) {
                            nombre_archivo = String.valueOf(hora) + String.valueOf(min) + String.valueOf(sec) + "_" + idtr + "_" + item.getName().toUpperCase();
                            no_original = item.getName();
                            Thread thread = new Thread(new Renombrar(item, ubicacion, nombre_archivo));
                            thread.start();
                        } else {
                            no_original = no_original;
                            nombre_archivo = nombre_archivo;
                        }
                    }
                }
                foto.INSERT_FOTOS_TRABAJADOR(null, null, nombre_archivo, no_original, String.valueOf(sizeInBytes), tipo_archivo, idtr);
                rpta.put("rpta", "1");
                rpta.put("archivo", nombre_archivo);

            } catch (Exception e) {
                rpta.put("rpta", "-1");
                rpta.put("mensaje", e.getMessage());
            } finally {
                rpta.put("ruta", context.getRealPath("/"));
            }

        }

        return new ResponseEntity<>(rpta, HttpStatus.OK);
    }


}
