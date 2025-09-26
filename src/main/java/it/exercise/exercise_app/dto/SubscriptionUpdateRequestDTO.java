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
public class SubscriptionUpdateRequestDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Double price;
}
