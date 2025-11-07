package com.projectmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 100)
    private String position;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Min(value = 0, message = "Salary must be positive")
    private Double salary;

    @ManyToMany(mappedBy = "employees")
    @JsonBackReference(value = "team-employees")
    private Set<Team> teams = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    @JsonBackReference(value = "task-employees")
    private Set<Task> tasks = new HashSet<>();
}