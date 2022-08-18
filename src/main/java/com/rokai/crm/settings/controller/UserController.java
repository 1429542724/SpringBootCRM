package com.rokai.crm.settings.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.exception.UserException;
import com.rokai.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 进入crm系统登陆界面，
     *
     * @return 返回登录界面视图路径，
     */
    @RequestMapping("/")
    public String loginPage() {
        return "/settings/qx/user/login";
    }

    /**
     * 进入crm系统主界面，
     *
     * @return
     */
    @RequestMapping("/workbench/")
    public String workbenchPage() {
        return "/workbench/index";
    }

    /**
     * 用户登录功能，
     *
     * @param loginAct 用户账号，
     * @param loginPwd 用户密码。
     * @param remember 是否记住账号和密码
     * @return 视图和信息，
     */
    @PostMapping("/user/login.do")
    public void userLogin(HttpServletRequest request, HttpServletResponse response,
                          String loginAct, String loginPwd, boolean remember) throws IOException, ParseException {

        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        map.put("userIP", request.getRemoteAddr());

        if ("".equals(loginAct)) {
            throw new UserException("C1001", "Error 账号不能为空！");
        } else if ("".equals(loginPwd)) {
            throw new UserException("C1001", "Error 密码不能为空！");
        }

        User user = userService.userLogin(map);

        if (remember) {
            Cookie userAct = new Cookie("userAct", user.getLoginAct());
            userAct.setMaxAge(60 * 60 * 24 * 10);
            userAct.setPath("/crm");
            response.addCookie(userAct);
            Cookie userPwd = new Cookie("userPwd", user.getLoginPwd());
            userPwd.setMaxAge(60 * 60 * 24 * 10);
            userPwd.setPath("/crm");
            response.addCookie(userPwd);

        } else {
            Cookie userAct = new Cookie("userAct", "");
            userAct.setMaxAge(0);
            userAct.setPath("/crm");
            response.addCookie(userAct);
            Cookie userPwd = new Cookie("userPwd", "");
            userPwd.setMaxAge(0);
            userPwd.setPath("/crm");
            response.addCookie(userPwd);
        }

        request.getSession().setAttribute("userInfo", user);
        response.getWriter().write("true");
    }

}
