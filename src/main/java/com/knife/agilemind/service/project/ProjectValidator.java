package com.knife.agilemind.service.project;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.service.user.UserService;
import com.knife.agilemind.service.user.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.Objects;
import java.util.Set;

/**
 * Service to manage project validations
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class ProjectValidator {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    /**
     * Assert the specified ID is not null
     *
     * @param projectId The project ID to check
     */
    public void assertNotNullId(Long projectId) {
        if (projectId == null) {
            throw new TechnicalException();
        }
    }

    /**
     * Assert and get a project with the specified ID exists in database
     *
     * @param projectId The ID of project to check
     */
    public ProjectEntity findById(Long projectId) {
        ProjectEntity projectEntity = this.projectRepository.findById(projectId).orElse(null);

        if (projectEntity == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return projectEntity;
    }

    /**
     * Assert the specified DTO is valid to create a project in database
     *
     * @param createProjectDTO The project to create
     */
    public void assertValid(CreateProjectDTO createProjectDTO) {
        if (createProjectDTO == null) {
            throw new TechnicalException();
        }

        if (createProjectDTO.getName() == null) {
            throw new BusinessException(ProjectConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        } else if (StringUtils.isBlank(createProjectDTO.getName())) {
            throw new BusinessException(ProjectConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
        }
    }

    /**
     * Assert the specified DTO is valid to update a project in database
     *
     * @param projectDTO The project to update
     *
     * @return The project entity to update
     */
    public ProjectEntity assertValid(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            throw new TechnicalException();
        }

        if (projectDTO.getId() == null) {
            throw new BusinessException(ProjectConstant.Error.ID_NULL, Status.BAD_REQUEST);
        }

        if (projectDTO.getName() == null) {
            throw new BusinessException(ProjectConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        } else if (StringUtils.isBlank(projectDTO.getName())) {
            throw new BusinessException(ProjectConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
        }

        this.assertValidAssignedUsers(projectDTO.getAssignedUserIdList());

        ProjectEntity projectEntity = this.projectRepository.findById(projectDTO.getId()).orElse(null);

        if (projectEntity == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return projectEntity;
    }

    /**
     * Assert the specified user is an admin or is assigned to the specified project
     *
     * @param user    The user
     * @param project The project to check
     */
    public boolean userIsNotAssigned(UserEntity user, ProjectEntity project) {
        boolean isAssigned = false;

        if (user == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        if (user.getId() == null || project == null) {
            throw new TechnicalException();
        }

        if (project.getAssignedUsers() != null) {
            for (UserEntity assignedUser : project.getAssignedUsers()) {
                if (assignedUser.getId().equals(user.getId())) {
                    isAssigned = true;
                    break;
                }
            }
        }

        return !isAssigned && !this.userService.userIsAdmin(user);
    }

    /**
     * Assert the specified assigned users is valid
     *
     * @param assignedUsers The value to check
     */
    private void assertValidAssignedUsers(Set<Long> assignedUsers) {
        if (assignedUsers != null) {
            assignedUsers.removeIf(Objects::isNull);
            assignedUsers.forEach(userId -> userValidator.assertExists(userId));
        }
    }
}
