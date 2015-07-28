package com.moccaplusplus.cdi.injection;

import java.lang.reflect.InvocationTargetException;

import com.moccaplusplus.cdi.BeanProvider;

public interface Injections<T> {

    void inject(T instance, BeanProvider beanProvider)
            throws InstantiationException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException;
}
