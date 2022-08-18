package com.rokai.crm.settings.service;

import com.rokai.crm.settings.domain.User;

import java.text.ParseException;
import java.util.Map;

public interface UserService {

    User selectByPrimaryKey(String id);

    User userLogin(Map<String,Object> map) throws ParseException;

    int insert(User user);
}
