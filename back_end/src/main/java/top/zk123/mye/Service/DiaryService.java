package top.zk123.mye.Service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.Diary;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 日记相关方法接口
 */
public interface DiaryService {
    /**
     * 发布日记
     *
     * @param diaryId      日记的Id（-1代表新建，否则代表修改某个日记）
     * @param date         日期
     * @param title        标题
     * @param weather      天气
     * @param emotions     心情数组
     * @param activities   活动数组
     * @param background   背景
     * @param diaryContent 日记内容 JSON
     * @return ReturnInfo
     */
    ReturnInfo createDiary(long diaryId, String date, String title, String weather, ArrayList<String> emotions,
                           ArrayList<String> activities, List<String> background, String diaryContent)
            throws NoLoginException, DiaryNotFoundException;


    /**
     * 获得日历信息
     *
     * @param selectedDate 日期格式：YYYY-MM
     * @return ReturnInfo
     */
    ReturnInfo getCalendarInfo(String selectedDate) throws NoLoginException;


    /**
     * 普通情况下的获得日记
     *
     * @param pageSize 分页大小
     * @param pageNum  当前分页
     * @return ReturnInfo
     */
    ReturnInfo getDiary(int pageSize, int pageNum) throws NoLoginException;

    /**
     * 通过日期来获得日记
     *
     * @param selectedData 日期格式：YYYY-MM-DD
     * @return ReturnInfo
     */
    ReturnInfo getDiaryByDate(String selectedData, int pageSize, int pageNum) throws NoLoginException;

    /**
     * 通过关键词来获得日记
     *
     * @param keyword  关键字
     * @param pageSize 分页大小
     * @param pageNum  当前分页
     * @return ReturnInfo
     */
    ReturnInfo getDiaryByKeyword(String keyword, int pageSize, int pageNum) throws NoLoginException;

    /**
     * 通过日记ID获得日记的具体信息
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     */
    ReturnInfo getDiaryInfo(long diaryId) throws DiaryNotFoundException, NoLoginException;

    /**
     * 通过日记ID删除日记
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     * @throws NoLoginException
     */
    ReturnInfo deleteDiary(long diaryId) throws DiaryNotFoundException, NoLoginException;

    /**
     * 通过日记ID，将该ID设置为收藏（喜欢）
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     * @throws NoLoginException
     */
    ReturnInfo toggleFavorite(long diaryId) throws DiaryNotFoundException, NoLoginException;
}
