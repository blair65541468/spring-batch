package com.citi.springbatch.itemReaderDB;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("dbJdbcWriter")
public class DBJdbcWriter implements ItemWriter<User> {
  @Override
  public void write(List<? extends User> list) throws Exception {
    for (User user : list) {
      System.out.println(user);
    }
  }
}
