package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.constant.release.ReleaseConstant;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.dto.release.CreateReleaseDTO;
import com.knife.agilemind.dto.release.ReleaseDTO;
import com.knife.agilemind.repository.release.ReleaseRepository;
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

import java.time.Instant;
import java.util.Date;

@SpringBootTest(classes = AgileMindApp.class)
class ReleaseResourceCreateIT {
    @Autowired
    private ReleaseResource releaseResource;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    @Autowired
    private ListUtil listUtil;

    /**
     * Test on the valid creation
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidCreate() {
        CreateReleaseDTO newRelease = new CreateReleaseDTO()
            .setName("Name")
            .setDescription("Description")
            .setDate(Date.from(Instant.now()))
            .setProject(1000L);

        // Test response

        ReleaseDTO response = this.httpTestUtil.assertNotNullBody(this.releaseResource.create(newRelease), HttpStatus.OK);

        Assertions.assertNotNull(response.getId());
        Assertions.assertEquals(newRelease.getName(), response.getName());
        Assertions.assertEquals(newRelease.getDescription(), response.getDescription());
        Assertions.assertEquals(newRelease.getDate(), response.getDate());
        Assertions.assertEquals(newRelease.getProject(), response.getProject());
        this.listUtil.assertContainsID(response.getStories());

        // Test database

        Assertions.assertEquals(4, this.releaseRepository.count(),
            "Only one release MUST be created in database after CREATION operation"
        );

        ReleaseEntity releaseEntity = this.releaseRepository.findById(response.getId()).orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(newRelease.getName(), releaseEntity.getName());
        Assertions.assertEquals(newRelease.getDescription(), releaseEntity.getDescription());
        Assertions.assertEquals(newRelease.getDate(), releaseEntity.getDate());
        Assertions.assertNotNull(releaseEntity.getProject());
        Assertions.assertEquals(newRelease.getProject(), releaseEntity.getProject().getId());
        Assertions.assertEquals(0, releaseEntity.getStories().size());
    }

    /**
     * Test on invalid create because not connected
     */
    @Test
    @Transactional
    void testInvalidCreateBecauseNotConnected() {
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(null), null, Status.NOT_FOUND);

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()),
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid create because connected user is not project admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidCreate() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.create(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()),
            ReleaseConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        // Without name, a NAME_EMPTY must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO().setName("")),
            ReleaseConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );

        // Without projectId, a PROJECT_ID_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()
                .setName("Name")
            ),
            ReleaseConstant.Error.PROJECT_ID_NULL,
            Status.BAD_REQUEST
        );

        // Without not existing projectId, a PROJECT_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()
                .setName("Name")
                .setProject(Long.MAX_VALUE)
            ),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid create because connected user is not project admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidCreateBecauseNotProjectAdmin() {
        // If user is not project admin of the specified project, a PROJECT_NOT_FOUND must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()
                .setName("Name")
                .setProject(1000L)
            ),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(
            new CreateReleaseDTO().setName("Name").setProject(1000L)), ProjectConstant.Error.NOT_FOUND, Status.NOT_FOUND
        );
    }
}
