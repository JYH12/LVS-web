package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigExtMapper;
import com.unnet.triangle.lvs.master.model.ConfigExt;
import com.unnet.triangle.lvs.master.model.ConfigExtExample;
import com.unnet.triangle.lvs.master.model.ConfigExtExample.Criteria;
import com.unnet.triangle.lvs.master.service.config.ConfigExtService;

/*
 * @author john
 */
@Service
public class ConfigExtServiceImpl implements ConfigExtService {

	@Autowired
	ConfigExtMapper configExtMapper;

	@Override
	public long insert(ConfigExt configExt) {
		long configExtId=configExtMapper.insertSelective(configExt);
		if (configExtId > 0) {
			return configExt.getId();
		} else {
			return -1;
		}
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return configExtMapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigExt> selectAll(Long configId) {
		// TODO Auto-generated method stub
		ConfigExtExample example = new ConfigExtExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfig_idEqualTo(configId);
		return configExtMapper.selectByExample(example);
	}

	@Override
	public ConfigExt select(long id) {
		// TODO Auto-generated method stub
		return configExtMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean update(ConfigExt configExt) {
		// TODO Auto-generated method stub
		return configExtMapper.updateByPrimaryKey(configExt) > 0;
	}

}
