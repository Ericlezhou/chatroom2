package com.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.chat.util.XMLUtil;

public class ServerMsgThread extends Thread
{
	
	private Server server ;

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

	public void updateUserList()
	{
		@SuppressWarnings("unchecked")
		Set<String> set = this.server.getMap().keySet();
		
		String xml = XMLUtil.constructUserListXML(set);
		
		String list = "";
		
		for(String str : set)
		{
			list = str + "\n" ;
		}
		
		this.server.getjTextArea1().setText(list); 
		
		@SuppressWarnings("unchecked")
		Set<ServerMsgThread> set2 = this.server.getMap().entrySet();
		
		for(ServerMsgThread smt : set2)
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
		catch(Exception e)
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
					socket = serverSocket.accept();

					InputStream is = socket.getInputStream();

					byte[] buffer = new byte[1024];

					int len = is.read(buffer);

					String msg = new String(buffer, 0, len);

					Set set2 = Server.map.entrySet();
					Iterator iter2 = set2.iterator();

					while (iter2.hasNext()) // 遍历每个用户的host地址，并将新的消息发给客户端
					{
						Map.Entry entry = (Map.Entry) iter2.next();
						String temp = (String) entry.getValue();
						// zhoule@@@4567_2345_ZHL-PC/127.0.0.1
						int index_1 = temp.indexOf("_");
						int index_2 = temp.lastIndexOf("_");
						int index_3 = temp.indexOf("/");
						int clientPort = Integer.parseInt(temp.substring(
								index_1 + 1, index_2));
						String address = temp.substring(index_3 + 1);
						Socket socket2 = new Socket(address, clientPort);

						OutputStream os2 = socket2.getOutputStream();

						os2.write(msg.getBytes());
						os2.close();
						socket2.close();
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
