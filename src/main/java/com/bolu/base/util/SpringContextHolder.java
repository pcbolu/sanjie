package com.bolu.base.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring IOC 上下文工具类
 * 2017-12-05 15:04:02
 * 
 * @author 0
 *
 */
public class SpringContextHolder implements ApplicationContextAware {

	/** 当前IOC */
	private static ApplicationContext applicationContext;

	/**
	 * 设置当前上下文环境，此方法由spring自动装配
	 * 2017-12-05 15:05:04
	 * 
	 * @param arg0
	 * @throws BeansException
	 */
	//@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
	}

	/**
	 * 从当前IOC获取bean
	 * 2017-12-05 15:06:19
	 * @param id
	 * @return
	 */
	public static Object getObject(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}
	
	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		return (T) applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 清除applicationContext静态变量
	 */
	public static void cleanApplicationContext() {
		applicationContext = null;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			System.out.println("applicaitonContext未注?,请在applicationContext.xml中定义SpringContextHolder");
			throw new IllegalStateException("applicaitonContext未注?,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}
