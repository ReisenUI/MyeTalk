package top.zk123.mye.Bean.ReturnObj;

import top.zk123.mye.Bean.Diary;

import java.util.List;

public class DiaryObj {
    private long totalSize;
    private int totalPage;
    private List<Diary> diaryList;

    public DiaryObj(){}

    public long getTotalSize() { return totalSize; }

    public void setTotalSize(long totalSize) { this.totalSize = totalSize; }

    public int getTotalPage() { return totalPage; }

    public void setTotalPage(int totalPage) { this.totalPage = totalPage; }

    public List<Diary> getDiaryList() { return diaryList; }

    public void setDiaryList(List<Diary> diaryList) { this.diaryList = diaryList; }
}
