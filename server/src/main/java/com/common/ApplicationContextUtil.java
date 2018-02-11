package com.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by WuTaoyu on 2018/2/6.
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private  static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(context == null ) {
            context = applicationContext;
        }
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
