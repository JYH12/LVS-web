package com.unnet.triangle.lvs.master.controller.api.v1;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unnet.triangle.lvs.master.constants.Constants;
import com.unnet.triangle.lvs.master.controller.api.v1.ConfigController;
import com.unnet.triangle.lvs.master.service.config.ConfigExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceVipService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupService;
import com.unnet.triangle.lvs.master.service.node.NodeService;
import com.unnet.triangle.lvs.master.entity.BooleanMessage;
import com.unnet.triangle.lvs.master.model.ConfigExt;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServer;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExt;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserver;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExt;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstance;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExt;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceVip;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroup;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExt;
import com.unnet.triangle.lvs.master.controller.restful.RestResult;
import com.unnet.triangle.lvs.master.model.Config;
import com.unnet.triangle.lvs.master.service.config.CommonService;

/*
 * @author john
 */
@Controller
@RequestMapping(value = Constants.V1_URI_API_PREFIX + "{nodeId}")
public class ConfigController {
	private static Logger logger = LoggerFactory.getLogger(ConfigController.class);

	/*
	 * check chain
	 * 
	 */
	private void chainConfigId(long nodeId, long configId) throws Exception {
		if (configService.selectAll(nodeId).getConfig_id() != configId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigExtId(long nodeId, long configId, long configExtId) throws Exception {
		chainConfigId(nodeId, configId);
		if (configExtService.select(configExtId).getConfig_id() != configId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVirtualServerId(long nodeId, long configId, long virtualServerId) throws Exception {
		chainConfigId(nodeId, configId);
		if (configVirtualServerService.select(virtualServerId).getConfig_id() != configId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVirtualServerExtId(long nodeId, long configId, long virtualServerId,
			long virtualServerExtId) throws Exception {
		chainConfigVirtualServerId(nodeId, configId, virtualServerId);
		if (configVirtualServerExtService.select(virtualServerExtId).getVs_id() != virtualServerId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVirtualServerRealserverId(long nodeId, long configId, long virtualServerId,
			long realServerId) throws Exception {
		chainConfigVirtualServerId(nodeId, configId, virtualServerId);
		if (configVirtualServerRealserverService.select(realServerId).getVirtualserver_id() != virtualServerId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVirtualServerRealserverExtId(long nodeId, long configId, long virtualServerId,
			long realServerId, long realServerExtId) throws Exception {
		chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, realServerId);
		if (configVirtualServerRealserverExtService.select(realServerExtId).getRealserver_id() != realServerId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVrrpInstanceId(long nodeId, long configId, long instanceId) throws Exception {
		chainConfigId(nodeId, configId);
		if (configVrrpInstanceService.select(instanceId).getConfig_id() != configId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVrrpInstanceExtId(long nodeId, long configId, long instanceId, long instanceExtId)
			throws Exception {
		chainConfigVrrpInstanceId(nodeId, configId, instanceId);
		if (configVrrpInstanceExtService.select(instanceExtId).getInstance_id() != instanceId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVrrpInstanceVipId(long nodeId, long configId, long instanceId, long vipId)
			throws Exception {
		chainConfigVrrpInstanceId(nodeId, configId, instanceId);
		if (configVrrpInstanceVipService.select(vipId).getInstance_id() != instanceId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVrrpSyncGroupId(long nodeId, long configId, long vrrpGroupId) throws Exception {
		chainConfigId(nodeId, configId);
		if (configVrrpSyncGroupService.select(vrrpGroupId).getConfig_id() != configId) {
			throw new Exception("invalid id chain");
		}
	}

	private void chainConfigVrrpSyncGroupExtId(long nodeId, long configId, long vrrpGroupId, long vrrpGroupExtId)
			throws Exception {
		chainConfigVrrpSyncGroupId(nodeId, configId, vrrpGroupId);
		if (configVrrpSyncGroupExtService.select(vrrpGroupExtId).getVrrpgroup_id() != vrrpGroupId) {
			throw new Exception("invalid id chain");
		}
	}

	/*
	 * URL常量
	 */
	private static final String CONFIG_PREFIX = "/config";
	private static final String CONFIG_EXT_PREFIX = CONFIG_PREFIX + "/{configId}" + "/ext";
	private static final String VIRTUAL_SERVER_PREFIX = CONFIG_PREFIX + "/{configId}" + "/virtual_server";
	private static final String VIRTUAL_SERVER_EXT_PREFIX = VIRTUAL_SERVER_PREFIX + "/{virtualServerId}" + "/ext";
	private static final String VIRTUAL_SERVER_REALSERVER_PREFIX = VIRTUAL_SERVER_PREFIX + "/{virtualServerId}"
			+ "/real_server";
	private static final String VIRTUAL_SERVER_REALSERVER_EXT_PREFIX = VIRTUAL_SERVER_REALSERVER_PREFIX
			+ "/{realServerId}" + "/ext";
	private static final String VRRP_INSTANCE_PREFIX = CONFIG_PREFIX + "/{configId}" + "/vrrp_instance";
	private static final String VRRP_INSTANCE_EXT_PREFIX = VRRP_INSTANCE_PREFIX + "/{vrrpInstanceId}" + "/ext";
	private static final String VRRP_INSTANCE_VIP_PREFIX = VRRP_INSTANCE_PREFIX + "/{vrrpInstanceId}" + "/vip";
	private static final String VRRP_SYNC_GROUP_PREFIX = CONFIG_PREFIX + "/{configId}" + "/vrrp_sync_group";
	private static final String VRRP_SYNC_GROUP_EXT_PREFIX = VRRP_SYNC_GROUP_PREFIX + "/{vrrpsyncGroupId}" + "/ext";
	@Autowired
	CommonService commonService;
	@Autowired
	NodeService nodeService;
	@Autowired
	ConfigService configService;
	@Autowired
	ConfigExtService configExtService;

	@Autowired
	ConfigVirtualServerService configVirtualServerService;
	@Autowired
	ConfigVirtualServerExtService configVirtualServerExtService;
	@Autowired
	ConfigVirtualServerRealserverService configVirtualServerRealserverService;
	@Autowired
	ConfigVirtualServerRealserverExtService configVirtualServerRealserverExtService;

	@Autowired
	ConfigVrrpInstanceService configVrrpInstanceService;
	@Autowired
	ConfigVrrpInstanceExtService configVrrpInstanceExtService;
	@Autowired
	ConfigVrrpInstanceVipService configVrrpInstanceVipService;

	@Autowired
	ConfigVrrpSyncGroupService configVrrpSyncGroupService;
	@Autowired
	ConfigVrrpSyncGroupExtService configVrrpSyncGroupExtService;

	/*
	 * config改查
	 */

	/*
	 * 根据节点id 查询 节点主配置
	 */
	@ResponseBody
	@GetMapping(CONFIG_PREFIX)
	public RestResult<Config> selectConfig(@PathVariable("nodeId") long nodeId) {
		RestResult<Config> restResult = new RestResult<>();
		try {
			Config config = configService.selectAll(nodeId);
			if (null != config) {
				restResult.setData(config);
			} else {
				throw new Exception("no such config");
			}
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error(e.getMessage());
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}

	/*
	 * 按照节点id和主配置id 修改主配置
	 */
	@ResponseBody
	@PutMapping(CONFIG_PREFIX + "/{id}")
	public RestResult<Boolean> update(@PathVariable("nodeId") long nodeId, @PathVariable("id") long id,
			@RequestBody Config config) {
		RestResult<Boolean> restResult = new RestResult<Boolean>();
		try {
			// check id chain
			chainConfigId(nodeId, id);
			config.setConfig_id(id);
			config.setNode_id(nodeId);
			if (null == config.getCreate_time()) {
				config.setCreate_time(new Date());
			}
			config.setUpdate_time(new Date());
			boolean result = configService.update(config);
			if (!result) {
				throw new Exception("update config failed");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			logger.error(e.getMessage());
			restResult.setMessage(e.getMessage());
			restResult.setData(null);
		}
		return restResult;
	}
	@ResponseBody
	  @GetMapping(CONFIG_PREFIX + "/{configId}/deploy")
	  public RestResult<BooleanMessage> deploy(@PathVariable("nodeId") long nodeId, @PathVariable("configId") long configId) {
	    RestResult<BooleanMessage> restResult = new RestResult<>();
	    try {
	      chainConfigId(nodeId, configId);
	      BooleanMessage message =new BooleanMessage();
	      Long taskId = configService.deployByNodeId(nodeId);
	      message=commonService.async2sync(taskId);
	      restResult.setData(message);
	    } catch (Exception e) {
	      restResult.setCode(-1);
	      logger.error("", e);
	      restResult.setMessage(e.getMessage());
	      restResult.setData(null);
	    }
	    return restResult;
	  }

	/*
	 * configExt增删改查
	 */
	@ResponseBody
	@PostMapping(CONFIG_EXT_PREFIX)
	public RestResult<Long> insertConfigExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @RequestBody ConfigExt configExt) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			configExt.setConfig_id(configId);
			long id = configExtService.insert(configExt);
			if (id < 0) {
				throw new Exception("cannot insert config ext");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(CONFIG_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigExtId(nodeId, configId, id);
			boolean result = configExtService.delete(id);
			if (!result) {
				throw new Exception("cannot update config ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(CONFIG_EXT_PREFIX + "/{id}")
	public RestResult<ConfigExt> selectConfigExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<ConfigExt> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigExtId(nodeId, configId, id);
			ConfigExt result = configExtService.select(id);
			if (null == result) {
				throw new Exception("cannot get config ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(CONFIG_EXT_PREFIX)
	public RestResult<List<ConfigExt>> selectConfigExts(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId) {
		RestResult<List<ConfigExt>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			List<ConfigExt> result = configExtService.selectAll(configId);
			if (null == result) {
				throw new Exception("cannot get config exts");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(CONFIG_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id, @RequestBody ConfigExt configExt) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigExtId(nodeId, configId, id);
			configExt.setId(id);
			configExt.setConfig_id(configId);
			boolean result = configExtService.update(configExt);
			if (!result) {
				throw new Exception("cannot update config ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * virtualServer
	 */
	@ResponseBody
	@PostMapping(VIRTUAL_SERVER_PREFIX)
	public RestResult<Long> insertConfigVirtualServer(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @RequestBody ConfigVirtualServer virtualServer) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			virtualServer.setConfig_id(configId);
			long id = configVirtualServerService.insert(virtualServer);
			if (id < 0) {
				throw new Exception("cannot insert VirtualServer");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VIRTUAL_SERVER_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVirtualServer(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, id);
			boolean result = configVirtualServerService.delete(id);
			if (!result) {
				throw new Exception("cannot update VirtualServer");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_PREFIX + "/{id}")
	public RestResult<ConfigVirtualServer> selectConfigVirtualServer(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<ConfigVirtualServer> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, id);
			ConfigVirtualServer result = configVirtualServerService.select(id);
			if (null == result) {
				throw new Exception("cannot get VirtualServer");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_PREFIX)
	public RestResult<List<ConfigVirtualServer>> selectConfigVirtualServers(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId) {
		RestResult<List<ConfigVirtualServer>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			List<ConfigVirtualServer> result = configVirtualServerService.selectAll(configId);
			if (null == result) {
				throw new Exception("cannot get VirtualServer");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VIRTUAL_SERVER_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVirtualServer(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id,
			@RequestBody ConfigVirtualServer virtualServer) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, id);
			virtualServer.setId(id);
			virtualServer.setConfig_id(configId);
			boolean result = configVirtualServerService.update(virtualServer);
			if (!result) {
				throw new Exception("cannot update VirtualServer");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * virtualServerExt
	 */

	@ResponseBody
	@PostMapping(VIRTUAL_SERVER_EXT_PREFIX)
	public RestResult<Long> insertConfigVirtualServerExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@RequestBody ConfigVirtualServerExt virtualServerExt) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, virtualServerId);
			virtualServerExt.setVs_id(virtualServerId);
			long id = configVirtualServerExtService.insert(virtualServerExt);
			if (id < 0) {
				throw new Exception("cannot insert VirtualServer Ext");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VIRTUAL_SERVER_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVirtualServerExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerExtId(nodeId, configId, virtualServerId, id);
			boolean result = configVirtualServerExtService.delete(id);
			if (!result) {
				throw new Exception("cannot update VirtualServer Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_EXT_PREFIX + "/{id}")
	public RestResult<ConfigVirtualServerExt> selectConfigVirtualServerExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("id") long id) {
		RestResult<ConfigVirtualServerExt> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerExtId(nodeId, configId, virtualServerId, id);
			ConfigVirtualServerExt result = configVirtualServerExtService.select(id);
			if (null == result) {
				throw new Exception("cannot get VirtualServer Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_EXT_PREFIX)
	public RestResult<List<ConfigVirtualServerExt>> selectConfigVirtualServerExts(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId) {
		RestResult<List<ConfigVirtualServerExt>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, virtualServerId);
			List<ConfigVirtualServerExt> result = configVirtualServerExtService.selectAll(virtualServerId);
			if (null == result) {
				throw new Exception("cannot get VirtualServer Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VIRTUAL_SERVER_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVirtualServerExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("id") long id, @RequestBody ConfigVirtualServerExt virtualServerExt) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerExtId(nodeId, configId, virtualServerId, id);
			virtualServerExt.setId(id);
			virtualServerExt.setVs_id(virtualServerId);
			;
			boolean result = configVirtualServerExtService.update(virtualServerExt);
			if (!result) {
				throw new Exception("cannot update VirtualServer Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * virtualServerRealserver
	 */
	@ResponseBody
	@PostMapping(VIRTUAL_SERVER_REALSERVER_PREFIX)
	public RestResult<Long> insertConfigVirtualServerRealserver(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@RequestBody ConfigVirtualServerRealserver realserver) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, virtualServerId);
			realserver.setVirtualserver_id(virtualServerId);
			long id = configVirtualServerRealserverService.insert(realserver);
			if (id < 0) {
				throw new Exception("cannot insert realserver");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VIRTUAL_SERVER_REALSERVER_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVirtualServerRealserver(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, id);
			boolean result = configVirtualServerRealserverService.delete(id);
			if (!result) {
				throw new Exception("cannot update realserver");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_REALSERVER_PREFIX + "/{id}")
	public RestResult<ConfigVirtualServerRealserver> selectConfigVirtualServerRealserver(
			@PathVariable("nodeId") long nodeId, @PathVariable("configId") long configId,
			@PathVariable("virtualServerId") long virtualServerId, @PathVariable("id") long id) {
		RestResult<ConfigVirtualServerRealserver> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, id);
			ConfigVirtualServerRealserver result = configVirtualServerRealserverService.select(id);
			if (null == result) {
				throw new Exception("cannot get realserver");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_REALSERVER_PREFIX)
	public RestResult<List<ConfigVirtualServerRealserver>> selectConfigVirtualServerRealservers(
			@PathVariable("nodeId") long nodeId, @PathVariable("configId") long configId,
			@PathVariable("virtualServerId") long virtualServerId) {
		RestResult<List<ConfigVirtualServerRealserver>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerId(nodeId, configId, virtualServerId);
			List<ConfigVirtualServerRealserver> result = configVirtualServerRealserverService
					.selectAll(virtualServerId);
			if (null == result) {
				throw new Exception("cannot get realserver");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VIRTUAL_SERVER_REALSERVER_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVirtualServerRealserver(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("id") long id, @RequestBody ConfigVirtualServerRealserver realserver) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, id);
			realserver.setId(id);
			realserver.setVirtualserver_id(virtualServerId);
			boolean result = configVirtualServerRealserverService.update(realserver);
			if (!result) {
				throw new Exception("cannot update realserverExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * virtualServerRealserverExt
	 */

	@ResponseBody
	@PostMapping(VIRTUAL_SERVER_REALSERVER_EXT_PREFIX)
	public RestResult<Long> insertConfigVirtualServerRealserverExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("realServerId") long realServerId,
			@RequestBody ConfigVirtualServerRealserverExt realserverExt) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, realServerId);
			realserverExt.setRealserver_id(realServerId);
			long id = configVirtualServerRealserverExtService.insert(realserverExt);
			if (id < 0) {
				throw new Exception("cannot insert realserverExt");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VIRTUAL_SERVER_REALSERVER_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVirtualServerRealserverExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("realServerId") long realServerId, @PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverExtId(nodeId, configId, virtualServerId, realServerId, id);
			boolean result = configVirtualServerRealserverExtService.delete(id);
			if (!result) {
				throw new Exception("cannot delete realserverExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_REALSERVER_EXT_PREFIX + "/{id}")
	public RestResult<ConfigVirtualServerRealserverExt> selectConfigVirtualServerRealserverExt(
			@PathVariable("nodeId") long nodeId, @PathVariable("configId") long configId,
			@PathVariable("virtualServerId") long virtualServerId, @PathVariable("realServerId") long realServerId,
			@PathVariable("id") long id) {
		RestResult<ConfigVirtualServerRealserverExt> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverExtId(nodeId, configId, virtualServerId, realServerId, id);
			ConfigVirtualServerRealserverExt result = configVirtualServerRealserverExtService.select(id);
			if (null == result) {
				throw new Exception("cannot get realserverExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VIRTUAL_SERVER_REALSERVER_EXT_PREFIX)
	public RestResult<List<ConfigVirtualServerRealserverExt>> selectConfigVirtualServerRealserverExts(
			@PathVariable("nodeId") long nodeId, @PathVariable("configId") long configId,
			@PathVariable("virtualServerId") long virtualServerId, @PathVariable("realServerId") long realServerId) {
		RestResult<List<ConfigVirtualServerRealserverExt>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverId(nodeId, configId, virtualServerId, realServerId);
			List<ConfigVirtualServerRealserverExt> result = configVirtualServerRealserverExtService
					.selectAll(realServerId);
			if (null == result) {
				throw new Exception("cannot get realserverExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VIRTUAL_SERVER_REALSERVER_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVirtualServerRealserverExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("virtualServerId") long virtualServerId,
			@PathVariable("realServerId") long realServerId, @PathVariable("id") long id,
			@RequestBody ConfigVirtualServerRealserverExt realserverExt) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVirtualServerRealserverExtId(nodeId, configId, virtualServerId, realServerId, id);
			realserverExt.setId(id);
			realserverExt.setRealserver_id(realServerId);
			boolean result = configVirtualServerRealserverExtService.update(realserverExt);
			if (!result) {
				throw new Exception("cannot update realserverExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * vrrpInstance
	 */

	@ResponseBody
	@PostMapping(VRRP_INSTANCE_PREFIX)
	public RestResult<Long> insertConfigVrrpInstance(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @RequestBody ConfigVrrpInstance vrrpInstance) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			vrrpInstance.setConfig_id(configId);
			long id = configVrrpInstanceService.insert(vrrpInstance);
			if (id < 0) {
				throw new Exception("cannot insert VirtualServer");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VRRP_INSTANCE_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVrrpInstance(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, id);
			boolean result = configVrrpInstanceService.delete(id);
			if (!result) {
				throw new Exception("cannot update vrrpinstance");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_PREFIX + "/{id}")
	public RestResult<ConfigVrrpInstance> selectConfigVrrpInstance(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<ConfigVrrpInstance> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, id);
			ConfigVrrpInstance result = configVrrpInstanceService.select(id);
			if (null == result) {
				throw new Exception("cannot get vrrpinstance");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_PREFIX)
	public RestResult<List<ConfigVrrpInstance>> selectConfigVrrpInstances(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId) {
		RestResult<List<ConfigVrrpInstance>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			List<ConfigVrrpInstance> result = configVrrpInstanceService.selectAll(configId);
			if (null == result) {
				throw new Exception("cannot get vrrpinstance");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VRRP_INSTANCE_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVrrpInstance(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id,
			@RequestBody ConfigVrrpInstance vrrpInstance) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, id);
			vrrpInstance.setId(id);
			vrrpInstance.setConfig_id(configId);
			boolean result = configVrrpInstanceService.update(vrrpInstance);
			if (!result) {
				throw new Exception("cannot update vrrpinstance");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}
	
	/*
	 * vrrpInstanceVip
	 * 
	 */
	@ResponseBody
	@PostMapping( VRRP_INSTANCE_VIP_PREFIX)
	public RestResult<Long> insertConfigVrrpInstanceVip(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@RequestBody ConfigVrrpInstanceVip vrrpInstanceVip) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, vrrpInstanceId);
			vrrpInstanceVip.setInstance_id(vrrpInstanceId);
			long id = configVrrpInstanceVipService.insert(vrrpInstanceVip);
			if (id < 0) {
				throw new Exception("cannot insert vrrpInstanceVIP");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VRRP_INSTANCE_VIP_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVrrpInstanceVip(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceVipId(nodeId, configId, vrrpInstanceId, id);
			boolean result = configVrrpInstanceVipService.delete(id);
			if (!result) {
				throw new Exception("cannot delete vrrpInstanceExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_VIP_PREFIX + "/{id}")
	public RestResult<ConfigVrrpInstanceVip> selectConfigVrrpInstanceVip(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id) {
		RestResult<ConfigVrrpInstanceVip> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceVipId(nodeId, configId, vrrpInstanceId, id);
			ConfigVrrpInstanceVip result = configVrrpInstanceVipService.select(id);
			if (null == result) {
				throw new Exception("cannot get vrrpinstanceVIP");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_VIP_PREFIX)
	public RestResult<List<ConfigVrrpInstanceVip>> selectConfigVrrpInstanceVips(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId) {
		RestResult<List<ConfigVrrpInstanceVip>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, vrrpInstanceId);
			List<ConfigVrrpInstanceVip> result = configVrrpInstanceVipService.selectAll(vrrpInstanceId);
			if (null == result) {
				throw new Exception("cannot get vrrpInstanceVIP");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VRRP_INSTANCE_VIP_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVrrpInstanceVip(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id, @RequestBody ConfigVrrpInstanceVip vrrpInstanceVip) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceVipId(nodeId, configId, vrrpInstanceId, id);
			vrrpInstanceVip.setId(id);
			vrrpInstanceVip.setInstance_id(vrrpInstanceId);
			boolean result = configVrrpInstanceVipService.update(vrrpInstanceVip);
			if (!result) {
				throw new Exception("cannot update vrrpInstanceVIP");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}
	/*
	 * vrrpInstanceExt
	 */
	@ResponseBody
	@PostMapping(VRRP_INSTANCE_EXT_PREFIX)
	public RestResult<Long> insertConfigVrrpInstanceExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@RequestBody ConfigVrrpInstanceExt vrrpInstanceExt) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, vrrpInstanceId);
			vrrpInstanceExt.setInstance_id(vrrpInstanceId);
			long id = configVrrpInstanceExtService.insert(vrrpInstanceExt);
			if (id < 0) {
				throw new Exception("cannot insert vrrpInstance Ext");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VRRP_INSTANCE_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVrrpInstanceExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceExtId(nodeId, configId, vrrpInstanceId, id);
			boolean result = configVrrpInstanceExtService.delete(id);
			if (!result) {
				throw new Exception("cannot delete vrrpInstanceExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_EXT_PREFIX + "/{id}")
	public RestResult<ConfigVrrpInstanceExt> selectConfigVrrpInstanceExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id) {
		RestResult<ConfigVrrpInstanceExt> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceExtId(nodeId, configId, vrrpInstanceId, id);
			ConfigVrrpInstanceExt result = configVrrpInstanceExtService.select(id);
			if (null == result) {
				throw new Exception("cannot get vrrpinstance Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_INSTANCE_EXT_PREFIX)
	public RestResult<List<ConfigVrrpInstanceExt>> selectConfigVrrpInstanceExts(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId) {
		RestResult<List<ConfigVrrpInstanceExt>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceId(nodeId, configId, vrrpInstanceId);
			List<ConfigVrrpInstanceExt> result = configVrrpInstanceExtService.selectAll(vrrpInstanceId);
			if (null == result) {
				throw new Exception("cannot get vrrpInstance Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VRRP_INSTANCE_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVrrpInstanceExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpInstanceId") long vrrpInstanceId,
			@PathVariable("id") long id, @RequestBody ConfigVrrpInstanceExt vrrpInstanceExt) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpInstanceExtId(nodeId, configId, vrrpInstanceId, id);
			vrrpInstanceExt.setId(id);
			vrrpInstanceExt.setInstance_id(vrrpInstanceId);
			boolean result = configVrrpInstanceExtService.update(vrrpInstanceExt);
			if (!result) {
				throw new Exception("cannot update vrrpInstance Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * vrrpSyncGroup
	 */
	@ResponseBody
	@PostMapping(VRRP_SYNC_GROUP_PREFIX)
	public RestResult<Long> insertConfigVrrpSyncGroup(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @RequestBody ConfigVrrpSyncGroup vrrpSyncGroup) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			vrrpSyncGroup.setConfig_id(configId);
			long id = configVrrpSyncGroupService.insert(vrrpSyncGroup);
			if (id < 0) {
				throw new Exception("cannot insert vrrpsyncgroup");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VRRP_SYNC_GROUP_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVrrpSyncGroup(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupId(nodeId, configId, id);
			boolean result = configVrrpSyncGroupService.delete(id);
			if (!result) {
				throw new Exception("cannot delete vrrpsyncgroup");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_SYNC_GROUP_PREFIX + "/{id}")
	public RestResult<ConfigVrrpSyncGroup> selectConfigVrrpSyncGroup(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id) {
		RestResult<ConfigVrrpSyncGroup> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupId(nodeId, configId, id);
			ConfigVrrpSyncGroup result = configVrrpSyncGroupService.select(id);
			if (null == result) {
				throw new Exception("cannot get VrrpSyncGroup");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_SYNC_GROUP_PREFIX)
	public RestResult<List<ConfigVrrpSyncGroup>> selectConfigVrrpSyncGroups(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId) {
		RestResult<List<ConfigVrrpSyncGroup>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigId(nodeId, configId);
			List<ConfigVrrpSyncGroup> result = configVrrpSyncGroupService.selectAll(configId);
			if (null == result) {
				throw new Exception("cannot get vrrpSyncGroup");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VRRP_SYNC_GROUP_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVrrpSyncGroup(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("id") long id,
			@RequestBody ConfigVrrpSyncGroup vrrpSyncGroup) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupId(nodeId, configId, id);
			vrrpSyncGroup.setId(id);
			vrrpSyncGroup.setConfig_id(configId);
			boolean result = configVrrpSyncGroupService.update(vrrpSyncGroup);
			if (!result) {
				throw new Exception("cannot update vrrpSyncGroup");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	/*
	 * vrrpSyncGroupExt
	 */
	@ResponseBody
	@PostMapping(VRRP_SYNC_GROUP_EXT_PREFIX)
	public RestResult<Long> insertConfigVrrpSyncGroupExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpsyncGroupId") long vrrpsyncGroupId,
			@RequestBody ConfigVrrpSyncGroupExt vrrpSyncGroupExt) {
		RestResult<Long> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupId(nodeId, configId, vrrpsyncGroupId);
			vrrpSyncGroupExt.setVrrpgroup_id(vrrpsyncGroupId);
			long id = configVrrpSyncGroupExtService.insert(vrrpSyncGroupExt);
			if (id < 0) {
				throw new Exception("cannot insert vrrpsyncgroup");
			}
			restResult.setData(id);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(-1L);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@DeleteMapping(VRRP_SYNC_GROUP_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> deleteConfigVrrpSyncGroupExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpsyncGroupId") long vrrpsyncGroupId,
			@PathVariable("id") long id) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupExtId(nodeId, configId, vrrpsyncGroupId, id);
			boolean result = configVrrpSyncGroupExtService.delete(id);
			if (!result) {
				throw new Exception("cannot delete VrrpSyncGroupExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_SYNC_GROUP_EXT_PREFIX + "/{id}")
	public RestResult<ConfigVrrpSyncGroupExt> selectConfigVrrpSyncGroupExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpsyncGroupId") long vrrpsyncGroupId,
			@PathVariable("id") long id) {
		RestResult<ConfigVrrpSyncGroupExt> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupExtId(nodeId, configId, vrrpsyncGroupId, id);
			ConfigVrrpSyncGroupExt result = configVrrpSyncGroupExtService.select(id);
			if (null == result) {
				throw new Exception("cannot get vrrpsyncgroup Ext");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@GetMapping(VRRP_SYNC_GROUP_EXT_PREFIX)
	public RestResult<List<ConfigVrrpSyncGroupExt>> selectConfigVrrpSyncGroupExts(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpsyncGroupId") long vrrpsyncGroupId) {
		RestResult<List<ConfigVrrpSyncGroupExt>> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupId(nodeId, configId, vrrpsyncGroupId);
			List<ConfigVrrpSyncGroupExt> result = configVrrpSyncGroupExtService.selectAll(vrrpsyncGroupId);
			if (null == result) {
				throw new Exception("cannot get vrrpSyncGroupExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(null);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}

	@ResponseBody
	@PutMapping(VRRP_SYNC_GROUP_EXT_PREFIX + "/{id}")
	public RestResult<Boolean> updateConfigVrrpSyncGroupExt(@PathVariable("nodeId") long nodeId,
			@PathVariable("configId") long configId, @PathVariable("vrrpsyncGroupId") long vrrpsyncGroupId,
			@PathVariable("id") long id, @RequestBody ConfigVrrpSyncGroupExt vrrpSyncGroupExt) {
		RestResult<Boolean> restResult = new RestResult<>();
		try {
			// check id chain
			chainConfigVrrpSyncGroupExtId(nodeId, configId, vrrpsyncGroupId, id);
			vrrpSyncGroupExt.setId(id);
			vrrpSyncGroupExt.setVrrpgroup_id(vrrpsyncGroupId);
			boolean result = configVrrpSyncGroupExtService.update(vrrpSyncGroupExt);
			if (!result) {
				throw new Exception("cannot update vrrpSyncGroupExt");
			}
			restResult.setData(result);
		} catch (Exception e) {
			restResult.setCode(-1);
			restResult.setData(false);
			restResult.setMessage(e.getMessage());
		}
		return restResult;
	}
}
