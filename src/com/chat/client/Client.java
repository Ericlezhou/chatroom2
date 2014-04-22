
package com.chat.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.chat.util.CharacterUtil;

@SuppressWarnings("serial")
public class Client extends JFrame
{
	private JPanel jPanel;

	private JLabel jLabel1;
	private JLabel jLabel2; 
	private JLabel jLabel3;

	private JTextField jTextField1;
	private JTextField jTextField2;
	private JTextField jTextField3;

	private JButton jButton1;
	private JButton jButton2;

	public Client()
	{
		super();
		initCompenents();
	}

	private void initCompenents()
	{
		jPanel = new JPanel();

		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();

		jTextField1 = new JTextField(15);
		jTextField2 = new JTextField(15);
		jTextField3 = new JTextField(15);

		jButton1 = new JButton();
		jButton2 = new JButton();

		jPanel.setBorder(BorderFactory.createTitledBorder("Login Info"));

		jLabel1.setText("Username");
		jTextField1.setText("");

		jLabel2.setText("IP Address");
		jTextField1.setText("");

		jLabel3.setText("Port Number");
		jTextField1.setText("");

		jButton1.setText("Login");
		jButton2.setText("Clear");

		jButton1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Client.this.login(e);
			}
		});
		jButton2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO Auto-generated method stub
				jTextField1.setText("");
				jTextField2.setText("");
				jTextField3.setText("");
			}
		});

		jPanel.add(jLabel1);
		jPanel.add(jTextField1);

		jPanel.add(jLabel2);
		jPanel.add(jTextField2);

		jPanel.add(jLabel3);
		jPanel.add(jTextField3);

		jPanel.add(jButton1);
		jPanel.add(jButton2);

		this.getContentPane().add(jPanel);
		this.setSize(280, 300);
		this.setResizable(false);
		this.setTitle("Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 定义关闭窗口后退出jvm
		this.setVisible(true);

	}

	private void login(ActionEvent e)
	{

		String userName = this.jTextField1.getText();
		String ipAddress = this.jTextField2.getText();
		String hostPort = this.jTextField3.getText();

		// usrName
		if (CharacterUtil.isEmpty(userName))
		{
			JOptionPane.showMessageDialog(this, "Username is not null.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField1.setText("");
			this.jTextField1.requestFocus();
			return;
		}

		if (!CharacterUtil.isLegalName(userName))
		{
			JOptionPane.showMessageDialog(this, "首尾字符必须是字母或者数字，中间不得出现\\和@，长度为5～14之间", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField1.setText("");
			this.jTextField1.requestFocus();
			return;
		}

		// IPAddress
		if (CharacterUtil.isEmpty(ipAddress))
		{
			JOptionPane.showMessageDialog(this, "IP Address is not null.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField2.setText("");
			this.jTextField2.requestFocus();
			return;
		}

		if (!CharacterUtil.isLegalIPAddress(ipAddress))
		{
			JOptionPane.showMessageDialog(this, "IP Address is not legal.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField2.setText("");
			this.jTextField2.requestFocus();
			return;
		}

		// HostPort
		if (CharacterUtil.isEmpty(hostPort))
		{
			JOptionPane.showMessageDialog(this, "HoostPort is not null.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField3.setText("");
			this.jTextField3.requestFocus();
			return;
		}

		if (!CharacterUtil.isPositiveInteger(hostPort))
		{
			JOptionPane.showMessageDialog(this, "You should input a positive Integer.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			this.jTextField3.setText("");
			this.jTextField3.requestFocus();
			return;
		}

		if (!CharacterUtil.isLegalPort(hostPort))
		{
			JOptionPane.showMessageDialog(this, "You should input the number between 1024~65535.",
					"Warning", JOptionPane.WARNING_MESSAGE);
			this.jTextField3.setText("");
			this.jTextField3.requestFocus();
			return;
		}
		// 结束，所有输入信息都合法

		ClientConnectionThread clientCon = new ClientConnectionThread(this, ipAddress,
				Integer.parseInt(hostPort), userName);
		clientCon.start();
	}

	public static void main(String[] args)
	{
		new Client();
	}

}
