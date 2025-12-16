package com.bisekh.Task.service.Impl;

import com.bisekh.Task.dto.TaskDto;
import com.bisekh.Task.model.TaskModel;
import com.bisekh.Task.repository.TaskRepository;
import com.bisekh.Task.service.TaskService;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public  TaskServiceImpl(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskModel createTask(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    @Override
    public List<TaskModel> getAllTasks() {

        return  taskRepository.findAll();

    }

    @Override
    public TaskModel getSingleTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No user found with the id "+id));
    }

    @Override
    public TaskModel updateTask(Long id) {
        TaskModel tasks = taskRepository.findById(id).orElseThrow(()-> new RuntimeException(("No task found")));
        tasks.setStatus(!tasks.isStatus());
        tasks.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(tasks);
    }

    @Override
    public String deleteTask(Long id) {
        taskRepository.deleteById(id);
        return "Task Deleted Successfully";
    }
}
