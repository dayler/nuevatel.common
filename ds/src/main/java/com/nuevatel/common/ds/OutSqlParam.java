package com.nuevatel.common.ds;

/**
 * 
 * @author asalazar
 *
 */
public class OutSqlParam extends AbstractSqlParam<Integer> {

    OutSqlParam(Integer jdbcType) {
        super(jdbcType);
    }

    public final static AbstractSqlParam<Integer> get(Integer jdbcType) {
        return new OutSqlParam(jdbcType);
    }
}
