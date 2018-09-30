package cn.com.jinke.wh_drugcontrol.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: jinke
 * @Date: 2016/4/19 14:24
 * @Description: 单线程池
 */
public class SingleThreadPool {
    private static Object sLock = new Object();
    private static ExecutorService sExecutor;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "SingleThreadPool(1 SingleThread) Thread #" + mCount.getAndIncrement());
        }
    };
    
    /* package */ static void submit(Runnable task) {
        if (task != null) {
            getThreadPool().submit(task);
        }
    }
    
    public static ExecutorService getThreadPool() {
        if (sExecutor == null) {
            synchronized (sLock) {
                if (sExecutor == null) {
                    sExecutor = Executors.newSingleThreadExecutor(sThreadFactory);
                }
            }
        }
        return sExecutor;
    }
}

