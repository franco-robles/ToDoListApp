# ToDoListApp
This proyect is a **REST API** that provide a service to manage a ToDo List


# Requirements

- Java 17
- maven
- Spring Boot 3.2

## Dependencis

- spring boot starter data jpa
- spring boot starter web
- h2
- springdoc openapi starter webmvc ui
- swagger annotations

## Installation

To install the project, follow these steps:

1.  Clone the project from the GitHub repository.
2.  Navigate to the project directory.
3.  Run the command  `mvn clean install`  or using the option **open proyect** in your IDE
4. Run and have fun


## To use the API, follow these steps
1.  Send a POST request to  `/api/tasks`  to create a new task.
2.  Send a GET request to  `/api/tasks`  to get a list of all tasks.
3.  Send a GET request to  `/api/tasks/page`  to get tasks using pageable method.
4.  Send a GET request to  `/api/tasks/{id}`  to get a task.
5.  Send a GET request to  `/api/tasks/{status}`  to get a  list of all task with specific status	.
6.  Send a PATCH request to  `/api/tasks/{id}`  to mark as finished an existing task.
7.  Send a DELETE request to  `/api/tasks/{id}`  to delete an existing task.

## Documentation

The API documentation is located in  "http://localhot:8080/swagger-ui", in addition you may found the console for H2 in "http://localhost:8080/h2-console"


Contributions are welcome. To contribute, create a new branch in the GitHub repository and submit a pull request.
 