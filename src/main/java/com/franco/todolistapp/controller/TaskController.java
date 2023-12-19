package com.franco.todolistapp.controller;

import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.entity.TaskStatus;
import com.franco.todolistapp.service.TaskService;
import com.franco.todolistapp.service.dto.TaskInDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name ="Task", description = "Operations for the entity Task")
@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiResponse(responseCode = "200", description = "the task has been successfully created")
    @Operation(summary = "Create a new task")
    @PostMapping("/createTask")
    public ResponseEntity<Task> createTask(@RequestBody TaskInDto in){
        return this.taskService.createTask(in);
    }

    @ApiResponses(value={
            @ApiResponse(responseCode = "204", description = "no task to show"),
            @ApiResponse(responseCode = "200", description = "tasks exist in db")
    })
    @Operation(summary = "Get all tasks")
    @GetMapping("/findAll")
    public ResponseEntity<List<Task>> findAll(){
        return  this.taskService.findAll();
    }


    @Operation(summary = "Get task by pages")
    @GetMapping("/tasks")
    public ResponseEntity<Page<Task>> findPage(@PageableDefault(size = 3, page = 0, sort = "id") Pageable pageable){
        return  this.taskService.findTasksPaged(pageable);
    }

    @ApiResponses(value={
            @ApiResponse(responseCode = "204", description = "no task with selected status to show"),
            @ApiResponse(responseCode = "200", description = "tasks with selected status exist in db")
    })
    @Operation(summary = "Get all tasks with status=ON_TIME or LATE")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> findAllByTaskStatus(@PathVariable("status")TaskStatus status){
        return this.taskService.findAllByTaskStatus(status) ;

    }

    @ApiResponses(value={
            @ApiResponse(responseCode = "204", description = "id exists in the database and was marked as finished"),
            @ApiResponse(responseCode = "404", description = "nonexistent id")
    })
    @Operation(summary = "Mark task as Finished")
    @PatchMapping("/markAsFinished/{id}")
        public ResponseEntity<Void> markASFinished(@PathVariable("id") Long id){
            this.taskService.updateTaskAsFinished(id);
            return ResponseEntity.noContent().build();
        }

    @ApiResponses(value={
           @ApiResponse(responseCode = "204", description = "id existed and was eliminated"),
           @ApiResponse(responseCode = "404", description = "nonexistent id")
    })
    @Operation(summary = "Delete task for ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id){
         this.taskService.deleteTaskById(id);
         return ResponseEntity.noContent().build();
    }




}
