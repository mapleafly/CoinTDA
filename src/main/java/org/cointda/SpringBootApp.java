package org.cointda;

import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.cointda.entity.TradeInfo;
import org.cointda.mapper.TradeInfoMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import java.util.List;

@SpringBootApplication
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
    }
}
