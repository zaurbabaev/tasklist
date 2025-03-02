package com.example.tasklist.web.controller;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.task.TaskImageDto;
import com.example.tasklist.web.mappers.TaskImageMapper;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.validation.OnUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@Validated
@Tag(name = "Task Controller",
        description = "Task API")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final TaskImageMapper taskImageMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper, TaskImageMapper taskImageMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
        this.taskImageMapper = taskImageMapper;
    }

    @PutMapping
    @Operation(summary = "Update task")
    @PreAuthorize("@cse.canAccessTask(#dto.id)")
    public TaskDto update(@Validated(OnUpdate.class)
                          @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task updateTask = taskService.update(task);
        return taskMapper.toDto(updateTask);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get TaskDto by id")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get TaskDto by user id")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("{id}/image")
    @Operation(summary = "Upload image to task")
    @PreAuthorize("@cse.canAccessTask(#id)")
    public void uploadImage(@PathVariable Long id,
                            @Validated @ModelAttribute TaskImageDto imageDto) {
        TaskImage image = taskImageMapper.toEntity(imageDto);
        taskService.uploadImage(id, image);
    }


}
