package com.unnet.triangle.lvs.master.entity.agent;

/**
 * 
 * @author ricl
 *
 */
public class AgentConfig {

  private Long workgroup_id;
  private String workgroup_name;
  private String master_ip_port;
  private String uuid;
  private Long node_id;
  //private Boolean is_container;
 //暂未定
 // private String kafka_endpoints;
 // private String kafka_topic;

  public Long getWorkgroup_id() {
    return workgroup_id;
  }

  public void setWorkgroup_id(Long workgroup_id) {
    this.workgroup_id = workgroup_id;
  }


  public String getWorkgroup_name() {
    return workgroup_name;
  }

  public void setWorkgroup_name(String workgroup_name) {
    this.workgroup_name = workgroup_name;
  }

  public String getMaster_ip_port() {
    return master_ip_port;
  }

  public void setMaster_ip_port(String master_ip_port) {
    this.master_ip_port = master_ip_port;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Long getNode_id() {
    return node_id;
  }

  public void setNode_id(Long node_id) {
    this.node_id = node_id;
  }

}
