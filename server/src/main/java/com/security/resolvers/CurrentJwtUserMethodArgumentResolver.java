package com.security.resolvers;

import com.common.Global;
import com.security.annotation.CurrentJwtUser;
import com.security.jwt.JwtTokenUtil;
import com.security.model.dto.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by WuTaoyu on 2018/2/11.
 */
/**
 * 增加方法注入，将含有CurrentJwtUser注解的方法参数注入当前登录用户
 * @see com.security.annotation.CurrentJwtUser
 */
@Component
public class CurrentJwtUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是JwtUser并且有CurrentJwtUser注解则支持
        if (parameter.getParameterType().isAssignableFrom(JwtUser.class) &&
                parameter.hasParameterAnnotation(CurrentJwtUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //取出鉴权时存入的登录用户Authorization
        //String currentAuthorization = (String) webRequest.getAttribute(Global.tokenHeader, RequestAttributes.SCOPE_REQUEST);
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String currentAuthorization = servletRequest.getHeader(Global.tokenHeader);
        if (currentAuthorization != null) {
            //从数据库中查询并返回
            String token = currentAuthorization.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
            return jwtUser;
        }
        throw new MissingServletRequestPartException(Global.tokenHeader);
    }
}
