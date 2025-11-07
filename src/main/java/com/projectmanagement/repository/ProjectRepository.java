package com.projectmanagement.repository;

import com.projectmanagement.model.Project;
import com.projectmanagement.model.Project.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Method name convention queries
    List<Project> findByStatus(ProjectStatus status);

    List<Project> findByTeamId(Long teamId);

    List<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    List<Project> findByBudgetGreaterThan(Double budget);

    // JPQL queries
    @Query("SELECT p FROM Project p WHERE p.status = :status AND p.team.id = :teamId")
    List<Project> findByStatusAndTeamId(@Param("status") ProjectStatus status, @Param("teamId") Long teamId);

    @Query("SELECT p FROM Project p WHERE p.endDate < :currentDate AND p.status != 'COMPLETED'")
    List<Project> findOverdueProjects(@Param("currentDate") LocalDate currentDate);
}