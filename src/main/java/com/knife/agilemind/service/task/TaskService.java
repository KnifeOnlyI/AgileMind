package com.knife.agilemind.service.task;

import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.task.TaskConstant;
import com.knife.agilemind.domain.task.TaskEntity;
import com.knife.agilemind.dto.task.CreateTaskDTO;
import com.knife.agilemind.dto.task.TaskDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.task.TaskRepository;
import com.knife.agilemind.service.project.ProjectValidator;
import com.knife.agilemind.service.story.StoryService;
import com.knife.agilemind.service.story.StoryValidator;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service to manage the stories
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskValidator taskValidator;

    @Autowired
    private StoryValidator storyValidator;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ProjectValidator projectValidator;

    /**
     * Create the specified task in database
     *
     * @param createTaskDTO The DTO of task to create
     *
     * @return The created task
     */
    public TaskDTO create(CreateTaskDTO createTaskDTO) {
        this.userService.assertLogged();

        this.taskValidator.assertValid(createTaskDTO);
        this.taskValidator.assertLoggedUserCanAll(createTaskDTO.getStoryId(), StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return this.toDTO(this.taskRepository.save(new TaskEntity()
            .setName(createTaskDTO.getName())
            .setDescription(createTaskDTO.getDescription())
            .setEstimatedTime(createTaskDTO.getEstimatedTime())
            .setLoggedTime(createTaskDTO.getLoggedTime())
            .setStatus(this.taskStatusService.findById(createTaskDTO.getStatusId()))
            .setAssignedUser(this.userService.findById(createTaskDTO.getAssignedUserId()))
            .setStory(this.storyService.findById(createTaskDTO.getStoryId()))
        ));
    }

    /**
     * Get the task identified by the specified ID
     *
     * @param id The ID
     *
     * @return The task
     */
    public TaskDTO get(Long id) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        TaskEntity task = this.taskRepository.findById(id).orElse(null);

        BusinessAssert.notNull(task, TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        TechnicalAssert.notNull(task.getStory());
        TechnicalAssert.notNull(task.getStory().getProject());

        this.projectValidator.assertLoggedUserCanView(task.getStory().getProject().getId(), TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return this.toDTO(task);
    }

    /**
     * Get all tasks from the specified story
     *
     * @param storyId The story ID
     *
     * @return The task list
     */
    public List<TaskDTO> getAllFromStory(Long storyId) {
        this.userService.assertLogged();

        List<TaskDTO> results = new ArrayList<>();

        this.storyValidator.assertExists(storyId);

        TaskEntity task = this.findById(storyId);

        TechnicalAssert.notNull(task.getStory());
        TechnicalAssert.notNull(task.getStory().getProject());
        TechnicalAssert.notNull(task.getStory().getProject().getId());

        this.taskValidator.assertLoggedUserCanAll(task.getStory().getProject().getId());

        for (TaskEntity tmpTask : task.getStory().getTasks()) {
            results.add(this.toDTO(tmpTask));
        }

        return results;
    }

    /**
     * Update the specified task in database
     *
     * @param taskDTO The DTO of task to update
     *
     * @return The updated task
     */
    public TaskDTO update(TaskDTO taskDTO) {
        this.userService.assertLogged();

        this.taskValidator.assertValid(taskDTO);

        TaskEntity task = this.findById(taskDTO.getId());

        TechnicalAssert.notNull(task.getStory());
        TechnicalAssert.notNull(task.getStory().getProject());
        TechnicalAssert.notNull(task.getStory().getProject().getId());

        this.taskValidator.assertLoggedUserCanAll(task.getStory().getProject().getId());

        task.setName(taskDTO.getName())
            .setDescription(taskDTO.getDescription())
            .setEstimatedTime(taskDTO.getEstimatedTime())
            .setLoggedTime(taskDTO.getLoggedTime())
            .setStatus(this.taskStatusService.findById(taskDTO.getStatusId()))
            .setStory(this.storyService.findById(taskDTO.getStoryId()));

        if (taskDTO.getAssignedUserId() != null) {
            task.setAssignedUser(this.userService.findById(taskDTO.getAssignedUserId()));
        } else {
            task.setAssignedUser(null);
        }

        return this.toDTO(task);
    }

    /**
     * Delete the task with the specified ID
     *
     * @param id The ID of task to delete
     */
    public void delete(Long id) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        TaskEntity task = this.findById(id);

        TechnicalAssert.notNull(task.getStory());
        TechnicalAssert.notNull(task.getStory().getProject());
        TechnicalAssert.notNull(task.getStory().getProject().getId());

        this.taskValidator.assertLoggedUserCanAll(task.getStory().getProject().getId(), TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        this.taskRepository.delete(task);
    }

    /**
     * Find task in database with the specified id
     *
     * @return The task
     */
    public TaskEntity findById(Long id) {
        TechnicalAssert.notNull(id);

        TaskEntity task = this.taskRepository.findById(id).orElse(null);

        BusinessAssert.notNull(task, TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return task;
    }

    /**
     * Find all tasks by the specified ids
     *
     * @param ids The IDs
     *
     * @return The stories
     */
    public Set<TaskEntity> findAllById(Set<Long> ids) {
        this.userService.assertLogged();

        List<TaskEntity> tasks = this.taskRepository.findAllById(ids);

        for (TaskEntity task : tasks) {
            TechnicalAssert.notNull(task.getStory());
            TechnicalAssert.notNull(task.getStory().getProject());
            TechnicalAssert.notNull(task.getStory().getProject().getId());

            this.taskValidator.assertLoggedUserCanAll(task.getStory().getProject().getId());
        }

        return new HashSet<>(tasks);
    }

    /**
     * Convert the specified entity to DTO
     *
     * @param entity The entity
     *
     * @return The DTO
     */
    public TaskDTO toDTO(TaskEntity entity) {
        TaskDTO results = new TaskDTO();

        if (entity != null) {
            results.setId(entity.getId());
            results.setName(entity.getName());
            results.setDescription(entity.getDescription());
            results.setEstimatedTime(entity.getEstimatedTime());
            results.setLoggedTime(entity.getLoggedTime());

            if (entity.getStatus() != null) {
                results.setStatusId(entity.getStatus().getId());
            }

            if (entity.getAssignedUser() != null) {

                results.setAssignedUserId(entity.getAssignedUser().getId());
            }

            if (entity.getStory() != null) {
                results.setStoryId(entity.getStory().getId());
            }
        }

        return results;
    }
}
