package com.vodafone.Task_Manager.entity;


import com.vodafone.Task_Manager.enums.Frequency;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "subscription", indexes = {
        @Index(name = "idx_report_time", columnList = "report_time, start_date")})

public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Frequency frequency;
    @Column(name = "report_time", nullable = false)
    private int reportTime;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "last_report_date")
    private Date lastReportDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


}
