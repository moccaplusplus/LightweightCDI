package com.moccaplusplus.cdi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import com.moccaplusplus.cdi.injection.ConstructorInjection;
import com.moccaplusplus.cdi.injection.FieldInjection;
import com.moccaplusplus.cdi.injection.Injections;
import com.moccaplusplus.cdi.injection.MethodInjection;
import com.moccaplusplus.cdi.injection.MethodSignature;

public class TypeInfo<T> implements Injections<T> {

    private final Class<T> rawClass;
    private final List<FieldInjection<? super T>> fieldInjections;
    private final Map<MethodSignature, MethodInjection<? super T>> methodInjections;
    private final Set<BeanDefinition> beanDefinitions;
    private ConstructorInjection<T> constructorInjection;
    private Method postConstruct;
    private Method preDestroy;

    public TypeInfo(Class<T> rawClass, TypeInfo<? super T> parentType) {
        this.rawClass = rawClass;
        fieldInjections = new ArrayList<FieldInjection<? super T>>();
        methodInjections = new HashMap<MethodSignature, MethodInjection<? super T>>();

        if (parentType != null) {
            fieldInjections.addAll(parentType.fieldInjections);
            methodInjections.putAll(parentType.methodInjections);
            if (postConstruct == null) {
                postConstruct = parentType.postConstruct;
            }
            if (preDestroy == null) {
                preDestroy = parentType.preDestroy;
            }
        }

        for (Constructor<?> constructor : rawClass.getConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                if (constructorInjection != null) {
                    throw new IllegalStateException(
                            "There can be only one injectable constructor!");
                }
                @SuppressWarnings("unchecked")
                Constructor<T> typedContructor = (Constructor<T>) constructor;
                constructorInjection = new ConstructorInjection<T>(
                        typedContructor);
            }
        }

        for (Field field : rawClass.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (field.isAnnotationPresent(Inject.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldInjections.add(new FieldInjection<T>(field));
            }
        }

        for (Method method : rawClass.getDeclaredMethods()) {
            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }
            if (method.isAnnotationPresent(Inject.class)) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                methodInjections.put(
                        new MethodSignature(method),
                        new MethodInjection<T>(method));
            } else if (method.isAnnotationPresent(PostConstruct.class)) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                postConstruct = method;
            } else if (method.isAnnotationPresent(PreDestroy.class)) {
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                preDestroy = method;
            }
        }

        final Set<BeanDefinition> set = new HashSet<BeanDefinition>();
        if (constructorInjection != null) {
            set.addAll(constructorInjection.getBeanDefinitions());
        }
        for (FieldInjection<? super T> injection : fieldInjections) {
            set.add(injection.getBeanDefinition());
        }
        for (MethodInjection<? super T> injection : methodInjections.values()) {
            set.addAll(injection.getBeanDefinitions());
        }
        beanDefinitions = Collections.unmodifiableSet(set);
    }

    public Set<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public boolean hasConstructorInjection() {
        return constructorInjection != null;
    }

    public T createAndInject(BeanProvider beanProvider)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final T bean = newBean(beanProvider);
        inject(bean, beanProvider);
        postConstruct(bean);
        return bean;
    }

    public T newBean(BeanProvider beanProvider)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final T bean;
        if (constructorInjection == null) {
            bean = rawClass.newInstance();
        } else {
            bean = constructorInjection.newInstance(beanProvider);
        }
        return bean;
    }

    @Override
    public void inject(T instance, BeanProvider beanProvider)
            throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        for (FieldInjection<? super T> injection : fieldInjections) {
            injection.inject(instance, beanProvider);
        }
        for (MethodInjection<? super T> injection : methodInjections.values()) {
            injection.inject(instance, beanProvider);
        }
    }

    public void postConstruct(T instance) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        if (postConstruct == null) {
            postConstruct.invoke(instance);
        }
    }

    public void preDestroy(T instance) throws IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        if (preDestroy != null) {
            preDestroy.invoke(instance);
        }
    }
}
