package mobile.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mobile.biz.IInsorgBiz;
import mobile.biz.IMessageBiz;
import mobile.biz.INumBiz;
import mobile.biz.IUserBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.biz.impl.MessageBizImpl;
import mobile.biz.impl.NumBizImpl;
import mobile.biz.impl.UserBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.Num;
import mobile.entity.Users;
import mobile.tokenThread.TokenThread;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request.BtnJsonList;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;

public class SendTxtToUser{
	private static Logger log = LoggerFactory.getLogger(SendTxtToUser.class);
	
	private static INumBiz numBiz = new NumBizImpl();
	private static IMessageBiz megBiz = new MessageBizImpl();
	private static IInsorgBiz insorgBiz = new InsorgBizImpl();
	
	/**
	 * 发送微信消息 
	 * @param conetnt 消息内容
	 * @param weixinid 接收人 企业号内id
	 * @param wxscmid 企业号标识
	 * @param appid	企业号内应用id
	 * @return 执行状态 
	 */
	public static String wxInformUser(String conetnt,String weixinid,String wxscmid,String appid,String scm){
		if(appid == null || appid.equals("") || appid.equals("null"))
			return "no";
		String zt="";
		try {
			String str[]=weixinid.split("\\|");
			for (String string : str) {
				weixinid=string;
//				if(weixinid.indexOf("@")==-1){
//					IUserBiz userBiz = new UserBizImpl();
//					String email=oU.getEmail(weixinid, wxscmid);
//					if(email!=null&&!email.equals("")&&!email.equals("null")&&!email.equals("\"null\""))
//						weixinid=email;
//				}
			 	AccessToken acc=TokenThread.getWXAccToken(wxscmid+"-"+appid);
			 	if(acc!=null){
				String requestUrl="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+acc.getW_accessToken();
				String outputStr="{\"touser\": \""+weixinid+"\","//
						+ "\"msgtype\": \"text\",  "
						+ "\"agentid\": "+appid+", "
						+ "\"text\": { \"content\": \""+conetnt+"\" },"
						+ "\"safe\":0}"; 
				String requestMethod="POST";
				JSONObject jsonObject=HttpUtil.httpRequest(requestUrl, requestMethod, outputStr);
				log.info(outputStr+"返回码:"+jsonObject.getString("errcode"));
				zt=jsonObject.getString("errmsg");
				
				if(jsonObject.getInt("errcode")==0){
					Num m=new Num();
					m.setAppid(appid);
					m.setW_corpid(wxscmid);
					m.setWeixinid(weixinid);
					m.setScm(scm);
					m.setTime(new Date());
					m.setContent(conetnt);
					numBiz.insertNum(m);
					zt="ok";
				}else{
					zt="no";
				}
			 	}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return zt;
	}
	/**
	 * 执行 发送 消息 并统计待审数据条数
	 */
	public static String wxToSendSPMsg(String keyid,String weixinid,String wxscmid,String scm,String appid){
		if(appid == null || appid.equals("") || appid.equals("null"))
			return "no";
		String zt="";
		try {
			String str[]=weixinid.split("\\|");
			for (String string : str) {
				if(string==null||string .equals(""))
					continue;
				int num1=megBiz.showNum(string, "0",wxscmid);
			 	AccessToken acc=TokenThread.getWXAccToken(wxscmid+"-"+appid);
			 	if(acc!=null){
			 		String content="";
		 			if(num1==0){
		 				content="您暂无待审批任务";
		 			}else{
		 				content="您有"+num1+"条待审批任务";
		 			}
		 			if(keyid!=null)
		 				content=keyid;
					String requestUrl="https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+acc.getW_accessToken();
					String outputStr="{\"touser\": \""+string+"\","//
							+ "\"msgtype\": \"text\",  "
							+ "\"agentid\":"+appid+","
							+ "\"text\": { \"content\": \""+content+"\" },"
							+ " \"safe\":0}";
					String requestMethod="POST";
					JSONObject jsonObject=HttpUtil.httpRequest(requestUrl, requestMethod, outputStr);
					log.info(outputStr+"返回码:"+jsonObject.getString("errcode"));
					zt=jsonObject.getString("errmsg"); 
					
					if(jsonObject.getInt("errcode")==0){
						Num m=new Num();
						m.setAppid(appid);
						m.setW_corpid(wxscmid);
						m.setWeixinid(weixinid);
						m.setScm(scm);
						m.setTime(new Date());
						m.setContent(content);
						numBiz.insertNum(m);
						zt="ok";
					}else{
						zt="no";
					}
			 	}else{
			 		zt="no";
			 	}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return zt;
	}
	
	/**
	 * 发送微信消息 
	 * @param conetnt 消息内容
	 * @param weixinid 接收人 id
	 * @param wxscmid 钉钉企业号标识
	 * @param appid	dd企业号内应用id
	 * @param appKey 应用key
	 * @return 执行状态 
	 */
	public static String ddInformUser(String conetnt,String weixinid,String ddscmid,String appid,String scm,String appKey){
		if(appid == null || appid.equals("") || appid.equals("null"))
			return "no";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date());
		String zt="";
		try {
			String str[]=weixinid.split("\\|");
			for (String string : str) {
				if(string==null||string .equals(""))
					continue;
				IUserBiz userBiz = new UserBizImpl();
				String[] bipuid = string.split(";");
				String d_userid = "";
				for(String bipid :bipuid) {
					Users u =new Users();
					u.setUserid(bipid);
					u.setD_corpid(ddscmid);
					u.setW_corpid(ddscmid);
					Users user = userBiz.getUser(u);
					d_userid += user.getD_userid()+",";
				}
				d_userid = d_userid.substring(0,d_userid.length()-1);
				
			 	AccessToken acc=TokenThread.getDDAccToken(appKey+"-"+appid);
			 	if(acc!=null){
			 		String bipAppId = insorgBiz.selBipAppid(ddscmid, appid);
		 			String title = "";
		 			String btnTitle = "";

		 			String url = acc.getDomainName()+"login/dlogin.jsp?corpId="+ddscmid+"&appId="+appid+"&bipAppId="+bipAppId;
		 			
		 			log.info("user:"+d_userid);
		 			log.info("conetnt:"+conetnt);
		 			log.info("url:"+url);
		 			
		 			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
		 			OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		 			request.setUseridList(d_userid);
		 			request.setAgentId(Long.valueOf(appid));
		 			request.setToAllUser(false);
		 			
		 			if(bipAppId.equals("04")){
		 				title = "公告中心";
		 				btnTitle = "查看";
		 				OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
			 			msg.setMsgtype("action_card");
			 			msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
			 			msg.getActionCard().setTitle(date+":"+title);
			 			msg.getActionCard().setMarkdown("#### " +conetnt);
			 			msg.getActionCard().setBtnOrientation("1");
			 			List<BtnJsonList> btnJsonList = new ArrayList<BtnJsonList>();
			 			BtnJsonList btn = new BtnJsonList();
			 			btn.setActionUrl(url);
			 			btn.setTitle(btnTitle);
			 			btnJsonList.add(btn);
						msg.getActionCard().setBtnJsonList(btnJsonList );
			 			request.setMsg(msg);
		 			}else{
		 				OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		 				msg.setMsgtype("text");
		 				msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		 				msg.getText().setContent(conetnt);
		 				request.setMsg(msg);
		 			}
		 			OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,acc.getD_accessToken());
					 
					if(response.getErrorCode().equals("0")||response.getErrcode().equals("0")){
						Num m=new Num();
						m.setAppid(appid);
						m.setW_corpid(ddscmid);
						m.setWeixinid(weixinid);
						m.setScm(scm);
						m.setTime(new Date());
						m.setContent(conetnt);
						numBiz.insertNum(m);
						zt="ok";
					}else{
						log.info(response.getErrorCode()+":"+response.getErrmsg());
						zt="no";
					}
			 	}else{
			 		zt="no";
			 	}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return zt;
	}
	 
	/***
	 * 执行 发送 消息 并统计待审数据条数
	 * @param keyid		 内容
	 * @param weixinid	接收人
	 * @param appkey	应用key
	 * @param scm		公司
	 * @param appid		应用编码
	 * @param corpid	企业号唯一标识
	 * @return
	 */
	public static String ddToSendSPMsg(String keyid,String weixinid,String appkey,String scm,String appid,String corpid){
		if(appid == null || appid.equals("") || appid.equals("null"))
			return "no";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = sdf.format(new Date());
		String zt="";
		try {
			String str[]=weixinid.split("\\|");
			for (String string : str) {
				if(string==null||string .equals(""))
					continue; 
				
				IUserBiz userBiz = new UserBizImpl();
				String[] bipuid = string.split(";");
				String d_userid = "";
				for(String bipid :bipuid) {
					Users u =new Users();
					u.setUserid(bipid);
					u.setD_corpid(corpid);
					u.setW_corpid(corpid);
					Users user = userBiz.getUser(u);
					if(user != null) {
						d_userid += user.getD_userid()+",";
					}else {
						log.info(bipid+"/"+corpid);
					}
				}
				if(d_userid.length()<=0)
					return "ok";
				d_userid = d_userid.substring(0,d_userid.length()-1);
				
				
				int num1=megBiz.showNum(string, "0",corpid);
			 	AccessToken acc=TokenThread.getDDAccToken(appkey+"-"+appid);
			 	if(acc!=null){
			 		String content="";
		 			if(num1==0){
		 				content="您暂无待审批任务";
		 			}else{
		 				content=" 您有"+num1+"条待审批任务";
		 			}
		 			if(keyid!=null)
		 				content=keyid; 
		 			String bipAppId = "01";
		 			String title = "";
		 			String btnTitle = "";
//		 			String url = acc.getDomainName()+"login/dlogin.jsp?corpId="+corpid+"&appId="+appid+"&bipAppId="+bipAppId;
		 			String vs = System.currentTimeMillis()+"";
		 			String url = acc.getServerurl()+"/webMobile/mobileWD?corpId="+corpid+"&agentId="+appid+"&bipAppId="+bipAppId+"&jumpRoute=wxAppletsMsg&vs="+vs;

		 			log.info("user:"+d_userid);
		 			log.info("conetnt:"+content);

		 			if(bipAppId.equals("01")){
		 				title = "审批中心";
		 				btnTitle = "审批";
		 			}else if(bipAppId.equals("04")){
		 				title = "公告中心";
		 				btnTitle = "查看";
		 			}

		 			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
		 			OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		 			request.setUseridList(d_userid);
		 			request.setAgentId(Long.valueOf(appid));
		 			request.setToAllUser(false);
		 			OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		 			msg.setMsgtype("action_card");
		 			msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
		 			msg.getActionCard().setTitle(date+":"+title);
		 			msg.getActionCard().setMarkdown("#### " +content); 
		 			msg.getActionCard().setBtnOrientation("1");
		 			List<BtnJsonList> btnJsonList = new ArrayList<BtnJsonList>();
		 			BtnJsonList btn = new BtnJsonList();
		 			btn.setActionUrl(url);
		 			btn.setTitle(btnTitle);
		 			btnJsonList.add(btn);
					msg.getActionCard().setBtnJsonList(btnJsonList );
		 			request.setMsg(msg);
		 			OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,acc.getD_accessToken());
					 
					if(response.getErrorCode().equals("0")||response.getErrcode().equals("0")){
						Num m=new Num();
						m.setAppid(appid);
						m.setW_corpid(corpid);
						m.setWeixinid(weixinid);
						m.setScm(scm);
						m.setTime(new Date());
						m.setContent(content);
						numBiz.insertNum(m);
						zt="ok";
					}else{
						log.info(response.getErrorCode()+":"+response.getErrmsg());
						zt="no";
					}
			 	}else{
			 		zt="no";
			 	}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return zt;
	}
}