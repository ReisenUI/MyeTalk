package top.zk123.mye.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Rating;

import java.util.List;

public interface RatingMapper extends JpaRepository<Rating, String> {

    /**
     * 查询是否存在对应的话题
     *
     * @param topicName 话题
     * @return {@link Rating}
     */
    @Query(value = "select * from rating where topic_name = :topicName", nativeQuery = true)
    Rating findByTopic_name(String topicName);

    /**
     * 查询属于 region 的话题, 热度不为0
     *
     * @param region 专区
     * @return Page
     */
    @Query(value = "select * from rating where region = :region and heat!=0", nativeQuery = true)
    Page<Rating> findByRegion(String region, Pageable pageable);


    /**
     * 获取最热门的话题
     *
     * @param region 所属分区
     * @return List
     */
    @Query(value = "select topic_name from rating where region = :region order by heat limit 10", nativeQuery = true)
    List<String> getHottestTopic(String region);
}
