package com.unnet.triangle.lvs.master.entity.lvs.wrapper;

import java.util.List;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.lvs.VirtualServerWrapper;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpInstanceWrapper;
import com.unnet.triangle.lvs.master.entity.lvs.wrapper.vrrpd.VrrpSyncGroupWrapper;

/*
 * @author john
 */
public class LVSWrapper extends ConfigWrapper {
	List<VirtualServerWrapper> virtualServers;
	List<VrrpInstanceWrapper> vrrpInstances;
	List<VrrpSyncGroupWrapper> vrrpSyncGroups;

	public List<VirtualServerWrapper> getVirtualServers() {
		return virtualServers;
	}

	public void setVirtualServers(List<VirtualServerWrapper> virtualServers) {
		this.virtualServers = virtualServers;
	}

	public List<VrrpInstanceWrapper> getVrrpInstances() {
		return vrrpInstances;
	}

	public void setVrrpInstances(List<VrrpInstanceWrapper> vrrpInstances) {
		this.vrrpInstances = vrrpInstances;
	}

	public List<VrrpSyncGroupWrapper> getVrrpSyncGroups() {
		return vrrpSyncGroups;
	}

	public void setVrrpSyncGroups(List<VrrpSyncGroupWrapper> vrrpSyncGroups) {
		this.vrrpSyncGroups = vrrpSyncGroups;
	}
}
