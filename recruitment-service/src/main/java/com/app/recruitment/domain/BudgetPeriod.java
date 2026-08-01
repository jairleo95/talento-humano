package com.app.recruitment.domain;
import lombok.AllArgsConstructor; import lombok.Builder; import lombok.Data; import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id; import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column; import org.springframework.data.relational.core.mapping.Table;
import java.time.Instant; import java.util.UUID;
@Data @Builder @NoArgsConstructor @AllArgsConstructor @Table("budget_period")
public class BudgetPeriod {
    @Id private UUID id; @Version private Long version;
    @Column("name") private String name;
    @Column("start_date") private Instant startDate;
    @Column("end_date") private Instant endDate;
    @Column("created_at") private Instant createdAt;
}