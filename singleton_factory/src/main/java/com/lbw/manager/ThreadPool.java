package com.lbw.manager;

import java.util.concurrent.*;

/**
 * @author LBW
 * @Classname ThreadPool
 * @Description 初始化线程池
 * @Date 2021/5/31 10:08
 */
public class ThreadPool {
    // 核心线程池大小
    private static int corePoolSize = 5;

    // 最大可创建的线程数
    private static int maxPoolSize = 10;

    // 队列最大长度
    private  static int queueCapacity = 3000;

    // 线程池维护线程所允许的空闲时间
    private static long keepAliveSeconds = 3000;

    public static ThreadPoolExecutor init() {
        System.out.println("-----------------线程被初始化----------------");
        return new ThreadPoolExecutor(corePoolSize,maxPoolSize,keepAliveSeconds, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(queueCapacity),
                new MyThreadFactory());
    }
    public static ScheduledExecutorService  initScheduled() {
      return  Executors.newScheduledThreadPool(corePoolSize,new MyThreadFactory());
    }
    private static class MyThreadFactory implements  ThreadFactory{
        public Thread newThread(Runnable r) {
              Thread thread=new Thread(r);
              thread.setDaemon(true);
            return thread;
        }
    }
}
