package com.vofaone.Task_Manager.repository;

import com.vofaone.Task_Manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
