package com.mars.ecsheet.auth;

import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @FileName: UrlSecurityInterceptor.java
 * @Description: UrlSecurityInterceptor.java类说明
 * @Author: tao.shi
 * @Date: 2021/8/1 13:40
 */
public class UrlSecurityInterceptor implements HandlerInterceptor {

	static Log log = LogFactory.get(UrlSecurityInterceptor.class);

	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(this.hasPermission(request,handler)) {
			return true;
		}
		response.sendError(HttpStatus.FORBIDDEN.value(),"无权限");

		return false;
	}

	/**
	 * 判断用户是否有权限
	 *  1 对于所有请求，默认都需要权限才可以访问
	 *  2 判断方法上是否有注解，如果有注解，此请求，只允许管理员访问
	 * @param handler
	 * @return
	 */
	private boolean hasPermission(HttpServletRequest request, Object handler) {
		// 先判断ip是否为允许访问的客户端IP
		String requestIp = HttpUtil.getClientIP(request);
		Set<String> ips = authService.getRequestSet();
		String uri = request.getRequestURI();
		if( !ips.contains(requestIp) ) {
			log.error("常规请求被拦截，request ip{}; 请求路径：{}", requestIp,uri);
			return false;
		}

		//判断是否为管理员请求，如果是，需要判断是否有权限
		if(handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod)handler;
			Check check = handlerMethod.getMethod().getAnnotation(Check.class);
			if(check != null) {
				// 如果标记了注解，需要判断其是否有管理员权限，没有则返回false
				Set<String> adminIps = authService.getAdminSet();
				if(!adminIps.contains(requestIp)) {
					log.error("管理员操作请求被拦截，request ip{}; 请求路径：{}", requestIp,uri);
					return false;
				}
			}
			// 如果没有标记注解，直接放行
		}

		return true;
	}



}
