package com.knife.agilemind.web.rest.story;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.story.StoryConstant;
import com.knife.agilemind.constant.story.StoryStatusConstant;
import com.knife.agilemind.constant.story.StoryTypeConstant;
import com.knife.agilemind.constant.user.UserConstant;
import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.dto.story.CreateStoryDTO;
import com.knife.agilemind.dto.story.StoryDTO;
import com.knife.agilemind.repository.story.StoryRepository;
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
class StoryResourceIT {
    @Autowired
    private StoryResource storyResource;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    /**
     * Test on the valid story creation
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidCreate() {
        CreateStoryDTO newStory = new CreateStoryDTO()
            .setName("Name")
            .setDescription("Description")
            .setPoints(1.5)
            .setBusinessValue(1L)
            .setStatus(StoryStatusConstant.DB.TODO_ID)
            .setType(StoryTypeConstant.DB.TODO_ID)
            .setAssignedUser(3L)
            .setProject(1000L);

        // Test response

        StoryDTO response = this.httpTestUtil.assertNotNullBody(this.storyResource.create(newStory), HttpStatus.OK);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(newStory.getName(), response.getName());
        Assertions.assertEquals(newStory.getDescription(), response.getDescription());
        Assertions.assertEquals(newStory.getPoints(), response.getPoints());
        Assertions.assertEquals(newStory.getStatus(), response.getStatus());
        Assertions.assertEquals(newStory.getType(), response.getType());
        Assertions.assertEquals(newStory.getAssignedUser(), response.getAssignedUser());

        // Test database

        Assertions.assertEquals(11L, this.storyRepository.count(),
            "Only one story MUST be created in database after CREATION operation"
        );

        StoryEntity storyEntity = this.storyRepository.findById(response.getId()).orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(newStory.getName(), storyEntity.getName());
        Assertions.assertEquals(newStory.getDescription(), storyEntity.getDescription());
        Assertions.assertEquals(newStory.getPoints(), storyEntity.getPoints());
        Assertions.assertEquals(newStory.getBusinessValue(), storyEntity.getBusinessValue());
        Assertions.assertNotNull(storyEntity.getStatus());
        Assertions.assertEquals(newStory.getStatus(), storyEntity.getStatus().getId());
        Assertions.assertNotNull(storyEntity.getType());
        Assertions.assertEquals(newStory.getType(), storyEntity.getType().getId());
        Assertions.assertNotNull(storyEntity.getAssignedUser());
        Assertions.assertEquals(newStory.getAssignedUser(), storyEntity.getAssignedUser().getId());
    }

    /**
     * Test on the valid story get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGet() {
        StoryDTO response = this.httpTestUtil.assertNotNullBody(this.storyResource.get(1000L), HttpStatus.OK);

        Assertions.assertEquals(1000L, response.getId());
        Assertions.assertEquals("AgileMind - Story #1", response.getName());
        Assertions.assertEquals("AgileMind - Story description #1", response.getDescription());
        Assertions.assertEquals(1, response.getPoints());
        Assertions.assertEquals(1, response.getBusinessValue());
        Assertions.assertEquals(1, response.getStatus());
        Assertions.assertEquals(1, response.getType());
        Assertions.assertEquals(3, response.getAssignedUser());
        Assertions.assertEquals(1000L, response.getProject());

        StoryEntity storyEntity = this.storyRepository.getOne(1000L);

        Assertions.assertEquals(1000L, storyEntity.getId());
        Assertions.assertEquals("AgileMind - Story #1", storyEntity.getName());
        Assertions.assertEquals("AgileMind - Story description #1", storyEntity.getDescription());
        Assertions.assertEquals(1, storyEntity.getPoints());
        Assertions.assertEquals(1, storyEntity.getBusinessValue());
        Assertions.assertNotNull(storyEntity.getStatus());
        Assertions.assertEquals(1, storyEntity.getStatus().getId());
        Assertions.assertNotNull(storyEntity.getType());
        Assertions.assertEquals(1, storyEntity.getType().getId());
        Assertions.assertNotNull(storyEntity.getAssignedUser());
        Assertions.assertEquals(3, storyEntity.getAssignedUser().getId());
        Assertions.assertNotNull(storyEntity.getProject());
        Assertions.assertEquals(1000L, storyEntity.getProject().getId());
    }

    /**
     * Test valid get all stories from project
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGetAllFromProject() {
        List<StoryDTO> response = this.httpTestUtil.assertNotNullBody(this.storyResource.getAllFromProject(1000L), HttpStatus.OK);

        response.sort((a, b) -> (int) (a.getId() - b.getId()));

        Assertions.assertEquals(5, response.size());
        Assertions.assertEquals(1000L, response.get(0).getId());
        Assertions.assertEquals(1001L, response.get(1).getId());
        Assertions.assertEquals(1002L, response.get(2).getId());
        Assertions.assertEquals(1003L, response.get(3).getId());
        Assertions.assertEquals(1004L, response.get(4).getId());
    }

    /**
     * Test invalid get all stories from project because specified project not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGetAllFromProjectBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.getAllFromProject(Long.MAX_VALUE),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test invalid get all stories from project because logged user is not assigned to story
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetAllFromProjectBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.getAllFromProject(1000L),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    @Test
    @Transactional
    void testInvalidGetAllFromProjectBecauseNotLogged() {
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.getAllFromProject(1000L), Status.NOT_FOUND);
    }

    /**
     * Test on the valid story get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidUpdate() {
        StoryDTO storyDTO = new StoryDTO()
            .setId(1000L)
            .setName("Updated name")
            .setType(3L)
            .setStatus(3L)
            .setProject(1001L);

        // Test response

        StoryDTO response = this.httpTestUtil.assertNotNullBody(this.storyResource.update(storyDTO), HttpStatus.OK);

        Assertions.assertEquals(1000L, storyDTO.getId());
        Assertions.assertEquals(storyDTO.getName(), response.getName());
        Assertions.assertEquals(storyDTO.getDescription(), response.getDescription());
        Assertions.assertEquals(storyDTO.getPoints(), response.getPoints());
        Assertions.assertEquals(storyDTO.getBusinessValue(), response.getBusinessValue());
        Assertions.assertEquals(storyDTO.getStatus(), response.getStatus());
        Assertions.assertEquals(storyDTO.getType(), response.getType());
        Assertions.assertEquals(storyDTO.getAssignedUser(), response.getAssignedUser());
        Assertions.assertEquals(storyDTO.getProject(), response.getProject());

        // Test database

        StoryEntity storyEntity = this.storyRepository.findById(1000L).orElse(null);

        Assertions.assertNotNull(storyEntity);
        Assertions.assertEquals(storyDTO.getName(), storyEntity.getName());
        Assertions.assertEquals(storyDTO.getDescription(), storyEntity.getDescription());
        Assertions.assertEquals(storyDTO.getPoints(), storyEntity.getPoints());
        Assertions.assertEquals(storyDTO.getBusinessValue(), storyEntity.getBusinessValue());
        Assertions.assertNotNull(storyEntity.getStatus());
        Assertions.assertEquals(storyDTO.getStatus(), storyEntity.getStatus().getId());
        Assertions.assertNotNull(storyEntity.getType());
        Assertions.assertEquals(storyDTO.getType(), storyEntity.getType().getId());
        Assertions.assertNull(storyEntity.getAssignedUser());
        Assertions.assertNotNull(storyEntity.getProject());
        Assertions.assertEquals(storyDTO.getProject(), storyEntity.getProject().getId());
    }

    /**
     * Test on the valid story delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidDelete() {
        this.httpTestUtil.assertNullBody(this.storyResource.delete(1000L), HttpStatus.OK);

        Assertions.assertEquals(9L, this.storyRepository.count(),
            "Only one story MUST be deleted in database after story DELETE operation"
        );

        Assertions.assertNull(this.storyRepository.findById(1000L).orElse(null));
    }

    /**
     * Test on invalid create
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidCreate() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.storyResource.create(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()),
            StoryConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        // With empty name, a NAME_EMPTY must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("")
            ),
            StoryConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );

        // With negative story points, a POINTS_LESS_0 must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setPoints(-1.5)
            ),
            StoryConstant.Error.POINTS_LESS_0,
            Status.BAD_REQUEST
        );

        // With negative story points, a POINTS_LESS_0 must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setBusinessValue(-1L)
            ),
            StoryConstant.Error.BUSINESS_VALUE_LESS_0,
            Status.BAD_REQUEST
        );

        // Without status, a STORY_STATUS_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
            ),
            StoryConstant.Error.STATUS_ID_NULL,
            Status.BAD_REQUEST
        );

        // With invalid status, a STORY_STATUS_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(Long.MAX_VALUE)
            ),
            StoryStatusConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        // Without type, a STORY_TYPE_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(3L)
            ),
            StoryConstant.Error.TYPE_ID_NULL,
            Status.BAD_REQUEST
        );

        // With invalid type, a STORY_TYPE_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(3L)
                .setType(Long.MAX_VALUE)
            ),
            StoryTypeConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        // Without project, a PROJECT_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(1L)
                .setType(1L)
            ),
            StoryConstant.Error.PROJECT_ID_NULL,
            Status.BAD_REQUEST
        );

        // With invalid project, a PROJECT_STATUS_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(1L)
                .setType(1L)
                .setProject(Long.MAX_VALUE)
            ),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        // Error when trying to assign to story a user not assigned to project
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
            .setName("Name")
            .setStatus(1L)
            .setType(1L)
            .setProject(1000L)
            .setAssignedUser(4L)
        ), ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
    }

    /**
     * Test on invalid create because not assigned to project
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidCreateBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.create(new CreateStoryDTO()
                .setName("Name")
                .setStatus(1L)
                .setType(1L)
                .setProject(1000L)
            ), ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid story get because ID not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGetBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.get(Long.MAX_VALUE),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid story get because user is not assigned
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.get(1001L),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid story get because user is not assigned
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidUpdate() {
        this.httpTestUtil.assertTechnicalException(() -> this.storyResource.update(null));

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
        ), StoryConstant.Error.ID_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
        ), StoryConstant.Error.NAME_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("")
        ), StoryConstant.Error.NAME_EMPTY, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
        ), StoryConstant.Error.STATUS_ID_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(Long.MAX_VALUE)
        ), StoryStatusConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(3L)
        ), StoryConstant.Error.TYPE_ID_NULL, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(3L)
                .setType(Long.MAX_VALUE)
        ), StoryTypeConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(3L)
                .setType(3L)
                .setPoints(-1.5)
        ), StoryConstant.Error.POINTS_LESS_0, Status.BAD_REQUEST);

        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(3L)
                .setType(3L)
                .setProject(1000L)
                .setAssignedUser(Long.MAX_VALUE)
        ), UserConstant.Error.NOT_FOUND, Status.NOT_FOUND);

        // Error when trying to assign to story a user not assigned to project
        this.httpTestUtil.assertBusinessException(() -> this.storyResource.update(
            new StoryDTO()
                .setId(1000L)
                .setName("UpdatedName")
                .setStatus(3L)
                .setType(3L)
                .setAssignedUser(4L)
                .setProject(1000L)
        ), ProjectConstant.Error.USER_NOT_ASSIGNED, Status.BAD_REQUEST);
    }

    /**
     * Test on the invalid story delete because not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidDeleteBecauseNotExists() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.delete(Long.MAX_VALUE),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        Assertions.assertEquals(10L, this.storyRepository.count(),
            "No one story MUST be deleted in database after invalid story DELETE operation"
        );

        Assertions.assertNotNull(this.storyRepository.findById(1000L).orElse(null));
    }

    /**
     * Test on the invalid story delete because not assigned to project
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidDeleteBecauseNotAssigned() {
        this.httpTestUtil.assertBusinessException(
            () -> this.storyResource.delete(1000L),
            StoryConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        Assertions.assertEquals(10L, this.storyRepository.count(),
            "No one story MUST be deleted in database after invalid story DELETE operation"
        );

        Assertions.assertNotNull(this.storyRepository.findById(1000L).orElse(null));
    }
}
