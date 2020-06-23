package com.ratta.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 刘明
 */
@Configuration
public class ShiroBeanConfig {

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        // 添加shiro内置过滤器
        //登录url
        factory.setLoginUrl("/login");
        //登录成功跳转地址
        factory.setSuccessUrl("/index");
        //登录失败没有权限跳转地址
        factory.setUnauthorizedUrl("/noPermission");
        /*
            请求拦截的规则，key是访问的路径，value是该路径使用什么拦截规则
            anon：无参，开放权限，可以理解为匿名用户或游客
            authc：无参，需要认证
            logout：无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
            authcBasic：无参，表示 httpBasic 认证
            user：无参，表示必须存在用户，当登入操作时不做检查
            ssl：无参，表示安全的URL请求，协议为 https
            perms[user]：参数可写多个，表示需要某个或某些权限才能通过，多个参数时写 perms["user, admin"]，当有多个参数时必须每个参数都通过才算通过
            roles[admin]：参数可写多个，表示是某个或某些角色才能通过，多个参数时写 roles["admin，user"]，当有多个参数时必须每个参数都通过才算通过
            rest[user]：根据请求的方法，相当于 perms[user:method]，其中 method 为 post，get，delete 等
            port[8081]：当请求的URL端口不是8081时，跳转到schemal://serverName:8081?queryString 其中 schmal 是协议 http 或 https 等等，serverName 是你访问的 Host，8081 是 Port 端口，queryString 是你访问的 URL 里的 ? 后面的参数
         */
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        //login拦截规则anon
        filterChainMap.put("/login", "anon");
        //anon表示不拦截
        filterChainMap.put("/loginUser", "anon");
        //logout：无参，注销，执行后会直接跳转到shiroFilterFactoryBean.setLoginUrl(); 设置的 url
        filterChainMap.put("/logout", "logout");

        //admin拦截规则roles[admin]（设置访问页面需要的权限）
        filterChainMap.put("/admin", "roles[admin]");//roles[admin]表示只允许admin角色访问

        //用户需要有编辑权限才能访问edit接口（设置访问页面需要的权限）
        filterChainMap.put("/edit", "perms[edit]");

        //在登录后可以访问任意地址（这个设置了只要用户登陆了就可以访问所有权限）
        //filterChainMap.put("/**", "user");

        //其他拦截规则authc  《必须这个/**匹配放到最后，如果在它之后做的一些规则都不会生效   切记》
        filterChainMap.put("/**", "authc");//authc表示需要认证才能访问


        factory.setFilterChainDefinitionMap(filterChainMap);
        return factory;
    }

    /**
     * 把realm注册到securityManager中
     *
     * @param authRealm
     * @return
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(authRealm);
        return defaultWebSecurityManager;
    }

    @Bean(name = "authRealm")
    public AuthRealm getAuthRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        //设置密码规则
        authRealm.setCredentialsMatcher(matcher);
        //设置权限缓存
        authRealm.setCacheManager(new MemoryConstrainedCacheManager());
        return authRealm;
    }

    /**
     * 设置密码的加密方式和加盐次数
     *
     * @return
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法，也可以使用rs1
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""))
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }

    /**
     * springboot shiro开启注释  记得添加   spring-boot-starter-aop   这个依赖
     * 开启这个配置后只需要在controller中的方法前加上注解 @RequiresPermissions("userInfo:test")
     * 开启这个还需要在 application.properties 中补充 spring.aop.proxy-target-class=true
     * 或者在这个类进行进行装配
     *     @Bean
     *     public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
     *         //代理类
     *         DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
     *         creator.setProxyTargetClass(true);
     *         return creator;
     *     }
     *   详细解答地址为         https://www.cnblogs.com/tuifeideyouran/p/7696055.html
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager securityManager) {
        //spring在对shiro进行处理时，使用的权限管理者为我们自定义的
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        //代理类
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);

        return creator;
    }
}
