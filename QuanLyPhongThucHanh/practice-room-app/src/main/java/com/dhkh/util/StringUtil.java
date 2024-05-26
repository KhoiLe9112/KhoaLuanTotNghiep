package com.dhkh.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	/**
	 * Function to check string is null or empty
	 * @param str
	 * @return true if str is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.equals(StringUtils.EMPTY);
	}
	
	/**
	 * Function to check a list<String> is null or empty
	 * @param lst
	 * @return
	 */
	public static boolean isListNullOrEmpty(List<String> lst) {
		return lst == null || lst.isEmpty();
	}
	
}