package com.mars.ecsheet.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @FileName: InterceptorConfig.java
 * @Description: InterceptorConfig.java类说明
 * @Author: tao.shi
 * @Date: 2021/8/1 14:16
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

	@Bean
	public HandlerInterceptor getUrlSecurityInterceptor() {
		return new UrlSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getUrlSecurityInterceptor()).addPathPatterns("/**");
	}

}
