package com.franco.todolistapp.mapper;

import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.entity.TaskStatus;
import com.franco.todolistapp.service.dto.TaskInDto;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TaskInDtoToTask implements IMapper<TaskInDto, Task>{

    @Override
    public Task map(TaskInDto in) {
        Task task = new Task();
        task.setTitle(in.getTitle());
        task.setDescription(in.getDescription());
        task.setEstimateEndTime(in.getEstimateEndTime());
        task.setDateOfCreation(LocalDateTime.now());
        task.setFinished(false);
        task.setTaskStatus(TaskStatus.ON_TIME);

        return task;
    }
}
