package com.ssafy.happyhouse.config;

import java.util.Arrays;
import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.happyhouse.interceptor.ConfirmInterceptor;


@Configuration
@EnableAspectJAutoProxy
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
public class WebMvcConfiguration implements WebMvcConfigurer{
	
	@Autowired
	private ConfirmInterceptor confirm;
	
////	private final List<String> patterns = Arrays.asList("/happyhouse/*", "/admin/*", "/user/list");
//	private final List<String> patterns = Arrays.asList("/happyhouse/*", "/user/list");
	private final List<String> patterns = Arrays.asList("/happyhouse/*");

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirm).addPathPatterns(patterns);
	}


}
