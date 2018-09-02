package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs.RealServerWrapper;

import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserver;

public interface ConfigVirtualServerRealserverService {
	public long insert(ConfigVirtualServerRealserver realserver);

	public boolean delete(long id);

	public List<ConfigVirtualServerRealserver> selectAll(long vsId);

	public ConfigVirtualServerRealserver select(long id);

	public boolean update(ConfigVirtualServerRealserver realserverConfig);
	
	public List<RealServerWrapper> selectWrappers(long vsId);
}
