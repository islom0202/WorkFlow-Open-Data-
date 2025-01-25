package org.example.projectdevtool.controller;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.EmployeeToProject;
import org.example.projectdevtool.dto.ProjectRequestDto;
import org.example.projectdevtool.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/pro")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto dto) {
        return ResponseEntity.ok(projectService.createProject(dto));
    }

    @PostMapping("/add-emp")
    public ResponseEntity<?> addEmployeesToProject(@RequestBody EmployeeToProject dto) {
        return ResponseEntity.ok(projectService.addEmployees(dto));
    }

    @GetMapping("/get-list")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(projectService.getAll());
    }

    @PatchMapping("/update-status")
    public ResponseEntity<?> updateProjectStatus(@RequestParam("id") Long id,
                                                 @RequestParam("status") String status) {
        return ResponseEntity.ok(projectService.updateStatus(id, status));
    }

    @PatchMapping("/delay/{id}")
    public ResponseEntity<?> delayProjectTime(@PathVariable("id") Long id,
                                              @RequestParam("startDate") LocalDate startDate,
                                              @RequestParam(value = "endDate", required = false) LocalDate endDate) {
        return ResponseEntity.ok(projectService.delayProjectTime(id, startDate, endDate));
    }

    @GetMapping("/list-today")
    public ResponseEntity<?> listOfTodayProjects() {
        return ResponseEntity.ok(projectService.getOfToday());
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.findById(id));
    }

    @GetMapping("/findBy/empId/{id}")
    public ResponseEntity<?> findByEmployeeList(@PathVariable("id") Long id) {
        return ResponseEntity.ok(projectService.findByEmployeeId(id));
    }

    @GetMapping("/emp-list/{projectId}/{ownerId}")
    public ResponseEntity<?> getEmployeeList(@PathVariable("projectId") Long projectId,
                                             @PathVariable("ownerId") Long ownerId){
            return ResponseEntity.ok(projectService.getEmployeeList(projectId, ownerId));
    }


    @GetMapping("/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(projectService.findAll());
    }

}