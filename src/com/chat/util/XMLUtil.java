package com.chat.util;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLUtil
{
	/*
	 * �ͻ��˵�½ʱ�����˷��͵�XML����
	 */

	// ����xml�ļ�����ͬ��xml���֣����ڵ�Ԫ�أ���������xml�ĵ�
	public static Document constructDocument()
	{
		Element root = DocumentHelper.createElement("message");
		Document document = DocumentHelper.createDocument(root);
		return document;
	}

	// ����������¼��Ϣ���û�������xml�ĵ��������ַ�������ʽ����
	public static String constructLoginXML(String username)
	{
		Document document = constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("1");

		root.addElement("user").setText(username);

		return document.asXML(); // document.asXML()������xml�ļ����ݵ��ַ�����ʽ

	}

	// ���ַ�����ʽ��xml�ĵ�����ȡ��¼��Ϣ���û�����
	public static String extractUsername(String xml)
	{
		try
		{
			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			return root.element("user").getText();
		}
		catch (DocumentException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	// ����һ���Ƿ��½�ɹ���xml�ĵ��������ַ�������

	public static String constructIsLoginResultXML(String isLoginResult)
	{
		Document document = constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("2");

		root.addElement("result").setText(isLoginResult);

		return document.asXML();

	}

	// ���ַ�����ʽ��xml�ĵ�����ȡ��¼�ɹ�ʧ����Ϣ
	public static String extractIsLoginResult(String xml)
	{
		SAXReader saxReader = new SAXReader();

		try
		{
			Document document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			return root.element("result").getText();

		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// �����������ͻ��˷��͵��û��б���Ϣ��xml�ַ���

	public static String constructUserListXML(Set<String> userset)
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("3");
		Element user = root.addElement("users");

		for (String e : userset)
		{
			user.addElement("user").setText(e);
		}
		return document.asXML();

	}

	// ��ȡ����˷����ͻ����û��б��xml�ַ���

	public static String extractUserList(String xml)
	{

		SAXReader saxReader = new SAXReader();

		String userList = "";

		try
		{
			Document document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			Element users = root.element("users");

			for (@SuppressWarnings("rawtypes")
			Iterator iter = users.elementIterator("user"); iter.hasNext();)
			{
				userList += ((Element) iter.next()).getText() + "\n";
			}

			return userList;
		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	// �����������ݵ�xml���������ַ�����ʾ��ʽ
	public static String constructChatMsgXML(String msg)
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("4");

		root.addElement("msg").setText(msg);

		return document.asXML();
	}

	// �����������ݵ�xml�ַ���
	public static String extractChatMsg(String xml)
	{
		SAXReader saxReader = new SAXReader();

		try
		{
			Document document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			return root.element("msg").getText();

		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	// �����ͻ��˹رմ��ڵ���Ϣxml�ַ�����ʽ
	public static String constructClientCloseWindowXML(String msg)
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("5");

		root.addElement("closewindow").setText(msg);

		return document.asXML();
	}

	// ��������˹رմ��ڵ���Ϣxml�ַ�����ʽ
	public static String constructServerCloseWindowXML()
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("6");

		return document.asXML();
	}
	
	// �����ͻ��˹رմ���ȷ�ϵ���Ϣxml�ַ�����ʽ
		public static String constructConfirmClientExitXML()
		{
			Document document = XMLUtil.constructDocument();

			Element root = document.getRootElement();

			root.addElement("type").setText("7");

			return document.asXML();
		}
	
	

	// ��ȡxml��typeֵ
	public static String getTypeFromXML(String xml)
	{
		SAXReader saxReader = new SAXReader();

		try
		{
			Document document = saxReader.read(new StringReader(xml));

			Element root = document.getRootElement();

			String type = root.element("type").getText();

			return type;
		}
		catch (DocumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
