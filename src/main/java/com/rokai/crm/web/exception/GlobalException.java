package com.rokai.crm.web.exception;

import com.rokai.crm.settings.exception.UserException;
import com.rokai.crm.workbench.exception.WorkbenchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = UserException.class)
    @ResponseBody
    public String userLoginException(HttpServletRequest request, UserException e) {
        return e.getMes();
    }

    @ExceptionHandler(value = WorkbenchException.class)
    @ResponseBody
    public Map<String, Object> updatePwdException(HttpServletRequest request, WorkbenchException e) {

        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("message", e.getMessage());

        return map;
    }

}
