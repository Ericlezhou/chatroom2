package com.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.chat.util.CharacterUtil;
import com.chat.util.XMLUtil;

public class ClientConnectionThread extends Thread
{
	private InputStream is; // 输入流，接受服务端的消息流
	private OutputStream os; // 输出流，发送服务端的消息流
	protected static int randomPort1 = CharacterUtil.randomPort1; // 客户端随机产生的端口号，
	protected static int randomPort2 = CharacterUtil.randomPort2; // 客户端随机产生的端口号，
	protected static int serverPort1;
	protected static int serverPort2;

	// 构造函数
	public ClientConnectionThread(Client client, String ipAddress,
			String hostPort, String userName)
	{
		
		this.login();
		
	}
	
	//  登陆服务器成功
	private void login()
	{
		String xml = XMLUtil.constructLoginXML(this.userName);
		try
		{
			os.write(xml.getBytes());  //向服务器发送消息（用户名）
			
			byte[] buf = new byte[1024];
			
			int length = is.read(buf);
			
			String response = new String(buf, 0, length);
			
			if(CharacterUtil.SUCCESS.equals(response))
			{
				client.setVisible(false); // 登录成功后隐藏登陆界面，进入聊天室界面
				new ClientChat(userName);
			}
				
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	// 该线程的run()函数，处理与服务端的通信
	@Override
	public void run()
	{
		try
		{

			// 客户端发给服务端自己的地址和端口消息

			InetAddress address = InetAddress.getLocalHost();
			String clientAddress = address.toString();
			String info1 = userName + "@@@" + randomPort1 + "_" + randomPort2
					+ "_" + clientAddress;
			os.write(info1.getBytes()); // zhoule@@@4567_2345_127.0.0.1

			// 客户端接收到服务端的产生的登录成功信息以及新建的两个端口号
			byte[] buffer = new byte[1024];
			int len = is.read(buffer);

			String temp = new String(buffer, 0, len); // SUCCESS@@@xxxx_xxxx
			int index = temp.indexOf("@@@");
			int index_ = temp.indexOf("_");

			String isLogInfo = temp.substring(0, index);
			serverPort1 = Integer.parseInt(temp.substring(index + 3, index_));
			serverPort2 = Integer.parseInt(temp.substring(index_ + 1));
		}

		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
