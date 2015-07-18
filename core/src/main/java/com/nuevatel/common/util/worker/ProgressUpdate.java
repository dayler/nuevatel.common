/**
 * 
 */
package com.nuevatel.common.util.worker;

import com.nuevatel.common.exception.OperationException;

/**
 * Allows notify the progress of asynch task.
 * 
 * @author asalazar
 *
 */
public interface ProgressUpdate <I> {

    /**
     * Action to perform to notify the progress.
     * 
     * @param args Indicator of progress.
     * @throws OperationException
     */
    void update(I args) throws OperationException;
}
