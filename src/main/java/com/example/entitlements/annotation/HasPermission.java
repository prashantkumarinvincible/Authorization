package com.example.entitlements.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasPermission {
    String value();
    String parameter() default "";
    String parameterType() default "";
}
