package com.unnet.triangle.lvs.master.entity.workgroup;

import java.util.ArrayList;
import java.util.List;

import com.unnet.triangle.lvs.master.model.Node;

public class WorkgroupExt {
	long nodeSize = 0;
	List<Node> nodes = new ArrayList<>();
	public long getNodeSize() {
		return nodeSize;
	}
	public void setNodeSize(long nodeSize) {
		this.nodeSize = nodeSize;
	}
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
}
