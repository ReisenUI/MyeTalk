package top.zk123.mye.Util;

import java.io.*;
import java.net.URL;

public class EmailTemplate {
    public String getHtml(String title, String userName, String type, String code) {
        String emailTemplate = getFile("emailTemplate");
        emailTemplate = emailTemplate.replace("$(title)", title);
        emailTemplate = emailTemplate.replace("$(userName)", userName);
        emailTemplate = emailTemplate.replace("$(type)", type);
        emailTemplate = emailTemplate.replace("$(code)", code);


        emailTemplate = emailTemplate.replace("$(str1)", "亲爱的用户，你好！");
        emailTemplate = emailTemplate.replace("$(str2)", "请核对你的用户名：");
        emailTemplate = emailTemplate.replace("$(str3)", "的验证码：");
        emailTemplate = emailTemplate.replace("$(str4)", "(请输入该验证码完成");
        emailTemplate = emailTemplate.replace("$(str5)", "，验证码15分钟内有效！)");
        emailTemplate = emailTemplate.replace("$(str6)", "如果你未申请");
        emailTemplate = emailTemplate.replace("$(str7)", "服务，请忽略该邮件。");
        emailTemplate = emailTemplate.replace("$(str8)", "如果仍有问题，请拨打我们的会员服务专线: ");

        return emailTemplate;
    }

    public String getFile(String fileName) {
        URL url = this.getClass().getClassLoader().getResource("templates/" + fileName + ".html");

        StringBuffer sb = new StringBuffer();
        BufferedInputStream bis = null;
        try {
            File f = new File(url.toURI());
            FileInputStream fis = new FileInputStream(f);
            bis = new BufferedInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
