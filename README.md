# Project Management System - Spring Boot REST API

## Project Description

A comprehensive Project Management System built with Spring Boot that enables organizations to manage teams, projects, tasks, and employees efficiently. The system supports complex many-to-many relationships and provides a complete RESTful API for all operations.

## Technologies Used

- **Spring Boot 3.2.0**
- **Spring Data JPA** - Database operations
- **Spring Web** - REST API
- **Spring Validation** - Input validation
- **MySQL 8** - Database
- **Lombok** - Boilerplate code reduction
- **Maven** - Dependency management

## Entity Relationships

```
┌─────────────┐         ┌──────────────┐         ┌─────────────┐
│    Team     │ 1     * │   Project    │ 1     * │    Task     │
│             │────────▶│              │────────▶│             │
│  - id       │         │  - id        │         │  - id       │
│  - name     │         │  - name      │         │  - title    │
│  - desc     │         │  - budget    │         │  - priority │
│  - created  │         │  - startDate │         │  - status   │
└─────────────┘         │  - endDate   │         │  - dueDate  │
      ▲                 │  - status    │         └─────────────┘
      │                 └──────────────┘               ▲
      │                                                │
      │  Many-to-Many                    Many-to-Many │
      │                                                │
      └────────────────┐         ┌────────────────────┘
                       │         │
                  ┌────┴─────────┴───┐
                  │    Employee      │
                  │                  │
                  │  - id            │
                  │  - firstName     │
                  │  - lastName      │
                  │  - email         │
                  │  - position      │
                  │  - hireDate      │
                  │  - salary        │
                  └──────────────────┘
```

### Relationship Details:
- **Team ↔ Project**: One-to-Many (One team has many projects)
- **Project ↔ Task**: One-to-Many (One project has many tasks, cascading delete)
- **Team ↔ Employee**: Many-to-Many (Employees can belong to multiple teams)
- **Task ↔ Employee**: Many-to-Many (Tasks can be assigned to multiple employees)

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Postman (for API testing)

### Database Setup

1. Start MySQL server
2. Create database:
```sql
CREATE DATABASE project_management_db;
```

3. Update `application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application

1. Clone the repository
```bash
git clone <repository-url>
cd project-management-system
```

2. Build the project
```bash
mvn clean install
```

3. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints Documentation

### Team Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/teams` | Create a new team |
| GET | `/api/teams` | Get all teams |
| GET | `/api/teams/{id}` | Get team by ID |
| PUT | `/api/teams/{id}` | Update team |
| DELETE | `/api/teams/{id}` | Delete team |
| POST | `/api/teams/{teamId}/employees/{employeeId}` | Add employee to team |
| DELETE | `/api/teams/{teamId}/employees/{employeeId}` | Remove employee from team |
| GET | `/api/teams/search?name={name}` | Search teams by name |
| GET | `/api/teams/employee/{employeeId}` | Get teams by employee |

### Project Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/projects` | Create a new project |
| GET | `/api/projects` | Get all projects |
| GET | `/api/projects/{id}` | Get project by ID |
| PUT | `/api/projects/{id}` | Update project |
| DELETE | `/api/projects/{id}` | Delete project |
| PUT | `/api/projects/{projectId}/team/{teamId}` | Assign project to team |
| PATCH | `/api/projects/{projectId}/status?status={STATUS}` | Update project status |
| GET | `/api/projects/team/{teamId}` | Get projects by team |
| GET | `/api/projects/status/{status}` | Get projects by status |
| GET | `/api/projects/overdue` | Get overdue projects |

**Project Status Values**: `PLANNED`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`

### Task Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/tasks` | Create a new task |
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |
| PUT | `/api/tasks/{taskId}/project/{projectId}` | Assign task to project |
| POST | `/api/tasks/{taskId}/employees/{employeeId}` | Assign employee to task |
| DELETE | `/api/tasks/{taskId}/employees/{employeeId}` | Remove employee from task |
| PATCH | `/api/tasks/{taskId}/status?status={STATUS}` | Update task status |
| PATCH | `/api/tasks/{taskId}/priority?priority={PRIORITY}` | Update task priority |
| GET | `/api/tasks/project/{projectId}` | Get tasks by project |
| GET | `/api/tasks/employee/{employeeId}` | Get tasks by employee |
| GET | `/api/tasks/status/{status}` | Get tasks by status |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority |

**Task Status Values**: `TODO`, `IN_PROGRESS`, `DONE`  
**Task Priority Values**: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

### Employee Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/employees` | Create a new employee |
| GET | `/api/employees` | Get all employees |
| GET | `/api/employees/{id}` | Get employee by ID |
| PUT | `/api/employees/{id}` | Update employee |
| DELETE | `/api/employees/{id}` | Delete employee |
| GET | `/api/employees/email?email={email}` | Get employee by email |
| GET | `/api/employees/position/{position}` | Get employees by position |
| GET | `/api/employees/team/{teamId}` | Get employees by team |
| GET | `/api/employees/search?name={name}` | Search employees by name |
| GET | `/api/employees/salary-range?min={min}&max={max}` | Get employees by salary range |

## Postman Testing Guide

### 1. Create Team
**POST** `http://localhost:8080/api/teams`
```json
{
    "name": "Development Team",
    "description": "Main development team"
}
```

### 2. Create Employees
**POST** `http://localhost:8080/api/employees`
```json
{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@company.com",
    "position": "Senior Developer",
    "hireDate": "2023-01-15",
    "salary": 75000.00
}
```

### 3. Assign Employee to Team
**POST** `http://localhost:8080/api/teams/1/employees/1`

### 4. Create Project for Team
**POST** `http://localhost:8080/api/projects`
```json
{
    "name": "E-Commerce Platform",
    "description": "Building online shopping platform",
    "budget": 150000.00,
    "startDate": "2024-01-01",
    "endDate": "2024-12-31",
    "status": "IN_PROGRESS"
}
```

**Assign to Team**:  
**PUT** `http://localhost:8080/api/projects/1/team/1`

### 5. Create Tasks for Project
**POST** `http://localhost:8080/api/tasks`
```json
{
    "title": "Design Database Schema",
    "description": "Create ERD and design database tables",
    "priority": "HIGH",
    "status": "TODO",
    "estimatedHours": 16,
    "dueDate": "2024-02-15"
}
```

**Assign to Project**:  
**PUT** `http://localhost:8080/api/tasks/1/project/1`

### 6. Assign Employee to Task
**POST** `http://localhost:8080/api/tasks/1/employees/1`

### 7. Update Task Status
**PATCH** `http://localhost:8080/api/tasks/1/status?status=IN_PROGRESS`

### 8. Get Projects by Team
**GET** `http://localhost:8080/api/projects/team/1`

### 9. Get Tasks by Employee
**GET** `http://localhost:8080/api/tasks/employee/1`

### 10. Update Project Status
**PATCH** `http://localhost:8080/api/projects/1/status?status=COMPLETED`

## Error Handling

The API returns consistent error responses:

```json
{
    "timestamp": "2024-11-04T10:30:00",
    "status": 404,
    "error": "Not Found",
    "message": "Team not found with id : '999'",
    "path": "/api/teams/999"
}
```

### HTTP Status Codes
- `200 OK` - Successful GET, PUT, PATCH
- `201 Created` - Successful POST
- `204 No Content` - Successful DELETE
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

## Validation Rules

### Team
- Name: Required, max 100 characters

### Project
- Name: Required, max 150 characters
- Budget: Must be >= 0
- Start Date: Required

### Task
- Title: Required, max 200 characters

### Employee
- First Name: Required, max 50 characters
- Last Name: Required, max 50 characters
- Email: Required, valid email format, unique
- Salary: Must be >= 0

## Features

✅ Complete CRUD operations for all entities  
✅ Many-to-Many relationship management  
✅ Cascading delete for Project → Tasks  
✅ Email uniqueness validation  
✅ Custom query methods  
✅ Global exception handling  
✅ Input validation with detailed error messages  
✅ RESTful API design  
✅ Transaction management

## Project Structure

```
src/main/java/com/projectmanagement/
├── model/
│   ├── Team.java
│   ├── Project.java
│   ├── Task.java
│   └── Employee.java
├── repository/
│   ├── TeamRepository.java
│   ├── ProjectRepository.java
│   ├── TaskRepository.java
│   └── EmployeeRepository.java
├── service/
│   ├── TeamService.java
│   ├── ProjectService.java
│   ├── TaskService.java
│   └── EmployeeService.java
├── controller/
│   ├── TeamController.java
│   ├── ProjectController.java
│   ├── TaskController.java
│   └── EmployeeController.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── ProjectManagementApplication.java
```

## Testing Checklist

- [x] Create Team
- [x] Create multiple Projects for a Team
- [x] Create multiple Tasks for a Project
- [x] Create Employees
- [x] Assign Employees to Team (ManyToMany)
- [x] Assign Employees to Tasks (ManyToMany)
- [x] Update Project status
- [x] Update Task priority
- [x] Get all Projects by Team ID
- [x] Get all Tasks by Project ID
- [x] Get all Tasks by Employee ID
- [x] Delete Task (should not delete Employee)
- [x] Delete Project (should cascade delete Tasks)
- [x] Handle 404 errors (entity not found)
- [x] Handle 400 errors (validation failures)

## License

This project is created for educational purposes.
