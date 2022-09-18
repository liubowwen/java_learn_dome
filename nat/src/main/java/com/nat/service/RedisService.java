package com.nat.service;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author ：lbw
 * @date ：Created in 2022/7/1 9:49
 * @description：TODO
 */
public class RedisService {

    public Integer i = 6;
    public static Lock lock = new ReentrantLock();
    public static Map<String, Jedis> map = new HashMap();

    static {
        for (int i = 0; i < 8; i++) {
            map.put("Thread-" + i, getRedis());
        }
    }

    public static Jedis getRedis() {
        String host = "127.0.0.1";
        Integer port = 6379;
        Jedis redis;
        try {
            redis = new Jedis(host, port, 2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return redis;
    }

    public static void main(String[] args) throws InterruptedException {
        RedisService redisService = new RedisService();
        redisService.test();

    }

    public void test() throws InterruptedException {
        Buy buy = new Buy();

        Thread thread1 = new Thread(buy);

        Thread thread2 = new Thread(buy);

        Thread thread3 = new Thread(buy);

        Thread thread4 = new Thread(buy);

        Thread thread5 = new Thread(buy);

        Thread thread6 = new Thread(buy);

        Thread thread7 = new Thread(buy);

        Thread thread8 = new Thread(buy);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        Thread.sleep(5000);
        System.out.println(i);
    }

    class Buy implements Runnable {


        @Override
        public void run() {
            boolean b = true;
            while (b) {

                if (redisLock()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    buy();
                    b = false;

                }

            }
        }

        public boolean buy() {
            String name = Thread.currentThread().getName();
            if (i > 0) {
                i--;
                System.out.println(name + "抢购成功 还剩" + i);
                return true;
            } else {
                System.out.println(name + "抢购失败");
                return false;
            }

        }

        public boolean redisLock() {
            String name = Thread.currentThread().getName();
            Jedis jedis = map.get(name);
            String luaScripts = "if redis.call('setnx',KEYS[1],ARGV[1]) == 1 then" +
                    " redis.call('PEXPIRE',KEYS[1],ARGV[1]) return 1 else return 0 end";
            Object result = jedis.eval(luaScripts, Collections.singletonList("buy"), Collections.singletonList("50"));

//判断是否成功
            jedis.close();
            return result.equals(1L);
        }
    }


}
