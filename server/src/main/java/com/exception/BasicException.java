package com.exception;

/**
 * Created by WuTaoyu on 2018/2/9.
 */

/**
 * 自定义异常的基类
 * 继承自RuntimeException，可以在运行时抛出
 */
public class BasicException extends RuntimeException {

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BasicException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
