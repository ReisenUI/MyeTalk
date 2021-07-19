package top.zk123.mye.Shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import top.zk123.mye.Bean.User;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 从 ShiroAuthToken 取得 Token 并进行身份验证和角色权限配置。
 */
@Service
public class ShiroRealm extends AuthorizingRealm {
    @Resource
    TokenUtils tokenUtils;

    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        // 指定当前 authenticationToken 需要为 ShiroAuthToken 的实例
        return authenticationToken instanceof ShiroAuthToken;
    }

    /**
     * 获取 Token 并验证
     *
     * @param authenticationToken Token 实例
     * @return 授权信息
     * @throws AuthenticationException Exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        ShiroAuthToken shiroAuthToken = (ShiroAuthToken) authenticationToken;
        String token = (String) shiroAuthToken.getCredentials();
        // 验证 Token
        User user = tokenUtils.validationToken(token);
        if (user == null) {
            throw new AuthenticationException("Token 无效");
        }
        return new SimpleAuthenticationInfo(token, token, "ShiroRealm");
    }

    /**
     * 实现用户角色、用户权限的配置，对于没有用户角色、权限的系统来说，可以不实现，直接 super
     *
     * @param principals Token
     * @return 认证信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取用户信息
        User user = tokenUtils.validationToken(principals.toString());
        // 创建一个授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 判断用户角色是否存在
        if (!user.getRole().isEmpty()) {
            // 角色设置
            info.addRole(user.getRole());
        }
        if (!user.getPermission().isEmpty()) {
            // 进行权限设置,根据 , 分割
            Arrays.stream("user".split(",")).forEach(info::addStringPermission);
        }
        return info;
    }
}
