package com.citi.springbatch.itemReaderFile;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
@Component("flatFileWriter")
public class FlatFileWriter implements ItemWriter<Customer> {
  @Override
  public void write(List<? extends Customer> list) throws Exception {
    for (Customer customer : list) {
      System.out.println(customer);
    }
  }
}
