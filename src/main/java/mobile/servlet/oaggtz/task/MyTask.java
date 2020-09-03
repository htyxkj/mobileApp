package mobile.servlet.oaggtz.task;

import java.util.List;

import mobile.biz.IInsorgBiz;
import mobile.biz.IUserBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.biz.impl.UserBizImpl;
import mobile.util.SendTxtToUser;

public class MyTask implements NoticeI {
	private String w_corpid,w_appid,scm,d_corpid,d_appid;
	public static List<String> ListSU=null;
	IInsorgBiz insorgBiz = new InsorgBizImpl();  
	public void run() {
		try { 
			IUserBiz userBiz = new UserBizImpl();
			ListSU=userBiz.getUserList("",w_corpid,d_corpid);
	    	for (int j = 0; j < ListSU.size(); j++) {
	    		String tous=""+ListSU.get(j);
				//统计每个人有多少条未读消息？
				//进行发送
				SendTxtToUser.wxInformUser("您有新的公告请查看!",tous, w_corpid,w_appid, scm);
				String key = insorgBiz.getWorkKey(d_corpid,d_appid);
				SendTxtToUser.ddInformUser("您有新的公告请查看!", tous, d_corpid, d_appid, scm,key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(String w_corpid,String w_appid,String scm,String d_corpid,String d_appid) {
		this.w_corpid = w_corpid;
		this.w_appid = w_appid;
		this.d_corpid = d_corpid;
		this.d_appid = d_appid;
		this.scm = scm;
		new Thread(this).start();
	}
}