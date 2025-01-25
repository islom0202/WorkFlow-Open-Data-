//package org.example.projectdevtool.components;
//
//import lombok.AllArgsConstructor;
//import org.example.projectdevtool.entity.Project;
//import org.example.projectdevtool.repo.ProjectRepo;
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
//public class ProjectNotificationScheduler {
//
//    private final ProjectRepo projectRepo;
//    private final EmailService emailService;
//
//    @Scheduled(fixedDelay = 180000) // Runs every 3 min
//    public void sendProjectStartNotifications() {
//        LocalDate today = LocalDate.now();
//        List<Project> projectsStartingToday = projectRepo.findByStartDate(today);
//        String text = "Project '%s' is starting today. Please check your tasks.";
//        String subject = "Project Start Notification";
//
//        for (Project project : projectsStartingToday) {
//            emailService.notifyEmployees(project.getOwner(),
//                    text,
//                    subject,
//                    project.getName());
//        }
//    }
//}
