package com.github.alenfive.rocketapi.core.inteface;

import org.springframework.beans.factory.BeanNameAware;

/**
 * SQL  构造器
 */

public interface RocketTemplate extends BeanNameAware {

    /**
     * 获取该beanname 用于jdbc在控制台暴露出name
     * @return
     */
    public String getBeanName();


}
