package com.knife.agilemind.repository.release;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.release.ReleaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to manage releases in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseEntity, Long> {
    /**
     * Get all releases if there are associated to specified project
     *
     * @param project The associated project
     *
     * @return The releases
     */
    List<ReleaseEntity> getAllByProject(ProjectEntity project);
}
