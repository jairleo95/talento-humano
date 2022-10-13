/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.documents;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.dao.ContratoDAO;
import com.app.dao_imp.InterfaceContratoDAO;
import com.app.factory.FactoryConnectionDB;
import com.app.model.Renombrar;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("documento_adjunto")
public class DocumentoAdjuntoController {

    InterfaceContratoDAO c = new ContratoDAO();

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    protected ResponseEntity<?> process(HttpServletRequest request) throws FileUploadException, InterruptedException {

        ///response.setContentType("text/html;charset=UTF-8");

        String dgp = request.getParameter("iddgp");
        String idtr = request.getParameter("idtr");
        String idctr = request.getParameter("idctr");
        String opc = request.getParameter("opc");
        HttpSession sesion = request.getSession(true);
        String user = (String) sesion.getAttribute("IDUSER");

        // try (PrintWriter out = response.getWriter()) {
        if (opc != null) {
            if (opc.equals("Eliminar")) {
                String id_c = request.getParameter("idc");
                c.Eliminar_Contratos_firmados(id_c);
                int coun_doc = c.Count_doc_con(request.getParameter("idc"));
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/Subir_Contrato_Firmado.html?idc=" + id_c + "&coun_doc=" + coun_doc);
                //out.print(coun_doc + id_c);
            }
        } else {

            String ubicacion = FactoryConnectionDB.url+ "/Contratos_Adjuntos/";
            System.out.println("Enter to Save Contrato");

            DiskFileItemFactory f = new DiskFileItemFactory();
            f.setSizeThreshold(1024);
            f.setRepository(new File(ubicacion));
            ServletFileUpload upload = new ServletFileUpload(f);
            ServletRequestContext src = new ServletRequestContext(request);
            // try {
            List<FileItem> p = upload.parseRequest(src);
            int num_filas = 0;
            String idc = null;

            List<String> list_files = new ArrayList<String>();
            Iterator itera = p.iterator();

            while (itera.hasNext()) {
                FileItem i_n_f = (FileItem) itera.next();
                if (i_n_f.isFormField()) {
                    String nombre = i_n_f.getFieldName();
                    String valor = i_n_f.getString();
                    num_filas = (nombre.equals("num")) ? Integer.parseInt(valor) : num_filas;
                    if (nombre.equals("idc") & idc == null) {
                        idc = valor;
                    }
                }
            }
            String nombre_archivo = null;

            String estado = null;
            String archivo = null;
            int num = 0;
            String no_original = null;

            Iterator it = p.iterator();

            while (it.hasNext()) {

                FileItem item = (FileItem) it.next();

                if (item.isFormField()) {
                    String nombre = item.getFieldName();
                } else {
                    String fieldName = item.getFieldName();

                    num++;
                    Calendar fecha = new GregorianCalendar();
                    int hora = fecha.get(Calendar.HOUR_OF_DAY);
                    int min = fecha.get(Calendar.MINUTE);
                    int sec = fecha.get(Calendar.SECOND);

                    if (fieldName.equals("archivo") & item.getName() != null) {
                        if (!item.getName().equals("")) {

                            // out.println(item.getFieldName() + " : " + item.getName());
                            nombre_archivo = String.valueOf(hora) + String.valueOf(min) + String.valueOf(sec) + "_" + num + idc + "_" + item.getName().toUpperCase();
                            no_original = item.getName();
                            Thread thread = new Thread(new Renombrar(item, ubicacion, nombre_archivo));
                            thread.start();
                            archivo = no_original + ":" + nombre_archivo;
                            list_files.add(archivo);
                        }

                    } else {
                        no_original = no_original;
                        nombre_archivo = nombre_archivo;
                    }

                    System.out.println(no_original);
                    if (nombre_archivo != null) {

                        if (!nombre_archivo.equals("")) {

                            estado = ((estado == null) ? "0" : estado);

                            for (int t = 0; t < list_files.size(); t++) {
                                c.INSERT_CONTRATO_ADJUNTO(null, idc, nombre_archivo, no_original, null, null, null, null, null, null);
                                String g[] = list_files.get(t).split(":");

                            }
                            list_files.clear();

                        }

                    }
                    no_original = null;
                    estado = null;

                    no_original = null;
                }
            }

            Thread.sleep(2000);

            int coun_doc = c.Count_doc_con(idc);
            if (coun_doc > 0) {
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/Subir_Contrato_Firmado.html?idc=" + idc + "&coun_doc=" + coun_doc);
            } else {
                ///response.sendRedirect("views/Contrato/Formato_Plantilla/Subir_Contrato_Firmado.html?idc=" + idc + "&coun_doc=" + coun_doc);
            }
        }
        // }

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
