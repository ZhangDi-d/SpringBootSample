package com.ryze;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by xueLai on 2019/7/10.
 */
@SpringBootApplication
@EnableTransactionManagement
public class MultiDataSourceApplication {
    public static void main(String[] args){
        SpringApplication.run(MultiDataSourceApplication.class,args);
    }
}
