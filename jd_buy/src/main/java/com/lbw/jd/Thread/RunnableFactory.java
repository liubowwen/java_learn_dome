package com.lbw.jd.Thread;

import com.lbw.jd.servcie.SendEamil;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author LBW
 * @Classname RunnableFactory
 * @Description 生产任务用
 * @Date 2021/5/31 10:28
 */
public class RunnableFactory {
    public static Runnable thread(String threadName){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.currentThread().setName(threadName);
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Thread thread = Thread.currentThread();

            }
        };
    }

    public synchronized static Runnable sendEmail(SendEamil sendEamil){
        return new Runnable() {
            @Override
            public void run() {
                try {
                    sendEamil.sendEmail();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        };
    }



}
