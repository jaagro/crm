package com.jaagro.crm.web.config;

import com.jaagro.utils.BaseResponse;
import com.jaagro.utils.ResponseStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author tony
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResponse javaExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResponse.errorInstance(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = NullPointerException.class)
    public BaseResponse authorizationExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(value=MethodArgumentNotValidException.class)
    @ResponseBody
    private BaseResponse methodArgumentNotValidExceptionHandler(Exception ex){
        log.info("methodArgumentNotValidExceptionHandler",ex);
        //处理Validation异常
        StringBuffer errorMsg = new StringBuffer();
        MethodArgumentNotValidException c = (MethodArgumentNotValidException) ex;
        List<ObjectError> errors = c.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            errorMsg.append(error.getDefaultMessage()).append(";");
        }
        return BaseResponse.errorInstance(ResponseStatusCode.QUERY_DATA_ERROR.getCode(), errorMsg.toString());
    }
}
