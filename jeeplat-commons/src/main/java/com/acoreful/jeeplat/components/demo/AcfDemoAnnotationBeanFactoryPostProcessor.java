package com.acoreful.jeeplat.components.demo;

import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringValueResolver;

import com.acoreful.jeeplat.commons.api.AcfDemoServiceApi;
import com.acoreful.jeeplat.commons.spring.ClassScaner;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class AcfDemoAnnotationBeanFactoryPostProcessor implements BeanFactoryPostProcessor,EmbeddedValueResolverAware {

	private String basePackages;
	private StringValueResolver resolver;
	
	
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		Set<Class<?>> clazzs=ClassScaner.scan(basePackages, AcfDemoApi.class);
		if(CollectionUtils.isEmpty(clazzs)) {
			return ;
		}
		clazzs.stream().forEach(clazz->{
			Class<?> targetClass=clazz;
			AcfDemoApi acfDemoApi=targetClass.getAnnotation(AcfDemoApi.class);
			if(acfDemoApi!=null) {
				String url=this.resolver.resolveStringValue(acfDemoApi.serviceUrl());
				log.info("---------------{}",url);
				beanFactory.registerSingleton(targetClass.getName(), new AcfDemoServiceApi());
			}
		});
		Arrays.asList(beanFactory.getBeanDefinitionNames()).forEach(o->{
			log.info("---------------{}",o);
		});
		AcfDemoServiceApi api=beanFactory.getBean(AcfDemoServiceApi.class);
		System.out.println(api);
		beanFactory.getBeanNamesIterator().forEachRemaining(o->{
			log.info("==============={}",o);
		});
		
		log.info("====------------=========={}",3);

	}
	
	public void setBasePackages(String basePackages) {
		this.basePackages = basePackages;
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.resolver=resolver;
	}
}
