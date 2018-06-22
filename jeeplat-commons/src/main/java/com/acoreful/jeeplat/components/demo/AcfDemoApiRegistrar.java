package com.acoreful.jeeplat.components.demo;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcfDemoApiRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableAcfDemo.class.getName());
		String basePackages = StringUtils.join((String[]) map.get("basePackages"), ",");
		log.info("[AcfDemoApi]basePackages:{}", basePackages);
		BeanDefinition beanDefinition = BeanDefinitionBuilder
				.genericBeanDefinition(AcfDemoAnnotationBeanFactoryPostProcessor.class)
				.addPropertyValue("basePackages", basePackages).getBeanDefinition();
		registry.registerBeanDefinition("acfDemoAnnotationBeanFactoryPostProcessor", beanDefinition);
	}

}
