package top.zk123.mye.Shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 实现 AuthenticationToken 接口，作为 Token 传入到 Realm 的载体：
 */
public class ShiroAuthToken implements AuthenticationToken {
    private String token;

    public ShiroAuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
