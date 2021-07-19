package top.zk123.mye.Service;

import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.Article;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.ReturnObj.ArticleObj;
import top.zk123.mye.Exception.*;

import java.util.List;

public interface ArticleService {
    /**
     * 发帖子
     *
     * @param imgPathList 图片
     * @param title       标题
     * @param content     内容
     * @param tags        tags
     * @param expireTime  过期时间 0 -(普通专区帖子,永不过期) 1 - (阿基米德专区帖子,定时过期)
     * @return ReturnInfo
     */
    ReturnInfo postArticle(List<String> imgPathList, String title, String content, List<String> tags, String expireTime)
            throws FileUploadException, WrongFileTypeException, FileEmptyException, NoLoginException;

    /**
     * 上传单个文件并返回网址
     *
     * @param file
     * @return
     */
    ReturnInfo uploadFile(MultipartFile file) throws FileEmptyException, WrongFileTypeException, FileUploadException;

    /**
     * 分页获取某个专区的热门话题
     *
     * @param region   专区 0-普通 1-阿基米德
     * @param pageSize 分页大小
     * @param pageNum  当前页数
     * @return ReturnInfo
     */
    ReturnInfo getTopic(String region, int pageSize, int pageNum);

    /**
     * 分页获取帖子
     *
     * @param userId   .
     * @param tag      .
     * @param pageSize .
     * @param pageNum  .
     * @return ReturnInfo
     */
    ReturnInfo getArticles(String userId, String tag, int pageSize, int pageNum) throws NoLoginException, UserNotFoundException;

    /**
     * 获取用户自身的帖子 (need token)
     *
     * @param tag      .
     * @param pageSize .
     * @param pageNum  .
     * @return ReturnInfo
     */
    ReturnInfo getMyArticles(String tag, int pageSize, int pageNum) throws NoLoginException, UserNotFoundException;

    /**
     * 查看社区个人主界面
     *
     * @param userId uerId
     * @return ReturnInfo
     */
    ReturnInfo getHomePage(String userId) throws UserNotFoundException;

    /**
     * 获取个人发表的文章
     *
     * @param userId userId
     * @return ReturnInfo
     */
    ReturnInfo getHomePageArticle(String userId, int pageSize, int pageNum) throws UserNotFoundException, NoLoginException;

    /**
     * 获取帖子详细信息
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     * @throws ArticleNotFoundException 帖子不存在
     * @throws UserNotFoundException    用户不存在
     */
    ReturnInfo getArticleDetails(String articleId) throws ArticleNotFoundException, UserNotFoundException;

    /**
     * 点赞操作
     *
     * @param id   帖子Id
     * @param flag 标志 0 - 给帖子点赞; 1 - 给评论点赞
     * @return ReturnInfo
     * @throws IllegalParamException    非法参数
     * @throws ArticleNotFoundException 帖子不存在
     * @throws NoLoginException         未登录
     * @throws NoticeNotFoundException  通知信息不存在
     */
    ReturnInfo star(String id, int flag) throws ArticleNotFoundException, IllegalParamException, NoLoginException, NoticeNotFoundException, CommentNotFoundException;

    /**
     * 删除帖子
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     */
    ReturnInfo deleteArticle(String articleId) throws ArticleNotFoundException;

    /*
    *
    *
    *
    *
    *
    Utils
    *
    *
    *
    *
    *
    *
    */

    /**
     * 更新话题热度
     *
     * @param tag    话题
     * @param region 帖子所属专区(0-普通专区, 1-阿基米德专区)
     */
    void updateRating(String tag, String region);

    /**
     * 分页查询热门话题
     *
     * @param pageSize 每页数据量
     * @param pageNum  当前页
     * @return Rating列表
     */
    ReturnInfo getRatingPageByRegion(String region, int pageSize, int pageNum);


    /**
     * 通过 UserId 和 tag 分页查询 Article.
     *
     * @param userId   0 - 用户本身; 1 - 全体用户; other - 对应用户Id
     * @param tag      tag
     * @param pageSize 分页大小
     * @param pageNum  当前所在页
     * @return ReturnInfo
     */
    ReturnInfo getArticlePageByUserId(long userId, String tag, int pageSize, int pageNum) throws UserNotFoundException, NoLoginException;


    /**
     * 获取另一个我的帖子
     *
     * @param offset 当前页数
     * @return ReturnInfo
     * @throws NoLoginException   未登录
     * @throws AnotherMeException 缺少数据,无法查询另一个我
     */
    ReturnInfo getAnotherMe(int offset) throws NoLoginException, AnotherMeException, UserNotFoundException;

    /**
     * 获取最热门的帖子
     *
     * @param limit  每页大小
     * @param offset 当前页
     * @param region 分区
     * @return ReturnInfo
     */
    ReturnInfo getHottestArticles(int limit, int offset, String region) throws UserNotFoundException, NoLoginException;


    /**
     * 获取最近更新的帖子
     *
     * @param limit  分页大小
     * @param offset 当前页
     * @param region 所属分区
     * @return ReturnInfo
     */
    ReturnInfo getLatestArticles(int limit, int offset, String region) throws UserNotFoundException, NoLoginException;



    /*
     *
     *
     *
     *
     Utils
     *
     *
     *
     *
     *
     *
     */

    /**
     * 将 帖子列表 转化为 ArticleObj 列表
     *
     * @param articleList 帖子列表
     * @return ArticleObj
     */
    List<ArticleObj> returnArticleObj(List<Article> articleList) throws UserNotFoundException, NoLoginException;

    /**
     * 判断帖子是否为当前用户所发
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     * @throws NoLoginException         未登录
     * @throws ArticleNotFoundException 帖子不存在
     */
    ReturnInfo isMyArticle(String articleId) throws NoLoginException, ArticleNotFoundException;

    /**
     * 判断是否对帖子或评论点赞
     *
     * @param id 帖子Id 或 评论Id
     * @return ReturnInfo
     * @throws NoLoginException 未登录
     */
    ReturnInfo judgeStar(String id) throws NoLoginException;
}
