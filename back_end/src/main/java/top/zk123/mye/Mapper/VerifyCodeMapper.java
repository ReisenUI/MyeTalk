package top.zk123.mye.Mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.VerifyCode;

public interface VerifyCodeMapper extends JpaRepository<VerifyCode, String> {
    // 查询验证码
    @Query(value = "select * from verify_code where email = :email and code = :code order by id LIMIT 1", nativeQuery = true)
    VerifyCode findByEmail(String email, String code);
}
