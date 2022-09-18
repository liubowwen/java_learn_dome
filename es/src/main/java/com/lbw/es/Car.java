package com.lbw.es;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author LBW
 * @Classname Car
 * @Description TODO
 * @Date 2021/6/2 10:47
 */
public class Car {
    private int a = 0;
    private AtomicReference<Thread> cas = new AtomicReference<Thread>();
    private static Hashtable<String,Integer> map=new Hashtable<>();

    static {
        map.put("v",0);
    }

    public AtomicReference<Thread> getCas() {
        return cas;
    }

    public void lock() {
        Thread current = Thread.currentThread();
        while (!cas.compareAndSet(null, current)) {

        }

    }

    public void unlock() {

        Thread current = Thread.currentThread();
        cas.compareAndSet(current, null);

    }


    public  void update() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer s = map.get("v");
        lock();
        int b=a;
        b++;
        if (map.get("v").equals(s))
        {
            a=b;
            map.put("v",s++);
        }else {
            update();
        }
        unlock();

    }
    public synchronized void updateLock() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        a++;


    }




}
