package com.knife.agilemind.service.task;

import com.knife.agilemind.constant.task.TaskStatusConstant;
import com.knife.agilemind.domain.task.TaskStatusEntity;
import com.knife.agilemind.dto.task.TaskStatusDTO;
import com.knife.agilemind.exception.BusinessAssert;
import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.repository.task.TaskStatusRepository;
import com.knife.agilemind.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Service to manage task status
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class TaskStatusService {
    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private UserService userService;

    /**
     * Get all task status
     *
     * @return All task status DTO
     */
    public List<TaskStatusDTO> getAll() {
        if (this.userService.userIsNotLogged()) {
            throw new BusinessException(Status.NOT_FOUND);
        }

        List<TaskStatusDTO> results = new ArrayList<>();
        List<TaskStatusEntity> entities = this.taskStatusRepository.findAll();

        for (TaskStatusEntity entity : entities) {
            results.add(new TaskStatusDTO().setId(entity.getId()).setKey(entity.getKey()));
        }

        return results;
    }

    /**
     * Find task status in database by the specified ID
     *
     * @param id The status id to find
     */
    public TaskStatusEntity findById(Long id) {
        TaskStatusEntity results = null;

        if (id != null) {
            results = this.taskStatusRepository.findById(id).orElse(null);
        }

        return results;
    }

    /**
     * Assert the specified task status ID exists in database
     *
     * @param id The task status id to check
     */
    public void assertExists(Long id) {
        BusinessAssert.notNull(this.findById(id), TaskStatusConstant.Error.NOT_FOUND, Status.NOT_FOUND);
    }
}
