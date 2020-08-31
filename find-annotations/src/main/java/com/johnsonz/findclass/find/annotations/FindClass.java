package com.johnsonz.findclass.find.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface FindClass {

    String classPath() default "";

    boolean allowThrowExpection() default false;

}