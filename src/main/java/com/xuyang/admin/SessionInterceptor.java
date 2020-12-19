package com.xuyang.admin;

import com.xuyang.admin.utils.MessageOut;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {

            // System.out.println("crate->"+ session.getCreationTime() + "last"
            // +session.getLastAccessedTime() + "(" + session.getMaxInactiveInterval() +")");
            return true;
        } else {



            PrintWriter printWriter = response.getWriter();
            printWriter.write(MessageOut.sessionOut().toJSONString());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
