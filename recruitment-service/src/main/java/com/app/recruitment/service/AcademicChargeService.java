package com.app.recruitment.service;

import com.app.recruitment.domain.AcademicCharge;
import com.app.recruitment.domain.AcademicCourse;
import com.app.recruitment.domain.AcademicPayment;
import com.app.recruitment.domain.Worker;
import com.app.recruitment.persistence.AcademicChargeRepository;
import com.app.recruitment.persistence.AcademicCourseRepository;
import com.app.recruitment.persistence.AcademicPaymentRepository;
import com.app.recruitment.persistence.WorkerRepository;
import com.app.recruitment.web.AcademicChargeMapper;
import com.app.recruitment.web.dto.AcademicChargeRequest;
import com.app.recruitment.web.dto.AcademicChargeResponse;
import com.app.recruitment.web.dto.AcademicChargeStatusRequest;
import com.app.recruitment.web.dto.AcademicCourseResponse;
import com.app.recruitment.web.dto.AcademicPaymentResponse;
import com.app.recruitment.web.dto.AcademicPaymentStatusRequest;
import com.app.recruitment.domain.AcademicModality;
import com.app.recruitment.domain.AcademicPeriod;
import com.app.recruitment.persistence.AcademicModalityRepository;
import com.app.recruitment.persistence.AcademicPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AcademicChargeService {

    private static final String CHARGE_NOT_FOUND = "Carga académica no encontrada: ";
    private static final String WORKER_NOT_FOUND = "Trabajador no encontrado: ";

    private final AcademicChargeRepository chargeRepository;
    private final AcademicCourseRepository courseRepository;
    private final AcademicPaymentRepository paymentRepository;
    private final WorkerRepository workerRepository;
    private final AcademicModalityRepository modalityRepository;
    private final AcademicPeriodRepository periodRepository;
    private final AcademicChargeMapper mapper;
    private final ReactiveTransactionManager transactionManager;

    public Flux<AcademicChargeResponse> list() {
        return chargeRepository.findAll()
                .flatMap(this::toSummaryResponse);
    }

    public Mono<AcademicChargeResponse> findById(UUID id) {
        return chargeRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(CHARGE_NOT_FOUND + id)))
                .flatMap(this::toDetailResponse);
    }

    public Mono<AcademicChargeResponse> create(AcademicChargeRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        AcademicCharge charge = mapper.toEntity(request);
        charge.setId(UUID.randomUUID());
        return workerRepository.findById(request.workerId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException(WORKER_NOT_FOUND + request.workerId())))
                .then(Mono.defer(() -> saveChargeWithDetails(charge, request)))
                .as(tx::transactional)
                .flatMap(this::toDetailResponse);
    }

    public Mono<AcademicChargeResponse> updateStatus(UUID id, AcademicChargeStatusRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return chargeRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(CHARGE_NOT_FOUND + id)))
                .flatMap(charge -> {
                    charge.setStatus(request.status());
                    charge.setUpdatedAt(Instant.now());
                    return chargeRepository.save(charge);
                })
                .as(tx::transactional)
                .flatMap(this::toDetailResponse);
    }

    public Mono<AcademicPaymentResponse> updatePaymentStatus(UUID chargeId, UUID paymentId, AcademicPaymentStatusRequest request) {
        TransactionalOperator tx = TransactionalOperator.create(transactionManager);
        return paymentRepository.findById(paymentId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Cuota no encontrada: " + paymentId)))
                .flatMap(payment -> {
                    if (!payment.getChargeId().equals(chargeId)) {
                        return Mono.error(new IllegalArgumentException("La cuota no pertenece a la carga: " + chargeId));
                    }
                    payment.setStatus(request.status());
                    return paymentRepository.save(payment);
                })
                .map(mapper::toResponse)
                .as(tx::transactional);
    }

    private Mono<AcademicCharge> saveChargeWithDetails(AcademicCharge charge, AcademicChargeRequest request) {
        return chargeRepository.save(charge)
                .flatMap(saved -> saveCourses(saved.getId(), request)
                        .then(savePayments(saved.getId(), request))
                        .thenReturn(saved));
    }

    private Mono<Void> saveCourses(UUID chargeId, AcademicChargeRequest request) {
        List<AcademicCourse> courses = request.courses().stream()
                .map(course -> mapper.toEntity(course, chargeId))
                .map(course -> {
                    course.setId(UUID.randomUUID());
                    return course;
                })
                .toList();
        return courseRepository.saveAll(courses).then();
    }

    private Mono<Void> savePayments(UUID chargeId, AcademicChargeRequest request) {
        List<AcademicPayment> payments = request.payments().stream()
                .map(payment -> mapper.toEntity(payment, chargeId))
                .map(payment -> {
                    payment.setId(UUID.randomUUID());
                    return payment;
                })
                .toList();
        return paymentRepository.saveAll(payments).then();
    }

    private Mono<AcademicChargeResponse> toSummaryResponse(AcademicCharge charge) {
        Mono<Worker> workerMono = workerRepository.findById(charge.getWorkerId());
        Mono<String> modalityNameMono = charge.getModalityId() != null
                ? modalityRepository.findById(charge.getModalityId()).map(AcademicModality::getName).defaultIfEmpty("")
                : Mono.just("");
        Mono<String> periodNameMono = charge.getPeriodId() != null
                ? periodRepository.findById(charge.getPeriodId()).map(AcademicPeriod::getName).defaultIfEmpty("")
                : Mono.just("");

        return Mono.zip(workerMono, modalityNameMono, periodNameMono)
                .map(tuple -> withWorkerAndCatalogs(bare(charge), tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private Mono<AcademicChargeResponse> toDetailResponse(AcademicCharge charge) {
        Mono<List<AcademicCourseResponse>> courses = courseRepository
                .findByChargeIdOrderByCourseNameAsc(charge.getId())
                .map(mapper::toResponse)
                .collectList();
        Mono<List<AcademicPaymentResponse>> payments = paymentRepository
                .findByChargeIdOrderByQuotaNumberAsc(charge.getId())
                .map(mapper::toResponse)
                .collectList();
        return toSummaryResponse(charge)
                .flatMap(summary -> Mono.zip(courses, payments)
                        .map(tuple -> withDetails(summary, tuple.getT1(), tuple.getT2())));
    }

    private AcademicChargeResponse bare(AcademicCharge charge) {
        return new AcademicChargeResponse(
                charge.getId(),
                charge.getWorkerId(),
                null,
                null,
                charge.getSemester(),
                charge.getFaculty(),
                charge.getSchool(),
                charge.getEducationalSituation(),
                charge.getProfession(),
                charge.getCondition(),
                charge.getPayType(),
                charge.getTotalHours(),
                charge.getStartDate(),
                charge.getEndDate(),
                charge.getModalityId(),
                null,
                charge.getPeriodId(),
                null,
                charge.getStatus(),
                charge.getCreatedBy(),
                charge.getCreatedAt(),
                charge.getUpdatedAt(),
                List.of(),
                List.of()
        );
    }

    private AcademicChargeResponse withWorkerAndCatalogs(AcademicChargeResponse response, Worker worker, String modalityName, String periodName) {
        return new AcademicChargeResponse(
                response.id(),
                response.workerId(),
                fullName(worker),
                worker.getDocumentNumber(),
                response.semester(),
                response.faculty(),
                response.school(),
                response.educationalSituation(),
                response.profession(),
                response.condition(),
                response.payType(),
                response.totalHours(),
                response.startDate(),
                response.endDate(),
                response.modalityId(),
                modalityName.isEmpty() ? null : modalityName,
                response.periodId(),
                periodName.isEmpty() ? null : periodName,
                response.status(),
                response.createdBy(),
                response.createdAt(),
                response.updatedAt(),
                List.of(),
                List.of()
        );
    }

    private String fullName(Worker worker) {
        return worker.getFirstName() + " "
                + worker.getLastNamePaternal() + " "
                + (worker.getLastNameMaternal() != null ? worker.getLastNameMaternal() : "");
    }

    private AcademicChargeResponse withDetails(AcademicChargeResponse response,
                                               List<AcademicCourseResponse> courses,
                                               List<AcademicPaymentResponse> payments) {
        return new AcademicChargeResponse(
                response.id(),
                response.workerId(),
                response.workerName(),
                response.documentNumber(),
                response.semester(),
                response.faculty(),
                response.school(),
                response.educationalSituation(),
                response.profession(),
                response.condition(),
                response.payType(),
                response.totalHours(),
                response.startDate(),
                response.endDate(),
                response.modalityId(),
                response.modalityName(),
                response.periodId(),
                response.periodName(),
                response.status(),
                response.createdBy(),
                response.createdAt(),
                response.updatedAt(),
                courses,
                payments
        );
    }
}