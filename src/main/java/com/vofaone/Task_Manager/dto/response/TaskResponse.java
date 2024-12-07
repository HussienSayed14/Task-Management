package com.vofaone.Task_Manager.dto.response;

import com.vofaone.Task_Manager.entity.Task;
import com.vofaone.Task_Manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class TaskResponse {
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date dueDate;
    private Date completionDate;
    private Status status;
    private boolean isOverdue;
    private Timestamp creationDate;

    public TaskResponse(Task task){
        this.id = task.getUser().getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.startDate =  task.getStartDate();
        this.dueDate = task.getDueDate();
        this.completionDate = task.getCompletionDate();
        this.status = task.getStatus();
        this.creationDate = task.getCreatedAt();

    }
}
