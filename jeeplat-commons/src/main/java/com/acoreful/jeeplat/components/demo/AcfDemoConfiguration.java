package com.acoreful.jeeplat.components.demo;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class AcfDemoConfiguration {

	@Bean(name = "acfDemoAnnotationProcessor")
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	public AcfDemoAnnotationBeanPostProcessor acfDemoAnnotationProcessor() {
		return new AcfDemoAnnotationBeanPostProcessor();
	}

}