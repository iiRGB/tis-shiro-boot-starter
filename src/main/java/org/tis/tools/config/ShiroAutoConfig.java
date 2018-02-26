package org.tis.tools.config;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.tis.tools.model.dto.shiro.AbfPermissionResolve;
import org.tis.tools.rservice.ac.capable.IAuthenticationRService;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tis.tools.shiro.authenticationToken.UserIdPasswordIdentityToken;
import org.tis.tools.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import org.tis.tools.shiro.filter.AbfLoginFilter;
import org.tis.tools.shiro.filter.AbfPermissionFilter;
import org.tis.tools.shiro.realm.UserRealm;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro权限管理的配置
 *
 */
@Configuration
@ConditionalOnBean({IAuthenticationRService.class, IRoleRService.class, IOperatorRService.class})
@ConditionalOnProperty(prefix = "abf.shiro", value = "enabled", havingValue = "true")
public class ShiroAutoConfig {

    /**
     * 安全管理器
     * DefaultWebSecurityManager 用于Web环境的实现，可以替代ServletContainerSessionManager，
     * 自己维护着会话，直接废弃了Servlet容器的会话管理
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.abfShiroRealm());
        securityManager.setAuthorizer(this.authorizer());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    public UserRealm abfShiroRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthenticationTokenClass(UserIdPasswordIdentityToken.class);
        userRealm.setCredentialsMatcher(this.credentialsMatcher());
        userRealm.setCachingEnabled(true);
        userRealm.setAuthenticationCachingEnabled(true);
        userRealm.setAuthenticationCacheName("authenticationCache");
        userRealm.setAuthorizationCachingEnabled(true);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    /**
     * 会话Cookie模板
     * 指定本系统SESSIONID, 默认为: JSESSIONID 问题: 与SERVLET容器名冲突, 如JETTY, TOMCAT 等默认JSESSIONID,
     * 当跳出SHIRO SERVLET时如ERROR-PAGE容器会为JSESSIONID重新分配值导致登录会话丢失!
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("ABFSESSIONID");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(180000);
        return cookie;
    }

    /**
     * 会话管理器
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 设置全局会话超时时间，默认30分钟，即如果30分钟内没有访问会话将过期 1800000
        sessionManager.setGlobalSessionTimeout(1800000);
        // 删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        // 是否开启会话验证器，默认是开启的
        sessionManager.setSessionValidationSchedulerEnabled(true);
        /*Shiro提供了会话验证调度器，用于定期的验证会话是否已过期，如果过期将停止会话；
        出于性能考虑，一般情况下都是获取会话时来验证会话是否过期并停止会话的；
        但是如在web环境中，如果用户不主动退出是不知道会话是否过期的，因此需要定期的检测会话是否过期，
        Shiro提供了会话验证调度器SessionValidationScheduler来做这件事情*/
//        sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }
    /**
     * 会话DAO
     * @return
     */
    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }

    /**
     * 会话ID生成器
     *
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

//    /**
//     * 会话验证调度器
//     * @return
//     */
//    @Bean
//    public QuartzSessionValidationScheduler sessionValidationScheduler() {
//        QuartzSessionValidationScheduler scheduler = new QuartzSessionValidationScheduler();
//        scheduler.setSessionValidationInterval(1800000);
//        scheduler.setSessionManager(sessionManager());
//        return scheduler;
//    }


    @Bean
    public RetryLimitHashedCredentialsMatcher credentialsMatcher() {
        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(2);
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

//    @Bean
//    public AbfLoginFilter abfLoginFilter() {
//        return new AbfLoginFilter();
//    }
//
//    @Bean
//    public AbfPermissionFilter abfPermissionFilter() {
//        return new AbfPermissionFilter();
//    }

    /**
     * 自定义authorizer
     *
     * @return
     */
    @Bean
    public ModularRealmAuthorizer authorizer() {
        ModularRealmAuthorizer modularRealmAuthorizer = new ModularRealmAuthorizer();
        modularRealmAuthorizer.setPermissionResolver(this.abfPermission());
        return new ModularRealmAuthorizer();
    }

    /**
     * 自定义permissionResolver
     *
     * @return
     */
    @Bean
    public AbfPermissionResolve abfPermission() {
        return new AbfPermissionResolve();
    }


    /**
     * Shiro的过滤器链
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Value("${abf.shiro.anons}")String anons,
                                              @Value("${abf.shiro.permission.enabled}")Boolean permEnable) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager());

        Map<String, Filter> filterMap = new LinkedHashMap<>(2);
        filterMap.put("abfLogin", new AbfLoginFilter());
        filterMap.put("abfPerm", new AbfPermissionFilter());
        shiroFilter.setFilters(filterMap);
        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         *
         */
        Map<String, String> hashMap = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(anons)) {
            for (String anon : anons.split(",")) {
                hashMap.put(anon, "anon");
            }
        }
        if (permEnable) {
            hashMap.put("/**", "abfLogin,abfPerm");
        } else {
            hashMap.put("/**", "abfLogin");
        }
        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }


    /**
     * 在方法中 注入 securityManager,进行代理控制
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager()});
        return bean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    @DependsOn(value = "lifecycleBeanPostProcessor") //依赖其他bean的初始化
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

}
