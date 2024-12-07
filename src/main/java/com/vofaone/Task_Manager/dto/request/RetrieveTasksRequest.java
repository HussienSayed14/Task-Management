package com.vofaone.Task_Manager.dto.request;


import com.vofaone.Task_Manager.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class RetrieveTasksRequest {
    private Status status; // Optional: Pending, Completed, Overdue

    private Date startDate;

    private Date endDate; // Optional: Filter tasks up to this date
}
