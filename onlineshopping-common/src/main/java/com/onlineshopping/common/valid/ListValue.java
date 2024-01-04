package com.onlineshopping.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {com.onlineshopping.common.valid.ListValueConstraintValidator.class})//ListValueConstraintValidator.class类是自定义参数的校验注解的校验器
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 自定义的参数校验注解
 */

public @interface ListValue {
    String message() default "{com.onlineshopping.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int[] values() default {};;
}
