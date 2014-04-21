package com.chat.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.chat.util.CharacterUtil;

@SuppressWarnings("serial")
public class ClientChat extends JFrame
{

	private String title;
	private ClientConnectionThread clientConnectionThread;
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

	public ClientChat(ClientConnectionThread clientConnectionThread) throws Exception
	{
		this.clientConnectionThread = clientConnectionThread;
		initComponents();
	}

	public void updateUserList(String usersList)
	{
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
		this.title = clientConnectionThread.getUserName();
		this.setTitle(title);

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

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // ����رմ��ں��˳�jvm
		this.setResizable(false); // �Ƿ�������Ĵ��ڴ�С
		this.pack(); // ���ڴ���Ϊ���ʴ�С
		this.setVisible(true); // ʹ����ɼ�

		jButton1.addActionListener(new ActionListener() // Send
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				sendMessage(e);
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
		
		this.addWindowListener(new WindowListener()
		{
			
			@Override
			public void windowOpened(WindowEvent e)
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
			public void windowDeactivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				closeWindow(e);
			}
			
			@Override
			public void windowClosed(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	private void sendMessage(ActionEvent event)
	{
		//��jTextFiled1�л�ȡ����������
		String content = jTextField1.getText();
		//�жϷ�����Ϣ�Ƿ�Ϊ�գ�Ϊ�վ���
		if (CharacterUtil.isEmpty(content))
		{
			JOptionPane.showMessageDialog(ClientChat.this,
					"Sent message is empty.Write something.", "Warning",
					JOptionPane.WARNING_MESSAGE);

			jTextField1.setText("");

			jTextField1.requestFocus();

			return;
		}
		//���մ������Ϣ����Ϊmessage
		String message = "<" + title + ">" + " :\n" + content + "\n\n";		
		
		this.clientConnectionThread.sendMessage(message, CharacterUtil.USER_MSG);
	}
	
	private void closeWindow(WindowEvent e)
	{
		String message = "true";
		
		this.clientConnectionThread.sendMessage(message, CharacterUtil.USER_MSG);
	}
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
