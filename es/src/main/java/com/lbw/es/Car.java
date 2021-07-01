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


    public static void main(String[] args) throws InterruptedException {

        Car car=new Car();
        car.lock();
        AtomicReference<Thread> cas = car.getCas();
        Thread thread = cas.get();
        System.out.println(thread);
        new Thread(() -> {
            Car car1=new Car();
            car.lock();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AtomicReference<Thread> cas1 = car.getCas();
            Thread thread1 = cas1.get();
            System.out.println(thread1);
            car.unlock();
            thread1 = cas1.get();
            System.out.println(thread1);
        }).start();
        Thread.sleep(100);
        car.unlock();
        thread = cas.get();
        System.out.println("主线程 "+thread);
//        HashMap<Integer,Integer> map=new HashMap();
//        for (int j = 0; j < 1000; j++) {
//            Car car = new Car();
//            for (int i = 0; i < 1000; i++) {
//                new Thread(() -> car.update()).start();
//            }
//            Thread.sleep(5);
//            if (map.containsKey(car.a))
//            {
//                Integer o = map.get(car.a);
//                o=o+1;
//                map.put(car.a,o);
//            }else {
//                map.put(car.a,1);
//            }
//
//        }
//        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey()+" : " + entry.getValue());
//        }
    }

}
