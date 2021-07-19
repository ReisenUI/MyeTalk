package top.zk123.mye.Bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 维护当前用户id
 */
@Entity
@Table(name = "hibernate_id")
public class HibernateId {
    @Id
    private String name;
    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
