package com.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Set;

import com.chat.util.XMLUtil;

public class ServerMsgThread extends Thread
{

	private Server server;

	private InputStream is;

	private OutputStream os;

	public ServerMsgThread(Server server, Socket socket)
	{
		this.server = server;

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

			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
