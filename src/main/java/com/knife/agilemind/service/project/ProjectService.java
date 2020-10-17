package com.knife.agilemind.service.project;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.mapper.project.ProjectMapper;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.List;

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
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectValidator projectValidator;

    @Autowired
    private UserService userService;

    /**
     * Get the project identified by the specified ID
     *
     * @param id The ID
     *
     * @return The project
     */
    public ProjectDTO get(Long id) {
        if (!this.userService.userIsLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        this.projectValidator.assertNotNullId(id);

        ProjectEntity projectEntity = this.projectValidator.findById(id);

        if (!this.userService.currentIsAdmin()) {
            this.projectValidator.assertUserIsAssignated(this.userService.getLoggedUser(), projectEntity);
        }

        return this.projectMapper.toDTO(projectEntity);
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
            projectList = this.projectRepository.getAllByAssignatedUsersContains(this.userService.getLoggedUser());
        }

        return this.projectMapper.toDTOs(projectList);
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

        return this.projectMapper.toDTO(this.projectRepository.save(this.projectMapper.toEntity(createProjectDTO)));
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

        this.projectValidator.assertValid(projectDTO);

        return this.projectMapper.toDTO(this.projectRepository.save(this.projectMapper.toEntity(projectDTO)));
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
}
