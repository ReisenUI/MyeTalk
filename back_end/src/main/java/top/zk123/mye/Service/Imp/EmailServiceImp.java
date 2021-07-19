package top.zk123.mye.Service.Imp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import top.zk123.mye.Service.EmailService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class EmailServiceImp implements EmailService {


    @Resource
    private JavaMailSender javaMailSender;

    /*
     *
     *
     *
     *
     *
     *
     *
     *
     * TOOL
     *
     *
     *
     *
     *
     *
     *
     *
     *
     */
    @Override
    public void sendSimpleMail(String mailFrom, String mailFromNick, String mailTo, String cc,
                               String subject, String content) {
        try {
            // 多个收件之间只用逗号分隔
            String[] mailToArr = mailTo.split(",");
            for (String address : mailToArr) {
                // 简单邮件信息类
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                // HTML 邮件聚合类
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                // 设置发件人昵称、发件地址
                mimeMessageHelper.setFrom(new InternetAddress(mailFromNick + "<" + mailFrom + ">"));
                mimeMessageHelper.setTo(address);
                // 设置抄送人
                if (!cc.isEmpty()) {
                    mimeMessageHelper.setCc(cc);
                }
                // 设置邮件主题
                mimeMessageHelper.setSubject(subject);
                // 设置邮件内容
                mimeMessageHelper.setText(content, true);
                javaMailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailWithAttachments(String mailFrom, String mailFromNick, String mailTo,
                                        String cc, String subject, String content, List<File> files) {
        try {
            // 多个收件之间只用逗号分隔
            String[] mailToArr = mailTo.split(",");
            for (String address : mailToArr) {
                // 简单邮件信息类
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                // HTML 邮件聚合类;  multipart参数为 true，表示构造一个 Multipart message 类型的邮件；该类型
                // 的邮件包含多个正文、附件及内嵌资源，邮件的表现形式更为丰富。
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                // 设置发件人昵称、发件地址
                mimeMessageHelper.setFrom(new InternetAddress(mailFromNick + "<" + mailFrom + ">"));
                mimeMessageHelper.setTo(address);
                // 设置抄送人
                if (!cc.isEmpty()) {
                    mimeMessageHelper.setCc(cc);
                }
                // 设置邮件主题
                mimeMessageHelper.setSubject(subject);
                // 设置邮件内容
                mimeMessageHelper.setText(content);
                // 添加附件
                if (files != null) {
                    for (File file : files) {
                        mimeMessageHelper.addAttachment(file.getName(), file);
                    }
                }
                javaMailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendMailWithImages(String mailFrom, String mailFromNick, String mailTo, String cc,
                                   String subject, String content, List<String> imagePaths, List<String> imageId) {
        try {
            // 多个收件之间只用逗号分隔
            String[] mailToArr = mailTo.split(",");
            for (String address : mailToArr) {
                // 简单邮件信息类
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                // HTML 邮件聚合类;  multipart参数为 true，表示构造一个 Multipart message 类型的邮件；该类型
                // 的邮件包含多个正文、附件及内嵌资源，邮件的表现形式更为丰富。
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                // 设置发件人昵称、发件地址
                mimeMessageHelper.setFrom(new InternetAddress(mailFromNick + "<" + mailFrom + ">"));
                mimeMessageHelper.setTo(address);
                // 设置抄送人
                if (!cc.isEmpty()) {
                    mimeMessageHelper.setCc(cc);
                }
                // 设置邮件主题
                mimeMessageHelper.setSubject(subject);
                // 设置邮件内容; 第二个参数为 true 表示邮件正文是 html 格式的，默认为 false
                mimeMessageHelper.setText(content, true);
                // 添加图片
                if (imagePaths != null && imagePaths.size() != 0) {
                    for (int i = 0; i < imagePaths.size(); i++) {
                        // 通过 FileSystemResource 构造静态资源
                        FileSystemResource fileSystemResource = new FileSystemResource(imagePaths.get(i));
                        // 将资源加入邮件对象中
                        mimeMessageHelper.addInline(imageId.get(i), fileSystemResource);
                    }
                }
                javaMailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}