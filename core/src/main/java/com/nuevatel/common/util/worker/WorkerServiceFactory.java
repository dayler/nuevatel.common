/**
 * 
 */
package com.nuevatel.common.util.worker;

/**
 * @author asalazar
 *
 */
public final class WorkerServiceFactory {

    private WorkerServiceFactory() {
        // No op
    }

    public static WorkerService getWorkerService(int size) {
        return new WorkerServiceImpl(size);
    }
}
