package com.moccaplusplus.cdi.injection;

import java.lang.reflect.Executable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.moccaplusplus.cdi.BeanDefinition;
import com.moccaplusplus.cdi.InjectionPoint;
import com.moccaplusplus.cdi.Utils;

public abstract class ExecutableInjection<E extends Executable> implements
        HasBeanDependencies {

    protected final Set<BeanDefinition> beanDefinitions;
    protected final InjectionPoint[] injectionPoints;
    protected final E executable;

    public ExecutableInjection(E executable) {
        this.executable = executable;
        injectionPoints = Utils.createInjectionPointsForParameters(executable);
        final Set<BeanDefinition> set = new HashSet<BeanDefinition>();
        for (InjectionPoint injectionPoint : injectionPoints) {
            set.add(injectionPoint.getBeanDefinition());
        }
        beanDefinitions = Collections.unmodifiableSet(set);
    }

    @Override
    public Set<BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }
}
