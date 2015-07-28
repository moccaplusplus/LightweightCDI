package com.moccaplusplus.cdi.injection;

import java.util.Set;

import com.moccaplusplus.cdi.BeanDefinition;

public interface HasBeanDependencies {

    Set<BeanDefinition> getBeanDefinitions();
}
