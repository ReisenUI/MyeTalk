package top.zk123.mye.Bean.ReturnObj;

import top.zk123.mye.Bean.Article;

public class ArticleObj {
    private Article article;
    // 是否有更多内容 0-没有 1-有
    private int more;
    private String nickname;
    private String avatar;
    // 是否点赞, 未登录默认未点赞
    private boolean started;

    public ArticleObj() {
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
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

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
