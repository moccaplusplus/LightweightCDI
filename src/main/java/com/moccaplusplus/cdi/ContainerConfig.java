package com.moccaplusplus.cdi;


public class ContainerConfig {

    public void addInstance(Object instances) {
    }

    public void addClass(String className) throws ClassNotFoundException {
        addClass(Class.forName(className));
    }

    public void addClass(Class<?> c) {
        // TODO
    }
}
