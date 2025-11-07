package com.projectmanagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Team name is required")
    @Size(max = 100, message = "Team name must not exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "team-projects")
    private List<Project> projects = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "team_employees",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    @JsonManagedReference(value = "team-employees")
    private Set<Employee> employees = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDate.now();
        }
    }
}