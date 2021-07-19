package top.zk123.mye.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Service.CommentService;

import javax.annotation.Resource;

@RestController
public class CommentController {
    @Resource
    CommentService commentService;

    /**
     * 发表评论
     *
     * @param articleId 帖子ID
     * @param commentId 评论楼层
     * @param content   评论内容
     * @return ReturnInfo
     * @throws ArticleNotFoundException 帖子不存在
     */
    @PostMapping(value = "/c/leaveCmt")
    public ReturnInfo leaveComment(@RequestParam(value = "articleId") String articleId,
                                   @RequestParam(value = "commentId") long commentId,
                                   @RequestParam(value = "content") String content) throws ArticleNotFoundException, NoLoginException {
        return commentService.leaveComment(articleId, commentId, content);
    }


    /**
     * 删除自己发表的评论
     *
     * @param commentId 评论Id
     * @return ReturnInfo
     */
    @PostMapping(value = "/c/cmt/delete")
    public ReturnInfo deleteComment(@RequestParam(value = "commentId") long commentId)
            throws IllegalParamException, DeleteCommentFailedException, NoLoginException {
        return commentService.deleteComment(commentId);
    }

    /**
     * 判断当前选中的评论是否为自己发表的
     *
     * @param commentId 评论Id
     * @return ReturnInfo
     */
    @PostMapping(value = "/c/cmt/judge")
    public ReturnInfo isMyComment(@RequestParam(value = "commentId") long commentId) throws NoLoginException {
        return commentService.isMyComment(commentId);
    }

    /**
     * 分页获取评论数据
     *
     * @param limit     分页大小
     * @param offset    偏移量
     * @param articleId 帖子Id
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/cmt")
    public ReturnInfo getComments(@RequestParam(value = "limit") int limit,
                                  @RequestParam(value = "offset") int offset,
                                  @RequestParam(value = "articleId") String articleId) {
        return commentService.getComments(limit, offset, articleId);
    }

    /**
     * 查看更多评论
     *
     * @param commentId 评论Id
     * @param limit     分页大小
     * @param offset    当前分页
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/cmt/detail")
    public ReturnInfo showMoreComments(@RequestParam(value = "commentId") long commentId,
                                       @RequestParam(value = "limit") int limit,
                                       @RequestParam(value = "offset") int offset) throws CommentNotFoundException {
        return commentService.getMoreComments(commentId, limit, offset);
    }
}
