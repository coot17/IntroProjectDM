package edu.northwestu.intc3283.ApiExampleApplication.controller;

import edu.northwestu.intc3283.ApiExampleApplication.entity.Tasks;
import edu.northwestu.intc3283.ApiExampleApplication.requests.TaskRequest;
import edu.northwestu.intc3283.ApiExampleApplication.responses.TaskResponse;
import edu.northwestu.intc3283.ApiExampleApplication.repository.TasksRepository;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TasksApi {

    private final List<TaskResponse> taskResponseList = new ArrayList<>();
    private final TasksRepository taskRepository;

    public TasksApi(TasksRepository tRepository) {
        this.taskRepository = tRepository;
    }

    //@GetMapping("/{page}/{size}")
    @GetMapping("")
    List<Tasks> getAllTasks(@RequestParam("page") Long page, @RequestParam("size") Long size) {
        return taskRepository.GetAllTasks((page-1) * size, size);
    }

    @GetMapping("/{taskId}")
    List<Tasks> getTask(@PathVariable Long taskId) {
        return taskRepository.GetTask(taskId);
    }

    @PostMapping("")
    List<TaskResponse> addTask(@Valid @RequestBody TaskRequest taskRequest) {

        Tasks t = new Tasks();
        t.setTitle(taskRequest.title());
        t.setDescription(taskRequest.description());
        t.setWilling_to_spend(taskRequest.willingToPay());

        taskRepository.save(t);

        taskResponseList.add(new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getWilling_to_spend(), t.getCreated_at()));

        return taskResponseList;
    }

    @DeleteMapping("/{taskId}")
    String DeleteTask(@PathVariable Long taskId) {
        taskRepository.deleteById(taskId);
        return "Task deleted";
    }

    @PutMapping("")
    TaskResponse UpdateTask(@Valid @RequestBody TaskRequest taskRequest) {
        Tasks t = new Tasks();
        t.setId(taskRequest.iD());
        t.setTitle(taskRequest.title());
        t.setDescription(taskRequest.description());
        t.setWilling_to_spend(taskRequest.willingToPay());
        t.setCreated_at(taskRequest.createdAt());

        taskRepository.save(t);

        return (new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.getWilling_to_spend(), t.getCreated_at()));

     }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
