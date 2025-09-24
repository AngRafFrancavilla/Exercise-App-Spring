package it.exercise.exercise_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.math.BigDecimal;

@Entity
@Table(name = "subscription_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="subscription_id", nullable=false)
    @JsonBackReference
    private Subscription subscription;

    private BigDecimal price = BigDecimal.ZERO;

    @Column(name="created_at", updatable=false, insertable=false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;
}
