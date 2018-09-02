package com.unnet.triangle.lvs.master.service.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unnet.triangle.lvs.master.mapper.ConfigVirtualServerExtMapper;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExt;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExtExample;
import com.unnet.triangle.lvs.master.service.config.ConfigVirtualServerExtService;

/*
 * @author john
 */
@Service
public class ConfigVirtualServerExtServiceImpl implements ConfigVirtualServerExtService {

	@Autowired
	ConfigVirtualServerExtMapper mapper;

	@Override
	public long insert(ConfigVirtualServerExt configVirtualServerExt) {
		// TODO Auto-generated method stub
		long vsExtId=mapper.insertSelective(configVirtualServerExt);
		if (vsExtId > 0) {
			return configVirtualServerExt.getId();
		}
		return -1;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id) > 0;
	}

	@Override
	public List<ConfigVirtualServerExt> selectAll(Long vsId) {
		// TODO Auto-generated method stub
		ConfigVirtualServerExtExample example = new ConfigVirtualServerExtExample();
		example.createCriteria().andIdEqualTo(vsId);
		return mapper.selectByExample(example);
	}

	@Override
	public ConfigVirtualServerExt select(long id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean update(ConfigVirtualServerExt configVirtualServerExt) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(configVirtualServerExt) > 0;
	}

}
