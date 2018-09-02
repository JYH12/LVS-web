package com.unnet.triangle.lvs.master.utils;

import java.lang.reflect.Field;

/**
 * 
 * @author ricl
 *
 */
public class BeanCompareUtil {

	public static boolean compare(final Object o1, final Object o2) {

		Field o1Fields[] = o1.getClass().getDeclaredFields();

		Field o2Fields[] = o2.getClass().getDeclaredFields();

		if (o1Fields.length != o2Fields.length) {
			System.out.println("length mismatch");
			return false;
		}

		Field.setAccessible(o1Fields, true);
		Field.setAccessible(o2Fields, true);

		try {
			for (int i = 0; i < o1Fields.length; i++) {
				if (!o1Fields[i].getName().equals(o2Fields[i].getName())) {
					return false;
				}

				Object v1 = o1Fields[i].get(o1);
				Object v2 = o2Fields[i].get(o2);

				if (null == v1 && null == v2) {
					continue;
				}

				if (!v1.equals(v2)) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
