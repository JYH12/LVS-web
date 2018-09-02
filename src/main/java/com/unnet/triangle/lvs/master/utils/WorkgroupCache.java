package com.unnet.triangle.lvs.master.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.tuple.Triple;

/**
 * let it faster to get workgorup id and uuid mapping
 * 
 * @author ricl
 *
 */
public class WorkgroupCache {

	private Map<Long, Triple<String, String, Long>> workgroups = new ConcurrentHashMap<>();
	
	private static WorkgroupCache instance = new WorkgroupCache();

	public static WorkgroupCache getInstance() {
		return instance;
	}

	private WorkgroupCache() {
		// do nothing
	}

	/**
	 * 
	 * @param key workgroupId
	 * @param value name,uuid,timestamp
	 * @return
	 */
	public Triple<String, String, Long> set(Long key, Triple<String, String, Long> value) {
		return workgroups.put(key, value);
	}

	/**
	 * Get workgroup name, uuid, timestamp by key
	 * 
	 * @param key
	 *            workgroupID
	 * @return
	 */
	public Triple<String, String, Long> get(Long key) {
		return workgroups.get(key);
	}

}
