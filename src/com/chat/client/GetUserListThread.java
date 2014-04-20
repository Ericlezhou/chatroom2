package com.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class GetUserListThread extends Thread
{
	private StringBuffer sb;

	private JFrame jFrame;

	private ServerSocket ss;

	private Socket s;

	private InputStream is;

	public GetUserListThread(JFrame jFrame) throws Exception
	{
		this.jFrame = jFrame;

		ss = new ServerSocket(ClientConnectionThread.randomPort1);

	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				s = ss.accept();

				is = s.getInputStream();

				byte[] buffer = new byte[1024];

				int len = is.read(buffer);
				
				String usersList = new String(buffer, 0, len);
				
				ClientChat cc = (ClientChat)jFrame;
				
				cc.updateUserList(usersList);
				
				is.close();
				
				s.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
