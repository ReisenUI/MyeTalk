package top.zk123.mye.Bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Table
@Entity(name = "verify_code")
public class VerifyCode {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    // 邮箱
    private String email;
    // 验证码发送时间
    private String date;
    // 验证码
    private String code;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
