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
	private InputStream is; // �����������ܷ���˵���Ϣ��
	private OutputStream os; // ����������ͷ���˵���Ϣ��
	private ClientChat clientChat;

	// get the username
	public String getUserName()
	{
		return userName;
	}

	// ���캯��
	public ClientConnectionThread(Client client, String ipAddress, int hostPort, String userName)
	{
		this.client = client;
		this.ipAddress = ipAddress;
		this.hostPort = hostPort;
		this.userName = userName;

		this.connect2server();
		this.login();

	}

	// ���ӷ�����
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

	// ��½�������ɹ�
	private void login()
	{
		String xml = XMLUtil.constructLoginXML(this.userName);
		try
		{
			os.write(xml.getBytes()); // �������������Ϣ���û�����

			byte[] buf = new byte[1024];

			int length = is.read(buf); // ��ȡ��������Ӧ����Ϣ

			String response = new String(buf, 0, length);

			String isLogin = XMLUtil.extractIsLoginResult(response);

			if (CharacterUtil.SUCCESS.equals(isLogin))
			{
				client.setVisible(false); // ��¼�ɹ������ص�½���棬���������ҽ���

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

	// �ͻ��������˷�������
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

	// ���̵߳�run()���������������˵�ͨ��
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
						this.is.close();    //�ر�clientConnectionThread��������
						this.os.close();	//�ر�clientConnectionThread�������
						this.socket.close();					//�ر�socket����
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
