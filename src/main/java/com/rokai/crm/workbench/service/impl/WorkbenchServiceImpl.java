package com.rokai.crm.workbench.service.impl;

import com.rokai.crm.settings.dao.UserMapper;
import com.rokai.crm.workbench.exception.WorkbenchException;
import com.rokai.crm.workbench.service.WorkbenchService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkbenchServiceImpl implements WorkbenchService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String updatePwd(String newPwd, String id) {
        int updateStatus = userMapper.updatePwd(newPwd, id);

        if (updateStatus == 1) {
            return "修改密码成功！请重新登录。";
        } else {
            throw new WorkbenchException("S2001", "Error 修改密码失败！");
        }
    }


}
