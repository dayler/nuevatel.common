/**
 *
 */
package com.nuevatel.common.exception;

/**
 * Common main exception.
 *
 * @author asalazar
 */
public class OperationException extends Exception {

    private static final long serialVersionUID = 20140624L;

    public OperationException(String msg, Throwable twbl) {
        super(msg, twbl);
    }

    public OperationException(String msg) {
        super(msg);
    }
}
