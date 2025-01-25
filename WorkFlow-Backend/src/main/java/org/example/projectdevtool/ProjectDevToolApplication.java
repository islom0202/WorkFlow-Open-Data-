package org.example.projectdevtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
public class ProjectDevToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDevToolApplication.class, args);
    }

}
