package com.knife.agilemind.repository.task;

import com.knife.agilemind.domain.task.TaskStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage task status in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatusEntity, Long> {
}
