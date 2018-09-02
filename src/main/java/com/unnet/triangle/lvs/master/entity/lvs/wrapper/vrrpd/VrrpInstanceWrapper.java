package com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd;

import java.util.List;

import com.unnet.triangle.lvs.master.model.ConfigVrrpInstance;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceExt;
import com.unnet.triangle.lvs.master.model.ConfigVrrpInstanceVip;

/*
 * @author john
 */
public class VrrpInstanceWrapper extends ConfigVrrpInstance {
	List<ConfigVrrpInstanceVip> vips;
	List<ConfigVrrpInstanceExt> exts;
	private String checksum = "";

	public List<ConfigVrrpInstanceVip> getVips() {
		return vips;
	}

	public void setVips(List<ConfigVrrpInstanceVip> vips) {
		this.vips = vips;
	}

	public List<ConfigVrrpInstanceExt> getExts() {
		return exts;
	}

	public void setExts(List<ConfigVrrpInstanceExt> exts) {
		this.exts = exts;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
}
