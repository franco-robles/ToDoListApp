# ToDoListApp

## Overview

This project provides a **REST API** that allows you to create, read, update, and delete tasks in a ToDo list. It's built with Spring Boot and leverages Spring Data JPA for data persistence, Swagger UI for API documentation, and H2 for in-memory database support

# Requirements

- Java   -v 17
- maven  -v 3.9.4
- Spring Boot  -v 3.2

## Dependencis

- spring boot starter data jpa -v 3.2.1
- spring boot starter web -v 3.2.1
- h2  -v 2.2.224
- springdoc openapi starter webmvc ui  -v 2.3.0
- swagger annotations -v 2.2.19

## Installation

To install the project, follow these steps:

1. Clone the project from the GitHub repository.
2. Navigate to the project directory.
3. Run `mvn clean install` to build the project.
4. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse) and run the application.


## To use the API
### API Endpoints
| Method | Endpoint | Description | 
|---------|--------------------------------|---------------------------------------------------|
| POST | `/api/tasks` | Create a new task | 
| GET | `/api/tasks` | Get a list of all tasks | 
| GET | `/api/tasks/page` | Get tasks with pagination | 
| GET | `/api/tasks/{id}` | Get a task by ID | 
| GET | `/api/tasks/{status}` | Get tasks by status (ON_TIME, LATE) | 
| PATCH | `/api/tasks/{id}` | Mark a task as finished | 
| DELETE | `/api/tasks/{id}` | Delete a task |

### API Documentation
- Swagger UI: http://localhost:8080/swagger-ui
- H2 Console: http://localhost:8080/h2-console

## Tests 
 The ToDoListApp project uses unit tests to ensure the quality and reliability of the code. Unit tests are written for each class or function to verify that it behaves as expected. 
 
 **TaskService** 
The TaskService layer is responsible for interacting with taskRepository to create, read, update, and delete tasks. The unit tests for the TaskService layer have a coverage of 100%. The following unit tests have been added to verify the functionality of the TaskService: 
 * Create task
 * Read task
 * Update task
 * Delete task
 * Pagination
 * Status-based retrieval
 * Error handling
 * Invalid input

Contributions are welcome. To contribute, create a new branch in the GitHub repository and submit a pull request. 

# License
This project is distributed under the [MIT License](https://choosealicense.com/licenses/mit/).
