package com.security.resolvers;

import com.common.Global;
import com.security.annotation.CurrentJwtUser;
import com.security.annotation.CurrentToken;
import com.security.jwt.JwtTokenUtil;
import com.security.model.dto.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WuTaoyu on 2018/2/11.
 */
/**
 * 增加方法注入，将含有CurrentToken注解的方法参数注入当前登录用户的token
 * @see com.security.annotation.CurrentJwtUser
 */
@Component
public class CurrentTokenMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //如果参数类型是Sting并且有CurrentToken注解则支持
        if (parameter.getParameterType().isAssignableFrom(String.class) &&
                parameter.hasParameterAnnotation(CurrentToken.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

//        String currentAuthorization = (String) webRequest.getAttribute(Global.tokenHeader, RequestAttributes.SCOPE_REQUEST);
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String currentAuthorization = servletRequest.getHeader(Global.tokenHeader);
        if (currentAuthorization != null) {
            String token = currentAuthorization.substring(7);
            return token;
        }
        throw new MissingServletRequestPartException(Global.tokenHeader);
    }
}
