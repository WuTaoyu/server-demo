package com.exception;

/**
 * Created by Wutaoyu on 2016/8/1.
 */
/**
 * 自定义的BadRequestException
 */
public class NotFoundException extends BasicException {

    public NotFoundException(int code, String message) {
        super(code, message);
    }
}