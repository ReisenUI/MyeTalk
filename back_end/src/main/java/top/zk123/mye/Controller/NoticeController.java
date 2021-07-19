package top.zk123.mye.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.ArticleNotFoundException;
import top.zk123.mye.Exception.NoLoginException;
import top.zk123.mye.Service.NoticeService;

import javax.annotation.Resource;

/**
 * 消息盒子
 */
@RestController
public class NoticeController {

    @Resource
    NoticeService noticeService;

    /**
     * 获取消息列表
     *
     * @return ReturnInfo
     */
    @GetMapping(value = "/c/message")
    public ReturnInfo getMessage() throws NoLoginException, ArticleNotFoundException {
        return noticeService.getMessage();
    }
}
