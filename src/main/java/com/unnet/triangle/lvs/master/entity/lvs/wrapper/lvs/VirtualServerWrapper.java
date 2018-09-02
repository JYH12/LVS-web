package com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVirtualServer;
import com.unnet.triangle.lvs.master.model.ConfigVirtualServerExt;

/*
 * @author john
 */
public class VirtualServerWrapper extends ConfigVirtualServer {
	List<RealServerWrapper> realServer;
	List<ConfigVirtualServerExt> exts;
	private String checksum = "";

	public List<RealServerWrapper> getRealServer() {
		return realServer;
	}

	public void setRealServer(List<RealServerWrapper> realServer) {
		this.realServer = realServer;
	}

	public List<ConfigVirtualServerExt> getExts() {
		return exts;
	}

	public void setExts(List<ConfigVirtualServerExt> exts) {
		this.exts = exts;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}
