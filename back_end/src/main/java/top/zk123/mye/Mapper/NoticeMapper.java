package top.zk123.mye.Mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Notice;

import java.util.List;

public interface NoticeMapper extends JpaRepository<Notice, String> {

    /**
     * 通过用户查找对应的消息
     *
     * @param userId 用户Id
     * @return List
     */
    @Query(value = "select * from notice where p1_id != :userId and user_id=:userId", nativeQuery = true)
    List<Notice> findByUser_id(long userId);

    /**
     * 查找点赞记录
     *
     * @param articleId 帖子Id
     * @param floor     楼层
     * @param userId    用户Id
     * @return Notice
     */
    @Query(value = "select * from notice where article_id = :articleId and floor = :floor and p1_id = :userId and type = 0", nativeQuery = true)
    Notice findByArticleIdAndUserIdAndFloor(String articleId, long floor, long userId);


    /**
     * @param articleId
     * @return
     */
    @Query(value = "select * from notice where article_id = :articleId", nativeQuery = true)
    List<Notice> findByArticleId(String articleId);

}
