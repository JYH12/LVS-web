package com.unnet.triangle.lvs.master.entity.lvs.wrapper;

import java.util.List;

import com.unnet.triangle.lvs.master.model.Config;
import com.unnet.triangle.lvs.master.model.ConfigExt;

/*
 * @author john
 */

public class ConfigWrapper extends Config {
	List<ConfigExt> exts;

	private String checksum = "";

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public List<ConfigExt> getExts() {
		return exts;
	}

	public void setExts(List<ConfigExt> exts) {
		this.exts = exts;
	}
}
