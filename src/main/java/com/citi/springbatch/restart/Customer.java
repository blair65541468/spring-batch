package com.citi.springbatch.restart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Customer {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthday;
}
