package com.ryze.sample.filter;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xueLai on 2019/3/1.
 */
public interface CustomCallback {
    void onAfterLogin(HttpServletRequest servletRequest, String userInfo);
}
