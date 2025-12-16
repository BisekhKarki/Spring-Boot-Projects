package com.bisekh.Task.controller;

import com.bisekh.Task.ErrorMesage.ErrorResponse;
import com.bisekh.Task.model.TaskModel;
import com.bisekh.Task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public  TaskController(TaskService taskService){
        this.taskService = taskService;
    }


    @PostMapping("/create")
    public ResponseEntity<?> createTask(
            @RequestBody
            TaskModel taskModel
    ){
    try{
        if(taskModel.getDescription().isEmpty() || taskModel.getTitle().isEmpty() ){
            return  ResponseEntity.status(400).body(new ErrorResponse(400,"Fields cannot be empty"));
        }

        TaskModel newTask = new TaskModel();
        newTask.setDescription(taskModel.getDescription());
        newTask.setTitle(taskModel.getTitle());
        newTask.setStatus(false);
        newTask.setCreatedAt(LocalDateTime.now());
        newTask.setUpdatedAt(LocalDateTime.now());


        TaskModel savedTask = taskService.createTask(newTask);
        return  ResponseEntity.status(201).body(new ErrorResponse(201,savedTask));
    } catch (Exception e) {
     return  ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()));
    }

    }


    @GetMapping("")
    public ResponseEntity<?> getAllTasks(

    ){
        try{
            List<TaskModel> tasks = taskService.getAllTasks();
            if(tasks.isEmpty()){
                return  ResponseEntity.status(200).body(new ErrorResponse(200,"No Tasks Available"));
            }
            return  ResponseEntity.status(200).body(new ErrorResponse(200,tasks));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()));
        }

    }


    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTasksById(
    @PathVariable Long taskId
    ){
        try{
            TaskModel task = taskService.getSingleTask(taskId);
            if(task == null){
                return  ResponseEntity.status(200).body(new ErrorResponse(200,"No Task Found with the id "+ taskId));
            }
            return  ResponseEntity.status(200).body(new ErrorResponse(200,task));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()));
        }

    }


    @PatchMapping("/{taskId}/update")
    public ResponseEntity<?> updateTask(
            @PathVariable Long taskId
    ){
        try{
            TaskModel task = taskService.getSingleTask(taskId);
            if(task == null){
                return  ResponseEntity.status(200).body(new ErrorResponse(200,"No Task Found with the id "+ taskId));
            }
            TaskModel updatedTask = taskService.updateTask(taskId);
            return  ResponseEntity.status(200).body(new ErrorResponse(200,updatedTask));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()));
        }

    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long taskId

    ){
        try{
            TaskModel task = taskService.getSingleTask(taskId);
            if(task == null){
                return  ResponseEntity.status(200).body(new ErrorResponse(200,"No Task Found with the id "+ taskId));
            }
            String deletedTask = taskService.deleteTask(taskId);
            return  ResponseEntity.status(200).body(new ErrorResponse(200,deletedTask));
        } catch (Exception e) {
            return  ResponseEntity.status(500).body(new ErrorResponse(500,e.getMessage()));
        }

    }


}
