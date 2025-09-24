// SubscriptionDTO.java
package it.exercise.exercise_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionDTO {
    private Long id;
    private String name;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
}
