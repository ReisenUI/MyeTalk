package top.zk123.mye.Bean.ReturnObj;

public class DiaryReturnInfo {
    long diaryId;
    String imageAddr;

    public DiaryReturnInfo(long diaryId, String imageAddr) {
        this.diaryId = diaryId;
        this.imageAddr = imageAddr;
    }

    public long getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(long diaryId) {
        this.diaryId = diaryId;
    }

    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }
}
