package org.example.projectdevtool.service;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.entity.Profile;
import org.hibernate.annotations.Array;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final String from = "turgunpolatovislom5@gmail.com";

    @Async
    public void sendEmailInvitation(List<String> emails, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emails.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }

    @Async
    public void sendNotification(List<String> emails, String projectName) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(emails.toArray(new String[0]));
        message.setSubject("you joined " + projectName);
        message.setText("you have been joined " + projectName + " in ProDevTool\nplease visit your account!");

        javaMailSender.send(message);
    }

    @Async
    public void notifyEmployees(Profile owner, String text, String subject, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        String text1 = String.format(text, name);

        message.setFrom(from);
        message.setTo(owner.getUser().getEmail());
        message.setSubject(subject);
        message.setText(text1);

        javaMailSender.send(message);
    }

    @Async
    public void notifyForTaskAssignment(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);
    }
}
