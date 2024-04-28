package com.github.alenfive.rocketapi.core.impl;

import com.github.alenfive.rocketapi.core.inteface.RocketTemplate;

/**
 * SQL  构造器
 */
public class TemplateWrapper implements RocketTemplate {
    private String beanName ;

    @Override
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }
}
