package qqServer.biz;

import java.util.ArrayList;
import java.util.List;

import qqServer.dao.UserDao;
import qqServer.dao.impl.UserDaoImpl;
import qqServer.entity.RegisterRs;
import qqServer.entity.User;

public class UserBiz {
	UserDao uDao = new UserDaoImpl();

	// 登录
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
		if (rs.getRs() == true) {// 注册成功
			rs.setId(u.getSickname());
			System.out.println("获得的ID" + rs.getId());
			return rs;
		} else {// 注册失败

			return null;
		}
	}

	public List<User> SelectAll(String myId) {// 查找全部好友
		List<User> fList = new ArrayList<User>();
		fList = uDao.selectAllFriend(myId);
		if (fList != null) {
			return fList;
		} else
			return null;

	}

	public List<User> Select(String friendId) {// 按条件查找好友
		List<User> fList = new ArrayList<User>();
		fList = uDao.selectFriend(friendId);
		if (fList != null) {
			return fList;
		} else
			return null;
	}

	public void addFriend(String myId, String friendId) {// 添加好友
		uDao.addFriend(myId, friendId);
	}
}
