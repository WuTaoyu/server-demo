package com.security.service;

/**
 * Created by WuTaoyu on 2018/2/11.
 */
public interface UserService {

    boolean authenticateUser(String username, String password);
}
