package com.knife.agilemind.constant.release;

/**
 * Store all release constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class ReleaseConstant {
    private ReleaseConstant() {
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "release.error.not_found";

        public static final String ID_NULL = "release.error.id.null";
        public static final String NAME_NULL = "release.error.name.null";
        public static final String NAME_EMPTY = "release.error.name.empty";
        public static final String PROJECT_ID_NULL = "release.error.project.id.null";

        private Error() {
        }
    }
}
