package com.xuyang.admin.annotation;

import java.lang.annotation.*;

/**
 * 登录注解
 * @author chengtinghua
 * @Date 2017/6/22 16:17
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginRequired {
}
