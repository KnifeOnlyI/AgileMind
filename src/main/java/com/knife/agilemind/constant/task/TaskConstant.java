package com.knife.agilemind.constant.task;

/**
 * Store all task constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class TaskConstant {
    private TaskConstant() {
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "task.error.not_found";

        public static final String ID_NULL = "task.error.id.null";
        public static final String NAME_NULL = "task.error.name.null";
        public static final String NAME_EMPTY = "task.error.name.null";

        public static final String ESTIMATED_TIME_LESS_0 = "task.error.estimatedTime.less_0";
        public static final String LOGGED_TIME_LESS_0 = "task.error.loggedTime.less_0";

        public static final String STORY_ID_NULL = "task.error.story.id.null";
        public static final String STATUS_ID_NULL = "task.error.status.id.null";

        private Error() {
        }
    }
}
