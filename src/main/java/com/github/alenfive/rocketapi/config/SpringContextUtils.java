package com.github.alenfive.rocketapi.config;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils {

    private static ApplicationContext applicationContext;

    public SpringContextUtils(ApplicationContext applicationContext){
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

//    public static void registerBean(String beanName , Class c){
//        AnnotationConfigServletWebServerApplicationContext context = (AnnotationConfigServletWebServerApplicationContext)applicationContext;
//        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(c).getBeanDefinition();
//        context.registerBeanDefinition(beanName,beanDefinition);
//        //context.refresh();
//        Object bean = context.getBean(beanName);
//        System.out.println("object "+ bean);
//    }

    public ApplicationContext getContext(){
        return SpringContextUtils.applicationContext;
    }
}
