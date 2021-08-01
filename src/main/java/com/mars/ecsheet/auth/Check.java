package com.mars.ecsheet.auth;

import java.lang.annotation.*;

/**
 * @FileName: Check.java
 * @Description: 注解，用于url权限拦截
 * @Author: tao.shi
 * @Date: 2021/8/1 13:31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Check {

	boolean admin() default false;


}
