package com.pf.common.thread;
/**
 * @author zzwei
 * @version 2017年6月21日 下午10:30:05
 */


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolManager {
    private static int CORE_POOL_SIZE = 1;
    private static int MAXI_MUM_POOL_SIZE = 5000;
    private static int KEEP_ALIVE_TIME = 1000;
    private static int QUEUE_SIZE = 500;
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXI_MUM_POOL_SIZE,
            KEEP_ALIVE_TIME,
            TimeUnit.MINUTES,
            new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
            new CustomThreadFactory(),
            new CustomRejectedExecutionHandler());
    private Runnable thread;

    public ThreadPoolManager() {

    }

    public static void exec(Runnable thread) {
        setThread(thread);
        System.out.println("线程名称 " + Thread.currentThread().getName() + "  " + Thread.currentThread().getId());
        pool.execute(thread);
    }

    public Runnable getThread() {
        return thread;
    }

    public static void setThread(Runnable thread) {
        thread = thread;
    }

    public void init() {
        this.pool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXI_MUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(QUEUE_SIZE),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }


    public void destory() {
        if (pool != null) {
            pool.shutdownNow();
        }
    }


    public static ExecutorService getThreadPoolExecutor() {
        return pool;
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = ThreadPoolManager.class.getSimpleName() + count.addAndGet(1);
            System.out.println(threadName);
            t.setName(threadName);
            return t;
        }
    }


    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        ExecutorService pool = ThreadPoolManager.getThreadPoolExecutor();
        System.out.println(pool);
        final int i = 0;
        ThreadPoolManager.exec(new Runnable() {

            @Override
            public void run() {
                for (int j = 0; j < 100000; j++) {
//                    System.out.println(j);
                }
            }
        });
    }

}  