package com.github.alenfive.rocketapi.function;

import org.springframework.beans.factory.BeanNameAware;

public class JdbcFunction implements BeanNameAware  {
    String functionName;
    @Override
    public void setBeanName(String s) {
        this.functionName = s;
    }
}
