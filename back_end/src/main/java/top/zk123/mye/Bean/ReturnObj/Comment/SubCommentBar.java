package top.zk123.mye.Bean.ReturnObj.Comment;

public class SubCommentBar extends CommentParent {
    // 回复对象的 nickname
    private String p2_nickname;
    private String avatar;

    public String getP2_nickname() {
        return p2_nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setP2_nickname(String p2_nickname) {
        this.p2_nickname = p2_nickname;
    }
}
