package com.moccaplusplus.cdi.injection;

import java.lang.reflect.Member;

import com.moccaplusplus.cdi.BeanDefinition;
import com.moccaplusplus.cdi.InjectionPoint;

public class InjectionPointImpl<T extends Member> implements InjectionPoint {

    private final BeanDefinition beanDefinition;
    private final T member;

    public InjectionPointImpl(BeanDefinition beanDefinition, T member) {
        this.member = member;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }

    @Override
    public T getMember() {
        return member;
    }
}