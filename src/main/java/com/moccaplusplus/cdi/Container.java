package com.moccaplusplus.cdi;

import java.lang.reflect.InvocationTargetException;

public class Container {

    private final TypeInfoProvider typeInfoProvider;
    private BeanProvider beanProvider;

    public Container(ContainerConfig config) {
        typeInfoProvider = new TypeInfoProvider();
    }

    public Object[] getBeans(BeanDefinition beanDefinition) {
        return beanProvider.getExistingBeans(beanDefinition);
    }

    public Object getFirstBeans(BeanDefinition beanDefinition) {
        Object[] beans = getBeans(beanDefinition);
        return beans.length == 0 ? null : beans[0];
    }

    public <T> void inject(T instance) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        @SuppressWarnings("unchecked")
        final Class<T> c = (Class<T>) instance.getClass();
        typeInfoProvider.<T> getTypeInfo(c).inject(instance, beanProvider);
    }
}
