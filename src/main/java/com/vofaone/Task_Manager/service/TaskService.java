package com.vofaone.Task_Manager.service;

import com.vofaone.Task_Manager.config.JwtService;
import com.vofaone.Task_Manager.dto.request.CreateTaskRequest;
import com.vofaone.Task_Manager.entity.Task;
import com.vofaone.Task_Manager.entity.User;
import com.vofaone.Task_Manager.repository.TaskRepository;
import com.vofaone.Task_Manager.util.GenericResponse;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


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
            Task task = Task.builder()
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

            taskRepository.save(task);
            response.setSuccessful("Task Created Successfully");
        }catch (Exception e){
            response.setServerError();
            logger.error("An Error happened while creating a task" + "\n" +
                    "Error Message: " + e.getMessage());
            e.printStackTrace();

        }
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
