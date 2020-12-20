package com.knife.agilemind.service.release;

import com.knife.agilemind.constant.release.ReleaseConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.dto.release.CreateReleaseDTO;
import com.knife.agilemind.dto.release.UpdateReleaseDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.release.ReleaseRepository;
import com.knife.agilemind.service.project.ProjectService;
import com.knife.agilemind.service.project.ProjectValidator;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

/**
 * Service to manage releases validations
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class ReleaseValidator {
    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReleaseRepository releaseRepository;

    /**
     * Check if the specified release id exists in database
     *
     * @param id The release id to check
     */
    public void assertExists(Long id) {
        BusinessAssert.isTrue(this.releaseRepository.existsById(id), ReleaseConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }

    /**
     * Assert the specified DTO is valid to create a release in database
     *
     * @param dto The release to create
     */
    public void assertValid(CreateReleaseDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidName(dto.getName());
        this.assertValidProject(dto.getProject());
    }

    /**
     * Assert the specified DTO is valid to update a release in database
     *
     * @param dto The release to update
     */
    public void assertValid(UpdateReleaseDTO dto) {
        TechnicalAssert.notNull(dto);

        this.assertValidId(dto.getId());
        this.assertValidName(dto.getName());
    }

    /**
     * Assert the specified release ID is valid (not null and exists)
     *
     * @param id The ID to check
     */
    private void assertValidId(Long id) {
        BusinessAssert.notNull(id, ReleaseConstant.Error.ID_NULL, Status.BAD_REQUEST);

        this.assertExists(id);
    }

    /**
     * Assert the specified name is valid
     *
     * @param name The name to check
     */
    private void assertValidName(String name) {
        BusinessAssert.notNull(name, ReleaseConstant.Error.NAME_NULL, Status.BAD_REQUEST);
        BusinessAssert.notEmpty(name, ReleaseConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);
    }

    /**
     * Assert the specified projectId is valid
     *
     * @param projectId The projectId to check
     */
    private void assertValidProject(Long projectId) {
        BusinessAssert.notNull(projectId, ReleaseConstant.Error.PROJECT_ID_NULL, Status.BAD_REQUEST);

        this.projectService.findById(projectId);
    }

    /**
     * Check if the logged user can view the specified release
     *
     * @param id       The id to check
     * @param errorKey The error key if error (Null for default value : PROJECT_NOT_FOUND)
     * @param status   The status if error (NULL for default value : NOT_FOUND)
     */
    public ReleaseEntity assertLoggedUserCanView(Long id, String errorKey, Status status) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        ReleaseEntity release = this.releaseRepository.findById(id).orElse(null);

        BusinessAssert.notNull(release, ReleaseConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        TechnicalAssert.notNull(release.getProject());
        TechnicalAssert.notNull(release.getProject().getId());

        this.projectValidator.assertLoggedUserCanView(
            release.getProject().getId(),
            (errorKey == null ? ReleaseConstant.Error.NOT_FOUND : errorKey),
            (status == null ? Status.NOT_FOUND : status)
        );

        return release;
    }

    /**
     * Check if the logged user can view the specified release
     *
     * @param id The release id to check
     */
    public ReleaseEntity assertLoggedUserCanView(Long id) {
        return this.assertLoggedUserCanView(id, null, null);
    }

    /**
     * Check if the logged user can view the specified release
     *
     * @param id     The id to check
     * @param userId The user id to check
     */
    public ReleaseEntity assertUserCanView(Long id, Long userId, String errorKey, Status status) {
        TechnicalAssert.notNull(id);
        TechnicalAssert.notNull(userId);

        ReleaseEntity release = this.releaseRepository.findById(id).orElse(null);

        BusinessAssert.notNull(release, StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        TechnicalAssert.notNull(release.getProject());
        TechnicalAssert.notNull(release.getProject().getId());

        this.projectValidator.assertUserCanView(release.getProject().getId(), userId, errorKey, status);

        return release;
    }

    /**
     * Check if the logged user can view the specified release
     *
     * @param id     The id to check
     * @param userId The user id to check
     */
    public ReleaseEntity assertUserCanView(Long id, Long userId) {
        return this.assertUserCanView(id, userId, null, null);
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
