package com.vofaone.Task_Manager.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/taskManager/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "User Authentication" , description = "Apis That is Responsible User Authenticated Operations, does not need JWT token")
public class UserAuthController {
}
