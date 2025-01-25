package org.example.projectdevtool.controller;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.ProfileRequestDto;
import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.service.EmailService;
import org.example.projectdevtool.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emp")
@AllArgsConstructor
public class EmployeeController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/invite")
    public ResponseEntity<?> addEmployeesList(@RequestBody List<String> emails){
        String subject = "ProDevTool invitation";
        String link = "";
        String text = "you have been invited to ProDevTool \nregister via link: " + link;
        emailService.sendEmailInvitation(emails, subject, text);
        return ResponseEntity.ok("sent");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Profile>> getAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/findBy/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable("email") String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping("/fill-profile")
    public ResponseEntity<?> fillProfile(@RequestBody ProfileRequestDto dto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         return ResponseEntity.ok(userService.fillProfile(dto, userDetails.getUsername()));
    }

    @GetMapping("/get-profile")
    public ResponseEntity<?> getMyProfile(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.of(userService.myProfile(userDetails.getUsername()));
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findById(id));
    }
}
