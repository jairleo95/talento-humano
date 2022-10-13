/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.web.controller.notify;

import com.app.dao.NotificationDAO;
import com.app.dao_imp.InterfaceNotificationDAO;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Cesar
 */
@RestController
@RequestMapping("cnot")
public class NotificationController {


    InterfaceNotificationDAO notdao = new NotificationDAO();

    @PostMapping
    public ResponseEntity<?> process(HttpServletRequest request) {

        Map<String, Object> rpta = new HashMap<String, Object>();
        int op = Integer.parseInt(request.getParameter("op"));
        String iduser;

        switch (op) {
            case 1:
                iduser=request.getParameter("id");
                System.out.println(iduser);
                try {
                    rpta.put("rpta", "1");
                    rpta.put("lista", notdao.List_Notifications_json(iduser));
                } catch (Exception e) {
                    rpta.put("rpta", "-1");
                    rpta.put("mensaje", e.getMessage());
                }
                //Gson gson = new Gson();
                //out.print(gson.toJson(rpta));
                //out.flush();
                //out.close();
                break;
            case 2:
                String id = request.getParameter("data");
                notdao.leido(id);
                break;
            case 3:
                iduser=request.getParameter("id");
                System.out.println(iduser);
                try {
                    rpta.put("rpta", "1");
                    rpta.put("lista", notdao.List_Notifications_json(iduser));

                } catch (Exception e) {
                    rpta.put("rpta", "-1");
                    rpta.put("mensaje", e.getMessage());
                }
                //gson = new Gson();
                //out.print(gson.toJson(rpta));
                //out.flush();
                //out.close();
                break;
            case 4:
                try {
                    String[] listid = request.getParameterValues("listid[]");
                    if (listid != null) {
                        for (int i = 0; i < listid.length; i++) {
                            notdao.visualizado(listid[i]);
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("Error por aca " + ex);
                }
                break;
            case 5:
                iduser=request.getParameter("id");
                try{
                    int n=0;
                    int no=0;
                    n=notdao.CountUnreadAuthorized(iduser);
                    no=notdao.CountUnreadUnAuthorized(iduser);
                    rpta.put("rpta", "1");
                    rpta.put("si", n);
                    rpta.put("no", no);
                }catch(Exception ex){
                    rpta.put("rpta", "-1");
                    rpta.put("mensaje", ex.getMessage());
                }
//                gson = new Gson();
//                out.print(gson.toJson(rpta));
//                out.flush();
//                out.close();
                break;
        }
        return new ResponseEntity<>(rpta , HttpStatus.OK);
    }

}
