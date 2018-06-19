package com.acoreful.jeeplat.components.demo;

import java.util.Arrays;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.NamedBeanHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.Ordered;
import org.springframework.util.StringValueResolver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcfDemoAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor, Ordered,
		EmbeddedValueResolverAware, BeanNameAware, BeanFactoryAware, SmartInitializingSingleton, DisposableBean {
	private StringValueResolver embeddedValueResolver;

	private String beanName;

	private BeanFactory beanFactory;

	private ApplicationContext applicationContext;

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;

	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		log.info("==================destroy");
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	@Override
	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		this.embeddedValueResolver = resolver;
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public void afterSingletonsInstantiated() {
		log.info("==================afterSingletonsInstantiated");
		this.finishRegistration();
	}

	private void finishRegistration() {
		if (this.beanFactory instanceof ConfigurableListableBeanFactory) {
			ConfigurableListableBeanFactory beanFactory = (ConfigurableListableBeanFactory) this.beanFactory;
			log.info("---------------");
			String clazz="com.acoreful.jeeplat.commons.api.AcfDemoServiceApi";
			Class<?> targetClass=null;
			try {
				targetClass = Class.forName(clazz);
				AcfDemoApi acfDemoApi=targetClass.getAnnotation(AcfDemoApi.class);
				if(acfDemoApi!=null) {
					beanFactory.registerSingleton(targetClass.getName(), targetClass);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Arrays.asList(beanFactory.getBeanDefinitionNames()).forEach(o->{
				log.info("---------------{}",o);
			});
			beanFactory.getBeanNamesIterator().forEachRemaining(o->{
				log.info("---------------{}",o);
			});
		}
		
	}

	/**
	 * @param schedulerType
	 * @param byName
	 * @return
	 */
	private <T> T resolveSchedulerBean(Class<T> schedulerType, boolean byName) {
		if (this.beanFactory instanceof AutowireCapableBeanFactory) {
			NamedBeanHolder<T> holder = ((AutowireCapableBeanFactory) this.beanFactory).resolveNamedBean(schedulerType);
			if (this.beanFactory instanceof ConfigurableBeanFactory) {
				((ConfigurableBeanFactory) this.beanFactory).registerDependentBean(holder.getBeanName(), this.beanName);
			}
			return holder.getBeanInstance();
		} else {
			return this.beanFactory.getBean(schedulerType);
		}
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
		return bean;
	}

	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

	}

}
