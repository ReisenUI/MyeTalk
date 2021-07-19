package top.zk123.mye.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Comment;

import java.util.List;

public interface CommentMapper extends JpaRepository<Comment, String> {

    /**
     * 根据帖子Id, 定位属于该楼的评论
     *
     * @param articleId 帖子Id
     * @param floor     楼层
     * @return Comment
     */
    @Query(value = "select * from comment where article_id = :articleId and floor =:floor and p2_comment_id=0", nativeQuery = true)
    Comment findByArticle_idAndFloorAndP2_comment_id(String articleId, long floor);

    /**
     * 查找属于某个帖子的评论
     *
     * @param articleId 帖子Id
     * @return List
     */
    @Query(value = "select * from comment where article_id =:articleId", nativeQuery = true)
    List<Comment> findByArticle_id(String articleId);

    /**
     * 通过评论Id查找对应评论
     *
     * @param commentId 评论Id
     * @return Comment
     */
    @Query(value = "select * from comment where comment_id = :commentId", nativeQuery = true)
    Comment findByComment_id(long commentId);

    /**
     * 查找对应 p2_id 的评论
     *
     * @param p2_comment_id .
     * @return List
     */
    @Query(value = "select * from comment where p2_comment_id = :p2_comment_id", nativeQuery = true)
    List<Comment> findByP2_comment_id(long p2_comment_id);

    /**
     * 分页查询某个帖子的评论
     *
     * @param articleId 文章 Id
     * @param pageable  是否分页
     * @return Page
     */
    @Query(value = "select * from comment where article_id = :articleId and p2_comment_id = 0", nativeQuery = true)
    Page<Comment> findByArticle_idAndP2_comment_id(String articleId, Pageable pageable);

    /**
     * 返回2条及以内的 楼中楼数据
     *
     * @param articleId 帖子Id
     * @param floor     楼层
     * @return List
     */
    @Query(value = "select * from comment  where article_id = :articleId and floor = :floor and p2_comment_id != 0 order by time limit 2", nativeQuery = true)
    List<Comment> find2ByArticle_idAndFloor(String articleId, long floor);

    /**
     * 查询对应楼层的所有楼中楼数据
     *
     * @param articleId 帖子Id
     * @param floor     楼层
     * @return List
     */
    @Query(value = "select * from comment where article_id = :articleId and floor = :floor and p2_comment_id != 0", nativeQuery = true)
    List<Comment> findAllByArticle_idAndFloor(String articleId, long floor);

    /**
     * 分页查询楼中楼评论
     *
     * @param articleId 帖子Id
     * @param floor     楼层
     * @param pageable  是否分页
     * @return Page
     */
    @Query(value = "select * from comment where article_id = :articleId and floor = :floor and p2_comment_id != 0", nativeQuery = true)
    Page<Comment> findByArticle_idAndFloor(String articleId, long floor, Pageable pageable);
}
