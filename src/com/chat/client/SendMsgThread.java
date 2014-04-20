package com.chat.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JFrame;

import com.test.chat.util.CharacterUtil;

public class SendMsgThread extends Thread
{
	private Socket socket;

	private String msg;
	
	public SendMsgThread(String msg) throws Exception
	{
		this.msg = msg;
		
	}
	
	@Override
	public void run()
	{
			try
			{
				socket = new Socket(CharacterUtil.SERVERHOST, ClientConnectionThread.serverPort1);
				
				OutputStream os = socket.getOutputStream();
				
				os.write(msg.getBytes());
				
				os.close();
				
				socket.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
