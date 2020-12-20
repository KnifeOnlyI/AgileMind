package com.knife.agilemind.service.story;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.story.StoryRepository;
import com.knife.agilemind.service.project.ProjectService;
import com.knife.agilemind.service.project.ProjectValidator;
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
public class StoryValidator {
    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryStatusService storyStatusService;

    @Autowired
    private StoryTypeService storyTypeService;

    @Autowired
    private StoryRepository storyRepository;

    /**
     * Check if the specified story id exists in database
     *
     * @param id The story id to check
     */
    public void assertExists(Long id) {
        BusinessAssert.isTrue(this.storyRepository.existsById(id), StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }

    /**
     * Assert the specified DTO is valid to create a story in database
     *
     * @param dto The story to create
     */
    public void assertValid(CreateStoryDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidName(dto.getName());
        this.assertValidPoints(dto.getPoints());
        this.assertValidBusinessValue(dto.getBusinessValue());
        this.assertValidStatus(dto.getStatus());
        this.assertValidType(dto.getType());
        this.assertValidProject(dto.getProject());
        this.assertValidUser(dto.getProject(), dto.getAssignedUser());
    }

    /**
     * Assert the specified DTO is valid to update a story in database
     *
     * @param dto The story to update
     */
    public void assertValid(StoryDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidId(dto.getId());
        this.assertValidName(dto.getName());
        this.assertValidPoints(dto.getPoints());
        this.assertValidBusinessValue(dto.getBusinessValue());
        this.assertValidStatus(dto.getStatus());
        this.assertValidType(dto.getType());
        this.assertValidProject(dto.getProject());
        this.assertValidUser(dto.getProject(), dto.getAssignedUser());
    }

    /**
     * Assert the specified story ID is valid (not null and exists)
     *
     * @param id The ID to check
     */
    private void assertValidId(Long id) {
        BusinessAssert.notNull(id, StoryConstant.Error.ID_NULL, Status.BAD_REQUEST);

        this.assertExists(id);
    }

    /**
     * Assert the specified name is valid
     *
     * @param name The name to check
     */
    private void assertValidName(String name) {
        BusinessAssert.notNull(name, StoryConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        BusinessAssert.notEmpty(name, StoryConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified points is valid
     *
     * @param points The points to check
     */
    private void assertValidPoints(Double points) {
        BusinessAssert.greaterOrEqualsThan(points, 0d, StoryConstant.Error.POINTS_LESS_0, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified business value is valid
     *
     * @param businessValue The business value to check
     */
    private void assertValidBusinessValue(Long businessValue) {
        BusinessAssert.greaterOrEqualsThan(businessValue, 0L, StoryConstant.Error.BUSINESS_VALUE_LESS_0, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified statusId is valid
     *
     * @param statusId The statusId to check
     */
    private void assertValidStatus(Long statusId) {
        BusinessAssert.notNull(statusId, StoryConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);

        this.storyStatusService.assertExists(statusId);
    }

    /**
     * Assert the specified statusId is valid
     *
     * @param typeId The typeId to check
     */
    private void assertValidType(Long typeId) {
        BusinessAssert.notNull(typeId, StoryConstant.Error.TYPE_ID_NULL, Status.BAD_REQUEST);

        this.storyTypeService.assertExists(typeId);
    }

    /**
     * Assert the specified projectId is valid
     *
     * @param projectId The projectId to check
     */
    private void assertValidProject(Long projectId) {
        BusinessAssert.notNull(projectId, StoryConstant.Error.PROJECT_ID_NULL, Status.BAD_REQUEST);

        this.projectService.findById(projectId);
    }

    /**
     * Assert the specified userId is valid
     *
     * @param projectId The project contains the story
     * @param userId    The userId to check
     */
    private void assertValidUser(Long projectId, Long userId) {
        TechnicalAssert.notNull(projectId);

        if (userId != null) {
            this.projectValidator.assertUserCanView(projectId, userId, ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
        }
    }

    /**
     * Check if the logged user can view the specified story
     *
     * @param storyId  The story id to check
     * @param errorKey The error key if error (Null for default value : PROJECT_NOT_FOUND)
     * @param status   The status if error (NULL for default value : NOT_FOUND)
     */
    public StoryEntity assertLoggedUserCanView(Long storyId, String errorKey, Status status) {
        this.userService.assertLogged();

        StoryEntity story = this.storyRepository.findById(storyId).orElse(null);

        BusinessAssert.notNull(story, StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        TechnicalAssert.notNull(story.getProject());
        TechnicalAssert.notNull(story.getProject().getId());

        this.projectValidator.assertLoggedUserCanView(
            story.getProject().getId(),
            (errorKey == null ? StoryConstant.Error.NOT_FOUND : errorKey),
            (status == null ? Status.NOT_FOUND : status)
        );

        return story;
    }

    /**
     * Check if the logged user can view the specified story
     *
     * @param storyId The story id to check
     */
    public StoryEntity assertLoggedUserCanView(Long storyId) {
        return this.assertLoggedUserCanView(storyId, null, null);
    }

    /**
     * Check if the logged user can view the specified story
     *
     * @param storyId The story id to check
     * @param userId  The user id to check
     */
    public StoryEntity assertUserCanView(Long storyId, Long userId, String errorKey, Status status) {
        TechnicalAssert.notNull(storyId);
        TechnicalAssert.notNull(userId);

        StoryEntity story = this.storyRepository.findById(storyId).orElse(null);

        BusinessAssert.notNull(story, StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        TechnicalAssert.notNull(story.getProject());
        TechnicalAssert.notNull(story.getProject().getId());

        this.projectValidator.assertUserCanView(story.getProject().getId(), userId, errorKey, status);

        return story;
    }

    /**
     * Check if the logged user can view the specified story
     *
     * @param storyId The story id to check
     * @param userId  The user id to check
     */
    public StoryEntity assertUserCanView(Long storyId, Long userId) {
        return this.assertUserCanView(storyId, userId, null, null);
    }

    /**
     * Check if the logged user can create or update stories
     *
     * @param projectId The project contains the story
     */
    public void assertLoggedUserCanCreateOrUpdateOrDelete(Long projectId) {
        this.userService.assertLogged();

        this.projectValidator.assertLoggedUserCanView(projectId);
    }
}
