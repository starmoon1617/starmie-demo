/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.starmoon1617.starmie.core.criterion.enums.OperatorType;

/**
 * 附加参数的注解
 * 
 * @date 2023-12-22
 * @author Nathan Liao
 */
@Target({ METHOD })
@Retention(RUNTIME)
public @interface ExtraParam {

    /**
     * 参数的名称
     * 
     * @return
     */
    String field() default "";

    /**
     * 参数的值
     * 
     * @return
     */
    String value() default "";

    /**
     * 类型
     */
    String type();

    /**
     * 操作符
     */
    OperatorType operator();
}
