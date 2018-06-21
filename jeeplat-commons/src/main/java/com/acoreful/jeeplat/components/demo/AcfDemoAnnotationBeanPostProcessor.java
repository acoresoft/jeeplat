package com.acoreful.jeeplat.components.demo;

import java.util.Arrays;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.StringValueResolver;

import com.acoreful.jeeplat.commons.api.AcfDemoServiceApi;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AcfDemoAnnotationBeanPostProcessor implements MergedBeanDefinitionPostProcessor,  PriorityOrdered,
		EmbeddedValueResolverAware, BeanNameAware, BeanFactoryAware, SmartInitializingSingleton, InitializingBean,DisposableBean {
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
		log.info("==================destroy:{}",beanName);
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
		//this.finishRegistration();
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
					beanFactory.registerSingleton(targetClass.getName(), new AcfDemoServiceApi());
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		//Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
		return bean;
	}

	@Override
	public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("=======================applicationContext:{}",applicationContext);
		this.finishRegistration();
		log.info("-------embeddedValueResolver:{}",embeddedValueResolver);
	}

}
