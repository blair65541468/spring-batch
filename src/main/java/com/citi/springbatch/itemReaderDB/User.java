package com.citi.springbatch.itemReaderDB;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
  private Integer id;
  private String username;
  private String password;
  private int age;
}
