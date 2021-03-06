package com.knife.agilemind.service.story;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.story.StoryRepository;
import com.knife.agilemind.service.project.ProjectService;
import com.knife.agilemind.service.project.ProjectValidator;
import com.knife.agilemind.service.release.ReleaseService;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class StoryService {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryValidator storyValidator;

    @Autowired
    private StoryStatusService storyStatusService;

    @Autowired
    private StoryTypeService storyTypeService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private ReleaseService releaseService;

    /**
     * Create the specified story in database
     *
     * @param storyDTO The DTO of story to create
     *
     * @return The created story
     */
    public StoryDTO create(CreateStoryDTO storyDTO) {
        this.userService.assertLogged();

        this.storyValidator.assertValid(storyDTO);
        this.storyValidator.assertLoggedUserCanCreateOrUpdateOrDelete(storyDTO.getProject());

        StoryEntity story = new StoryEntity()
            .setName(storyDTO.getName())
            .setDescription(storyDTO.getDescription())
            .setPoints(storyDTO.getPoints())
            .setBusinessValue(storyDTO.getBusinessValue())
            .setType(this.storyTypeService.findById(storyDTO.getType()))
            .setProject(this.projectService.findById(storyDTO.getProject()));

        if (storyDTO.getStatus() != null) {
            story.setStatus(this.storyStatusService.findById(storyDTO.getStatus()));
        }

        if (storyDTO.getAssignedUser() != null) {
            story.setAssignedUser(this.userService.findById(storyDTO.getAssignedUser()));
        }

        if (storyDTO.getRelease() != null) {
            story.setRelease(this.releaseService.findById(storyDTO.getRelease()));
        }

        return this.toDTO(this.storyRepository.save(story));
    }

    /**
     * Get the story identified by the specified ID
     *
     * @param id The ID
     *
     * @return The story
     */
    public StoryDTO get(Long id) {
        return this.toDTO(this.findById(id));
    }

    /**
     * Get all stories from the specified project
     *
     * @param projectId The project ID
     *
     * @return story list
     */
    public List<StoryDTO> getAllFromProject(Long projectId) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(projectId);

        this.projectValidator.assertLoggedUserCanView(projectId);

        List<StoryDTO> results = new ArrayList<>();

        ProjectEntity project = this.projectService.findById(projectId);

        for (StoryEntity story : project.getStories()) {
            results.add(this.toDTO(story));
        }

        return results;
    }

    /**
     * Update the specified story in database
     *
     * @param storyDTO The DTO of story to update
     *
     * @return The updated story
     */
    public StoryDTO update(StoryDTO storyDTO) {
        this.userService.assertLogged();

        TechnicalAssert.notNull(storyDTO);

        this.storyValidator.assertValid(storyDTO);

        StoryEntity story = this.findById(storyDTO.getId())
            .setName(storyDTO.getName())
            .setDescription(storyDTO.getDescription())
            .setPoints(storyDTO.getPoints())
            .setBusinessValue(storyDTO.getBusinessValue())
            .setType(this.storyTypeService.findById(storyDTO.getType()))
            .setProject(this.projectService.findById(storyDTO.getProject()));

        if (storyDTO.getStatus() != null) {
            story.setStatus(this.storyStatusService.findById(storyDTO.getStatus()));
        } else {
            story.setStatus(null);
        }

        if (storyDTO.getAssignedUser() != null) {
            story.setAssignedUser(this.userService.findById(storyDTO.getAssignedUser()));
        } else {
            story.setAssignedUser(null);
        }

        if (storyDTO.getRelease() != null) {
            story.setRelease(this.releaseService.findById(storyDTO.getRelease()));
        } else {
            story.setRelease(null);
        }

        return this.toDTO(story);
    }

    /**
     * Delete the story with the specified ID
     *
     * @param id The ID of story to delete
     */
    public void delete(Long id) {
        this.storyRepository.delete(this.findById(id));
    }

    /**
     * Find story in database by the specified ID
     *
     * @param id The stiry id to find
     */
    public StoryEntity findById(Long id) {
        return this.storyValidator.assertLoggedUserCanView(id);
    }

    /**
     * Find all stories by the specified ids
     *
     * @param ids The IDs
     *
     * @return The stories
     */
    public Set<StoryEntity> findAllById(Set<Long> ids) {
        return new HashSet<>(this.storyRepository.findAllById(ids));
    }

    /**
     * Convert the entities to IDs
     *
     * @param entities The entities
     *
     * @return The IDs
     */
    public Set<Long> toIds(Set<StoryEntity> entities) {
        Set<Long> ids = new HashSet<>();

        if (entities != null) {
            for (StoryEntity entity : entities) {
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
    public StoryDTO toDTO(StoryEntity entity) {
        StoryDTO results = new StoryDTO();

        if (entity != null) {
            results.setId(entity.getId());
            results.setName(entity.getName());
            results.setDescription(entity.getDescription());
            results.setPoints(entity.getPoints());
            results.setBusinessValue(entity.getBusinessValue());
            results.setStatus(entity.getStatus() != null ? entity.getStatus().getId() : null);
            results.setType(entity.getType() != null ? entity.getType().getId() : null);
            results.setAssignedUser(entity.getAssignedUser() != null ? entity.getAssignedUser().getId() : null);
            results.setRelease(entity.getRelease() != null ? entity.getRelease().getId() : null);
            results.setProject(entity.getProject() != null ? entity.getProject().getId() : null);
        }

        return results;
    }
}
