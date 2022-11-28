package com.citi.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobDemo {
  @Autowired private StepBuilderFactory stepBuilderFactory;
  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Bean
  public Job jobDemoJob() {
    return jobBuilderFactory.get("jobDemoJob").start(step1()).next(step2()).next(step3()).build();
  }

  private Step step3() {
    return (Step)
        stepBuilderFactory
            .get("step3")
            .tasklet(
                new Tasklet() {
                  @Override
                  public RepeatStatus execute(
                      StepContribution stepContribution, ChunkContext chunkContext)
                      throws Exception {
                    System.out.println("step3");
                    return RepeatStatus.FINISHED;
                  }
                })
            .build();
  }

  private Step step2() {
    return (Step)
        stepBuilderFactory
            .get("step2")
            .tasklet(
                new Tasklet() {
                  @Override
                  public RepeatStatus execute(
                      StepContribution stepContribution, ChunkContext chunkContext)
                      throws Exception {
                    System.out.println("step2");
                    return RepeatStatus.FINISHED;
                  }
                })
            .build();
  }

  private Step step1() {
    return (Step)
        stepBuilderFactory
            .get("step1")
            .tasklet(
                new Tasklet() {
                  @Override
                  public RepeatStatus execute(
                      StepContribution stepContribution, ChunkContext chunkContext)
                      throws Exception {
                    System.out.println("step1");
                    return RepeatStatus.FINISHED;
                  }
                })
            .build();
  }
}
