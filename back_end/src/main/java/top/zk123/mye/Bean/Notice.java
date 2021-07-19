package top.zk123.mye.Bean;

import javax.persistence.*;

/**
 * 个人收件箱
 */
@Entity
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 无意义的主键
    private long id;
    // 用户 id
    private long user_id;
    // 点赞、评论者的Id
    private long p1_id;
    // 操作类型 0 - 点赞;  1 - 评论, 2 - 系统信息;
    // article+floor 表示点赞和评论的对象; (floor=0表示针对帖子, floor为其他自然数表示针对对应楼层的评论)
    private int type;
    private String p1_content;
    private String p2_content;
    // 帖子 id
    private String articleId;
    // 帖子楼层
    private long floor;
    // 消息是否有效(未读)
    private int valid;
    // 发生时间
    private String time;

    public Notice(long user_id, long p1_id, int type, String p1_content, String p2_content,
                  String articleId, long floor, int valid, String time) {
        this.user_id = user_id;
        this.p1_id = p1_id;
        this.type = type;
        this.p1_content = p1_content;
        this.p2_content = p2_content;
        this.articleId = articleId;
        this.floor = floor;
        this.valid = valid;
        this.time = time;
    }

    public Notice(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public String getP1_content() {
        return p1_content;
    }

    public void setP1_content(String p1_content) {
        this.p1_content = p1_content;
    }

    public String getP2_content() {
        return p2_content;
    }

    public void setP2_content(String p2_content) {
        this.p2_content = p2_content;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getP1_id() {
        return p1_id;
    }

    public void setP1_id(long p1_id) {
        this.p1_id = p1_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
