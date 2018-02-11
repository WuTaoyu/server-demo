package com.config;

import com.security.jwt.JwtTokenUtil;
import com.security.resolvers.CurrentJwtUserMethodArgumentResolver;
import com.security.resolvers.CurrentTokenMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by WuTaoyu on 2018/2/6.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private CurrentJwtUserMethodArgumentResolver currentJwtUserMethodArgumentResolver;

    @Autowired
    private CurrentTokenMethodArgumentResolver currentTokenMethodArgumentResolver;

    /**
     * 配置自定义resolvers的注入
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentJwtUserMethodArgumentResolver);
        argumentResolvers.add(currentTokenMethodArgumentResolver);
    }

}
