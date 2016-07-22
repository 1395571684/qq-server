package qqServer.thread;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

import qqServer.biz.ServerBiz;
import qqServer.biz.UserBiz;
import qqServer.entity.AddFriendMsg;
import qqServer.entity.AddFriendRSMsg;
import qqServer.entity.Find;
import qqServer.entity.SendMsg;
import qqServer.entity.User;
import qqServer.util.ObjectUtil;

public class ClientThread extends Thread {
	Socket s;
	UserBiz uBiz = new UserBiz();

	public ClientThread(Socket s) {
		super();
		this.s = s;
	}

	public void run() {
		while (true) {
			User user;
			try {
				Object o = ObjectUtil.readObject(s);
				if (o instanceof User) {
					user = (User) o;
					if (user.getId() != null) {// ���е��ǵ�¼����
						System.out.println(user.getId() + "         " + user.getPassword());
						// ���е�¼��֤
						user = uBiz.login(user);
						if (user != null) {// ��¼�ɹ�
							System.out.println(user.getId());
							ServerBiz.getMaps().put(user.getId(), s);// ���û����뵽map��
							System.out.println(user.getSickname() + "��½�ɹ�");
						}
						ObjectUtil.writeObject(s, user);
						System.out.println("hi");
					} else {// ���е���ע�����
						System.out.println("����ע��" + user.getSickname());
						ObjectUtil.writeObject(s, uBiz.register(user));
					}
				} else if (o instanceof Find) {// ���в��Ҳ���
					Find find = (Find) o;
					if (find.getType() == find.EXACT) {// ��ȷ����
						System.out.println("���ڽ��о�ȷ����");
						ObjectUtil.writeObject(s, uBiz.Select(find.getId()));
					} else {// ����ȫ��
						ObjectUtil.writeObject(s, uBiz.SelectAll(find.getId()));
						System.out.println("���ڽ��в���ȫ��");
						System.out.println(find.getId());
					}
				} else if (o instanceof AddFriendMsg) {// �û�������Ӻ�������
					AddFriendMsg msg = (AddFriendMsg) o;
					System.err.println(((AddFriendMsg) o).getFrom().getSickname() + "�����"
							+ ((AddFriendMsg) o).getTo().getSickname() + "Ϊ����");
					Socket sf;
					sf = ServerBiz.getMaps().get(msg.getTo().getId());
					if (sf != null) {// ��������
						ObjectUtil.writeObject(sf, msg);// ����������Ϣת����Ҫ��ӵĺ���
					} else {// ���Ѳ�����
						System.out.println("�ú��Ѳ����ߣ�");
					}

				} else if (o instanceof AddFriendRSMsg) {// �û����͵���Ӻ��ѽ��
					AddFriendRSMsg addRs = (AddFriendRSMsg) o;
					Socket s = ServerBiz.getMaps().get(addRs.getTo().getId());
					System.out.println(addRs.isResult() + " " + addRs.getFrom() + " " + s);
					if (addRs.isResult() == true) {// ͬ�����Ϊ����
						// �޸����ݿ�
						uBiz.addFriend(addRs.getFrom().getId(), addRs.getTo().getId());
						uBiz.addFriend(addRs.getTo().getId(), addRs.getFrom().getId());
					}
					ObjectUtil.writeObject(s, addRs);// ��������֪������ӷ�����ӽ��
				} else if (o instanceof SendMsg) {// �û����͵���������
					SendMsg sMsg = (SendMsg) o;
					Socket sm;
					sm = ServerBiz.getMaps().get(sMsg.getTo().getId());
					if (sm != null) {// ��������
						System.out.println(sMsg.getFrom().getSickname() + "���ڷ�����Ϣ��" + sMsg.getMsg() + "����"
								+ sMsg.getTo().getSickname());
						ObjectUtil.writeObject(sm, sMsg);
					} else// ���Ѳ�����
						System.out.println("�ú��Ѳ����ߣ�");
				}

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
