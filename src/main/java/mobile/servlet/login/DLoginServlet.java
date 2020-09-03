package mobile.servlet.login;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;

/**
 * 
 * ClassName: DLoginServlet
 * 
 * @Description: 功能描述: 钉钉登录 company:北京斯坦德科技发展有限公司
 * @date 2018年9月11日上午11:18:51
 */
public class DLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -6080111682538491505L;
	private static Logger log = LoggerFactory.getLogger(DLoginServlet.class);
	public static final String BIKey="sitande@2017";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject retJSON = new JSONObject();
		String toUrl = "";
		try {
			String userCode = ""; 
			String code = request.getParameter("code");
			String appid = request.getParameter("appId");
			String corpId = request.getParameter("corpId"); 
			String bipAppId = request.getParameter("bipAppId");
			IInsorgBiz insorgBiz = new InsorgBizImpl();  
			String key = insorgBiz.getWorkKey(corpId,appid); 
			log.info("code:"+code+",appid:"+appid+",corpId:"+corpId+",bipAppId:"+bipAppId);
			// 获取钉钉token
			AccessToken acct = TokenThread.getDDAccToken(key+"-"+appid);
			if(acct == null || code ==null ||appid==null){
				retJSON.put("toUrl", "./error.html");
				retJSON.put("code", "0");
				response.sendRedirect("./error.html");
				return;
			}
			// 构建获取用户ID连接
			
			DingTalkClient client = new DefaultDingTalkClient(APIAddr.DD_USER_CODE);
			OapiUserGetuserinfoRequest getUserReq = new OapiUserGetuserinfoRequest();
			getUserReq.setCode(code);
			getUserReq.setHttpMethod("GET");
			OapiUserGetuserinfoResponse getUserResp = client.execute(getUserReq, acct.getD_accessToken());
	 
			if (getUserResp.getErrcode() == 0) {
				String ddUsrCode = getUserResp.getUserid();
		 
				IUserBiz usersBiz = new UserBizImpl();
				Users user = usersBiz.getUserIdByDID(ddUsrCode, corpId);

				if(user != null && bipAppId.equals("99")){ 
					retJSON.put("code", 0);
					retJSON.put("userCode", user.getTel());
					OutputStream outputStream = response.getOutputStream();
					outputStream.write(retJSON.toString().getBytes("UTF-8"));
					outputStream.close(); 
					return;
				}
				if (user != null) {
					user.setD_corpid(corpId);
					user.setLoginType("d");
					user.setLoginAppid(appid);
					user.setW_corpid("n-u-l-l");
					request.getSession().setAttribute("sessionUser", user);
				} else {
					retJSON.put("code", "-1");
					return;
				}
				if(appid.equals("00")){
					// 获取钉钉用户详细信息 更新用户头像
					client = new DefaultDingTalkClient(APIAddr.DD_USER_INFO);
					OapiUserGetRequest usrInfoReq = new OapiUserGetRequest();
					usrInfoReq.setUserid(userCode);
					usrInfoReq.setHttpMethod("GET");
					OapiUserGetResponse usrInfoResp = client.execute(usrInfoReq, acct.getD_accessToken());
					if(usrInfoResp.getErrcode() == 0) {
						String avatar = usrInfoResp.getAvatar();
						if(avatar != null && !avatar.equals("")){
							user.setD_imgurl(avatar);
							usersBiz.uodateUsersImgUrl(user, "d");
						}
					} 
				}
				
				String redirect_domain = acct.getDomainName();//"http://192.168.0.200:8080/weixinweb/";
				String home = redirect_domain+"login/dlogin.jsp?corpId="+corpId+"&appId="+appid+"&bipAppId="+bipAppId;			 
				Cookie cookie = new Cookie("loginURL", home);  
				cookie.setPath(request.getContextPath());  
				response.addCookie(cookie); 
				if(bipAppId.equals("01")){
					log.info("跳转到 审批页面");
					response.sendRedirect("ButtonServlet");
				}else if (bipAppId.equals("04")){
					log.info("跳转到 公告页面");
					response.sendRedirect("OaggtzServlet");
				} 
				retJSON.put("toUrl", toUrl);
				retJSON.put("code", "0");
			} else {
				retJSON.put("code", "-1");
				return;
			}
		} catch (Exception e) {
			retJSON.put("code", "-1");
			e.printStackTrace();
		} finally {
			log.info(retJSON.toString());
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(retJSON.toString().getBytes("UTF-8"));
//			outputStream.close();
		}
	}
	public static Object sha_md5(byte[] ssrc, String encName, boolean tohex)
			throws Exception {
		if ((encName == null) || (encName.length() < 1))
			encName = "MD5";
		MessageDigest md = MessageDigest.getInstance(encName);
		md.update(ssrc);
		byte[] bits = md.digest();
		if (tohex)
			return byteTOhex(bits);
		return bits;
	}
	public static String byteTOhex(byte[] bits) {
		StringBuffer buf = new StringBuffer();
		int cc = bits.length;
		for (int i = 0; i < cc; i++) {
			int t0 = bits[i] & 0xFF;
			if (t0 < 16)
				buf.append("0");
			buf.append(Integer.toHexString(t0));
		}
		return buf.toString();
	} 
}
