package com.knife.agilemind.exception;

import org.apache.commons.lang3.StringUtils;
import org.zalando.problem.Status;

/**
 * Utilitary class for technical asserts
 *
 * @author Dany Pignoux (dany.pignoux@erudo.fr)
 */
public class BusinessAssert {
    private BusinessAssert() {
    }

    /**
     * Assert the specified object is not null
     *
     * @param object   The object to check
     * @param errorKey The error key if object is null
     * @param status   The status if object is null
     */
    public static void notNull(Object object, String errorKey, Status status) {
        if (object == null) {
            throw new BusinessException(errorKey, status);
        }
    }

    /**
     * Assert the specified string is not empty
     *
     * @param string   The string to check
     * @param errorKey The error key if string is empty
     * @param status   The status if object is empty
     */
    public static void notEmpty(String string, String errorKey, Status status) {
        if (StringUtils.isBlank(string)) {
            throw new BusinessException(errorKey, status);
        }
    }

    /**
     * Check if the specified value is lower than the specified max value
     *
     * @param value    The value to check
     * @param maxValue The maximum value
     * @param errorKey The error key if error
     * @param status   The status if error
     * @param <T>      The comparable type
     */
    public static <T extends Comparable<T>> void lowerThan(T value, T maxValue, String errorKey, Status status) {
        if (value != null) {
            TechnicalAssert.notNull(maxValue);

            if (value.compareTo(maxValue) >= 0) {
                throw new BusinessException(errorKey, status);
            }
        }
    }

    /**
     * Check if the specified value is lower or equals than the specified max value
     *
     * @param value    The value to check
     * @param maxValue The maximum value
     * @param errorKey The error key if error
     * @param status   The status if error
     * @param <T>      The comparable type
     */
    public static <T extends Comparable<T>> void lowerOrEqualsThan(
        T value,
        T maxValue,
        String errorKey,
        Status status
    ) {
        if (value != null) {
            TechnicalAssert.notNull(maxValue);

            if (value.compareTo(maxValue) > 0) {
                throw new BusinessException(errorKey, status);
            }
        }
    }

    /**
     * Check if the specified value is greater than the specified min value
     *
     * @param value    The value to check
     * @param minValue The maximum value
     * @param errorKey The error key if error
     * @param status   The status if error
     * @param <T>      The comparable type
     */
    public static <T extends Comparable<T>> void greaterThan(T value, T minValue, String errorKey, Status status) {
        if (value != null) {
            TechnicalAssert.notNull(minValue);

            if (value.compareTo(minValue) <= 0) {
                throw new BusinessException(errorKey, status);
            }
        }
    }

    /**
     * Check if the specified value is greater or equals than the specified min value
     *
     * @param value    The value to check
     * @param minValue The maximum value
     * @param errorKey The error key if error
     * @param status   The status if error
     * @param <T>      The comparable type
     */
    public static <T extends Comparable<T>> void greaterOrEqualsThan(
        T value,
        T minValue,
        String errorKey,
        Status status
    ) {
        if (value != null) {
            TechnicalAssert.notNull(minValue);

            if (value.compareTo(minValue) < 0) {
                throw new BusinessException(errorKey, status);
            }
        }
    }

    /**
     * Assert the specified bool is true and not null
     *
     * @param bool     The bool to check
     * @param errorKey The error key if bool is false or null
     * @param status   The status if bool is false or null
     */
    public static void isTrue(boolean bool, String errorKey, Status status) {
        if (!bool) {
            throw new BusinessException(errorKey, status);
        }
    }
}
