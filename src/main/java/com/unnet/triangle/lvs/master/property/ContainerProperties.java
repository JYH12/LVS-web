package com.unnet.triangle.lvs.master.property;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.unnet.triangle.utils.es.ElasticSearchConfig;

/**
 * 
 * @author ricl
 *
 */
@Component
public class ContainerProperties {

  @Value("${CONTAINER_APP_NAME}")
  private String appName;

  @Value("${CONTAINER_HTTP_HOST}")
  private String httpHost;

  @Value("${CONTAINER_HTTP_PORT}")
  private int httpPort;

  @Value("${CONTAINER_DEBUG_MODE}")
  private boolean debug;

  @Value("${CONTAINER_SECRET_KEY}")
  private String secretKey;

  @Value("${CONTAINER_DB_URL}")
  private String dbUrl;

  @Value("${CONTAINER_DB_USERNAME}")
  private String dbUsername;

  @Value("${CONTAINER_DB_PASSWORD}")
  private String dbPassword;

  @Value("${CONTAINER_MONITOR_OUTFLOW}")
  private String monitorOutflow;

  @Value("${CONTAINER_ES_INDEX_NAME_PREFIX}")
  private String esIndexNamePrefix;

  @Value("${CONTAINER_ES_INDEX_TYPE}")
  private String esIndexType;

  @Value("${CONTAINER_ES_ENTRYPOINTS}")
  private String esEntrypoints;

  @Value("${CONTAINER_ES_CLUSTER_AUTH}")
  private boolean esClusterAuth;

  @Value("${CONTAINER_ES_CLUSTER_USER}")
  private String esClusterUser;

  @Value("${CONTAINER_ES_CLUSTER_PASS}")
  private String esClusterPass;

  @Value("${CONTAINER_KAFKA_BOOTSTRAP_SERVERS}")
  private String kafkaBootstrapServers;

  @Value("${CONTAINER_KAFKA_TOPIC}")
  private String kafkaTopic;

  @Value("${CONTAINER_NOTIFY_API_ADDRESS}")
  private String notifyApiAddress;

  private static ElasticSearchConfig elasticSearchConfig = null;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getHttpHost() {
    return httpHost;
  }

  public void setHttpHost(String httpHost) {
    this.httpHost = httpHost;
  }

  public int getHttpPort() {
    return httpPort;
  }

  public void setHttpPort(int httpPort) {
    this.httpPort = httpPort;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getDbUrl() {
    return dbUrl;
  }

  public void setDbUrl(String dbUrl) {
    this.dbUrl = dbUrl;
  }

  public String getDbUsername() {
    return dbUsername;
  }

  public void setDbUsername(String dbUsername) {
    this.dbUsername = dbUsername;
  }

  public String getDbPassword() {
    return dbPassword;
  }

  public void setDbPassword(String dbPassword) {
    this.dbPassword = dbPassword;
  }

  public String getMonitorOutflow() {
    return monitorOutflow;
  }

  public void setMonitorOutflow(String monitorOutflow) {
    this.monitorOutflow = monitorOutflow;
  }

  public String getEsIndexNamePrefix() {
    return esIndexNamePrefix;
  }

  public void setEsIndexNamePrefix(String esIndexNamePrefix) {
    this.esIndexNamePrefix = esIndexNamePrefix;
  }

  public String getEsIndexType() {
    return esIndexType;
  }

  public void setEsIndexType(String esIndexType) {
    this.esIndexType = esIndexType;
  }

  public String getEsEntrypoints() {
    return esEntrypoints;
  }

  public void setEsEntrypoints(String esEntrypoints) {
    this.esEntrypoints = esEntrypoints;
  }

  public boolean isEsClusterAuth() {
    return esClusterAuth;
  }

  public void setEsClusterAuth(boolean esClusterAuth) {
    this.esClusterAuth = esClusterAuth;
  }

  public String getEsClusterUser() {
    return esClusterUser;
  }

  public void setEsClusterUser(String esClusterUser) {
    this.esClusterUser = esClusterUser;
  }

  public String getEsClusterPass() {
    return esClusterPass;
  }

  public void setEsClusterPass(String esClusterPass) {
    this.esClusterPass = esClusterPass;
  }

  public String getKafkaBootstrapServers() {
    return kafkaBootstrapServers;
  }

  public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
    this.kafkaBootstrapServers = kafkaBootstrapServers;
  }

  public String getKafkaTopic() {
    return kafkaTopic;
  }

  public void setKafkaTopic(String kafkaTopic) {
    this.kafkaTopic = kafkaTopic;
  }

  public String getNotifyApiAddress() {
    return notifyApiAddress;
  }

  public void setNotifyApiAddress(String notifyApiAddress) {
    this.notifyApiAddress = notifyApiAddress;
  }

  /*
   * 下面为复合形式的返回
   */
  public ElasticSearchConfig getElasticSearchConfig() {
    if (elasticSearchConfig == null) {
      elasticSearchConfig = new ElasticSearchConfig();
      elasticSearchConfig.setAuth(this.isEsClusterAuth());
      elasticSearchConfig.setEntryPoints(this.getEsEntrypoints());
      elasticSearchConfig.setIndexType(this.getEsIndexType());
      elasticSearchConfig.setPassword(this.getEsClusterPass());
      elasticSearchConfig.setUsername(this.getEsClusterUser());
    }
    return elasticSearchConfig;
  }

  public String getIndexNameWithDefaultPrefix(String type) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    return this.getEsIndexNamePrefix() + "-" + type + "-" + simpleDateFormat.format(new Date());
  }

  public String getIndexNameWithDefaultPrefixAndSpecialDate(String type, Long startDate) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    return this.getEsIndexNamePrefix() + "-" + type + "-" + simpleDateFormat.format(new Date(startDate));
  }

  public String getIndexNameWithDefaultPrefixAndALL(String type) {
    return this.getEsIndexNamePrefix() + "-" + type + "-*";
  }
}
