package com.projectmanagement.service;

import com.projectmanagement.exception.ResourceNotFoundException;
import com.projectmanagement.model.Employee;
import com.projectmanagement.model.Team;
import com.projectmanagement.repository.EmployeeRepository;
import com.projectmanagement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Team create(Team team) {
        if (team.getCreatedDate() == null) {
            team.setCreatedDate(LocalDate.now());
        }
        return teamRepository.save(team);
    }

    @Transactional(readOnly = true)
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team update(Long id, Team teamDetails) {
        Team team = findById(id);

        team.setName(teamDetails.getName());
        team.setDescription(teamDetails.getDescription());

        return teamRepository.save(team);
    }

    public void delete(Long id) {
        Team team = findById(id);
        teamRepository.delete(team);
    }

    // Custom business methods
    public Team addEmployeeToTeam(Long teamId, Long employeeId) {
        Team team = findById(teamId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        team.getEmployees().add(employee);
        return teamRepository.save(team);
    }

    public Team removeEmployeeFromTeam(Long teamId, Long employeeId) {
        Team team = findById(teamId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        team.getEmployees().remove(employee);
        return teamRepository.save(team);
    }

    @Transactional(readOnly = true)
    public List<Team> findTeamsByEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        return teamRepository.findByEmployeesContaining(employee);
    }

    @Transactional(readOnly = true)
    public List<Team> searchByName(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }
}