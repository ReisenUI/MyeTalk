package top.zk123.mye.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.Diary;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Service.DiaryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 主界面日记相关接口
 */

@RestController
public class DiaryController {

    @Resource
    DiaryService diaryService;

    /**
     * 发布日记
     *
     * @param date         日期
     * @param title        标题
     * @param weather      天气
     * @param emotions     心情数组
     * @param activities   活动数组
     * @param background   背景
     * @param diaryContent 日记内容 JSON
     * @return ReturnInfo
     * @throws FileUploadException    .
     * @throws WrongFileTypeException .
     * @throws FileEmptyException     .
     */
    @PostMapping(value = "/diary/create")
    public ReturnInfo postDiary(@RequestParam(value = "diaryId") long diaryId,
                                @RequestParam(value = "date") String date,
                                @RequestParam(value = "title") String title,
                                @RequestParam(value = "weather") String weather,
                                @RequestParam(value = "emotions") ArrayList<String> emotions,
                                @RequestParam(value = "activities") ArrayList<String> activities,
                                @RequestParam(value = "background") List<String> background,   //MultipartFile[] background
                                @RequestParam(value = "diaryContent") String diaryContent)
            throws DiaryNotFoundException, NoLoginException{
        return diaryService.createDiary(diaryId, date, title, weather, emotions, activities, background, diaryContent);
    }


    /**
     * 获取具体YYYY-MM下哪天记录了日记
     *
     * @param date
     * @return
     */
    @GetMapping(value = "/calendar")
    public ReturnInfo getCalendarInfo(@RequestParam(value = "selectedDate") String date) throws NoLoginException {
        return diaryService.getCalendarInfo(date);
    }

    /**
     * 获取日记列表，分页实现
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/diary")
    public ReturnInfo getDiary(@RequestParam(value = "pageSize") int pageSize,
                               @RequestParam(value = "pageNum") int pageNum) throws NoLoginException {
        return diaryService.getDiary(pageSize, pageNum);
    }

    /**
     * 通过关键词查询日记
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping(value = "/diary/keyword")
    public ReturnInfo getDiaryByKeyWord(@RequestParam(value = "keyword") String keyword,
                                        @RequestParam(value = "pageSize") int pageSize,
                                        @RequestParam(value = "pageNum") int pageNum) throws NoLoginException {
        return diaryService.getDiaryByKeyword(keyword, pageSize, pageNum);
    }

    /**
     * 通过具体的某一天寻找日记
     *
     * @param date
     * @return
     */
    @GetMapping(value = "/diary/calendar")
    public ReturnInfo getDiaryByCalendar(@RequestParam(value = "selectedDate") String date,
                                         @RequestParam(value = "pageSize") int pageSize,
                                         @RequestParam(value = "pageNum") int pageNum) throws NoLoginException {
        return diaryService.getDiaryByDate(date, pageSize, pageNum);
    }

    /**
     * 通过日记ID获取日记内容
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     */
    @GetMapping(value = "/diary/detail")
    public ReturnInfo getDiaryInfo(@RequestParam(value = "diaryId") long diaryId) throws DiaryNotFoundException, NoLoginException {
        return diaryService.getDiaryInfo(diaryId);
    }

    /**
     * 通过日记Id删除日记
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     * @throws NoLoginException
     */
    @PostMapping(value = "/diary/delete")
    public ReturnInfo deleteDiary(@RequestParam(value = "diaryId") long diaryId) throws DiaryNotFoundException, NoLoginException {
        return diaryService.deleteDiary(diaryId);
    }

    /**
     * 通过日记ID，将某个日记设置为喜欢
     *
     * @param diaryId
     * @return
     * @throws DiaryNotFoundException
     * @throws NoLoginException
     */
    @PostMapping(value = "/diary/favorite")
    public ReturnInfo favoriteDiary(@RequestParam(value = "diaryId") long diaryId) throws DiaryNotFoundException, NoLoginException{
        return diaryService.toggleFavorite(diaryId);
    }
}
