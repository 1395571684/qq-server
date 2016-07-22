package qqServer.ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import qqServer.biz.ServerBiz;

public class qqServerFlame extends JFrame {

	private JPanel contentPane;
	private JButton button;
	private JButton button_1;
	//
	private ServerBiz serverBiz = new ServerBiz();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					qqServerFlame frame = new qqServerFlame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public qqServerFlame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		button = new JButton("\u542F\u52A8\u670D\u52A1");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		button.setBounds(66, 95, 129, 51);
		contentPane.add(button);

		button_1 = new JButton("\u505C\u6B62\u670D\u52A1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		button_1.setBounds(246, 95, 129, 51);
		contentPane.add(button_1);
	}

	// 启动服务
	private void start() {
		System.out.println("start");
		button.setEnabled(false);
		button_1.setEnabled(true);
		new Thread() {
			public void run() {
				// TODO Auto-generated method stub
				serverBiz.startServer();
			}
		}.start();

	}

	// 停止服务
	private void stop() {
		System.out.println("stop");
		button.setEnabled(true);
		button_1.setEnabled(false);
		new Thread() {
			public void run() {
				serverBiz.stopServer();
			}
		}.start();
	}
}
