package top.zk123.mye.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Service.UserService;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 用户相关接口
 */
@RestController
public class UserController {

    @Resource
    UserService userService;

    /**
     * 用户注册.
     * 采用 SHA-512 加盐加密存储.
     *
     * @param email    邮箱
     * @param password 密码
     * @param code     邮箱验证码
     * @return ReturnInfo
     * @throws RegisterFailedException 注册失败
     */
    @PostMapping("/register")
    public ReturnInfo register(@RequestParam(value = "email") String email,
                               @RequestParam(value = "password") String password,
                               @RequestParam(value = "code") String code) throws
            RegisterFailedException, UserExistedException, WrongDynamicCodeException, ParseException {
        return userService.register(email, password, code);
    }

    /**
     * 注册发送短信验证码
     *
     * @param email 邮箱
     * @return ReturnInfo
     */
    @PostMapping("/register/sendCode")
    public ReturnInfo sendDynamicCode2Email(@RequestParam(value = "email") String email) throws UserExistedException {
        return userService.sendDynamicCode2Email(email);
    }

    /**
     * 邮箱 + 密码 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @return ReturnInfo
     * @throws UserNotFoundException  用户名不存在
     * @throws WrongPasswordException 密码错误
     */
    @PostMapping("/login")
    public ReturnInfo login(@RequestParam(value = "email") String email,
                            @RequestParam(value = "password") String password) throws
            UserNotFoundException, WrongPasswordException {
        return userService.login(new User(email, password));
    }

    /**
     * 更新用户信息.
     *
     * @param user 用户部分属性  nickname, introduction, tag, gender, birth
     * @return ReturnInfo
     */
    @PostMapping(value = "/people/update")
    public ReturnInfo updateUserInfo(@RequestBody User user) throws NoLoginException {
        return userService.updateUserInfo(user);
    }

    /**
     * 修改密码
     *
     * @param password 密码
     * @return ReturnInfo
     */
    @PostMapping(value = "/people/pwd")
    public ReturnInfo updatePwd(@RequestParam(name = "password") String password) throws InvalidPwdException,
            SamePwdException, NoLoginException {
        return userService.updatePwd(password);
    }

    /**
     * 获取个人中心数据
     *
     * @return ReturnInfo
     */
    @GetMapping(value = "/people/info")
    public ReturnInfo getUserInfo() throws NoLoginException {
        return userService.getUserInfo();
    }

    /**
     * 更新用户头像
     *
     * @param avatar 头像
     * @return ReturnInfo
     * @throws FileUploadException    .
     * @throws WrongFileTypeException .
     * @throws FileEmptyException     .
     */
    @PostMapping(value = "/people/avatar")
    public ReturnInfo updateAvatar(@RequestParam(name = "avatar") MultipartFile avatar) throws
            FileUploadException, WrongFileTypeException, FileEmptyException, NoLoginException {
        return userService.updateAvatar(avatar);
    }
}
