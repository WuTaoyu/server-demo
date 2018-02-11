package com.security.service.impl;

import com.security.model.domain.User;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by WuTaoyu on 2018/2/11.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticateUser(String username, String password) {
        boolean authenticated = false;
        User user = userRepository.findByUsername(username);
        if (user != null) {
            //前者为未加密的密码，后者为加密后的密码
            // 由于BCryptPasswordEncoder加密算法本身有随机数，密码经过加密后的字符串是不一样的
            //所以需要用其matches(CharSequence rawPassword, String encodedPassword)方法去比较
            if (passwordEncoder.matches(password,user.getPassword())) {
                authenticated=true;
            }
        }
        return authenticated;
    }
}
