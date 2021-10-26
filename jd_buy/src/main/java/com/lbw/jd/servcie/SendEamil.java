package com.lbw.jd.servcie;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEamil {
    private static Session session;
    private String email;
    private String message;
    private String title;

    public SendEamil(String email, String message, String title) {
        this.email = email;
        this.message = message;
        this.title = title;
    }

    public  Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        SendEamil.session = session;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }




    public void sendEmail() throws MessagingException {
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");
        try {
            //QQ存在一个特性设置SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            //创建一个session对象
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("854226741@qq.com", "gniiabixzgvubefd");
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        //开启debug模式
        getSession().setDebug(true);
        //获取连接对象
        Transport transport = getSession().getTransport();
        //连接服务器
        transport.connect("smtp.qq.com", "854226741@qq.com", "gniiabixzgvubefd");
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(getSession());
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("854226741@qq.com"));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //邮件标题
        mimeMessage.setSubject(title);
        //邮件内容
        mimeMessage.setContent(message, "text/html;charset=UTF-8");
        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
    }


}
