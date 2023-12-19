package com.franco.todolistapp.persistance.repository;

import com.franco.todolistapp.persistance.entity.Task;
import com.franco.todolistapp.persistance.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findAllByTaskStatus(TaskStatus status);

    @Modifying
    @Query(value = "UPDATE TASK SET FINISHED=true, TASK_STATUS=0 WHERE ID=:id", nativeQuery = true)
    public void markTaskAsFinished(@Param("id") Long id);

    @Modifying
    @Query(value = "UPDATE TASK SET FINISHED=true, TASK_STATUS=1 WHERE ID=:id", nativeQuery = true)
    public void markTaskAsFinishedAndLate(@Param("id") Long id);

}
