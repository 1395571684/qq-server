package qqServer.entity;

import java.io.Serializable;

public class AddFriendMsg implements Serializable {
	// �������������͵�������Ӻ�����Ϣ
	private User from;// ����Ӻ��ѵ�һ��
	private User to;// Ҫ��ӵĺ���

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public AddFriendMsg(User from, User to) {
		super();
		this.from = from;
		this.to = to;
	}

}
