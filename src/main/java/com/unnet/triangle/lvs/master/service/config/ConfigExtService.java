package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigExt;

public interface ConfigExtService {
	public long insert(ConfigExt configExt);

	public boolean delete(long id);

	public List<ConfigExt> selectAll(Long configId);

	public ConfigExt select(long id);

	public boolean update(ConfigExt configExt);
}
