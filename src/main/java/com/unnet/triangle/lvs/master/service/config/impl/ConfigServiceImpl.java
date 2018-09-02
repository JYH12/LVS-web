package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.ConfigWrapper;
import com.unnet.triangle.lvs.master.mapper.ConfigMapper;
import com.unnet.triangle.lvs.master.model.Config;
import com.unnet.triangle.lvs.master.service.config.ConfigExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigLvsService;
import com.unnet.triangle.lvs.master.service.config.ConfigService;
import com.unnet.triangle.lvs.master.service.node.NodeService;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.LVSWrapper;
import com.unnet.triangle.lvs.master.model.Node;
import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.utils.ChecksumUtil;
import com.unnet.triangle.lvs.master.model.ConfigExample;

/*
 * @author john
 */
@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	ConfigMapper configMapper;
	@Autowired
	ConfigExtService extService;
	@Autowired
	NodeService nodeService;
	@Autowired
	ConfigLvsService lvsService;
	@Autowired
	TaskService taskService;
	//
	private Config compareAndSetDefault(Config config) {
		if(config.getCreate_time()==null) {
			config.setCreate_time(new Date());
		}
		if (config.getUpdate_time()==null) {
			config.setUpdate_time(new Date());
		}
		if(config.getLvs_sync_daemon()==null) {
			config.setLvs_sync_daemon("");
		}
		if (config.getLvs_sync_instance()==null) {
			config.setLvs_sync_instance(1);
		}
		if(config.getLvs_timeout()==null) {
			config.setLvs_timeout(5);
		}
		if(config.getRouter_id()==null) {
			config.setRouter_id("LVS_"+config.getNode_id());
		}
		return config;
	}

	@Override
	public Boolean update(Config config) {
		// TODO Auto-generated method stub
		return configMapper.updateByPrimaryKey(config) > 0;
	}

	@Override
	public Config selectAll(long nodeId) {
		// TODO Auto-generated method stub
		ConfigExample example = new ConfigExample();
		example.createCriteria().andNode_idEqualTo(nodeId);
		List<Config> configs = configMapper.selectByExample(example);
		if (null != configs && configs.size() > 0) {
			return configs.get(0);
		} else {
			Config config = new Config();
			config.setNode_id(nodeId);
			config = compareAndSetDefault(config);
			if (!(configMapper.insertSelective(config) > 0)) {
				return null;
			} else {
				return config;
			}
		}
	}

	@Override
	public Config select(long id) {
		// TODO Auto-generated method stub
		return configMapper.selectByPrimaryKey(id);
	}

	@Override
	public long deployByNodeId(long nodeId) {
		// TODO Auto-generated method stub
		Node node = nodeService.select(nodeId);
	    if (null == node) {
	      throw new RuntimeException("no such node for deploy");
	    }
	    LVSWrapper lvsWrapper = lvsService.select(nodeId);
	    lvsWrapper = ChecksumUtil.packageObjectWithChecksum(lvsWrapper);
	    Task task = new Task();
	    task.setNode_id(nodeId);
	    task.setStatus(Constants.TASK_STATUS_PENDING);
	    task.setData(JSON.toJSONString(lvsWrapper));
	    task.setMsg("");
	    task.setCount(0);
	    task.setCreate_time(new Date());
	    task.setUpdate_time(new Date());
	    task.setTask_type(Constants.TASK_TYPE_MOD_CONFIG);
	    Long taskId = taskService.insert(task);
	    if (-1 == taskId) {
	      throw new RuntimeException("insert task failed");
	    }
	    return taskId;
	  }
	@Override
	public ConfigWrapper selectWrapper(long nodeId) {
		// TODO Auto-generated method stub
		ConfigWrapper wrapper = new ConfigWrapper();
		Config config = select(nodeId);
		try {
			BeanUtils.copyProperties(wrapper, config);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		wrapper.setExts(extService.selectAll(config.getConfig_id()));
		return wrapper;
	}

}
