package com.knife.agilemind.web.rest.task;

import com.knife.agilemind.dto.task.CreateTaskDTO;
import com.knife.agilemind.dto.task.TaskDTO;
import com.knife.agilemind.service.task.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Resource to manage tasks
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TaskResource {
    @Autowired
    private TaskService taskService;

    /**
     * Get the task with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @GetMapping("/task/{id}")
    public ResponseEntity<TaskDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(this.taskService.get(id), HttpStatus.OK);
    }

    /**
     * Get all stories from the specified story
     *
     * @param storyId The story ID
     *
     * @return The HTTP response
     */
    @GetMapping("/story/{storyId}/task")
    public ResponseEntity<List<TaskDTO>> getAllFromStory(@PathVariable Long storyId) {
        return new ResponseEntity<>(this.taskService.getAllFromStory(storyId), HttpStatus.OK);
    }

    /**
     * Create new task
     *
     * @param taskDTO The task to create
     *
     * @return The HTTP response
     */
    @PostMapping("/task")
    public ResponseEntity<TaskDTO> create(@RequestBody CreateTaskDTO taskDTO) {
        return new ResponseEntity<>(this.taskService.create(taskDTO), HttpStatus.OK);
    }

    /**
     * Update the specified task
     *
     * @param taskDTO The new task data
     *
     * @return The HTTP response
     */
    @PutMapping("/task")
    public ResponseEntity<TaskDTO> update(@RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(this.taskService.update(taskDTO), HttpStatus.OK);
    }

    /**
     * Delete the task with the specified id
     *
     * @param id The id
     */
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.taskService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
