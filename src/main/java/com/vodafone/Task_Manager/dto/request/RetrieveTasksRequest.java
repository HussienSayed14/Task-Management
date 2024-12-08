package com.vodafone.Task_Manager.dto.request;


import com.vodafone.Task_Manager.enums.Status;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class RetrieveTasksRequest {
    @Pattern(regexp = "PENDING|COMPLETED|OVERDUE", message = "Invalid status value")
    private Status status; // Optional: Pending, Completed, Overdue

    private Date startDate;

    private Date endDate; // Optional: Filter tasks up to this date
}
