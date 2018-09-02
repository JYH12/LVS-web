package com.unnet.triangle.lvs.master.service.config;

import com.unnet.triangle.lvs.master.model.Config;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.ConfigWrapper;

public interface ConfigService {
	Boolean update(Config config);

	Config selectAll(long nodeId);

	Config select(long id);

	long deployByNodeId(long nodeId);

	ConfigWrapper selectWrapper(long nodeId);
}
