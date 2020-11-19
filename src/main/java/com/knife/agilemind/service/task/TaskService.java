package com.knife.agilemind.service.task;

import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.task.TaskConstant;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.task.TaskEntity;
import com.knife.agilemind.dto.task.CreateTaskDTO;
import com.knife.agilemind.dto.task.TaskDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.task.TaskRepository;
import com.knife.agilemind.service.project.ProjectValidator;
import com.knife.agilemind.service.story.StoryService;
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
        this.taskValidator.assertValid(createTaskDTO);

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
        if (id == null) {
            throw new TechnicalException();
        }

        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        TaskEntity taskEntity = this.taskRepository.findById(id).orElse(null);

        // If the task's not exists or user is not assigned, throw "TASK_NOT_FOUND" error
        if (taskEntity == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), taskEntity.getStory().getProject())
        ) {
            throw new BusinessException(TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return this.toDTO(taskEntity);
    }

    /**
     * Get all tasks from the specified story
     *
     * @param storyId The story ID
     *
     * @return The task list
     */
    public List<TaskDTO> getAllFromStory(Long storyId) {
        List<TaskDTO> results = new ArrayList<>();

        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        StoryEntity story = this.storyService.findById(storyId);

        // If the story's not exists or user is not assigned, throw "STORY_NOT_FOUND" error
        if (story == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), story.getProject())
        ) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        for (TaskEntity task : story.getTasks()) {
            results.add(this.toDTO(task));
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
        return this.toDTO(this.taskValidator.assertValid(taskDTO)
            .setName(taskDTO.getName())
            .setDescription(taskDTO.getDescription())
            .setEstimatedTime(taskDTO.getEstimatedTime())
            .setLoggedTime(taskDTO.getLoggedTime())
            .setStatus(this.taskStatusService.findById(taskDTO.getStatusId()))
            .setAssignedUser(this.userService.findById(taskDTO.getAssignedUserId()))
            .setStory(this.storyService.findById(taskDTO.getStoryId()))
        );
    }

    /**
     * Delete the task with the specified ID
     *
     * @param id The ID of task to delete
     */
    public void delete(Long id) {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        if (id == null) {
            throw new TechnicalException();
        }

        TaskEntity taskEntity = this.taskRepository.findById(id).orElse(null);

        if (taskEntity == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), taskEntity.getStory().getProject())
        ) {
            throw new BusinessException(TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        this.taskRepository.delete(taskEntity);
    }

    /**
     * Find all stories by the specified ids
     *
     * @param ids The IDs
     *
     * @return The stories
     */
    public Set<TaskEntity> findAllById(Set<Long> ids) {
        return new HashSet<>(this.taskRepository.findAllById(ids));
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
