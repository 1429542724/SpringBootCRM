package com.rokai.crm.workbench.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.workbench.exception.WorkbenchException;
import com.rokai.crm.workbench.service.WorkbenchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WorkbenchController {

    @Resource
    private WorkbenchService workbenchService;

    /**
     * 进入市场活动界面，
     *
     * @return 返回市场活动地址，
     */
    @RequestMapping("/workbench/profile")
    public String profile() {
        return "/settings/index";
    }

    /**
     * 进入市场活动界面，
     *
     * @return 返回市场活动地址，
     */
    @RequestMapping("/workbench/activity")
    public String activity() {
        return "/workbench/activity/index";
    }

    /**
     * 安全退出CRM系统，
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/workbench/outAct.do")
    public void outAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //删除会话作用域用户数据信息，
        request.getSession().removeAttribute("userInfo");

        //删除用户浏览器Cookie信息，
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            Cookie cookieTwo = new Cookie(cookie.getName(), cookie.getValue());
            cookieTwo.setMaxAge(0);
            cookieTwo.setPath("/crm");
            response.addCookie(cookieTwo);
        }

        response.getWriter().write("true");
    }

    /**
     * 修改用户密码，
     *
     * @param request    当前请求作用域，
     * @param oldPwd     原密码，
     * @param newPwd     新密码，
     * @param confirmPwd 确认新密码，
     * @return 返回结果，
     * @throws WorkbenchException workbench中的异常，
     */
    @RequestMapping("/workbench/updatePwd.do")
    @ResponseBody
    public Map<String, Object> updateUserPwd(HttpServletRequest request, HttpServletResponse response,
                                             String oldPwd, String newPwd, String confirmPwd) throws WorkbenchException {
        if (newPwd.equals(confirmPwd)) {
            User userInfo = (User) request.getSession().getAttribute("userInfo");

            if (oldPwd.equals(userInfo.getLoginPwd())) {
                String message = workbenchService.updatePwd(newPwd, userInfo.getId());

                //删除用户浏览器Cookie信息，
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    Cookie cookieTwo = new Cookie(cookie.getName(), cookie.getValue());
                    cookieTwo.setMaxAge(0);
                    cookieTwo.setPath("/crm");
                    response.addCookie(cookieTwo);
                }

                Map<String, Object> map = new HashMap<>();
                map.put("status", true);
                map.put("message", message);

                return map;
            } else {
                throw new WorkbenchException("G202", "Error 您输入的原密码不正确！");
            }
        } else {
            throw new WorkbenchException("G201", "Error 两次输入的密码不匹配！");
        }

    }


}
