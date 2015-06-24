/**
 * 
 */
package com.nuevatel.common.util.worker;

import java.util.Collection;

/**
 * Handle parallel tasks.
 * 
 * @author asalazar
 *
 */
public interface WorkerService {

    /**
     * Schedule worker to execute in background task.
     * 
     * @param worker Delegate to execute.
     * @param args Args to execute delegate.
     */
    <P, R> void execute(Worker<P, R> worker, P args);

    /**
     * Execute worker for N times (N=args.size) and wait. It free when args were finished to consume.
     * <br/><br/>
     * pre-execute -> one time for N iterations.
     * <br/>
     * post-execute -> one time per each iteration (N iterations).
     * <br/>
     * do-in-background -> one time per each iteration (N iterations). args[i] as parameter of do-in-background
     * 
     * @param worker Delegate to execute.
     * @param args Arguments for delegates
     */
    <P, R> void executeAndWait(Worker<P, R> worker, Collection<P>args);

    /**
     * Finalize service. Stop all threads.
     */
    void shutdown();
}
