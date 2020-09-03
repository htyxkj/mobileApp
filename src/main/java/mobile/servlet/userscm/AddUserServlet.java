package mobile.servlet.userscm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.UUID;

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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserCreateRequest;
import com.dingtalk.api.request.OapiUserGetByMobileRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.response.OapiUserCreateResponse;
import com.dingtalk.api.response.OapiUserGetByMobileResponse;
import com.dingtalk.api.response.OapiUserGetResponse;

/**
 * 
 * ClassName: AddUserServlet
 * @Description: 功能描述: 同步平台用户至微信 钉钉
 * company:北京斯坦德科技发展有限公司
 * @date 2018年9月10日下午4:41:24
 */
public class AddUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(AddUserServlet.class);
	private static String retStr = "";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 *将平台用户批量添加到钉钉/微信端
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		retStr = "";
		try {
			String jsonstr ="";
			//获取post参数
			BufferedReader br = new BufferedReader(isr);
			jsonstr=br.readLine();
			jsonstr=URLDecoder.decode(jsonstr,"UTF-8");
			//去除数据中的换行符
			jsonstr=jsonstr.replaceAll("\r\n", "");
			jsonstr=jsonstr.replaceAll("\r", "");
			jsonstr=jsonstr.replaceAll("\n", "");
			jsonstr=jsonstr.replaceAll("\t", "");
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			JSONArray arry = JSONArray.fromObject(jsonObject.get("users"));
			IUserBiz userBiz = new UserBizImpl();
			for (int i = 0; i < arry.size(); i++) {
				//单条信息
				JSONObject jsonuser = arry.getJSONObject(i);
				log.info(jsonuser);
//				str += wxUser(jsonuser);
				String ddUserid  =  ddUser(jsonuser);
				
				String bipUserCode = jsonuser.getString("usrcode");
				String d_corpid = jsonuser.getString("d_corpid");
				String tel = jsonuser.getString("tel");
//				userBiz.delUsers(bipUserCode, d_corpid);//根据编码删除一次
				userBiz.delUsersByTel(tel, d_corpid);//根据手机号删除一次
				
				Users users = new Users();
				users.setUserid(bipUserCode);
				users.setUsername(jsonuser.getString("usrname"));
				users.setTel(tel);
				users.setScm(jsonuser.getString("scm"));
				users.setEmail(jsonuser.getString("email"));
				users.setW_corpid(jsonuser.getString("w_corpid"));
				users.setD_corpid(d_corpid);
				users.setD_userid(ddUserid);
				users.setD_imgurl("img/ren.png");
				users.setW_imgurl("img/ren.png");
				userBiz.insertUser(users);
				
			} 
			if(!retStr.equals("")) {
				retStr = "-1;"+retStr;
			}else {
				retStr = "0;同步完成";
			}
			log.info(retStr);
		} catch (Exception e) {
			retStr="-1;同步失败";
			e.printStackTrace();
		}finally{ 
			OutputStream outputStream = response.getOutputStream();  
	        outputStream.write(retStr.getBytes("UTF-8"));
	        outputStream.close(); 
	        isr.close();
			is.close();
		}
	}
	
	/**
	 * 同步微信用户
	 */
	private static String wxUser(JSONObject jsonuser){
		String userid = null;
		JSONObject jsonobj = null;
		String zt = "";
		String wxscmid=jsonuser.getString("w_corpid");
		if(wxscmid == null ||wxscmid.equals(""))
			return "";
		AccessToken acc = TokenThread.getWXAccToken(wxscmid+"-"+"00");
		userid=jsonuser.getString("usrcode");
		//数据--添加
		String jsonString="{\"userid\": \""+userid+"\",\"name\": \""+jsonuser.get("usrname")+"\",\"department\": [1],\"mobile\":\""+jsonuser.get("tel")+"\",\"email\":\""+jsonuser.get("email")+"\"}";
		//数据--修改
		String xgString="{\"userid\": \""+userid+"\",\"name\": \""+jsonuser.get("usrname")+"\",\"mobile\":\""+jsonuser.get("tel")+"\",\"email\":\""+jsonuser.get("email")+"\"}";
		//查询企业号内是否有该员工   有进行修改   没有进行添加
		if(acc!=null){
			String requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="
					+ acc.getW_accessToken() + "&userid=" + userid;
			jsonobj = HttpUtil.httpRequest(requestUrl, "POST", jsonString);
			if(jsonobj.get("errcode").equals("50002")){
				zt="-1;成员不在权限范围";
			}
			if (jsonobj.get("errmsg").equals("ok")) {//有
				requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=" + acc.getW_accessToken();
				jsonobj = HttpUtil.httpRequest(requestUrl, "POST", xgString); 
			} else {//没有
				requestUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=" + acc.getW_accessToken();
				jsonobj = HttpUtil.httpRequest(requestUrl, "POST", jsonString);
			}
			log.info(jsonobj);
			String error = jsonobj.get("errcode").toString();
			if(!error.equals("0") && !error.equals("40103")){
				zt="-1;"+jsonuser.get("usrname")+"同步失败";
			} 
		} 
		return zt;
	}
	/**
	 * 同步钉钉用户
	 */
	private static String ddUser(JSONObject jsonuser){
		String userid = "";
		try {
			IInsorgBiz insorgBiz = new InsorgBizImpl(); 
			String appid = insorgBiz.getDDUserAppID(jsonuser.getString("d_corpid"));
			String key = insorgBiz.getWorkKey(jsonuser.getString("d_corpid"),appid);
			AccessToken acc = TokenThread.getDDAccToken(key+"-"+appid);
			String tel =jsonuser.getString("tel");
			String usrname =jsonuser.getString("usrname");
			if(acc !=null){
				//根据手机号获取钉钉ID，没有获取到进行人员添加
				DingTalkClient client = new DefaultDingTalkClient(APIAddr.DD_USER_BY_MOBILE);
				OapiUserGetByMobileRequest request = new OapiUserGetByMobileRequest();
				request.setMobile(tel);
				OapiUserGetByMobileResponse execute = client.execute(request,  acc.getD_accessToken());
				if(execute.getErrcode() ==0) {
					userid = execute.getUserid();
				}else {
					userid = UUID.randomUUID().toString().replaceAll("-","");
					DingTalkClient creatClient = new DefaultDingTalkClient(APIAddr.DD_USER_CREATE);
					OapiUserCreateRequest crearReq = new OapiUserCreateRequest();
					crearReq.setUserid(userid);
					crearReq.setMobile(tel);
					crearReq.setName(usrname);
					// 需要用字符串， "[59869009,60345027]" 这种格式  新建用户默认添加至根部门
					crearReq.setDepartment("[1]");
					OapiUserCreateResponse crearResp = creatClient.execute(crearReq, acc.getD_accessToken());
					if(crearResp.getErrcode() !=0 && crearResp.getErrcode()!= 40103){ //0：成功     40103：该用户需要其同意才可添加，已向对方发送邀请
						retStr += usrname+"同步失败，ERRCODE:"+crearResp.getErrcode()+"；";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return userid;
	}
}