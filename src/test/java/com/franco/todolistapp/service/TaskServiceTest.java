package com.franco.todolistapp.service;

import com.franco.todolistapp.controller.TaskController;
import com.franco.todolistapp.mapper.TaskInDtoToTask;
import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.repository.TaskRepository;
import com.franco.todolistapp.service.dto.TaskInDto;
import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskInDtoToTask taskMapper;
    private TaskInDto taskInDto;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setup() {

        taskInDto = new TaskInDto();
        System.out.println("QUE PASOOOOOOOOOOO!!!!");
        LocalDateTime localTime = LocalDateTime.now();
        taskInDto.setTitle("Test THE CREATE Task");
        taskInDto.setDescription("This is a test task description.");
        taskInDto.setEstimateEndTime(localTime);
    }
    @DisplayName("save a new task")
    @Test
    void testCreateTask(){

        // call createTask and capture argument
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        ResponseEntity<Task> response = taskService.createTask(taskInDto);

        // verify TaskRepository save call and argument
        verify(taskRepository).save(taskCaptor.capture());
        Task capturedTask = taskCaptor.getValue();
        assertEquals(taskInDto.getTitle(), capturedTask.getTitle());
        assertEquals(taskInDto.getDescription(), capturedTask.getDescription());

        // check response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody()); // assert object properties using Task assertions
    }

}
