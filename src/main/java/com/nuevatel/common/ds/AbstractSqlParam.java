/**
 * 
 */
package com.nuevatel.common.ds;

/**
 * @author asalazar
 *
 */
public abstract class AbstractSqlParam<T> {

    private T value;

    public AbstractSqlParam(T value) {
        this.value = value;
    }

    public final T getValue() {
        return value;
    }
}
