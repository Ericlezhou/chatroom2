package com.chat.server;

import java.awt.Color;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.chat.util.CharacterUtil;
import com.chat.util.XMLUtil;

public class ServerConnectionThread extends Thread
{
	private ServerSocket serverSocket;

	private Socket socket;

	private JLabel jLabelToChange;

	private JButton jButtonToChange;

	private JTextField jTextFieldToChange;

	private InputStream is;

	private OutputStream os;

	private Server server;

	public ServerConnectionThread(Server server, String threadName, int hostPort)
	{
		try
		{
			this.server = server;
			this.setName(threadName);

			serverSocket = new ServerSocket(hostPort); // 初始化服务端socket

			jLabelToChange = server.getjLabel2();
			jLabelToChange.setText("Run");
			jLabelToChange.setForeground(Color.green);

			jButtonToChange = server.getjButton1();
			jButtonToChange.setVisible(false);

			jTextFieldToChange = server.getjTextField1();
			jTextFieldToChange.setEditable(false);

		}
		catch (Exception e)
		{
			e.printStackTrace();

			JOptionPane.showMessageDialog(this.server,
					"This port has been used.Please change.", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				socket = serverSocket.accept();

				is = socket.getInputStream();
				os = socket.getOutputStream();

				byte[] buffer = new byte[1024];
				int len = is.read(buffer);

				// 客户端发来的连接信息（包括用户名）
				String loginXML = new String(buffer, 0, len);
				// 从客户端发来的连接信息解析出用户名
				String userName = XMLUtil.extractUsername(loginXML);
				// 设置登陆成功与否的标记
				String isLoginResult = null;

				if (this.server.getMap().containsKey(userName))
				{
					isLoginResult = CharacterUtil.FALSE;
				}
				else
				{
					isLoginResult = CharacterUtil.SUCCESS;
				}
				// 生成是否登陆成功的xml信息格式 并返回字符串的形式
				String isLoginXML = XMLUtil
						.constructIsLoginResultXML(isLoginResult);
				// 将上述生成的xml字符串信息发给客户端
				os.write(isLoginXML.getBytes());
				// 构建子线程用来处理客户端与服务器之间的消息传输
				ServerMsgThread serverMsgThread = new ServerMsgThread(server,
						socket, userName);
				// 将用户名，以及该用户对应的子线程保存到servre维护的map当中
				server.getMap().put(userName, serverMsgThread);
				// 更新客户端的用户列表
				serverMsgThread.updateUserList();

				serverMsgThread.start();

			}

			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
