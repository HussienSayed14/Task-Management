package com.vofaone.Task_Manager.controller;

import com.vofaone.Task_Manager.dto.request.CreateTaskRequest;
import com.vofaone.Task_Manager.service.TaskService;
import com.vofaone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Task Creation", description = "Apis That is Responsible Task (Creation,Retrieval, Deletion and Updating")
public class TaskController {
    private final TaskService taskService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody CreateTaskRequest request,HttpServletRequest httpRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return taskService.createTask(request, httpRequest);
    }

}
