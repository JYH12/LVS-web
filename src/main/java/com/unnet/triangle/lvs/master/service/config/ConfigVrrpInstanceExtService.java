package com.unnet.triangle.lvs.master.service.config;

import java.util.List;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExt;

public interface ConfigVrrpInstanceExtService {
	public long insert(ConfigVrrpInstanceExt vrrpInstanceExt);

	public boolean delete(long id);

	public List<ConfigVrrpInstanceExt> selectAll(long instanceId);

	public ConfigVrrpInstanceExt select(long id);

	Boolean update(ConfigVrrpInstanceExt vrrpInstanceExt);
}
