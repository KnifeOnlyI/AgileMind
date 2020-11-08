package com.knife.agilemind.web.rest.story;

import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.service.story.StoryService;
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
 * Resource to manage stories
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@RestController
@RequestMapping("/api")
@Transactional
public class StoryResource {
    @Autowired
    private StoryService storyService;

    /**
     * Get the story with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @GetMapping("/story/{id}")
    public ResponseEntity<StoryDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(this.storyService.get(id), HttpStatus.OK);
    }

    /**
     * Get all stories from the specified project
     *
     * @param projectId The project ID
     *
     * @return The HTTP response
     */
    @GetMapping("/project/{projectId}/story")
    public ResponseEntity<List<StoryDTO>> getAllFromProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(this.storyService.getAllFromProject(projectId), HttpStatus.OK);
    }

    /**
     * Create new story
     *
     * @param storyDTO The story to create
     *
     * @return The HTTP response
     */
    @PostMapping("/story")
    public ResponseEntity<StoryDTO> create(@RequestBody CreateStoryDTO storyDTO) {
        return new ResponseEntity<>(this.storyService.create(storyDTO), HttpStatus.OK);
    }

    /**
     * Update the specified story
     *
     * @param storyDTO The new story data
     *
     * @return The HTTP response
     */
    @PutMapping("/story")
    public ResponseEntity<StoryDTO> update(@RequestBody StoryDTO storyDTO) {
        return new ResponseEntity<>(this.storyService.update(storyDTO), HttpStatus.OK);
    }

    /**
     * Delete the story with the specified id
     *
     * @param id The id
     */
    @DeleteMapping("/story/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.storyService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
