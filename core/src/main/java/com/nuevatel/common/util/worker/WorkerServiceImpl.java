/**
 * 
 */
package com.nuevatel.common.util.worker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;

import com.nuevatel.common.exception.OperationException;
import com.nuevatel.common.util.Parameters;

/**
 * 
 * @author asalazar
 *
 */
class WorkerServiceImpl implements WorkerService {

    private static final int DEFAULT_TIMEOUT = 2000;

    private ExecutorService service = null;

    /**
     * Time out in milliseconds for callable tasks.
     */
    private int callableTimeout = DEFAULT_TIMEOUT;

    public WorkerServiceImpl(int size) {
        service = Executors.newFixedThreadPool(size);
    }

    public WorkerServiceImpl(int size, int timeout) {
        this(size);
        this.callableTimeout = timeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized <P, R> void execute(Worker<P, R> worker, P args) {
        Parameters.checkNull(worker, "worker");

        try {
            // do pre-execute
            worker.onPreExecute();
            // Schedule task to do on background
            WorkerTask<P, R>task = new WorkerTask<P, R>(worker, args);
            service.execute(task);
        } catch (OperationException ex) {
            worker.onError(ex, args);
        }
    }

    @Override
    public <P, R> void executeAndWait(Worker<P, R> worker, Collection<P> args) {
        // On pre-execute
        Iterator<P>it = args.iterator();
        List<CallableTask<P, R>>tasks = new ArrayList<CallableTask<P, R>>();
        P param = null;

        while (it.hasNext()) {
            param = it.next();
            tasks.add(new CallableTask<P, R>(worker, param));
        }

        try {
            // just one execute for all iterations.
            worker.onPreExecute();
            // Schedule all tasks in the service.
            List<Future<R>>fTask = service.invokeAll(tasks);
            for (Future<R> future : fTask) {
                R result = future.get(callableTimeout, TimeUnit.MILLISECONDS);
                System.out.println(result);
                // On post execute is on handler OnPostExecuteTask.
            }
        } catch (Throwable ex) {
            worker.onError(ex, param);
        }
    }

    @Override
    public void shutdown() {
        service.shutdown();
    }

    private static final class OnPostExecuteTask<P, R> implements Runnable {

        private Worker<P, R>worker;

        private R results;

        public OnPostExecuteTask(Worker<P, R>worker, R results) {
            this.worker = worker;
            this.results = results;
        }

        @Override
        public void run() {
            try {
                worker.onPostExecute(results);
            } catch (OperationException ex) {
                worker.onError(ex, null);
            }
        }

    }

    private static final class CallableTask<P, R> implements Callable<R> {

        private Worker<P, R> worker;

        private P arg;

        public CallableTask(Worker<P, R> worker, P arg) {
            this.worker = worker;
            this.arg = arg;
        }

        @Override
        public R call() throws Exception {
            try {
                R result = worker.doInBackground(arg);
                // Back main thread.s
                SwingUtilities.invokeLater(new OnPostExecuteTask<P, R>(worker, result));
                return result;
            } catch (Throwable ex) {
                worker.onError(ex, arg);
                return null;
            }
        }
    }

    private static final class WorkerTask <P, R> implements Runnable {

        private Worker<P, R>worker;

        private P args;

        public WorkerTask(Worker<P, R>worker, P args) {
            this.worker = worker;
            this.args = args;
        }

        @Override
        public void run() {
            try {
                R result = worker.doInBackground(args);
                // Back main thread.s
                SwingUtilities.invokeLater(new OnPostExecuteTask<P, R>(worker, result));
            } catch (OperationException ex) {
                worker.onError(ex, args);
            }
        }
    }
}
