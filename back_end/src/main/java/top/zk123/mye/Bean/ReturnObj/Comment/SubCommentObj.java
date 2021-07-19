package top.zk123.mye.Bean.ReturnObj.Comment;

import java.util.LinkedList;
import java.util.List;

/**
 * 楼中楼评论 ReturnInfo专用类
 */
public class SubCommentObj {
    private long totalSize;
    private long totalPage;
    private List<SubCommentBar> subCommentList;

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

    public List<SubCommentBar> getSubCommentList() {
        return subCommentList;
    }

    public void setSubCommentList(List<SubCommentBar> subCommentList) {
        this.subCommentList = subCommentList;
    }
}
