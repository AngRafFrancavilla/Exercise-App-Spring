package it.exercise.exercise_app.repository;

import it.exercise.exercise_app.entity.Subscription;
import it.exercise.exercise_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);
}

