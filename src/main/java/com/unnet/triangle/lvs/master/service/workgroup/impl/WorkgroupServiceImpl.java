package com.unnet.triangle.lvs.master.service.workgroup.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unnet.triangle.lvs.master.entity.GidAUUID;
import com.unnet.triangle.lvs.master.model.Workgroup;
import com.unnet.triangle.lvs.master.service.workgroup.WorkgroupService;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.controller.exception.TransactionException;
import com.unnet.triangle.lvs.master.entity.auth.PolicyHook;
import com.unnet.triangle.lvs.master.mapper.WorkgroupMapper;
import com.unnet.triangle.lvs.master.model.Node;
import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.model.WorkgroupExample;
import com.unnet.triangle.lvs.master.model.WorkgroupExample.Criteria;
import com.unnet.triangle.lvs.master.property.ContainerProperties;
import com.unnet.triangle.lvs.master.service.config.ConfigService;
import com.unnet.triangle.lvs.master.service.node.NodeService;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.service.workgroup.impl.WorkgroupServiceImpl;

@Service
public class WorkgroupServiceImpl implements WorkgroupService {

	@Autowired
	WorkgroupMapper workgroupMapper;
	@Autowired
	NodeService nodeService;
	@Autowired
	TaskService taskService;
	@Autowired
	ConfigService configService;
	@Autowired
	ContainerProperties containerProperties;

	private static Logger logger = LoggerFactory.getLogger(WorkgroupServiceImpl.class);

	@Override
	public GidAUUID insert(Workgroup workgroup, HttpServletRequest request) {
		logger.info("add workgroup");
		long workgroupId = -1;
		String uuid = UUID.randomUUID().toString();
		try {
			workgroup.setCreate_time(new Date());
			workgroup.setUpdate_time(workgroup.getCreate_time());
			workgroup.setUuid(uuid);
			if (!(workgroupMapper.insertSelective(workgroup) > 0)) {
				throw new TransactionException();
			}
			workgroupId = workgroup.getGroup_id();
			if (null == configService.selectAll(workgroupId)) {
				throw new TransactionException();
			}
		} catch (Exception e) {
			throw new TransactionException();
		}

		// 工作组授权
		try {
			// 发出向DashBoard 回调
			String uri = Constants.DASHBOARD_API + Constants.DASHBOARD_WEBHOOK_PATH;
			String callback = request.getHeader("callback");
			String username = request.getHeader("username");
			logger.info("username = " + username);
			Map<String, Object> reMap = new HashMap<>();
			reMap.put("path", Constants.V1_URI_API_PREFIX + workgroupId + "/");
			reMap.put("info", "Group of " + containerProperties.getAppName() + " " + workgroupId);
			reMap.put("username", username);
			reMap.put("service", containerProperties.getAppName());
			reMap.put("gid", "" + workgroupId);

			String body = JSONObject.toJSONString(reMap);

			logger.info("uri: " + uri + "\nbody:" + body);

			String result = PolicyHook.post(uri + "?" + "callback=" + callback, body, null, true);
			if (null == result) {
				throw new Exception("webhook resp invalid");
			}
			// 结果分析
			logger.info("result: " + result);

		} catch (Exception e) {
			throw new TransactionException();
		}

		GidAUUID result = new GidAUUID();
		result.setWorkgroupId(workgroupId);
		result.setUuid(uuid);
		return result;
	}

	@Override
	public Boolean delete(Long workgroupId) {
		// 删除workgroup表中的信息
		Workgroup workgroup = workgroupMapper.selectByPrimaryKey(workgroupId);
		if (null == workgroup) {
			return false;
		}
		if (!(workgroupMapper.deleteByPrimaryKey(workgroupId) > 0)) {
			throw new TransactionException();
		}
		return true;
	}

	@Override
	public List<Workgroup> select(HttpServletRequest request) {
		try {
			String idsStr = request.getHeader("ids");
			boolean hasIDs = idsStr == null ? false : true;
			List<Long> ids = new ArrayList<Long>();
			if (hasIDs) {
				String[] sliceIds = idsStr.split(",");
				for (String id : sliceIds) {
					try {
						long lId = Long.parseLong(id);
						ids.add(lId);
					} catch (Exception e) {
						// ignore
					}
				}
			}
			WorkgroupExample workgroupExample = new WorkgroupExample();
			Criteria criteria = workgroupExample.createCriteria();

			if (hasIDs) {
				criteria.andGroup_idIn(ids);
			}

			List<Workgroup> workgroups = workgroupMapper.selectByExample(workgroupExample);
			return workgroups;
		} catch (Exception e) {
			logger.error("", e);
			throw new TransactionException();
		}
	}

	@Override
	public Workgroup select(Long workgroupId) {
		return workgroupMapper.selectByPrimaryKey(workgroupId);
	}

	@Override
	public Boolean update(Workgroup workgroup) {
		workgroup.setUpdate_time(new Date());
		if (!(workgroupMapper.updateByPrimaryKeySelective(workgroup) > 0)) {
			throw new TransactionException();
		}
		notifyAllNodes(workgroup);
		return true;
	}

	private void notifyAllNodes(Workgroup workgroup) {
		String data = JSON.toJSONString(workgroup);
		List<Node> nodes = nodeService.selectNodesByWorkgroupId(workgroup.getGroup_id());
		for (Node node : nodes) {
			Task task = new Task();
			task.setNode_id(node.getNode_id());
			task.setStatus(Constants.TASK_STATUS_PENDING);
			task.setData(data);
			task.setMsg("");
			task.setCount(0);
			task.setCreate_time(new Date());
			task.setUpdate_time(new Date());
			task.setTask_type(Constants.TASK_TYPE_MOD_WORKGROUP);
			Long taskId = taskService.insert(task);
			if (!(taskId > 0)) {
				logger.error("cannot notify workgroup update for node " + node.getNode_id());
			}
		}
	}
}
