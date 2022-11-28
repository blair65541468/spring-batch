package com.citi.springbatch.restart;

import com.citi.springbatch.itemReaderFile.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("restartWriter")
public class RestartWriter implements ItemWriter<Customer> {
  @Override
  public void write(List<? extends Customer> list) throws Exception {
    for (Customer customer : list) {
      System.out.println(customer);
    }
  }
}
