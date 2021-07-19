package top.zk123.mye.Bean;

import java.io.Serializable;
import java.util.Objects;

/**
 * 实现 User 符合主键
 */
public class UserPK implements Serializable {
    // field 就是 复合主键
    private long id;
    private String email;

    // 重写
    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
