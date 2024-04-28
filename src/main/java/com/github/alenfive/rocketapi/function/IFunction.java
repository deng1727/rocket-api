package com.github.alenfive.rocketapi.function;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 函数接口，实现此接口可自动注册到脚本执行上下文中
 */
public interface IFunction extends BeanNameAware {
    String getFuncName();
}
