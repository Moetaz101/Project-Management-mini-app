package com.projectmanagement.repository;

import com.projectmanagement.model.Employee;
import com.projectmanagement.model.Task;
import com.projectmanagement.model.Task.TaskPriority;
import com.projectmanagement.model.Task.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Method name convention queries
    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByProjectId(Long projectId);

    List<Task> findByEmployeesContaining(Employee employee);

    List<Task> findByDueDateBefore(LocalDate date);

    // JPQL queries
    @Query("SELECT t FROM Task t WHERE t.priority = :priority AND t.status = :status")
    List<Task> findByPriorityAndStatus(@Param("priority") TaskPriority priority, @Param("status") TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.status != 'DONE'")
    List<Task> findIncompleteTasksByProject(@Param("projectId") Long projectId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.id = :projectId AND t.status = :status")
    Long countByProjectIdAndStatus(@Param("projectId") Long projectId, @Param("status") TaskStatus status);
}