package com.moccaplusplus.cdi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class BeanDefinition {

    public static BeanDefinition forField(Field field) {
        return new BeanDefinition(field.getGenericType(),
                Utils.getQualifiers(field.getAnnotations()));
    }

    public static BeanDefinition[] forExecutable(Executable executable) {
        final Parameter[] parameters = executable.getParameters();
        final int length = parameters.length;
        final BeanDefinition[] beanDefinitions = new BeanDefinition[length];
        for (int i = 0; i < length; i++) {
            final Parameter p = parameters[i];
            beanDefinitions[i] = new BeanDefinition(p.getParameterizedType(),
                    Utils.getQualifiers(p.getAnnotations()));
        }
        return beanDefinitions;
    }

    private final Type type;

    private final List<Annotation> qualifiers;

    public BeanDefinition(Type type, List<Annotation> qualifiers) {
        this.type = type;
        this.qualifiers = Collections.unmodifiableList(qualifiers);
    }

    public Type getType() {
        return type;
    }

    public List<Annotation> getQualifiers() {
        return qualifiers;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().equals(obj.getClass())) {
            return false;
        }
        final BeanDefinition bd = (BeanDefinition) obj;
        return qualifiers.equals(bd.qualifiers) && type.equals(bd.type);
    }

    @Override
    public int hashCode() {
        // TODO
        return type.hashCode() + qualifiers.hashCode();
    }
}
