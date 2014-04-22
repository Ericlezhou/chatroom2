package com.chat.server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.chat.util.CharacterUtil;
import com.chat.util.XMLUtil;

@SuppressWarnings("serial")
public class Server extends JFrame
{
	private JLabel jLabel1;

	private JLabel jLabel2;

	private JLabel jLabel3;

	private JPanel jPanel1;

	private JPanel jPanel2;

	private JScrollPane jScrollPane;

	private JTextArea jTextArea1;

	private JTextField jTextField1;

	private JButton jButton1;

	private Map<String, ServerMsgThread> map = new HashMap<String, ServerMsgThread>();

	public JTextArea getjTextArea1()
	{
		return jTextArea1;
	}

	public JButton getjButton1()
	{
		return jButton1;
	}

	public void setjButton1(JButton jButton1)
	{
		this.jButton1 = jButton1;
	}

	public JLabel getjLabel2()
	{
		return jLabel2;
	}

	public Map<String, ServerMsgThread> getMap()
	{
		return map;
	}

	public void setMap(Map<String, ServerMsgThread> map)
	{
		this.map = map;
	}

	public Server(String name)
	{
		super(name);

		this.initComponents();
	}

	public void deleteUser(String key)
	{
		map.remove(key);
	}

	private void initComponents()
	{
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		jLabel3 = new JLabel();

		jPanel1 = new JPanel();
		jPanel2 = new JPanel();

		jTextArea1 = new JTextArea();
		jTextField1 = new JTextField(10);
		jScrollPane = new JScrollPane();
		jButton1 = new JButton();

		jPanel1.setBorder(BorderFactory.createTitledBorder("Server Info"));
		jPanel2.setBorder(BorderFactory.createTitledBorder("User List"));

		jLabel1.setText("Current State");
		jLabel2.setText("Stop");
		jLabel2.setForeground(Color.red);
		jLabel3.setForeground(Color.blue);
		jLabel3.setText("Port");
		// test: the port is always 5000.
		jTextField1.setText("5000");

		jButton1.setText("Start The Server");

		jPanel1.add(jLabel1);
		jPanel1.add(jLabel2);
		jPanel1.add(jLabel3);
		jPanel1.add(jTextField1);
		jPanel1.add(jButton1);

		jTextArea1.setEditable(false);

		jTextArea1.setColumns(30);
		jTextArea1.setRows(20);
		jTextArea1.setForeground(Color.DARK_GRAY);

		jScrollPane.setViewportView(jTextArea1);

		jPanel2.add(jScrollPane);

		this.getContentPane().add(jPanel1, BorderLayout.NORTH);
		this.getContentPane().add(jPanel2, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 定义关闭窗口后退出jvm
		this.setResizable(false); // 是否允许更改窗口大小
		this.setTitle("Server");
		this.pack(); // 调节窗口为最适大小
		this.setVisible(true); // 使界面可见

		// 以下对事件进行注册

		jButton1.addActionListener(new ActionListener() // 匿名内部类
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					Server.this.execute();
				}
				catch (NumberFormatException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // 注意此处execute()是非static的，所以使用this代表当前类的一个实例
			}
		});

		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				try
				{
					Collection<ServerMsgThread> collection = Server.this.getMap().values();

					String xml = XMLUtil.constructServerCloseWindowXML();

					for (ServerMsgThread smt : collection)
					{
						smt.sendMsg(xml);
					}
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				finally
				{

					System.exit(0);
				}
			}
		});
	}

	public JTextField getjTextField1()
	{
		return jTextField1;
	}

	private void execute() throws NumberFormatException, Exception
	{
		String hostPort = this.jTextField1.getText();

		if (CharacterUtil.isEmpty(hostPort))
		{
			JOptionPane.showMessageDialog(this, "Port is not null.", "Warning",
					JOptionPane.WARNING_MESSAGE);

			this.jTextField1.setText("");

			this.jTextField1.requestFocus();

			return;
		}

		if (!CharacterUtil.isPositiveInteger(hostPort))
		{
			JOptionPane.showMessageDialog(this, "You should input a positive Integer.", "Warning",
					JOptionPane.WARNING_MESSAGE);

			this.jTextField1.setText("");

			this.jTextField1.requestFocus();

			return;
		}

		if (!CharacterUtil.isLegalPort(hostPort))
		{
			JOptionPane.showMessageDialog(this, "You should input the number between 1024~65535.",
					"Warning", JOptionPane.WARNING_MESSAGE);

			this.jTextField1.setText("");

			this.jTextField1.requestFocus();

			return;
		}

		ServerConnectionThread serverCon = new ServerConnectionThread(this, "ConnetThread",
				Integer.parseInt(hostPort));
		serverCon.start();

	}

	public static void main(String[] args)
	{
		new Server("server");
	}

}
