package top.zk123.mye.Bean.ReturnObj.Comment;

/**
 * 楼主评论
 */
public class MainCommentBar extends CommentParent {
    // 头像
    private String avatar;
    // 点赞信息
    private long star;

    // 若用户已登录，则判断是否对该评论点赞
    boolean started;

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getStar() {
        return star;
    }

    public void setStar(long star) {
        this.star = star;
    }
}
