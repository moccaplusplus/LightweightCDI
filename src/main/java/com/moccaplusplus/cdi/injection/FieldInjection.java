package com.moccaplusplus.cdi.injection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import com.moccaplusplus.cdi.BeanDefinition;
import com.moccaplusplus.cdi.BeanProvider;

public class FieldInjection<T> extends InjectionPointImpl<Field> implements
        Injections<T> {

    public FieldInjection(Field field) {
        super(BeanDefinition.forField(field), field);
    }

    @Override
    public void inject(T instance, BeanProvider beanProvider)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        getMember().set(instance, beanProvider.getBean(this));
    }
}
