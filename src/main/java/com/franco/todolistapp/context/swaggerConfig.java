package com.franco.todolistapp.context;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("/api-docs")
@OpenAPIDefinition(info = @Info(
        title = "ToDo API",
        version = "V1",
        description = "The API REST for ToDoList App",
        termsOfService = "Terms of service",
        contact = @Contact(name = "Franco", url = "franco_robles94@outlook.com", email = "franco_robles94@outlook.com"),
        license = @License(name = "License of API", url = "API license URL")
))
public class swaggerConfig {

}
