package com.knife.agilemind.service.project;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.service.user.UserService;
import com.knife.agilemind.service.user.UserValidator;
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
     * Assert and get a project with the specified ID exists in database
     *
     * @param projectId The ID of project to check
     */
    public ProjectEntity findById(Long projectId) {
        ProjectEntity project = this.projectRepository.findById(projectId).orElse(null);

        BusinessAssert.notNull(project, ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        return project;
    }

    /**
     * Assert the specified DTO is valid to create a project in database
     *
     * @param dto The project to create
     */
    public void assertValid(CreateProjectDTO dto) {
        TechnicalAssert.notNull(dto);

        BusinessAssert.notNull(dto.getName(), ProjectConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        BusinessAssert.notEmpty(dto.getName(), ProjectConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified DTO is valid to update a project in database
     *
     * @param dto The project to update
     */
    public void assertValid(ProjectDTO dto) {
        TechnicalAssert.notNull(dto);

        BusinessAssert.notNull(dto.getId(), ProjectConstant.Error.ID_NULL, Status.BAD_REQUEST);
        BusinessAssert.notNull(dto.getName(), ProjectConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        BusinessAssert.notEmpty(dto.getName(), ProjectConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);

        this.assertValidUsers(dto.getAssignedUserIdList());
        this.assertValidUsers(dto.getAdminUserIdList());
    }

    /**
     * Assert the specified user can view the specified project.
     * The user can view the project if :
     * He is admin
     * OR he is a project admin of specified project
     * OR he is assigned to the specified project
     *
     * @param projectId The project id
     * @param userId    The user id
     * @param errorKey  The error key if error (Null for default value : PROJECT_NOT_FOUND)
     * @param status    The status if error (NULL for default value : NOT_FOUND)
     */
    public ProjectEntity assertUserCanView(Long projectId, Long userId, String errorKey, Status status) {
        TechnicalAssert.notNull(projectId);
        TechnicalAssert.notNull(userId);

        UserEntity loggedUser = this.userService.findById(userId);

        ProjectEntity project = this.findById(projectId);

        // If the user doesn't have the rights to view this project, throw error
        BusinessAssert.isTrue(this.userService.userIsAdmin(loggedUser) ||
                this.userIsProjectAdmin(project, loggedUser) ||
                this.userIsAssigned(project, loggedUser),
            (errorKey == null ? ProjectConstant.Error.NOT_FOUND : errorKey),
            (status == null ? Status.NOT_FOUND : status)
        );

        return project;
    }

    /**
     * Assert the specified user can view the specified project.
     * The user can view the project if :
     * He is admin
     * OR he is a project admin of specified project
     * OR he is assigned to the specified project
     *
     * @param projectId The project id
     * @param userId    The user id
     */
    public ProjectEntity assertUserCanView(Long projectId, Long userId) {
        return this.assertUserCanView(projectId, userId, null, null);
    }

    /**
     * Assert the logged user can view the specified project.
     * The user can view the project if :
     * He is admin
     * OR he is a project admin of specified project
     * OR he is assigned to the specified project
     *
     * @param projectId The project id
     * @param errorKey  The error key if error (Null for default value : PROJECT_NOT_FOUND)
     * @param status    The status if error (NULL for default value : NOT_FOUND)
     */
    public ProjectEntity assertLoggedUserCanView(Long projectId, String errorKey, Status status) {
        this.userService.assertLogged();

        UserEntity loggedUser = this.userService.getLoggedUser();

        ProjectEntity project = this.findById(projectId);

        // If the user doesn't have the rights to view this project, throw error
        BusinessAssert.isTrue(this.userService.userIsAdmin(loggedUser) ||
                this.userIsProjectAdmin(project, loggedUser) ||
                this.userIsAssigned(project, loggedUser),
            (errorKey == null ? ProjectConstant.Error.NOT_FOUND : errorKey),
            (status == null ? Status.NOT_FOUND : status)
        );

        return project;
    }

    /**
     * Assert the logged user can view the specified project.
     * The user can view the project if :
     * He is admin
     * OR he is a project admin of specified project
     * OR he is assigned to the specified project
     *
     * @param projectId The project id
     */
    public ProjectEntity assertLoggedUserCanView(Long projectId) {
        return this.assertLoggedUserCanView(projectId, null, null);
    }

    /**
     * Assert the logged user can edit or delete the specified project.
     * The user can edit or delete the project if :
     * He is admin
     * OR he is a project admin of specified project
     *
     * @param projectId The project id
     */
    public ProjectEntity assertLoggedUserCanEditOrDelete(Long projectId) {
        this.userService.assertLogged();

        UserEntity loggedUser = this.userService.getLoggedUser();

        ProjectEntity project = this.findById(projectId);

        // If the user doesn't have the rights to view this project, throw error
        BusinessAssert.isTrue(this.userService.userIsAdmin(loggedUser) || this.userIsProjectAdmin(project, loggedUser),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        return project;
    }

    /**
     * Assert the specified users are valid
     *
     * @param users The value to check
     */
    private void assertValidUsers(Set<Long> users) {
        if (users != null) {
            users.removeIf(Objects::isNull);
            users.forEach(userId -> userValidator.findById(userId));
        }
    }

    /**
     * Check if the specified user is assigned to the specified project
     *
     * @param project The project to check
     * @param user    The user
     *
     * @return TRUE if the specified user is assigned to the specified project, FALSE otherwise
     */
    private boolean userIsAssigned(ProjectEntity project, UserEntity user) {
        TechnicalAssert.notNull(project);
        TechnicalAssert.notNull(user);

        return this.containsId(project.getAssignedUsers(), user.getId());
    }

    /**
     * Check if the specified user is project admin of the specified project
     *
     * @param project The project to check
     * @param user    The user
     *
     * @return TRUE if the specified user is project admin of the specified project, FALSE otherwise
     */
    private boolean userIsProjectAdmin(ProjectEntity project, UserEntity user) {
        TechnicalAssert.notNull(project);
        TechnicalAssert.notNull(user);

        return this.containsId(project.getAdminUsers(), user.getId());
    }

    /**
     * Check if the specified user list contains the specified user id
     *
     * @param users The user list to check
     * @param id    The ID to find
     *
     * @return TRUE if the specified user id is not in the specified user list, FALSE otherwise
     */
    private boolean containsId(Set<UserEntity> users, Long id) {
        boolean isPresent = false;

        if (users != null && id != null) {
            for (UserEntity user : users) {
                if (user.getId().equals(id)) {
                    isPresent = true;
                    break;
                }
            }
        }

        return isPresent;
    }
}
