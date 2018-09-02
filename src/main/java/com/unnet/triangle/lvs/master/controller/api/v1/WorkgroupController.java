package com.unnet.triangle.lvs.master.controller.api.v1;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.unnet.triangle.lvs.master.controller.api.v1.WorkgroupController;
import com.unnet.triangle.lvs.master.service.workgroup.WorkgroupService;
import com.unnet.triangle.lvs.master.entity.workgroup.WorkgroupExt;
import com.unnet.triangle.lvs.master.controller.restful.RestResult;
import com.unnet.triangle.lvs.master.entity.GidAUUID;
import com.unnet.triangle.lvs.master.model.Workgroup;
import com.unnet.triangle.lvs.master.service.node.NodeService;

@Controller
@RequestMapping({ Constants.V1_URI_API_PREFIX + "workgroup" })
public class WorkgroupController {

	private static Logger logger = LoggerFactory.getLogger(WorkgroupController.class);
	@Autowired
	private WorkgroupService workgroupService;
	@Autowired
	NodeService nodeService;

	/**
	 * 增加工作组
	 * 
	 * @param workgroup
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public RestResult<GidAUUID> insert(@RequestBody Workgroup workgroup, HttpServletRequest request) {
		RestResult<GidAUUID> restResult = new RestResult<GidAUUID>();
		try {
			if (StringUtils.isEmpty(workgroup.getName())) {
				throw new IllegalArgumentException("添加工作组参数错误");
			}
			GidAUUID gidAUuid = workgroupService.insert(workgroup, request);
			restResult.setData(gidAUuid);
		} catch (IllegalArgumentException e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage("系统错误，添加失败");
			restResult.setData(null);
		}
		return restResult;
	}

	/**
	 * 删除工作组
	 * 
	 * @param workgroupId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{workgroupId}", method = RequestMethod.DELETE)
	public RestResult<Boolean> delete(@PathVariable("workgroupId") long workgroupId) {
		RestResult<Boolean> restResult = new RestResult<Boolean>();
		if (workgroupService.delete(workgroupId)) {
			restResult.setData(true);
		} else {
			restResult.setCode(-1);
			restResult.setMessage("系统错误");
			restResult.setData(false);
		}
		return restResult;
	}

	/*
	 * 更新工作组
	 */
	@ResponseBody
	@RequestMapping(value = "/{workgroupId}", method = RequestMethod.PUT)
	public RestResult<GidAUUID> update(@PathVariable("workgroupId") long workgroupId,
			@RequestBody Workgroup workgroup) {
		RestResult<GidAUUID> restResult = new RestResult<GidAUUID>();
		try {
			Workgroup current = workgroupService.select(workgroupId);
			if (null == current) {
				throw new Exception("no such workgroup");
			}

			if (null != workgroup.getName()) {
				current.setName(workgroup.getName());
			}
			/*
			 * if (null != workgroup.getK8s_deployment()) {
			 * current.setK8s_deployment(workgroup.getK8s_deployment()); }
			 */
			current.setUpdate_time(new Date());
			GidAUUID gidAUuid = new GidAUUID();
			/* gidAUuid.setNamespace(current.getK8s_namespace()); */
			gidAUuid.setWorkgroupId(current.getGroup_id());
			gidAUuid.setUuid(current.getUuid());
			Boolean updateResult = workgroupService.update(current);
			if (!updateResult) {
				throw new Exception("update failed");
			}

			restResult.setData(gidAUuid);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error("", e);
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}
	
	/*
	 * 查询工作组疑问待解决
	 */
	/**
	   * 查询工作组
	   * 
	   * @param workgroup
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping(method = RequestMethod.GET)
	  public RestResult<List<WorkgroupExt>> select( HttpServletRequest request) {
	    RestResult<List<WorkgroupExt>> restResult = new RestResult<List<WorkgroupExt>>();
	    try {
	      List<Workgroup> workgroups = workgroupService.select(request);
	      
	     /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	      long startDate = sdf.parse(sdf.format(new Date(System.currentTimeMillis()))).getTime();
	      long endDate = startDate + 86400 * 1000 - 1;
	      */
	      // add ext
	      List<WorkgroupExt> workgroupExts = new ArrayList<>();
	      for (Workgroup group : workgroups) {
	        WorkgroupExt ext = new WorkgroupExt();
	        BeanUtils.copyProperties(ext, group);
	        // find nodes
	        ext.setNodes(nodeService.selectAll(group.getGroup_id()));
	        ext.setNodeSize(ext.getNodes().size());
	        // find alarms
	        /*try {
	          ext.setAlarms(alarmService.getAlarms(group.getId(), startDate, endDate));
	          ext.setAlarmSize(ext.getAlarms().size());
	        } catch (Exception e) {
	          // ignore
	        }*/
	        workgroupExts.add(ext);
	      }
	      restResult.setData(workgroupExts);
	    } catch (Exception e) {
	      restResult.setCode(-1);
	      logger.error("", e);
	      restResult.setMessage(e.getMessage());
	      restResult.setData(null);
	    }
	    return restResult;
	  }
}
