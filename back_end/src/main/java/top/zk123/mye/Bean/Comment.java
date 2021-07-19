package top.zk123.mye.Bean;

import javax.persistence.*;

/**
 * 评论表
 */
@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 无意义主键
    private long comment_id;
    // 帖子 Id
    @Column(nullable = false)
    private String article_id;
    // 楼层
    private long floor;
    // 评论人 Id
    private long p1_id;
    // 被评论人 CommentId
    private long p2_comment_id;
    // 发表评论时间
    private String time;
    // 评论内容
    private String content;

    // 评论点赞数
    private long star;

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public long getP1_id() {
        return p1_id;
    }

    public void setP1_id(long p1_id) {
        this.p1_id = p1_id;
    }

    public long getP2_comment_id() {
        return p2_comment_id;
    }

    public void setP2_comment_id(long p2_comment_id) {
        this.p2_comment_id = p2_comment_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
