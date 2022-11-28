package com.citi.springbatch.multiThreadReaderDB;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("mutliThreadWriter")
public class MultiThreadWriter implements ItemWriter<User> {

  @Override
  public void write(List<? extends User> items) throws Exception {
    for (User user : items) {
      System.out.println(user);
    }
  }
}
