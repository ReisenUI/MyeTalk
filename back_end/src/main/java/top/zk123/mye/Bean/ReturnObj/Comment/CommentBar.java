package top.zk123.mye.Bean.ReturnObj.Comment;

import java.util.List;

public class CommentBar {
    // 楼层
    private long floor;
    // 楼主评论
    private MainCommentBar mainCommentBar;
    // 楼中楼评论
    private List<SubCommentBar> subCommentBarList;
    // 楼中楼评论个数
    private long numOfSubComment;

    public long getNumOfSubComment() {
        return numOfSubComment;
    }

    public void setNumOfSubComment(long numOfSubComment) {
        this.numOfSubComment = numOfSubComment;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public MainCommentBar getMainCommentBar() {
        return mainCommentBar;
    }

    public void setMainCommentBar(MainCommentBar mainCommentBar) {
        this.mainCommentBar = mainCommentBar;
    }

    public List<SubCommentBar> getSubCommentBarList() {
        return subCommentBarList;
    }

    public void setSubCommentBarList(List<SubCommentBar> subCommentBarList) {
        this.subCommentBarList = subCommentBarList;
    }
}
