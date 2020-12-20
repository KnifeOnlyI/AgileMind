package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.release.ReleaseConstant;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.dto.release.ReleaseDTO;
import com.knife.agilemind.dto.release.UpdateReleaseDTO;
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
class ReleaseResourceUpdateIT {
    @Autowired
    private ReleaseResource releaseResource;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    @Autowired
    private ListUtil listUtil;

    /**
     * Test on the valid release update
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidUpdate() {
        ReleaseEntity release = this.releaseRepository.getOne(1000L);

        UpdateReleaseDTO updatedReleaseDTO = new UpdateReleaseDTO()
            .setId(release.getId())
            .setName("UpdatedName")
            .setDescription("UpdatedDescription")
            .setDate(Date.from(Instant.now()));

        // Test response

        ReleaseDTO response = this.httpTestUtil.assertNotNullBody(
            this.releaseResource.update(updatedReleaseDTO), HttpStatus.OK
        );

        Assertions.assertEquals(updatedReleaseDTO.getId(), response.getId());
        Assertions.assertEquals(updatedReleaseDTO.getName(), response.getName());
        Assertions.assertEquals(updatedReleaseDTO.getDescription(), response.getDescription());
        Assertions.assertEquals(updatedReleaseDTO.getDate(), response.getDate());
        Assertions.assertNotNull(release.getProject());
        Assertions.assertEquals(release.getProject().getId(), response.getProject());
        this.listUtil.assertContainsID(response.getStories(), 1000L, 1001L, 1002L);

        // Test database

        Assertions.assertEquals(3, this.releaseRepository.count(),
            "No releases MUST be created in database after UPDATE operation"
        );

        ReleaseEntity releaseEntity = this.releaseRepository.findById(1000L)
            .orElseThrow(AssertionFailedError::new);

        Assertions.assertEquals(release.getId(), releaseEntity.getId());
        Assertions.assertEquals(release.getName(), releaseEntity.getName());
        Assertions.assertEquals(release.getDescription(), releaseEntity.getDescription());
        Assertions.assertEquals(release.getDate(), releaseEntity.getDate());
        Assertions.assertNotNull(release.getProject());
        Assertions.assertNotNull(releaseEntity.getProject());
        Assertions.assertEquals(release.getProject().getId(), releaseEntity.getProject().getId());
        this.listUtil.assertContainsStories(release.getStories(), 1000L, 1001L, 1002L);
    }

    /**
     * Test on invalid updates
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidUpdate() {
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.update(null));

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.update(new UpdateReleaseDTO()),
            ReleaseConstant.Error.ID_NULL,
            Status.BAD_REQUEST
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.update(new UpdateReleaseDTO()
                .setId(1L)
            ),
            ReleaseConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.update(new UpdateReleaseDTO()
                .setId(1002L)
            ),
            ReleaseConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.update(new UpdateReleaseDTO()
                .setId(1002L)
                .setName("   ")
            ),
            ReleaseConstant.Error.NAME_EMPTY,
            Status.BAD_REQUEST
        );
    }

    /**
     * Test on invalid update because not project admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidUpdateBecauseNotProjectAdmin() {
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.update(null));

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.update(new UpdateReleaseDTO()
                .setId(1000L)
                .setName("Name")
            ),
            ReleaseConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }
}
