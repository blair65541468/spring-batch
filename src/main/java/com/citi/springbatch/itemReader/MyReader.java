package com.citi.springbatch.itemReader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

public class MyReader implements ItemReader {
  private Iterator<String> iterator;

  public MyReader(List<String> data) {
    this.iterator = data.iterator();
  }

  @Override
  public Object read()
      throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
    if (iterator.hasNext()) {
      return iterator.next();
    } else {
      return null;
    }
  }
}
