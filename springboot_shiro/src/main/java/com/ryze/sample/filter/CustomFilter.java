package com.ryze.sample.filter;

import com.ryze.sample.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by xueLai on 2019/3/1.
 */
public class CustomFilter  implements Filter{
    private static Logger logger = LoggerFactory.getLogger(CustomFilter.class);
    private CustomCallback customCallback;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("CustomFilter============>init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("CustomFilter============>doFilter begin");
        if(customCallback==null){
            customCallback = (CustomCallback)SpringUtil.getBean("customCallback");
        }
        customCallback.onAfterLogin((HttpServletRequest) servletRequest,"zhangsan");
        logger.info("CustomFilter============>doFilter end ");
    }

    @Override
    public void destroy() {
        logger.info("CustomFilter============>destroy");
    }
}
