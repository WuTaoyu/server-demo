
package com.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Created by WuTaoyu on 2018/2/8.
 */

@Component
public class JsonWebTokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

    public JsonWebTokenAuthenticationFilter() {
        // Don't throw exceptions if the header is missing
        this.setExceptionIfHeaderMissing(false);

        // This is the request header it will look for
        //从请求中读取这个header
        //由于filter加载早于Global读取配置文件，所以这个tokenHeader不能从Global中读取，需要先写入
        this.setPrincipalRequestHeader("Authorization");
    }

    @Override
    @Autowired
    public void setAuthenticationManager(
            AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}