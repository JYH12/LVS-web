package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigVrrpInstanceVipMapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceVip;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceVipExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpInstanceVipService;

@Service
public class ConfigVrrpInstanceVipServiceImpl implements ConfigVrrpInstanceVipService {

	@Autowired
	ConfigVrrpInstanceVipMapper mapper;

	@Override
	public long insert(ConfigVrrpInstanceVip vrrpInstanceVip) {
		// TODO Auto-generated method stub
		long vipId = mapper.insertSelective(vrrpInstanceVip);
		if (vipId > 0) {
			return vrrpInstanceVip.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVrrpInstanceVip> selectAll(long instanceId) {
		// TODO Auto-generated method stub
		ConfigVrrpInstanceVipExample example = new ConfigVrrpInstanceVipExample();
		example.createCriteria().andInstance_idEqualTo(instanceId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVrrpInstanceVip select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVrrpInstanceVip vrrpInstanceVip) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(vrrpInstanceVip) > 0;
	}

}
