package com.nuevatel.common.ds;

/**
 * 
 * @author asalazar
 *
 */
public class InSqlParam<T> extends AbstractSqlParam<T> {

    InSqlParam(T value) {
        super(value);
    }

    public final static <T> AbstractSqlParam<T> get(T value) {
        return new InSqlParam<T>(value);
    }
}
