package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.release.ReleaseConstant;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.dto.release.CreateReleaseDTO;
import com.knife.agilemind.dto.release.ReleaseDTO;
import com.knife.agilemind.repository.release.ReleaseRepository;
import com.knife.agilemind.util.HttpTestUtil;
import com.knife.agilemind.util.ListUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

@SpringBootTest(classes = AgileMindApp.class)
class ReleaseResourceGetIT {
    @Autowired
    private ReleaseResource releaseResource;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    @Autowired
    private ListUtil listUtil;

    /**
     * Test on the valid get
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGet() {
        ReleaseEntity saved = this.releaseRepository.getOne(1000L);

        // Test response

        ReleaseDTO response = this.httpTestUtil.assertNotNullBody(this.releaseResource.get(saved.getId()), HttpStatus.OK);

        Assertions.assertEquals(saved.getId(), response.getId());
        Assertions.assertEquals(saved.getName(), response.getName());
        Assertions.assertEquals(saved.getDescription(), response.getDescription());
        Assertions.assertEquals(saved.getDate(), response.getDate());
        Assertions.assertEquals(saved.getProject().getId(), response.getProject());
        this.listUtil.assertContainsID(response.getStories(), 1000L, 1001L, 1002L);

        // Test database

        Assertions.assertEquals(3, this.releaseRepository.count(),
            "No one release MUST be deleted or created in database after GET operation"
        );

        ReleaseEntity releaseEntity = this.releaseRepository.getOne(saved.getId());

        Assertions.assertEquals(saved.getId(), releaseEntity.getId());
        Assertions.assertEquals(saved.getName(), releaseEntity.getName());
        Assertions.assertEquals(saved.getDescription(), releaseEntity.getDescription());
        Assertions.assertEquals(saved.getDate(), releaseEntity.getDate());
        Assertions.assertNotNull(releaseEntity.getProject());
        Assertions.assertEquals(saved.getId(), releaseEntity.getProject().getId());
        this.listUtil.assertContainsStories(saved.getStories(), 1000L, 1001L, 1002L);
    }

    /**
     * Test on the invalid get because not connected
     */
    @Test
    @Transactional
    void testInvalidGetBecauseNotConnected() {
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.get(null),
            Status.NOT_FOUND
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.get(1000L),
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid get because not exists
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidGetBecauseNotExists() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.get(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.get(Long.MAX_VALUE),
            ReleaseConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on the invalid get because connected admin is not project admin
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetBecauseNotProjectAdmin() {
        // Without object to create, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.get(null));

        // Without name, a NAME_NULL must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.create(new CreateReleaseDTO()),
            ReleaseConstant.Error.NAME_NULL,
            Status.BAD_REQUEST
        );
    }
}
