package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigVrrpInstanceExtMapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExt;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExtExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceExtService;

@Service
public class ConfigVrrpInstanceExtServiceImpl implements ConfigVrrpInstanceExtService {

	@Autowired
	ConfigVrrpInstanceExtMapper mapper;

	@Override
	public long insert(ConfigVrrpInstanceExt vrrpInstanceExt) {
		// TODO Auto-generated method stub
		long instanceExtId = mapper.insertSelective(vrrpInstanceExt);
		if (instanceExtId > 0) {
			return vrrpInstanceExt.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVrrpInstanceExt> selectAll(long instanceId) {
		// TODO Auto-generated method stub
		ConfigVrrpInstanceExtExample example = new ConfigVrrpInstanceExtExample();
		example.createCriteria().andInstance_idEqualTo(instanceId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVrrpInstanceExt select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVrrpInstanceExt vrrpInstanceExt) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(vrrpInstanceExt) > 0;
	}
}
