package mobile.servlet.userscm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.IUserBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.biz.impl.UserBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.Users;
import mobile.tokenThread.TokenThread;
import mobile.util.APIAddr;
import mobile.util.HttpUtil;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserDeleteRequest;
import com.dingtalk.api.response.OapiUserDeleteResponse;

public class DeleteUserServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(DeleteUserServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 *删除企业号的成员  
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		IUserBiz userBiz = new UserBizImpl();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		log.info("收到删除用户请求！");
		try {
			//流里面拿
			String jsonstr = "";
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			jsonstr=br.readLine();
			jsonstr=URLDecoder.decode(jsonstr,"UTF-8");
			isr.close();
			is.close();
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			log.info(jsonstr);
			String userid=jsonObject.getString("userid");//集团编码
			String wxscmid=jsonObject.getString("w_corpid");//企业号标识
			String ddscmid=jsonObject.getString("d_corpid");//企业号标识
			
			 
			userBiz.delUsers(userid, wxscmid);
			userBiz.delUsers(userid, ddscmid);
			
			IInsorgBiz insorgBiz = new InsorgBizImpl();
			String appid = insorgBiz.getDDUserAppID(ddscmid);
			String key = insorgBiz.getWorkKey(ddscmid,appid);
			AccessToken acc= TokenThread.getDDAccToken(key+"-"+appid);
			String requestUrl="https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+acc.getW_accessToken()+"&userid="+userid;
			JSONObject jsonobj=HttpUtil.httpRequest(requestUrl, "GET", null);
			
			
			
			Users u =new Users();
			u.setUserid(userid);
			u.setD_corpid(ddscmid);
			u.setW_corpid(ddscmid);
			Users user = userBiz.getUser(u);
			String d_userid = user.getD_userid();
			
			DingTalkClient client = new DefaultDingTalkClient(APIAddr.DD_USER_DELETE);
			OapiUserDeleteRequest userDeleteRequest = new OapiUserDeleteRequest();
			userDeleteRequest.setUserid(d_userid);
			userDeleteRequest.setHttpMethod("GET");
			OapiUserDeleteResponse userDeleteResponse = client.execute(userDeleteRequest, acc.getD_accessToken());
			
			log.info(jsonobj);
			String _out=""; 
			if(jsonobj.getString("errcode").equals("0") ||userDeleteResponse.getErrcode() == 0){
				userBiz.delUsers(userid, wxscmid);
				_out="1;删除成功!";
			}else if(jsonobj.getString("errcode").equals("60111")){
				userBiz.delUsers(userid, wxscmid);
				_out="1;删除成功!";
			}else{
				_out="-1;删除失败!";
			}
			PrintWriter out = response.getWriter();
			out.write(_out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}