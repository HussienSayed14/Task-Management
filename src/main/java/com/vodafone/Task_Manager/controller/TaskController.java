package com.vodafone.Task_Manager.controller;

import com.vodafone.Task_Manager.dto.request.CreateTaskRequest;
import com.vodafone.Task_Manager.dto.request.RetrieveTasksRequest;
import com.vodafone.Task_Manager.dto.request.UpdateTaskRequest;
import com.vodafone.Task_Manager.dto.response.TaskResponse;
import com.vodafone.Task_Manager.service.TaskService;
import com.vodafone.Task_Manager.util.GenericResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Task Management", description = "APIs for managing tasks, including creation, retrieval, updating, and deletion.")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Create a new task", description = "Creates a task with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class)))
    })
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> signUp(@Valid @RequestBody CreateTaskRequest request, HttpServletRequest httpRequest,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return taskService.createTask(request, httpRequest);
    }

    @Operation(summary = "Retrieve tasks", description = "Fetches a list of tasks based on the provided filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/fetch-all")
    public ResponseEntity<List<TaskResponse>> retrieveTasks(@Valid @RequestBody RetrieveTasksRequest request,
                                                            HttpServletRequest httpRequest) {
        List<TaskResponse> tasks = taskService.retrieveTasks(request, httpRequest);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Update an existing task", description = "Updates a task with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GenericResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PutMapping("/update")
    public ResponseEntity<GenericResponse> retrieveTasks(@Valid @RequestBody UpdateTaskRequest request,
                                                         HttpServletRequest httpRequest,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new GenericResponse(errorMessage));
        }
        return taskService.updateTask(request, httpRequest);
    }

    @Operation(summary = "Delete a task by ID", description = "Deletes a task identified by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @DeleteMapping("/{taskId}")
    public ResponseEntity<GenericResponse> deleteTask(
            @PathVariable @Parameter(description = "ID of the task to delete") int taskId,
            HttpServletRequest httpRequest) {
        return taskService.deleteTask(taskId, httpRequest);
    }

    @Operation(summary = "Batch delete tasks", description = "Deletes tasks within the specified date range.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid date range")
    })
    @DeleteMapping("/batch")
    public ResponseEntity<GenericResponse> batchDeleteTasks(
            @RequestParam("startDate") @Parameter(description = "Start date for batch deletion") Date startDate,
            @RequestParam("endDate") @Parameter(description = "End date for batch deletion") Date endDate,
            HttpServletRequest httpRequest) {
        return taskService.batchDeleteTasks(startDate, endDate, httpRequest);
    }

    @Operation(summary = "Restore the last deleted task", description = "Restores the most recently deleted task.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task restored successfully"),
            @ApiResponse(responseCode = "404", description = "No deleted task to restore")
    })
    @PostMapping("/restore")
    public ResponseEntity<GenericResponse> restoreLastDeletedTask(HttpServletRequest httpRequest) {
        return taskService.restoreLastDeletedTask(httpRequest);
    }

}
