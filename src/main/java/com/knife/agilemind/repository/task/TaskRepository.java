package com.knife.agilemind.repository.task;

import com.knife.agilemind.domain.task.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository to manage tasks in database
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
}
