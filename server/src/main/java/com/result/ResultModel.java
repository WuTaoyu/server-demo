package com.result;

/**
 * Created by WuTaoyu on 2018/2/10.
 */
/**
 * 自定义返回结果
 */
public class ResultModel {

    public static final Integer OK = 200;
    public static final Integer ERROR = 400;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    /**
     * 正常返回
     */
    public static ResultModel ok() {
        return new ResultModel(OK ,"success");
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(OK , "success", content);
    }

    /**
     * 异常返回
     */
    public static ResultModel error() {
        return new ResultModel(ERROR, "failed" );
    }

    public static ResultModel error(int code, String message) {
        return new ResultModel(code, message);
    }

    public static ResultModel error(int code, String message, Object content) {
        return new ResultModel(code, message, content);
    }

}