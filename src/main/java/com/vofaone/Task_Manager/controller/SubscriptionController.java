package com.vofaone.Task_Manager.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
@Tag(name = "Report Subscription", description = "Apis That is Responsible Task (Creation,Retrieval, Deletion and Updating")
public class SubscriptionController {
}
