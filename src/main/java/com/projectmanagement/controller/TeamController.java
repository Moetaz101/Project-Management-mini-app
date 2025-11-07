package com.projectmanagement.controller;

import com.projectmanagement.model.Team;
import com.projectmanagement.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
        Team createdTeam = teamService.create(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.findAll();
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.findById(id);
        return ResponseEntity.ok(team);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team team) {
        Team updatedTeam = teamService.update(id, team);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/employees/{employeeId}")
    public ResponseEntity<Team> addEmployeeToTeam(
            @PathVariable Long teamId,
            @PathVariable Long employeeId) {
        Team team = teamService.addEmployeeToTeam(teamId, employeeId);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping("/{teamId}/employees/{employeeId}")
    public ResponseEntity<Team> removeEmployeeFromTeam(
            @PathVariable Long teamId,
            @PathVariable Long employeeId) {
        Team team = teamService.removeEmployeeFromTeam(teamId, employeeId);
        return ResponseEntity.ok(team);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Team>> searchTeams(@RequestParam String name) {
        List<Team> teams = teamService.searchByName(name);
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Team>> getTeamsByEmployee(@PathVariable Long employeeId) {
        List<Team> teams = teamService.findTeamsByEmployee(employeeId);
        return ResponseEntity.ok(teams);
    }
}