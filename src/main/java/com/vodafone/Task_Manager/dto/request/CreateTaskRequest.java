package com.vodafone.Task_Manager.dto.request;


import com.vodafone.Task_Manager.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CreateTaskRequest {

    @NotNull(message = "Title Is required")
    @NotBlank(message = "Title is required and cannot be blank.")
    @Size(max = 100, message = "Title cannot exceed 100 characters.")
    private String title;

    @NotBlank(message = "Description is required and cannot be blank.")
    @NotNull(message = "Description Is required")
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;

    @NotBlank(message = "Start Date Cannot be blank")
    @NotNull(message = "Start date is required.")
    private Date startDate;

    @NotBlank(message = "Due Date Cannot be blank")
    @NotNull(message = "Due date is required.")
    private Date dueDate;

    private Status status = Status.PENDING;
}
