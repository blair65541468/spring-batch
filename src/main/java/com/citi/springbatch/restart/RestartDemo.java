package com.citi.springbatch.restart;

import com.citi.springbatch.itemReaderFile.Customer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestartDemo {
  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("restartReader")
  private ItemStreamReader<Customer> restartReader;

  @Autowired
  @Qualifier("restartWriter")
  private ItemWriter<? super Customer> restartWriter;

  @Bean
  public Job restartDemoJob() {
    return jobBuilderFactory.get("restartDemoJob").start(restartDemoStep()).build();
  }

  private Step restartDemoStep() {
    return stepBuilderFactory
        .get("restartDemoStep")
        .<com.citi.springbatch.itemReaderFile.Customer, Customer>chunk(2)
        .reader(restartReader)
        .writer(restartWriter)
        .build();
  }
}
