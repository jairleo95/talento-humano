package com.app.recruitment.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BudgetAllocationRequest(
        @NotBlank(message = "El tipo de requerimiento es obligatorio") String requirementType,
        String positionId,
        @NotNull(message = "El número de trabajadores es obligatorio") Integer workerCount,
        Double minSalary,
        Double maxSalary,
        Double minBonus,
        Double maxBonus,
        Double minFoodBonus,
        Double maxFoodBonus
) {
}