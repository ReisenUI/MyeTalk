package top.zk123.mye.Service.Imp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.Diary;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.ReturnObj.DiaryObj;
import top.zk123.mye.Bean.ReturnObj.DiaryReturnInfo;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Mapper.DiaryMapper;
import top.zk123.mye.Mapper.UserMapper;
import top.zk123.mye.Service.DiaryService;
import top.zk123.mye.Util.GlobalUtil;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

import static top.zk123.mye.Util.GlobalUtil.*;

@Service
public class DiaryServiceImp implements DiaryService {
    @Resource
    DiaryMapper diaryMapper;

    @Resource
    UserMapper userMapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ReturnInfo createDiary(long diaryId, String date, String title, String weather, ArrayList<String> emotions,
                                  ArrayList<String> activities, List<String> background, String content)
            throws NoLoginException, DiaryNotFoundException {
        long newId = diaryId;
        if (diaryId == -1) {  // 代表新建日记
            // 判断当天是否记录过日记，用来添加记录天数
            long existDate = diaryMapper.getDiaryByCalendarNoPage(GlobalUtil.getIdByToken(), date).size();

            Diary diary = newDiary(date, title, weather, emotions, activities, background, content);
            diaryMapper.save(diary);
            newId = diary.getDiary_id();

            // 统计该日记记录字数信息等
            JSONArray jsonArray = JSON.parseObject(content).getJSONArray("list");
            long words = 0;
            // 获得长JSON字符串中记录内容的信息（字数等）
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String contents = jsonObject.getString("valueSync");
                words += contents.replaceAll(" ", "").length();
            }

            // 更新用户记录日记的状态
            User currentUser = userMapper.findByUserId(GlobalUtil.getIdByToken());
            long diaryNum = currentUser.getNum_of_dairy(),
                    wordsNum = currentUser.getWords_of_record(),
                    dayNum = currentUser.getDay_of_record();
            currentUser.setNum_of_dairy(diaryNum + 1);
            currentUser.setWords_of_record(wordsNum + words);
            currentUser.setDay_of_record(dayNum + (existDate == 0 ? 1 : 0));
            userMapper.save(currentUser);
            return new ReturnInfo(200, "记录新日记成功", new DiaryReturnInfo(newId, background.get(0)));  //返回列表中第一个地址
        } else {    // 代表修改日记
            Diary existDiary = diaryMapper.getOneDiary(GlobalUtil.getIdByToken(), diaryId);
            if (existDiary == null) {
                throw new DiaryNotFoundException();
            }

            String recordedDate = existDiary.getTime(); // 接下来需要判断日期是否改变
            String recordedContent = existDiary.getContent();
            long existDate = diaryMapper.getDiaryByCalendarNoPage(GlobalUtil.getIdByToken(), date).size();
            long recordedDates = diaryMapper.getDiaryByCalendarNoPage(GlobalUtil.getIdByToken(), recordedDate).size();

            long recordedWords = 0, words = 0;

            // 获得日记改变前后记录字数的差距
            JSONArray recordedJsonArray = JSON.parseObject(recordedContent).getJSONArray("list");
            for (Object obj : recordedJsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String contents = jsonObject.getString("valueSync");
                recordedWords += contents.replaceAll(" ", "").length();
            }
            JSONArray jsonArray = JSON.parseObject(content).getJSONArray("list");
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String contents = jsonObject.getString("valueSync");
                words += contents.replaceAll(" ", "").length();
            }

            // 获得日记改变前后天数的差距
            long dateChanges = 0;
            if (existDate == 0) {
                ++dateChanges;
            }
            if (recordedDates == 1) {
                --dateChanges;
            }

            // 存储修改后的日记
            Diary modifiedDiary = modifyDiary(existDiary, date, title, weather, emotions, activities, background, content);
            diaryMapper.save(modifiedDiary);

            // 存储修改后的用户信息
            User currentUser = userMapper.findByUserId(GlobalUtil.getIdByToken());
            long wordsNum = currentUser.getWords_of_record(),
                    dayNum = currentUser.getDay_of_record();

            currentUser.setWords_of_record(wordsNum - recordedWords + words);
            currentUser.setDay_of_record(dayNum + dateChanges);
            userMapper.save(currentUser);
            return new ReturnInfo(200, "修改日记成功", new DiaryReturnInfo(newId, background.get(0)));  //返回列表中第一个地址
        }
    }

    @Override
    public ReturnInfo getCalendarInfo(String selectedDate) throws NoLoginException {
        List<String> dateList = diaryMapper.findDate(GlobalUtil.getIdByToken(), selectedDate);
        Map<Integer, Boolean> map = new HashMap<>();
        for (String date : dateList) {
            String[] ymd = date.split("-");
            map.put(Integer.parseInt(ymd[2]), true);
        }
        return new ReturnInfo(200, "查询成功", map);
    }

    @Override
    public ReturnInfo getDiary(int pageSize, int pageNum) throws NoLoginException {
        DiaryObj diaryObj = new DiaryObj();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "time"));
        Page<Diary> pageList = diaryMapper.getDiary(GlobalUtil.getIdByToken(), pageable);
        // 数据总数
        diaryObj.setTotalSize(pageList.getTotalElements());
        // 总页数
        diaryObj.setTotalPage(pageList.getTotalPages());
        // 分页数据
        diaryObj.setDiaryList(pageList.getContent());
        return new ReturnInfo(200, "第" + pageNum + "页数据查询成功", JSON.toJSON(diaryObj));
    }

    @Override
    public ReturnInfo getDiaryByKeyword(String keyword, int pageSize, int pageNum) throws NoLoginException {
        DiaryObj diaryObj = new DiaryObj();
        // 创建关键字列表，用于接下来的查询
        String[] keywords = keyword.split("-");
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "time"));
        // 创建分页参数，
        Page<Diary> pageList;

        if (keyword.equals("")) {  //如果关键词为空，则寻找喜爱的日记
            pageList = diaryMapper.getFavoriteDiary(GlobalUtil.getIdByToken(), pageable);
        } else {    //否则按照关键字查找
            // 创建日记的列表，后来需要转换成分页
            List<Diary> diaryList = new ArrayList<>();
            for (String s : keywords) {
                List<Diary> tempDiaryList;
                tempDiaryList = diaryMapper.getDiaryByKeyword(GlobalUtil.getIdByToken(), s);
                diaryList.addAll(tempDiaryList);
            }
            diaryList.sort(Comparator.comparing(Diary::getTime).reversed());
            pageList = listConvertToPage(diaryList, pageable);
        }

        // 数据总数
        diaryObj.setTotalSize(pageList.getTotalElements());
        // 总页数
        diaryObj.setTotalPage(pageList.getTotalPages());
        // 分页数据
        diaryObj.setDiaryList(pageList.getContent());
        return new ReturnInfo(200, "第" + pageNum + "页数据检索成功", JSON.toJSON(diaryObj));
    }

    @Override
    public ReturnInfo getDiaryByDate(String selectedData, int pageSize, int pageNum) throws NoLoginException {
        DiaryObj diaryObj = new DiaryObj();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by(Sort.Direction.DESC, "diary_id"));
        Page<Diary> pageList;
        pageList = diaryMapper.getDiaryByCalendar(GlobalUtil.getIdByToken(), selectedData, pageable);
        diaryObj.setTotalSize(pageList.getTotalElements());
        diaryObj.setTotalPage(pageList.getTotalPages());
        diaryObj.setDiaryList(pageList.getContent());
        return new ReturnInfo(200, selectedData + "当天的" + "第" + pageNum + "当天的日记查询成功", JSON.toJSON(diaryObj));
    }


    @Override
    public ReturnInfo getDiaryInfo(long diaryId) throws DiaryNotFoundException, NoLoginException {
        Diary diary = diaryMapper.getOneDiary(GlobalUtil.getIdByToken(), diaryId);
        if (diary == null) {
            throw new DiaryNotFoundException();
        }
        return new ReturnInfo(200, "当前日记的内容获取成功", diary);
    }

    @Override
    public ReturnInfo deleteDiary(long diaryId) throws DiaryNotFoundException, NoLoginException {
        Diary deletingDiary = diaryMapper.getOneDiary(GlobalUtil.getIdByToken(), diaryId);
        if (deletingDiary == null) {
            throw new DiaryNotFoundException();
        }
        // 获取被删除的日记的字数
        String currentContent = deletingDiary.getContent();
        long words = 0;
        JSONArray deletingJsonArray = JSON.parseObject(currentContent).getJSONArray("list");
        for (Object obj : deletingJsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String contents = jsonObject.getString("valueSync");
            words += contents.replaceAll(" ", "").length();
        }
        diaryMapper.delete(deletingDiary);

        User currentUser = userMapper.findByUserId(GlobalUtil.getIdByToken());
        long wordsNum = currentUser.getWords_of_record(),
                diaryNum = currentUser.getNum_of_dairy();
        currentUser.setWords_of_record(wordsNum - words);
        currentUser.setNum_of_dairy(diaryNum - 1);
        userMapper.save(currentUser);
        return new ReturnInfo(200, "删除日记成功", null);
    }

    @Override
    public ReturnInfo toggleFavorite(long diaryId) throws DiaryNotFoundException, NoLoginException {
        Diary favoriteDiary = diaryMapper.getOneDiary(GlobalUtil.getIdByToken(), diaryId);
        if (favoriteDiary == null) {
            throw new DiaryNotFoundException();
        }
        boolean hasFavorite = favoriteDiary.isFavorite();
        favoriteDiary.setFavorite(!hasFavorite);
        diaryMapper.save(favoriteDiary);
        return new ReturnInfo(200, "改变收藏状态成功", null);
    }

    /** utils **/

    /**
     * 新建日记
     *
     * @param date
     * @param title
     * @param weather
     * @param emotions
     * @param activities
     * @param images
     * @param content
     * @return
     * @throws NoLoginException
     */
    private Diary newDiary(String date, String title, String weather, ArrayList<String> emotions,
                           ArrayList<String> activities, List<String> images, String content) throws NoLoginException {
        Diary diary = new Diary();
        setDiary(date, title, weather, emotions, activities, images, content, diary);
        diary.setFavorite(false);
        return diary;
    }

    /**
     * 修改日记
     *
     * @param diary
     * @param date
     * @param title
     * @param weather
     * @param emotions
     * @param activities
     * @param images
     * @param content
     * @return
     * @throws NoLoginException
     */
    private Diary modifyDiary(Diary diary, String date, String title, String weather, ArrayList<String> emotions,
                              ArrayList<String> activities, List<String> images, String content) throws NoLoginException {
        Diary modifyDiary = diary;
        setDiary(date, title, weather, emotions, activities, images, content, modifyDiary);
        return diary;
    }

    private void setDiary(String date, String title, String weather, ArrayList<String> emotions,
                          ArrayList<String> activities, List<String> images, String content, Diary modifyDiary) throws NoLoginException {
        modifyDiary.setTime(date);
        modifyDiary.setTitle(title);
        modifyDiary.setWeather(weather);
        modifyDiary.setEmotions(List2String(emotions));
        modifyDiary.setActivity(List2String(activities));
        modifyDiary.setBackground(List2String(images));
        modifyDiary.setUser_id(GlobalUtil.getIdByToken());
        modifyDiary.setContent(content);
    }

    /**
     * 手动实现列表转成分页
     *
     * @param list
     * @param pageable
     * @param <T>
     * @return
     */
    private static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }
}
