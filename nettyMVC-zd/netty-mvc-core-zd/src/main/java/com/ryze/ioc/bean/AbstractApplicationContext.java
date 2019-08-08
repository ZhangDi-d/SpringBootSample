package com.ryze.ioc.bean;

import com.ryze.ioc.factory.BeanFactory;
import com.ryze.ioc.xml.XmlUtil;


/**
 * Created by xueLai on 2019/8/8.
 */
public abstract class AbstractApplicationContext extends BeanFactory {
    protected String configuration;
    protected XmlUtil xmlUtil;


    public AbstractApplicationContext(String configuration) {
        this.configuration = configuration;
        this.xmlUtil = new XmlUtil();
    }

    public AbstractApplicationContext() {
    }
}
