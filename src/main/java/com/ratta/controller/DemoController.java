package com.ratta.controller;

import com.ratta.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 刘明
 */
@Slf4j
@RestController
public class DemoController {

    /**
     * 登陆页
     *
     * @return
     */
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /**
     * 首页
     *
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public ModelAndView logout() {
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            subject.logout();
        }
        return new ModelAndView("login");
    }

    /**
     * 管理员页面接口
     *
     * @return
     */
    @RequestMapping("/admin")
    public ModelAndView admin() {
        return new ModelAndView("admin");
    }

    /**
     * 修改接口
     *
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView update() {
        return new ModelAndView("update");
    }

    /**
     * 没有权限跳转接口
     *
     * @return
     */
    @RequestMapping("/noPermission")
    public ModelAndView noPermission() {
        return new ModelAndView("noPermission");
    }

    /**
     * 删除接口
     *
     * @return
     */
    @RequestMapping("/delete")
    //这个注解需要开启aop的proxy-target-class为true才会生效
    //要求subject中必须同时含有delete的权限才能执行方法delete()。否则抛出异常AuthorizationException
    @RequiresPermissions({"delete"})
    public ModelAndView delete() {
        return new ModelAndView("delete");
    }

    /**
     * 登陆认证接口
     *
     * @param username
     * @param password
     * @param model
     * @return
     */
    @RequestMapping("/loginUser")
    public ModelAndView loginUser(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //获取在AuthRealm中SimpleAuthenticationInfo()第一个参数信息
            User user = (User) subject.getPrincipal();
            model.addAttribute("user", user);
            return new ModelAndView("index", "index", model);
        } catch (UnknownAccountException e) {
            log.error("用户名不存在");
            return new ModelAndView("login");
        } catch (IncorrectCredentialsException e) {
            log.error("密码错误");
            return new ModelAndView("login");
        }
    }
}
