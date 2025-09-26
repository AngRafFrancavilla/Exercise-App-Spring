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

    public List<SubscriptionDTO> getAllSubscriptions() {
        return subscriptionRepository.findAll().stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SubscriptionDTO getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .map(subscriptionMapper::toDTO)
                .orElse(null);
    }

    public SubscriptionDTO createSubscription(Subscription subscription) {
        Subscription saved = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(saved);
    }


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

    public List<SubscriptionDTO> getActiveSubscriptionsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        List<Subscription> activeSubscriptions = subscriptionRepository.findByUser(user).stream()
                .filter(sub -> "ACTIVE".equalsIgnoreCase(sub.getStatus()))
                .toList();

        return activeSubscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<SubscriptionDTO> getAllActiveSubscriptions() {
        List<Subscription> activeSubscriptions = subscriptionRepository.findAll()
                .stream()
                .filter(sub -> "Active".equalsIgnoreCase(sub.getStatus()))
                .toList();
        System.out.println("DEBUG : Found " + activeSubscriptions.size() + "active subscriptions");

        return activeSubscriptions.stream()
                .map(subscriptionMapper::toDTO)
                .toList();
    }
}
