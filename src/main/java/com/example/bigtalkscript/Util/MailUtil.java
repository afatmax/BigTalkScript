package com.example.bigtalkscript.Util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Properties;

public class MailUtil {

    /**
     * 本方法用于发送QQ邮箱到指定收件人
     *
     * @param senderAccount     - 寄件人的QQ账号
     * @param authorizationCode - 寄件人的邮件POP3/SMTP服务的授权码
     * @param recipientAccount  - 收件人的QQ账号
     * @param MailTheme         - 指定邮件主题
     * @param MailContent       - 指定邮件内容
     *
     * @throws Exception - 如果网络故障或者因授权码输入错误导致身份验证失败则抛出异常
     *
     */
    public static void sendMessages(String senderAccount, String authorizationCode, String recipientAccount, String MailTheme, String MailContent,String filePath) throws Exception {

        // 创建Properties类存储邮箱属性
        Properties props = new Properties();
        // 表示SMTP发送邮件时是否进行身份验证
        props.put("mail.smtp.auth", "true");
        // 表示SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        // 表示SMTP端口号(推荐25,587)
        props.put("mail.smtp.port", "587");
        // 表示发邮件的QQ账号
        props.put("mail.user", senderAccount + "@qq.com");
        // 表示该账号的POP3/SMTP服务的授权码
        props.put("mail.password", authorizationCode);
        // 创建授权信息,用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 创建Session邮件会话类
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);
        // 设置收件人
        InternetAddress to = new InternetAddress(recipientAccount + "@qq.com");
        message.setRecipient(Message.RecipientType.TO, to);
        // 设置邮件主题
        message.setSubject(MailTheme, "UTF-8");
        // 创建图片"节点"
        MimeBodyPart image = new MimeBodyPart();
        // 获取屏幕的尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 截取屏幕
        BufferedImage img = new Robot().createScreenCapture(new Rectangle(screenSize));
        // 调整图片大小(参数可自调)
        img = setImgSize(img, 500, 300);
        // 保存图片到桌面(你可以保存到其他位置)--------------
        ImageIO.write(img, "png", new File(filePath));
        // 读取本地文件
        DataHandler dataHandler = new DataHandler(new FileDataSource(filePath));
        // 将图片添加到"节点"
        image.setDataHandler(dataHandler);
        // 为"节点"设置一个唯一编号
        image.setContentID("mailPng");
        // 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 设置文本内容
        text.setContent(MailContent + "<br/><img src='cid:mailPng'/></a>", "text/html;charset=UTF-8");
        // 设置 文本"节点" 和 图片 "节点"的关系(将 文本"节点" 和 图片 "节点" 合成一个复合"节点")
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(image);
        // 关联关系
        mm_text_image.setSubType("related");
        // 将 复合"节点"封装成一个普通"节点"
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);
        // 设置文本,图片的关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        // 混合关系
        mm.setSubType("mixed");
        // 设置整个邮件的关系
        message.setContent(mm);
        // 设置邮件日期
        message.setSentDate(new Date());
        // 发送邮件
        Transport.send(message);
        // 删除图片
        new File(filePath).delete();

    }

    // 本方法就是来调整图片的大小
    public static BufferedImage setImgSize(BufferedImage img, int newImgWidth, int newImgHeight) {

        Image image = img.getScaledInstance(newImgWidth, newImgHeight, Image.SCALE_SMOOTH);
        BufferedImage newImg = new BufferedImage(newImgWidth, newImgHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D paint = newImg.createGraphics();
        paint.drawImage(image, 0, 0, null);
        paint.dispose();

        return newImg;
    }

}
