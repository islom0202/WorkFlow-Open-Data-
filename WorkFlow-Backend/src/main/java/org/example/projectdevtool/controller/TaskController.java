package org.example.projectdevtool.controller;

import lombok.AllArgsConstructor;
import org.example.projectdevtool.dto.AssignTaskToEmployeeDto;
import org.example.projectdevtool.dto.RateTaskDto;
import org.example.projectdevtool.dto.TaskRequestDto;
import org.example.projectdevtool.dto.TaskResponse;
import org.example.projectdevtool.entity.Task;
import org.example.projectdevtool.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {


    private final TaskService taskService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequestDto dto) {
        Task task = taskService.createTask(dto);
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getProject().getName(),
                task.getAssignedTo().getUser().getEmail(),
                task.getStatus(),
                task.getPrior().toString(),
                task.getProject().getOwner().getId(),
                task.getScore()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assignTo-emp")
    public ResponseEntity<TaskResponse> assignTaskToEmployee(@RequestBody AssignTaskToEmployeeDto dto) {
        TaskResponse response = taskService.assignTask(dto);
        simpMessagingTemplate.convertAndSend("/task/message", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listBy-proId/{projectId}/{employeeId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProjectId(@PathVariable("projectId") Long projectId,
                                                                  @PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.ok(taskService.getTasksByProjectId(projectId, employeeId));
    }

    @PatchMapping("/update-status")
    public ResponseEntity<TaskResponse> updateStatusTask(@RequestParam("id") Long id,
                                              @RequestParam("status") String status) {
        TaskResponse task = taskService.updateStatusTask(id, status);
        simpMessagingTemplate.convertAndSend("/task/message", task);
        simpMessagingTemplate.convertAndSend("/emp-task/message", task);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/getAll/{empId}/{projectId}") //
    public ResponseEntity<List<TaskResponse>> getAll(@PathVariable("empId") Long empId,
                                    @PathVariable("projectId") Long projectId) {
        return ResponseEntity.ok(taskService.getAll(empId, projectId));
    }

    @PatchMapping("/rate/{projectOwnerId}")
    public ResponseEntity<TaskResponse> rateTask(@PathVariable("projectOwnerId") Long ownerId,
                                      @RequestBody RateTaskDto dto) {
        TaskResponse response = taskService.rateTask(ownerId, dto);
        simpMessagingTemplate.convertAndSend("/task/message", response);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/moveToBacklog/{taskId}")
    public ResponseEntity<TaskResponse> moveToBacklog(@PathVariable("taskId") Long taskId){
        TaskResponse response = taskService.moveToBacklog(taskId);
        simpMessagingTemplate.convertAndSend("/emp-task/message", response);
        simpMessagingTemplate.convertAndSend("/task/message", response);
        return ResponseEntity.ok(response);
    }
}
