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
	 * �жϸ������ַ����Ƿ�Ϊ�գ���Ϊ�գ��򷵻�true
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
	 * �жϸ������ַ����Ƿ�Ϊ������
	 */
	public static boolean isPositiveInteger(String str)
	{
		// try{ //��һ���жϷ�ʽ������ת���������Ƿ��׳��쳣���жϣ��÷������ʺϸ�Ƶ�ȵĵ��ã�������ʹ��
		// Integer.valueOf(str);
		// return true;
		// }catch(Exception e){
		// return false;
		// }

		// return Pattern.matches("[1-9]+\\d*", str);
		// //�ڶ����жϷ�ʽ������������ʽ���ж��Ƿ���һ��������

		for (int i = 0; i < str.length(); i++) // �������жϷ����������ַ����е�ÿһ���ַ�������java�Դ���isDigit�����������Ƿ�������
		{
			if (!Character.isDigit(str.charAt(i))) return false;
		}
		return true;

	}

	/*
	 * �ж�����Ķ˿ں��Ƿ�Ϸ�
	 */
	public static boolean isLegalPort(String str)
	{
		int port = Integer.parseInt(str);
		if (port > 65535 || port < 1024) return false;
		return true;
	}

	/*
	 * �ж�����������Ƿ�Ϸ�,��������β�ַ���������ĸ�������֣��м䲻�ó���\��@������Ϊ5��14֮��
	 */
	public static boolean isLegalName(String str)
	{
		return Pattern.matches("\\w[^\\@]{3,12}\\w", str);
	}

	/*
	 * �ж������IP��ַ�Ƿ�Ϸ�
	 */
	public static boolean isLegalIPAddress(String str)
	{
		return Pattern
				.matches(
						"(((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))\\.){3}((25[0-5])|(2[0-4]\\d)|(1\\d{2})|(\\d{1,2}))",
						str);
	}

	/*
	 * ����һ������Ķ˿ں�
	 */
	public static int randomPort()
	{
		return (int) (Math.random() * 50000 + 1025);
	}

}
