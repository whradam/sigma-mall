package com.whradam.sigmamall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ConfigurationPropertiesScan
@MapperScan(basePackages = {"com.whradam.sigmamall.dao"})
@EnableScheduling
//@EnableAsync
//todo: 增加图片管理功能
//todo: 增加定时修改管理员密码并发送邮件提示功能
//todo: 启用多线程调优
public class SigmaMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(SigmaMallApplication.class, args);
    }

}
