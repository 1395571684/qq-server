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
					if (user.getId() != null) {// 进行的是登录操作
						System.out.println(user.getId() + "         " + user.getPassword());
						// 进行登录验证
						user = uBiz.login(user);
						if (user != null) {// 登录成功
							System.out.println(user.getId());
							ServerBiz.getMaps().put(user.getId(), s);// 把用户放入到map中
							System.out.println(user.getSickname() + "登陆成功");
						}
						ObjectUtil.writeObject(s, user);
						System.out.println("hi");
					} else {// 进行的是注册操作
						System.out.println("正在注册" + user.getSickname());
						ObjectUtil.writeObject(s, uBiz.register(user));
					}
				} else if (o instanceof Find) {// 进行查找操作
					Find find = (Find) o;
					if (find.getType() == find.EXACT) {// 精确查找
						System.out.println("正在进行精确查找");
						ObjectUtil.writeObject(s, uBiz.Select(find.getId()));
					} else {// 查找全部
						ObjectUtil.writeObject(s, uBiz.SelectAll(find.getId()));
						System.out.println("正在进行查找全部");
						System.out.println(find.getId());
					}
				} else if (o instanceof AddFriendMsg) {// 用户发送添加好友请求
					AddFriendMsg msg = (AddFriendMsg) o;
					System.err.println(((AddFriendMsg) o).getFrom().getSickname() + "请求加"
							+ ((AddFriendMsg) o).getTo().getSickname() + "为好友");
					Socket sf;
					sf = ServerBiz.getMaps().get(msg.getTo().getId());
					if (sf != null) {// 好友在线
						ObjectUtil.writeObject(sf, msg);// 服务器把消息转发给要添加的好友
					} else {// 好友不在线
						System.out.println("该好友不在线！");
					}

				} else if (o instanceof AddFriendRSMsg) {// 用户发送的添加好友结果
					AddFriendRSMsg addRs = (AddFriendRSMsg) o;
					Socket s = ServerBiz.getMaps().get(addRs.getTo().getId());
					System.out.println(addRs.isResult() + " " + addRs.getFrom() + " " + s);
					if (addRs.isResult() == true) {// 同意添加为好友
						// 修改数据库
						uBiz.addFriend(addRs.getFrom().getId(), addRs.getTo().getId());
						uBiz.addFriend(addRs.getTo().getId(), addRs.getFrom().getId());
					}
					ObjectUtil.writeObject(s, addRs);// 服务器告知请求添加方的添加结果
				} else if (o instanceof SendMsg) {// 用户发送的聊天内容
					SendMsg sMsg = (SendMsg) o;
					Socket sm;
					sm = ServerBiz.getMaps().get(sMsg.getTo().getId());
					if (sm != null) {// 好友在线
						System.out.println(sMsg.getFrom().getSickname() + "正在发送消息：" + sMsg.getMsg() + "给："
								+ sMsg.getTo().getSickname());
						ObjectUtil.writeObject(sm, sMsg);
					} else// 好友不在线
						System.out.println("该好友不在线！");
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
