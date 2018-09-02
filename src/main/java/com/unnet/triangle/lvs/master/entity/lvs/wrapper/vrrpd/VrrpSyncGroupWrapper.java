package com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroup;
import com.unnet.triangle.lvs.master.model.ConfigVrrpSyncGroupExt;

/*
 * @author john
 */
public class VrrpSyncGroupWrapper extends ConfigVrrpSyncGroup {
	List<ConfigVrrpSyncGroupExt> exts;
	private String checksum = "";

	public List<ConfigVrrpSyncGroupExt> getExts() {
		return exts;
	}

	public void setExts(List<ConfigVrrpSyncGroupExt> exts) {
		this.exts = exts;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

}
