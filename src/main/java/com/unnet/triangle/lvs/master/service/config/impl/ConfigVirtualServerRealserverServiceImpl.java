package com.unnet.triangle.lvs.master.service.config.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs.RealServerWrapper;
import com.unnet.triangle.lvs.master.mapper.ConfigVirtualServerRealserverMapper;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserver;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverExtService;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverService;

@Service
public class ConfigVirtualServerRealserverServiceImpl implements ConfigVirtualServerRealserverService {

	@Autowired
	ConfigVirtualServerRealserverMapper mapper;
	@Autowired
	ConfigVirtualServerRealserverExtService extService;

	@Override
	public long insert(ConfigVirtualServerRealserver realserver) {
		// TODO Auto-generated method stub
		long rsId=mapper.insertSelective(realserver);
		if (rsId > 0) {
			return realserver.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVirtualServerRealserver> selectAll(long vsId) {
		// TODO Auto-generated method stub
		ConfigVirtualServerRealserverExample example = new ConfigVirtualServerRealserverExample();
		example.createCriteria().andVirtualserver_idEqualTo(vsId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVirtualServerRealserver select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean update(ConfigVirtualServerRealserver realserverConfig) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(realserverConfig) > 0;
	}

	@Override
	public List<RealServerWrapper> selectWrappers(long vsId) {
		// TODO Auto-generated method stub
		List<ConfigVirtualServerRealserver> realservers = selectAll(vsId);
		List<RealServerWrapper> realServerWrappers = new ArrayList<>();
		for (ConfigVirtualServerRealserver realserver : realservers) {
			RealServerWrapper wrapper = new RealServerWrapper();
			try {
				BeanUtils.copyProperties(wrapper, realserver);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			wrapper.setExts(extService.selectAll(realserver.getId()));
			realServerWrappers.add(wrapper);
		}
		return realServerWrappers;
	}

}
