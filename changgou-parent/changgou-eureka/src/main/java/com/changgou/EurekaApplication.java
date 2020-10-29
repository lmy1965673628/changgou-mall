package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//服务开启
public class EurekaApplication {
    /**
     * 加载启动类，以启动类为当前SpringBoot的启动标准
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}
