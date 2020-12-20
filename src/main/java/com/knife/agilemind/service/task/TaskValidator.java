package com.knife.agilemind.service.task;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.task.TaskConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
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

/**
 * Service to manage stories validations
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class TaskValidator {

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StoryValidator storyValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    @Autowired
    private ProjectValidator projectValidator;

    /**
     * Check if the specified task id exists in database
     *
     * @param id The task id to check
     */
    public void assertExists(Long id) {
        BusinessAssert.isTrue(this.taskRepository.existsById(id), TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }

    /**
     * Assert the specified DTO is valid to create a task in database
     *
     * @param dto The task to create
     */
    public void assertValid(CreateTaskDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidName(dto.getName());
        this.assertValidEstimatedTime(dto.getEstimatedTime());
        this.assertValidLoggedTime(dto.getLoggedTime());
        this.assertValidStatus(dto.getStatus());
        this.assertValidStory(dto.getStory());
        this.assertValidUser(dto.getStory(), dto.getAssignedUser());
    }

    /**
     * Assert the specified DTO is valid to update a task in database
     *
     * @param dto The task to update
     */
    public void assertValid(TaskDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidId(dto.getId());
        this.assertValidName(dto.getName());
        this.assertValidEstimatedTime(dto.getEstimatedTime());
        this.assertValidLoggedTime(dto.getLoggedTime());
        this.assertValidStatus(dto.getStatus());
        this.assertValidStory(dto.getStory());
        this.assertValidUser(dto.getStory(), dto.getAssignedUser());
    }

    /**
     * Assert the logged user can perform all actions on tasks
     *
     * @param projectId The project id
     * @param errorKey  The error key if error (Null for default value : PROJECT_NOT_FOUND)
     * @param status    The status if error (NULL for default value : NOT_FOUND)
     */
    public void assertLoggedUserCanAll(Long projectId, String errorKey, Status status) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(projectId);

        ProjectEntity project = this.projectValidator.assertLoggedUserCanView(projectId, errorKey, status);

        TechnicalAssert.notNull(project);
        TechnicalAssert.notNull(project.getId());

        this.projectValidator.assertLoggedUserCanView(
            project.getId(),
            (errorKey == null ? StoryConstant.Error.NOT_FOUND : errorKey),
            (status == null ? Status.NOT_FOUND : status)
        );
    }

    /**
     * Assert the logged user can perform all actions on tasks
     *
     * @param projectId The story id
     */
    public void assertLoggedUserCanAll(Long projectId) {
        this.assertLoggedUserCanAll(projectId, null, null);
    }

    /**
     * Assert the specified task ID is valid (not null and exists)
     *
     * @param id The ID to check
     */
    private void assertValidId(Long id) {
        BusinessAssert.notNull(id, TaskConstant.Error.ID_NULL, Status.BAD_REQUEST);

        this.assertExists(id);
    }

    /**
     * Assert the specicied value is valid
     *
     * @param value The value to check
     */
    private void assertValidName(String value) {
        BusinessAssert.notNull(value, TaskConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        BusinessAssert.notEmpty(value, TaskConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
    }

    /**
     * Assert the specicied estimated time is valid
     *
     * @param value The estimated time to check
     */
    private void assertValidEstimatedTime(Long value) {
        BusinessAssert.greaterOrEqualsThan(value, 0L, TaskConstant.Error.ESTIMATED_TIME_LESS_0, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified logged time is valid
     *
     * @param value The value to check
     */
    private void assertValidLoggedTime(Long value) {
        BusinessAssert.greaterOrEqualsThan(value, 0L, TaskConstant.Error.LOGGED_TIME_LESS_0, Status.BAD_REQUEST);
    }

    /**
     * Assert the specicied value is valid
     *
     * @param value The value to check
     */
    private void assertValidStatus(Long value) {
        BusinessAssert.notNull(value, TaskConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);

        this.taskStatusService.assertExists(value);
    }

    /**
     * Assert the specicied value is valid
     *
     * @param value The value to check
     */
    private void assertValidStory(Long value) {
        BusinessAssert.notNull(value, TaskConstant.Error.STORY_ID_NULL, Status.BAD_REQUEST);

        this.storyValidator.assertExists(value);
    }

    /**
     * Assert the specicied story is valid
     *
     * @param storyId The story id contains the task
     * @param userId  The user id to check
     */
    private void assertValidUser(Long storyId, Long userId) {
        if (userId != null) {
            TechnicalAssert.notNull(storyId);

            this.storyValidator.assertUserCanView(storyId, userId, ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
        }
    }
}
