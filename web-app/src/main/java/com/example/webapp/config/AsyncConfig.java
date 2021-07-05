//package com.example.webapp.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
//@Configuration
//@EnableAsync
//@Slf4j
//public class AsyncConfig {
//    @Bean
//    public Executor asyncThreadTaskExecutor() {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(8);
//        threadPoolTaskExecutor.setMaxPoolSize(8);
//        threadPoolTaskExecutor.setThreadNamePrefix("file-pool");
//        log.info("set async info!!!!!");
//        return threadPoolTaskExecutor;
//    }
//}
