package com.moccaplusplus.cdi;

public class ContainerConfig {

    public void addInstances(Object... instances) {
        // TODO
    }

    public void addPackages(String... packageNames) {
        final int length = packageNames.length;
        final Package[] packages = new Package[length];
        for (int i = 0; i < length; i++) {
            packages[i] = Package.getPackage(packageNames[i]);
        }
        addPackages(packages);
    }

    public void addPackages(Package... packages) {
        // TODO
    }

    public void addClasses(String... classNames) throws ClassNotFoundException {
        final int length = classNames.length;
        final Class<?>[] classes = new Class[length];
        for (int i = 0; i < length; i++) {
            classes[i] = Class.forName(classNames[i]);
        }
        addClasses(classes);
    }

    public void addClasses(Class<?>... classes) {
        // TODO
    }
}
