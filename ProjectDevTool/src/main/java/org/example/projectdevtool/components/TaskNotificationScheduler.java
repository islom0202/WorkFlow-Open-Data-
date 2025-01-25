//package org.example.projectdevtool.components;
//
//import lombok.AllArgsConstructor;
//import org.example.projectdevtool.entity.Task;
//import org.example.projectdevtool.repo.TaskRepo;
//import org.example.projectdevtool.service.EmailService;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@EnableScheduling
//@Component
//@AllArgsConstructor
//public class TaskNotificationScheduler {
//
//    private final TaskRepo taskRepo;
//    private final EmailService emailService;
//
//    @Scheduled(fixedDelay = 120000) // Runs every 2 min
//    public void sendTaskStartTimeNotifications() {
//        LocalDate today = LocalDate.now();
//        List<Task> tasksForToday = taskRepo.findByStartDate(today);
//        String text = "Task '%s' is starting today. Please check your account.";
//        String subject = "Task Start Notification";
//
//        for (Task task : tasksForToday) {
//            emailService.notifyEmployees(task.getAssignedTo(),
//                    text,
//                    subject,
//                    task.getName());
//        }
//    }
//}
