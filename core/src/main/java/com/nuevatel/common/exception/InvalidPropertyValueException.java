package com.nuevatel.common.exception;

/**
 * Created by nuevatel.com on 6/23/14.
 */
public class InvalidPropertyValueException extends Exception {

    private static final long serialVersionUID = 20141003;

    public InvalidPropertyValueException(String msg) {
        super(msg);
    }

    public InvalidPropertyValueException(Throwable thrwbl) {
        super(thrwbl);
    }
}
