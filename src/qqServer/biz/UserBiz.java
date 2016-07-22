package qqServer.biz;

import java.util.ArrayList;
import java.util.List;

import qqServer.dao.UserDao;
import qqServer.dao.impl.UserDaoImpl;
import qqServer.entity.RegisterRs;
import qqServer.entity.User;

public class UserBiz {
	UserDao uDao = new UserDaoImpl();

	// ��¼
	public User login(User u) {
		u = uDao.login(u.getId(), u.getPassword());

		if (u != null) {
			u.setFriend(uDao.selectMyFriend(u.getId()));
			return u;
		} else
			return null;
	}

	public RegisterRs register(User u) {
		RegisterRs rs = new RegisterRs();
		rs.setRs(uDao.register(u.getSickname(), u.getAge(), u.getEmail(), u.getImg(), u.getPassword()));
		if (rs.getRs() == true) {// ע��ɹ�
			rs.setId(u.getSickname());
			System.out.println("��õ�ID" + rs.getId());
			return rs;
		} else {// ע��ʧ��

			return null;
		}
	}

	public List<User> SelectAll(String myId) {// ����ȫ������
		List<User> fList = new ArrayList<User>();
		fList = uDao.selectAllFriend(myId);
		if (fList != null) {
			return fList;
		} else
			return null;

	}

	public List<User> Select(String friendId) {// ���������Һ���
		List<User> fList = new ArrayList<User>();
		fList = uDao.selectFriend(friendId);
		if (fList != null) {
			return fList;
		} else
			return null;
	}

	public void addFriend(String myId, String friendId) {// ��Ӻ���
		uDao.addFriend(myId, friendId);
	}
}
