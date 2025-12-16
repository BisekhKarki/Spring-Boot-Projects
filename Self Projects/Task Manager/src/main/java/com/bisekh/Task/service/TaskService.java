package com.bisekh.Task.service;

import com.bisekh.Task.dto.TaskDto;
import com.bisekh.Task.model.TaskModel;

import java.util.List;

public interface TaskService {

    TaskModel createTask(TaskModel taskModel);
    List<TaskModel> getAllTasks();
    TaskModel getSingleTask(Long id);
    TaskModel updateTask(Long id);
    String deleteTask(Long id);


}
