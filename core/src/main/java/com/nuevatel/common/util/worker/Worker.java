/**
 * 
 */
package com.nuevatel.common.util.worker;

import com.nuevatel.common.exception.OperationException;

/**
 * Delegate for async task
 * 
 * @author asalazar
 *
 */
public interface Worker <P, R> {

    /** 
     * Before to execute in secondary three 
     * 
     * @throws OperationException Exception occurs meanwhile operation was performed.
     */
    void onPreExecute() throws OperationException;

    /**
     * Action to do in secondary three.
     * 
     * @param args Args to launch the action.
     * @return Result of action performed. Is used as argument of onPostExecute,
     * @throws OperationException Exception occurs meanwhile operation was performed.
     */
    R doInBackground(P args) throws OperationException;

    /**
     * Action to perform once doInBackground was finished.
     * 
     * @param args Args to launch the action.
     * @throws OperationException Exception occurs meanwhile operation was performed.
     */
    void onPostExecute(R args) throws OperationException;

    /**
     * To handle any exception or error on any of previous actions.
     * 
     * @param ex Exception to cause the error
     * @param args Args to launch the action, it is the same of doInBackground
     */
    void onError(Throwable ex, P args);
}
