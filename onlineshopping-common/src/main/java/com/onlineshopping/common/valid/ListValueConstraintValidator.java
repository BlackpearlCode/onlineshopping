package com.onlineshopping.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义参数的校验注解的校验器
 * 参数1（ListValue）：指定注解
 * 参数2（Integer）：校验什么类型的数据
 */
public class ListValueConstraintValidator implements ConstraintValidator<com.onlineshopping.common.valid.ListValue,Integer> {
    private Set<Integer> set=new HashSet<>();

    //初始化方法
    @Override
    public void initialize(com.onlineshopping.common.valid.ListValue constraintAnnotation) {
        //给定的值必须是数组中的值
        int[] values = constraintAnnotation.values();
        for(int val:values){
            set.add( val);
        }
    }

    /**
     * 判断校验是否成功
     * @param value 需要校验的值
     * @param constraintValidatorContext 校验的上下文环境信息
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        //判断value是否在set集合中，如果在则校验成功；否则校验失败
        if(set.contains(value)){
            return true;
        }else{
            return false;
        }

    }
}
