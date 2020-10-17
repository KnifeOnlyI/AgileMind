package com.knife.agilemind.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

/**
 * Represent a technical exception throwed when server error occurs
 *
 * @author Dany Pignoux (dany.pignoux@outlook.fr)
 */
public class TechnicalException extends AbstractThrowableProblem {
    private static final long serialVersionUID = -2549634333610957653L;

    /**
     * Create new technical exception (500 Internal server error with key 'error.technical_exception')
     */
    public TechnicalException() {
        super(null, "error.technical_exception", Status.INTERNAL_SERVER_ERROR);
    }
}
