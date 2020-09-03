package mobile.servlet.inform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.registered.SRegServ;
import mobile.util.SendTxtToUser;
import net.sf.json.JSONObject;

public class InformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 消息通知
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		try {
			String zt="";
			//流里面拿平台提交数据
			String jsonstr = "";
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			jsonstr=br.readLine();
			jsonstr=URLDecoder.decode(jsonstr,"UTF-8");
	 		isr.close();
			is.close();
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			//{"title":"XXXX","type":"XXXX","content":"XXXX","users":"XXXX|XXXX|XXXX","w_corpid":"XXXX","appid":"XXXX","bipappid":"XXXX"}
			String conetnt=jsonObject.getString("content");
			String users=jsonObject.getString("users");
			String w_appid=jsonObject.getString("w_appid");
			String w_corpid=jsonObject.getString("w_corpid");
			String d_appid=jsonObject.getString("d_appid");
			String d_corpid=jsonObject.getString("d_corpid");
			String scm=jsonObject.getString("scm");  
			
			SRegServ t=new SRegServ();
			Object[] obj=(Object[]) t.processOperator("isReg",w_corpid);
			if(obj[0].equals("1")||obj[0].equals("-1")){
				return;
			}
//			TokenThread tokenThread=new TokenThread();
//		 	Map<String, AccessToken> map=tokenThread.maplist;
//		 	AccessToken accessToken=map.get(w_corpid);
//			OperateTextData oTxtD=new OperateTextData();

			/**如果type 是仅通知  conetnt=conetnt  数据不写入数据库 
			  *如果type 是通知加查看URL conetnt=生成url 数据写入数据库
			  */
//			if(type.equals("1")){
//
//			}else if(type.equals("0")){
//				Date time=new Date();
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				txt.setTime(sdf.parse(sdf.format(time)));
//				Integer id=oTxtD.insertTxt(txt);//刚刚插入的数据id
//				//生成url
//				String _url = "" + accessToken.getDomainName()
//						+ "/weixinweb/ShowTextServlet?wxscmid=" + w_corpid
//						+ "&id="+id;
//				_url = URLEncoder.encode(_url, "UTF-8");
//				_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
//						+ w_corpid
//						+ "&redirect_uri="
//						+ _url;
//				_url += "&response_type=code&scope=snsapi_base#wechat_redirect";
//				conetnt=_url;
//			}
			//通知Users
			zt=SendTxtToUser.wxInformUser(conetnt, users, w_corpid, w_appid, scm);
			
			IInsorgBiz insorgBiz = new InsorgBizImpl();  
			String key = insorgBiz.getWorkKey(d_corpid,d_appid);
			zt=SendTxtToUser.ddInformUser(conetnt, users, d_corpid, d_appid, scm,key);
			JSONObject json = new JSONObject();
			json.put("zt", zt);
			OutputStream outputStream = response.getOutputStream();
	        //注意编码格式，防止中文乱码
			outputStream.write(json.toString().getBytes("UTF-8"));
	        outputStream.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}