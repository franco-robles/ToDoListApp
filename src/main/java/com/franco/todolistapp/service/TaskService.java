package com.franco.todolistapp.service;

import com.franco.todolistapp.exceptions.SortFieldValidatorException;
import com.franco.todolistapp.exceptions.ToDoExceptions;
import com.franco.todolistapp.mapper.TaskInDtoToTask;
import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.entity.TaskStatus;
import com.franco.todolistapp.persistance.repository.TaskRepository;
import com.franco.todolistapp.service.dto.TaskInDto;
import com.franco.todolistapp.util.SortFieldValidater;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final TaskInDtoToTask taskMapper;
    private final SortFieldValidater sotrValidator;

    public TaskService(TaskRepository repository, TaskInDtoToTask taskMapper, SortFieldValidater sotrValidator) {
        this.repository = repository;
        this.taskMapper = taskMapper;
        this.sotrValidator = sotrValidator;
    }

    /** crate a new task. **/
    public ResponseEntity<Task> createTask(TaskInDto taskInDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(taskMapper.map(taskInDto)));

    }

    /** allows users to retrieve tasks  **/
    public ResponseEntity<List<Task>> findAll(){
        List<Task> listTask = this.repository.findAll();
        if (listTask.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok().body(listTask);
        }
    }

    /** allows users to retrieve tasks by pages and improves the efficiency of the application. **/
    public ResponseEntity<Page<Task>> findTasksPaged (Pageable pageable){
        if(!sotrValidator.isValidSort(pageable.getSort())){
            throw new SortFieldValidatorException("some attributes are not allowed for sorting.", HttpStatus.BAD_REQUEST);
        }
        Page<Task> pageListTask = this.repository.findAll(pageable);
        if (pageListTask.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok().body(pageListTask);
        }
    }

    /** get tasks filtering by status. it is not efficient if I have a lot of tasks by status. **/
    public ResponseEntity<List<Task>> findAllByTaskStatus(TaskStatus status){
        List<Task> listTask =  this.repository.findAllByTaskStatus(status);
        if (listTask.isEmpty()){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.ok().body(listTask);
        }
    }


    /** update the completed attribute and update the task status if the current date is later than the estimated completion date. **/
    @Transactional
    public ResponseEntity<Object> updateTaskAsFinished(Long id){
        try{
            Task task = this.repository.findById(id)
                    .orElseThrow(()->new ToDoExceptions("task not found", HttpStatus.NOT_FOUND));
            markTaskFinisher(id, task);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            ToDoExceptions.getLogger().error("Error ocurred when updating task with ID {}: {}", id, e, HttpStatus.NOT_FOUND, e.getMessage(), e.getStackTrace());
            return ResponseEntity.notFound().build();
        }
    }

    private void markTaskFinisher(Long id, Task task) {
        if(LocalDateTime.now().isAfter(task.getEstimateEndTime())){
            this.repository.markTaskAsFinishedAndLate(id);
        }else{
            this.repository.markTaskAsFinished(id);
        }
    }

    /** remove the task whose parameter is the one received by parameter **/
    @Transactional
    public ResponseEntity<Object> deleteTaskById(Long id){
        try {
            Task task = this.repository.findById(id)
                    .orElseThrow(() -> new ToDoExceptions("task not found", HttpStatus.NOT_FOUND));
            this.repository.delete(task);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            ToDoExceptions.getLogger().error("Error occurred when deleting task with ID {}: {}", id, HttpStatus.NOT_FOUND, e, e.getMessage(), e.getStackTrace());
            return ResponseEntity.notFound().build();
        }

    }

}
