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
	private InputStream is; // �����������ܷ���˵���Ϣ��
	private OutputStream os; // ����������ͷ���˵���Ϣ��
	protected static int randomPort1 = CharacterUtil.randomPort1; // �ͻ�����������Ķ˿ںţ�
	protected static int randomPort2 = CharacterUtil.randomPort2; // �ͻ�����������Ķ˿ںţ�
	protected static int serverPort1;
	protected static int serverPort2;

	// ���캯��
	public ClientConnectionThread(Client client, String ipAddress,
			String hostPort, String userName)
	{
		
		this.login();
		
	}
	
	//  ��½�������ɹ�
	private void login()
	{
		String xml = XMLUtil.constructLoginXML(this.userName);
		try
		{
			os.write(xml.getBytes());  //�������������Ϣ���û�����
			
			byte[] buf = new byte[1024];
			
			int length = is.read(buf);
			
			String response = new String(buf, 0, length);
			
			if(CharacterUtil.SUCCESS.equals(response))
			{
				client.setVisible(false); // ��¼�ɹ������ص�½���棬���������ҽ���
				new ClientChat(userName);
			}
				
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	// ���̵߳�run()���������������˵�ͨ��
	@Override
	public void run()
	{
		try
		{

			// �ͻ��˷���������Լ��ĵ�ַ�Ͷ˿���Ϣ

			InetAddress address = InetAddress.getLocalHost();
			String clientAddress = address.toString();
			String info1 = userName + "@@@" + randomPort1 + "_" + randomPort2
					+ "_" + clientAddress;
			os.write(info1.getBytes()); // zhoule@@@4567_2345_127.0.0.1

			// �ͻ��˽��յ�����˵Ĳ����ĵ�¼�ɹ���Ϣ�Լ��½��������˿ں�
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
