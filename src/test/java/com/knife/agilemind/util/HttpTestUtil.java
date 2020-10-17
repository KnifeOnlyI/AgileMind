package com.knife.agilemind.util;

import com.knife.agilemind.exception.BusinessException;
import com.knife.agilemind.exception.TechnicalException;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zalando.problem.StatusType;

/**
 * Utilitary service class to manage HTTP assertions
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
@Service
public class HttpTestUtil {
    /**
     * Assert get a not null body from the specified HTTP response and has the specified HTTP status code
     *
     * @param response       The HTTP response
     * @param expectedStatus The expected HTTP status code (Can be null to not expected any status)
     * @param <T>            The HTTP response type
     *
     * @return The HTTP response body
     */
    public <T> T getNotNullBody(ResponseEntity<T> response, HttpStatus expectedStatus) {
        Assertions.assertNotNull(response, "The HTTP response MUST NOT be null");

        if (expectedStatus != null) {
            Assertions.assertEquals(response.getStatusCode(), expectedStatus, String.format(
                "The HTTP response status code MUST BE equals to : %s", expectedStatus
            ));
        }

        T body = response.getBody();

        Assertions.assertNotNull(body, "The HTTP response body MUST NOT be null");

        return body;
    }

    /**
     * Assert the specified HTTP response has null body and the specified HTTP status code
     *
     * @param response       The HTTP response
     * @param expectedStatus The expected HTTP status code (Can be null to not expected any status)
     */
    public <T> void assertNullBody(ResponseEntity<T> response, HttpStatus expectedStatus) {
        Assertions.assertNotNull(response, "The HTTP response MUST NOT be null");

        if (expectedStatus != null) {
            Assertions.assertEquals(response.getStatusCode(), expectedStatus, String.format(
                "The HTTP response status code MUST BE equals to : %s", expectedStatus
            ));
        }

        Assertions.assertNull(response.getBody(), "The HTTP response body MUST be null");
    }

    /**
     * Assert the specified action throws a business exception with the specified errorKey and status
     *
     * @param testedAction     The action to test
     * @param expectedErrorKey The expected error key
     * @param expectedStatus   The expected status
     */
    public void assertBusinessException(TestedAction testedAction, String expectedErrorKey, StatusType expectedStatus) {
        try {
            testedAction.doAction();

            Assertions.fail(String.format("Expected exception of type '%s', none was thrown",
                BusinessException.class.getName()
            ));
        } catch (BusinessException e) {
            Assertions.assertEquals(expectedErrorKey, e.getTitle());
            Assertions.assertEquals(expectedStatus, e.getStatus());
        } catch (Exception e) {
            Assertions.fail(String.format("Expected exception of type '%s' , '%s' thrown",
                BusinessException.class.getName(),
                e.getClass().getName()
            ));
        }
    }

    /**
     * Assert the specified action throws a technical exception with the specified errorKey and status
     *
     * @param testedAction The action to test
     */
    public void assertTechnicalException(TestedAction testedAction) {
        String errorName = null;

        try {
            testedAction.doAction();
        } catch (Exception e) {
            errorName = e.getClass().getName();
        }

        if (errorName == null) {
            Assertions.fail(String.format("Expected exception of type '%s', none was thrown",
                TechnicalException.class.getName()
            ));
        } else if (!errorName.equals(TechnicalException.class.getName())) {
            Assertions.fail(String.format("Expected exception of type '%s', '%s' thrown",
                TechnicalException.class.getName(),
                errorName
            ));
        }
    }

    /**
     * Interface for tested action
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public interface TestedAction {
        /**
         * Action to do
         */
        void doAction();
    }
}
