package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpInstanceWrapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstance;

public interface ConfigVrrpInstanceService {

	public long insert(ConfigVrrpInstance vrrpInstance);

	public boolean delete(long id);

	public List<ConfigVrrpInstance> selectAll(long configId);

	public ConfigVrrpInstance select(long id);

	Boolean update(ConfigVrrpInstance vrrpInstance);

	List<VrrpInstanceWrapper> selectWrappers(long configId);
}
