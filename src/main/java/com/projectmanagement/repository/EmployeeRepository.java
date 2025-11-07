package com.projectmanagement.repository;

import com.projectmanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Method name convention queries
    Optional<Employee> findByEmail(String email);

    List<Employee> findByPosition(String position);

    List<Employee> findByHireDateAfter(LocalDate date);

    List<Employee> findByFirstNameContainingOrLastNameContainingIgnoreCase(String firstName, String lastName);

    List<Employee> findBySalaryBetween(Double minSalary, Double maxSalary);

    // JPQL queries
    @Query("SELECT e FROM Employee e WHERE SIZE(e.tasks) > :taskCount")
    List<Employee> findEmployeesWithMoreThanNTasks(@Param("taskCount") int taskCount);

    @Query("SELECT e FROM Employee e JOIN e.teams t WHERE t.id = :teamId")
    List<Employee> findByTeamId(@Param("teamId") Long teamId);

    @Query("SELECT e FROM Employee e JOIN e.tasks task WHERE task.id = :taskId")
    List<Employee> findByTaskId(@Param("taskId") Long taskId);
}