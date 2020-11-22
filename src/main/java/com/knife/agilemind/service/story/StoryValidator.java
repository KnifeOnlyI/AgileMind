package com.knife.agilemind.service.story;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.repository.story.StoryRepository;
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
public class StoryValidator {
    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryStatusService storyStatusService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StoryRepository storyRepository;

    /**
     * Asserts all commons data
     *
     * @param name          The name
     * @param points        The points
     * @param businessValue The business value
     * @param status        The status
     * @param project       The project id
     * @param assignedUser  The assigned user id
     */
    private void assertValidCommon(
        String name,
        Double points,
        Long businessValue,
        Long status,
        Long project,
        Long assignedUser
    ) {
        this.assertValidName(name);
        this.assertValidPoints(points);
        this.assertValidBusinessValue(businessValue);
        this.assertValidStatus(status);
        this.assertValidProject(project);
        this.assertValidUser(project, assignedUser);
        this.assertLoggedUserCanCreateOrUpdate(project);
    }

    /**
     * Assert the specified DTO is valid to create a story in database
     *
     * @param dto The story to create
     */
    public void assertValid(CreateStoryDTO dto) {
        this.assertNotNull(dto);

        assertValidCommon(
            dto.getName(),
            dto.getPoints(),
            dto.getBusinessValue(),
            dto.getStatusId(),
            dto.getProjectId(),
            dto.getAssignedUserId()
        );
    }

    /**
     * Assert the specified DTO is valid to update a story in database
     *
     * @param dto The story to update
     */
    public StoryEntity assertValid(StoryDTO dto) {
        this.assertNotNull(dto);
        this.assertValidID(dto.getId());
        assertValidCommon(dto.getName(), dto.getPoints(), dto.getBusinessValue(), dto.getStatusId(), dto.getProjectId(), dto.getAssignedUserId());

        return this.storyRepository.findById(dto.getId()).orElseThrow(TechnicalException::new);
    }

    /**
     * Assert the specified dto is not null
     *
     * @param dto The DTO to check
     */
    public void assertNotNull(CreateStoryDTO dto) {
        if (dto == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert the specified dto is not null
     *
     * @param dto The DTO to check
     */
    public void assertNotNull(StoryDTO dto) {
        if (dto == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert the specified story ID is valid (not null and exists)
     *
     * @param id The ID to check
     */
    public void assertValidID(Long id) {
        if (id == null) {
            throw new BusinessException(StoryConstant.Error.ID_NULL, Status.BAD_REQUEST);
        }

        StoryEntity storyEntity = this.storyRepository.findById(id).orElse(null);

        if (storyEntity == null) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert the specicied name is valid
     *
     * @param name The name to check
     */
    public void assertValidName(String name) {
        if (name == null) {
            throw new BusinessException(StoryConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        } else if (StringUtils.isBlank(name)) {
            throw new BusinessException(StoryConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied points is valid
     *
     * @param points The points to check
     */
    public void assertValidPoints(Double points) {
        if (points != null && points < 0) {
            throw new BusinessException(StoryConstant.Error.POINTS_LESS_0, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied business value is valid
     *
     * @param businessValue The business value to check
     */
    public void assertValidBusinessValue(Long businessValue) {
        if (businessValue != null && businessValue < 0) {
            throw new BusinessException(StoryConstant.Error.BUSINESS_VALUE_LESS_0, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specicied statusId is valid
     *
     * @param statusId The statusId to check
     */
    public void assertValidStatus(Long statusId) {
        if (statusId == null) {
            throw new BusinessException(StoryConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);
        }

        this.storyStatusService.assertExists(statusId);
    }

    /**
     * Assert the specicied projectId is valid
     *
     * @param projectId The projectId to check
     */
    public void assertValidProject(Long projectId) {
        if (projectId == null) {
            throw new BusinessException(StoryConstant.Error.PROJECT_ID_NULL, Status.BAD_REQUEST);
        }

        ProjectEntity project = this.projectRepository.findById(projectId).orElse(null);

        if (project == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert the specicied userId is valid
     *
     * @param projectId The project contains the story
     * @param userId    The userId to check
     */
    public void assertValidUser(Long projectId, Long userId) {
        if (userId != null) {
            ProjectEntity project = this.projectRepository.findById(projectId).orElseThrow(TechnicalException::new);
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
     * Check if the logged user can create or update the story
     *
     * @param projectId The project contains the story
     */
    public void assertLoggedUserCanCreateOrUpdate(Long projectId) {
        UserEntity loggedUser = this.userService.getLoggedUser();

        if (loggedUser == null) {
            throw new BusinessException(Status.BAD_REQUEST);
        }

        ProjectEntity project = this.projectRepository.findById(projectId).orElseThrow(TechnicalException::new);

        if (this.projectValidator.userIsNotAssigned(loggedUser, project)) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }
}
