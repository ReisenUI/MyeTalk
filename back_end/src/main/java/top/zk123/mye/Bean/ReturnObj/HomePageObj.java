package top.zk123.mye.Bean.ReturnObj;

import java.util.List;

/**
 * 个人主界面 Bean
 */
public class HomePageObj {
    private String id;
    private String avatar;
    private String nickname;
    private String introduction;
    private List<String> tag;

    public HomePageObj() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
