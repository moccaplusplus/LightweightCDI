package com.moccaplusplus.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Qualifier;

import com.moccaplusplus.cdi.injection.InjectionPointImpl;

public class Utils {

    public static BeanDefinition[] getBeanDefinitions(Parameter[] parameters) {
        final int length = parameters.length;
        final BeanDefinition[] beanDefinitions = new BeanDefinition[length];
        for (int i = 0; i < length; i++) {
            final Parameter p = parameters[i];
            beanDefinitions[i] = new BeanDefinition(
                    p.getParameterizedType(), getQualifiers(p.getAnnotations()));
        }
        return beanDefinitions;
    }

    public static List<Annotation> getQualifiers(Annotation[] annotations) {
        final List<Annotation> qualifiers = new ArrayList<Annotation>();
        for (Annotation a : annotations) {
            if (a.annotationType().isAnnotationPresent(Qualifier.class)) {
                qualifiers.add(a);
            }
        }
        return qualifiers;
    }

    public static InjectionPoint[] createInjectionPointsForParameters(
            Executable executable) {
        final BeanDefinition[] beanDefinitions =
                BeanDefinition.forExecutable(executable);
        final InjectionPoint[] injectionPoints =
                new InjectionPoint[beanDefinitions.length];
        for (int i = 0; i < beanDefinitions.length; i++) {
            injectionPoints[i] = new InjectionPointImpl<Executable>(
                    beanDefinitions[i], executable);
        }
        return injectionPoints;
    }

    public static Object[] getBeans(BeanProvider beanProvider,
            InjectionPoint[] injectionPoints) {
        final Object[] beans = new Object[injectionPoints.length];
        for (int i = 0; i < injectionPoints.length; i++) {
            beans[i] = beanProvider.getBean(injectionPoints[i]);
        }
        return beans;
    }
}
