package com.acoreful.jeeplat.commons.init;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.collect.ImmutableMap;

public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {
	private static Logger logger = LoggerFactory.getLogger(ApplicationStartListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		ApplicationContext cxt = contextRefreshedEvent.getApplicationContext();
		ApplicationContext parentCxt = cxt.getParent();
		if(parentCxt==null) {
			logger.info("----------ActiveProfiles:{}", ToStringBuilder.reflectionToString(cxt.getEnvironment().getActiveProfiles(),
					ToStringStyle.JSON_STYLE));
			this.printSystemEnvProperties();
			return ;
		}
		logger.info("----------ActiveProfiles:{}", ToStringBuilder.reflectionToString(parentCxt.getEnvironment().getActiveProfiles(),
				ToStringStyle.JSON_STYLE));
		System.out.println("我的父容器为：" + parentCxt);
		System.out.println("初始化时我被调用了。");
	}

	private void printSystemEnvProperties() {
		ImmutableMap.builder().put("操作系统", SystemUtils.OS_NAME).put("操作系统版本", SystemUtils.OS_VERSION)
				.put("操作系统架构", SystemUtils.OS_ARCH).put("文件编码", SystemUtils.FILE_ENCODING)
				.put("当前用户", SystemUtils.USER_NAME).put("当前用户目录", SystemUtils.USER_HOME)
				.put("当前用户时区", SystemUtils.USER_TIMEZONE).put("Java", SystemUtils.JAVA_RUNTIME_NAME)
				.put("Java供应商", SystemUtils.JAVA_VENDOR).put("Java版本", SystemUtils.JAVA_RUNTIME_VERSION)
				.put("Java虚拟机", SystemUtils.JAVA_VM_NAME).put("Java虚拟机供应商", SystemUtils.JAVA_VM_VENDOR)
				.put("Java虚拟机版本", SystemUtils.JAVA_VM_VERSION).build().forEach((k, v) -> logger.info("{} - {}", k, v));
	}
}