package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpInstanceWrapper;
import com.unnet.triangle.lvs.master.mapper.ConfigVrrpInstanceMapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstance;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceService;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceVipService;

@Service
public class ConfigVrrpInstanceServiceImpl implements ConfigVrrpInstanceService {

	@Autowired
	ConfigVrrpInstanceMapper mapper;
	@Autowired
	ConfigVrrpInstanceExtService extService;
	@Autowired
	ConfigVrrpInstanceVipService vipService;

	@Override
	public long insert(ConfigVrrpInstance vrrpInstance) {
		// TODO Auto-generated method stub
		long instanceId=mapper.insertSelective(vrrpInstance);
		if (instanceId > 0) {
			return vrrpInstance.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVrrpInstance> selectAll(long configId) {
		// TODO Auto-generated method stub
		ConfigVrrpInstanceExample example = new ConfigVrrpInstanceExample();
		example.createCriteria().andConfig_idEqualTo(configId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVrrpInstance select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVrrpInstance vrrpInstance) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(vrrpInstance) > 0;
	}

	@Override
	public List<VrrpInstanceWrapper> selectWrappers(long configId) {
		// TODO Auto-generated method stub
		List<VrrpInstanceWrapper> wrappers = new ArrayList<>();
		List<ConfigVrrpInstance> instances = selectAll(configId);
		for (ConfigVrrpInstance vrrpInstance : instances) {
			VrrpInstanceWrapper wrapper = new VrrpInstanceWrapper();
			try {
				BeanUtils.copyProperties(wrapper, vrrpInstance);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			wrapper.setExts(extService.selectAll(vrrpInstance.getId()));
			wrapper.setVips(vipService.selectAll(vrrpInstance.getId()));
			wrappers.add(wrapper);
		}
		return wrappers;
	}

}
