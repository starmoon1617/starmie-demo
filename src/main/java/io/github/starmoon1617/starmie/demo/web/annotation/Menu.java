/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.web.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 菜单
 * 
 * @date 2023-12-11
 * @author Nathan Liao
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface Menu {

    String value() default "";
}
