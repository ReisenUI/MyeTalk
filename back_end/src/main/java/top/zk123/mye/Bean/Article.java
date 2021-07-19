package top.zk123.mye.Bean;

import javax.persistence.*;

/**
 * 帖子表
 */
@Entity
@Table(name = "article")
public class Article {
    @Id
    // 帖子 id (用户 Id_发帖时间)
    private String article_id;
    // 图片网址
    private String pic_url;
    // 发帖用户 Id (0 表示匿名用户)
    private long user_id;
    // 帖子标题
    private String title;
    // 帖子内容
    private String content;
    // 帖子点赞数
    private long star;
    // comment数量
    private long comment;
    // 过期时间 (-1表示永不过期)
    private String expire_time;
    // 帖子对应的 tag
    private String tag;
    // 发帖时间
    private String time;

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public long getComment() {
        return comment;
    }

    public void setComment(long comment) {
        this.comment = comment;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
