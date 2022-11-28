package com.citi.springbatch.itemReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class ItemReaderDemo {
  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job itemReaderDemoJob() {
    return jobBuilderFactory.get("itemReaderDemoJob").start(itemReaderDemoStep()).build();
  }

  @Bean
  public Step itemReaderDemoStep() {
    return stepBuilderFactory
        .get("itemReaderDemoStep")
        .<String, String>chunk(2)
        .reader(itemRead())
        .writer(
            data -> {
              for (Object d : data) {
                System.out.println(d);
              }
            })
        .build();
  }

  @Bean
  public MyReader itemRead() {
    List<String> data = Arrays.asList("dog", "cat", "giraffe");
    return new MyReader(data);
  }
}
