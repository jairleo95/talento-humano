package com.app.controller.process;



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.app.persistence.dao_imp.InterfaceComentario_DGPDAO;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.persistence.dao.Comentario_DGPDAO;

@RestController
@RequestMapping("comentario")
public class CommentaryController {

    private static final long serialVersionUID = 1L;

    public CommentaryController() {
        super();
    }

//-------------------------------------------------------------------------------------------
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    protected void processRequest(HttpServletRequest request) {

        //response.setContentType("text/html;charset=UTF-8");

        InterfaceComentario_DGPDAO comentarioDgpdao = new Comentario_DGPDAO();
        String opc = request.getParameter("opc");
        HttpSession session = request.getSession(true);
        String idu=(String)session.getAttribute("IDUSER");

        /*try {*/
        if (opc.equals("COMENTAR")) {
            String ID_COMENTARIO_DGP = "";
            String ID_DGP = request.getParameter("IDDETALLE_DGP");
            String ID_AUTORIZACION = request.getParameter("ID_AUTORIZACION");
            String CM_COMENTARIO = request.getParameter("COMENTARIO");
            String FE_CREACION = null;
            String US_MODIFICACION = null;
            String FE_MODIFICACION = null;
            String ES_COMENTARIO_DGP = "1";
            comentarioDgpdao.INSERT_COMENTARIO_DGP(ID_COMENTARIO_DGP, ID_DGP, ID_AUTORIZACION, CM_COMENTARIO, idu, FE_CREACION, US_MODIFICACION, FE_MODIFICACION, ES_COMENTARIO_DGP);
            session.setAttribute("List_Comentario_DGP", comentarioDgpdao.List_Comentario_DGP(ID_DGP));
            //response.sendRedirect("views/Dgp/Comentario/Reg_Comentario.html?iddgp="+ID_DGP);
        }
        if (opc.equals("Comentar_Dgp")) {

            String iddgp=request.getParameter("iddgp");
            session.setAttribute("List_Comentario_DGP", comentarioDgpdao.List_Comentario_DGP(iddgp));
            //response.sendRedirect("views/Dgp/Comentario/Reg_Comentario.html?iddgp="+iddgp);
        }

        /*} finally {
         out.close();
         }*/
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> doPost(HttpServletRequest request) throws ServletException, IOException {

        String iddgp = request.getParameter("iddgp");
        String opc = request.getParameter("opc");
        String cometario =request.getParameter("coment");

        HttpSession sesion = request.getSession(true);
        
        String idu=(String)sesion.getAttribute("IDUSER");

//        response.setContentType("application/json");
//        response.setHeader("Cache-control", "no-cache, no-store");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "-1");
        
        InterfaceComentario_DGPDAO x = new Comentario_DGPDAO();
        Map<String, Object> rpta = new HashMap<String, Object>();
        
        
            if (opc.equals("COMENTAR")) {
            String ID_COMENTARIO_DGP = "";
            String ID_DGP = iddgp;
            String ID_AUTORIZACION = request.getParameter("ID_AUTORIZACION");
            String CM_COMENTARIO = cometario;
            String FE_CREACION = null;
            String US_MODIFICACION = null;
            String FE_MODIFICACION = null;
            String ES_COMENTARIO_DGP = "1";
            x.INSERT_COMENTARIO_DGP(ID_COMENTARIO_DGP, ID_DGP, ID_AUTORIZACION, CM_COMENTARIO, idu, FE_CREACION, US_MODIFICACION, FE_MODIFICACION, ES_COMENTARIO_DGP);
        }
            if(opc.equals("list")){
            rpta.put("List_Comentarios",x.List_Comentario_DGP(iddgp));


            }

            return new ResponseEntity<>(rpta,HttpStatus.OK);
    }


}