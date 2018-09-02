package com.unnet.triangle.lvs.master.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author ricl
 *
 */
public class DefaultValue {

  /**
   * return an empty array of type T
   * 
   * @return
   */
  public static Boolean emptyBoolean() {
    return false;
  }

  /**
   * return an empty array of type T
   * 
   * @return
   */
  public static <T> List<T> emptyArray() {
    return new ArrayList<T>();
  }

  /**
   * return an empty map of type <T,P>
   * 
   * @return
   */
  public static <T, P> HashMap<T, P> emptyMap() {
    return new HashMap<T, P>();
  }

  /**
   * return an empty string
   * 
   * @return
   */
  public static String emptyString() {
    return "";
  }

  /**
   * return an empty Long
   * 
   * @return
   */
  public static Long emptyLong() {
    return Long.getLong("0");
  }

  /**
   * if null return empty array
   * 
   * @param list
   * @return
   */
  public static <T> List<T> ifNullEmptyArray(List<T> list) {
    if (null != list && list.size() > 0) {
      return list;
    } else {
      return emptyArray();
    }
  }

}
