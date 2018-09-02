package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.LVSWrapper;
import com.unnet.triangle.lvs.master.service.config.ConfigLvsService;
import com.unnet.triangle.lvs.master.service.config.ConfigService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupService;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.ConfigWrapper;

@Service
public class ConfigLvsServiceImpl implements ConfigLvsService {
	@Autowired
	ConfigService configService;
	@Autowired
	ConfigVirtualServerService virtualServerService;
	@Autowired
	ConfigVrrpInstanceService vrrpInstanceService;
	@Autowired
	ConfigVrrpSyncGroupService vrrpSyncGroupSerice;
	@Override
	public LVSWrapper select(long nodeId) {
		// TODO Auto-generated method stub
		ConfigWrapper configWrapper = configService.selectWrapper(nodeId);
		LVSWrapper lvsWrapper = new LVSWrapper();
		try {
			BeanUtils.copyProperties(lvsWrapper, configWrapper);
		} catch (IllegalAccessException | InvocationTargetException e) {
			return null;
		}
		long configId = configWrapper.getConfig_id();

		// instances
		lvsWrapper.setVrrpInstances(vrrpInstanceService.selectWrappers(configId));
		//virtualserver
		lvsWrapper.setVirtualServers(virtualServerService.selectWrappers(configId));
		// vrrpsyncgroup
		lvsWrapper.setVrrpSyncGroups(vrrpSyncGroupSerice.selectWrappers(configId));
		
		return lvsWrapper;
	}

}
