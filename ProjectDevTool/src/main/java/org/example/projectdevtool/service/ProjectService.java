package org.example.projectdevtool.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.EmployeeToProject;
import org.example.projectdevtool.dto.ProjectRequestDto;
import org.example.projectdevtool.dto.ProjectResponse;
import org.example.projectdevtool.entity.Profile;
import org.example.projectdevtool.entity.Project;
import org.example.projectdevtool.entity.Status;
import org.example.projectdevtool.entity.Users;
import org.example.projectdevtool.repo.ProfileRepo;
import org.example.projectdevtool.repo.ProjectRepo;
import org.example.projectdevtool.repo.UsersRepo;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProjectService {

    private final ProjectRepo projectRepo;
    private final ProfileRepo profileRepo;
    private final EmailService emailService;
    private final UsersRepo usersRepo;

    public Project createProject(ProjectRequestDto dto) {
        Project project = new Project();

        Users user = usersRepo.findById(dto.getOwnerId())
                        .orElseThrow(()->new NoSuchElementException("not found"));

        if (!(user.getRole().toString().equals("PM") || user.getRole().toString().equals("DIRECTOR"))) {
            throw new RuntimeException("Permission denied");
        }

        Profile profile = profileRepo.findById(dto.getOwnerId())
                        .orElseThrow(()->new NoSuchElementException("not found"));

        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setBudget(dto.getBudget());
        project.setOwner(profile);

        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must not be before start date.");
        }

        project.setStartDate(startDate);
        project.setEndDate(endDate);

        if (startDate.isBefore(LocalDate.now())) {
            project.setStatus(Status.PENDING);
        } else if (startDate.isEqual(LocalDate.now())) {
            project.setStatus(Status.IN_PROGRESS);
        } else {
            project.setStatus(Status.PENDING);
        }
        project.setRate(0);

        return projectRepo.save(project);
    }


    @Transactional
    public Project addEmployees(EmployeeToProject dto) {
        Project project = projectRepo.findById(dto.getProjectId()).
                orElseThrow(() -> new NoSuchElementException("not found"));

        Profile owner = profileRepo.findById(dto.getOwnerId()).
                orElseThrow(() -> new NoSuchElementException("not found"));

        List<Profile> employees = profileRepo.findAllById(dto.getEmployeesId());

        project.setOwner(owner);
        project.setEmployees(employees);

        List<String> emails = employees.stream()
                .map(Profile::getUser)
                .map(Users::getEmail)
                .toList();

        emailService.sendNotification(emails, project.getName());
//        setProjectToEmployeesProfile(owner, employees, project); // once employees have been added to the project, the project will be set into these employees`s profile
        return projectRepo.save(project);
    }

//    private void setProjectToEmployeesProfile(Profile owner, List<Profile> employees, Project project) {
//        List<Project> projects = new LinkedList<>();
//        owner.setProject(projects);
//        profileRepo.save(owner);
//
//        for (Profile profile : employees) {
//            profile.setProject(projects);
//            profileRepo.save(profile);
//        }
//    }

    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    @Transactional

    public Project updateStatus(Long id, String status) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found"));

        switch (status) {
            case "START" -> {
                project.setStatus(Status.IN_PROGRESS);
                List<String> emails = project.getEmployees().stream()
                        .map(Profile::getUser)
                        .map(Users::getEmail)
                        .toList();
                emailService.sendEmailInvitation(emails,
                        "project " + project.getName() + " started",
                        "project " + project.getName() + " has been started so please check out your account!");
            }
            case "CANCELLED" -> project.setStatus(Status.CANCELLED);
            case "FINISH" -> project.setStatus(Status.COMPLETED);
        }

        return projectRepo.save(project);
    }

    public Project delayProjectTime(Long id, LocalDate startDate, LocalDate endDate) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found"));

        if (startDate != null) {
            project.setStartDate(startDate);
        }
        if (endDate != null) {
            project.setEndDate(endDate);
        }

        return projectRepo.save(project);
    }

    public List<Project> getOfToday() {
        return projectRepo.findByStartDateOrEndDate(LocalDate.now(), LocalDate.now());
    }

    public Project findById(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found"));
    }

    public List<Project> findByEmployeeId(Long id) {
        Users user = usersRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        Profile profile = profileRepo.findByUser(user);

        return switch (user.getRole().toString()) {
            case "EMPLOYEE" -> projectRepo.findByEmployee(profile);
            case "PM", "DIRECTOR" -> projectRepo.findByOwner(profile.getId());
            default -> throw new IllegalArgumentException("Invalid role: " + user.getRole());
        };
    }

    public List<Profile> getEmployeeList(Long projectId, Long ownerId) {
        Users user = usersRepo.findById(ownerId)
                .orElseThrow(() -> new NoSuchElementException("not found"));

        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new NoSuchElementException("not found"));

        if (project.getOwner().getUser().equals(user)) {
            return project.getEmployees();
        } else
            throw new RuntimeException("permission denied");
    }

    public List<ProjectResponse> findAll() {
        List<Project> projects = projectRepo.findAll();

        List<ProjectResponse> responses = new ArrayList<>();

        for(Project project: projects){
            ProjectResponse response = new ProjectResponse();
            response.setId(project.getId());
            response.setProjectName(project.getName());
            response.setOwnerName(project.getOwner().getUser().getEmail());

            responses.add(response);
        }

        return responses;
    }
}
