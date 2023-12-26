package com.franco.todolistapp.service;

import com.franco.todolistapp.exceptions.SortFieldValidatorException;
import com.franco.todolistapp.exceptions.ToDoExceptions;
import com.franco.todolistapp.mapper.TaskInDtoToTask;
import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.entity.TaskStatus;
import com.franco.todolistapp.persistance.repository.TaskRepository;
import com.franco.todolistapp.service.dto.TaskInDto;
import com.franco.todolistapp.util.SortFieldValidater;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    SortFieldValidater sortFieldValidater;
    @Mock
    TaskRepository taskRepository;
    @Mock
    TaskInDtoToTask taskMapper;
    @InjectMocks
    TaskService taskService;

    Pageable pageable ;
    TaskInDto taskInDto = new TaskInDto();
    List<Task> listTask = new ArrayList<>();
    Task task = new Task();

    @BeforeEach
    void setUp(){
        taskInDto.setEstimateEndTime(LocalDate.now());
        taskInDto.setTitle("hola");
        taskInDto.setDescription("chau");
        taskService.createTask(taskInDto);


        task.setTaskStatus(TaskStatus.ON_TIME);
        task.setTitle("hola");
        task.setDescription("chau");
        task.setFinished(false);
        task.setId(1L);
        task.setDateOfCreation(LocalDate.now());
        task.setEstimateEndTime(LocalDate.now());
        listTask.add(task);
    }
    @DisplayName("save a new task")
    @Test
    void createTask(){
        when(taskMapper.map(taskInDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);

        ResponseEntity<Task> response = taskService.createTask(taskInDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(task, response.getBody());

    }

    @DisplayName("find task by ID: task already exist in DB")
    @Test
    void findTaskById(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));
        ResponseEntity<Task> response = taskService.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());

    }

    @DisplayName("find task by ID: task not exist in DB")
    @Test
    void findTaskByIdFail(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        ResponseEntity<Task> response = taskService.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

    }
    @DisplayName("check findAll: status 200 ok")
    @Test
    void checkFindAll(){
        when(taskRepository.findAll()).thenReturn( listTask);

        ResponseEntity<List<Task>> response = taskService.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertFalse(response.getBody().isEmpty());
        assertEquals(taskInDto.getDescription(), response.getBody().get(0).getDescription());
        assertEquals(taskInDto.getTitle(), response.getBody().get(0).getTitle());
        assertEquals(taskInDto.getEstimateEndTime(), response.getBody().get(0).getEstimateEndTime());
        assertEquals(TaskStatus.ON_TIME, response.getBody().get(0).getTaskStatus());
        assertEquals(1, response.getBody().get(0).getId());
        assertEquals(false, response.getBody().get(0).isFinished());

    }

    @DisplayName("check find all with empty DB: status 204")
    @Test
    void checkFindAllWithDBEmpty(){
        List<Task> listEmpty = new ArrayList<>();
        when(taskRepository.findAll()).thenReturn( listEmpty);
        ResponseEntity<List<Task>> response = taskService.findAll();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

    @Test
    @DisplayName("find Tasks using pageable: valid sort method")
    void findAllByTaskStatus(){
        when(sortFieldValidater.isValidSort(Sort.by("id"))).thenReturn(true);
        pageable = PageRequest.of(0, 2, Sort.by("id"));
        List<Task> list = new ArrayList<>();
        list.add(task);
        when(taskRepository.findAll(pageable)).thenReturn(new PageImpl(list));
        ResponseEntity<Page<Task>> response = taskService.findTasksPaged(pageable);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list.size(), response.getBody().getSize());
    }

    @Test
    @DisplayName("find Tasks using pageable: valid sort method but nothing to show")
    void findAllByTaskStatusNoContent(){
        when(sortFieldValidater.isValidSort(Sort.by("id"))).thenReturn(true);
        pageable = PageRequest.of(0, 2, Sort.by("id"));
        List<Task> list = new ArrayList<>();
        when(taskRepository.findAll(pageable)).thenReturn(new PageImpl(list) );
        ResponseEntity<Page<Task>> response = taskService.findTasksPaged(pageable);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(null, response.getBody());
    }



    @Test
    @DisplayName("find Tasks using pageable: invalid sort method")
    void findTasksPagedTestInvalidaSort(){
        when(sortFieldValidater.isValidSort(Sort.by("tall"))).thenReturn(false);
        pageable = PageRequest.of(0, 2, Sort.by("tall"));
        assertThrows(SortFieldValidatorException.class, () -> taskService.findTasksPaged(pageable));
    }

    @Test
    @DisplayName("find all by status: LATE, ON_TIME")
    void findAllByTaskStatusSomeTaskWithThatStatus(){
        when(taskRepository.findAllByTaskStatus(TaskStatus.ON_TIME)).thenReturn(listTask);
        ResponseEntity<List<Task>> response =  taskService.findAllByTaskStatus(TaskStatus.ON_TIME);

        assertFalse(response.getBody().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    @DisplayName("find all by status: LATE, ON_TIME, not task to show")
    void findAllByTaskStatusNoTaskWithThatStatus(){
        List<Task> list = new ArrayList<>();
        when(taskRepository.findAllByTaskStatus(TaskStatus.LATE)).thenReturn(list);
        ResponseEntity<List<Task>> response =  taskService.findAllByTaskStatus(TaskStatus.LATE);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("mark task as finiched, and no change status")
    void updateTaskAsFinishedTest(){
        task.setFinished(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));

        ResponseEntity<Object> response2 = taskService.updateTaskAsFinished(1L);
        assertEquals(HttpStatus.NO_CONTENT, response2.getStatusCode());

        //cheking that the only change is on Finished attrbute
        ResponseEntity<Task> response3 = taskService.findById(1L);
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertEquals(taskInDto.getDescription(), response3.getBody().getDescription());
        assertEquals(taskInDto.getTitle(), response3.getBody().getTitle());
        assertEquals(taskInDto.getEstimateEndTime(), response3.getBody().getEstimateEndTime());
        assertEquals(TaskStatus.ON_TIME, response3.getBody().getTaskStatus());
        assertEquals(1, response3.getBody().getId());
        assertEquals(true, response3.getBody().isFinished()); //this is the only change

    }
    @Test
    @DisplayName("mark task as finiched and change status to late")
    void updateTaskAsFinishedAndStatusLateTest(){
        task.setFinished(true);
        task.setTaskStatus(TaskStatus.LATE);
        //dateOfCreation is less than EstimateEndTime
        task.setEstimateEndTime(LocalDate.of(2022, 7, 12));

        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));

        ResponseEntity<Object> response2 = taskService.updateTaskAsFinished(1L);
        assertEquals(HttpStatus.NO_CONTENT, response2.getStatusCode());

        //cheking that the only change is on Finished and status attrbute
        ResponseEntity<Task> response3 = taskService.findById(1L);
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertEquals(taskInDto.getDescription(), response3.getBody().getDescription());
        assertEquals(taskInDto.getTitle(), response3.getBody().getTitle());
        assertEquals(LocalDate.of(2022, 7, 12), response3.getBody().getEstimateEndTime());
        assertEquals(TaskStatus.LATE, response3.getBody().getTaskStatus());
        assertEquals(1, response3.getBody().getId());
        assertEquals(true, response3.getBody().isFinished()); //this is the only change

    }
    @DisplayName("mark task as finiched: task not exist in DB")
    @Test
    void updateTaskAsFinishedTaskNotFoundTest(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        ResponseEntity<Object> response = taskService.updateTaskAsFinished(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());

    }
    @DisplayName("mark task as finiched: invalid id")
    @Test
    void updateTaskAsFinishedInvalidIDTest(){
        assertThrows(ToDoExceptions.class,() -> taskService.updateTaskAsFinished(0L));
    }

    @Test
    @DisplayName("delete task using id: task already existe in DB")
    void deleteByIdTaskExist(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(task));

        ResponseEntity<Void> response = taskService.deleteTaskById(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());


    }
    @Test
    @DisplayName("delete task using id: task already existe in DB")
    void deleteByIdTaskNotExist(){
        when(taskRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Void> response = taskService.deleteTaskById(1L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
