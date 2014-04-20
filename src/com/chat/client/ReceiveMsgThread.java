package com.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class ReceiveMsgThread extends Thread
{
	private JFrame jFrame;
	
	private ServerSocket ss;

	private Socket s;
	
	public ReceiveMsgThread(JFrame jFrame) throws Exception
	{
		this.jFrame = jFrame;
	
		ss = new ServerSocket(ClientConnectionThread.randomPort2);
		
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				s = ss.accept();
				
				InputStream is = s.getInputStream();
				
				byte[] buffer = new byte[1024];
				
				int len = is.read(buffer);
				
				String msg = new String(buffer, 0, len);
				
				ClientChat c = (ClientChat)jFrame;
				
				c.appendMsg(msg);
				
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
