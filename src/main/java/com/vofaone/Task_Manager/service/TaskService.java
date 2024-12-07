package com.vofaone.Task_Manager.service;

import com.vofaone.Task_Manager.config.JwtService;
import com.vofaone.Task_Manager.dto.request.CreateTaskRequest;
import com.vofaone.Task_Manager.dto.request.RetrieveTasksRequest;
import com.vofaone.Task_Manager.dto.request.UpdateTaskRequest;
import com.vofaone.Task_Manager.dto.response.TaskResponse;
import com.vofaone.Task_Manager.entity.Task;
import com.vofaone.Task_Manager.entity.User;
import com.vofaone.Task_Manager.enums.Status;
import com.vofaone.Task_Manager.repository.TaskRepository;
import com.vofaone.Task_Manager.util.DateService;
import com.vofaone.Task_Manager.util.GenericResponse;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final JwtService jwtService;
    private final EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);


    public ResponseEntity<GenericResponse> createTask(CreateTaskRequest request, HttpServletRequest httpRequest) {
        GenericResponse response = new GenericResponse();

        try {
            int userId = jwtService.extractUserIdFromCookie(httpRequest);

            // Create a new task
            Task task = buildTaskFromRequest(request, userId);

            // Save the task to the repository
            taskRepository.save(task);

            // Set success response
            response.setSuccessful("Task Created Successfully");
        } catch (Exception e) {
            response.setServerError();
            logger.error("An Error happened while creating a task", e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    private Task buildTaskFromRequest(CreateTaskRequest request, int userId) {
        return Task.builder()
                .user(entityManager.getReference(User.class, userId))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .status(request.getStatus())
                .description(request.getDescription())
                .title(request.getTitle())
                .isDeleted(false)
                .isOverdue(false)
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .build();
    }


    public List<TaskResponse> retrieveTasks(RetrieveTasksRequest request, HttpServletRequest httpRequest) {
        List<TaskResponse> userTasks = new ArrayList<>();

        try {
            int userId = jwtService.extractUserIdFromCookie(httpRequest);
            List<Task> filteredTasks = taskRepository.findTasksByFilters(
                    userId,
                    request.getStatus(),
                    request.getStartDate(),
                    request.getEndDate());

            // Map each Task to TaskResponse
            return filteredTasks.stream()
                    .map(TaskResponse::new)
                    .collect(Collectors.toList());


        }catch (Exception e){
            logger.error("An Error happened while fetching tasks", e.getMessage());
            e.printStackTrace();
        }
        return userTasks;
    }

    public ResponseEntity<GenericResponse> updateTask(UpdateTaskRequest request, HttpServletRequest httpRequest) {
        GenericResponse response = new GenericResponse();

        try {
            // Extract user ID from JWT
            int userId = jwtService.extractUserIdFromCookie(httpRequest);

            // Fetch the task by ID
            Task task = taskRepository.findTaskById(request.getTaskId());
            if (task == null) {
                return buildErrorResponse(response, response::taskDoesNotExist);
            }

            // Check ownership
            if (userId != task.getUser().getId()) {
                return buildErrorResponse(response, response::userDoesNotOwnTask);
            }

            // Update task fields
            updateTaskFields(task, request);

            // Save the updated task
            taskRepository.save(task);

            // Return success response
            response.setSuccessful("Task Updated Successfully");
        } catch (Exception e) {
            logger.error("An Error happened while updating task", e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
    private void updateTaskFields(Task task, UpdateTaskRequest request) {
        task.setUpdatedAt(DateService.getCurrentTimestamp());
        task.setTitle(request.getTitle() != null ? request.getTitle() : task.getTitle());
        task.setDescription(request.getDescription() != null ? request.getDescription() : task.getDescription());
        task.setStartDate(request.getStartDate() != null ? request.getStartDate() : task.getStartDate());
        task.setDueDate(request.getDueDate() != null ? request.getDueDate() : task.getDueDate());

        // Handle status update
        if (request.getStatus() != null && request.getStatus() == Status.COMPLETED && task.getStatus() != Status.COMPLETED) {
            task.setCompletionDate(DateService.getCurrentDate());
            if (task.getDueDate().before(DateService.getCurrentDate())) {
                task.setOverdue(true);
            }
        }
    }

    private ResponseEntity<GenericResponse> buildErrorResponse(GenericResponse response, Runnable errorSetter) {
        errorSetter.run();
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
