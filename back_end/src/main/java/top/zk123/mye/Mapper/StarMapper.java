package top.zk123.mye.Mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Star;

public interface StarMapper extends JpaRepository<Star, String> {
    /**
     * 查询点赞记录
     */
    @Query(value = "select * from star where complex_id = :complexId and user_id = :userId", nativeQuery = true)
    Star findByUserIdAndComplexId(String complexId, long userId);

}

