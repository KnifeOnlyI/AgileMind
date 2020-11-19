package com.knife.agilemind.constant.task;

/**
 * Store all task status constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class TaskStatusConstant {
    private TaskStatusConstant() {
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

        public static final String TODO_KEY = "taskStatus.key.todo";
        public static final String IN_PROGRESS_KEY = "taskStatus.key.inProgress";
        public static final String DONE_KEY = "taskStatus.key.done";

        private DB() {
        }
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "task_status.error.not_found";

        public static final String ID_NULL = "task_status.error.null";

        private Error() {
        }
    }
}
