package com.knife.agilemind.constant.project;

/**
 * Store all project constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class ProjectConstant {
    private ProjectConstant() {
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "project.error.not_found";

        public static final String ID_NULL = "project.error.id.null";
        public static final String NAME_NULL = "project.error.name.null";
        public static final String NAME_EMPTY = "project.error.name.empty";

        public static final String USER_NOT_ASSIGNED = "project.error.user.not_assigned";

        private Error() {
        }
    }
}
