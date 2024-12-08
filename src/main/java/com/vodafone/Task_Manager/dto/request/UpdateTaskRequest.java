package com.vodafone.Task_Manager.dto.request;


import com.vodafone.Task_Manager.enums.Status;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UpdateTaskRequest {
    int taskId;

    @Size(max = 100, message = "Title cannot exceed 100 characters.")
    private String title;
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;
    private Date startDate;
    private Date dueDate;
    private Status status;

}
