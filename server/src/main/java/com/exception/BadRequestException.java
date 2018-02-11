package com.exception;

/**
 * Created by Wutaoyu on 2016/8/1.
 */

/**
 * 自定义的BadRequestException
 */
public class BadRequestException extends BasicException {

    public BadRequestException(int code, String message) {
        super(code, message);
    }
}
