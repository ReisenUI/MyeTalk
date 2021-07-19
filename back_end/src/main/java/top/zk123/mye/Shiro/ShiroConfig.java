package top.zk123.mye.Shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 */
@Configuration
public class ShiroConfig {
    /**
     * 使用自定义的 Realm 和关闭 Session 管理器
     *
     * @param realm 自定义的 Realm
     * @return SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的 realm
        manager.setRealm(realm);
        // 关闭 Session
        // shiro.ini 方式参考 http://shiro.apache.org/session-management.html#disabling-subject-state-session-storage
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }

    /**
     * 添加拦截器和配置拦截规则
     *
     * @param securityManager 安全管理器
     * @return 拦截器和拦截规则
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 一定要用 LinkedHashMap，HashMap 顺序不一定按照 put 的顺序，拦截匹配规则是从上往下的
        // 比如 /api/user/login ，已经匹配到了，即使用 anon 的拦截器，就不会再去匹配 /** 了
        // anon 支持匿名访问的拦截器
        LinkedHashMap<String, String> filterChainDefinitions = new LinkedHashMap<>(4);
        // 登录接口和注册放开
        filterChainDefinitions.put("/login", "anon");
        filterChainDefinitions.put("/register", "anon");
        filterChainDefinitions.put("/register/sendCode", "anon");
        filterChainDefinitions.put("/c/article/detail", "anon");
        filterChainDefinitions.put("/c/homepage/article", "anon");
        filterChainDefinitions.put("/c/homepage", "anon");
        filterChainDefinitions.put("/c/article", "anon");
        filterChainDefinitions.put("/c/topic", "anon");
        filterChainDefinitions.put("/c/article/hottest", "anon");
        filterChainDefinitions.put("/c/article/latest", "anon");
        filterChainDefinitions.put("/c/cmt", "anon");
        filterChainDefinitions.put("/c/cmt/detail", "anon");
        filterChainDefinitions.put("/data", "anon");
        filterChainDefinitions.put("/features", "anon");

        //  添加 shiroAuthFilter 的拦截器
        Map<String, Filter> filters = new HashMap<>(2);
        filters.put("authFilter", new ShiroAuthFilter());
        // 其他请求通过自定义的 authFilter
        filterChainDefinitions.put("/**", "authFilter");

        factoryBean.setFilterChainDefinitionMap(filterChainDefinitions);
        factoryBean.setFilters(filters);
        return factoryBean;
    }
}
