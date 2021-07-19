package top.zk123.mye.Mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import top.zk123.mye.Bean.Diary;

import java.util.List;

public interface DiaryMapper extends JpaRepository<Diary, String> {

    /**
     * 通过 userId 和 选择的YYYY-MM来查询哪几天记过日记
     *
     * @param id   userId
     * @param date YYYY-MM
     * @return dateList 列表记录哪几天记过日记
     */
    @Query(value = "select time from diary " +
            "where user_id = :id and time like %:date% " +
            "order by time ASC ", nativeQuery = true)
    List<String> findDate(long id, String date);

    /**
     * 普通情况下的获取日记
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "select * from diary where user_id = :id order by time desc, diary_id desc ", nativeQuery = true)
    Page<Diary> getDiary(long id, Pageable pageable);

    /**
     * 获得喜爱的日记
     *
     * @param id
     * @param pageable
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and favorite = true order by time desc", nativeQuery = true)
    Page<Diary> getFavoriteDiary(long id, Pageable pageable);

    /**
     * 通过关键词查找日记，分页
     *
     * @param id
     * @param keyword
     * @param pageable
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and " +
            "(title like %:keyword% or weather like %:keyword% or emotions like %:keyword% or activity like %:keyword%)" +
            "order by time DESC, diary_id DESC", nativeQuery = true)
    Page<Diary> getDiaryByKeywordPageable(long id, String keyword, Pageable pageable);

    /**
     * 通过关键词查找日记，不分页
     *
     * @param id
     * @param keyword
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and" +
            "(title like %:keyword% or weather like %:keyword% or emotions like %:keyword% or activity like %:keyword% or content like %:keyword%)" +
            "order by time DESC, diary_id DESC", nativeQuery = true)
    List<Diary> getDiaryByKeyword(long id, String keyword);

    /**
     * 通过具体的某一天寻找日记（分页版）
     *
     * @param id
     * @param date
     * @param pageable
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and time = :date order by diary_id desc", nativeQuery = true)
    Page<Diary> getDiaryByCalendar(long id, String date, Pageable pageable);

    /**
     * 通过具体的某一天寻找日记（不分页版）
     *
     * @param id
     * @param date
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and time = :date", nativeQuery = true)
    List<Diary> getDiaryByCalendarNoPage(long id, String date);

    /**
     * 查找将要被删除的日记
     *
     * @param id
     * @param diaryId
     * @return
     */
    @Query(value = "select * from diary where user_id = :id and diary_id = :diaryId", nativeQuery = true)
    Diary getOneDiary(long id, long diaryId);
}
