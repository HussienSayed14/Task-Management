package com.vodafone.Task_Manager.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vodafone.Task_Manager.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "task", indexes = {
        @Index(name = "idx_user_not_deleted", columnList = "user_id,is_deleted"),
        @Index(name = "idx_start_date", columnList = "start_date"),
        @Index(name = "idx_due_date", columnList = "due_date"),

})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 100, nullable = false)
    private String description;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "due_date", nullable = false)
    private Date dueDate;
    @Column(name = "completion_date")
    private Date completionDate;
    @Column(nullable = false)
    @Enumerated
    private Status status;
    @Column(name = "is_deleted")
    @JsonIgnore
    private boolean isDeleted;
    @Column(name = "is_overdue")
    private boolean isOverdue;
    @Column(name = "creation_date")
    private Timestamp createdAt;
    @Column(name = "update_date")
    private Timestamp updatedAt;
    @Column(name = "deletion_date")
    @JsonIgnore
    private Timestamp deletedAt;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user created the task


}
