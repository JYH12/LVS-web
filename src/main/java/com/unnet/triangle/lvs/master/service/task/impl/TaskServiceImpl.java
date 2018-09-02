package com.unnet.triangle.lvs.master.service.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.controller.exception.TransactionException;
import com.unnet.triangle.lvs.master.model.Node;
import com.unnet.triangle.lvs.master.model.NodeExample;
import com.unnet.triangle.lvs.master.model.TaskExample;
import com.unnet.triangle.lvs.master.model.TaskExample.Criteria;
import com.unnet.triangle.lvs.master.service.task.impl.TaskServiceImpl;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.mapper.NodeMapper;
import com.unnet.triangle.lvs.master.mapper.TaskMapper;
import com.unnet.triangle.lvs.master.mapper.WorkgroupMapper;
import com.unnet.triangle.lvs.master.property.ContainerProperties;
import com.unnet.triangle.lvs.master.service.config.ConfigService;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	TaskMapper mapper;
	@Autowired
	WorkgroupMapper workgroupMapper;
	@Autowired
	NodeMapper nodeMapper;
	@Autowired
	ConfigService configService;
	@Autowired
	ContainerProperties containerProperties;
	private static Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);

	@Override
	public List<Task> select(Long workgroupId, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		 NodeExample nodeExample = new NodeExample();
		    nodeExample.createCriteria().andGroup_idEqualTo(workgroupId);
		    List<Node> workgroupNodes = nodeMapper.selectByExample(nodeExample);
		    List<Long> nodeIds = new ArrayList<Long>();
		    for (Node machine : workgroupNodes) {
		      nodeIds.add(machine.getNode_id());
		    }
		    nodeIds.add(-1L);

		    try {
		      TaskExample taskExample = new TaskExample();

		      Criteria criteria = taskExample.createCriteria();

		      criteria.andNode_idIn(nodeIds);

		      criteria.andCreate_timeGreaterThanOrEqualTo(startDate);
		      criteria.andCreate_timeLessThanOrEqualTo(endDate);

		      taskExample.setOrderByClause("create_time desc");
		      List<Task> tasks = mapper.selectByExampleWithBLOBs(taskExample);
		      return tasks;
		    } catch (Exception e) {
		      logger.error("", e);
		      throw new TransactionException();
		    }
	}

	@Override
	public Task selectByNodeId(Long machineId) {
		// TODO Auto-generated method stub
		 TaskExample runningExample = new TaskExample();
		    runningExample.createCriteria().andNode_idEqualTo(machineId).andStatusEqualTo(Constants.TASK_STATUS_RUNNING);
		    List<Task> runningTasks = mapper.selectByExample(runningExample);
		    if (null != runningTasks && runningTasks.size() > 0) {
		      return null;
		    }

		    // assign oldest pending task
		    TaskExample pendingExample = new TaskExample();
		    pendingExample.createCriteria().andNode_idEqualTo(machineId).andStatusEqualTo(Constants.TASK_STATUS_PENDING);
		    pendingExample.setOrderByClause("node_id asc");
		    List<Task> pendingTasks = mapper.selectByExampleWithBLOBs(pendingExample);
		    if (pendingTasks.size() > 0) {
		      // change pending to running
		      Task task = pendingTasks.get(0);
		      task.setStatus(Constants.TASK_STATUS_RUNNING);
		      task.setUpdate_time(new Date());
		      if (!(mapper.updateByPrimaryKey(task) > 0)) {
		        throw new TransactionException();
		      }
		      return task;
		    } else {
		      // no pending tasks
		      return null;
		    }
	}

	@Override
	public Long insert(Task task) {
		// TODO Auto-generated method stub
		long taskId=mapper.insertSelective(task);
		if (!((taskId)> 0)) {
		      return -1L;
		    }
		    return task.getTask_id();
	}

	@Override
	public boolean update(Task task) {
		// TODO Auto-generated method stub
		task.setUpdate_time(new Date());
	    if (mapper.updateByPrimaryKeySelective(task) <= 0) {
	      throw new TransactionException();
	    }
	    return true;
	}

	@Override
	public Task selectByPrimaryKey(Long taskId) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(taskId);
	}

}
