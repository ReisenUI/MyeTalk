package top.zk123.mye.Service;

import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.*;

import java.text.ParseException;

/**
 * 用户相关方法 接口
 */
public interface UserService {
    /**
     * @param email    邮箱
     * @param password 密码
     * @param code     邮箱验证码
     * @return returnInfo
     * @throws RegisterFailedException 注册失败
     * @throws UserExistedException    邮箱已被使用
     */
    ReturnInfo register(String email, String password, String code) throws RegisterFailedException, UserExistedException, WrongDynamicCodeException, ParseException;


    /**
     * 登录
     *
     * @param user 用户
     * @return ReturnInfo
     * @throws UserNotFoundException  用户不存在
     * @throws WrongPasswordException 密码错误
     */
    ReturnInfo login(User user) throws UserNotFoundException, WrongPasswordException;

    /**
     * 更新用户信息(不包括密码)
     *
     * @param user {@link User}
     * @return ReturnInfo
     */
    ReturnInfo updateUserInfo(User user) throws NoLoginException;

    /**
     * 更新密码
     *
     * @param pwd 密码
     * @return ReturnInfo
     */
    ReturnInfo updatePwd(String pwd) throws InvalidPwdException, SamePwdException, NoLoginException;

    /**
     * 获取用户信息
     *
     * @return ReturnInfo
     */
    ReturnInfo getUserInfo() throws NoLoginException;

    /**
     * 更新用户头像
     *
     * @param avatar 头像图片
     * @return ReturnInfo
     */
    ReturnInfo updateAvatar(MultipartFile avatar) throws FileEmptyException, WrongFileTypeException,
            FileUploadException, NoLoginException;

    // 发送邮箱验证码
    ReturnInfo sendDynamicCode2Email(String email) throws UserExistedException;

}
