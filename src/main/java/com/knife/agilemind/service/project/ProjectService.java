package com.knife.agilemind.service.project;

import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.user.UserEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.service.story.StoryService;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service to manage the projects
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private StoryService storyService;

    /**
     * Get the project identified by the specified ID
     *
     * @param id The ID
     *
     * @return The project
     */
    public ProjectDTO get(Long id) {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        this.projectValidator.assertNotNullId(id);

        ProjectEntity projectEntity = this.projectValidator.findById(id);

        if (!this.userService.currentIsAdmin() &&
            this.projectValidator.userIsNotAssigned(this.userService.getLoggedUser(), projectEntity)
        ) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return this.toDTO(projectEntity);
    }

    /**
     * Get all projects
     *
     * @return The projects
     */
    public List<ProjectDTO> getAll() {
        List<ProjectEntity> projectList;

        if (this.userService.currentIsAdmin()) {
            projectList = this.projectRepository.findAll();
        } else {
            projectList = this.projectRepository.getAllByAssignedUsersContains(this.userService.getLoggedUser());
        }

        return this.toDTOs(projectList);
    }

    /**
     * Create the specified project in database
     *
     * @param createProjectDTO The DTO of project to create
     *
     * @return The created project
     */
    public ProjectDTO create(CreateProjectDTO createProjectDTO) {
        if (!this.userService.currentIsAdmin()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        this.projectValidator.assertValid(createProjectDTO);

        ProjectEntity projectEntity = this.projectRepository.save(new ProjectEntity()
            .setName(createProjectDTO.getName())
            .setDescription(createProjectDTO.getDescription()));

        return this.toDTO(projectEntity);
    }

    /**
     * Update the specified project in database
     *
     * @param projectDTO The DTO of project to update
     *
     * @return The updated project
     */
    public ProjectDTO update(ProjectDTO projectDTO) {
        if (!this.userService.currentIsAdmin()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        ProjectEntity projectEntity = this.projectValidator.assertValid(projectDTO)
            .setName(projectDTO.getName())
            .setDescription(projectDTO.getDescription())
            .setAssignedUsers(this.userService.findAllById(projectDTO.getAssignedUserIdList()));

        Set<StoryEntity> stories = this.storyService.findAllById(projectDTO.getStoryIdList());

        for (StoryEntity storyEntity : stories) {
            projectEntity.getStories().add(storyEntity);
        }

        return this.toDTO(projectEntity);
    }

    /**
     * Delete the project with the specified ID
     *
     * @param id The ID of project to delete
     */
    public void delete(Long id) {
        if (!this.userService.currentIsAdmin()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        this.projectValidator.assertNotNullId(id);

        this.projectRepository.delete(this.projectValidator.findById(id));
    }

    /**
     * Find project in database by the specified ID
     *
     * @param id The project id to find
     */
    public ProjectEntity findById(Long id) {
        ProjectEntity results = null;

        if (id != null) {
            results = this.projectRepository.findById(id).orElse(null);
        }


        return results;
    }

    /**
     * Find project in database by the specified ID (throw error if not found)
     *
     * @param id The project id to find
     */
    public ProjectEntity findExistingById(Long id) {
        ProjectEntity results = this.findById(id);

        if (results == null) {
            throw new BusinessException(ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND);
        }

        return results;
    }

    /**
     * Convert the specified entity to DTO
     *
     * @param entity The entity
     *
     * @return The DTO
     */
    public ProjectDTO toDTO(ProjectEntity entity) {
        ProjectDTO results = new ProjectDTO();

        if (entity != null) {
            results.setId(entity.getId());
            results.setName(entity.getName());
            results.setDescription(entity.getDescription());

            if (entity.getAssignedUsers() != null) {
                for (UserEntity assignedUser : entity.getAssignedUsers()) {
                    results.getAssignedUserIdList().add(this.userService.toId(assignedUser));
                }
            }

            if (entity.getStories() != null) {
                for (StoryEntity story : entity.getStories()) {
                    results.getStoryIdList().add(story.getId());
                }
            }
        }

        return results;
    }

    /**
     * Convert the specified entities to DTOs
     *
     * @param entities The entities
     *
     * @return The DTOs
     */
    public List<ProjectDTO> toDTOs(List<ProjectEntity> entities) {
        List<ProjectDTO> results = new ArrayList<>();

        if (entities != null) {
            for (ProjectEntity entity : entities) {
                results.add(this.toDTO(entity));
            }
        }

        return results;
    }
}
