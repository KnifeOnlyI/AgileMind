package com.knife.agilemind.service.release;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.release.CreateReleaseDTO;
import com.knife.agilemind.dto.release.ReleaseDTO;
import com.knife.agilemind.dto.release.UpdateReleaseDTO;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.release.ReleaseRepository;
import com.knife.agilemind.service.project.ProjectService;
import com.knife.agilemind.service.project.ProjectValidator;
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
public class ReleaseService {
    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private ReleaseValidator releaseValidator;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    /**
     * Get the story identified by the specified ID
     *
     * @param id The ID
     *
     * @return The story
     */
    public ReleaseDTO get(Long id) {
        return this.toDTO(this.findById(id));
    }

    /**
     * Get all releases from the specified project
     *
     * @param projectId The project ID
     *
     * @return Releases
     */
    public List<ReleaseDTO> getAllFromProject(Long projectId) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(projectId);

        ProjectEntity project = this.projectValidator.assertLoggedUserCanView(
            projectId, ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND
        );

        List<ReleaseDTO> results = new ArrayList<>();

        for (ReleaseEntity release : project.getReleases()) {
            results.add(this.toDTO(release));
        }

        return results;
    }

    /**
     * Create the specified release in database
     *
     * @param dto The DTO of release to create
     *
     * @return The created release
     */
    public ReleaseDTO create(CreateReleaseDTO dto) {
        this.userService.assertLogged();

        this.releaseValidator.assertValid(dto);
        this.releaseValidator.assertLoggedUserCanCreateOrUpdateOrDelete(dto.getProject());

        ReleaseEntity story = new ReleaseEntity()
            .setName(dto.getName())
            .setDescription(dto.getDescription())
            .setDate(dto.getDate())
            .setProject(this.projectService.findById(dto.getProject()));

        return this.toDTO(this.releaseRepository.save(story));
    }

    /**
     * Update the specified release in database
     *
     * @param dto The DTO of release to update
     *
     * @return The updated release
     */
    public ReleaseDTO update(UpdateReleaseDTO dto) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(dto);

        this.releaseValidator.assertValid(dto);

        ReleaseEntity story = this.findById(dto.getId())
            .setName(dto.getName())
            .setDescription(dto.getDescription())
            .setDate(dto.getDate());

        return this.toDTO(story);
    }

    /**
     * Delete the story with the specified ID
     *
     * @param id The ID of story to delete
     */
    public void delete(Long id) {
        ReleaseEntity release = this.findById(id);

        release.getStories().forEach(storyEntity -> storyEntity.setRelease(null));

        this.releaseRepository.delete(release);
    }

    /**
     * Find release in database by the specified ID
     *
     * @param id The release id to find
     */
    public ReleaseEntity findById(Long id) {
        return this.releaseValidator.assertLoggedUserCanView(id);
    }

    /**
     * Convert the entities to IDs
     *
     * @param entities The entities
     *
     * @return The IDs
     */
    public Set<Long> toIds(Set<ReleaseEntity> entities) {
        Set<Long> ids = new HashSet<>();

        if (entities != null) {
            for (ReleaseEntity entity : entities) {
                if (entity != null) {
                    ids.add(entity.getId());
                }
            }
        }

        return ids;
    }

    /**
     * Convert the specified entity to DTO
     *
     * @param entity The entity
     *
     * @return The DTO
     */
    public ReleaseDTO toDTO(ReleaseEntity entity) {
        ReleaseDTO results = new ReleaseDTO();

        if (entity != null) {
            results.setId(entity.getId());
            results.setName(entity.getName());
            results.setDescription(entity.getDescription());
            results.setDate(entity.getDate());

            if (entity.getStories() != null) {
                for (StoryEntity story : entity.getStories()) {
                    results.getStories().add(story.getId());
                }
            }

            if (entity.getProject() != null) {
                results.setProject(entity.getProject().getId());
            }
        }

        return results;
    }
}
