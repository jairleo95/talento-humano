package com.app.contract.service;

import com.app.contract.domain.Contract;
import com.app.contract.persistence.ContractRepository;
import com.app.contract.persistence.ContractTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemplateRenderService {

    private final ContractRepository contractRepository;
    private final ContractTemplateRepository templateRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.systemDefault());

    public Mono<String> renderContract(UUID contractId) {
        return contractRepository.findById(contractId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Contrato no encontrado: " + contractId)))
                .flatMap(contract -> {
                    if (contract.getTemplateId() == null) {
                        return Mono.just(buildDefaultRender(contract));
                    }
                    return templateRepository.findById(contract.getTemplateId())
                            .map(template -> replaceVariables(template.getContent(), contract))
                            .switchIfEmpty(Mono.just(buildDefaultRender(contract)));
                });
    }

    private String replaceVariables(String template, Contract c) {
        if (template == null) return buildDefaultRender(c);
        
        String result = template;
        result = result.replace("${NRO_CONTRATO}", HtmlSanitizer.escapeText(c.getContractNumber()));
        result = result.replace("${TRABAJADOR_ID}", HtmlSanitizer.escapeText(c.getWorkerId() != null ? c.getWorkerId().toString() : null));
        result = result.replace("${PUESTO_ID}", HtmlSanitizer.escapeText(c.getPositionId() != null ? c.getPositionId().toString() : null));
        result = result.replace("${SALARIO}", c.getSalaryAmount() != null ? "S/ " + c.getSalaryAmount() : "S/ 0.00");
        result = result.replace("${FECHA_INICIO}", c.getStartDate() != null ? DATE_FORMATTER.format(c.getStartDate()) : "—");
        result = result.replace("${FECHA_FIN}", c.getEndDate() != null ? DATE_FORMATTER.format(c.getEndDate()) : "—");
        result = result.replace("${REGIMEN_LABORAL}", HtmlSanitizer.escapeText(c.getLaborRegime()));
        result = result.replace("${REGIMEN_PENSION}", HtmlSanitizer.escapeText(c.getPensionRegime()));
        result = result.replace("${CONDICION}", HtmlSanitizer.escapeText(c.getConditionType()));
        return result;
    }

    private String buildDefaultRender(Contract c) {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================================================\n");
        sb.append("       CONTRATO INDIVIDUAL DE TRABAJO               \n");
        sb.append("=====================================================\n\n");
        sb.append("NÚMERO DE CONTRATO : ").append(HtmlSanitizer.escapeText(c.getContractNumber())).append("\n");
        sb.append("TRABAJADOR ID      : ").append(HtmlSanitizer.escapeText(c.getWorkerId() != null ? c.getWorkerId().toString() : null)).append("\n");
        sb.append("PUESTO ID          : ").append(HtmlSanitizer.escapeText(c.getPositionId() != null ? c.getPositionId().toString() : null)).append("\n");
        sb.append("RÉGIMEN LABORAL    : ").append(HtmlSanitizer.escapeText(c.getLaborRegime())).append("\n");
        sb.append("REMUNERACIÓN BASE  : S/ ").append(c.getSalaryAmount() != null ? c.getSalaryAmount() : 0.0).append("\n");
        sb.append("VIGENCIA DEL       : ").append(c.getStartDate() != null ? DATE_FORMATTER.format(c.getStartDate()) : "—")
          .append(" HASTA: ").append(c.getEndDate() != null ? DATE_FORMATTER.format(c.getEndDate()) : "—").append("\n");
        sb.append("ESTADO             : ").append(HtmlSanitizer.escapeText(c.getStatus())).append("\n\n");
        sb.append("OBSERVACIONES      : ").append(HtmlSanitizer.escapeText(c.getObservation())).append("\n");
        return sb.toString();
    }

    public Mono<String> renderBatchContracts(java.util.List<UUID> contractIds) {
        return reactor.core.publisher.Flux.fromIterable(contractIds)
                .concatMap(this::renderContract)
                .collectList()
                .map(list -> String.join("\n\n<div style='page-break-after: always; border-bottom: 2px dashed #999; margin: 40px 0;'></div>\n\n", list));
    }
}
