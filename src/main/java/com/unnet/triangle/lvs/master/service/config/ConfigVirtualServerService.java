package com.unnet.triangle.lvs.master.service.config;

import java.util.List;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs.VirtualServerWrapper;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServer;

public interface ConfigVirtualServerService {
	public long insert(ConfigVirtualServer virtualServer);
	
	public boolean delete(long id);
	
	public List<ConfigVirtualServer> selectAll(long configId);

	public ConfigVirtualServer select(long id);

	Boolean update(ConfigVirtualServer virtualServer);
	
	List<VirtualServerWrapper> selectWrappers(long configId);
}
