package com.unnet.triangle.lvs.master.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

public class UpstreamCache {

  private Map<Pair<Long, String>, Triple<String, String, Long>> upstreams = new ConcurrentHashMap<>();

  private static UpstreamCache instance = new UpstreamCache();

  public static UpstreamCache getInstance() {
    return instance;
  }

  private UpstreamCache() {
    // do nothing
  }

  /**
   * 
   * @param key workgroupId and serverAndPort
   * @param value name, name_origin, timestamp
   * @return
   */
  public Triple<String, String, Long> set(Long workgroupId, String serverAndPort, Triple<String, String, Long> value) {
    Pair<Long, String> pair = Pair.of(workgroupId, serverAndPort);
    return upstreams.put(pair, value);
  }

  /**
   * 
   * @param key
   * @param value
   * @return
   */
  public Triple<String, String, Long> setRaw(Pair<Long, String> key, Triple<String, String, Long> value) {
    return upstreams.put(key, value);
  }

  /**
   * Get name, name_origin, timestamp by workgroupId and serverAndPort
   * 
   * @param workgroupId
   * @param serverAndPort
   * @return
   */
  public Triple<String, String, Long> get(Long workgroupId, String serverAndPort) {
    Pair<Long, String> key = Pair.of(workgroupId, serverAndPort);
    return upstreams.get(key);
  }

  /**
   * raw data
   * 
   * @return
   */
  public Map<Pair<Long, String>, Triple<String, String, Long>> raw() {
    return upstreams;
  }

}
