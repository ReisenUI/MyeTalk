package top.zk123.mye.Bean;

import javax.persistence.*;

/**
 * 日记表
 */
@Entity
@Table(name = "diary")
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 日记 主键
    private long diary_id;
    // 用户 id
    private long user_id;
    // 活动
    private String activity;
    // 心情
    private String emotions;
    // 记录的日期
    private String time;
    // 日记标题
    private String title;
    // 当前天气
    private String weather;
    // 日记背景图片
    private String background;
    // 是否被收藏
    private boolean favorite;
    // 日记内容
    private String content;

    public long getDiary_id() {
        return diary_id;
    }

    public void setDiary_id(long diary_id) {
        this.diary_id = diary_id;
    }

    public long getUer_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favourite) {
        this.favorite = favourite;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getEmotions() {
        return emotions;
    }

    public void setEmotions(String emotions) {
        this.emotions = emotions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) { this.background = background; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }
}