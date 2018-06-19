package com.acoreful.jeeplat.components.demo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AcfDemoApi {

	/**
	 * 调用的服务地址
	 * 
	 * @return
	 */
	String serviceUrl();
}