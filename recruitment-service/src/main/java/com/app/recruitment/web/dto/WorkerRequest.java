package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record WorkerRequest(
        @NotBlank String firstName,
        @NotBlank String lastNamePaternal,
        String lastNameMaternal,
        String documentType,
        String documentNumber,
        String documentNumberComplement,
        LocalDate birthDate,
        String gender,
        String civilStatus,
        String bloodGroup,
        String rhFactor,
        String nationalityId,
        String departmentId,
        String provinceId,
        String districtId,
        String phone,
        String cellphone,
        String email,
        String institutionalEmail,
        String address,
        String educationLevel,
        String degree,
        String professionalTitle,
        String careerId,
        String universityId,
        String otherStudies,
        String pensionSystem,
        String afpId,
        Boolean isEssaludAffiliate,
        String religion,
        String churchName,
        String churchPosition,
        String authorityType,
        String authorityName,
        String authorityPhone,
        String workerType,
        String referencePayType,
        String fifthCategoryCompanyIncome,
        String fifthCategoryRucIncome,
        String fifthCategoryOtherIncome,
        String observations,
        String reference,
        String legacyId
) {
}
