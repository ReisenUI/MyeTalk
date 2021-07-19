package top.zk123.mye.Service.Imp;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import top.zk123.mye.Bean.*;
import top.zk123.mye.Bean.ReturnObj.Comment.*;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Mapper.ArticleMapper;
import top.zk123.mye.Mapper.CommentMapper;
import top.zk123.mye.Mapper.NoticeMapper;
import top.zk123.mye.Mapper.UserMapper;
import top.zk123.mye.Service.ArticleService;
import top.zk123.mye.Service.CommentService;
import top.zk123.mye.Util.GlobalUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Resource
    ArticleMapper articleMapper;
    @Resource
    CommentMapper commentMapper;
    @Resource
    NoticeMapper noticeMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ArticleService articleService;

    @Override
    public ReturnInfo leaveComment(String articleId, long commentId, String content) throws ArticleNotFoundException, NoLoginException {
        Article article = articleMapper.findByArticle_id(articleId);
        Comment comment = new Comment();
        Notice notice;
        if (article == null) throw new ArticleNotFoundException();
        // 评论帖子, p2_comment_id 设为 0
        if (commentId == 0) {
            comment.setFloor(getMaxFloor(articleId) + 1);
            comment.setP2_comment_id(0);
            notice = new Notice(article.getUser_id(), GlobalUtil.getIdByToken(), 1,
                    getSubString(content, SUBSTRINGSIZE), getSubString(article.getContent(), SUBSTRINGSIZE),
                    articleId, 0, 1, GlobalUtil.getCurrentTime());
        }
        // 评论水友的评论
        else {
            long floor = commentMapper.findByComment_id(commentId).getFloor();
            comment.setFloor(floor);
            comment.setP2_comment_id(commentId);
            notice = new Notice(article.getUser_id(), GlobalUtil.getIdByToken(), 1,
                    getSubString(content, SUBSTRINGSIZE), getSubString(article.getContent(), SUBSTRINGSIZE),
                    articleId, floor, 1, GlobalUtil.getCurrentTime());
        }
        comment.setP1_id(GlobalUtil.getIdByToken());
        comment.setArticle_id(articleId);
        comment.setContent(content);
        comment.setTime(GlobalUtil.getCurrentTime());
        // 评论后发送消息
        noticeMapper.save(notice);
        // 帖子评论数增加
        long commentNum = article.getComment();
        commentNum++;
        article.setComment(commentNum);
        // 保存至DB
        articleMapper.save(article);
        commentMapper.save(comment);
        return new ReturnInfo(200, "评论成功", comment);
    }

    @Override
    public long getMaxFloor(String articleId) {
        List<Comment> commentList = commentMapper.findByArticle_id(articleId);
        long floor;
        if (commentList == null) floor = 0;
        else {
            floor = commentList.size();
        }
        return floor;
    }

    @Override
    public ReturnInfo deleteComment(long commentId) throws IllegalParamException, DeleteCommentFailedException, NoLoginException {
        Comment comment = commentMapper.findByComment_id(commentId);
        if (comment == null) {
            throw new IllegalParamException();
        }
        long p1_id = comment.getP1_id();
        // 属于当前用户的评论
        if (p1_id != GlobalUtil.getIdByToken()) {
            throw new DeleteCommentFailedException();
        }
        // 删除所有与该评论相关的评论
        delete(comment);
        return new ReturnInfo(200, "评论删除成功", null);
    }


    @Override
    public void delete(Comment comment) {
        long comment_id = comment.getComment_id();
        Article article = articleMapper.findByArticle_id(comment.getArticle_id());
        long commentNum = article.getComment();
        commentMapper.delete(comment);
        commentNum--;
        article.setComment(commentNum);
        articleMapper.save(article);
        List<Comment> commentList = commentMapper.findByP2_comment_id(comment_id);
        if (commentList != null) {
            for (Comment c : commentList) {
                delete(c);
            }
        }
    }

    @Override
    public ReturnInfo isMyComment(long commentId) throws NoLoginException {
        long localUserId = GlobalUtil.getIdByToken();
        long userId = commentMapper.findByComment_id(commentId).getP1_id();
        return new ReturnInfo(200, "判断完成", localUserId == userId);
    }

    @Override
    public String getSubString(String s, int len) {
        return s.substring(0, Math.min(s.length(), len));
    }

    @Override
    public ReturnInfo getComments(int limit, int offset, String articleId) {
        CommentObj commentObj = new CommentObj();
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "time"));
        Page<Comment> pageList = commentMapper.findByArticle_idAndP2_comment_id(articleId, pageable);
        // 数据总条数
        commentObj.setTotalSize(pageList.getTotalElements());
        // 总页数
        commentObj.setTotalPage(pageList.getTotalPages());
        // 分页数据
        List<Comment> commentList = pageList.getContent();
        List<CommentBar> commentBarList = new ArrayList<>();
        long numOfComment = 0;
        // 每楼，获取展示两条楼中楼评论
        for (Comment c : commentList) {
            CommentBar commentBar = new CommentBar();
            MainCommentBar main = setMainCommentBar(c);
            List<SubCommentBar> subList = new ArrayList<>();
            List<Comment> floorInFloor = commentMapper.find2ByArticle_idAndFloor(articleId, c.getFloor());
            if (floorInFloor != null) {
                for (Comment c1 : floorInFloor) {
                    SubCommentBar sub = setSubCommentBar(c1);
                    subList.add(sub);
                }
                numOfComment = commentMapper.findAllByArticle_idAndFloor(articleId, c.getFloor()).size();
            }
            commentBar.setNumOfSubComment(numOfComment);
            commentBar.setFloor(c.getFloor());
            commentBar.setMainCommentBar(main);
            commentBar.setSubCommentBarList(subList);
            commentBarList.add(commentBar);
        }
        commentObj.setComments(commentBarList);
        return new ReturnInfo(200, "评论查询成功", JSON.toJSON(commentObj));
    }

    @Override
    public MainCommentBar setMainCommentBar(Comment comment) {
        MainCommentBar main = new MainCommentBar();
        boolean started = false;
        try {
            started = (boolean) articleService.judgeStar(comment.getArticle_id()).getData();
        } catch (NoLoginException ignored) {
        }
        main.setStarted(started);
        main.setCommentId(comment.getComment_id());
        main.setStar(comment.getStar());
        main.setContent(comment.getContent());
        main.setTime(comment.getTime());
        main.setUserId(comment.getP1_id());
        User user = userMapper.findByUserId(comment.getP1_id());
        main.setNickname(user.getNickname());
        main.setAvatar(user.getAvatar());
        main.setArticleId(comment.getArticle_id());
        return main;
    }

    @Override
    public SubCommentBar setSubCommentBar(Comment comment) {
        long p2_comment_id = comment.getP2_comment_id();
        long p2_id = commentMapper.findByComment_id(p2_comment_id).getP1_id();
        User p2 = userMapper.findByUserId(p2_id);
        SubCommentBar sub = new SubCommentBar();
        User user = userMapper.findByUserId(comment.getP1_id());
        sub.setNickname(user.getNickname());
        sub.setContent(comment.getContent());
        sub.setTime(comment.getTime());
        sub.setUserId(comment.getP1_id());
        sub.setAvatar(userMapper.findByUserId(comment.getP1_id()).getAvatar());
        sub.setP2_nickname(p2.getNickname());
        sub.setCommentId(comment.getComment_id());
        sub.setArticleId(comment.getArticle_id());
        return sub;
    }

    @Override
    public ReturnInfo getMoreComments(long commentId, int limit, int offset) throws CommentNotFoundException {
        Pageable pageable = PageRequest.of(offset - 1, limit, Sort.by(Sort.Direction.DESC, "time"));
        Comment comment = commentMapper.findByComment_id(commentId);
        if (comment == null) {
            throw new CommentNotFoundException();
        }
        Page<Comment> commentList = commentMapper.findByArticle_idAndFloor(comment.getArticle_id(), comment.getFloor(), pageable);
        SubCommentObj res = new SubCommentObj();
        res.setTotalSize(commentList.getTotalElements());
        res.setTotalPage(commentList.getTotalPages());
        List<SubCommentBar> subCommentList = new ArrayList<>();
        // comment -> subComment
        for (Comment cmt : commentList) {
            SubCommentBar sub = setSubCommentBar(cmt);
            subCommentList.add(sub);
        }
        res.setSubCommentList(subCommentList);
        return new ReturnInfo(200, "楼中楼评论查询成功", JSON.toJSON(res));
    }
}
