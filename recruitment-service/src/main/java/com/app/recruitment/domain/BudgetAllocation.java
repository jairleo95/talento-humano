package com.app.recruitment.domain;
import lombok.AllArgsConstructor; import lombok.Builder; import lombok.Data; import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column; import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant; import java.util.UUID;
@Data @Builder @NoArgsConstructor @AllArgsConstructor @Table("budget_allocation")
public class BudgetAllocation {
    @Id private UUID id; @Version private Long version;
    @Column("period_id") private UUID periodId;
    @Column("requirement_type") private String requirementType;
    @Column("position_id") private String positionId;
    @Column("worker_count") private Integer workerCount;
    @Column("min_salary") private Double minSalary;
    @Column("max_salary") private Double maxSalary;
    @Column("min_bonus") private Double minBonus;
    @Column("max_bonus") private Double maxBonus;
    @Column("min_food_bonus") private Double minFoodBonus;
    @Column("max_food_bonus") private Double maxFoodBonus;
    @Column("created_at") private Instant createdAt;
}