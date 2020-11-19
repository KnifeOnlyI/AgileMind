package com.knife.agilemind.service.story;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import com.knife.agilemind.repository.story.StoryRepository;
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
    private ProjectService projectService;

    @Autowired
    private ProjectValidator projectValidator;

    /**
     * Create the specified story in database
     *
     * @param createStoryDTO The DTO of story to create
     *
     * @return The created story
     */
    public StoryDTO create(CreateStoryDTO createStoryDTO) {
        this.storyValidator.assertValid(createStoryDTO);

        return this.toDTO(this.storyRepository.save(new StoryEntity()
            .setName(createStoryDTO.getName())
            .setDescription(createStoryDTO.getDescription())
            .setPoints(createStoryDTO.getPoints())
            .setBusinessValue(createStoryDTO.getBusinessValue())
            .setStatus(this.storyStatusService.findById(createStoryDTO.getStatusId()))
            .setAssignedUser(this.userService.findById(createStoryDTO.getAssignedUserId()))
            .setProject(this.projectService.findById(createStoryDTO.getProjectId()))
        ));
    }

    /**
     * Get the story identified by the specified ID
     *
     * @param id The ID
     *
     * @return The story
     */
    public StoryDTO get(Long id) {
        if (id == null) {
            throw new TechnicalException();
        }

        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        StoryEntity storyEntity = this.storyRepository.findById(id).orElse(null);

        // If the story's not exists or user is not assigned, throw "STORY_NOT_FOUND" error
        if (storyEntity == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), storyEntity.getProject())
        ) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return this.toDTO(storyEntity);
    }

    /**
     * Get all stories from the specified project
     *
     * @param projectId The project ID
     *
     * @return story list
     */
    public List<StoryDTO> getAllFromProject(Long projectId) {
        List<StoryDTO> results = new ArrayList<>();

        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        ProjectEntity project = this.projectService.findById(projectId);

        // If the project's not exists or user is not assigned, throw "PROJECT_NOT_FOUND" error
        if (project == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), project)
        ) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

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
        return this.toDTO(this.storyValidator.assertValid(storyDTO)
            .setName(storyDTO.getName())
            .setDescription(storyDTO.getDescription())
            .setPoints(storyDTO.getPoints())
            .setBusinessValue(storyDTO.getBusinessValue())
            .setStatus(this.storyStatusService.findById(storyDTO.getStatusId()))
            .setAssignedUser(this.userService.findById(storyDTO.getAssignedUserId()))
            .setProject(this.projectService.findById(storyDTO.getProjectId()))
        );
    }

    /**
     * Delete the story with the specified ID
     *
     * @param id The ID of story to delete
     */
    public void delete(Long id) {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        if (id == null) {
            throw new TechnicalException();
        }

        StoryEntity storyEntity = this.storyRepository.findById(id).orElse(null);

        if (storyEntity == null ||
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), storyEntity.getProject())
        ) {
            throw new BusinessException(StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        this.storyRepository.delete(storyEntity);
    }

    /**
     * Find story in database by the specified ID
     *
     * @param id The stiry id to find
     */
    public StoryEntity findById(Long id) {
        StoryEntity results = null;

        if (id != null) {
            results = this.storyRepository.findById(id).orElse(null);
        }


        return results;
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

            if (entity.getStatus() != null) {
                results.setStatusId(entity.getStatus().getId());
            }

            if (entity.getAssignedUser() != null) {

                results.setAssignedUserId(entity.getAssignedUser().getId());
            }

            if (entity.getProject() != null) {
                results.setProjectId(entity.getProject().getId());
            }
        }

        return results;
    }
}
