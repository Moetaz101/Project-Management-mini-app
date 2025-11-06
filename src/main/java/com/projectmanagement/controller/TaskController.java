package com.projectmanagement.controller;

import com.projectmanagement.model.Task;
import com.projectmanagement.model.Task.TaskPriority;
import com.projectmanagement.model.Task.TaskStatus;
import com.projectmanagement.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task createdTask = taskService.create(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody Task task) {
        Task updatedTask = taskService.update(id, task);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/project/{projectId}")
    public ResponseEntity<Task> assignTaskToProject(
            @PathVariable Long taskId,
            @PathVariable Long projectId) {
        Task task = taskService.assignToProject(taskId, projectId);
        return ResponseEntity.ok(task);
    }

    @PostMapping("/{taskId}/employees/{employeeId}")
    public ResponseEntity<Task> assignEmployeeToTask(
            @PathVariable Long taskId,
            @PathVariable Long employeeId) {
        Task task = taskService.assignEmployeeToTask(taskId, employeeId);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}/employees/{employeeId}")
    public ResponseEntity<Task> removeEmployeeFromTask(
            @PathVariable Long taskId,
            @PathVariable Long employeeId) {
        Task task = taskService.removeEmployeeFromTask(taskId, employeeId);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {
        Task task = taskService.updateStatus(taskId, status);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<Task> updateTaskPriority(
            @PathVariable Long taskId,
            @RequestParam TaskPriority priority) {
        Task task = taskService.updatePriority(taskId, priority);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        List<Task> tasks = taskService.findByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Long employeeId) {
        List<Task> tasks = taskService.findByEmployeeId(employeeId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskService.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority) {
        List<Task> tasks = taskService.findByPriority(priority);
        return ResponseEntity.ok(tasks);
    }
}