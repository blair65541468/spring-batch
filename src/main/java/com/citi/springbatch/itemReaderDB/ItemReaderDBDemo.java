package com.citi.springbatch.itemReaderDB;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
public class ItemReaderDBDemo {

  @Autowired private DataSource dataSource;

  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired
  @Qualifier("dbJdbcWriter")
  private ItemWriter<? super User> dbJdbcWriter;

  @Bean
  public Job itemReaderDBDemoJob() {
    return jobBuilderFactory.get("itemReaderDBDemoJob1").start(itemReaderDBDemoStep()).build();
  }

  @Bean
  public Step itemReaderDBDemoStep() {

    return stepBuilderFactory
        .get("itemReaderDBDemoStep")
        .<User, User>chunk(2)
        .reader(dbJDBCReader())
        .writer(dbJdbcWriter)
        .build();
  }

  @Bean
  @StepScope
  public JdbcPagingItemReader<User> dbJDBCReader() {
    JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<>();
    reader.setDataSource(dataSource);
    reader.setFetchSize(2);

    reader.setRowMapper(
        new RowMapper<User>() {
          @Override
          public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt(1));
            user.setUsername(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setAge(rs.getInt(4));
            return user;
          }
        });

    // 指定sql语句
    MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
    provider.setSelectClause("id, username,password,age");
    provider.setFromClause("from user");

    // 指定排序
    HashMap<String, Order> sort = new HashMap<>(1);
    sort.put("id", Order.ASCENDING);
    provider.setSortKeys(sort);

    reader.setQueryProvider(provider);

    return reader;
  }
}
