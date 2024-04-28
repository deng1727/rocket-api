package com.github.alenfive.rocketapi.function;

import org.springframework.beans.factory.BeanNameAware;

/**
 * 函数接口，实现此接口可自动注册到脚本执行上下文中
 */
public abstract class AbsIFunction implements IFunction {
   String funcName;
    @Override
    public void setBeanName(String s) {
        this.funcName = s;
    }
    @Override
    public String getFuncName(){
        return funcName;
    };
}
