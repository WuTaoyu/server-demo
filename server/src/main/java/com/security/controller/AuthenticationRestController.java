package com.security.controller;

import com.exception.BadRequestException;
import com.result.ResultModel;
import com.security.annotation.CurrentJwtUser;
import com.security.annotation.CurrentToken;
import com.security.jwt.JwtTokenUtil;
import com.security.model.dto.JwtAuthenticationRequest;
import com.security.model.dto.JwtAuthenticationResponse;
import com.security.model.dto.JwtUser;
import com.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by WuTaoyu on 2018/2/8.
 */
@RestController
public class AuthenticationRestController {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResultModel createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, Device device) {

        String token = null;

        boolean authenticated =userService.authenticateUser(authenticationRequest.getUsername(),authenticationRequest.getPassword());

        if(authenticated) {
            // Reload password post-security so we can generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails, device);
        }
        else {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED.value(),"账号或密码错误");
        }

        // Return the token
        return ResultModel.ok(new JwtAuthenticationResponse(token));
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResultModel refreshAndGetAuthenticationToken(@CurrentJwtUser JwtUser jwtUser, @CurrentToken String token) {

        if (jwtTokenUtil.canTokenBeRefreshed(token, jwtUser.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResultModel.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            throw new BadRequestException(HttpStatus.BAD_REQUEST.value(),"无法刷新token");
        }
    }

}
