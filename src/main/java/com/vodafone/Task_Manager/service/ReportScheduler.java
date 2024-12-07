package com.vodafone.Task_Manager.service;

import com.vodafone.Task_Manager.entity.Subscription;
import com.vodafone.Task_Manager.entity.Task;
import com.vodafone.Task_Manager.repository.SubscriptionRepository;
import com.vodafone.Task_Manager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportScheduler {
    private final SubscriptionRepository subscriptionRepository;
    private final TaskRepository taskRepository;
    private final EmailService emailService;

//    @Scheduled(cron = "0 0 * * * ?") // Every hour
    @Scheduled(cron = "0 */5 * * * ?") // Every 5 minutes
    public void generateAndSendReports() {
        int currentHour = LocalTime.now().getHour();
        Date today = Date.valueOf(LocalDate.now());


        // Fetch subscriptions for the current hour
        List<Subscription> subscriptions = subscriptionRepository.findByReportTime(currentHour, today);

        for (Subscription subscription : subscriptions) {
            // Determine if the email should be sent for this subscription
            boolean shouldSend = shouldSendEmail(subscription, today);

            if(shouldSend){
                // Fetch tasks based on frequency
                List<Task> tasks = fetchTasksForSubscription(subscription, today);

                // Generate the HTML report
                String reportHtml = generateHtmlReport(tasks);

                // Send email
                emailService.sendEmail(subscription.getUser().getEmail(), "Your Task Report", reportHtml);

                // Update last sent date
                subscription.setLastReportDate(today);
                subscriptionRepository.save(subscription);
            }
        }
    }

    private List<Task> fetchTasksForSubscription(Subscription subscription, Date today) {
        Date startDate;
        switch (subscription.getFrequency()) {
            case DAILY:
                startDate = Date.valueOf(today.toLocalDate().minusDays(1));
                break;
            case WEEKLY:
                startDate = Date.valueOf(today.toLocalDate().minusDays(7));
                break;
            case MONTHLY:
                startDate = Date.valueOf(today.toLocalDate().minusDays(30));
                break;
            default:
                throw new IllegalArgumentException("Invalid frequency: " + subscription.getFrequency());
        }

        return taskRepository.findTasksByEndDateBetweenAndUserId(startDate, today, subscription.getUser().getId());
    }

    private boolean shouldSendEmail(Subscription subscription, Date today) {
        // If no email has been sent yet
        if (subscription.getLastReportDate() == null) {
            return true;
        }

        // Calculate the next allowed send date based on frequency
        LocalDate lastSentDate = subscription.getLastReportDate().toLocalDate();
        LocalDate nextSendDate;

        switch (subscription.getFrequency()) {
            case DAILY:
                nextSendDate = lastSentDate.plusDays(1);
                break;
            case WEEKLY:
                nextSendDate = lastSentDate.plusDays(7);
                break;
            case MONTHLY:
                nextSendDate = lastSentDate.plusDays(30);
                break;
            default:
                throw new IllegalArgumentException("Invalid frequency: " + subscription.getFrequency());
        }

        return !today.toLocalDate().isBefore(nextSendDate); // Send if today is on or after the next send date
    }


    private String generateHtmlReport(List<Task> tasks) {
        StringBuilder html = new StringBuilder();
        html.append("<html><body>");
        html.append("<h1>Task Report</h1>");
        html.append("<table border='1'>");
        html.append("<tr><th>Title</th><th>Status</th><th>Due Date</th><th>Description</th></tr>");

        for (Task task : tasks) {
            html.append("<tr>")
                    .append("<td>").append(task.getTitle()).append("</td>")
                    .append("<td>").append(task.getStatus()).append("</td>")
                    .append("<td>").append(task.getDueDate()).append("</td>")
                    .append("<td>").append(task.getDescription() == null ? "N/A" : task.getDescription()).append("</td>")
                    .append("</tr>");
        }

        html.append("</table>");
        html.append("</body></html>");
        return html.toString();
    }
}
