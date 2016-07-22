package qqServer.entity;

import java.io.Serializable;

public class AddFriendRSMsg implements Serializable {
	// ��¼�Ƿ�ͬ����Ӻ���
	private User from;// ���Ͷ�
	private User to;// ���ն�
	private boolean result;// �Ƿ�ͬ����

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

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public AddFriendRSMsg(User from, User to, boolean result) {
		super();
		this.from = from;
		this.to = to;
		this.result = result;
	}

}
