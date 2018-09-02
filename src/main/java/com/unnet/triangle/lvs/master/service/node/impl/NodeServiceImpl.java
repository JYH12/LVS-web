package com.unnet.triangle.lvs.master.service.node.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.LVSWrapper;
import com.unnet.triangle.lvs.master.model.Node;
import com.unnet.triangle.lvs.master.service.node.NodeService;
import com.unnet.triangle.lvs.master.mapper.NodeMapper;
import com.unnet.triangle.lvs.master.property.ContainerProperties;
import com.unnet.triangle.lvs.master.service.config.CommonService;
import com.unnet.triangle.lvs.master.service.config.ConfigLvsService;
import com.unnet.triangle.lvs.master.service.config.ConfigService;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.service.workgroup.WorkgroupService;
import com.unnet.triangle.lvs.master.entity.agent.AgentConfig;
import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.model.Workgroup;
import com.alibaba.fastjson.JSON;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.controller.exception.TransactionException;
import com.unnet.triangle.lvs.master.model.NodeExample;

/*
 * @author john
 */
@Service
public class NodeServiceImpl implements NodeService {
	 @Autowired
	  NodeMapper mapper;
	  @Autowired
	  TaskService taskService;
	  @Autowired
	  ConfigService configService;
	  @Autowired
	  ContainerProperties containerProperties;
	  @Autowired
	  WorkgroupService workgroupService;
	  @Autowired
	  CommonService commonService;
	  @Autowired
	  ConfigLvsService lvsService;
	@Override
	public long insert(Node node) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(node.getInfo())) {
		      node.setInfo(Constants.NULL);
		    }
		    if (!(mapper.insertSelective(node) > 0)) {
		      return -1;
		    }
		    return node.getNode_id();
	}

	@Override
	public boolean delete(long nodeId) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(nodeId) > 0;
	}

	@Override
	public List<Node> selectAll(long workgroupId) {
		// TODO Auto-generated method stub
		NodeExample example = new NodeExample();
	    example.setOrderByClause("node_id desc");
	    example.createCriteria().andGroup_idEqualTo(workgroupId);
	    List<Node> nodes = mapper.selectByExample(example);
	    return nodes;
	}

	@Override
	public Node select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(Node node) {
		// TODO Auto-generated method stub
		if (!(mapper.updateByPrimaryKey(node) > 0)) {
		      throw new TransactionException();
		    }
		    return true;
	}

	@Override
	public boolean deploy(long nodeId) {
		// TODO Auto-generated method stub
		 Node node = mapper.selectByPrimaryKey(nodeId);
		    Workgroup workgroup = workgroupService.select(node.getGroup_id());
		    AgentConfig agentConfig = new AgentConfig();
		    agentConfig.setWorkgroup_id(workgroup.getGroup_id());
		    agentConfig.setWorkgroup_name(workgroup.getName());
		    agentConfig.setUuid(workgroup.getUuid());
		    agentConfig.setNode_id(node.getNode_id());
		    //agentConfig.setKafka_endpoints(containerProperties.getKafkaBootstrapServers());
		    //agentConfig.setKafka_topic(containerProperties.getKafkaTopic());
		    //agentConfig.setIs_container(Boolean.parseBoolean(node.getIs_container()));
		    agentConfig.setMaster_ip_port("http://" + "192.168.0.137"+ ":" + "8080");

		    String deployTaskData = "";
		    /*if (true == Boolean.parseBoolean(node.getIs_container())) {
		      deployTaskData = "TRIANGLE_PARAMS = " + Base64.encodeBase64String(JSON.toJSONString(agentConfig).getBytes());
		    }*/ 
		    //暂不考虑agent容器化部署
		    
		      deployTaskData = String.format("%s %s %s", "lvs-agentd", "init", Base64.encodeBase64String(JSON.toJSONString(agentConfig).getBytes()));

		    // create agent
		    Task deployTask = new Task();
		    deployTask.setNode_id(node.getNode_id());
		    deployTask.setStatus(Constants.TASK_STATUS_SUCCESS);
		    deployTask.setData(deployTaskData);
		    deployTask.setMsg("");
		    deployTask.setCount(0);
		    deployTask.setCreate_time(new Date());
		    deployTask.setUpdate_time(new Date());
		    deployTask.setTask_type(Constants.TASK_TYPE_DEPLOY);
		    if (taskService.insert(deployTask) < 0) {
		      return false;
		    }
		    // if agent start success, it will ask master for a config
		    //configService.deployByNodeId(node.getNode_id());
		    return true;
		  }

	@Override
	public List<Node> selectNodesByWorkgroupId(long workgroupId) {
		// TODO Auto-generated method stub
		NodeExample nodeExample = new NodeExample();
	    nodeExample.createCriteria().andGroup_idEqualTo(workgroupId);
	    List<Node> nodes = mapper.selectByExample(nodeExample);
	    if (null == nodes || nodes.isEmpty()) {
	      return null;
	    }
	    return nodes;
	}

	@Override
	public LVSWrapper config(long nodeId) {
		// TODO Auto-generated method stub
		return lvsService.select(nodeId);
	}

}
