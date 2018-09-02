package com.unnet.triangle.lvs.master.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;
import com.unnet.triangle.lvs.master.property.ContainerProperties;


@Component
public class CustomizationContainerBean
    implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

  private static final Logger logger = LoggerFactory.getLogger(CustomizationContainerBean.class);

  @Autowired
  ContainerProperties containerProperties;

  @Override
  public void customize(ConfigurableServletWebServerFactory server) {
    logger.info("using user defined http port [" + containerProperties.getHttpPort() + "]");
    server.setPort(containerProperties.getHttpPort());
  }

}
