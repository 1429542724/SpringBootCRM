package com.rokai.crm.web.filter;

import com.rokai.crm.settings.domain.User;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String[] staticResource = {".css", ".js", ".jpg", ".png", ".ico"};
        User user = (User) request.getSession().getAttribute("userInfo");
        String uriPath = request.getServletPath();

        boolean flag = true;
        for (String resource : staticResource) {
            if (uriPath.endsWith(resource)) {
                filterChain.doFilter(request, response);
                flag = false;
                break;
            }
        }

        if (flag) {
            if ("/".equals(uriPath) | "/user/login.do".equals(uriPath)) {
                filterChain.doFilter(request, response);
            } else if (user != null) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        }

    }


}
