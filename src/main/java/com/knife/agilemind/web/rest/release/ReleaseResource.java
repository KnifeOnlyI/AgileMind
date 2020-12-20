package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.dto.release.CreateReleaseDTO;
import com.knife.agilemind.dto.release.ReleaseDTO;
import com.knife.agilemind.dto.release.UpdateReleaseDTO;
import com.knife.agilemind.service.release.ReleaseService;
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
 * Resource to manage releases
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ReleaseResource {
    @Autowired
    private ReleaseService releaseService;

    /**
     * Get the release with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @GetMapping("/release/{id}")
    public ResponseEntity<ReleaseDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(this.releaseService.get(id), HttpStatus.OK);
    }

    /**
     * Get all releases for the specified project
     *
     * @return All releases of specified project
     */
    @GetMapping("/project/{projectId}/release")
    public ResponseEntity<List<ReleaseDTO>> getAllByProject(@PathVariable Long projectId) {
        return new ResponseEntity<>(this.releaseService.getAllFromProject(projectId), HttpStatus.OK);
    }

    /**
     * Create new release
     *
     * @param release The release to create
     *
     * @return The HTTP response
     */
    @PostMapping("/release")
    public ResponseEntity<ReleaseDTO> create(@RequestBody CreateReleaseDTO release) {
        return new ResponseEntity<>(this.releaseService.create(release), HttpStatus.OK);
    }

    /**
     * Update the specified release
     *
     * @param release The new release data
     *
     * @return The HTTP response
     */
    @PutMapping("/release")
    public ResponseEntity<ReleaseDTO> update(@RequestBody UpdateReleaseDTO release) {
        return new ResponseEntity<>(this.releaseService.update(release), HttpStatus.OK);
    }

    /**
     * Delete the release with the specified id
     *
     * @param id The id
     *
     * @return The HTTP response
     */
    @DeleteMapping("/release/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.releaseService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
