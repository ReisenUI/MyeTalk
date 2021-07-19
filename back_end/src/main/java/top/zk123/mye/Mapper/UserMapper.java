package top.zk123.mye.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.User;

public interface UserMapper extends JpaRepository<User, String> {

    /**
     * 通过 用户 Email 查询用户
     *
     * @param email 用户 email
     * @return User
     */
    @Query(value = "select * from user where email = :email", nativeQuery = true)
    User findByUserByEmail(String email);

    /**
     * 通过 UserId 查询用户
     *
     * @param id UserId
     * @return User
     */
    @Query(value = "select * from user where id = :id", nativeQuery = true)
    User findByUserId(long id);

    /**
     * 分页
     *
     * @param id       用户id
     * @param pageable 分页
     * @return Page
     */
    @Query(value = "select * from user where id = :id", nativeQuery = true)
    Page<User> findByUserId(long id, Pageable pageable);
}
