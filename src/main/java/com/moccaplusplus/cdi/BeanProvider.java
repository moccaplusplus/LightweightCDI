package com.moccaplusplus.cdi;


public interface BeanProvider {

    Object getBean(InjectionPoint injectionPoint);

    Object[] getExistingBeans(BeanDefinition beanDefinition);
}
