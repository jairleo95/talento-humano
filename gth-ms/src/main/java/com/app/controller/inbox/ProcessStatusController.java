package com.app.controller.inbox;

import com.app.persistence.dao.DgpDAO;
import com.app.persistence.dao_imp.IDgpDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("process")
public class ProcessStatusController {
    IDgpDAO dgpDAO = new DgpDAO();

    @PostMapping("status")
    public ResponseEntity<?> getStatus (@RequestBody ProcessStatusRequest request){

        log.info(request.getDgps().size()+"");
        List<String> html =  new ArrayList<>();
        request.getDgps().stream()
                .limit(10).forEach(d ->{
                    html.add(
                            dgpDAO.Imprimir_det_proceso(d.getIddgp(), d.getIddrp(), d.getIddep())
                    );
                });
//        List<ProcessStatusResponse> processStatusResponse;
        return new ResponseEntity<>(html, HttpStatus.OK);
    }
}
