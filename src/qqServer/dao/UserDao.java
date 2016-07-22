package qqServer.dao;

import java.util.List;

import qqServer.entity.User;

public interface UserDao {
	// ��¼
	public User login(String id, String password);

	// ע��
	public boolean register(String sickname, int age, String email, String img, String password);

	// ���Һ���
	// ����ȫ������
	public List<User> selectAllFriend(String myId);

	// ����������
	public List<User> selectFriend(String friendId);

	// �����Լ��ĺ���
	public List<User> selectMyFriend(String id);

	// ��Ӻ���
	public void addFriend(String userId, String friendId);

	// ��ȡ�˺�
	public String getId(String sickname);

}
