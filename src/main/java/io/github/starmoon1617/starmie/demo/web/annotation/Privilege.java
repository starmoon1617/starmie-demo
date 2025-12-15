/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import io.github.starmoon1617.starmie.demo.common.enums.PrivilegeType;

/**
 * 权限
 * 
 * @date 2023-12-19
 * @author Nathan Liao
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Inherited
public @interface Privilege {

    /**
     * 权限类型, 默认最高权限
     * 
     * @return
     */
    PrivilegeType value() default PrivilegeType.D;
}
