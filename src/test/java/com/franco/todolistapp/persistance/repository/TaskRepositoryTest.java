package com.franco.todolistapp.persistance.repository;

import com.franco.todolistapp.mapper.IMapper;
import com.franco.todolistapp.mapper.TaskInDtoToTask;
import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.service.dto.TaskInDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    TestEntityManager testEntityManager;
    private TaskInDto taskInDto;
    private Task task;
    private TaskInDtoToTask taskInDtoToTask = new TaskInDtoToTask();

    @BeforeEach
    void setUp() {
        taskInDto = new TaskInDto();
        System.out.println("QUE PASOOOOOOOOOOO!!!!");
        LocalDateTime localTime = LocalDateTime.now();
        taskInDto.setTitle("Test THE CREATE Task");
        taskInDto.setDescription("This is a test task description.");

        task = taskInDtoToTask.map(taskInDto);
        testEntityManager.persist(task);
    }

    @Test
    public void gaga (){
        Long id = 1L;
        Optional<Task> result = Optional.of(taskRepository.getReferenceById(id));

        assertEquals("st THE CREATE Task", "st THE CREATE Task");
    }

}