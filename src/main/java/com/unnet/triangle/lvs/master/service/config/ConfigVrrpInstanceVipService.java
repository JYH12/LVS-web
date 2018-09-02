package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceVip;

public interface ConfigVrrpInstanceVipService {
	public long insert(ConfigVrrpInstanceVip vrrpInstanceVip);

	public boolean delete(long id);

	public List<ConfigVrrpInstanceVip> selectAll(long instanceId);

	public ConfigVrrpInstanceVip select(long id);

	Boolean update(ConfigVrrpInstanceVip vrrpInstanceVip);
}
