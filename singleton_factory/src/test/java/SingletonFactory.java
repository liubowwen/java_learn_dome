import com.lbw.manager.RunnableFactory;
import com.lbw.manager.ThreadPoolManager;
import org.junit.Test;

import java.util.Scanner;

/**
 * @author LBW
 * @Classname SingletonFactory
 * @Description TODO
 * @Date 2021/5/31 10:35
 */
public class SingletonFactory {
    /**
     * 线程池
     */

    public  void t1() throws InterruptedException {
        thread();


    }

    public static void main(String[] args) throws InterruptedException {
         long pool=0;
         long notPool=0;
        SingletonFactory singletonFactory=new SingletonFactory();
        for (int i = 0; i <10; i++) {

            long timeMillis = System.currentTimeMillis();
            singletonFactory.thread();
            pool+=System.currentTimeMillis()-timeMillis;
            System.out.println("使用线程总池耗时"+pool+"ms");
            long timeMillis1 = System.currentTimeMillis();
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            singletonFactory.run("创建一个接口2");
            notPool+=(System.currentTimeMillis()-timeMillis1-9);
            System.out.println("不使用线程池耗总时"+notPool+"ms");
            System.out.println("--------------------数量: "+i+"-----------------------");
        }

    }

    public void thread(){
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口1"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口2"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口3"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口4"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口5"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口6"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口7"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口8"));
        ThreadPoolManager.getMe().executor(RunnableFactory.thread("创建一个接口9"));
    }
    public void run(String threadName){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
