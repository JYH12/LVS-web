package com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserver;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerRealserverExt;

/*
 * @author john
 */
public class RealServerWrapper extends ConfigVirtualServerRealserver {
	List<ConfigVirtualServerRealserverExt> exts;

	public List<ConfigVirtualServerRealserverExt> getExts() {
		return exts;
	}

	public void setExts(List<ConfigVirtualServerRealserverExt> exts) {
		this.exts = exts;
	}
}
