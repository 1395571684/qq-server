package qqServer.entity;

import java.io.Serializable;

public class RegisterRs implements Serializable {
	private boolean rs;// ע���Ƿ�ɹ�
	private String id;// ע��ɹ���õ��˺�

	public boolean getRs() {
		return rs;
	}

	public void setRs(boolean rs) {
		this.rs = rs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
