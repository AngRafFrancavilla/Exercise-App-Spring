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

    //Questo endpoint serve per aggiornare gli abbonamenti di un utente e disattivando quelli vecchi
    @PutMapping("/update/{userId}")
    public ResponseEntity<Void> updateUserSubscriptions(
            @PathVariable Long userId,
            @RequestBody List<SubscriptionUpdateRequestDTO> newSubscriptions) {

        subscriptionService.updateUserSubscriptions(userId, newSubscriptions);
        return ResponseEntity.noContent().build();
    }

    //Questo endpoint ci stampa tutti gli abbonamenti attivi di un singolo utente

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<SubscriptionDTO>> getActiveSubscriptionsByUser(@PathVariable Long userId) {
        List<SubscriptionDTO> subscriptions = subscriptionService.getActiveSubscriptionsByUser(userId);
        return subscriptions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(subscriptions);
    }

    //Questo endpoint stampa tutti gli abbonamenti attivi presenti nel db
    @GetMapping("/subscription/active")
    public ResponseEntity<List<SubscriptionDTO>> getAllActiveSubscriptions(){
        List<SubscriptionDTO> subscriptions = subscriptionService.getAllActiveSubscriptions();
        if (subscriptions == null || subscriptions.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(subscriptions);
        }
    }

}
