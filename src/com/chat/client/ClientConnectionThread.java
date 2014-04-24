package com.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import com.chat.util.CharacterUtil;
import com.chat.util.XMLUtil;

public class ClientConnectionThread extends Thread
{
	private Socket socket;
	public Socket getSocket()
	{
		return socket;
	}

	private Client client;
	private String ipAddress;
	private int hostPort;
	private String userName;
	private InputStream is; // 输入流，接受服务端的消息流
	private OutputStream os; // 输出流，发送服务端的消息流
	private ClientChat clientChat;

	// get the username
	public String getUserName()
	{
		return userName;
	}

	// 构造函数
	public ClientConnectionThread(Client client, String ipAddress, int hostPort, String userName)
	{
		this.client = client;
		this.ipAddress = ipAddress;
		this.hostPort = hostPort;
		this.userName = userName;

		this.connect2server();
		this.login();

	}

	// 连接服务器
	public void connect2server()
	{
		try
		{
			this.socket = new Socket(this.ipAddress, this.hostPort);
			is = this.socket.getInputStream();
			os = this.socket.getOutputStream();

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 登陆服务器成功
	private void login()
	{
		String xml = XMLUtil.constructLoginXML(this.userName);
		try
		{
			os.write(xml.getBytes()); // 向服务器发送消息（用户名）

			byte[] buf = new byte[1024];

			int length = is.read(buf); // 读取服务器回应的消息

			String response = new String(buf, 0, length);

			String isLogin = XMLUtil.extractIsLoginResult(response);

			if (CharacterUtil.SUCCESS.equals(isLogin))
			{
				client.setVisible(false); // 登录成功后隐藏登陆界面，进入聊天室界面

				this.clientChat = new ClientChat(this);
			}
			else
			{
				JOptionPane.showMessageDialog(client, "Login failure!", "Warning", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}

		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 客户端向服务端发送数据
	public void sendMessage(String msg, int type)
	{
		if (type == CharacterUtil.USER_MSG)
		{
			String xml = XMLUtil.constructChatMsgXML(msg);

			try
			{
				os.write(xml.getBytes());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (type == CharacterUtil.USER_CLOSE_WINDOW)
		{
			String xml = XMLUtil.constructClientCloseWindowXML(msg);

			try
			{
				os.write(xml.getBytes());
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	// 该线程的run()函数，处理与服务端的通信
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

				int type = Integer.parseInt(XMLUtil.getTypeFromXML(xml));

				if (type == CharacterUtil.USER_LIST)
				{
					this.clientChat.updateUserList(XMLUtil.extractUserList(xml));
				}
				else if (type == CharacterUtil.USER_MSG)
				{
					this.clientChat.updateMessageBox(XMLUtil.extractChatMsg(xml));
				}
				else if (type == CharacterUtil.SERVER_CLOSE_WINDOW)
				{
					JOptionPane.showMessageDialog(this.clientChat, "Server has been closed.",
							"Warn", JOptionPane.WARNING_MESSAGE);

					System.exit(0); 
				}
				else if(type == CharacterUtil.CONFIRM_EXIT) 
				{
					try
					{
						this.is.close();    //关闭clientConnectionThread的输入流
						this.os.close();	//关闭clientConnectionThread的输出流
						this.socket.close();					//关闭socket对象
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						System.exit(0);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
