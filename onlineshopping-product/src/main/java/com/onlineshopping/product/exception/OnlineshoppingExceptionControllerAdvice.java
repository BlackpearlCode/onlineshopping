package com.onlineshopping.product.exception;




import com.onlineshopping.common.utils.BizCodeEnum;
import com.onlineshopping.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice(basePackages = "com.onlineshopping.product.controller")
public class OnlineshoppingExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验有异常，异常类型：{}",e.getMessage(),e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap=new HashMap<>();
        bindingResult.getFieldErrors().forEach((item)->{
            errorMap.put(item.getField(),item.getDefaultMessage());
        });
        return Result.r(BizCodeEnum.VAILD_EXCEPTION.getCode(),BizCodeEnum.VAILD_EXCEPTION.getMsg()).put("data",errorMap);

    }
    @ExceptionHandler(value = Throwable.class)
    public Result handleException(Throwable throwable){
        log.error("错误：",throwable);
        return Result.r(BizCodeEnum.UNKNOW_EXCEPION.getCode(),BizCodeEnum.UNKNOW_EXCEPION.getMsg());
    }
}
