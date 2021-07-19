package top.zk123.mye.Service.Imp;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import top.zk123.mye.Bean.Article;
import top.zk123.mye.Bean.Notice;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.ReturnObj.NoticeObj;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.NoLoginException;
import top.zk123.mye.Mapper.ArticleMapper;
import top.zk123.mye.Mapper.NoticeMapper;
import top.zk123.mye.Mapper.UserMapper;
import top.zk123.mye.Service.NoticeService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static top.zk123.mye.Util.GlobalUtil.getIdByToken;

@Service
public class NoticeServiceImp implements NoticeService {

    @Resource
    NoticeMapper noticeMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    ArticleMapper articleMapper;

    @Override
    public ReturnInfo getMessage() throws NoLoginException {
        List<Notice> noticeList = noticeMapper.findByUser_id(getIdByToken());
        List<NoticeObj> noticeObjList = new ArrayList<>();
        // 解析
        for (Notice notice : noticeList) {
            Article article = articleMapper.findByArticle_id(notice.getArticleId());
            if (article != null) {
                User p1 = userMapper.findByUserId(notice.getP1_id());
                NoticeObj noticeObj = new NoticeObj();
                noticeObj.setP1_id(notice.getP1_id());
                noticeObj.setP1_avatar(p1.getAvatar());
                noticeObj.setP1_nickname(p1.getNickname());
                noticeObj.setP1_content(notice.getP1_content());
                noticeObj.setTime(notice.getTime());
                noticeObj.setValid(notice.getValid());
                noticeObj.setP2_content(notice.getP2_content());
                noticeObj.setP2_id(notice.getUser_id());
                noticeObj.setP2_title(article.getTitle());
                noticeObj.setP2_articleId(notice.getArticleId());
                noticeObjList.add(noticeObj);
            }

        }
        return new ReturnInfo(200, "ok", JSON.toJSON(noticeObjList));
    }

}
