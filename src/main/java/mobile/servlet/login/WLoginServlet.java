package mobile.servlet.login;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mobile.biz.IUserBiz;
import mobile.biz.impl.UserBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.Users;
import mobile.tokenThread.TokenThread;
import mobile.util.APIAddr;
import mobile.util.HttpUtil;

/**
 * 
 * ClassName: WLoginServlet
 * @Description: 功能描述: 微信用户登录
 * company:北京斯坦德科技发展有限公司
 * @date 2018年9月11日下午3:19:57
 */
public class WLoginServlet extends HttpServlet {
	private static final long serialVersionUID = -7357263538753375967L;
	private static Logger log = LoggerFactory.getLogger(WLoginServlet.class);
	private static IUserBiz userBiz = new UserBizImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String code = request.getParameter("code");
			String appid = request.getParameter("appId");
			String corpId = request.getParameter("corpId");
			String bipAppId = request.getParameter("bipAppId"); 
			//获取微信token
			AccessToken acct = TokenThread.getWXAccToken(corpId+"-"+appid);
			if(acct == null || code ==null){
				request.getRequestDispatcher("/error.html").include(request, response);  
				return;
			}
	 	 	String url = APIAddr.WX_USER_CODE;
			url = url.replace("ACCESS_TOKEN", acct.getW_accessToken()).replace("CODE",code);
			JSONObject jsonObj = HttpUtil.httpRequest(url, "GET",null);
			//获取用户id
			if(jsonObj.getInt("errcode")==0){
				String user_ticket = jsonObj.getString("user_ticket");
				String  ticketURL = APIAddr.WX_USER_INFO;
				ticketURL = ticketURL.replace("ACCESS_TOKEN", acct.getW_accessToken());
				String outStr = "{\"user_ticket\": \""+user_ticket+"\"}";
				jsonObj = HttpUtil.httpRequest(ticketURL,"POST",outStr);
				Users u =new Users();
				u.setUserid(jsonObj.getString("userid"));
				u.setD_corpid(corpId);
				u.setW_corpid(corpId);
				Users user = userBiz.getUser(u);
				String avatar = jsonObj.getString("avatar");
				user.setW_imgurl(avatar);
				if(user != null){
					user.setLoginAppid(appid);
					user.setLoginType("w");
					user.setD_corpid("n-u-l-l");
					request.getSession().setAttribute("sessionUser",user);
				} 
				userBiz.uodateUsersImgUrl(user, "w");
				
				String redirect_domain = acct.getDomainName();//"http://192.168.0.200:8080/weixinweb/";
				String aa = redirect_domain+"WLoginServlet?corpId="+corpId+"&appId="+appid+"&bipAppId="+bipAppId;
				aa = URLEncoder.encode(aa, "UTF-8");
				String home = APIAddr.WX_OAUTH2;
				home = home.replace("CORPID", corpId).replace("AGENTID", appid).replace("REDIRECT_URI", aa);
				Cookie cookie = new Cookie("loginURL", home);  
				cookie.setPath(request.getContextPath());  
				response.addCookie(cookie);
				
				//拼接登录成功后跳转连接
				if(bipAppId.equals("01")){
					log.info("跳转到 审批页面");
					response.sendRedirect("ButtonServlet");
				}else if (bipAppId.equals("04")){
					log.info("跳转到 公告页面");
					response.sendRedirect("OaggtzServlet");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		try {
//			String userid = request.getParameter("userid");
//			String corpId = request.getParameter("corpId");
//			String appid = request.getParameter("appId");
//			String bipAppId = request.getParameter("bipAppId");
//			OperateUsers userServ = new OperateUsers();
//			Users user = userServ.getUser(userid, corpId);
//			user.setLoginType("w");
//			user.setD_corpid("n-u-l-l");
//			user.setLoginAppid(appid);
//			request.getSession().setAttribute("sessionUser", user);
//			// 拼接登录成功后跳转连接
//			if (bipAppId.equals("01")) {
//				response.sendRedirect("ButtonServlet");
//			} else if (bipAppId.equals("04")) {
//				response.sendRedirect("OaggtzServlet");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//	}
}
