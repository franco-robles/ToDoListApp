package com.franco.todolistapp.persistance.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "task")
public class Task {
    @Schema(description = "task id", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Schema(description = "title task", example = "Hi i'm a taks")
    private String title;
    @Schema(description = " description task ", example = "read a book")
    private String description;
    @Schema(description = "date of task creation", example = "2045-09-12")
    private LocalDateTime dateOfCreation;
    @Schema(description = "estimated time to complete the task", example = "2045-10-12")
    private LocalDateTime estimateEndTime;
    @Schema(description = "flag to check if the task was finished or not", example = "false")
    private boolean finished;
    @Schema(description = "flag to find out if the task was completed after or before the end time ", example = "ON_TIME")
    private TaskStatus taskStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDateTime getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(LocalDateTime estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return finished == task.finished && Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && Objects.equals(dateOfCreation, task.dateOfCreation) && Objects.equals(estimateEndTime, task.estimateEndTime) && taskStatus == task.taskStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dateOfCreation, estimateEndTime, finished, taskStatus);
    }
}
