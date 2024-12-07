package com.vofaone.Task_Manager.service;

import com.vofaone.Task_Manager.config.JwtService;
import com.vofaone.Task_Manager.dto.request.SubscriptionRequest;
import com.vofaone.Task_Manager.entity.Subscription;
import com.vofaone.Task_Manager.entity.User;
import com.vofaone.Task_Manager.repository.SubscriptionRepository;
import com.vofaone.Task_Manager.util.GenericResponse;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    @Transactional
    public ResponseEntity<GenericResponse> subscribeToReports(SubscriptionRequest request, HttpServletRequest httpRequest) {
        GenericResponse response = new GenericResponse();
    try{
        int userId = jwtService.extractUserIdFromCookie(httpRequest);

        // Check if the user already has an active subscription
        Subscription existingSubscription = subscriptionRepository.findByUserId(userId);
        if (existingSubscription != null) {
            throw new IllegalArgumentException("User already has an active subscription.");
        }

        // Create a new subscription
        Subscription subscription = new Subscription();
        subscription.setUser(entityManager.getReference(User.class, userId));
        subscription.setStartDate(request.getStartDate());
        subscription.setFrequency(request.getFrequency());
        subscription.setReportTime(request.getReportTime());
        subscription.setLastReportDate(null);
        response.setSuccessful();

        subscriptionRepository.save(subscription);

    }catch (Exception e){
        response.setServerError();
        logger.error("An Error happened while creating a task", e.getMessage());
        e.printStackTrace();

    }
    return ResponseEntity.status(response.getHttpStatus()).body(response);

    }

    @Transactional
    public ResponseEntity<GenericResponse> unsubscribe(HttpServletRequest httpRequest) {
        GenericResponse response = new GenericResponse();
        try {
            int userId = jwtService.extractUserIdFromCookie(httpRequest);

            // Find the active subscription and deactivate it
            Subscription subscription = subscriptionRepository.findByUserId(userId);
            if (subscription == null) {
                throw new IllegalArgumentException("No active subscription found.");
            }
            subscriptionRepository.delete(subscription);
            response.setSuccessful();
        }catch (Exception e){
            response.setServerError();
            logger.error("An Error happened while creating a task", e.getMessage());
            e.printStackTrace();

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
