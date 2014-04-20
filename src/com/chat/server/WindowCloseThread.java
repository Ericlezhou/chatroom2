package com.chat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

public class WindowCloseThread extends Thread
{
	private ServerSocket ss;

	private Socket s;

	private InputStream is;

	private JFrame jFrame;

	private Map map = Server.map;

	public WindowCloseThread(JFrame jFrame) throws Exception
	{
		this.jFrame = jFrame;
		ss = new ServerSocket(ServerConnectionThread.serverPort2);
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

				String quitInfo = new String(buffer, 0, len); // quit@zhoule

				String userName = quitInfo.substring(quitInfo.indexOf("@") + 1);

				Server server = (Server) jFrame;

				server.deleteUser(userName);

				server.setUsersList();

				is.close();

				s.close();

				// 下面将用户列表发向各个客户端的消息进程ClientMessageThread
				Set set1 = map.keySet();
				StringBuffer sb = new StringBuffer();

				for (Iterator iter1 = set1.iterator(); iter1.hasNext();)
				{
					sb.append((String) iter1.next() + "\n");
				}

				String usersList = sb.toString(); // 得到用户列表

				Set set2 = map.entrySet();
				Iterator iter2 = set2.iterator();

				while (iter2.hasNext()) // 遍历每个用户的host地址，并将服务端新的端口号发给客户端 //
										// value的格式为4567_2345_ZHL-PC/127.0.0.1
				{
					Map.Entry entry = (Map.Entry) iter2.next();
					String temp = (String) entry.getValue();
					int index1 = temp.indexOf("_");
					int index2 = temp.lastIndexOf("/");
					int clientPort = Integer
							.parseInt(temp.substring(0, index1));
					String address = temp.substring(index2 + 1);
					Socket socket2 = new Socket(address, clientPort);

					OutputStream os2 = socket2.getOutputStream();

					os2.write(usersList.getBytes());
					os2.close();
					socket2.close();
				}

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
