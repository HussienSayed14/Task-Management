package com.vofaone.Task_Manager.controller;

import com.vofaone.Task_Manager.dto.request.SubscriptionRequest;
import com.vofaone.Task_Manager.service.SubscriptionService;
import com.vofaone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
@Tag(name = "Report Subscription", description = "Apis That is Responsible for Report Subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;


    @PostMapping
    public ResponseEntity<GenericResponse> subscribeToReports(@Valid @RequestBody SubscriptionRequest request, HttpServletRequest httpRequest) {
        return subscriptionService.subscribeToReports(request, httpRequest);
    }

    @DeleteMapping
    public ResponseEntity<GenericResponse> unsubscribe(HttpServletRequest httpRequest) {
        return subscriptionService.unsubscribe(httpRequest);
    }
}
