package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExt;

/*
 * @author john
 */
public interface ConfigVirtualServerRealserverExtService {

	public long insert(ConfigVirtualServerRealserverExt realserverExt);

	public boolean delete(long id);

	public List<ConfigVirtualServerRealserverExt> selectAll(Long rsId);

	public ConfigVirtualServerRealserverExt select(long id);

	public boolean update(ConfigVirtualServerRealserverExt configExt);
}
