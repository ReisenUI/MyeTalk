package top.zk123.mye.Bean;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 */
@IdClass(UserPK.class)
@Entity
@Table(name = "user")
public class User implements Serializable {
    // 存在两个主键,需要 实现 Serializable 的接口,并且定义serialVersionUID, 要求该实体类必须可序列化
    private static final long serialVersionUID = 1L;
    @Id
    // 用户标识
    private long id;
    @Transient
    private final String role = "user";
    @Transient
    private final String permission = "user";
    @Id
    private String email;
    private String password;
    private String nickname;
    private String avatar;
    // 当前记录日记数量
    private long num_of_dairy;
    // 当前记录天数
    private long day_of_record;
    // 当前记录字数
    private long words_of_record;
    // 自我介绍
    private String introduction;
    // 性别
    private String gender;
    // 生日 xxxx-xx-xx
    private String birth;
    // 心情指数
    private int emotion_temper;
    // 用户画像
    private String tag;
    // 用户最近状态
    private String recent_tag;
    // 是否允许用户查询最近状态 ( 0-不允许 1-允许)
    private int tag_trigger;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public String getPermission() {
        return permission;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getNum_of_dairy() {
        return num_of_dairy;
    }

    public void setNum_of_dairy(long num_of_dairy) {
        this.num_of_dairy = num_of_dairy;
    }

    public long getDay_of_record() {
        return day_of_record;
    }

    public void setDay_of_record(long day_of_record) {
        this.day_of_record = day_of_record;
    }

    public long getWords_of_record() {
        return words_of_record;
    }

    public void setWords_of_record(long words_of_record) {
        this.words_of_record = words_of_record;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public int getEmotion_temper() {
        return emotion_temper;
    }

    public void setEmotion_temper(int emotion_temper) {
        this.emotion_temper = emotion_temper;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getRecent_tag() {
        return recent_tag;
    }

    public void setRecent_tag(String recent_tag) {
        this.recent_tag = recent_tag;
    }

    public int getTag_trigger() {
        return tag_trigger;
    }

    public void setTag_trigger(int tag_trigger) {
        this.tag_trigger = tag_trigger;
    }
}
