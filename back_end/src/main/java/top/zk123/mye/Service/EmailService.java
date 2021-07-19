package top.zk123.mye.Service;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

public interface EmailService {

    /*
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
     */

    /**
     * 发送普通文本邮件
     *
     * @param mailFrom     发件人邮箱
     * @param mailFromNick 发件人昵称
     * @param mailTo       收件人邮箱（多个收件人之间用逗号分割）
     * @param cc           抄送人（可为空）
     * @param subject      主题
     * @param content      内容
     */
    void sendSimpleMail(String mailFrom, String mailFromNick, String mailTo, String cc,
                        String subject, String content);


    /**
     * 发送带有附件的邮件
     *
     * @param mailFrom     发件人邮箱
     * @param mailFromNick 发件人昵称
     * @param mailTo       收件人邮箱（多个收件人之间用逗号分割）
     * @param cc           抄送人（可为空）
     * @param subject      主题
     * @param content      内容
     * @param files        文件
     */
    void sendMailWithAttachments(String mailFrom, String mailFromNick, String mailTo, String cc,
                                 String subject, String content, List<File> files);

    /**
     * 发送邮件：正文内容带图片
     *
     * @param mailFrom     发件人邮箱
     * @param mailFromNick 发件人昵称
     * @param mailTo       收件人邮箱（多个收件人之间用逗号分割）
     * @param cc           抄送人（可为空）
     * @param subject      主题
     * @param content      内容
     * @param imagePaths   图片地址
     * @param imageId      图片Id
     */
    void sendMailWithImages(String mailFrom, String mailFromNick, String mailTo, String cc,
                            String subject, String content, List<String> imagePaths, List<String> imageId);

}
