package com.lbw.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author LBW
 * @Classname ThreadPoolManager
 * @Description TODO
 * @Date 2021/5/31 10:21
 */
public class ThreadPoolManager {
    private static long DELAY=1;
    private ThreadPoolExecutor executorService=ThreadPool.init();
    private static ThreadPoolManager me = new ThreadPoolManager();

    /**
     * 单例模式
     */
    public static ThreadPoolManager getMe(){
        return me;
    }

    /**
     * 执行
     */
    public synchronized void executor(Runnable runnable){
        executorService.execute(runnable);

    }
    /**
     * 停止
     */
    public void shutdown(){
        executorService.shutdown();
    }
}
