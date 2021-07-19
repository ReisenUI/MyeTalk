package top.zk123.mye.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Article;

public interface ArticleMapper extends JpaRepository<Article, String> {
    /**
     * 依照 user_id 和 tag 分页查询对应文章
     *
     * @param userId   用户Id
     * @param tag      文章所属标题
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where user_id = :userId and tag like %:tag%", nativeQuery = true)
    Page<Article> findByUser_idAndTag(long userId, String tag, Pageable pageable);

    /**
     * 依照 tag 分页查询对应文章
     *
     * @param tag      文章所属标题
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where tag like %:tag% and tag!='painting'", nativeQuery = true)
    Page<Article> findAllByTagWithoutPainting(String tag, Pageable pageable);

    @Query(value = "select * from article where tag like %:tag%", nativeQuery = true)
    Page<Article> findAllByTag(String tag, Pageable pageable);

    /**
     * 查找另一个我 发布的帖子, 过滤用户自身的帖子
     *
     * @param tag      标签
     * @param userId   用户 Id
     * @param pageable 是否分页
     * @return Pageable
     */
    @Query(value = "select * from article where tag like %:tag% and user_id != :userId", nativeQuery = true)
    Page<Article> findAllByTagNotMe(String tag, long userId, Pageable pageable);


    @Query(value = "select * from article where article_id = :id", nativeQuery = true)
    Article findByArticle_id(String id);

    /**
     * 分页查询普通专区最热门的帖子
     * 依照 点赞量排序
     *
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where expire_time=0 and tag!='painting'", nativeQuery = true)
    Page<Article> findNormalByStar(Pageable pageable);

    /**
     * 分页查询阿基米德专区热门帖子
     *
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where expire_time!=0 and tag!='painting'", nativeQuery = true)
    Page<Article> findAJIMIDEByStar(Pageable pageable);

    /**
     * 查询最新的帖子, 普通专区
     *
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where expire_time=0 and tag !='painting'", nativeQuery = true)
    Page<Article> findNormalLatest(Pageable pageable);

    /**
     * 查询最新的帖子, 阿基米德专区
     *
     * @param pageable 是否分页
     * @return Page
     */
    @Query(value = "select * from article where expire_time!=0 and tag !='painting'", nativeQuery = true)
    Page<Article> findAJIMIDELatest(Pageable pageable);
}
