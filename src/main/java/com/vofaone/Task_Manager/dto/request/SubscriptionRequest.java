package com.vofaone.Task_Manager.dto.request;

import com.vofaone.Task_Manager.enums.Frequency;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class SubscriptionRequest {
    @NotNull(message = "Start date is required.")
    @FutureOrPresent(message = "Start Date cannot be in the Past")
    private Date startDate;

    @NotNull(message = "Frequency is required.")
    private Frequency frequency;

    @Min(value = 0, message = "Report time must be between 0 and 23.")
    @Max(value = 23, message = "Report time must be between 0 and 23.")
    private int reportTime; // Only hours (00 to 23)
}