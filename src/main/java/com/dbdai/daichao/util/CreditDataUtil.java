package com.dbdai.daichao.util;

import java.util.regex.Pattern;

public class CreditDataUtil {

	
	/**
	 * 移动手机号 正则
	 */
	private static String REGEX_YDMOBLE="((^13[4-9])|(^14[7,8])|(^15[0-2,7-9])|(^17[2,8])|(^18[2-4,7,8])|(^198))\\d{8}$";
	/**
	 * 联通手机号 正则
	 */
	private static String REGEX_LTMOBLE="((^13[0-2])|(^1[4,5][5,6])|(^166)|(^1[7,8][5,6]))\\d{8}$";
	/**
	 * 电信手机号正则
	 */
	private static String REGEX_DXMOBLE="((^133)|(^149)|(^153)|(^17[3,4,7])|(^18[0,1,9])|(^199))\\d{8}$";
	
	private static String REGEX_VISUALMOBLE="^17[0,1]\\d{8}$";
	
	
	
	/**
	 * 是否为虚拟号码
	 * @param mobile
	 * @return
	 */
	public static boolean markVisualMoble(String mobile) {
		return Pattern.matches(REGEX_VISUALMOBLE, mobile);
	}
	
	/**
	 * 移动号码
	 * @param mobile
	 * @return
	 */
	public static boolean isYDMobile(String mobile) {
		return Pattern.matches(REGEX_YDMOBLE, mobile);
	}
	/**
	 * 联通号码
	 * @param mobile
	 * @return
	 */
	public static boolean isLTMobile(String mobile) {
		return Pattern.matches(REGEX_LTMOBLE, mobile);
	}
	/**
	 * 电信号码
	 * @param mobile
	 * @return
	 */
	public static boolean isDXMobile(String mobile) {
		return Pattern.matches(REGEX_DXMOBLE, mobile);
	}
	
	
	public static void main(String[] args) {
		String mobile="174455481821";
		System.out.println(isDXMobile(mobile));
	}
	

}
