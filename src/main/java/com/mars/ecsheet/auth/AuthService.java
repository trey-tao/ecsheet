package com.mars.ecsheet.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @FileName: AuthService.java
 * @Description: AuthService.java类说明
 * @Author: tao.shi
 * @Date: 2021/8/1 13:56
 */
@Component
public class AuthService {

	@Value("${auth.request}")
	private String requestIp;

	@Value("${auth.admin}")
	private String adminRequestIp;

	/**
	 * 可以访问系统的客户端ip集合
	 */
	private static Set<String> requestSet;

	/**
	 * 可以进行管理员操作的ip集合
	 */
	private static Set<String> adminSet;

	@PostConstruct
	private void init() {
		requestSet = Stream.of(requestIp.split(","))
				.collect(Collectors.toSet());

		adminSet = Stream.of(adminRequestIp.split(","))
				.collect(Collectors.toSet());
	}

	public Set<String> getRequestSet() {
		return requestSet;
	}

	public Set<String> getAdminSet() {
		return adminSet;
	}


}
