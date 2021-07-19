package top.zk123.mye.Service.Imp;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zk123.mye.Bean.HibernateId;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Bean.User;
import top.zk123.mye.Bean.VerifyCode;
import top.zk123.mye.Exception.*;
import top.zk123.mye.Mapper.HibernateIdMapper;
import top.zk123.mye.Mapper.UserMapper;
import top.zk123.mye.Mapper.VerifyCodeMapper;
import top.zk123.mye.Service.EmailService;
import top.zk123.mye.Service.UserService;
import top.zk123.mye.Shiro.TokenUtils;
import top.zk123.mye.Util.AESUtil;
import top.zk123.mye.Util.EmailTemplate;
import top.zk123.mye.Util.GlobalUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static top.zk123.mye.Util.GlobalUtil.*;

@Service
public class UserServiceImp implements UserService {
    /**
     * Hash 加密的盐
     **/
    private final String SALT = "#4d1*dlmmddewd@34%";

    @Value("${mail.mailFrom}")
    private String from;

    @Resource
    private TokenUtils tokenUtils;

    @Resource
    private HibernateIdMapper hibernateIdMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    EmailService emailService;

    @Resource
    VerifyCodeMapper verifyCodeMapper;

    @Override
    public ReturnInfo register(String email, String password, String code) throws WrongDynamicCodeException, ParseException, UserExistedException {
        // 验证邮箱是否被使用
        User local_user = userMapper.findByUserByEmail(email);
        if (local_user != null) {
            throw new UserExistedException();
        }
        VerifyCode verify = verifyCodeMapper.findByEmail(email, code);
        if (verify == null) {
            throw new WrongDynamicCodeException();
        }
        String dateStr = verify.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long date = sdf.parse(dateStr).getTime();
        long now = sdf.parse(getCurrentTime()).getTime();
        if ((now - date) > 15 * 60 * 1000) {
            throw new WrongDynamicCodeException();
        }
        // 注册新用户
        String pwd = new SimpleHash("SHA-512", password, this.SALT).toString();
        User user = new User();
        HibernateId hibernateId = hibernateIdMapper.findHibernateIdByName("userId");
        long id = hibernateId.getId();
        user.setId(id++);
        user.setEmail(email);
        user.setPassword(pwd);
        user.setGender("未知");
        hibernateId.setId(id);
        hibernateIdMapper.save(hibernateId);
        userMapper.save(user);
        return new ReturnInfo(200, "注册成功", null);
    }

    @Override
    public ReturnInfo login(User user) throws UserNotFoundException, WrongPasswordException {
        // 验证 用户名
        User user1 = userMapper.findByUserByEmail(user.getEmail());
        if (user1 == null) {
            throw new UserNotFoundException();
        }
        // 验证密码
        String password = new SimpleHash("SHA-512", user.getPassword(), this.SALT).toString();
        if (!password.equals(user1.getPassword())) {
            throw new WrongPasswordException();
        }
        // 生成 Token
        return new ReturnInfo(200, "登录成功", tokenUtils.createToken(user1));
    }

    @Override
    public ReturnInfo updateUserInfo(User user) throws NoLoginException {
        User local = userMapper.findByUserId(GlobalUtil.getIdByToken());
        if (!"".equals(user.getNickname())) {
            local.setNickname(user.getNickname());
        }
        if (!"".equals(user.getIntroduction())) {
            local.setIntroduction(user.getIntroduction());
        }
        if (user.getTag().length() != 0) {
            local.setTag(user.getTag());
        }
        if (!"".equals(user.getGender())) {
            local.setGender(user.getGender());
        }
        if (!"".equals(user.getBirth())) {
            local.setBirth(user.getBirth());
        }
        userMapper.save(local);
        return new ReturnInfo(200, "用户信息修改成功", null);
    }

    @Override
    public ReturnInfo updatePwd(String pwd) throws InvalidPwdException, SamePwdException, NoLoginException {
        String encryptPwd = new SimpleHash("SHA-512", pwd, this.SALT).toString();
        User local = userMapper.findByUserId(GlobalUtil.getIdByToken());
        if (local.getPassword().equals(encryptPwd)) {
            throw new SamePwdException();
        }
        Pattern p = Pattern.compile("(?=^.{6,16}$)(?=(?:.*?\\d){1})(?=.*[a-z])(?=(?:.*?[A-Z]){1})(?=(?:.*?[`·~!@#$%^&*()_+}{|:;'\",<.>/?\\=\\[\\]\\-\\\\]){1})(?!.*\\s)[0-9a-zA-Z`·~!@#$%^&*()_+}{|:;'\",<.>/?\\=\\[\\]\\-\\\\]*$");
        Matcher m = p.matcher(pwd);
        if (!m.matches()) {
            throw new InvalidPwdException();
        }
        local.setPassword(encryptPwd);
        userMapper.save(local);
        return new ReturnInfo(200, "密码修改成功", null);
    }

    @Override
    public ReturnInfo getUserInfo() throws NoLoginException {
        return new ReturnInfo(200, "用户信息获取成功", userMapper.findByUserId(GlobalUtil.getIdByToken()));
    }

    @Override
    public ReturnInfo updateAvatar(MultipartFile file) throws FileEmptyException, WrongFileTypeException,
            FileUploadException, NoLoginException {
        String newFileName = GlobalUtil.uploadImg(file, GlobalUtil.LOCAL_AVATAR_PATH);
        User local = userMapper.findByUserId(GlobalUtil.getIdByToken());
        local.setAvatar(GlobalUtil.WEB_AVATAR_PATH + newFileName);
        userMapper.save(local);
        return new ReturnInfo(200, "文件上传成功", GlobalUtil.WEB_AVATAR_PATH + newFileName);
    }

    @Override
    public ReturnInfo sendDynamicCode2Email(String email) throws UserExistedException {
        EmailTemplate emailTemplate = new EmailTemplate();
        User user1 = userMapper.findByUserByEmail(email);
        if (user1 != null) {
            throw new UserExistedException();
        }
        String code = generateVerifyCode();
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setCode(code);
        verifyCode.setEmail(email);
        verifyCode.setDate(getCurrentTime());
        verifyCodeMapper.save(verifyCode);
        // 发送激活邮箱
        String content = emailTemplate.getHtml("MYE_心情说", email, "注册", code);
        emailService.sendSimpleMail(from, "MYE", email, "", "【MYE】", content);
        return new ReturnInfo(200, "验证码发送成功", null);
    }
}
