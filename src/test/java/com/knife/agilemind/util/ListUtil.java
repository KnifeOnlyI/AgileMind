package com.knife.agilemind.util;

import com.knife.agilemind.domain.story.StoryEntity;
import com.knife.agilemind.domain.user.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ListUtil {
    /**
     * Assert the actual users contains the expected logins
     *
     * @param actual   The user entity list to test
     * @param expected The login list to find
     */
    public void assertContainsUsers(Set<UserEntity> actual, String... expected) {
        int expectedSize = expected != null ? expected.length : 0;
        int actualSize = actual != null ? actual.size() : 0;

        Assertions.assertEquals(expectedSize, actualSize, "The lists doesn't have same size");

        if (actual != null && expected != null) {
            for (String expectedLogin : expected) {
                Assertions.assertTrue(
                    actual.stream().anyMatch(story -> story.getLogin().equals(expectedLogin)),
                    String.format("Cannot find '%s' in actual", expectedLogin)
                );
            }
        }
    }

    /**
     * Assert the actual stories contains the expected ids
     *
     * @param actual   The story entity list to test
     * @param expected The ID list to find
     */
    public void assertContainsStories(Set<StoryEntity> actual, Long... expected) {
        int expectedSize = expected != null ? expected.length : 0;
        int actualSize = actual != null ? actual.size() : 0;

        Assertions.assertEquals(expectedSize, actualSize, "The lists doesn't have same size");

        if (actual != null && expected != null) {
            for (Long id : expected) {
                Assertions.assertNotNull(id, "The expected ID CANNOT be null");

                Assertions.assertTrue(
                    actual.stream().anyMatch(story -> story.getId().equals(id)),
                    String.format("Cannot find '%s' in actual", id)
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
    public void assertContainsID(Set<Long> actual, Long... expected) {
        int expectedSize = expected != null ? expected.length : 0;
        int actualSize = actual != null ? actual.size() : 0;

        Assertions.assertEquals(expectedSize, actualSize, "The lists doesn't have same size");

        if (actual != null && expected != null) {
            for (Long value : expected) {
                Assertions.assertNotNull(value, "The expected value CANNOT be null");

                Assertions.assertTrue(
                    actual.stream().anyMatch(actualValue -> actualValue.equals(value)),
                    String.format("Cannot find '%s' in actual", value)
                );
            }
        }
    }
}
