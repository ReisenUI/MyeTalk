package top.zk123.mye.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Service.ArticleService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ArticleController {
    @Resource
    ArticleService articleService;

    /**
     * 发布帖子
     *
     * @param imgPathList 图片网址
     * @param title       帖子标题
     * @param content     帖子内容
     * @param tag         帖子 tag
     * @param expireTime  过期时间( 0 - 永不过期,普通专区; 1 - 定时过期,阿基米德专区)
     * @return ReturnInfo
     * @throws FileUploadException    .
     * @throws WrongFileTypeException .
     * @throws FileEmptyException     .
     */
    @PostMapping(value = "/c/post")
    public ReturnInfo postArticle(@RequestParam(value = "imgPathList") List<String> imgPathList,
                                  @RequestParam(value = "title") String title,
                                  @RequestParam(value = "content") String content,
                                  @RequestParam(value = "tag") List<String> tag,
                                  @RequestParam(value = "expireTime") String expireTime) throws
            FileUploadException, WrongFileTypeException, FileEmptyException, NoLoginException {

        return articleService.postArticle(imgPathList, title, content, tag, expireTime);
    }

    @PostMapping(value = "/c/upload")
    public ReturnInfo uploadFile(@RequestParam(value = "file") MultipartFile file) throws FileEmptyException, WrongFileTypeException, FileUploadException {
        return articleService.uploadFile(file);
    }

    /**
     * 分页查询 热门话题
     *
     * @param region 所属区域 ( 0 - 默认专区, 1 - 阿基米德专区)
     * @param limit  分页大小
     * @param offset 当前页数
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/topic")
    public ReturnInfo getTopic(@RequestParam(value = "region") String region,
                               @RequestParam(value = "limit") int limit,
                               @RequestParam(value = "offset") int offset) {

        return articleService.getTopic(region, limit, offset);
    }

    /**
     * 分页获取帖子粗略信息
     *
     * @param userId 用户id, 0 - 查看当前用户的帖子; 1 - 查看属于 tag 的帖子; 其他用户的Id, 查看该用户的帖子
     * @param tag    帖子标签
     * @param limit  每页大小
     * @param offset 当前所在页
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/article")
    public ReturnInfo getArticles(@RequestParam(value = "userId") String userId,
                                  @RequestParam(value = "tag") String tag,
                                  @RequestParam(value = "limit") int limit,
                                  @RequestParam(value = "offset") int offset) throws NoLoginException, UserNotFoundException {
        return articleService.getArticles(userId, tag, limit, offset);
    }


    /**
     * 判断是否对帖子或评论点赞
     *
     * @param id 帖子Id 或 评论Id
     * @return ReturnInfo
     * @throws NoLoginException 未登录
     */
    @GetMapping(value = "/c/judgeStar")
    public ReturnInfo judgeStar(@RequestParam(value = "id") String id) throws NoLoginException {
        return articleService.judgeStar(id);
    }

    /**
     * 分页获取用户自身帖子
     *
     * @param tag    tag
     * @param limit  分页大小
     * @param offset 当前页
     * @return ReturnInfo
     * @throws NoLoginException      未登录
     * @throws UserNotFoundException 用户不存在
     */
    @GetMapping(value = "/c/myArticle")
    public ReturnInfo getMyArticles(@RequestParam(value = "tag") String tag,
                                    @RequestParam(value = "limit") int limit,
                                    @RequestParam(value = "offset") int offset) throws NoLoginException, UserNotFoundException {
        return articleService.getMyArticles(tag, limit, offset);
    }

    /**
     * 查看社区用户个人界面
     *
     * @param userId userId
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/homepage")
    public ReturnInfo getHomePage(@RequestParam(value = "userId") String userId) throws UserNotFoundException {
        return articleService.getHomePage(userId);
    }

    /**
     * HomePage 页面下,获取该用户发布的帖子
     *
     * @param userId userId
     * @param limit  分页大小
     * @param offset 当前页
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/homepage/article")
    public ReturnInfo getHomePageArticle(@RequestParam(value = "userId") String userId,
                                         @RequestParam(value = "limit") int limit,
                                         @RequestParam(value = "offset") int offset)
            throws UserNotFoundException, NoLoginException {
        return articleService.getHomePageArticle(userId, limit, offset);
    }

    /**
     * 获取帖子详细信息
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     * @throws ArticleNotFoundException 帖子不存在
     * @throws UserNotFoundException    用户不存在
     */
    @GetMapping(value = "/c/article/detail")
    public ReturnInfo getArticleDetails(@RequestParam(value = "articleId") String articleId) throws
            ArticleNotFoundException, UserNotFoundException {
        return articleService.getArticleDetails(articleId);
    }

    /**
     * 判断帖子是否为当前用户所发
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     * @throws NoLoginException         未登录
     * @throws ArticleNotFoundException 帖子不存在
     */
    @GetMapping(value = "/c/article/judge")
    public ReturnInfo isMyArticle(@RequestParam(value = "articleId") String articleId) throws NoLoginException, ArticleNotFoundException {
        return articleService.isMyArticle(articleId);
    }


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
    @PostMapping(value = "/c/star")
    public ReturnInfo star(@RequestParam(value = "id") String id,
                           @RequestParam(value = "flag") int flag) throws IllegalParamException,
            ArticleNotFoundException, NoLoginException, NoticeNotFoundException, CommentNotFoundException {
        return articleService.star(id, flag);
    }

    /**
     * 删除帖子
     *
     * @param articleId 帖子Id
     * @return ReturnInfo
     */
    @PostMapping(value = "/c/article/delete")
    public ReturnInfo deleteArticle(@RequestParam(value = "articleId") String articleId)
            throws ArticleNotFoundException {
        return articleService.deleteArticle(articleId);
    }

    /**
     * 获取另一个我的帖子
     *
     * @param offset 当前页数
     * @return ReturnInfo
     * @throws NoLoginException   未登录
     * @throws AnotherMeException 缺少数据,无法查询另一个我
     */
    @GetMapping(value = "/c/article/anotherMe")
    public ReturnInfo getAnotherMe(@RequestParam(value = "offset") int offset)
            throws NoLoginException, AnotherMeException, UserNotFoundException {
        return articleService.getAnotherMe(offset);
    }

    /**
     * 获取最热门的帖子
     *
     * @param limit  每页大小
     * @param offset 当前页
     * @param region 分区
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/article/hottest")
    public ReturnInfo getHottestArticles(@RequestParam(value = "offset") int offset,
                                         @RequestParam(value = "limit") int limit,
                                         @RequestParam(value = "region") String region)
            throws UserNotFoundException, NoLoginException {
        return articleService.getHottestArticles(limit, offset, region);
    }


    /**
     * 获取最近更新的帖子
     *
     * @param limit  分页大小
     * @param offset 当前页
     * @param region 所属分区
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/article/latest")
    public ReturnInfo getLatestArticles(@RequestParam(value = "limit") int limit,
                                        @RequestParam(value = "offset") int offset,
                                        @RequestParam(value = "region") String region)
            throws UserNotFoundException, NoLoginException {
        return articleService.getLatestArticles(limit, offset, region);
    }
}
