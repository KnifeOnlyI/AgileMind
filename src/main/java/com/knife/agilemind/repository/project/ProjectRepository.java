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
     * Get all projects if there are assigned to user or user is admin of them
     *
     * @param assignedUser The assigned user
     * @param adminUser    The admin user
     *
     * @return The projects
     */
    List<ProjectEntity> getAllByAssignedUsersContainsOrAdminUsersContains(
        UserEntity assignedUser,
        UserEntity adminUser
    );
}
