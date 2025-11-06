package com.projectmanagement.service;

import com.projectmanagement.exception.ResourceNotFoundException;
import com.projectmanagement.model.Employee;
import com.projectmanagement.model.Project;
import com.projectmanagement.model.Task;
import com.projectmanagement.model.Task.TaskPriority;
import com.projectmanagement.model.Task.TaskStatus;
import com.projectmanagement.repository.EmployeeRepository;
import com.projectmanagement.repository.ProjectRepository;
import com.projectmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Task create(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(TaskStatus.TODO);
        }
        if (task.getPriority() == null) {
            task.setPriority(TaskPriority.MEDIUM);
        }
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id));
    }

    @Transactional(readOnly = true)
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Task update(Long id, Task taskDetails) {
        Task task = findById(id);

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setEstimatedHours(taskDetails.getEstimatedHours());
        task.setDueDate(taskDetails.getDueDate());

        if (taskDetails.getPriority() != null) {
            task.setPriority(taskDetails.getPriority());
        }
        if (taskDetails.getStatus() != null) {
            task.setStatus(taskDetails.getStatus());
        }

        return taskRepository.save(task);
    }

    public void delete(Long id) {
        Task task = findById(id);
        taskRepository.delete(task);
    }

    // Custom business methods
    public Task assignToProject(Long taskId, Long projectId) {
        Task task = findById(taskId);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        task.setProject(project);
        return taskRepository.save(task);
    }

    public Task assignEmployeeToTask(Long taskId, Long employeeId) {
        Task task = findById(taskId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        task.getEmployees().add(employee);
        return taskRepository.save(task);
    }

    public Task removeEmployeeFromTask(Long taskId, Long employeeId) {
        Task task = findById(taskId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));

        task.getEmployees().remove(employee);
        return taskRepository.save(task);
    }

    public Task updateStatus(Long taskId, TaskStatus status) {
        Task task = findById(taskId);
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task updatePriority(Long taskId, TaskPriority priority) {
        Task task = findById(taskId);
        task.setPriority(priority);
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> findByProjectId(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Transactional(readOnly = true)
    public List<Task> findByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        return taskRepository.findByEmployeesContaining(employee);
    }

    @Transactional(readOnly = true)
    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Task> findByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority);
    }
}