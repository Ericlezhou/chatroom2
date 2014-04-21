package com.chat.util;

import java.util.regex.Pattern;

//import java.util.regex.Pattern;

public class CharacterUtil
{
	public static final String ERROR = "ERROR";

	public static final String SUCCESS = "SUCESS";

	public static final String FALSE = "FALSE";

	public static final int LOGIN = 2;

	public static final int USER_LIST = 3;
	
	public static final int USER_MSG = 4;
	
	public static final int USER_CLOSEWINDOW = 5;

	public static int PORT1 = randomPort();

	public static int PORT2 = randomPort();

	public static int randomPort1 = randomPort();

	public static int randomPort2 = randomPort();

	public static String SERVERHOST;

	public static int SERVERPORT;

	/*
	 * 判断给定的字符串是否为空，若为空，则返回true
	 */
	public static boolean isEmpty(String str)
	{
		if ("".equals(str))
		{
			return true;
		}
		return false;
	}

	/*
	 * 判断给定的字符串是否为正整数
	 */
	public static boolean isPositiveInteger(String str)
	{
		// try{ //第一种判断方式，根据转换过程中是否抛出异常来判断，该方法不适合高频度的调用，不建议使用
		// Integer.valueOf(str);
		// return true;
		// }catch(Exception e){
		// return false;
		// }

		// return Pattern.matches("[1-9]+\\d*", str);
		// //第二种判断方式，利用正则表达式来判断是否是一个正整数

		for (int i = 0; i < str.length(); i++) // 第三种判断方法，遍历字符串中的每一个字符，利用java自带的isDigit（）来看其是否是数字
		{
			if (!Character.isDigit(str.charAt(i))) return false;
		}
		return true;

	}

	/*
	 * 判断输入的端口号是否合法
	 */
	public static boolean isLegalPort(String str)
	{
		int port = Integer.parseInt(str);
		if (port > 65535 || port < 1024) return false;
		return true;
	}

	/*
	 * 判断输入的名字是否合法,条件是首尾字符必须是字母或者数字，中间不得出现\和@，长度为5～14之间
	 */
	public static boolean isLegalName(String str)
	{
		return Pattern.matches("\\w[^\\@]{3,12}\\w", str);
	}

	/*
	 * 判断输入的IP地址是否合法
	 */
	public static boolean isLegalIPAddress(String str)
	{
		return Pattern
				.matches(
						"(((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))\\.){3}((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))",
						str);
	}

	/*
	 * 产生一个随机的端口号
	 */
	public static int randomPort()
	{
		return (int) (Math.random() * 50000 + 1025);
	}

}
