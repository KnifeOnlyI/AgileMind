package com.knife.agilemind.service.project;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.TechnicalAssert;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.service.story.StoryService;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        return this.toDTO(this.findById(id));
    }

    /**
     * Get all projects
     *
     * @return The projects
     */
    public List<ProjectDTO> getAll() {
        this.userService.assertLogged();

        List<ProjectEntity> projectList;

        // If the user is admin, fetch all project
        // Else, fetch only project where user is assigned or project admin
        if (this.userService.loggedUserIsAdmin()) {
            projectList = this.projectRepository.findAll();
        } else {
            projectList = this.projectRepository.getAllByAssignedUsersContainsOrAdminUsersContains(
                this.userService.getLoggedUser(),
                this.userService.getLoggedUser()
            );
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
        this.userService.assertAdmin();

        TechnicalAssert.notNull(createProjectDTO);

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
        this.userService.assertLogged();

        TechnicalAssert.notNull(projectDTO);

        this.projectValidator.assertValid(projectDTO);

        ProjectEntity projectEntity = this.projectValidator.assertLoggedUserCanEditOrDelete(projectDTO.getId());

        projectEntity
            .setName(projectDTO.getName())
            .setDescription(projectDTO.getDescription())
            .setAssignedUsers(this.userService.findAllById(projectDTO.getAssignedUsers()))
            .setAdminUsers(this.userService.findAllById(projectDTO.getAdminUsers()));

        Set<StoryEntity> stories = this.storyService.findAllById(projectDTO.getStories());

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
        this.userService.assertLogged();

        TechnicalAssert.notNull(id);

        this.projectRepository.delete(this.projectValidator.assertLoggedUserCanEditOrDelete(id));
    }

    /**
     * Find project in database by the specified ID
     *
     * @param id The project id to find
     *
     * @return The founded project
     */
    public ProjectEntity findById(Long id) {
        TechnicalAssert.notNull(id);

        return this.projectValidator.assertLoggedUserCanView(id);
    }

    /**
     * Convert the specified entity to DTO
     *
     * @param entity The entity
     *
     * @return The DTO
     */
    private ProjectDTO toDTO(ProjectEntity entity) {
        ProjectDTO results = new ProjectDTO();

        if (entity != null) {
            results.setId(entity.getId());
            results.setName(entity.getName());
            results.setDescription(entity.getDescription());

            results.setAssignedUsers(this.userService.toIds(entity.getAssignedUsers()));
            results.setAdminUsers(this.userService.toIds(entity.getAdminUsers()));

            if (entity.getStories() != null) {
                for (StoryEntity story : entity.getStories()) {
                    results.getStories().add(story.getId());
                }
            }

            if (entity.getReleases() != null) {
                for (ReleaseEntity release : entity.getReleases()) {
                    results.getReleases().add(release.getId());
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
    private List<ProjectDTO> toDTOs(List<ProjectEntity> entities) {
        List<ProjectDTO> results = new ArrayList<>();

        if (entities != null) {
            for (ProjectEntity entity : entities) {
                results.add(this.toDTO(entity));
            }
        }

        return results;
    }
}
