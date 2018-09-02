package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExt;

public interface ConfigVrrpSyncGroupExtService {
	public long insert(ConfigVrrpSyncGroupExt vrrpSyncGroupExt);

	public boolean delete(long id);

	public List<ConfigVrrpSyncGroupExt> selectAll(long vrrpGroupId);

	public ConfigVrrpSyncGroupExt select(long id);

	Boolean update(ConfigVrrpSyncGroupExt vrrpSyncGroupExt);
}
