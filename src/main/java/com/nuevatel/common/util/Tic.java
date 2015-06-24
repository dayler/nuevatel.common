/**
 * 
 */
package com.nuevatel.common.util;

import java.util.concurrent.TimeUnit;

/**
 * @author asalazar
 *
 */
public class Tic {

    private long start;

    private long end = -1L;

    private long elapsedTime = -1L;

    public Tic() {
        start = System.currentTimeMillis();
    }

    public long toc() {
        if (-1L == end) {
            end = System.currentTimeMillis();
            elapsedTime = end - start;
        }

        return elapsedTime;
    }
    
    public long toc(TimeUnit unit) {
        return unit.convert(toc(), TimeUnit.MILLISECONDS);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }
}
