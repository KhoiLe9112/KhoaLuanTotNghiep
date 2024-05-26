package com.dhkh.util;

import java.util.List;

public class NumberUtil {
	/**
	 * Function to check number is null or 0
	 * @param num
	 * @return true if num is null or empty
	 */
	public static boolean isNullOrEmpty(Integer num) {
		return num == null || num == 0;		
	}
	
	/**
	 * Function to check str is number or not
	 * @param str
	 * @return true if str is number
	 */
	public static boolean isNumber(String str) {
		String regex = "^[+-]?[\\d]*[.]?[\\d]*$";
		return str.matches(regex);
	}
	
	/**
	 * Function to check a list<Integer> is null or empty
	 * @param lst
	 * @return
	 */
	public static boolean isListNullOrEmpty(List<Integer> lst) {
		return lst == null || lst.isEmpty();
	}
}