package top.zk123.mye.Handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zk123.mye.Bean.ReturnInfo;
import top.zk123.mye.Exception.*;

@RestControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(TokenInvalidException.class)
    public ReturnInfo tokenInvalidException() {
        return new ReturnInfo(1001, "非授权访问", null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ReturnInfo userNotFoundException() {
        return new ReturnInfo(1002, "用户不存在", null);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ReturnInfo wrongPasswordException() {
        return new ReturnInfo(1003, "密码错误", null);
    }

    @ExceptionHandler(RegisterFailedException.class)
    public ReturnInfo registerFailedException() {
        return new ReturnInfo(1004, "注册失败", null);
    }

    @ExceptionHandler(InvalidPwdException.class)
    public ReturnInfo InvalidPwdException() {
        return new ReturnInfo(1005, "密码不符合规范", "密码长度 6-16 位，包含至少一个特殊字符，一个数字，一个大写字母和一个小写字母。");
    }

    @ExceptionHandler(SamePwdException.class)
    public ReturnInfo SamePwdException() {
        return new ReturnInfo(1006, "新密码不能与旧密码相同", null);
    }

    @ExceptionHandler(FileEmptyException.class)
    public ReturnInfo FileEmptyException() {
        return new ReturnInfo(1007, "文件不得为空", null);
    }

    @ExceptionHandler(WrongFileTypeException.class)
    public ReturnInfo WrongFileTypeException() {
        return new ReturnInfo(1008, "不支持的文件类型", null);
    }

    @ExceptionHandler(FileUploadException.class)
    public ReturnInfo FileUploadException() {
        return new ReturnInfo(1009, "文件上传失败，请稍后再试", null);
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ReturnInfo ArticleNotFoundException() {
        return new ReturnInfo(1010, "该帖子不存在", null);
    }

    @ExceptionHandler(IllegalParamException.class)
    public ReturnInfo IllegalParamException() {
        return new ReturnInfo(1011, "非法参数", null);
    }

    @ExceptionHandler(DeleteCommentFailedException.class)
    public ReturnInfo DeleteCommentFailedException() {
        return new ReturnInfo(1012, "无法删除该评论", null);
    }

    @ExceptionHandler(NoLoginException.class)
    public ReturnInfo NoLoginException() {
        return new ReturnInfo(1013, "未登录", null);
    }

    @ExceptionHandler(UserExistedException.class)
    public ReturnInfo UserExistedException() {
        return new ReturnInfo(1014, "该邮箱已被使用", null);
    }

    @ExceptionHandler(AnotherMeException.class)
    public ReturnInfo AnotherMeException() {
        return new ReturnInfo(1015, "暂时没有数据,多写些日记再试吧", null);
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ReturnInfo NoticeNotFoundException() {
        return new ReturnInfo(1016, "Notice 记录不存在", null);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ReturnInfo CommentNotFoundException() {
        return new ReturnInfo(1017, "评论不存在", null);
    }


    @ExceptionHandler(WrongDynamicCodeException.class)
    public ReturnInfo WrongDynamicCodeException() {
        return new ReturnInfo(1018, "动态验证码错误", null);
    }





    @ExceptionHandler(DiaryNotFoundException.class)
    public ReturnInfo diaryNotFountException() {
        return new ReturnInfo(2001, "日记未找到", null);
    }

}
