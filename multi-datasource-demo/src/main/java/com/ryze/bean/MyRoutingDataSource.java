package com.ryze.bean;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

/**
 * Created by xueLai on 2019/7/10.
 * 获取路由key
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    @Nullable
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}
