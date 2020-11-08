package com.knife.agilemind.constant.story;

/**
 * Store all story constants key
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public final class StoryConstant {
    private StoryConstant() {
    }

    /**
     * All error keys
     *
     * @author Dany Pignoux (dany.pignoux@outlook.fr)
     */
    public static final class Error {
        public static final String NOT_FOUND = "story.error.not_found";

        public static final String ID_NULL = "story.error.id.null";
        public static final String NAME_NULL = "story.error.name.null";
        public static final String NAME_EMPTY = "story.error.name.null";

        public static final String POINTS_LESS_0 = "story.error.points.less_0";
        public static final String BUSINESS_VALUE_LESS_0 = "story.error.business.less_0";

        public static final String PROJECT_ID_NULL = "story.error.project.id.null";
        public static final String STATUS_ID_NULL = "story.error.status.id.null";

        private Error() {
        }
    }
}
