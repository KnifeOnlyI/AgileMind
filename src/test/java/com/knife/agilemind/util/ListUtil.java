package com.knife.agilemind.util;

import com.knife.agilemind.domain.user.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ListUtil {
    /**
     * Assert the actual users contains the expected logins
     *
     * @param actualUsers    The user entity list to test
     * @param expectedLogins The login list to find
     */
    public void assertContains(Set<UserEntity> actualUsers, String... expectedLogins) {
        int expectedLoginsSize = 0;
        int actualUsersSize = 0;

        if (expectedLogins != null) {
            expectedLoginsSize = expectedLogins.length;
        }

        if (actualUsers != null) {
            actualUsersSize = actualUsers.size();
        }

        Assertions.assertEquals(expectedLoginsSize, actualUsersSize, "The lists doesn't have same size");

        if (actualUsers != null && expectedLogins != null) {
            for (String login : expectedLogins) {
                Assertions.assertNotNull(login, "The expected login CANNOT be null");

                Assertions.assertNotNull(actualUsers.stream().findFirst().filter(user -> user.getLogin().equals(login)
                    ).orElse(null), String.format("Cannot find '%s' in actual", login)
                );
            }
        }
    }

    /**
     * Assert actual list contains the expected list
     *
     * @param actual   The actual list
     * @param expected The expected list
     */
    public void assertContains(Set<Long> actual, Long... expected) {
        int expectedSize = 0;
        int actualSize = 0;

        if (expected != null) {
            expectedSize = expected.length;
        }

        if (actual != null) {
            actualSize = actual.size();
        }

        Assertions.assertEquals(expectedSize, actualSize, "The lists doesn't have same size");

        if (actual != null && expected != null) {
            for (Long value : expected) {
                Assertions.assertNotNull(actual.stream().findFirst().filter(userId -> userId.equals(value)
                    ).orElse(null), String.format("Cannot find '%s' in actual", value)
                );
            }
        }
    }
}
