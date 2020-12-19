package com.xuyang.admin.interceptor;

import com.xuyang.admin.annotation.LoginRequired;
import com.xuyang.admin.utils.RedisUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) handler;
            LoginRequired loginRequired = getClassOrMethodAnnotationByClassFirst(method);
            if(null != loginRequired){
                if(redisUtil == null){
                    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    redisUtil = (RedisUtil) factory.getBean("redisUtil");
                }
                String token = "";
                if(null != request.getParameter("token")){
                    token = request.getParameter("token").toString();
                }
                boolean exists = redisUtil.exists(token);
                if(!exists){
                    throw new RuntimeException();
                }
            }
        }
        return super.preHandle(request,response,handler);
    }
    /**
     * 查看方法是否有登录注解
     * @param method
     * @return
     */
    private LoginRequired getClassOrMethodAnnotationByClassFirst(HandlerMethod method) {
        LoginRequired annotation = method.getBeanType().getAnnotation(LoginRequired.class);
        if (null == annotation) {
            annotation = method.getMethodAnnotation(LoginRequired.class);
        }
        return annotation;
    }


}
