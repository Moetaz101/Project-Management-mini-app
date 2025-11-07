package com.projectmanagement.controller;

import com.projectmanagement.model.Project;
import com.projectmanagement.model.Project.ProjectStatus;
import com.projectmanagement.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project createdProject = projectService.create(project);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.findAll();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody Project project) {
        Project updatedProject = projectService.update(id, project);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/team/{teamId}")
    public ResponseEntity<Project> assignProjectToTeam(
            @PathVariable Long projectId,
            @PathVariable Long teamId) {
        Project project = projectService.assignToTeam(projectId, teamId);
        return ResponseEntity.ok(project);
    }

    @PatchMapping("/{projectId}/status")
    public ResponseEntity<Project> updateProjectStatus(
            @PathVariable Long projectId,
            @RequestParam ProjectStatus status) {
        Project project = projectService.updateStatus(projectId, status);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Project>> getProjectsByTeam(@PathVariable Long teamId) {
        List<Project> projects = projectService.findByTeamId(teamId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Project>> getProjectsByStatus(@PathVariable ProjectStatus status) {
        List<Project> projects = projectService.findByStatus(status);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Project>> getOverdueProjects() {
        List<Project> projects = projectService.findOverdueProjects();
        return ResponseEntity.ok(projects);
    }
}