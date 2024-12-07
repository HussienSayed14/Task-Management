package com.vofaone.Task_Manager.controller;

import com.vofaone.Task_Manager.dto.request.CreateTaskRequest;
import com.vofaone.Task_Manager.dto.request.RetrieveTasksRequest;
import com.vofaone.Task_Manager.dto.request.UpdateTaskRequest;
import com.vofaone.Task_Manager.dto.response.TaskResponse;
import com.vofaone.Task_Manager.service.TaskService;
import com.vofaone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
@Tag(name = "Task Creation", description = "Apis That is Responsible Task (Creation,Retrieval, Deletion and Updating")
public class TaskController {
    private final TaskService taskService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody CreateTaskRequest request,HttpServletRequest httpRequest,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return taskService.createTask(request, httpRequest);
    }

    @PostMapping("/fetch-all")
    public ResponseEntity<List<TaskResponse>> retrieveTasks(@Valid @RequestBody RetrieveTasksRequest request,
                                                            HttpServletRequest httpRequest,
                                                            BindingResult bindingResult) {
        List<TaskResponse> tasks = taskService.retrieveTasks(request, httpRequest);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/update")
    public ResponseEntity<GenericResponse> retrieveTasks(@Valid @RequestBody UpdateTaskRequest request,
                                                            HttpServletRequest httpRequest,
                                                            BindingResult bindingResult) {
        return taskService.updateTask(request, httpRequest);

    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<GenericResponse> deleteTask(
            @PathVariable int taskId,
            HttpServletRequest httpRequest) {
        return taskService.deleteTask(taskId, httpRequest);
    }

    @DeleteMapping("/batch")
    public ResponseEntity<GenericResponse> batchDeleteTasks(
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            HttpServletRequest httpRequest) {
        return taskService.batchDeleteTasks(startDate, endDate, httpRequest);
    }

    @PostMapping("/restore")
    public ResponseEntity<GenericResponse> restoreLastDeletedTask(HttpServletRequest httpRequest) {
        return taskService.restoreLastDeletedTask(httpRequest);
    }


}
