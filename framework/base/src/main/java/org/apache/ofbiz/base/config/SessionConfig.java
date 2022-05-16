package org.apache.ofbiz.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableRedisHttpSession(flushMode=FlushMode.IMMEDIATE)
@EnableWebMvc
public class SessionConfig {
	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		CookieHttpSessionIdResolver defaultHttpSessionIdResolver = new CookieHttpSessionIdResolver();
//		RequestParamSessionIdResolver defaultHttpSessionIdResolver = new RequestParamSessionIdResolver("session");
		defaultHttpSessionIdResolver.setCookieSerializer(new RootCookieSerializer());
		return defaultHttpSessionIdResolver;
	}
	
}
