package top.zk123.mye.Service.Imp;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.*;
import top.zk123.mye.Bean.ReturnObj.ArticleObj;
import top.zk123.mye.Bean.ReturnObj.HomePageObj;
import top.zk123.mye.Bean.ReturnObj.RatingObj;
import top.zk123.mye.Bean.ReturnObj.ResArticleObj;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Mapper.*;
import top.zk123.mye.Service.ArticleService;
import top.zk123.mye.Util.GlobalUtil;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static top.zk123.mye.Util.GlobalUtil.*;

@Service
public class ArticleServiceImp implements ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    RatingMapper ratingMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    NoticeMapper noticeMapper;

    @Resource
    StarMapper starMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ReturnInfo postArticle(List<String> imgPathList, String title, String content, List<String> tags, String expireTime)
            throws NoLoginException {
        long userId = getIdByToken();
        String currentTime = GlobalUtil.getCurrentTime();
        String articleId = userId + "_" + currentTime;
        // 对应话题的热度增加
        // region = expireTime  0 - 普通专区, 1 - 阿基米德专区
        for (String s : tags) {
            updateRating(s, expireTime);
        }
        // 存储到 DB
        Article article = new Article();
        article.setArticle_id(articleId);
        article.setUser_id(userId);
        article.setContent(content);
        article.setExpire_time(expireTime);
        if (imgPathList.size() != 0) {
            article.setPic_url(GlobalUtil.List2String(imgPathList));
        }
        article.setTag(GlobalUtil.List2String(tags));
        article.setTitle(title);
        article.setStar(0);
        article.setTime(currentTime);
        articleMapper.save(article);
        return new ReturnInfo(200, "帖子发表成功", null);
    }

    @Override
    public ReturnInfo uploadFile(MultipartFile file) throws FileEmptyException, WrongFileTypeException, FileUploadException {
        String currentDate = GlobalUtil.getCurrentDate();
        String filePath = GlobalUtil.LOCAL_ARTICLE_IMG_PATH + currentDate + "/";
        String webImgPath = GlobalUtil.WEB_ARTICLE_IMG_PATH + currentDate + "/";
        String imgName = GlobalUtil.uploadImgs(new MultipartFile[]{file}, filePath).get(0);
        String path = webImgPath + imgName;
        // 存储图片，获取链接
        return new ReturnInfo(200, "success", path);
    }

    @Override
    public ReturnInfo getTopic(String region, int pageSize, int pageNum) {
        return getRatingPageByRegion(region, pageSize, pageNum);
    }

    @Override
    public ReturnInfo getArticles(String userId, String tag, int pageSize, int pageNum)
            throws UserNotFoundException {
        // 获取所有帖子;
        if (userId.equals("1")) {
            return getArticlePageByUserId(1, tag, pageSize, pageNum);
        }
        // 获取对应userId的帖子
        else {
            return getArticlePageByUserId(Long.parseLong(userId), tag, pageSize, pageNum);
        }

    }

    @Override
    public ReturnInfo getMyArticles(String tag, int pageSize, int pageNum) throws NoLoginException, UserNotFoundException {
        return getArticlePageByUserId(getIdByToken(), tag, pageSize, pageNum);
    }

    @Override
    public ReturnInfo getHomePage(String userId) throws UserNotFoundException {
        User user = userMapper.findByUserId(Long.parseLong(userId));
        if (user == null) throw new UserNotFoundException();
        HomePageObj homePageObj = new HomePageObj();
        homePageObj.setId(userId);
        homePageObj.setAvatar(user.getAvatar());
        homePageObj.setNickname(user.getNickname());
        homePageObj.setIntroduction(user.getIntroduction());
        homePageObj.setTag(parseString(user.getTag()));
        return new ReturnInfo(200, "信息获取成功", JSON.toJSON(homePageObj));
    }

    @Override
    public ReturnInfo getHomePageArticle(String userId, int pageSize, int pageNum) throws UserNotFoundException {
        return getArticlePageByUserId(Long.parseLong(userId), "", pageSize, pageNum);
    }


    @Override
    public ReturnInfo getArticleDetails(String articleId) throws ArticleNotFoundException, UserNotFoundException {
        ArticleObj res = new ArticleObj();
        Article article = articleMapper.findByArticle_id(articleId);
        if (article == null) throw new ArticleNotFoundException();
        User user = userMapper.findByUserId(article.getUser_id());
        if (user == null) throw new UserNotFoundException();
        res.setMore(0);
        res.setNickname(user.getNickname());
        res.setAvatar(user.getAvatar());
        res.setArticle(article);
        return new ReturnInfo(200, "帖子信息查询成功", JSON.toJSON(res));
    }

    @Override
    public ReturnInfo star(String id, int flag) throws ArticleNotFoundException,
            IllegalParamException, NoLoginException, NoticeNotFoundException, CommentNotFoundException {
        long userId = getIdByToken();
        String currentTime = getCurrentTime();
        if (flag == 0) {
            // 点赞/取消点赞 帖子 id = articleId
            Article article = articleMapper.findByArticle_id(id);
            if (article == null) {
                throw new ArticleNotFoundException();
            }
            Notice notice;
            long currentStar = article.getStar();
            Star star = starMapper.findByUserIdAndComplexId(id, userId);
            if (star == null) {
                // 点赞帖子
                star = new Star();
                star.setComplexId(id);
                star.setDate(currentTime);
                star.setUserId(userId);
                currentStar++;
                article.setStar(currentStar);
                notice = new Notice(article.getUser_id(), getIdByToken(), 0, "",
                        "", article.getArticle_id(), 0, 1, currentTime);
                starMapper.save(star);
                articleMapper.save(article);
                noticeMapper.save(notice);
                return new ReturnInfo(200, "点赞成功", null);
            } else {
                // 取消点赞帖子
                notice = noticeMapper.findByArticleIdAndUserIdAndFloor(article.getArticle_id(), 0, userId);
                if (notice == null) {
                    throw new NoticeNotFoundException();
                }
                currentStar--;
                article.setStar(currentStar);
                articleMapper.save(article);
                starMapper.delete(star);
                noticeMapper.delete(notice);
                return new ReturnInfo(200, "取消点赞成功", null);
            }
        } else if (flag == 1) {
            // TODO: 点赞评论不提示
            // 点赞/取消点赞 评论 id = commentId
            Comment comment = commentMapper.findByComment_id(Long.parseLong(id));
            if (comment == null) {
                throw new CommentNotFoundException();
            }
            Article article = articleMapper.findByArticle_id(comment.getArticle_id());
            if (article == null) {
                throw new ArticleNotFoundException();
            }
            long currentStar = comment.getStar();
            Star star = starMapper.findByUserIdAndComplexId(id, userId);
            if (star == null) {
                // 点赞 评论
                star = new Star();
                star.setUserId(userId);
                star.setComplexId(id);
                star.setDate(currentTime);
                currentStar++;
                comment.setStar(currentStar);
                commentMapper.save(comment);
                starMapper.save(star);
                return new ReturnInfo(200, "点赞成功", null);
            } else {
                // 取消点赞 评论
                currentStar--;
                comment.setStar(currentStar);
                starMapper.delete(star);
                commentMapper.save(comment);
                return new ReturnInfo(200, "取消点赞成功", null);
            }
        } else {
            // 非法参数
            throw new IllegalParamException();
        }
    }


    @Override
    public ReturnInfo deleteArticle(String articleId) throws ArticleNotFoundException {
        // TODO: 删除帖子, 帖子中的评论数据暂时保留
        Article article = articleMapper.findByArticle_id(articleId);
        if (article == null) {
            throw new ArticleNotFoundException();
        }
        // 对应话题热度减少
        List<String> tagList = parseString(articleMapper.findByArticle_id(articleId).getTag());
        for (String tag : tagList) {
            Rating rating = ratingMapper.findByTopic_name(tag);
            if (rating != null) {
                rating.setHeat(rating.getHeat() - 1);
                ratingMapper.save(rating);
            }
        }
        // 删除对应消息提示
        List<Notice> noticeList = noticeMapper.findByArticleId(articleId);
        for (Notice notice : noticeList) {
            noticeMapper.delete(notice);
        }
        articleMapper.delete(article);
        return new ReturnInfo(200, "帖子删除成功", null);
    }

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
        */
    @Override
    public void updateRating(String tag, String region) {
        Rating local = new Rating();
        Rating rating = ratingMapper.findByTopic_name(tag);
        if (rating == null) {
            local.setTopic_name(tag);
            local.setHeat(1);
            local.setRegion(region);
        } else {
            local = rating;
            local.setHeat(local.getHeat() + 1);
        }
        ratingMapper.save(local);
    }

    @Override
    public ReturnInfo getRatingPageByRegion(String region, int pageSize, int pageNum) {
        RatingObj ratingObj = new RatingObj();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "heat"));
        Page<Rating> pageList = ratingMapper.findByRegion(region, pageable);
        // 数据总条数
        ratingObj.setTotalSize(pageList.getTotalElements());
        // 总页数
        ratingObj.setTotalPage(pageList.getTotalPages());
        // 分页数据
        ratingObj.setRatingList(pageList.getContent());
        return new ReturnInfo(200, "第" + pageNum + "页数据查询成功", JSON.toJSON(ratingObj));
    }

    @Override
    public ReturnInfo getArticlePageByUserId(long userId, String tag, int pageSize, int pageNum)
            throws UserNotFoundException {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "time"));
        ResArticleObj res = new ResArticleObj();
        Page<Article> pageList;
        // 查询所有帖子
        if (userId == 1) {
            if (tag.equals("")) {
                pageList = articleMapper.findAllByTagWithoutPainting(tag, pageable);
            } else {
                pageList = articleMapper.findAllByTag(tag, pageable);
            }
        } else {
            pageList = articleMapper.findByUser_idAndTag(userId, tag, pageable);
        }
        List<Article> articleList = pageList.getContent();
        res.setTotalSize(pageList.getTotalElements());
        res.setTotalPage(pageList.getTotalPages());
        res.setArticleList(returnArticleObj(articleList));
        return new ReturnInfo(200, "第" + pageNum + "页数据查询成功", JSON.toJSON(res));
    }

    @Override
    public ReturnInfo getAnotherMe(int offset) throws NoLoginException, AnotherMeException, UserNotFoundException {
        // 默认一次返回20个帖子
        User user = userMapper.findByUserId(getIdByToken());
        String recentTags = user.getRecent_tag();
        // 没有记录日记, 缺少 another me 的数据
        if (null == recentTags) {
            throw new AnotherMeException();
        }
        List<Article> articleList = new ArrayList<>();
        List<String> recentTagList = parseString(recentTags);
        ResArticleObj res = new ResArticleObj();
        long totalSize = 0;
        // recent_tag 最多有四个
        int limit = 20 / (recentTagList.size());
        for (String tag : recentTagList) {
            // 分页查询对应的
            Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "time"));
            Page<Article> articlePage = articleMapper.findAllByTagNotMe(tag, user.getId(), pageable);
            totalSize += articlePage.getTotalElements();
            List<Article> tmpArticleList = articlePage.getContent();
            articleList.addAll(tmpArticleList);
        }
        res.setTotalSize(totalSize);
        res.setTotalPage(totalSize % 20 == 0 ? totalSize / 20 : totalSize / 20 + 1);
        res.setArticleList(returnArticleObj(articleList));
        return new ReturnInfo(200, "第" + offset + "页数据查询成功", JSON.toJSON(res));
    }

    @Override
    public ReturnInfo getHottestArticles(int limit, int offset, String region) throws UserNotFoundException {
        ResArticleObj res = new ResArticleObj();
        // 分页查询对应的
        // TODO: 最热话题的排序 1*star + 1.5*comment
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "star"));
        Page<Article> articlePage;
        if (region.equals("0")) {
            articlePage = articleMapper.findNormalByStar(pageable);
        } else {
            articlePage = articleMapper.findAJIMIDEByStar(pageable);
        }
        res.setTotalSize(articlePage.getTotalElements());
        List<Article> articleList = articlePage.getContent();
        res.setTotalPage(articlePage.getTotalPages());
        res.setArticleList(returnArticleObj(articleList));
        return new ReturnInfo(200, "第" + offset + "页数据查询成功", JSON.toJSON(res));
    }


    @Override
    public ReturnInfo getLatestArticles(int limit, int offset, String region) throws UserNotFoundException {
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "time"));
        Page<Article> articlePage;
        if (region.equals("0")) {
            articlePage = articleMapper.findNormalLatest(pageable);
        } else {
            articlePage = articleMapper.findAJIMIDELatest(pageable);
        }
        ResArticleObj res = new ResArticleObj();
        res.setTotalSize(articlePage.getTotalElements());
        res.setTotalPage(articlePage.getTotalPages());
        List<Article> articleList = articlePage.getContent();
        res.setArticleList(returnArticleObj(articleList));
        return new ReturnInfo(200, "第" + offset + "页数据查询成功", JSON.toJSON(res));
    }


    /*
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
    @Override
    public List<ArticleObj> returnArticleObj(List<Article> articleList) throws UserNotFoundException {
        List<ArticleObj> res = new ArrayList<>();
        for (Article article : articleList) {
            ArticleObj articleObj = new ArticleObj();
            User tmpUser = userMapper.findByUserId(article.getUser_id());
            if (tmpUser == null) {
                throw new UserNotFoundException();
            }
            hasMoreContent content = haveMoreContent(article);
            // 截取5行字符串
            article.setContent(article.getContent().substring(0, content.getIndex()));
            articleObj.setNickname(tmpUser.getNickname());
            articleObj.setAvatar(tmpUser.getAvatar());
            articleObj.setMore(content.getMore());
            articleObj.setArticle(article);
            res.add(articleObj);
        }
        return res;
    }

    @Override
    public ReturnInfo isMyArticle(String articleId) throws NoLoginException, ArticleNotFoundException {
        long userId = getIdByToken();
        Article article = articleMapper.findByArticle_id(articleId);
        if (article == null) {
            throw new ArticleNotFoundException();
        }
        return new ReturnInfo(200, "查询成功", userId == article.getUser_id());
    }

    @Override
    public ReturnInfo judgeStar(String id) throws NoLoginException {
        long userId = getIdByToken();
        Star star = starMapper.findByUserIdAndComplexId(id, userId);
        return new ReturnInfo(200, "get star status", !(star == null));
    }
}
