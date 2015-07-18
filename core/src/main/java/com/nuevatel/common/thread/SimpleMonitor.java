/**
 *
 */
package com.nuevatel.common.thread;

/**
 * Simple monitor, used to handle concurrency.
 *
 * @author asalazar
 */
public final class SimpleMonitor {

    /**
     * Execute synchronized wait().
     *
     * @throws InterruptedException
     */
    public void doWait() throws InterruptedException {
        synchronized (this) {
            wait();
        }
    }

    /**
     * Execute synchronized notifyAll().
     */
    public void doNotifyAll() {
        synchronized (this) {
            notifyAll();
        }
    }


    /**
     * Execute synchronized wait().
     * 
     * @param timeout Timeout to await by the release in milliseconds.
     *
     * @throws InterruptedException
     */
    public void doWait(long timeout) throws InterruptedException {
        synchronized (this) {
            wait(timeout);
        }
    }
}
