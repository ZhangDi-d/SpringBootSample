package com.ryze.ioc.xml;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * Created by xueLai on 2019/8/8.
 */
public class XmlUtil {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtil.class);

    public String handlerXMLForScanPackage(String configuration) {
        logger.info("param:configuration=" + configuration);
        //user.dir指定了当前的路径
        logger.info("current path=" + System.getProperty("user.dir"));
        if (StringUtils.isBlank(configuration)) {
            return null;
        }
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configuration);
        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(inputStream);
            Element root = doc.getRootElement();
            Element element = root.element("package-scan");
            return element.attributeValue("component-scan");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
