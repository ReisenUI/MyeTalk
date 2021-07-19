package top.zk123.mye.Service;

import top.zk123.mye.Bean.Comment;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.ReturnObj.Comment.MainCommentBar;
import top.zk123.mye.Bean.ReturnObj.Comment.SubCommentBar;
import top.zk123.mye.Exception.*;

public interface CommentService {
    public static final int SUBSTRINGSIZE = 60;

    /**
     * 评论
     *
     * @param articleId 帖子ID
     * @param commentId 评论Id 0-表示评论帖子, 其他表示回复其他的评论
     * @param content   回应的楼层(0-评论当前帖子, 1,2,...,n-评论对应楼层的评论)
     * @return ReturnInfo
     */
    ReturnInfo leaveComment(String articleId, long commentId, String content) throws ArticleNotFoundException, NoLoginException;


    /**
     * 获取当前帖子最大楼层
     *
     * @param articleId 帖子ID
     * @return long
     */
    long getMaxFloor(String articleId);

    /**
     * 删除评论
     *
     * @param commentId 评论Id
     * @return ReturnInfo
     */
    ReturnInfo deleteComment(long commentId) throws IllegalParamException, DeleteCommentFailedException, NoLoginException;

    /**
     * 递归删除所有的评论
     *
     * @param comment 评论
     */
    void delete(Comment comment);

    /**
     * 判断某条评论是否属于当前用户
     *
     * @param commentId 评论Id
     * @return ReturnInfo
     */
    ReturnInfo isMyComment(long commentId) throws NoLoginException;

    /**
     * 获取指定长度的子串
     *
     * @param s   原字符串
     * @param len 保留长度
     * @return String
     */
    String getSubString(String s, int len);

    /**
     * 获取评论
     *
     * @param limit     页长
     * @param offset    偏移
     * @param articleId 帖子Id
     * @return ReturnInfo
     */
    ReturnInfo getComments(int limit, int offset, String articleId);

    /**
     * 根据 comment 记录， 生成对应的 MainCommentBar 对象
     *
     * @param comment {@link Comment}
     * @return {@link MainCommentBar}
     */
    MainCommentBar setMainCommentBar(Comment comment);

    /**
     * 根据 comment 记录， 生成对应 SubCommentBar 对象
     *
     * @param comment {@link Comment}
     * @return {@link SubCommentBar}
     */
    SubCommentBar setSubCommentBar(Comment comment);

    /**
     * 查看更多评论
     *
     * @param commentId 帖子Id
     * @param limit     分页大小
     * @param offset    当前分页
     * @return ReturnInfo
     */
    ReturnInfo getMoreComments(long commentId, int limit, int offset) throws CommentNotFoundException;

}
