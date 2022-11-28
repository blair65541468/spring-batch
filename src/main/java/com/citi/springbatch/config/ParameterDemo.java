package com.citi.springbatch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ParameterDemo implements StepExecutionListener {
  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;
  private Map<String, JobParameter> parameter = new HashMap<>();

  @Bean
  public Job ParameterJob() {
    return jobBuilderFactory.get("ParameterJob").start(parameterStep()).build();
  }

  @Bean
  public Step parameterStep() {
    return stepBuilderFactory
        .get("parameterStep")
        .listener(this)
        .tasklet(
            new Tasklet() {
              @Override
              public RepeatStatus execute(
                  StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println(parameter.get("info"));
                return RepeatStatus.FINISHED;
              }
            })
        .build();
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    parameter = stepExecution.getJobParameters().getParameters();
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    return null;
  }
}
