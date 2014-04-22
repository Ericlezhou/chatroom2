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
	 * 客户端登陆时向服务端发送的XML数据
	 */

	// 构建xml文件中相同的xml部分（根节点元素），并返回xml文档
	public static Document constructDocument()
	{
		Element root = DocumentHelper.createElement("message");
		Document document = DocumentHelper.createDocument(root);
		return document;
	}

	// 构建包含登录信息（用户名）的xml文档，并以字符串的形式返回
	public static String constructLoginXML(String username)
	{
		Document document = constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("1");

		root.addElement("user").setText(username);

		return document.asXML(); // document.asXML()返回了xml文件内容的字符串形式

	}

	// 从字符串形式的xml文档中提取登录信息（用户名）
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

	// 构建一个是否登陆成功的xml文档，并以字符串返回

	public static String constructIsLoginResultXML(String isLoginResult)
	{
		Document document = constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("2");

		root.addElement("result").setText(isLoginResult);

		return document.asXML();

	}

	// 从字符串形式的xml文档中提取登录成功失败信息
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

	// 构建服务端向客户端发送的用户列表信息的xml字符串

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

	// 提取服务端发往客户端用户列表的xml字符串

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

	// 构建聊天数据的xml，并返回字符串表示形式
	public static String constructChatMsgXML(String msg)
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("4");

		root.addElement("msg").setText(msg);

		return document.asXML();
	}

	// 解析聊天数据的xml字符串
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

	// 构建客户端关闭窗口的消息xml字符串形式
	public static String constructClientCloseWindowXML(String msg)
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("5");

		root.addElement("closewindow").setText(msg);

		return document.asXML();
	}

	// 构建服务端关闭窗口的消息xml字符串形式
	public static String constructServerCloseWindowXML()
	{
		Document document = XMLUtil.constructDocument();

		Element root = document.getRootElement();

		root.addElement("type").setText("6");

		return document.asXML();
	}
	
	// 构建客户端关闭窗口确认的消息xml字符串形式
		public static String constructConfirmClientExitXML()
		{
			Document document = XMLUtil.constructDocument();

			Element root = document.getRootElement();

			root.addElement("type").setText("7");

			return document.asXML();
		}
	
	

	// 获取xml的type值
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
