package com.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by WuTaoyu on 2018/2/6.
 */
@Configuration
public class Global {

    public static String tokenHeader;

    public static String secret;

    public static Long expiration;

    @Value("${jwt.header}")
    public void setTokenHeader(String tokenHeader){
        Global.tokenHeader = tokenHeader;
    }

    @Value("${jwt.secret}")
    public void setSecret(String secret){
        Global.secret = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long expiration){
        Global.expiration = expiration;
    }
}
