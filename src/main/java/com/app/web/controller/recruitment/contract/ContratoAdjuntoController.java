/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.recruitment.contract;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.app.dao.ContratoDAO;
import com.app.dao_imp.InterfaceContratoDAO;
import com.app.factory.FactoryConnectionDB;
import com.app.model.Renombrar;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ALFA 3
 */

@RestController
@RequestMapping("contrato_archivo_adjunto")
public class ContratoAdjuntoController {

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public void process(HttpServletRequest request) {

        String ubicacion = "";
        InterfaceContratoDAO c = new ContratoDAO();
        try {
            ubicacion = FactoryConnectionDB.url + "Contratos_Adjuntos/";
            DiskFileItemFactory f = new DiskFileItemFactory();

            if (f.getSizeThreshold() <= 500000) {
                //out.print("adassd");
            } else {
                // out.print("putopoooo");
            }
            f.setRepository(new File(ubicacion));

            ServletFileUpload upload = new ServletFileUpload(f);

            List<FileItem> p = upload.parseRequest(request);
            String idc = null;
            String nombre_archivo = null;
            String no_original = null;
            long tamaño = 0;
            //  long sizeInBytes = 0;
            Iterator it = p.iterator();
            while (it.hasNext()) {

                FileItem item = (FileItem) it.next();

                if (item.isFormField()) {

                    String nombre = item.getFieldName();
                    String valor = item.getString();
                    if (nombre.equals("idc") & idc == null) {
                        idc = valor;
                    }

                } else {
                    tamaño = item.getSize();
                    if (tamaño <= 500000) {
                        String fieldName = item.getFieldName();
                        Calendar fecha = new GregorianCalendar();
                        int hora = fecha.get(Calendar.HOUR_OF_DAY);
                        int min = fecha.get(Calendar.MINUTE);
                        int sec = fecha.get(Calendar.SECOND);
                        if (fieldName.equals("archivo")) {
                            nombre_archivo = String.valueOf(hora) + String.valueOf(min) + String.valueOf(sec) + "_" + idc + "_" + item.getName().toUpperCase();
                            no_original = item.getName();
                            Thread thread = new Thread(new Renombrar(item, ubicacion, nombre_archivo));
                            thread.start();
                        } else {
                            no_original = no_original;
                            nombre_archivo = nombre_archivo;
                        }

                    }
                }

            }
            if (tamaño <= 500000) {
                if (nombre_archivo != null) {
                    c.INSERT_CONTRATO_ADJUNTO(null, idc, nombre_archivo, no_original, null, null, null, null, null, null);
                }
                System.out.println(no_original);
                System.out.println(nombre_archivo);
                System.out.println(tamaño);
            } else {
                System.out.print("No se permite subir archivos mayores a 0.5MB");
                System.out.print(upload.getFileSizeMax());
            }

            //sesion.setAttribute("ListaridTrabajador", tr.ListaridTrabajador(idtr));
            //Thread.sleep(2000);
            // response.sendRedirect("views/Trabajador/Detalle_Trabajador.html?idtr=" + idtr);
            // out.println("Archivo subido correctamente");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


}
