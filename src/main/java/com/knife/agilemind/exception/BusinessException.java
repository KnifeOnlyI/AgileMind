package com.knife.agilemind.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Represent a business exception throwed because user done an invalid action (e.g : send invalid DTO)
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class BusinessException extends AbstractThrowableProblem {
    private static final long serialVersionUID = -5850196435079623112L;

    /**
     * Create new business exception without error key
     *
     * @param status The HTTP status
     */
    public BusinessException(Status status) {
        super(null, null, status);
    }

    /**
     * Create new business exception
     *
     * @param errorKey The error key
     * @param status   The HTTP status
     */
    public BusinessException(String errorKey, Status status) {
        super(null, errorKey, status);
    }
}
