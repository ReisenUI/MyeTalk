package top.zk123.mye.Bean;

import javax.persistence.*;

@Entity
@Table(name = "star")
public class Star {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    // 无意义主键
    private long userId;    // 点赞用户Id
    private String complexId;   // 帖子Id 或 评论Id
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getComplexId() {
        return complexId;
    }

    public void setComplexId(String complexId) {
        this.complexId = complexId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
