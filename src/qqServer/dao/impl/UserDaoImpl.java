package qqServer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import qqServer.dao.UserDao;
import qqServer.entity.User;
import qqServer.util.Util;

public class UserDaoImpl implements UserDao {
	Connection conn = null;
	PreparedStatement stat = null;
	ResultSet rs = null;
	String sql = null;
	static List<User> list = new ArrayList<User>();
	Scanner input = new Scanner(System.in);

	@Override

	public User login(String id, String password) {// 登录
		// TODO Auto-generated method stub
		try {
			User u = new User();
			sql = "select * from qq_user where id=? and password=?";
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setString(2, password);
			rs = stat.executeQuery();
			if (rs.next()) {
				u.setId(rs.getString(1));
				u.setSickname(rs.getString(2));
				u.setAge(rs.getInt(3));
				u.setEmail(rs.getString(4));
				u.setImg(rs.getString(5));
				u.setPassword(rs.getString(6));
				return u;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("no");
		return null;
	}

	@Override

	public boolean register(String sickname, int age, String email, String img, String password) {// 注册
		// TODO Auto-generated method stub
		sql = "insert into qq_user(id,sickname,age,email,img,password)values(getid(),?,?,?,?,?)";
		try {
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, sickname);
			stat.setInt(2, age);
			stat.setString(3, email);
			stat.setString(4, img);
			stat.setString(5, password);
			stat.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> selectFriend(String friendId) {// 精确查找

		sql = "select * from qq_user where id=?";
		List<User> listUser = new ArrayList<User>();
		try {
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, friendId);
			stat.executeQuery();
			rs = stat.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getString(1));
				user.setSickname(rs.getString(2));
				user.setAge(rs.getInt(3));
				user.setEmail(rs.getString(4));
				user.setImg(rs.getString(5));
				user.setPassword(rs.getString(6));
				listUser.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listUser;
	}

	public List<User> selectMyFriend(String id) {// 查找我已经存在的好友
		// TODO Auto-generated method stub
		sql = "select * from qq_user where id in(select friend_id from qq_relation where user_id=?)";
		List<User> listUser = new ArrayList<User>();
		try {
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			rs = stat.executeQuery();
			while (rs.next()) {
				System.out.println("hi1");
				User user = new User();
				user.setId(rs.getString(1));
				user.setSickname(rs.getString(2));
				user.setAge(rs.getInt(3));
				user.setEmail(rs.getString(4));
				user.setImg(rs.getString(5));
				user.setPassword(rs.getString(6));
				System.out.println(user.getSickname());
				listUser.add(user);
			}
			return listUser;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void addFriend(String userId, String friendId) {// 添加好友
		// TODO Auto-generated method stub
		sql = "insert into qq_relation(user_id,friend_id)values(?,?)";
		try {
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, userId);
			stat.setString(2, friendId);
			stat.executeQuery();
			conn.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		UserDaoImpl userDao = new UserDaoImpl();
		System.out.println(userDao.getId("tom"));
	}

	@Override
	public String getId(String sickname) {// 获取自己的账号
		// TODO Auto-generated method stub
		String id;
		String sql;
		sql = "select id from qq_user where sickname=?";
		conn = new Util().getCon();
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, sickname);
			rs = stat.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> selectAllFriend(String myId) {// 查找除自己和自己的好友以外的全部好友
		// TODO Auto-generated method stub
		List<User> listUser = new ArrayList<User>();
		// sql="select * from qq_user where id!=?";
		// sql="select * from qq_user where id not in(select friend_id from
		// qq_relation where user_id=?)";
		sql = "select a.* from(select * from qq_user where id not in(select friend_id from qq_relation where user_id=?))a,(select id from qq_user where id=?)b where a.id!=b.id";
		try {
			conn = new Util().getCon();
			stat = conn.prepareStatement(sql);
			stat.setString(1, myId);
			stat.setString(2, myId);
			rs = stat.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getString(1));
				user.setSickname(rs.getString(2));
				user.setAge(rs.getInt(3));
				user.setEmail(rs.getString(4));
				user.setImg(rs.getString(5));
				user.setPassword(rs.getString(6));
				listUser.add(user);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listUser;

	}

}
