package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigVrrpSyncGroupExtMapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExt;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExtExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVrrpSyncGroupExtService;

@Service
public class ConfigVrrpSyncGroupExtServiceImpl implements ConfigVrrpSyncGroupExtService {

	@Autowired
	ConfigVrrpSyncGroupExtMapper mapper;

	@Override
	public long insert(ConfigVrrpSyncGroupExt vrrpSyncGroupExt) {
		// TODO Auto-generated method stub
		long vsgId = mapper.insertSelective(vrrpSyncGroupExt);
		if (vsgId > 0) {
			return vrrpSyncGroupExt.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVrrpSyncGroupExt> selectAll(long vrrpGroupId) {
		// TODO Auto-generated method stub
		ConfigVrrpSyncGroupExtExample example = new ConfigVrrpSyncGroupExtExample();
		example.createCriteria().andVrrpgroup_idEqualTo(vrrpGroupId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVrrpSyncGroupExt select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public Boolean update(ConfigVrrpSyncGroupExt vrrpSyncGroupExt) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(vrrpSyncGroupExt) > 0;
	}

}
