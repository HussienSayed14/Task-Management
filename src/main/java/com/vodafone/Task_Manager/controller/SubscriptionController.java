package com.vodafone.Task_Manager.controller;

import com.vodafone.Task_Manager.dto.request.SubscriptionRequest;
import com.vodafone.Task_Manager.service.SubscriptionService;
import com.vodafone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
@Tag(name = "Report Subscription", description = "APIs for managing report subscriptions, including subscribing and unsubscribing.")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Subscribe to Reports", description = "Allows a user to subscribe to periodic task reports based on frequency (daily, weekly, monthly).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid subscription request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping
    public ResponseEntity<GenericResponse> subscribeToReports(
            @Valid @RequestBody SubscriptionRequest request,
            HttpServletRequest httpRequest) {
        return subscriptionService.subscribeToReports(request, httpRequest);
    }

    @Operation(summary = "Unsubscribe from Reports", description = "Allows a user to unsubscribe from receiving periodic task reports.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unsubscribed successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active subscription found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)))
    })
    @DeleteMapping
    public ResponseEntity<GenericResponse> unsubscribe(HttpServletRequest httpRequest) {
        return subscriptionService.unsubscribe(httpRequest);
    }
}
