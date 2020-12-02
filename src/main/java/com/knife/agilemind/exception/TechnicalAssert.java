package com.knife.agilemind.exception;

/**
 * Utilitary class for technical asserts
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
public class TechnicalAssert {
    private TechnicalAssert() {
    }

    /**
     * Assert the specified object is not null
     *
     * @param object The object to check
     */
    public static void notNull(Object object) {
        if (object == null) {
            throw new TechnicalException();
        }
    }
}
