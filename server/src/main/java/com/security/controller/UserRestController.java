package com.security.controller;

import com.result.ResultModel;
import com.security.annotation.CurrentJwtUser;
import com.security.jwt.JwtTokenUtil;
import com.security.model.dto.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WuTaoyu on 2018/2/8.
 */
@RestController
public class UserRestController {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResultModel getAuthenticatedUser(@CurrentJwtUser JwtUser jwtUser) {
        return ResultModel.ok(jwtUser);
    }

}
