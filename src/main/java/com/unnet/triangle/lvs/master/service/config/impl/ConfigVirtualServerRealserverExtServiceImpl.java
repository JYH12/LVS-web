package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigVirtualServerRealserverExtMapper;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExt;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExtExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerRealserverExtService;

/*
 * @author
 */
@Service
public class ConfigVirtualServerRealserverExtServiceImpl implements ConfigVirtualServerRealserverExtService {

	@Autowired
	ConfigVirtualServerRealserverExtMapper mapper;

	@Override
	public long insert(ConfigVirtualServerRealserverExt realserverExt) {
		// TODO Auto-generated method stub
		long rsExtId = mapper.insertSelective(realserverExt);
		if (rsExtId > 0) {
			return realserverExt.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVirtualServerRealserverExt> selectAll(Long rsId) {
		// TODO Auto-generated method stub
		ConfigVirtualServerRealserverExtExample example = new ConfigVirtualServerRealserverExtExample();
		example.createCriteria().andIdEqualTo(rsId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVirtualServerRealserverExt select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean update(ConfigVirtualServerRealserverExt configExt) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(configExt) > 0;
	}

}
