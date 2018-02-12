package com.exception;

import com.result.ResultModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by WuTaoyu on 2018/2/9.
 */

/**
 * 统一异常处理
 * 异常捕捉判断按照声明的顺序由上至下
 * 声明父类异常之后，抛出子类异常也会被捕捉，所以要管理好声明的顺序
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = BasicException.class)
    public ResultModel basicExceptionErrorHandler(BasicException e){
        return ResultModel.error(e.getCode(),e.getMessage());
    }

    /**
     * 无权限访问异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResultModel accessDeniedExceptionErrorHandler(AccessDeniedException e){
        return ResultModel.error(HttpStatus.FORBIDDEN.value(),e.getMessage());
    }

    /**
     * RuntimeException异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultModel runtimeExceptionErrorHandler(RuntimeException e){
        logger.error(e.getMessage(),e);
        return ResultModel.error(HttpStatus.SERVICE_UNAVAILABLE.value(),e.getMessage());
    }

    /**
     * 预料之外的异常处理
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResultModel defaultErrorHandler(HttpServletResponse req, Exception e){
        logger.error(e.getMessage(),e);
        return ResultModel.error(req.getStatus(),e.getMessage());
    }

}
