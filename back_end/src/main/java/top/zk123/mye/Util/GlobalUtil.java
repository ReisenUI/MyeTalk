package top.zk123.mye.Util;

import org.apache.shiro.SecurityUtils;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.Article;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.FileEmptyException;
import top.zk123.mye.Exception.FileUploadException;
import top.zk123.mye.Exception.NoLoginException;
import top.zk123.mye.Exception.WrongFileTypeException;
import top.zk123.mye.Shiro.TokenUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GlobalUtil {
    public static final String LOCAL_AVATAR_PATH = "/home/zhangke/workspace/apache-tomcat-8.5.60/webapps/ROOT/src/mye/avatar/";
    public static final String WEB_AVATAR_PATH = "https://www.zk123.top/src/mye/avatar/";
    public static final String LOCAL_ARTICLE_IMG_PATH = "/home/zhangke/workspace/apache-tomcat-8.5.60/webapps/ROOT/src/mye/article/";
    public static final String WEB_ARTICLE_IMG_PATH = "https://www.zk123.top/src/mye/article/";
    public static final String LOCAL_DIARY_IMG_PATH = "/home/zhangke/workspace/apache-tomcat-8.5.60/webapps/ROOT/src/mye/diary/";
    public static final String WEB_DIARY_IMG_PATH = "https://www.zk123.top/src/mye/diary/";

    public static class hasMoreContent {
        private int more;
        private int index;

        public hasMoreContent() {

        }

        public hasMoreContent(int more, int index) {
            this.more = more;
            this.index = index;
        }

        public int getMore() {
            return more;
        }

        public int getIndex() {
            return index;
        }
    }


    /**
     * 解析 Token 获取用户 Id
     *
     * @return Id
     */
    public static long getIdByToken() throws NoLoginException {
        String token = String.valueOf(SecurityUtils.getSubject().getPrincipal());
        User user = new TokenUtils().validationToken(token);
        if (user == null) {
            throw new NoLoginException();
        }
        return user.getId();
    }

    /**
     * 获取文件后缀名
     *
     * @param fileName 文件名
     * @return String
     */
    public static String getExtension(String fileName) {
        String extension = "";
        if (fileName == null || "".equals(fileName)) {
            throw new NullPointerException();
        }
        int i = fileName.lastIndexOf(".");
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    /**
     * 上传单张图片
     *
     * @param file     图片
     * @param filePath 存放文件的路径
     * @return 文件名
     */
    public static String uploadImg(MultipartFile file, String filePath) throws FileEmptyException,
            WrongFileTypeException, FileUploadException {
        if (file == null) {
            throw new FileEmptyException();
        }
        // 获取文件后缀名
        String originFileName = file.getOriginalFilename();
        String extension = getExtension(originFileName);

        // 仅支持 jpg, png, jpeg
        if (!(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg"))) {
            throw new WrongFileTypeException();
        }
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        String path = filePath + newFileName;
        File dest = new File(path);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadException();
        }
        return newFileName;
    }

    /**
     * 上传多张照片
     *
     * @param files    多张图片
     * @param filePath 存储路径
     * @return String List
     */
    public static List<String> uploadImgs(MultipartFile[] files, String filePath) throws FileUploadException,
            WrongFileTypeException, FileEmptyException {
        List<String> imgList = new ArrayList<>();
        for (MultipartFile file : files) {
            imgList.add(uploadImg(file, filePath));
        }
        return imgList;
    }

    /**
     * 获取当前日期  yyyy-MM-dd
     *
     * @return String
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(date);
    }

    /**
     * 获取当前时间 yyyy-MM-dd-hh-mm-ss
     *
     * @return String
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return dt.format(date);
    }

    /**
     * list 转 String
     *
     * @param list list
     * @return String
     */
    public static String List2String(List<String> list) {
        StringBuilder buffer = new StringBuilder();
        for (String s : list) {
            buffer.append(s);
            buffer.append(",");
        }
        buffer.deleteCharAt(buffer.lastIndexOf(","));
        return buffer.toString();
    }

    /**
     * String 转 List
     *
     * @param s String
     * @return List
     */
    public static List<String> parseString(String s) {
        return Arrays.asList(s.split(","));
    }

    /**
     * 给定文章, 返回是否有更多内容
     *
     * @param article 文章内容
     * @return hasMoreContent
     */
    public static hasMoreContent haveMoreContent(Article article) {
        int more = 0;
        int lineWords = 20;
        int index = 0;
        char[] chars = article.getContent().toCharArray();
        for (int tmp = 0, line = 0; index < chars.length; tmp++, index++) {
            if (tmp >= lineWords) {
                line++;
                tmp -= lineWords;
            }
            // 检测 \n 换行符 line+1
            if (chars[index] == '\n') {
                line++;
                tmp = 0;
            }

            if (line == 5) {
                return new hasMoreContent(0, index);
            }
        }
        if (index < article.getContent().length()) {
            more = 1;
        }
        return new hasMoreContent(1, index);
    }

    // 产生动态验证码 6位
    public static String generateVerifyCode() {
        StringBuilder vCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            vCode.append((int) (Math.random() * 10));
        }
        return vCode.toString();
    }
}
