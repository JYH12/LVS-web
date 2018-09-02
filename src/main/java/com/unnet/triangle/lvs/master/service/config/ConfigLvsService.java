package com.unnet.triangle.lvs.master.service.config;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.LVSWrapper;

public interface ConfigLvsService {
	public LVSWrapper select(long nodeId);
}
