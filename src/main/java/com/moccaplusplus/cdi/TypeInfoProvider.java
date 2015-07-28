package com.moccaplusplus.cdi;

import java.util.HashMap;
import java.util.Map;

public class TypeInfoProvider {

    private final Map<Class<?>, TypeInfo<?>> map;

    public TypeInfoProvider() {
        map = new HashMap<Class<?>, TypeInfo<?>>();
    }

    public <T> TypeInfo<T> getTypeInfo(Class<T> c) {
        if (c == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        TypeInfo<T> typeInfo = (TypeInfo<T>) map.get(c);
        if (typeInfo == null) {
            typeInfo = new TypeInfo<T>(c, getTypeInfo(c.getSuperclass()));
            map.put(c, typeInfo);
        }
        return typeInfo;
    }
}
