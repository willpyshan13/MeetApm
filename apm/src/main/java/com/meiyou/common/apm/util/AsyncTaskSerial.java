package com.meiyou.common.apm.util;

import android.os.AsyncTask;

import java.util.ArrayDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GA线程任务 使用独立线程池。一条线程执行,先进先出
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/3
 */

public abstract class AsyncTaskSerial<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    //    private static ExecutorService SINGLE_TASK_EXECUTOR;
//    private static ExecutorService LIMITED_TASK_EXECUTOR;
//    private static ExecutorService FULL_TASK_EXECUTOR;

//    static {
//        SINGLE_TASK_EXECUTOR = Executors.newSingleThreadExecutor();
//        LIMITED_TASK_EXECUTOR = Executors.newFixedThreadPool(7);
    //FULL_TASK_EXECUTOR = Executors.newCachedThreadPool();这个导致APP开线程太多报OOM了
//        FULL_TASK_EXECUTOR = Executors.newFixedThreadPool(5);
//    }


    /**
     * 并行执行
     * execute 在4.0后默认效果是 先进先出，一条线程执行；
     * executeOnExecutor 可以用改为线程池运行
     * http://blog.csdn.net/mddy2001/article/details/17127065
     * <p>
     * 自定义的CorePoolSize为7的Executor(Executors.newFixedThreadPool(7))：
     * 使用未设限制的Executor(Executors.newCachedThreadPool())：
     * 默认效果，单个线程池的executeOnExecutor(AsyncTask.SERIAL_EXECUTOR)
     * 内置的5个线程池的： AsyncTask.THREAD_POOL_EXECUTOR
     *
     * @param params
     * @return
     */
    public AsyncTask executeOnExecutor(Params... params) {
        return executeOnExecutor(SERIAL_EXECUTOR, params);
    }

    
    /**
    * 串行线程池
     * An {@link Executor} that executes tasks one at a time in serial
     * order.  This serialization is global to a particular process.
     */
    public static final Executor SERIAL_EXECUTOR = new SerialExecutor();
    
    private static class SerialExecutor implements Executor {
        final ArrayDeque<Runnable> mTasks = new ArrayDeque<Runnable>();
        Runnable mActive;

        public synchronized void execute(final Runnable r) {
            mTasks.offer(new Runnable() {
                public void run() {
                    try {
                        r.run();
                    } finally {
                        scheduleNext();
                    }
                }
            });
            if (mActive == null) {
                scheduleNext();
            }
        }

        protected synchronized void scheduleNext() {
            if ((mActive = mTasks.poll()) != null) {
                THREAD_POOL_EXECUTOR.execute(mActive);
            }
        }
    }


    /**
     * 并行线程池
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTaskSerial #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(128);

    /**
    * 并行线程池
     * An {@link Executor} that can be used to execute tasks in parallel.
     */
    public static final Executor THREAD_POOL_EXECUTOR
            = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE,
            TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
            
}