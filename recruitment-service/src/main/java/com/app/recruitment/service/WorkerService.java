package com.app.recruitment.service;

import com.app.recruitment.domain.Worker;
import com.app.recruitment.persistence.WorkerRepository;
import com.app.recruitment.web.WorkerMapper;
import com.app.recruitment.web.dto.WorkerRequest;
import com.app.recruitment.web.dto.WorkerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkerService {

    private static final String WORKER_NOT_FOUND = "Worker not found: ";

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    public Flux<WorkerResponse> list(String search) {
        Flux<Worker> flux = search == null || search.isBlank()
                ? repository.findAll()
                : repository.searchByTerm(search);
        return flux.map(mapper::toResponse);
    }

    public Mono<WorkerResponse> findById(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(WORKER_NOT_FOUND + id)))
                .map(mapper::toResponse);
    }

    public Mono<WorkerResponse> create(WorkerRequest request) {
        Worker entity = mapper.toEntity(request);
        entity.setId(UUID.randomUUID());
        return repository.save(entity).map(mapper::toResponse);
    }

    public Mono<WorkerResponse> update(UUID id, WorkerRequest request) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(WORKER_NOT_FOUND + id)))
                .flatMap(worker -> {
                    applyUpdate(worker, request);
                    return repository.save(worker).map(mapper::toResponse);
                });
    }

    private void applyUpdate(Worker worker, WorkerRequest request) {
        worker.setFirstName(request.firstName());
        worker.setLastNamePaternal(request.lastNamePaternal());
        worker.setLastNameMaternal(request.lastNameMaternal());
        worker.setDocumentType(request.documentType());
        worker.setDocumentNumber(request.documentNumber());
        worker.setDocumentNumberComplement(request.documentNumberComplement());
        worker.setBirthDate(request.birthDate());
        worker.setGender(request.gender());
        worker.setCivilStatus(request.civilStatus());
        worker.setBloodGroup(request.bloodGroup());
        worker.setRhFactor(request.rhFactor());
        worker.setNationalityId(request.nationalityId());
        worker.setDepartmentId(request.departmentId());
        worker.setProvinceId(request.provinceId());
        worker.setDistrictId(request.districtId());
        worker.setPhone(request.phone());
        worker.setCellphone(request.cellphone());
        worker.setEmail(request.email());
        worker.setInstitutionalEmail(request.institutionalEmail());
        worker.setAddress(request.address());
        worker.setEducationLevel(request.educationLevel());
        worker.setDegree(request.degree());
        worker.setProfessionalTitle(request.professionalTitle());
        worker.setCareerId(request.careerId());
        worker.setUniversityId(request.universityId());
        worker.setOtherStudies(request.otherStudies());
        worker.setPensionSystem(request.pensionSystem());
        worker.setAfpId(request.afpId());
        worker.setIsEssaludAffiliate(request.isEssaludAffiliate());
        worker.setReligion(request.religion());
        worker.setChurchName(request.churchName());
        worker.setChurchPosition(request.churchPosition());
        worker.setAuthorityType(request.authorityType());
        worker.setAuthorityName(request.authorityName());
        worker.setAuthorityPhone(request.authorityPhone());
        worker.setWorkerType(request.workerType());
        worker.setReferencePayType(request.referencePayType());
        worker.setFifthCategoryCompanyIncome(request.fifthCategoryCompanyIncome());
        worker.setFifthCategoryRucIncome(request.fifthCategoryRucIncome());
        worker.setFifthCategoryOtherIncome(request.fifthCategoryOtherIncome());
        worker.setObservations(request.observations());
        worker.setReference(request.reference());
        worker.setLegacyId(request.legacyId());
        worker.setUpdatedAt(Instant.now());
    }
}
