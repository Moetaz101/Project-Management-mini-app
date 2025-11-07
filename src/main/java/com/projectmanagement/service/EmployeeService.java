package com.projectmanagement.service;

import com.projectmanagement.exception.ResourceNotFoundException;
import com.projectmanagement.model.Employee;
import com.projectmanagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee create(Employee employee) {
        // Check if email already exists
        employeeRepository.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        });

        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public Employee update(Long id, Employee employeeDetails) {
        Employee employee = findById(id);

        // Check if new email already exists (excluding current employee)
        if (!employee.getEmail().equals(employeeDetails.getEmail())) {
            employeeRepository.findByEmail(employeeDetails.getEmail()).ifPresent(e -> {
                throw new IllegalArgumentException("Email already exists: " + employeeDetails.getEmail());
            });
        }

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setPosition(employeeDetails.getPosition());
        employee.setHireDate(employeeDetails.getHireDate());
        employee.setSalary(employeeDetails.getSalary());

        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

    // Custom business methods
    @Transactional(readOnly = true)
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "email", email));
    }

    @Transactional(readOnly = true)
    public List<Employee> findByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    @Transactional(readOnly = true)
    public List<Employee> findByTeamId(Long teamId) {
        return employeeRepository.findByTeamId(teamId);
    }

    @Transactional(readOnly = true)
    public List<Employee> searchByName(String name) {
        return employeeRepository.findByFirstNameContainingOrLastNameContainingIgnoreCase(name, name);
    }

    @Transactional(readOnly = true)
    public List<Employee> findBySalaryRange(Double minSalary, Double maxSalary) {
        return employeeRepository.findBySalaryBetween(minSalary, maxSalary);
    }
}