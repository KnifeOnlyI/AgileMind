package com.knife.agilemind.service.task;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.task.TaskConstant;
import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.task.TaskEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.task.CreateTaskDTO;
import com.knife.agilemind.dto.task.TaskDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.repository.story.StoryRepository;
import com.knife.agilemind.repository.task.TaskRepository;
import com.knife.agilemind.repository.user.UserRepository;
import com.knife.agilemind.service.project.ProjectValidator;
import com.knife.agilemind.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
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
    private ProjectValidator projectValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskStatusService taskStatusService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StoryRepository storyRepository;

    /**
     * Asserts all commons data
     *
     * @param name          The name
     * @param points        The points
     * @param businessValue The business value
     * @param status        The status
     * @param storyId       The story id
     * @param assignedUser  The assigned user id
     */
    private void assertValidCommon(
        String name,
        Long points,
        Long businessValue,
        Long status,
        Long storyId,
        Long assignedUser
    ) {
        this.assertValidName(name);
        this.assertValidPoints(points);
        this.assertValidBusinessValue(businessValue);
        this.assertValidStatus(status);
        this.assertValidStory(storyId);
        this.assertValidUser(storyId, assignedUser);
        this.assertLoggedUserCanCreateOrUpdate(storyId);
    }

    /**
     * Assert the specified DTO is valid to create a task in database
     *
     * @param dto The task to create
     */
    public void assertValid(CreateTaskDTO dto) {
        this.assertNotNull(dto);

        assertValidCommon(
            dto.getName(),
            dto.getEstimatedTime(),
            dto.getLoggedTime(),
            dto.getStatusId(),
            dto.getStoryId(),
            dto.getAssignedUserId()
        );
    }

    /**
     * Assert the specified DTO is valid to update a task in database
     *
     * @param dto The task to update
     */
    public TaskEntity assertValid(TaskDTO dto) {
        this.assertNotNull(dto);
        this.assertValidID(dto.getId());
        assertValidCommon(dto.getName(), dto.getEstimatedTime(), dto.getLoggedTime(), dto.getStatusId(), dto.getStoryId(), dto.getAssignedUserId());

        return this.taskRepository.findById(dto.getId()).orElseThrow(TechnicalException::new);
    }

    /**
     * Assert the specified dto is not null
     *
     * @param dto The DTO to check
     */
    public void assertNotNull(CreateTaskDTO dto) {
        if (dto == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert the specified dto is not null
     *
     * @param dto The DTO to check
     */
    public void assertNotNull(TaskDTO dto) {
        if (dto == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert the specified task ID is valid (not null and exists)
     *
     * @param id The ID to check
     */
    public void assertValidID(Long id) {
        if (id == null) {
            throw new BusinessException(TaskConstant.Error.ID_NULL, Status.BAD_REQUEST);
        }

        TaskEntity taskEntity = this.taskRepository.findById(id).orElse(null);

        if (taskEntity == null) {
            throw new BusinessException(TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert the specicied name is valid
     *
     * @param name The name to check
     */
    public void assertValidName(String name) {
        if (name == null) {
            throw new BusinessException(TaskConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        } else if (StringUtils.isBlank(name)) {
            throw new BusinessException(TaskConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied points is valid
     *
     * @param points The points to check
     */
    public void assertValidPoints(Long points) {
        if (points != null && points < 0) {
            throw new BusinessException(TaskConstant.Error.ESTIMATED_TIME_LESS_0, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied business value is valid
     *
     * @param businessValue The business value to check
     */
    public void assertValidBusinessValue(Long businessValue) {
        if (businessValue != null && businessValue < 0) {
            throw new BusinessException(TaskConstant.Error.LOGGED_TIME_LESS_0, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied statusId is valid
     *
     * @param statusId The statusId to check
     */
    public void assertValidStatus(Long statusId) {
        if (statusId == null) {
            throw new BusinessException(TaskConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);
        }

        this.taskStatusService.assertExists(statusId);
    }

    /**
     * Assert the specicied storyId is valid
     *
     * @param storyId The storyId to check
     */
    public void assertValidStory(Long storyId) {
        if (storyId == null) {
            throw new BusinessException(TaskConstant.Error.STORY_ID_NULL, Status.BAD_REQUEST);
        }

        StoryEntity story = this.storyRepository.findById(storyId).orElse(null);

        if (story == null) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert the specicied story is valid
     *
     * @param storyId The story id contains the task
     * @param userId  The user id to check
     */
    public void assertValidUser(Long storyId, Long userId) {
        if (userId != null) {
            StoryEntity story = this.storyRepository.findById(storyId).orElseThrow(TechnicalException::new);
            ProjectEntity project = this.projectRepository.findById(story.getProject().getId()).orElseThrow(TechnicalException::new);
            UserEntity assignedUser = this.userRepository.findById(userId).orElse(null);

            if (assignedUser == null) {
                throw new BusinessException(UserConstant.Error.NOT_FOUND, Status.BAD_REQUEST);
            }

            if (this.projectValidator.userIsNotAssigned(assignedUser, project)) {
                throw new BusinessException(ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
            }
        }
    }

    /**
     * Check if the logged user can create or update the task
     *
     * @param storyId The story contains the task
     */
    public void assertLoggedUserCanCreateOrUpdate(Long storyId) {
        UserEntity loggedUser = this.userService.getLoggedUser();

        if (loggedUser == null) {
            throw new BusinessException(Status.BAD_REQUEST);
        }

        StoryEntity story = this.storyRepository.findById(storyId).orElseThrow(TechnicalException::new);
        ProjectEntity project = this.projectRepository.findById(story.getProject().getId()).orElseThrow(TechnicalException::new);

        if (this.projectValidator.userIsNotAssigned(loggedUser, project)) {
            throw new BusinessException(TaskConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }
}
