package com.app.dgp.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProcessService {
    private final DGPRepository dgpRepository;

    public Flux<DGP> getAllDgp() {
        return Flux.fromIterable(dgpRepository.findAll());
    }
    public Mono<ProcessStatusResponse> getProcessDetail(String idDgp, String idDrp, String idDep) {
        ProcessStatusResponse response =  new ProcessStatusResponse();
        List<Map<String, String>> process = dgpRepository.findDgpProcessDetails(idDgp, idDrp, idDep);
        response.setHtml(getHtmlProcess(process));
        return Mono.just(response);
    }
    public String getHtmlProcess(List<Map<String, String>> process) {
        String html = "";
        for (int i = 0; i < process.size(); i++) {
            Map<String, String> item = process.get(i);
            if (item.get("es_autorizacion") != null) {
                if (item.get("es_autorizacion").equals("1")) {

                    if (i == 1) {
                        html = html
                                + " <div class=\"new-circle done\" rel=\"popover-hover\" data-placement=\"top\" data-original-title=\"" + item.get("de_pasos") + "\" data-content=\""
                                + item.get("puesto_aut") + "\" data-html=\"true\">"
                                + "<span class=\"new-label\">&#10004;</span>"
                                + "<span class=\"new-title\">" + item.get("nu_pasos") + "</span> "
                                + "</div>";
                    } else {
                        html = html
                                + " <span class=\"new-bar done\"></span> "
                                + "<div class=\"new-circle done\" rel=\"popover-hover\" data-placement=\"top\" data-original-title=\"" + item.get("de_pasos") + "\" data-content=\"" + item.get("puesto_aut") + " \" data-html=\"true\">"
                                + "<span class=\"new-label\">&#10004;</span>"
                                + "<span class=\"new-title\">" + item.get("nu_pasos") + "</span>"
                                + " </div>";

                    }

                }
                if (item.get("es_autorizacion").equals("2")) {
                    html = html
                            + " <span class=\"new-bar done\"></span> "
                            + "<div  class=\"new-circle rechazo\" rel=\"popover-hover\" data-placement=\"top\" data-original-title=\"" + item.get("de_pasos") + "\" data-content=\" " + item.get("puesto_aut") + " \" data-html=\"true\">"
                            + "<span style='color:white; font:bold;' class=\"new-label fa fa-warning\"></span>"
                            + "<span class=\"new-title\">" + item.get("nu_pasos") + "</span>"
                            + " </div>";

                }
//            } else if ((Integer.parseInt(item.get("count_aut"))) + 1 == i) {
//                html = html
//                        + " <span class=\"new-bar active\"></span> "
//                        + "<div class=\"new-circle active\" rel=\"popover-hover\" data-placement=\"top\" data-original-title=\"" + item.get("de_pasos") + "\" data-content=\"" + item.get("puesto_aut") + " \" data-html=\"true\">"
//                        + "<span class=\"new-label fa fa-inbox\"></span>"
//                        + "<span class=\"new-title\">" + item.get("nu_pasos") + "</span>"
//                        + " </div>";

            } else {
                html = html
                        + " <span class=\"new-bar \"></span> "
                        + "<div class=\"new-circle\" rel=\"popover-hover\" data-placement=\"top\" data-original-title=\"" + item.get("de_pasos") + "\" data-content=\"" + item.get("puesto_aut") + " \" data-html=\"true\">"
                        + "<span class=\"new-label fa fa-lock\"></span>"
                        + "<span class=\"new-title\">" + item.get("nu_pasos") + "</span>"
                        + " </div>";

            }
        }

        return html;
    }

}
