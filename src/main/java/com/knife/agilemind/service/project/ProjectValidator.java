package com.knife.agilemind.service.project;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.project.ProjectRepository;
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
     */
    public void assertValid(ProjectDTO projectDTO) {
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

        this.assertValidAssignatedUsers(projectDTO.getAssignatedUsers());
    }

    /**
     * Assert the specified user is assignated to the specified project
     *
     * @param user          The user
     * @param projectEntity The project to check
     */
    public void assertUserIsAssignated(UserEntity user, ProjectEntity projectEntity) {
        boolean isAssignated = false;

        if (user == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        if (user.getId() == null || projectEntity == null) {
            throw new TechnicalException();
        }

        if (projectEntity.getAssignatedUsers() != null) {
            for (UserEntity assignatedUser : projectEntity.getAssignatedUsers()) {
                if (assignatedUser.getId().equals(user.getId())) {
                    isAssignated = true;
                    break;
                }
            }
        }

        if (!isAssignated) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }
    }

    /**
     * Assert the specified assignated users is valid
     *
     * @param assignatedUsers The value to check
     */
    private void assertValidAssignatedUsers(Set<Long> assignatedUsers) {
        if (assignatedUsers != null) {
            assignatedUsers.removeIf(Objects::isNull);

            for (Long userId : assignatedUsers) {
                this.userValidator.findById(userId);
            }
        }
    }
}
