package com.moccaplusplus.cdi;

import java.lang.reflect.Member;

public interface InjectionPoint {

    BeanDefinition getBeanDefinition();
    
    Member getMember();
}
