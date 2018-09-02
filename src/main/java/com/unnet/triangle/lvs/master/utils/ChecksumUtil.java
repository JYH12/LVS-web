package com.unnet.triangle.lvs.master.utils;


import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.unnet.triangle.lvs.master.constants.Constants;

/**
 * 
 * @author ricl
 *
 */
public class ChecksumUtil {

  private static Logger logger = LoggerFactory.getLogger(ChecksumUtil.class);

  /**
   * check if object is a valid object
   * 
   * @param object
   * @return
   */
  public static boolean check(Object object) {

    try {

      JSONObject json = JSONObject.parseObject(JSON.toJSONString(object), Feature.OrderedField);
      String incomeChecksum = json.getString(Constants.OBJECT_CHECKSUM_FIELD_NAME);
      if (null == incomeChecksum) {
        return false;
      }
      json.put(Constants.OBJECT_CHECKSUM_FIELD_NAME, null);
      Object withoutChecksumObject = JSON.toJavaObject(json, object.getClass());
      String calcStr = JSON.toJSONString(withoutChecksumObject, SerializerFeature.MapSortField, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
      String calcChecksum = DigestUtils.md5Hex(calcStr);
      logger.info("calcStr: " + calcStr);
      logger.info("calcChecksum: " + calcChecksum + ", incomeChecksum: " + incomeChecksum);

      return incomeChecksum.equalsIgnoreCase(calcChecksum);
    } catch (Exception e) {
      return false;
    }

  }

  /**
   * package a single object with checksum
   * 
   * @param object
   * @return
   */
  @SuppressWarnings("unchecked")
  public static <T> T packageObjectWithChecksum(T object) {
    JSONObject json = JSONObject.parseObject(JSON.toJSONString(object));
    json.put(Constants.OBJECT_CHECKSUM_FIELD_NAME, null);

    Object withoutChecksumObject = JSON.toJavaObject(json, object.getClass());
    String calcStr = JSON.toJSONString(withoutChecksumObject, SerializerFeature.MapSortField, SerializerFeature.SortField, SerializerFeature.WriteMapNullValue);
    String calcChecksum = DigestUtils.md5Hex(calcStr);
    json.put(Constants.OBJECT_CHECKSUM_FIELD_NAME, calcChecksum);
    return (T) JSON.toJavaObject(json, object.getClass());
  }

  /**
   * package multiple objects with checksum
   * 
   * @param objects
   * @return
   */
  public static <T> List<T> packageObjectWithChecksum(List<T> objects) {
    List<T> newObjects = new ArrayList<>();
    for (T object : objects) {
      newObjects.add(packageObjectWithChecksum(object));
    }
    return newObjects;
  }

}
