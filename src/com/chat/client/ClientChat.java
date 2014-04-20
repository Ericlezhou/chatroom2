package com.chat.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.chat.util.CharacterUtil;

public class ClientChat extends JFrame
{

	private String title;
	private JPanel jPanel1;
	private JPanel jPanel2;
	private JPanel jPanel3;

	private JTextArea jTextArea1;
	private JTextArea jTextArea2;

	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;

	private JTextField jTextField1;

	private JButton jButton1;
	private JButton jButton2;


	public ClientChat(String title) throws Exception
	{
		super(title);
		this.title = title;
		initComponents();
	}

	public void updateUserList(String usersList)
	{
		this.jTextArea2.setText("");
		this.jTextArea2.setText(usersList);
	}

	public void appendMsg(String msg)
	{
		this.jTextArea1.append(msg);
		this.jTextField1.setText("");
		this.jTextField1.requestFocus(true);
		this.jTextArea1.setCaretPosition(jTextArea1.getText().length());
	}

	private void initComponents() throws Exception
	{
		jTextArea1 = new JTextArea();
		jTextArea1.setEditable(false);
		jTextArea1.setColumns(25);
		jTextArea1.setRows(20);
		jTextArea1.setForeground(Color.BLACK);
		jTextArea1.setLineWrap(true);

		jScrollPane1 = new JScrollPane();
		jScrollPane1.setViewportView(jTextArea1);

		jPanel1 = new JPanel();
		jPanel1.setBorder(BorderFactory.createTitledBorder("Chat Record"));
		jPanel1.add(jScrollPane1);

		jTextArea2 = new JTextArea();
		jTextArea2.setEditable(false);
		jTextArea2.setColumns(10);
		jTextArea2.setRows(20);
		jTextArea2.setForeground(Color.RED);

		jScrollPane2 = new JScrollPane();
		jScrollPane2.setViewportView(jTextArea2);

		jPanel2 = new JPanel();
		jPanel2.setBorder(BorderFactory.createTitledBorder("Online Users"));
		jPanel2.add(jScrollPane2);

		jTextField1 = new JTextField();
		jTextField1.setColumns(20);
		jButton1 = new JButton("Send");
		jButton2 = new JButton("Clear");

		jPanel3 = new JPanel();
		jPanel3.add(jTextField1);
		jPanel3.add(jButton1);
		jPanel3.add(jButton2);

		this.getContentPane().add(jPanel1, BorderLayout.WEST);
		this.getContentPane().add(jPanel2, BorderLayout.EAST);
		this.getContentPane().add(jPanel3, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 定义关闭窗口后退出jvm
		this.setResizable(false); // 是否允许更改窗口大小
		this.pack(); // 调节窗口为最适大小
		this.setVisible(true); // 使界面可见

		new GetUserListThread(this).start(); // 获取当前在线用户列表
		
		new ReceiveMsgThread(this).start(); // 建立接收消息线程
		
		this.addWindowListener(new WindowListener()
		{
			
			@Override
			public void windowOpened(WindowEvent e)
			{
				ClientChat.this.jTextField1.setFocusable(true);
			}
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				try
				{
					Socket socket = new Socket(CharacterUtil.SERVERHOST,ClientConnectionThread.serverPort2);
					
					OutputStream os = socket.getOutputStream();
					
					String quitInfo = "quit" + "@" + title;
					
					os.write(quitInfo.getBytes());
					
					os.close();
					
					socket.close();
					
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		
		

		jButton1.addActionListener(new ActionListener() // Send
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String content = jTextField1.getText();

				String msg = "<" + title + ">" + " :\n" + content + "\n\n";

				if (CharacterUtil.isEmpty(content))
				{
					JOptionPane.showMessageDialog(ClientChat.this,
							"Sent message is empty.Write something.",
							"Warning", JOptionPane.WARNING_MESSAGE);

					jTextField1.setText("");

					jTextField1.requestFocus();

					return;
				}

				try
				{
						
						
						new SendMsgThread(msg).start(); // 建立发送消息的线程

				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		jButton2.addActionListener(new ActionListener() // Clear
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				jTextField1.setText("");
			}
		});

	}
}
