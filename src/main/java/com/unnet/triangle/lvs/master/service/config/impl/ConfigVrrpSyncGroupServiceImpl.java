package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpSyncGroupWrapper;
import com.unnet.triangle.lvs.master.mapper.ConfigVrrpSyncGroupMapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroup;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupService;

@Service
public class ConfigVrrpSyncGroupServiceImpl implements ConfigVrrpSyncGroupService {

	@Autowired
	ConfigVrrpSyncGroupMapper mapper;
	@Autowired
	ConfigVrrpSyncGroupExtService extService;

	@Override
	public long insert(ConfigVrrpSyncGroup vrrpSyncGroup) {
		// TODO Auto-generated method stub
		long instanceId = mapper.insertSelective(vrrpSyncGroup);
		if (instanceId > 0) {
			return vrrpSyncGroup.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVrrpSyncGroup> selectAll(long configId) {
		// TODO Auto-generated method stub
		ConfigVrrpSyncGroupExample example = new ConfigVrrpSyncGroupExample();
		example.createCriteria().andConfig_idEqualTo(configId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVrrpSyncGroup select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVrrpSyncGroup vrrpSyncGroup) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(vrrpSyncGroup) > 0;
	}

	@Override
	public List<VrrpSyncGroupWrapper> selectWrappers(long configId) {
		// TODO Auto-generated method stub
		List<VrrpSyncGroupWrapper> wrappers = new ArrayList<>();
		List<ConfigVrrpSyncGroup> vrrpSyncGroups = selectAll(configId);
		for (ConfigVrrpSyncGroup vrrpSyncGroup : vrrpSyncGroups) {
			VrrpSyncGroupWrapper wrapper = new VrrpSyncGroupWrapper();
			try {
				BeanUtils.copyProperties(wrapper, vrrpSyncGroup);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			wrapper.setExts(extService.selectAll(vrrpSyncGroup.getId()));
			wrappers.add(wrapper);
		}
		return wrappers;
	}

}
