package com.unnet.triangle.lvs.master.config;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.unnet.triangle.lvs.master.property.ContainerProperties;


@Configuration
public class DataSourceConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

  @Autowired
  ContainerProperties containerProperties;

  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean(name = "dataSource")
  @Primary
  public DataSource dataSource() {
    logger.info("initializing dataSource with user defined config");
    return DataSourceBuilder.create().url(containerProperties.getDbUrl())
        .username(containerProperties.getDbUsername()).password(containerProperties.getDbPassword())
        .build();
  }

}
