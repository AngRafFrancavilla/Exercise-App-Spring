package it.exercise.exercise_app.service;

import it.exercise.exercise_app.dto.SubscriptionDTO;
import it.exercise.exercise_app.dto.SubscriptionUpdateRequestDTO;
import it.exercise.exercise_app.entity.Subscription;
import it.exercise.exercise_app.entity.SubscriptionDetail;
import it.exercise.exercise_app.entity.User;
import it.exercise.exercise_app.mapper.SubscriptionMapper;
import it.exercise.exercise_app.repository.SubscriptionRepository;
import it.exercise.exercise_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository; // ✅ aggiunto

    // Recupera tutti gli abbonamenti
    public List<SubscriptionDTO> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        System.out.println("DEBUG: Found " + subscriptions.size() + " subscriptions from DB");

        // Debug della prima subscription se esiste
        if (!subscriptions.isEmpty()) {
            Subscription first = subscriptions.get(0);
            System.out.println("DEBUG: First subscription - ID: " + first.getId() +
                    ", Name: " + first.getName() +
                    ", Status: " + first.getStatus());
        }

        // Test del mapper
        List<SubscriptionDTO> dtos = subscriptions.stream()
                .map(sub -> {
                    SubscriptionDTO dto = subscriptionMapper.toDTO(sub);
                    System.out.println("DEBUG: Mapped DTO - ID: " + (dto != null ? dto.getId() : "null") +
                            ", Name: " + (dto != null ? dto.getName() : "null"));
                    return dto;
                })
                .collect(Collectors.toList());

        return dtos;
    }

    // Recupera un abbonamento per ID
    public SubscriptionDTO getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toDTO)
                .orElse(null);
    }

    // Crea un nuovo abbonamento
    public SubscriptionDTO createSubscription(Subscription subscription) {
        Subscription saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(saved);
    }

    // Aggiorna un abbonamento esistente
    public SubscriptionDTO updateSubscription(Long id, Subscription updated) {
        return subscriptionRepository.findById(id)
                .map(sub -> {
                    sub.setName(updated.getName());
                    sub.setStartDate(updated.getStartDate());
                    sub.setEndDate(updated.getEndDate());
                    sub.setStatus(updated.getStatus());
                    Subscription saved = subscriptionRepository.save(sub);
                    return subscriptionMapper.toDTO(saved);
                }).orElse(null);
    }

    // Elimina un abbonamento
    public boolean deleteSubscription(Long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateUserSubscriptions(Long userId, List<SubscriptionUpdateRequestDTO> newSubscriptions) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Subscription> existingSubscriptions = subscriptionRepository.findByUser(user);
        existingSubscriptions.forEach(sub -> sub.setStatus("INACTIVE"));
        subscriptionRepository.saveAll(existingSubscriptions);

        List<Subscription> mappedSubscriptions = newSubscriptions.stream()
                .map(dto -> {
                    Subscription sub = new Subscription();
                    sub.setUser(user);
                    sub.setName(dto.getName());
                    sub.setStartDate(dto.getStartDate());
                    sub.setEndDate(dto.getEndDate());
                    sub.setStatus(dto.getStatus());

                    if (dto.getPrice() != null) {
                        SubscriptionDetail detail = new SubscriptionDetail();
                        detail.setPrice(BigDecimal.valueOf(dto.getPrice())); // converti Double -> BigDecimal
                        detail.setSubscription(sub); // collega il dettaglio all'abbonamento

                        if (sub.getDetails() == null) sub.setDetails(new ArrayList<>());
                        sub.getDetails().add(detail);
                    }
                    return sub;
                })
                .toList();

        subscriptionRepository.saveAll(mappedSubscriptions);
    }

}
