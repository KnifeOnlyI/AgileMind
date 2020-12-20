package com.knife.agilemind.web.rest.release;

import com.knife.agilemind.AgileMindApp;
import com.knife.agilemind.constant.project.ProjectConstant;
import com.knife.agilemind.domain.release.ReleaseEntity;
import com.knife.agilemind.dto.release.ReleaseDTO;
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

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = AgileMindApp.class)
class ReleaseResourceGetAllIT {
    @Autowired
    private ReleaseResource releaseResource;

    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private HttpTestUtil httpTestUtil;

    /**
     * Test on the valid project getAll and with connected user is "USER" (assigned user but not admin).
     *
     * User can get release of projects : 1002
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testValidGetAllWithAssignedUser() {
        ReleaseEntity release = this.releaseRepository.getOne(1002L);

        List<ReleaseDTO> response = this.httpTestUtil.assertNotNullBody(this.releaseResource.getAllByProject(1001L), HttpStatus.OK);

        Assertions.assertEquals(1, response.size(), "1 release MUST be founded");

        Assertions.assertEquals(release.getId(), response.get(0).getId());
        Assertions.assertEquals(release.getName(), response.get(0).getName());
        Assertions.assertEquals(release.getDescription(), response.get(0).getDescription());
        Assertions.assertEquals(release.getDate(), response.get(0).getDate());
        Assertions.assertNotNull(release.getProject());
        Assertions.assertEquals(release.getProject().getId(), response.get(0).getProject());
    }

    /**
     * Test on the valid project getAll and with connected user is "ADMIN" (admin)
     *
     * User can get release of all projects.
     * In this test only one project is tested (1000).
     */
    @Test
    @Transactional
    @WithMockUser(username = "admin")
    void testValidGetAllWithAdminUser() {
        ReleaseEntity release1 = this.releaseRepository.getOne(1000L);
        ReleaseEntity release2 = this.releaseRepository.getOne(1001L);

        List<ReleaseEntity> list = Arrays.asList(release1, release2);

        List<ReleaseDTO> response = this.httpTestUtil.assertNotNullBody(this.releaseResource.getAllByProject(1000L), HttpStatus.OK);

        // Sort the lists with the same order
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        response.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        Assertions.assertEquals(2, response.size(), "2 releases MUST be found");

        // Compare initial list and response list
        for (int i = 0; i < list.size(); i++) {
            Assertions.assertEquals(list.get(i).getId(), response.get(i).getId());
            Assertions.assertEquals(list.get(i).getName(), response.get(i).getName());
            Assertions.assertEquals(list.get(i).getDescription(), response.get(i).getDescription());
            Assertions.assertEquals(list.get(i).getDate(), response.get(i).getDate());
            Assertions.assertNotNull(list.get(i).getProject());
            Assertions.assertEquals(list.get(i).getProject().getId(), response.get(i).getProject());
        }
    }

    /**
     * Test on the invalid project getAll because not connected
     */
    @Test
    @Transactional
    void testInvalidGetAllBecauseNotConnected() {
        // Without id, a technical exception must be returned
        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.getAllByProject(null), Status.NOT_FOUND);

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.getAllByProject(1000L), Status.NOT_FOUND);
    }

    /**
     * Test on the invalid project getAll because not assigned and not project admin (expected : PROJECT_NOT_FOUND)
     */
    @Test
    @Transactional
    @WithMockUser(username = "user")
    void testInvalidGetAllBecauseNotAssignedAndNotProjectAdmin() {
        // Without id, a technical exception must be returned
        this.httpTestUtil.assertTechnicalException(() -> this.releaseResource.getAllByProject(null));

        this.httpTestUtil.assertBusinessException(() -> this.releaseResource.getAllByProject(1000L),
            ProjectConstant.Error.NOT_FOUND,
            Status.NOT_FOUND
        );
    }
}
