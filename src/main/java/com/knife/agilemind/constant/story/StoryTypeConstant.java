package com.knife.agilemind.constant.story;

/**
 * Store all story type constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class StoryTypeConstant {
    private StoryTypeConstant() {
    }

    /**
     * All database keys
     *
     * @author Dany Pignoux (dany.pignoux@erudo.fr)
     */
    public static final class DB {
        public static final Long TODO_ID = 1L;
        public static final Long IN_PROGRESS_ID = 2L;
        public static final Long DONE_ID = 3L;

        public static final String FUNCTIONALITY_KEY = "storyType.key.functionality";
        public static final String BUG_KEY = "storyType.key.bug";
        public static final String TECHNICAL_KEY = "storyType.key.technical";

        private DB() {
        }
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "storyType.error.notFound";

        private Error() {
        }
    }
}
