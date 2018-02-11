
package com.security.jwt;

import com.exception.BadRequestException;
import com.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * Created by WuTaoyu on 2018/2/8.
 */

@Component
public class JsonWebTokenAuthenticationProvider implements AuthenticationProvider {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        Authentication authenticatedUser = null;
        // Only process the PreAuthenticatedAuthenticationToken
        if (authentication.getClass().
                isAssignableFrom(PreAuthenticatedAuthenticationToken.class)
                && authentication.getPrincipal() != null) {
            String tokenRequestHeader = (String) authentication.getPrincipal();
            String username = null;
            String authToken = null;
            if (tokenRequestHeader != null && tokenRequestHeader.startsWith("Bearer_")) {
                authToken = tokenRequestHeader.substring(7);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(authToken);
                } catch (IllegalArgumentException e) {
                    logger.error("an error occured during getting username from token");
                    throw new NotFoundException(HttpStatus.NOT_ACCEPTABLE.value(),"无法从token中获取用户名");
                } catch (ExpiredJwtException e) {
                    logger.warn("the token is expired and not valid anymore");
                    throw new BadRequestException(HttpStatus.UNAUTHORIZED.value(),"token已过期，请重新登录");
                }
//                token可能被修改
                catch (SignatureException e){
                    logger.warn(e.getMessage());
                    throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"非法的token");
                }
            } else {
                //没有获取到bearer开头的header，例如登录
                logger.warn("couldn't find bearer string, will ignore the header");
            }
            logger.info("checking authentication for user " + username);
            if (username != null ) {

                // It is not compelling necessary to load the use details from the database. You could also store the information
                // in the token and read it from it. It's up to you ;)
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
                // the database compellingly. Again it's up to you ;)
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    PreAuthenticatedAuthenticationToken preAuthenticatedAuthenticationToken = new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    logger.info("authenticated user " + username + ", setting security context");
                    authenticatedUser = preAuthenticatedAuthenticationToken;
                }
            }
        } else {
            // It is already a JsonWebTokenAuthentication
            authenticatedUser = authentication;
        }
        return authenticatedUser;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return
                authentication.isAssignableFrom(
                        PreAuthenticatedAuthenticationToken.class);
    }

}
