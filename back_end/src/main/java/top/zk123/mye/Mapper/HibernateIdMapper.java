package top.zk123.mye.Mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.HibernateId;

public interface HibernateIdMapper extends JpaRepository<HibernateId, String> {
    /**
     * 查询当前 hibernateId
     */
    @Query(value = "select * from hibernate_id where name = :name", nativeQuery = true)
    HibernateId findHibernateIdByName(String name);
}
