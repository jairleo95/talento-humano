package com.app.recruitment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("worker")
public class Worker {

    @Id
    private UUID id;

    @Version
    private Long version;

    @Column("first_name")
    private String firstName;

    @Column("last_name_paternal")
    private String lastNamePaternal;

    @Column("last_name_maternal")
    private String lastNameMaternal;

    @Column("document_type")
    private String documentType;

    @Column("document_number")
    private String documentNumber;

    @Column("document_number_complement")
    private String documentNumberComplement;

    @Column("birth_date")
    private LocalDate birthDate;

    @Column("gender")
    private String gender;

    @Column("civil_status")
    private String civilStatus;

    @Column("blood_group")
    private String bloodGroup;

    @Column("rh_factor")
    private String rhFactor;

    @Column("nationality_id")
    private String nationalityId;

    @Column("department_id")
    private String departmentId;

    @Column("province_id")
    private String provinceId;

    @Column("district_id")
    private String districtId;

    @Column("phone")
    private String phone;

    @Column("cellphone")
    private String cellphone;

    @Column("email")
    private String email;

    @Column("institutional_email")
    private String institutionalEmail;

    @Column("address")
    private String address;

    @Column("education_level")
    private String educationLevel;

    @Column("degree")
    private String degree;

    @Column("professional_title")
    private String professionalTitle;

    @Column("career_id")
    private String careerId;

    @Column("university_id")
    private String universityId;

    @Column("other_studies")
    private String otherStudies;

    @Column("pension_system")
    private String pensionSystem;

    @Column("afp_id")
    private String afpId;

    @Column("is_essalud_affiliate")
    private Boolean isEssaludAffiliate;

    @Column("religion")
    private String religion;

    @Column("church_name")
    private String churchName;

    @Column("church_position")
    private String churchPosition;

    @Column("authority_type")
    private String authorityType;

    @Column("authority_name")
    private String authorityName;

    @Column("authority_phone")
    private String authorityPhone;

    @Column("worker_type")
    private String workerType;

    @Column("reference_pay_type")
    private String referencePayType;

    @Column("fifth_category_company_income")
    private String fifthCategoryCompanyIncome;

    @Column("fifth_category_ruc_income")
    private String fifthCategoryRucIncome;

    @Column("fifth_category_other_income")
    private String fifthCategoryOtherIncome;

    @Column("observations")
    private String observations;

    @Column("reference")
    private String reference;

    @Column("legacy_id")
    private String legacyId;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;
}
