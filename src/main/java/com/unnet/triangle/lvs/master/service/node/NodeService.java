package com.unnet.triangle.lvs.master.service.node;

import java.util.List;

import com.unnet.triangle.lvs.master.entity.lvs.wrapper.LVSWrapper;
import com.unnet.triangle.lvs.master.model.Node;

public interface NodeService {
	long insert(Node node);

	boolean delete(long nodeId);

	List<Node> selectAll(long workgroupId);

	Node select(long id);

	Boolean update(Node node);

	boolean deploy(long nodeId);

	List<Node> selectNodesByWorkgroupId(long workgroupId);
	
	LVSWrapper config(long nodeId);
}
