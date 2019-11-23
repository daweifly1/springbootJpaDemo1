package com.example.demo.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtils implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext;

    private static BeanDefinitionRegistry beanDefinitionRegistry;

    private static Binder binder;


    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
        beanDefinitionRegistry = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 注册bean
     *
     * @param beanId    所注册bean的id
     * @param className bean的className，
     */
    public static void registerBean(String beanId, String className) throws Exception {
        if (beanDefinitionRegistry == null) {
            throw new Exception("beanDefinitionRegistry is null");
        }
        // get the BeanDefinitionBuilder
        BeanDefinitionBuilder beanDefinitionBuilder =
                BeanDefinitionBuilder.genericBeanDefinition(className);
        // get the BeanDefinition
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // register the bean
        beanDefinitionRegistry.registerBeanDefinition(beanId, beanDefinition);
    }

    /**
     * 移除bean
     *
     * @param beanId bean的id
     */
    public static void unregisterBean(String beanId) throws Exception {
        if (beanDefinitionRegistry == null)
            throw new Exception("beanDefinitionRegistry is null");
        beanDefinitionRegistry.removeBeanDefinition(beanId);
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(name);
        return null;
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(clazz);
        return null;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        if (getApplicationContext() != null)
            return getApplicationContext().getBean(name, clazz);
        return null;
    }


    /**
     * 获取配置属性值
     *
     * @param key    属性名
     * @param tClass 类型
     * @param <T>    返回类型
     * @return
     */
    public static <T> T getProperty(String key, Class<T> tClass) {
        T value = null;
        Binder binder = getBinder();
        if (binder != null) {
            BindResult<T> bindResult = binder.bind(key, Bindable.of(tClass));
            if (bindResult.isBound())
                value = bindResult.get();
        }
        return value;
    }

    /**
     * 获取binder对象
     *
     * @return
     */
    public static Binder getBinder() {
        if (binder == null) {
            ApplicationContext context = SpringContextUtils.getApplicationContext();
            if (context != null) {
                binder = Binder.get(context.getEnvironment());
            }
        }
        return binder;
    }
}
