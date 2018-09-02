package com.unnet.triangle.lvs.master.service.config;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExt;

/*
 * @author john
 */
public interface ConfigVirtualServerExtService {
	public long insert(ConfigVirtualServerExt configVirtualServerExt);

	public boolean delete(long id);

	public List<ConfigVirtualServerExt> selectAll(Long vsId);

	public ConfigVirtualServerExt select(long id);

	public boolean update(ConfigVirtualServerExt configVirtualServerExt);
}
