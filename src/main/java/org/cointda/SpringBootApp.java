package org.cointda;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.cointda.mapper.TradeInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

@SpringBootApplication
@EnableFeignClients
@Order(value = 2)
@Slf4j
@MapperScan("org.cointda.mapper")
public class SpringBootApp implements CommandLineRunner {

    @Resource
    private TradeInfoMapper tradeInfoMapper;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("加载顺序2");
        Application.launch(App.class, args);

        //todo 下一步计划
        // 1.完善ICMCListingsLatestFeignClient
        // 2.创建ICMCListingsLatestService及实现类
        // 3.实现各种业务Service
        // 4.替换数据库
    }
}
