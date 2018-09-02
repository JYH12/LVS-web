package com.unnet.triangle.lvs.master.controller.api.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.controller.api.v1.NodeController;
import com.unnet.triangle.lvs.master.service.node.NodeService;
import com.unnet.triangle.lvs.master.service.task.TaskService;
import com.unnet.triangle.lvs.master.service.workgroup.WorkgroupService;
import com.unnet.triangle.lvs.master.controller.restful.RestResult;
import com.unnet.triangle.lvs.master.model.Node;

@Controller
@RequestMapping({ Constants.V1_URI_API_PREFIX + "{workgroupId}/node" })
public class NodeController {
	private static Logger logger = LoggerFactory.getLogger(NodeController.class);

	@Autowired
	NodeService nodeService;
	@Autowired
	WorkgroupService workGroupService;
	@Autowired
	TaskService taskService;
	@Autowired
	WorkgroupService workgroupService;

	/*
	 * 新增节点
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public RestResult<Long> insert(@RequestBody Node node, @PathVariable(value = "workgroupId") long workgroupId) {
		RestResult<Long> restResult = new RestResult<Long>();
		long nodeId = -1;
		try {
			node.setGroup_id(workgroupId);
			nodeId = nodeService.insert(node);
			if (!(nodeId > 0)) {
				throw new Exception("cannot insert node");
			}
			boolean deployResult = nodeService.deploy(nodeId);
			if (!deployResult) {
				// rollback
				nodeService.delete(nodeId);
				throw new Exception("could not deploy node");
			}
			restResult.setData(nodeId);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(-1L);
		}
		return restResult;
	}

	/*
	 * 删除节点
	 */
	@ResponseBody
	@RequestMapping(value = "/{nodeId}", method = RequestMethod.DELETE)
	public RestResult<Boolean> delete(@PathVariable(value = "workgroupId") long workgroupId,
			@PathVariable(value = "nodeId") long nodeId) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			if (nodeService.delete(nodeId)) {
				restResult.setData(true);
			} else {
				throw new Exception("cannot delete node");
			}
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(false);
		}
		return restResult;
	}

	/*
	 * 修改节点
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
	public RestResult<Boolean> update(@RequestBody Node node) {
		RestResult<Boolean> restResult = new RestResult<Boolean>();
		try {
			Node current = nodeService.select(node.getNode_id());
			if (null == node.getName()) {
				node.setName(current.getName());
			}
			current.setInfo(node.getInfo());
			if (nodeService.update(current)) {
				restResult.setData(true);
			} else {
				throw new Exception("unexpected error");
			}
		} catch (IllegalArgumentException e) {
			restResult.setCode(-2);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}
	
	/*
	 *按工作组查询节点列表
	 */
	@ResponseBody
	  @RequestMapping(method = RequestMethod.GET)
	  public RestResult<List<Node>> select(@PathVariable(value = "workgroupId") long workgroupId) {
	    RestResult<List<Node>> restResult = new RestResult<List<Node>>();
	    try {
	      List<Node> nodes = nodeService.selectAll(workgroupId);
	      restResult.setData(nodes);
	    } catch (Exception e) {
	      restResult.setCode(-1);
	      logger.error("", e);
	      restResult.setMessage(e.getMessage());
	      restResult.setData(null);
	    }
	    return restResult;
	  }
}
