package com.franco.todolistapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class TaskInDto {
    @Schema(description = "tittle task", example = "Hi i'm a taks")
    private String title;
    @Schema(description = " description task ", example = "read a book")
    private String description;
    @Schema(description = "estimated time to complete the task", example = "2045-10-12")
    private LocalDateTime estimateEndTime;

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

    public LocalDateTime getEstimateEndTime() {
        return estimateEndTime;
    }

    public void setEstimateEndTime(LocalDateTime estimateEndTime) {
        this.estimateEndTime = estimateEndTime;
    }
}
