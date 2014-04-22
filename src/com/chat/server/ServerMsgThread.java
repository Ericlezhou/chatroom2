package com.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;

import com.chat.util.CharacterUtil;
import com.chat.util.XMLUtil;

public class ServerMsgThread extends Thread
{

	private Server server;

	private InputStream is;

	private OutputStream os;
	
	private String userName;

	public ServerMsgThread(Server server, Socket socket, String userName)
	{
		this.server = server;
		
		this.userName = userName;

		try
		{
			this.is = socket.getInputStream();

			this.os = socket.getOutputStream();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���·������Լ��ͻ��˵��û��б�
	public void updateUserList()
	{

		Set<String> set = this.server.getMap().keySet();

		String xml = XMLUtil.constructUserListXML(set);

		String list = "";

		for (String str : set)
		{
			list += str + "\n";
		}
		// �����������û��б�
		this.server.getjTextArea1().setText(list);
		// ��ÿ���ͻ��ˣ��ͻ������ӣ�

		Collection<ServerMsgThread> collection = this.server.getMap().values();

		for (ServerMsgThread smt : collection)
		{
			smt.sendMsg(xml);
		}
	}

	//���¿ͻ�������box����
	public void updateChatBox(String xml)
	{
		Collection<ServerMsgThread> collection = this.server.getMap().values();

		for (ServerMsgThread smt : collection)
		{
			smt.sendMsg(xml);
		}
	}
	
	public void sendMsg(String msg)
	{
		try
		{
			os.write(msg.getBytes());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		try
		{
			while (true)
			{
				byte[] buf = new byte[1024];
				
				int len = is.read(buf);
				
				String xml = new String(buf, 0, len);
				
				System.out.println(xml);
				
				int type = Integer.parseInt(XMLUtil.getTypeFromXML(xml));
				
				if(type == CharacterUtil.USER_MSG)
				{
					updateChatBox(xml);
				}
				else if(type == CharacterUtil.USER_CLOSEWINDOW)
				{
					server.getMap().remove(userName);
					updateUserList();
					break;
				}
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
