package top.zk123.mye.Bean.ReturnObj;

import java.util.List;

public class ResArticleObj {
    private long totalSize;
    private long totalPage;
    private List<ArticleObj> articleList;

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public List<ArticleObj> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<ArticleObj> articleList) {
        this.articleList = articleList;
    }
}
