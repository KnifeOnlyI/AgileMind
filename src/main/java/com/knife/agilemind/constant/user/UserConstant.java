package com.knife.agilemind.constant.user;

/**
 * Store all user constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class UserConstant {
    private UserConstant() {
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "project.error.not_found";

        private Error() {
        }
    }

    /**
     * Store IDs
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Id {
        /**
         * ID for admin user
         */
        public static final long ADMIN = 3L;

        private Id() {
        }
    }

    /**
     * Store all cache keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Cache {
        /**
         * Key for cache by login
         */
        public static final String USERS_BY_LOGIN_CACHE = "usersByLogin";

        /**
         * Key for cache by email
         */
        public static final String USERS_BY_EMAIL_CACHE = "usersByEmail";

        private Cache() {
        }
    }
}
