package com.unnet.triangle.lvs.master.service.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.BooleanMessage;
import com.unnet.triangle.lvs.master.service.config.CommonService;
import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.service.task.TaskService;

@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	TaskService taskService;

	@Override
	public BooleanMessage async2sync(Long taskId) {
		BooleanMessage message = new BooleanMessage();
		if (!(taskId > 0)) {
			message.setOk(false);
			message.setMessage("invalid taskId");
		}
		int ttl = 30; // sec
		int idx = 0;
		while (++idx <= ttl) {
			Task task = taskService.selectByPrimaryKey(taskId);
			String status = task.getStatus();
			if (Constants.TASK_STATUS_SUCCESS.equals(status) || Constants.TASK_STATUS_FAILED.equals(status)) {
				message.setOk(Constants.TASK_STATUS_SUCCESS.equals(task.getStatus()));
				message.setMessage(task.getStatus());
				return message;
			}
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		message.setOk(false);
		message.setMessage("TaskID: " + taskId + " " + Constants.TASK_TTL_TIMEOUT);
		return message;
	}
}
