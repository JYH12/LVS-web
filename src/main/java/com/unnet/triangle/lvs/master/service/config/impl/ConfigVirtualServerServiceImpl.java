package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs.VirtualServerWrapper;
import com.unnet.triangle.lvs.master.mapper.ConfigVirtualServerMapper;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServer;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerService;

@Service
public class ConfigVirtualServerServiceImpl implements ConfigVirtualServerService {

	@Autowired
	ConfigVirtualServerMapper mapper;
	@Autowired
	ConfigVirtualServerRealserverService realserverService;
	@Autowired
	ConfigVirtualServerExtService extService;

	@Override
	public long insert(ConfigVirtualServer virtualServer) {
		// TODO Auto-generated method stub
		long vsId = mapper.insertSelective(virtualServer);
		if (vsId > 0) {
			return virtualServer.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVirtualServer> selectAll(long configId) {
		// TODO Auto-generated method stub
		ConfigVirtualServerExample example = new ConfigVirtualServerExample();
		example.createCriteria().andConfig_idEqualTo(configId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVirtualServer select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVirtualServer virtualServer) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(virtualServer) > 0;
	}

	@Override
	public List<VirtualServerWrapper> selectWrappers(long configId) {
		// TODO Auto-generated method stub
		List<VirtualServerWrapper> wrappers = new ArrayList<>();
		List<ConfigVirtualServer> virtualServers = selectAll(configId);
		for (ConfigVirtualServer virtualServer : virtualServers) {
			VirtualServerWrapper wrapper = new VirtualServerWrapper();
			try {
				BeanUtils.copyProperties(wrapper, virtualServer);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			wrapper.setExts(extService.selectAll(virtualServer.getId()));
			wrapper.setRealServer(realserverService.selectWrappers(virtualServer.getId()));
			wrappers.add(wrapper);
		}
		return wrappers;
	}

}
