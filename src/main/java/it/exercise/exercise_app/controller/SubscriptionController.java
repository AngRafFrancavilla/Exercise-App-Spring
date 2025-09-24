package it.exercise.exercise_app.controller;

import it.exercise.exercise_app.dto.SubscriptionDTO;
import it.exercise.exercise_app.dto.SubscriptionUpdateRequestDTO;
import it.exercise.exercise_app.entity.Subscription;
import it.exercise.exercise_app.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscriptionById(@PathVariable Long id) {
        SubscriptionDTO subscription = subscriptionService.getSubscriptionById(id);
        return subscription != null ? ResponseEntity.ok(subscription) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public SubscriptionDTO createSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.createSubscription(subscription);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@PathVariable Long id, @RequestBody Subscription subscription) {
        SubscriptionDTO updated = subscriptionService.updateSubscription(id, subscription);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        boolean deleted = subscriptionService.deleteSubscription(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUserSubscriptions(
            @PathVariable Long userId,
            @RequestBody List<SubscriptionUpdateRequestDTO> newSubscriptions) {

        subscriptionService.updateUserSubscriptions(userId, newSubscriptions);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
