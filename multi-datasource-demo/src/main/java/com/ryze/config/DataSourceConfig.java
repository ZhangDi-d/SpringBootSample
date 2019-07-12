package com.ryze.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.ryze.bean.MyRoutingDataSource;
import com.ryze.enums.DBTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xueLai on 2019/7/10.
 */
@Configuration
public class DataSourceConfig {
    private final static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
//    @Bean
//    @ConfigurationProperties("spring.datasource.master")
//    public DataSource masterDataSource(){
//        return DataSourceBuilder.create().build();
//    }
//    @Bean(name = "slave1DataSource")
//    @Qualifier("slave1DataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.slave1")
//    public DataSource slave1DataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean("masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        //return new DruidDataSource();
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "slave1DataSource")
    @Qualifier("slave1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "masterJdbcTemplate")
    public JdbcTemplate masterJdbcTemplate(@Qualifier("masterDataSource") DataSource masterDataSource) {
        return new JdbcTemplate(masterDataSource);
    }

    @Bean(name = "slave1JdbcTemplate")
    public JdbcTemplate slave1JdbcTemplate(@Qualifier("slave1DataSource") DataSource slave1DataSource) {
        return new JdbcTemplate(slave1DataSource);
    }

    @Bean
    public DataSource myRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                          @Qualifier("slave1DataSource") DataSource slave1DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DBTypeEnum.MASTER, masterDataSource);
        targetDataSources.put(DBTypeEnum.SLAVE1, slave1DataSource);
        MyRoutingDataSource myRoutingDataSource = new MyRoutingDataSource();
        myRoutingDataSource.setDefaultTargetDataSource(masterDataSource);
        myRoutingDataSource.setTargetDataSources(targetDataSources);
        return myRoutingDataSource;

    }
}
