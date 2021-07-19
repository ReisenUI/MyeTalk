package top.zk123.mye.Bean.ReturnObj;

import top.zk123.mye.Bean.Rating;

import java.util.List;

public class RatingObj {
    private long totalSize;
    private int totalPage;
    private List<Rating> ratingList;

    public RatingObj() {
    }

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

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}
