package com.knife.agilemind.web.rest.project;

import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.service.project.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Resource to manage projects
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProjectResource {
    @Autowired
    private ProjectService projectService;

    /**
     * Get the project with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @GetMapping("/project/{id}")
    public ResponseEntity<ProjectDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(this.projectService.get(id), HttpStatus.OK);
    }

    /**
     * Get all projects
     *
     * @return All projects
     */
    @GetMapping("/project")
    public ResponseEntity<List<ProjectDTO>> getAll() {
        return new ResponseEntity<>(this.projectService.getAll(), HttpStatus.OK);
    }

    /**
     * Create new project
     *
     * @param projectDTO The project to create
     *
     * @return The HTTP response
     */
    @PostMapping("/project")
    public ResponseEntity<ProjectDTO> create(@RequestBody CreateProjectDTO projectDTO) {
        return new ResponseEntity<>(this.projectService.create(projectDTO), HttpStatus.OK);
    }

    /**
     * Update the specified project
     *
     * @param projectDTO The new project data
     *
     * @return The HTTP response
     */
    @PutMapping("/project")
    public ResponseEntity<ProjectDTO> update(@RequestBody ProjectDTO projectDTO) {
        return new ResponseEntity<>(this.projectService.update(projectDTO), HttpStatus.OK);
    }

    /**
     * Delete the project with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @DeleteMapping("/project/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.projectService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
