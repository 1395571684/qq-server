package qqServer.dao;

import java.util.List;

import qqServer.entity.User;

public interface UserDao {
	// 登录
	public User login(String id, String password);

	// 注册
	public boolean register(String sickname, int age, String email, String img, String password);

	// 查找好友
	// 查找全部好友
	public List<User> selectAllFriend(String myId);

	// 按条件查找
	public List<User> selectFriend(String friendId);

	// 查找自己的好友
	public List<User> selectMyFriend(String id);

	// 添加好友
	public void addFriend(String userId, String friendId);

	// 获取账号
	public String getId(String sickname);

}
