package ru.obelisk.cucmaxl.ftp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(value = {ElementType.TYPE})
public @interface TableName {
	String name();
	String catalog() default "";
	String schema() default "";
}