package com.vofaone.Task_Manager.repository;

import com.vofaone.Task_Manager.entity.Task;
import com.vofaone.Task_Manager.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.user.id = :userId " +
            "AND t.isDeleted = false " +
            "AND (:status IS NULL OR t.status = :status) " +
            "AND (:startDate IS NULL OR t.startDate >= :startDate) " +
            "AND (:dueDate IS NULL OR t.dueDate <= :dueDate)")
    List<Task> findTasksByFilters(
            int userId,
            Status status,
            Date startDate,
            Date dueDate);

    @Query("SELECT t FROM Task t WHERE t.id =:taskId")
    Task findTaskById(int taskId);
}
