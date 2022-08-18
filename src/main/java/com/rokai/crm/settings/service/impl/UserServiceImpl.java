package com.rokai.crm.settings.service.impl;

import com.rokai.crm.settings.dao.UserMapper;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.exception.UserException;
import com.rokai.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    //后台专用，
    @Override
    public User selectByPrimaryKey(String id) {

        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public User userLogin(Map<String, Object> map) throws ParseException {

        User user = userMapper.userLogin(map);

        if (user != null) {
            if ("1".equals(user.getLockState())) {
                if (user.getAllowIps().contains((String) map.get("userIP"))) {
                    Date userDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(user.getExpireTime());
                    if (userDate.getTime() > new Date().getTime()) {
                        return user;
                    }
                    throw new UserException("S1001", "Error 账号已失效！");
                }
                throw new UserException("S1001", "Error 你的IP受限！");
            }
            throw new UserException("S1001", "Error 账号已锁定！");
        }
        throw new UserException("S1001", "Error 用户/密码错误！");
    }

    //后台专用，
    @Override
    public int insert(User user) {
        int insertStatus = userMapper.insert(user);
        return insertStatus;
    }


}
