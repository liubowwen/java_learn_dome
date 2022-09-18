package com.lbw.manager;

import java.util.Random;

/**
 * @author ：lbw
 * @date ：Created in 2022/6/23 11:10
 * @description：TODO
 */
public class ThreadLocalService {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static void set(Integer i) {
        Random r=new Random();
        i = r.nextInt(100);
        threadLocal.set(i);
    }

    public static Integer get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

}
