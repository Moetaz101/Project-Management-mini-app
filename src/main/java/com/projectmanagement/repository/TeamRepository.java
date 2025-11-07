package com.projectmanagement.repository;

import com.projectmanagement.model.Employee;
import com.projectmanagement.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Method name convention queries
    List<Team> findByNameContainingIgnoreCase(String name);

    List<Team> findByCreatedDateAfter(LocalDate date);

    List<Team> findByEmployeesContaining(Employee employee);

    // JPQL queries
    @Query("SELECT t FROM Team t WHERE SIZE(t.projects) > :projectCount")
    List<Team> findTeamsWithMoreThanNProjects(@Param("projectCount") int projectCount);

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.projects WHERE t.id = :id")
    Team findByIdWithProjects(@Param("id") Long id);
}