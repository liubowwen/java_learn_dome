package com.lbw.manager;

import java.util.TimerTask;
import java.util.concurrent.ThreadFactory;

/**
 * @author LBW
 * @Classname RunnableFactory
 * @Description 生产任务用
 * @Date 2021/5/31 10:28
 */
public class RunnableFactory  {
    public static Runnable thread(final String threadName){
        return new Runnable() {

            public void run() {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread thread = Thread.currentThread();

            }
        };
    }

}
