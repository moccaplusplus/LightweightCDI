package com.moccaplusplus.cdi.injection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.moccaplusplus.cdi.BeanProvider;
import com.moccaplusplus.cdi.Utils;

public class MethodInjection<T> extends ExecutableInjection<Method> implements
        Injections<T> {
    
    public MethodInjection(Method method) {
        super(method);
    }

    @Override
    public void inject(T instance, BeanProvider beanProvider)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        executable.invoke(instance,
                Utils.getBeans(beanProvider, injectionPoints));
    }
}
