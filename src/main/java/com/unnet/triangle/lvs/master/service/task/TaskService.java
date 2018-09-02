package com.unnet.triangle.lvs.master.service.task;

import java.util.Date;
import java.util.List;

import com.unnet.triangle.lvs.master.model.Task;

public interface TaskService {
	/**
	 * 根据条件查询
	 * 
	 * @param workgroupId
	 * @param task
	 * @return
	 */
	List<Task> select(Long workgroupId, Date startDate, Date endDate);

	/**
	 * 查询节点的任务
	 * 
	 * @param machineId
	 * @return
	 */
	Task selectByNodeId(Long machineId);

	/**
	 * raw insert
	 * 
	 * @param task
	 * @return
	 */
	Long insert(Task task);

	/**
	 * 更新任务状态
	 * 
	 * @param task
	 * @return
	 */
	boolean update(Task task);

	/**
	 * 根据主键查询任务
	 * 
	 * @param taskId
	 * @return
	 */
	Task selectByPrimaryKey(Long taskId);
}
