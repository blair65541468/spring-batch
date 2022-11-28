package com.citi.springbatch.listener.itemReaderJPA;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ExecutorConfiguration {

  @Bean("jpaThreadPoolTaskExecutor")
  public ThreadPoolTaskExecutor jpaThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setCorePoolSize(2);
    threadPoolTaskExecutor.setMaxPoolSize(10);
    threadPoolTaskExecutor.setQueueCapacity(50);
    threadPoolTaskExecutor.setThreadNamePrefix("Data-Job");
    return threadPoolTaskExecutor;
  }
}
