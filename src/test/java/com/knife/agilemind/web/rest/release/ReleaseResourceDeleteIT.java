package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.release.ReleaseConstant;
import com.knife.agilemind.repository.release.ReleaseRepository;
import com.knife.agilemind.util.HttpTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

@SpringBootTest(classes = AgileMindApp.class)
class ReleaseResourceDeleteIT {
    @Autowired
    private ReleaseResource releaseResource;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    /**
     * Test on the valid release delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidDelete() {
        // Test response

        this.httpTestUtil.assertNullBody(this.releaseResource.delete(1000L), HttpStatus.OK);
        this.httpTestUtil.assertNullBody(this.releaseResource.delete(1001L), HttpStatus.OK);
        this.httpTestUtil.assertNullBody(this.releaseResource.delete(1002L), HttpStatus.OK);

        // Test database

        Assertions.assertEquals(0, this.releaseRepository.count(),
            "No releases MUST be presents in database after DELETE operation"
        );

        Assertions.assertEquals(0, this.releaseRepository.count(),
            "No releases MUST be presents in database after DELETE operation"
        );
    }

    /**
     * Test on invalid delete
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testInvalidDelete() {
        // Without id, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.delete(null));

        // With not existing id, a business exception must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.delete(Long.MAX_VALUE),
            ReleaseConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid delete because not connected
     */
    @Test
    @Transactional
    void testInvalidDeleteBecauseNotConnected() {
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.delete(null),
            Status.NOT_FOUND
        );

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.delete(1000L),
            Status.NOT_FOUND
        );
    }

    /**
     * Test on invalid delete because connected user is not assigned or project admin of the project associated to
     * release
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidDeleteBecauseNotAssignedOrProjectAdmin() {
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.delete(1000L),
            ReleaseConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }
}
