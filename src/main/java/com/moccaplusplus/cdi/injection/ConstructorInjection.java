package com.moccaplusplus.cdi.injection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.moccaplusplus.cdi.BeanProvider;
import com.moccaplusplus.cdi.Utils;

public class ConstructorInjection<T> extends
        ExecutableInjection<Constructor<T>> {

    public ConstructorInjection(Constructor<T> constructor) {
        super(constructor);
    }

    public T newInstance(BeanProvider beanProvider)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        return executable.newInstance(
                Utils.getBeans(beanProvider, injectionPoints));
    }
}
