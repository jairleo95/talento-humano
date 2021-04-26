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
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.app.dao_imp.InterfaceDocumentoDAO;
import com.app.factory.FactoryConnectionDB;
import com.app.model.Renombrar;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.dao.DocumentoDAO;

/**
 *
 * @author joserodrigo
 */

@RestController
@RequestMapping("documento_trabajador")
public class CDocumento_Trabajador {


    InterfaceDocumentoDAO d = new DocumentoDAO();

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<?> processRequest(HttpServletRequest request) {

        String dgp = request.getParameter("iddgp");
        String idtr = request.getParameter("idtr");
        String opc = request.getParameter("opc");
        HttpSession session = request.getSession(true);
        String user = (String) session.getAttribute("IDUSER");

        try {
            if (opc != null) {
                if (opc.equals("Eliminar")) {
                    String id_doc_adj = request.getParameter("id_doc");
                    d.Desactivar_doc(id_doc_adj);
                    session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                    session.setAttribute("Documentos", d.Documentos());
                    session.setAttribute("Lis_doc_trabajador", d.Lis_doc_trabajador(idtr));
                    session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));
                    int s = d.List_Req_nacionalidad(idtr);
                    int num_ad = d.List_Adventista(idtr);
                    //    response.sendRedirect("views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr + "&pro=pr_dgp&P2=TRUE");
                    String pr = request.getParameter("P2");
                    String url = "views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr;
                    if (pr != null) {
                        url += "&pro=pr_dgp&P2=TRUE";
                        ///response.sendRedirect(url);
                    } else {
                        ///response.sendRedirect("views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr);
                    }

                }
                if (opc.equals("Ver_Documento")) {

                    session.setAttribute("List_doc_req_pla", d.List_doc_req_pla(dgp, idtr));
                    int i = d.List_Req_nacionalidad(idtr);
                    int num_ad = d.List_Adventista(idtr);
                    session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                    session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));

                    ///response.sendRedirect("views/Dgp/Documento/Reg_Documento.html?n_nac=" + i + "&num_ad=" + num_ad);
                }
                if (opc.equals("Reg_Pro_Dgp")) {
                    session.setAttribute("List_doc_req_pla", d.List_doc_req_pla(dgp, idtr));
                    int i = d.List_Req_nacionalidad(idtr);
                    int num_ad = d.List_Adventista(idtr);
                    session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                    session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));

                    ///response.sendRedirect("views/Dgp/Documento/Reg_Documento.html?n_nac=" + i + "&num_ad=" + num_ad + "&pro=pr_dgp");
                }

                if (("Listar_doc").equals(opc)) {

                    session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                    session.setAttribute("Documentos", d.Documentos());
                    session.setAttribute("Lis_doc_trabajador", d.Lis_doc_trabajador(idtr));
                    session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));
                    int s = d.List_Req_nacionalidad(idtr);
                    int num_ad = d.List_Adventista(idtr);
                    int count = d.count_documentos_x_tra(idtr);
                    if (count > 0) {
                       /// response.sendRedirect("views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&Vol=volver&idtr=" + idtr);
                    } else {
                        ///response.sendRedirect("views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr);
                    }

                }
            } else {

                String ubicacion = FactoryConnectionDB.url + "Archivo/";
                DiskFileItemFactory f = new DiskFileItemFactory();
                f.setSizeThreshold(1024);
                f.setRepository(new File(ubicacion));
                ServletFileUpload upload = new ServletFileUpload(f);
                ServletRequestContext src = new ServletRequestContext(request);
                List<FileItem> p = upload.parseRequest(src);
                int num_filas = 0;
                String iddgp = null;
                String pr = null;
                String id_ctr = null;
                String ms = null;
                String dt = null;
                String idh = null;
                List<String> list_files = new ArrayList<String>();
                Iterator itera = p.iterator();
                Random rnd = new Random();
                while (itera.hasNext()) {
                    FileItem i_n_f = (FileItem) itera.next();

                    if (i_n_f.isFormField()) {

                        String nombre = i_n_f.getFieldName();
                        String valor = i_n_f.getString();

                        num_filas = (nombre.equals("num")) ? Integer.parseInt(valor) : num_filas;
                        if (nombre.equals("iddgp") & iddgp == null) {
                            iddgp = valor;
                        }
                        if (nombre.equals("idtr") & idtr == null) {
                            idtr = valor;
                        }
                        if (nombre.equals("P2") & pr == null) {
                            pr = valor;
                        }

                        if (nombre.equals("idctr") & id_ctr == null) {
                            id_ctr = valor;
                        }

                        if (nombre.equals("ms") & ms == null) {
                            ms = valor;
                        }
                        if (nombre.equals("dt") & ms == null) {
                            dt = valor;
                        }

                    }

                }

                String iddoc = null;
                String nombre_archivo = null;
                String desc = null;
                String estado = null;
                String archivo = null;
                int num = 0;
                String no_original = null;

                String validar_nombre = "";
                for (int i = 0; i < num_filas; i++) {
                    Iterator it = p.iterator();
                    while (it.hasNext()) {

                        FileItem item = (FileItem) it.next();

                        if (item.isFormField()) {
                            String nombre = item.getFieldName();
                            String valor = item.getString();
                            iddoc = (nombre.equals("iddoc" + i)) ? valor : iddoc;
                            idh = (nombre.equals("idh" + i)) ? valor : idh;
                            desc = (nombre.equals("lob_description" + i)) ? valor : desc;
                            estado = (nombre.equals("estado" + i)) ? valor : estado;
                        } else {
                            String fieldName = item.getFieldName();

                            num++;
                            Calendar fecha = new GregorianCalendar();
                            int hora = fecha.get(Calendar.HOUR_OF_DAY);
                            int min = fecha.get(Calendar.MINUTE);
                            int sec = fecha.get(Calendar.SECOND);

                            if (fieldName.equals("archivos" + i) & item.getName() != null) {
                                if (!item.getName().equals("")) {

                                    //out.println(item.getFieldName() + " : " + item.getName());
                                    //nombre_archivo = String.valueOf(hora) + String.valueOf(min) + String.valueOf(sec) + "_" + num + iddgp + "_" + item.getName().toUpperCase();
                                    nombre_archivo = String.valueOf(hora) + String.valueOf(min) + String.valueOf(sec) + "_" + num;
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
                        }
                    }
                    Thread.sleep(200);
                    if (nombre_archivo != null) {

                        if (!nombre_archivo.equals("")) {

                            estado = ((estado == null) ? "0" : estado);

                            String id = d.INSERT_DOCUMENTO_ADJUNTO(null, iddoc, "1", user, null, null, null, null, desc, null, estado, id_ctr);
                            //out.print(id);
                            d.INSERT_DGP_DOC_tra(null, null, id, null, idtr, idh);
                            for (int t = 0; t < list_files.size(); t++) {
                                String g[] = list_files.get(t).split(":");
                                d.INSERT_ARCHIVO_DOCUMENTO(null, id, g[1], g[0], null);
                            }
                            list_files.clear();

                        }

                    }
                    no_original = null;
                    iddoc = null;
                    nombre_archivo = null;
                    desc = null;
                    estado = null;

                    no_original = null;

                }

                session.setAttribute("List_Hijos", d.List_Hijos(idtr));
                session.setAttribute("Documentos", d.Documentos());
                session.setAttribute("Lis_doc_trabajador", d.Lis_doc_trabajador(idtr));
                session.setAttribute("List_Conyugue", d.List_Conyugue(idtr));

                int s = d.List_Req_nacionalidad(idtr);
                int num_ad = d.List_Adventista(idtr);

                System.out.print(idtr);
                int count = d.count_documentos_x_tra(idtr);

                if (count > 0) {
                    String url = "views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr + "&m=si";
                    if (pr != null) {
                        url += "&P2=TRUE";
                    }
                    if (ms != null) {
                        url += "&ms=" + ms;
                    }
                    if (dt != null) {
                        url += "&dt=" + dt;
                    }
                    ///response.sendRedirect(url);

                } else {
                    String url = "views/Trabajador/Documento/Reg_Documento.html?n_nac=" + s + "&num_ad=" + num_ad + "&idtr=" + idtr + "&pro=pr_dgp&P2=TRUE";
                    if (ms != null) {
                        url += "&ms=" + ms;
                    }
                    if (dt != null) {
                        url += "&dt=" + dt;
                    }
                    ///response.sendRedirect(url);
                }
            }
        } catch (Exception e) {
            //out.println("Error : " + e.getMessage());
        } finally {
            //out.close();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
