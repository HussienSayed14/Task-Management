package com.vodafone.Task_Manager.service;

import com.vodafone.Task_Manager.entity.Task;
import com.vodafone.Task_Manager.enums.Status;
import com.vodafone.Task_Manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskStatusUpdater {
    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskStatusUpdater.class);


    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    public void updateOverdueTasks() {
        try {
            logger.info("Scheduled Task Updated started");
            // Get today's date
            Date today = new Date(System.currentTimeMillis());

            // Find all pending tasks where the start date is after the due date
            List<Task> tasks = taskRepository.findPendingOverdueTasks(today);

            // Update their status to OVERDUE
            tasks.forEach(task -> {
                task.setStatus(Status.OVERDUE);
                task.setOverdue(true);
            });

            // Save updated tasks
            taskRepository.saveAll(tasks);

        } catch (Exception e) {
            logger.error("An error happened while updating tasks status: ", e.getMessage());
            e.printStackTrace();

        }
    }
}
