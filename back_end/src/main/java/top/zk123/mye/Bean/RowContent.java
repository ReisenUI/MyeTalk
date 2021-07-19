package top.zk123.mye.Bean;

/**
 * 日记内容存储形式
 */
public class RowContent {
    private long row;
    private long x;
    private long pattern;   //内容气泡样式
    private String content;

    public RowContent(long row, long x, long pattern, String content) {
        this.row = row;
        this.x = x;
        this.pattern = pattern;
        this.content = content;
    }

    public long getRow() {
        return row;
    }

    public void setRow(long row) {
        this.row = row;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getPattern() {
        return pattern;
    }

    public void setPattern(long pattern) {
        this.pattern = pattern;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
