package com.knife.agilemind.repository.project;

import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository to manage projects in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    /**
     * Get all projects assigned to the specified user
     *
     * @param userEntity The user
     *
     * @return The projects assigned to the specified user
     */
    List<ProjectEntity> getAllByAssignedUsersContains(UserEntity userEntity);
}
