package top.zk123.mye.Bean;

import javax.persistence.*;

/**
 * 热度表
 */
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 话题 Id
    private long topic_id;
    // 话题名
    private String topic_name;
    // 话题热度(被引用量)
    private long heat;
    // 话题所属专区 ( 0 - 默认专区, 1 - 阿基米德专区)
    private String region;

    public long getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(long topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public long getHeat() {
        return heat;
    }

    public void setHeat(long heat) {
        this.heat = heat;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
