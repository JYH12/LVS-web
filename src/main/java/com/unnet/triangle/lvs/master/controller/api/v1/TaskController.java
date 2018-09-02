package com.unnet.triangle.lvs.master.controller.api.v1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.controller.api.v1.TaskController;
import com.unnet.triangle.lvs.master.controller.restful.RestResult;
import com.unnet.triangle.lvs.master.model.Node;
import com.unnet.triangle.lvs.master.model.Task;
import com.unnet.triangle.lvs.master.model.Workgroup;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.service.workgroup.WorkgroupService;

@Controller
@RequestMapping({ Constants.V1_URI_API_PREFIX + "{workgroupId}/task" })
public class TaskController {

	private static Logger logger = LoggerFactory.getLogger(TaskController.class);

	private static int FAILED_COUNTER = 3;

	@Autowired
	TaskService taskService;
	@Autowired
	WorkgroupService workgroupService;

	/**
	 * 查询任务,这个给前台用的
	 * 
	 * @param task
	 * @return
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public RestResult<List<Task>> select(@PathVariable("workgroupId") long workgroupId,
			@RequestParam("startdate") String startDate, @RequestParam("enddate") String enddate) {
		RestResult<List<Task>> restResult = new RestResult<List<Task>>();
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date start = simpleDateFormat.parse(startDate);

			Date end = simpleDateFormat.parse(enddate);
			end = new Date(end.getTime() + (86400 - 1) * 1000);

			List<Task> tasks = taskService.select(workgroupId, start, end);
			restResult.setData(tasks);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}

	/**
	 * 获取当前机器的任务队列的任务 node节点用的
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/{nodeId}", method = RequestMethod.GET)
	public RestResult<Task> select(@PathVariable("workgroupId") long workgroupId, @PathVariable("nodeId") long nodeId,
			@RequestParam("uuid") String uuid) {
		RestResult<Task> restResult = new RestResult<Task>();
		try {
			Workgroup workgroup = workgroupService.select(workgroupId);
			if (!workgroup.getUuid().equals(uuid)) {
				throw new Exception("operation not allowed");
			}
			Task task = taskService.selectByNodeId(nodeId);
			restResult.setData(task);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}

	/**
	 * 更新任务，代理执行完任务后向master汇报 node节点用的
	 * 
	 * @param task
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/node/{nodeId}", method = RequestMethod.PUT)
	public RestResult<Boolean> update(@PathVariable("workgroupId") long workgroupId,
			@PathVariable("nodeId") long nodeId, @RequestParam("uuid") String uuid, @RequestBody Task task) {
		RestResult<Boolean> restResult = new RestResult<Boolean>();
		try {
			Workgroup workgroup = workgroupService.select(workgroupId);
			if (!workgroup.getUuid().equals(uuid)) {
				throw new Exception("operation not allowed");
			}
			// 判断任务状态
			String taskStatus = task.getStatus();
			Long taskId = task.getTask_id();
			Task currentTask = taskService.selectByPrimaryKey(taskId);
			Date now = new Date();
			// 若任务执行成功则更新数据库
			if (!Constants.TASK_STATUS_SUCCESS.equals(taskStatus)) {
				// 计数变量加一
				int count = currentTask.getCount() + 1;
				currentTask.setCount(count);
				// 如果错误计数超过3次则标记为失败
				if (count > FAILED_COUNTER) {
					currentTask.setStatus(Constants.TASK_STATUS_FAILED);
					currentTask.setUpdate_time(now);
				} else {
					currentTask.setStatus(Constants.TASK_STATUS_PENDING);
					currentTask.setUpdate_time(now);
				}
			} else {
				currentTask.setStatus(taskStatus);
				currentTask.setUpdate_time(now);
			}
			currentTask.setNode_id(nodeId);
			currentTask.setMsg(task.getMsg());
			Boolean result = taskService.update(currentTask);
			if (result) {
				restResult.setData(result);
			} else {
				throw new Exception("failed");
			}
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(false);
		}
		return restResult;
	}

	/*@ResponseBody
	@RequestMapping(value = "/node/{nodeId}", method = RequestMethod.POST)
	public RestResult<Long> insert(@RequestBody Task task, @PathVariable("nodeId") long nodeId) {
		RestResult<Long> restResult = new RestResult<Long>();
		long taskId = taskService.insert(task);
		restResult.setData(taskId);
		return restResult;
	}*/
}
