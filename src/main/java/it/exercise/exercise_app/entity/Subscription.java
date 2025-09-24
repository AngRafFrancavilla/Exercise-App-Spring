package it.exercise.exercise_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "subscriptions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false)
    @JoinColumn(name="user_id", nullable=false)
    @JsonBackReference //lato figlio
    private User user;

    @Column(nullable=false, length=150)
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(length=50)
    private String status;

    @Column(name="created_at", updatable=false, insertable=false,
            columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @OneToMany(mappedBy="subscription", cascade=CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private List<SubscriptionDetail> details;
}
