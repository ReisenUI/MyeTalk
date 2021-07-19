package top.zk123.mye.Shiro;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import top.zk123.mye.Exception.TokenInvalidException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器，拦截和验证 Token 的有效性.
 */
public class ShiroAuthFilter extends BasicHttpAuthenticationFilter {

    /**
     * 存储 Token 的 Headers Key
     */
    protected static final String AUTHORIZATION_HEADER = "Authorization";
    /**
     * Token 的开头部分
     */
    protected static final String BEARER = "Bearer ";
    private String token;

    /**
     * 判断是否允许访问
     *
     * @param request     Request
     * @param response    Response
     * @param mappedValue mapperValue
     * @return true 表示允许访问
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (this.getAuthzHeader(request) != null) {
            // Case 1: Token 未过期，如果即将过期，则刷新 Token
            try {
                // 对 Token 进行检查
                executeLogin(request, response);
                String refreshToken = new TokenUtils().refreshToken(this.token);
                if (refreshToken != null) {
                    this.token = refreshToken.substring(BEARER.length());
                    // 更新 Header 中的 Token 值
                    shiroAuthResponse(response,true);
                }
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * 从 Request 的 Header 取得 Token
     *
     * @param request ServletRequest
     * @return token or null
     */
    @Override
    protected String getAuthzHeader(ServletRequest request) {
        try {
            // header 是否存在 Token
            HttpServletRequest httpRequest = WebUtils.toHttp(request);
            this.token = httpRequest.getHeader(AUTHORIZATION_HEADER).substring(BEARER.length());
            return this.token;
        } catch (Exception e) {
            return null;
        }
    }

    // 自动调用 ShiroRealm 进行登入
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        // Subject 主体，可以代表用户和程序，它需要访问系统，系统则需要对其进行认证和授权。
        this.getSubject(request, response).login(new top.zk123.mye.Shiro.ShiroAuthToken(this.token));
        return true;
    }

    /**
     * 未授权访问 || 刷新 Header 中 Token的值
     *
     * @param response Response
     * @param refresh 是否刷新
     */
    private void shiroAuthResponse(ServletResponse response, boolean refresh) throws TokenInvalidException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (refresh) {
            // 刷新 Token，设置返回的头部
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            httpServletResponse.addHeader(AUTHORIZATION_HEADER, BEARER + this.token);
        } else throw new TokenInvalidException();
    }
}

