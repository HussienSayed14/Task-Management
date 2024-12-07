package com.vofaone.Task_Manager.repository;

import com.vofaone.Task_Manager.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Query(value = "SELECT s FROM Subscription s WHERE s.user.id =:userId")
    Subscription findByUserId(int userId);


    @Query("SELECT s FROM Subscription s WHERE s.reportTime = :reportTime AND s.startDate <= :today")
    List<Subscription> findByReportTime(int currentHour, Date today);
}
