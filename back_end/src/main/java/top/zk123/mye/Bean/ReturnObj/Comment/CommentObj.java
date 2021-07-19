package top.zk123.mye.Bean.ReturnObj.Comment;

import java.util.List;

public class CommentObj {
    // 数据总条数
    private long totalSize;
    // 数据总页数
    private int totalPage;
    // 评论
    private List<CommentBar> comments;

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<CommentBar> getComments() {
        return comments;
    }

    public void setComments(List<CommentBar> comments) {
        this.comments = comments;
    }
}
