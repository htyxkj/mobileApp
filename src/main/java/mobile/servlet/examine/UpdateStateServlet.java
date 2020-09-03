package mobile.servlet.examine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.IMessageBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.biz.impl.MessageBizImpl;
import mobile.entity.Message;
import mobile.util.SendTxtToUser;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateStateServlet extends HttpServlet {
	private static IMessageBiz msgBiz = new MessageBizImpl();
	IInsorgBiz insorgBiz = new InsorgBizImpl();  
	private static Logger log = LoggerFactory.getLogger(UpdateStateServlet.class);  
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 平台  同意，驳回  重置审批信息。
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			log.info("UpdateStateServlet");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String jsonstr = "";
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");  
			BufferedReader br = new BufferedReader(isr);
			jsonstr=br.readLine();
			jsonstr=URLDecoder.decode(jsonstr,"UTF-8");
			log.info(jsonstr);
			jsonstr=jsonstr.replaceAll("\r\n", "");
			jsonstr=jsonstr.replaceAll("\r", "");
			jsonstr=jsonstr.replaceAll("\n", "");
			jsonstr=jsonstr.replaceAll("\t", "");
			jsonstr=jsonstr.replaceAll("null", "");
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			String w_corpid,scm,documentsid,d_corpid;
			w_corpid = jsonObject.getString("w_corpid");
			d_corpid = jsonObject.getString("d_corpid");
			scm=jsonObject.getString("scm");
			documentsid=jsonObject.getString("documentsid");
			String rows ="";
			Message message=new Message();
			String state1=jsonObject.getString("state1");
			String spname=jsonObject.getString("spname");
			String yjcontent=jsonObject.getString("yjcontent");
			if("\"null\"".equals(yjcontent) || yjcontent==null){
				yjcontent = "";
			}
			message.setDocumentsid(documentsid);
			message.setState1(state1);
			message.setState(jsonObject.getInt("state"));
			message.setSpname(spname);
			message.setW_corpid(w_corpid);
			message.setYjcontent(yjcontent);
			message.setD_corpid(d_corpid);
			log.info(message.getState()+"");
			if(jsonObject.getInt("state")==0){//重置审批单据
				String zt=weidu(jsonObject);
			}else if(jsonObject.getInt("state")==-1){//驳回
				List<Message> lm=msgBiz.showSPUser(documentsid,w_corpid,d_corpid );
				message.setSptime(new Date());
				message.setState(2);
				rows =msgBiz.BipUpdate(message);
				rows = msgBiz.BipTuiHui(message);
//				String name=sh.showName(w_corpid,scm,documentsid);
//				sendTxt.tosend("您有一条被驳回消息,请去平台处理！", name,jsonObject.getString("w_corpid"),jsonObject.getString("appid"),scm);
				for(Message m :lm){
					SendTxtToUser.wxToSendSPMsg(null, m.getSpname(),w_corpid,scm,m.getW_appid());

					String key = insorgBiz.getWorkKey(jsonObject.getString("d_corpid"), jsonObject.getString("d_appid"));
					SendTxtToUser.ddToSendSPMsg(null, m.getSpname(),key,scm,m.getD_appid(),jsonObject.getString("d_corpid"));
				}
			}else{
				//同意
				List<Message> lm=msgBiz.showSPUser(documentsid,w_corpid,d_corpid );
				message.setSptime(new Date());
				rows =msgBiz.BipUpdate(message);
				rows = msgBiz.BipTuiHui(message);
				log.info(lm.size()+"");
				for(Message m :lm){
					SendTxtToUser.wxToSendSPMsg(null, m.getSpname(),w_corpid,scm,m.getW_appid());
					String key = insorgBiz.getWorkKey(jsonObject.getString("d_corpid"), jsonObject.getString("d_appid"));
					SendTxtToUser.ddToSendSPMsg(null, m.getSpname(),key,scm,m.getD_appid(),jsonObject.getString("d_corpid"));
				}
			}
			isr.close();
			is.close();
			JSONObject json = new JSONObject();
			json.put("zt", "ok");
			OutputStream outputStream = response.getOutputStream();  
			// 注意编码格式，防止中文乱码  
			outputStream.write(json.toString().getBytes("UTF-8"));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e+"");
		}
	}
	/**
	 *将审批数据重置为未读状态
	 **/
	public String weidu(JSONObject jsonObject){
		try {
			Message message=new Message();
			message.setDocumentsid(jsonObject.getString("documentsid"));
			message.setState1(jsonObject.getString("state1"));
			message.setSpname(jsonObject.getString("spname"));
			message.setW_corpid(jsonObject.getString("w_corpid"));
			message.setD_corpid(jsonObject.getString("d_corpid"));
			String num=msgBiz.ToWeiDu(message);
			SendTxtToUser.wxToSendSPMsg(null, jsonObject.getString("spweixinid"),jsonObject.getString("w_corpid"),jsonObject.getString("scm"),jsonObject.getString("w_appid"));
			String key = insorgBiz.getWorkKey(jsonObject.getString("d_corpid"), jsonObject.getString("d_appid"));
			SendTxtToUser.ddToSendSPMsg(null, jsonObject.getString("spweixinid"),key,jsonObject.getString("scm"),jsonObject.getString("d_appid"),jsonObject.getString("d_corpid"));
//		up.ToHistory(message.getDocumentsid(), message.getW_corpid(), null,message.getState1()+"",message.getSpname());
			return num;
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}
}