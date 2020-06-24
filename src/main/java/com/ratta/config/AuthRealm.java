package com.ratta.config;

import com.ratta.domain.ActiverUser;
import com.ratta.domain.Permission;
import com.ratta.domain.Role;
import com.ratta.domain.User;
import com.ratta.service.UserService;
import com.ratta.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 刘明
 */
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获取盐
     */
    @Value("${shiro.salt}")
    private String salt;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        List<String> permissionList = new ArrayList<>();
        Set<String> roleNameSet = new HashSet<>();
        //获取用户的角色
        Set<Role> roles = activerUser.getUser().getRoles();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                roleNameSet.add(role.getKey());
                //获取用户的权限
                Set<Permission> permissions = role.getPermissions();
                permissions.forEach(permission -> permissionList.add(permission.getKey()));
            });
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //返回的授权信息中设置角色
        info.addRoles(roleNameSet);
        //同时添加权限
        info.addStringPermissions(permissionList);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws
            AuthenticationException {

        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //根据用户名查询用户信息
        User user = userService.findByUserName(usernamePasswordToken.getUsername());
        if (null == user) {
            return null;
        }
        //生成token,返回前端
        String token = JwtUtil.createToken(user.getId(), user.getUserName());
        //用来装返回前端token和用户信息《token给前端，user用在授权》
        ActiverUser activerUser = new ActiverUser(token, user);
        //认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
        //盐也放进去
        //这样通过applicationContext-shiro.xml里配置的 HashedCredentialsMatcher 进行自动校验
        //我们这里使用AuthRealm
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPassword(), ByteSource.Util.bytes(salt), this.getClass().getSimpleName());
        return info;
    }
}
