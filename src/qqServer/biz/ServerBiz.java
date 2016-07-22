package qqServer.biz;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import qqServer.thread.ClientThread;

public class ServerBiz {
	ServerSocket server = null;
	Socket socket = null;
	ClientThread cThread;
	private static Map<String, Socket> maps = new HashMap<String, Socket>();

	public static Map<String, Socket> getMaps() {
		return maps;
	}

	public void startServer() {
		try {
			server = new ServerSocket(8000);
			System.out.println("�������ѿ�����");
			while (true) {
				socket = server.accept();
				System.out.println("�ͻ��������ӣ�");
				System.out.println(socket+""+server);
				cThread = new ClientThread(socket);
				cThread.start();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("����������ʧ�ܣ�����");
		}
	}

	public void stopServer() {
		try {
			server.close();
			System.out.println("�������ѹرգ�");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("�������ر�ʧ�ܣ�����");
		}
	}

}
