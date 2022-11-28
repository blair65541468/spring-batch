package com.citi.springbatch.config;

import com.citi.springbatch.listener.MyChunkListener;
import com.citi.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ListenerDemo {
  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job listenerJob() {
    return jobBuilderFactory
        .get("listenerJob")
        .start(listenerStep1())
        .listener(new MyJobListener())
        .build();
  }

  @Bean
  public Step listenerStep1() {
    return stepBuilderFactory
        .get("step1")
        .<String, String>chunk(2)
        .faultTolerant()
        .listener(new MyChunkListener())
        .reader(read())
        .writer(write())
        .build();
  }

  @Bean
  public ItemWriter<String> write() {
    return new ItemWriter<String>() {
      @Override
      public void write(List<? extends String> items) throws Exception {
        for (String item : items) {
          System.out.println(item);
        }
      }
    };
  }

  @Bean
  public ItemReader<String> read() {
    return new ListItemReader<>(Arrays.asList("java", "spring", "mybatis"));
  }
}
