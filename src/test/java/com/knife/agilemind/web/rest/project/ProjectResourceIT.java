package com.knife.agilemind.web.rest.project;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.project.ProjectEntity;
import com.knife.agilemind.dto.project.CreateProjectDTO;
import com.knife.agilemind.dto.project.ProjectDTO;
import com.knife.agilemind.repository.project.ProjectRepository;
import com.knife.agilemind.repository.story.StoryRepository;
import com.knife.agilemind.util.HttpTestUtil;
import com.knife.agilemind.util.ListUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@SpringBootTest(classes = AgileMindApp.class)
class ProjectResourceIT {
    @Autowired
    private ProjectResource projectResource;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    @Autowired
    private ListUtil listUtil;

    /**
     * Test on the valid project creation
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidCreate() {
        CreateProjectDTO newProject = new CreateProjectDTO()
            .setName("Name")
            .setDescription("Description");

        // Test response

        ProjectDTO response = this.httpTestUtil.getNotNullBody(this.projectResource.create(newProject), HttpStatus.OK);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(newProject.getName(), response.getName());
        Assertions.assertEquals(newProject.getDescription(), response.getDescription());
        this.listUtil.assertContainsID(response.getAssignatedUserIdList());
        this.listUtil.assertContainsID(response.getStoryIdList());

        // Test database

        Assertions.assertEquals(3L, this.projectRepository.count(),
            "Only one project MUST be created in database after CREATION operation"
        );

        ProjectEntity projectEntity = this.projectRepository.findById(response.getId()).orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(newProject.getName(), projectEntity.getName());
        Assertions.assertEquals(newProject.getDescription(), projectEntity.getDescription());
        Assertions.assertEquals(0, projectEntity.getAssignatedUsers().size());
        Assertions.assertEquals(0, projectEntity.getStories().size());
    }

    /**
     * Test on the valid project get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGet() {
        ProjectEntity savedProject = this.projectRepository.getOne(1000L);

        // Test response

        ProjectDTO response = this.httpTestUtil.getNotNullBody(this.projectResource.get(savedProject.getId()), HttpStatus.OK);

        Assertions.assertEquals(savedProject.getId(), response.getId());
        Assertions.assertEquals(savedProject.getName(), response.getName());
        Assertions.assertEquals(savedProject.getDescription(), response.getDescription());
        this.listUtil.assertContainsID(response.getAssignatedUserIdList(), 3L);
        this.listUtil.assertContainsID(response.getStoryIdList(), 1000L, 1001L, 1002L, 1003L, 1004L);

        // Test database

        Assertions.assertEquals(2, this.projectRepository.count(),
            "No one project MUST be deleted in database after GET operation"
        );

        ProjectEntity projectEntity = this.projectRepository.getOne(savedProject.getId());

        Assertions.assertEquals(savedProject.getId(), projectEntity.getId());
        Assertions.assertEquals(savedProject.getName(), projectEntity.getName());
        Assertions.assertEquals(savedProject.getDescription(), projectEntity.getDescription());
        this.listUtil.assertContainsUsers(savedProject.getAssignatedUsers(), "admin");
        this.listUtil.assertContainsStories(savedProject.getStories(), 1000L, 1001L, 1002L, 1003L, 1004L);
    }

    /**
     * Test on the valid project getAll and with connected user is "ADMIN"
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGetAllWithAdminUser() {
        ProjectEntity project1 = this.projectRepository.getOne(1000L);
        ProjectEntity project2 = this.projectRepository.getOne(1001L);

        List<ProjectEntity> list = Arrays.asList(project1, project2);

        List<ProjectDTO> response = this.httpTestUtil.getNotNullBody(this.projectResource.getAll(), HttpStatus.OK);

        // Sort the lists with the same order
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        response.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        Assertions.assertEquals(2, response.size(), "3 projects MUST be founded (all)");

        // Compare initial list and response list
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(list.get(i).getId(), response.get(i).getId());
            Assertions.assertEquals(list.get(i).getName(), response.get(i).getName());
            Assertions.assertEquals(list.get(i).getDescription(), response.get(i).getDescription());

            if (list.get(i).getId().equals(project1.getId())) {
                this.listUtil.assertContainsID(response.get(i).getAssignatedUserIdList(), 3L);
            }
        }
    }

    /**
     * Test on the valid project getAll
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testValidGetAllWithNotAdminUser() {
        ProjectEntity project = this.projectRepository.getOne(1001L);

        List<ProjectDTO> response = this.httpTestUtil.getNotNullBody(this.projectResource.getAll(), HttpStatus.OK);

        Assertions.assertEquals(1, response.size(), "1 project MUST be founded (all where 'user' is assignated)");

        Assertions.assertEquals(project.getId(), response.get(0).getId());
        Assertions.assertEquals(project.getName(), response.get(0).getName());
        Assertions.assertEquals(project.getName(), response.get(0).getName());
        this.listUtil.assertContainsID(response.get(0).getAssignatedUserIdList(), 3L, 4L);
    }

    /**
     * Test on the valid project getAll without projects in the database
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGetAllWithoutProjects() {
        this.projectRepository.deleteAll();

        List<ProjectDTO> response = this.httpTestUtil.getNotNullBody(this.projectResource.getAll(), HttpStatus.OK);

        Assertions.assertEquals(0, response.size(), "No one project MUST be founded (all)");
    }

    /**
     * Test on the valid project update
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidUpdate() {
        ProjectEntity project = this.projectRepository.getOne(1001L);

        ProjectDTO updatedProjectDTO = new ProjectDTO()
            .setId(project.getId())
            .setName("UpdatedName")
            .setDescription("UpdatedDescription");

        // Assign "user" to project and remove "admin"
        updatedProjectDTO.setAssignatedUserIdList(new HashSet<>()).getAssignatedUserIdList().add(4L);

        // Test response

        ProjectDTO response = this.httpTestUtil.getNotNullBody(
            this.projectResource.update(updatedProjectDTO), HttpStatus.OK
        );

        Assertions.assertEquals(updatedProjectDTO.getId(), response.getId());
        Assertions.assertEquals(updatedProjectDTO.getName(), response.getName());
        Assertions.assertEquals(updatedProjectDTO.getDescription(), response.getDescription());
        this.listUtil.assertContainsID(response.getAssignatedUserIdList(), 4L);

        // Test database

        Assertions.assertEquals(2L, this.projectRepository.count(),
            "No projects MUST be created in database after UPDATE operation"
        );

        ProjectEntity projectEntity = this.projectRepository.findById(1001L)
            .orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(project.getId(), projectEntity.getId());
        Assertions.assertEquals(project.getName(), projectEntity.getName());
        Assertions.assertEquals(project.getDescription(), projectEntity.getDescription());
        this.listUtil.assertContainsUsers(projectEntity.getAssignatedUsers(), "user");
        this.listUtil.assertContainsStories(projectEntity.getStories(), 1005L, 1006L, 1007L, 1008L, 1009L);
    }

    /**
     * Test on the valid project delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidDelete() {
        // Test response

        this.httpTestUtil.assertNullBody(this.projectResource.delete(1000L), HttpStatus.OK);
        this.httpTestUtil.assertNullBody(this.projectResource.delete(1001L), HttpStatus.OK);

        // Test database

        Assertions.assertEquals(0, this.projectRepository.count(),
            "No projects MUST be presents in database after DELETE operation"
        );

        Assertions.assertEquals(0, this.storyRepository.count(),
            "No stories MUST be presents in database after DELETE operation"
        );
    }

    /**
     * Test on invalid create
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidCreate() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.projectResource.create(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.create(new CreateProjectDTO()),
            ProjectConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        // Without name, a NAME_EMPTY must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.create(new CreateProjectDTO().setName("")),
            ProjectConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );
    }

    /**
     * Test on invalid create because user is not an admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidCreateBecauseNotAdmin() {
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.create(null), null, Status.NOT_FOUND);
    }

    /**
     * Test on invalid get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGet() {
        // Without id, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.projectResource.get(null));

        // With not existing id, a business exception must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.get(Long.MAX_VALUE),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid get because not connected
     */
    @Test
    @Transactional
    void testInvalidGetBecauseNotConnected() {
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.get(1L), null, Status.NOT_FOUND);
    }

    /**
     * Test on invalid get because connected user is not assignated on the
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetBecauseNotAssignated() {
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.get(1L),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid updated
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidUpdate() {
        this.httpTestUtil.assertTechnicalException(() -> this.projectResource.update(null));

        // Without id, an error ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.update(new ProjectDTO()),
            ProjectConstant.Error.ID_NULL,
            Status.BAD_REQUEST
        );

        // Without name, an error NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.update(
            new ProjectDTO().setId(1L)),
            ProjectConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        // With empty name, an error NAME_EMPTY must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.update(
            new ProjectDTO().setId(1L).setName("   ")),
            ProjectConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );
    }

    /**
     * Test on invalid updated because connected user is not an admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidUpdateBecauseNotAdmin() {
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.update(null), null, Status.NOT_FOUND);
    }

    /**
     * Test on invalid delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidDelete() {
        // Without id, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.projectResource.delete(null));

        // With not existing id, a business exception must be returned
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.delete(Long.MAX_VALUE),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid delete because connected user is not an admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidDeleteBecauseNotAdmin() {
        this.httpTestUtil.assertBusinessException(() -> this.projectResource.delete(Long.MAX_VALUE), null, Status.NOT_FOUND);
    }
}
