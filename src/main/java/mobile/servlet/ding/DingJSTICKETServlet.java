package mobile.servlet.ding;

import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.entity.AccessToken;
import mobile.tokenThread.TokenThread;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;

public class DingJSTICKETServlet extends HttpServlet {
	private static final Log log = LogFactory.getLog(DingJSTICKETServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
 
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String url = request.getParameter("url");
			String nonceStr = "HTyxkj120808";
			long timeStamp = new Date().getTime();
			String corpId = request.getParameter("corpId");
			String agentId = request.getParameter("agentId");
			
			IInsorgBiz insorgBiz = new InsorgBizImpl();
			String wk = insorgBiz.getWorkKey(corpId,agentId);
			AccessToken acc = TokenThread.getDDAccToken(wk+"-"+agentId);
			
			DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/get_jsapi_ticket");
			OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
			req.setTopHttpMethod("GET");
			OapiGetJsapiTicketResponse execute = client.execute(req, acc.getD_accessToken());
			String ticket= "";
			if(execute.getErrcode() == 0){
				ticket = execute.getTicket(); 
			} else {
				log.info("获取前端js验证错误："+execute.getErrcode()+":"+execute.getErrmsg());
			}
			String signature = sign(ticket, nonceStr, timeStamp, url);
			JSONObject retJSON = new JSONObject();
			retJSON.put("code", 0);
			retJSON.put("DDJSTICKET", signature);
			retJSON.put("timeStamp", timeStamp);
			retJSON.put("nonceStr", nonceStr);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(retJSON.toString().getBytes("UTF-8"));
			outputStream.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
  public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws Exception {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHexString(sha1.digest());
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
  public static String bytesToHexString(byte[] src){
      StringBuilder stringBuilder = new StringBuilder("");
      if (src == null || src.length <= 0) {
          return null;
      }
      for (int i = 0; i < src.length; i++) {
          int v = src[i] & 0xFF;
          String hv = Integer.toHexString(v);
          if (hv.length() < 2) {
              stringBuilder.append(0);
          }
          stringBuilder.append(hv);
      }
      return stringBuilder.toString();
  }

}
