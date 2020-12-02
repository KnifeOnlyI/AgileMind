package com.knife.agilemind.web.rest.task;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.task.TaskConstant;
import com.knife.agilemind.constant.task.TaskStatusConstant;
import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.task.TaskEntity;
import com.knife.agilemind.dto.task.CreateTaskDTO;
import com.knife.agilemind.dto.task.TaskDTO;
import com.knife.agilemind.repository.task.TaskRepository;
import com.knife.agilemind.util.HttpTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.List;

@SpringBootTest(classes = AgileMindApp.class)
class TaskResourceIT {
    @Autowired
    private TaskResource taskResource;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    /**
     * Test on the valid task creation
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidCreate() {
        CreateTaskDTO newTask = new CreateTaskDTO()
            .setName("Name")
            .setDescription("Description")
            .setEstimatedTime(1L)
            .setLoggedTime(1L)
            .setStatusId(TaskStatusConstant.DB.TODO_ID)
            .setAssignedUserId(3L)
            .setStoryId(1000L);

        // Test response

        TaskDTO response = this.httpTestUtil.assertNotNullBody(this.taskResource.create(newTask), HttpStatus.OK);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(newTask.getName(), response.getName());
        Assertions.assertEquals(newTask.getDescription(), response.getDescription());
        Assertions.assertEquals(newTask.getEstimatedTime(), response.getEstimatedTime());
        Assertions.assertEquals(newTask.getLoggedTime(), response.getLoggedTime());
        Assertions.assertEquals(newTask.getStatusId(), response.getStatusId());
        Assertions.assertEquals(newTask.getAssignedUserId(), response.getAssignedUserId());
        Assertions.assertEquals(newTask.getStoryId(), response.getStoryId());

        // Test database

        Assertions.assertEquals(10L, this.taskRepository.count(),
            "Only one task MUST be created in database after CREATION operation"
        );

        TaskEntity taskEntity = this.taskRepository.findById(response.getId()).orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(newTask.getName(), taskEntity.getName());
        Assertions.assertEquals(newTask.getDescription(), taskEntity.getDescription());
        Assertions.assertEquals(newTask.getEstimatedTime(), taskEntity.getEstimatedTime());
        Assertions.assertEquals(newTask.getLoggedTime(), taskEntity.getLoggedTime());
        Assertions.assertNotNull(taskEntity.getStatus());
        Assertions.assertEquals(newTask.getStatusId(), taskEntity.getStatus().getId());
        Assertions.assertNotNull(taskEntity.getAssignedUser());
        Assertions.assertEquals(newTask.getAssignedUserId(), taskEntity.getAssignedUser().getId());
        Assertions.assertNotNull(taskEntity.getStory());
        Assertions.assertEquals(newTask.getStoryId(), taskEntity.getStory().getId());
    }

    /**
     * Test on the valid task get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGet() {
        TaskDTO response = this.httpTestUtil.assertNotNullBody(this.taskResource.get(1000L), HttpStatus.OK);

        Assertions.assertEquals(1000L, response.getId());
        Assertions.assertEquals("AgileMind - Story #1 - Task #1", response.getName());
        Assertions.assertEquals("AgileMind - Task description #1", response.getDescription());
        Assertions.assertEquals(60, response.getEstimatedTime());
        Assertions.assertEquals(0, response.getLoggedTime());
        Assertions.assertEquals(1, response.getStatusId());
        Assertions.assertEquals(3, response.getAssignedUserId());
        Assertions.assertEquals(1000L, response.getStoryId());

        TaskEntity taskEntity = this.taskRepository.getOne(1000L);

        Assertions.assertEquals(1000L, taskEntity.getId());
        Assertions.assertEquals("AgileMind - Story #1 - Task #1", taskEntity.getName());
        Assertions.assertEquals("AgileMind - Task description #1", taskEntity.getDescription());
        Assertions.assertEquals(60, taskEntity.getEstimatedTime());
        Assertions.assertEquals(0, taskEntity.getLoggedTime());
        Assertions.assertNotNull(taskEntity.getStatus());
        Assertions.assertEquals(1, taskEntity.getStatus().getId());
        Assertions.assertNotNull(taskEntity.getAssignedUser());
        Assertions.assertEquals(3, taskEntity.getAssignedUser().getId());
        Assertions.assertNotNull(taskEntity.getStory());
        Assertions.assertEquals(1000L, taskEntity.getStory().getId());
    }

    /**
     * Test valid get all tasks from story
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGetAllFromStory() {
        List<TaskDTO> response = this.httpTestUtil.assertNotNullBody(this.taskResource.getAllFromStory(1000L), HttpStatus.OK);

        response.sort((a, b) -> (int) (a.getId() - b.getId()));

        Assertions.assertEquals(3, response.size());
        Assertions.assertEquals(1000L, response.get(0).getId());
        Assertions.assertEquals(1001L, response.get(1).getId());
        Assertions.assertEquals(1002L, response.get(2).getId());
    }

    /**
     * Test invalid get all tasks from story because specified story not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGetAllFromStoryBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.getAllFromStory(Long.MAX_VALUE),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test invalid get all tasks from story because logged user is not assigned to task
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetAllFromStoryBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.getAllFromStory(1000L),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    @Test
    @Transactional
    void testInvalidGetAllFromStoryBecauseNotLogged() {
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.getAllFromStory(1000L), Status.NOT_FOUND);
    }

    /**
     * Test on the valid task get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidUpdate() {
        TaskDTO taskDTO = new TaskDTO()
            .setId(1000L)
            .setName("Updated name")
            .setStatusId(3L)
            .setStoryId(1001L);

        // Test response

        TaskDTO response = this.httpTestUtil.assertNotNullBody(this.taskResource.update(taskDTO), HttpStatus.OK);

        Assertions.assertEquals(1000L, taskDTO.getId());
        Assertions.assertEquals(taskDTO.getName(), response.getName());
        Assertions.assertEquals(taskDTO.getDescription(), response.getDescription());
        Assertions.assertEquals(taskDTO.getEstimatedTime(), response.getEstimatedTime());
        Assertions.assertEquals(taskDTO.getLoggedTime(), response.getLoggedTime());
        Assertions.assertEquals(taskDTO.getStatusId(), response.getStatusId());
        Assertions.assertEquals(taskDTO.getAssignedUserId(), response.getAssignedUserId());
        Assertions.assertEquals(taskDTO.getStoryId(), response.getStoryId());

        // Test database

        TaskEntity taskEntity = this.taskRepository.findById(1000L).orElse(null);

        Assertions.assertNotNull(taskEntity);
        Assertions.assertEquals(taskDTO.getName(), taskEntity.getName());
        Assertions.assertEquals(taskDTO.getDescription(), taskEntity.getDescription());
        Assertions.assertEquals(taskDTO.getEstimatedTime(), taskEntity.getEstimatedTime());
        Assertions.assertEquals(taskDTO.getLoggedTime(), taskEntity.getLoggedTime());
        Assertions.assertNotNull(taskEntity.getStatus());
        Assertions.assertEquals(taskDTO.getStatusId(), taskEntity.getStatus().getId());
        Assertions.assertNull(taskEntity.getAssignedUser());
        Assertions.assertNotNull(taskEntity.getStory());
        Assertions.assertEquals(taskDTO.getStoryId(), taskEntity.getStory().getId());
    }

    /**
     * Test on the valid task delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidDelete() {
        this.httpTestUtil.assertNullBody(this.taskResource.delete(1000L), HttpStatus.OK);

        Assertions.assertEquals(8L, this.taskRepository.count(),
            "Only one task MUST be deleted in database after task DELETE operation"
        );

        Assertions.assertNull(this.taskRepository.findById(1000L).orElse(null));
    }

    /**
     * Test on invalid create
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidCreate() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.taskResource.create(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()),
            TaskConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        // With empty name, a NAME_EMPTY must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("")
            ),
            TaskConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );

        // With negative task estimatedTime, a ESTIMATED_TIME_LESS_0 must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setEstimatedTime(-1L)
            ),
            TaskConstant.Error.ESTIMATED_TIME_LESS_0,
            Status.BAD_REQUEST
        );

        // With negative task estimatedTime, a LOGGED_TIME_LESS_0 must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setLoggedTime(-1L)
            ),
            TaskConstant.Error.LOGGED_TIME_LESS_0,
            Status.BAD_REQUEST
        );

        // Without status, a TASK_STATUS_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
            ),
            TaskConstant.Error.STATUS_ID_NULL,
            Status.BAD_REQUEST
        );

        // With invalid status, a TASK_STATUS_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setStatusId(Long.MAX_VALUE)
            ),
            TaskStatusConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        // Without story, a STORY_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setStatusId(1L)
            ),
            TaskConstant.Error.STORY_ID_NULL,
            Status.BAD_REQUEST
        );

        // With invalid story, a STORY_STATUS_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setStatusId(1L)
                .setStoryId(Long.MAX_VALUE)
            ),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        // Error when trying to assign to task a user not assigned to story
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
            .setName("Name")
            .setStatusId(1L)
            .setStoryId(1000L)
            .setAssignedUserId(4L)
        ), ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
    }

    /**
     * Test on invalid create because not assigned to story
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidCreateBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.create(new CreateTaskDTO()
                .setName("Name")
                .setStatusId(1L)
                .setStoryId(1000L)
            ), StoryConstant.Error.NOT_FOUND, Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid task get because ID not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGetBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.get(Long.MAX_VALUE),
            TaskConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid task get because user is not assigned
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.get(1001L),
            TaskConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid task get because user is not assigned
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidUpdate() {
        this.httpTestUtil.assertTechnicalException(() -> this.taskResource.update(null));

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
        ), TaskConstant.Error.ID_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
        ), TaskConstant.Error.NAME_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("")
        ), TaskConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("UpdatedName")
        ), TaskConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatusId(Long.MAX_VALUE)
        ), TaskStatusConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatusId(3L)
                .setEstimatedTime(-1L)
        ), TaskConstant.Error.ESTIMATED_TIME_LESS_0, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatusId(3L)
                .setStoryId(1000L)
                .setAssignedUserId(Long.MAX_VALUE)
        ), UserConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        // Error when trying to assign to task a user not assigned to story
        this.httpTestUtil.assertBusinessException(() -> this.taskResource.update(
            new TaskDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatusId(3L)
                .setAssignedUserId(4L)
                .setStoryId(1000L)
        ), ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
    }

    /**
     * Test on the invalid task delete because not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidDeleteBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.delete(Long.MAX_VALUE),
            TaskConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        Assertions.assertEquals(9L, this.taskRepository.count(),
            "No one task MUST be deleted in database after invalid task DELETE operation"
        );

        Assertions.assertNotNull(this.taskRepository.findById(1000L).orElse(null));
    }

    /**
     * Test on the invalid task delete because not assigned to story
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidDeleteBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.taskResource.delete(1000L),
            TaskConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        Assertions.assertEquals(9L, this.taskRepository.count(),
            "No one task MUST be deleted in database after invalid task DELETE operation"
        );

        Assertions.assertNotNull(this.taskRepository.findById(1000L).orElse(null));
    }
}
