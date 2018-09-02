package com.unnet.triangle.lvs.master.service.config;

import java.util.List;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpSyncGroupWrapper;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroup;

public interface ConfigVrrpSyncGroupService {

	public long insert(ConfigVrrpSyncGroup vrrpSyncGroup);

	public boolean delete(long id);

	public List<ConfigVrrpSyncGroup> selectAll(long configId);

	public ConfigVrrpSyncGroup select(long id);

	Boolean update(ConfigVrrpSyncGroup vrrpSyncGroup);

	List<VrrpSyncGroupWrapper> selectWrappers(long configId);
}
