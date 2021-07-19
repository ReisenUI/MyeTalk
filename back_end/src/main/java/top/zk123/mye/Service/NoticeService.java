package top.zk123.mye.Service;

import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.ArticleNotFoundException;
import top.zk123.mye.Exception.NoLoginException;

public interface NoticeService {

    /**
     * 查看该用户的消息列表
     *
     * @return ReturnInfo
     */
    ReturnInfo getMessage() throws NoLoginException, ArticleNotFoundException;
}
