package com.nuevatel.common.exception;

/**
 * Created by asalazar on 7/22/15.
 */
public class OperationRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 20150722L;

    public OperationRuntimeException(String msg, Throwable twbl) {
        super(msg, twbl);
    }

    public OperationRuntimeException(String msg) {
        super(msg);
    }
}
