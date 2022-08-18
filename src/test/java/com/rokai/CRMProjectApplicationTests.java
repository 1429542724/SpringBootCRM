package com.rokai;

import com.alibaba.fastjson.JSON;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
class CRMProjectApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {

        User user = userService.selectByPrimaryKey("06f5fc056eac41558a964f96daa7f27c");
        System.out.println("查询到的数据：" + user);
    }

    @Test
    void testUserLogin() throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", "admin");
        map.put("loginPwd", "admin123");

        User user = userService.userLogin(map);
        System.out.println("查询到的数据：" + user);

    }

    @Test
    void msg() throws ParseException {
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", "admin");
        map.put("loginPwd", "admin123");
        map.put("userIP", "127.0.0.1");

        User user = userService.userLogin(map);
        System.out.println("转换前：" + user);

        String userJson = JSON.toJSONString(user);
        System.out.println("转换后：" + userJson);

    }

    @Test
    void saveUser() {

        User user = new User();
        user.setId(String.valueOf(UUID.randomUUID()).replace("-", ""));
        user.setLoginAct("rokai");
        user.setName("罗凯");
        user.setLoginPwd("12345");
        user.setEmail("rokai@qq.com");
        user.setExpireTime("2022-12-31 00:00:00");
        user.setLockState("1");
        user.setDeptno("Y101");
        user.setAllowIps("127.0.0.1");
        user.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        user.setCreateBy("管理员");

        int insertStatus = userService.insert(user);
        if (insertStatus == 1) {
            System.out.println("System:\t" + "创建用户成功~");
        } else {
            System.out.println("System:\t" + "创建失败成功！");
        }

    }



}
