package mobile.servlet.userscm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mobile.biz.IInsorgBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.entity.AccessToken;
import mobile.entity.Insorg;
import mobile.entity.Inswaplist;
import mobile.tokenThread.TokenThread;
import mobile.util.APIAddr;
import mobile.util.HttpUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMediaUploadRequest;
import com.dingtalk.api.request.OapiMicroappListRequest;
import com.dingtalk.api.request.OapiMicroappUpdateRequest;
import com.dingtalk.api.response.OapiMediaUploadResponse;
import com.dingtalk.api.response.OapiMicroappListResponse;
import com.dingtalk.api.response.OapiMicroappListResponse.Applist;
import com.dingtalk.api.response.OapiMicroappUpdateResponse;
import com.taobao.api.FileItem;

/**
 * 
 * ClassName: AddScmServlet
 * @Description: 功能描述: 同步钉钉微信应用配置信息 
 * company:北京斯坦德科技发展有限公司
 * @date 2018年9月10日下午1:43:48
 */
public class AddScmServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = LoggerFactory.getLogger(AddScmServlet.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static OapiMicroappListResponse ddApp = null;
	IInsorgBiz insorgBiz = new InsorgBizImpl();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	/**
	 * 有新的公司加入
	 **/
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			String zt = "";
			// 流里面拿
			String jsonstr = "";
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			jsonstr = br.readLine();
			jsonstr = URLDecoder.decode(jsonstr, "UTF-8");
			isr.close();
			is.close();
			// 去除数据中的换行符
			jsonstr = jsonstr.replaceAll("\r\n", "");
			jsonstr = jsonstr.replaceAll("\r", "");
			jsonstr = jsonstr.replaceAll("\n", "");
			jsonstr = jsonstr.replaceAll("\t", ""); 
			JSONObject jsonObject = JSONObject.fromObject(jsonstr);
			log.info(jsonstr);
			String dbid = "01";
			if (jsonObject.containsKey("dbid"))
				dbid = jsonObject.getString("dbid");
			String c_corp = jsonObject.getString("c_corp");// 集团编码
			String w_corpid = jsonObject.getString("w_corpid");// 微信企业号标识
			String w_secret = jsonObject.getString("w_secret");// 微信管理组标识
			String d_corpid = jsonObject.getString("d_corpid");// 钉钉企业号标识
			String d_secret = jsonObject.getString("d_secret");// 钉钉secret秘钥
			String CompanyId = jsonObject.getString("companyid");// 公司标识
			String w_trusturl = jsonObject.getString("w_trusturl");// 微信url(域名)
			String d_trusturl = jsonObject.getString("d_trusturl");// 钉钉url(域名)
			if (w_trusturl.indexOf("http://") == -1)
				w_trusturl = "http://" + w_trusturl;
			if (d_trusturl.indexOf("http://") == -1)
				d_trusturl = "http://" + d_trusturl;
			String serverurl = jsonObject.getString("serverurl");// 数据信息来源地址

			if(w_corpid!=null&&(w_corpid.equals("")||w_corpid.equals("null")||w_corpid.equals("\"null\""))){
				w_corpid=null;
			}
			if(w_secret!=null&&(w_secret.equals("")||w_secret.equals("null")||w_secret.equals("\"null\""))){
				w_secret=null;
			}
			if(d_corpid!=null&&(d_corpid.equals("")||d_corpid.equals("null")||d_corpid.equals("\"null\""))){
				d_corpid=null;
			}
			if(d_secret!=null&&(d_secret.equals("")||d_secret.equals("null")||d_secret.equals("\"null\""))){
				d_secret=null;
			}
			
			// 获取应用信息
			JSONArray arry = JSONArray.fromObject(jsonObject.get("app"));
			Inswaplist inswaplist = null; 
			
			// 检查公司微/钉钉信息是否储存在本地数据库
			Insorg insorg = new Insorg();
			String insorgUUID =UUID.randomUUID().toString().replaceAll("-", ""); 
			insorg.setUuid(insorgUUID); 
			insorg.setOrgcode(CompanyId);
			insorg.setC_corp(c_corp);
			insorg.setW_corpid(w_corpid);
			insorg.setW_secret(w_secret);
			insorg.setW_trusturl(w_trusturl);
			insorg.setD_corpid(d_corpid);
			insorg.setD_secret(d_secret);
			insorg.setD_trusturl(d_trusturl);
			insorg.setServerurl(serverurl);
			insorgUUID = insorgBiz.upOrInInsorg(insorg);
			 
			insorgBiz.delInsorgList(insorgUUID);
			for (int i = 0; i < arry.size(); i++) {
				JSONObject json = arry.getJSONObject(i);
				inswaplist = new Inswaplist();
				String Secret = ""; 
				String wapno = json.getString("wapno");
				String appid = json.getString("w_applyid");
				String orgcode = json.getString("companyid");
				String d_applyid = json.getString("d_applyid");
				String d_appkey  = json.getString("d_appkey");
				String d_appsecret  = json.getString("d_appsecret");
				
				
				// 获取微信应用的消息
				inswaplist.setUuid(insorgUUID);
				inswaplist.setW_applyid(appid);
				inswaplist.setWapno(wapno);
				inswaplist.setOrgcode(orgcode);
				inswaplist.setW_corpid(w_corpid);
				inswaplist.setW_appsecret(Secret);
				inswaplist.setD_applyid(d_applyid);
				inswaplist.setD_appkey(d_appkey);
				inswaplist.setD_appsecret(d_appsecret);
				inswaplist.setDbid(dbid); 

				int num = insorgBiz.upOrInInsorgList(inswaplist);
				if(!inswaplist.getW_applyid().equals("00") && num>0 && !inswaplist.getD_applyid().equals("00")){
					upWXAppUrl(appid, w_corpid, wapno, w_trusturl); 
					String ddappid = inswaplist.getD_applyid();
					upDDAppUrl(ddappid, d_appkey, wapno, d_trusturl); 
					zt = "0;完成";
				}
			}
			OutputStream outputStream = response.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(zt.getBytes("UTF-8"));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			OutputStream outputStream = response.getOutputStream();
			// 注意编码格式，防止中文乱码
			String zt = "-1;同步失败！！";
			outputStream.write(zt.getBytes("UTF-8"));
			outputStream.close();
		}
	}

	/**
	 * 修改微信应用访问地址
	 * @param appid     应用编码
	 * @param corpid    企业唯一标识
	 * @param bipAppid  BIP应用编码
	 * @param redirect_domain URL地址
	 * @return
	 */
	public void upWXAppUrl(String appid,String corpid,String bipAppid,String redirect_domain){
		try {
			if(corpid == null || corpid.equals(""))
				return;
			AccessToken acc = TokenThread.getWXAccToken(corpid+"-"+appid);
			if(acc == null)
				return;
			String url = APIAddr.WX_APP_UPDATE;
			url = url.replace("ACCESS_TOKEN", acc.getW_accessToken());
			String aa = redirect_domain+"WLoginServlet?corpId="+corpid+"&appId="+appid+"&bipAppId="+bipAppid;
			aa = URLEncoder.encode(aa, "UTF-8");
			String home = APIAddr.WX_OAUTH2;
			home = home.replace("CORPID", corpid).replace("AGENTID", appid).replace("REDIRECT_URI", aa);
			String outStr = "{\"agentid\": \""+appid+"\",\"home_url\":\""+home+"\"}";
			JSONObject json = HttpUtil.httpRequest(url, "POST", outStr);
			log.info(json.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改钉钉应用访问地址
	 * @param appid     应用编码
	 * @param corpid    企业唯一标识
	 * @param bipAppid  BIP应用编码
	 * @param redirect_domain URL地址
	 * @return
	 */
	public void upDDAppUrl(String appid,String corpid,String bipAppid,String redirect_domain){
		try {
			if(corpid ==null ||corpid.equals(""))
				return;
			AccessToken acc = TokenThread.getDDAccToken(corpid+"-"+appid);
			if(acc == null)
				return;
			DefaultDingTalkClient  client1 = new DefaultDingTalkClient(APIAddr.DD_APP_LIST);
			OapiMicroappListRequest req1 = new OapiMicroappListRequest();
			ddApp = client1.execute(req1,acc.getD_accessToken()); 
		
			String appName = "";
			String appDesc = "";
			if(ddApp.getAppList() !=null)
			for(int i = 0;i<ddApp.getAppList().size();i++){
				Applist applist =ddApp.getAppList().get(i);
				if(applist.getAgentId() .equals( Long.valueOf(appid))){
					appName = applist.getName();
					appDesc = applist.getAppDesc();
					break;
				}
			}
			String logo = insorgBiz.getDDLogo(corpid, appid); 
			if(logo == null || logo.equals(""))
				logo = uploadDDLogo(appid,corpid,bipAppid);
			String home = redirect_domain+"login/dlogin.jsp?corpId="+corpid+"&appId="+appid+"&bipAppId="+bipAppid;
			DingTalkClient  client = new DefaultDingTalkClient(APIAddr.DD_APP_UPDATE);
			OapiMicroappUpdateRequest req = new OapiMicroappUpdateRequest();
			req.setAppName(appName);
			req.setAppDesc(appDesc);
			req.setAppIcon(logo);
			req.setHomepageUrl(home);
			req.setOmpLink(home);
			req.setPcHomepageUrl(home);
			req.setAgentId(Long.valueOf(appid));
			OapiMicroappUpdateResponse response = client.execute(req,acc.getD_accessToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//上传钉钉应用logo
	private String uploadDDLogo(String appid,String corpid,String bipAppid){
		String logo = "";
		try {
			String dir = AddScmServlet.class.getResource("/").getPath()+"img/";
			FileItem file  = null;
			if(bipAppid.equals("01"))
				file =new FileItem(dir+"sp.png");
			else if(bipAppid.equals("04"))
				file =new FileItem(dir+"gg.png");
			else if(bipAppid.equals("03"))
				file =new FileItem(dir+"xx.png");
			AccessToken acc = TokenThread.getDDAccToken(corpid+"-"+appid);
			DingTalkClient  client = new DefaultDingTalkClient(APIAddr.DD_UPLOAD_FILE);
			OapiMediaUploadRequest request = new OapiMediaUploadRequest();
			request.setType("image");
			request.setMedia(file);
			OapiMediaUploadResponse response = client.execute(request,acc.getD_accessToken());
			logo = response.getMediaId(); 
			insorgBiz.upDDLogo(corpid, appid, logo);
			return logo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logo;
	}
}