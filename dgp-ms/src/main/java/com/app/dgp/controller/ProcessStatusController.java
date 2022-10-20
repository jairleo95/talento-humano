package com.app.dgp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("process")
public class ProcessStatusController {
    // InterfaceDgpDAO dgpDAO = new DgpDAO();

    @GetMapping("status")
    public ResponseEntity<?> getStatus(){
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("status")
    public ResponseEntity<?> postStatus(@RequestBody ProcessStatusRequest request){

        log.info(request.getDgps().size()+"");
        List<String> html =  new ArrayList();
        request.getDgps().stream()
                .limit(10).forEach(d ->{
                    // html.add(
                    //         dgpDAO.Imprimir_det_proceso(d.getIddgp(), d.getIddrp(), d.getIddep())
                    // ); 
                });
        return new ResponseEntity<>(html, HttpStatus.OK);
    }
}
