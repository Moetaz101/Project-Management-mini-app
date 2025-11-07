package com.projectmanagement.controller;

import com.projectmanagement.model.Employee;
import com.projectmanagement.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee createdEmployee = employeeService.create(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.update(id, employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Employee> getEmployeeByEmail(@RequestParam String email) {
        Employee employee = employeeService.findByEmail(email);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/position/{position}")
    public ResponseEntity<List<Employee>> getEmployeesByPosition(@PathVariable String position) {
        List<Employee> employees = employeeService.findByPosition(position);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Employee>> getEmployeesByTeam(@PathVariable Long teamId) {
        List<Employee> employees = employeeService.findByTeamId(teamId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(@RequestParam String name) {
        List<Employee> employees = employeeService.searchByName(name);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/salary-range")
    public ResponseEntity<List<Employee>> getEmployeesBySalaryRange(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Employee> employees = employeeService.findBySalaryRange(min, max);
        return ResponseEntity.ok(employees);
    }
}