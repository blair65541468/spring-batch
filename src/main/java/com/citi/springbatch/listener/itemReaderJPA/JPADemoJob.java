package com.citi.springbatch.listener.itemReaderJPA;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.persistence.EntityManagerFactory;

@Configuration
public class JPADemoJob {

  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired private EntityManagerFactory emf;

  @Autowired
  @Qualifier("jpaThreadPoolTaskExecutor")
  private ThreadPoolTaskExecutor threadPoolTaskExecutor;

  @Autowired
  @Qualifier("jpaWriter")
  private ItemWriter<User> jpaWriter;

  @Bean
  public Job jpaReaderDemoJob() {
    return jobBuilderFactory.get("jpaReaderDemoJob2").start(jpaReaderDemoStep()).build();
  }

  @Bean
  public Step jpaReaderDemoStep() {

    return stepBuilderFactory
        .get("jpaReaderDemoStep")
        .<User, User>chunk(2)
        .reader(getJPAReader())
        .writer(jpaWriter)
        .taskExecutor(threadPoolTaskExecutor)
        .build();
  }

  private ItemReader<? extends User> getJPAReader() {
    // 读取数据,这里可以用JPA,JDBC,JMS 等方式读取数据
    JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();
    try {
      // 这里选择JPA方式读取数据
      JpaNativeQueryProvider<User> queryProvider = new JpaNativeQueryProvider<>();
      // 一个简单的 native SQL
      queryProvider.setSqlQuery("SELECT * FROM user");
      // 设置实体类
      queryProvider.setEntityClass(User.class);
      queryProvider.afterPropertiesSet();

      reader.setEntityManagerFactory(emf);
      // 设置每页读取的记录数
      reader.setPageSize(5);
      // 设置数据提供者
      reader.setQueryProvider(queryProvider);
      reader.afterPropertiesSet();

      // 所有ItemReader和ItemWriter实现都会在ExecutionContext提交之前将其当前状态存储在其中,
      // 如果不希望这样做,可以设置setSaveState(false)
      reader.setSaveState(true);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return reader;
  }
}
