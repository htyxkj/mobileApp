package mobile.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import mobile.entity.AccessToken;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpUtil {
	private static Logger log = LoggerFactory.getLogger(HttpUtil.class);  
	// 获取access_token的接口地址（GET） 限200（次/天）  
    public final static String wxaccess_token_url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=Corpid&corpsecret=Corpsecret";
    public final static String ddaccess_token_url = "https://oapi.dingtalk.com/gettoken?corpid=CORPID&corpsecret=SECRECT";
	
	/** 
     * 通过httpclient发起http请求并获取结果 
     *  
     * @param url 请求地址 
     * @param Map 提交的数据 
     * @return String 
     */
	public static String httpclient(String url,Map<String, String > map) throws Exception{
			String strfhz = null;
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			CloseableHttpResponse response2 =null;
		try {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			//设置参数  
	        Iterator iterator = map.entrySet().iterator();  
	        while(iterator.hasNext()){  
	            Entry<String,String> elem = (Entry<String, String>) iterator.next();  
	            nvps.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
	        }
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response2 = httpclient.execute(httpPost);
		    HttpEntity entity2 = response2.getEntity();
		    strfhz=EntityUtils.toString(entity2);
		}catch(Exception e){
			log.info(e.toString());
		}finally {
			response2.close();
		}
        return strfhz;
	}
	
	/** 
     * 发起http请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */
	public static  String writeResponse(String urlString, String requestMethod, String info)
			throws Exception {
			info = URLEncoder.encode(info,"utf-8");
			String outputStr = info;
			StringBuffer buffer = new StringBuffer();
			InputStream inputStream = null;
			
			URL url = new URL(urlString);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			//application/x-www-form-urlencoded
			httpUrlConn.setRequestProperty("Content-Type","text/html;charset=UTF-8");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
//			int statusCode = httpUrlConn.getResponseCode();
//			System.out.println(statusCode);
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			return buffer.toString();
		}
	/** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {
            //创建SSLContext对象,并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new Certificate() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            //从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  

            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
            httpUrlConn.setRequestProperty("Content-Type","application/json;charset=UTF-8");
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  

            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }
            //将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin服务器连接超时。");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }
        log.info(jsonObject.toString());
        return jsonObject;  
    }
 
    /** 
     * 获取微信access_token 
     * @param appid 凭证 
     * @param appsecret 密钥 
     * @return 
     */  
    public static AccessToken getWxAccessToken(String corpid, String corpsecret) {  
        AccessToken accessToken = null;  
        String requestUrl = wxaccess_token_url.replace("Corpid", corpid).replace("Corpsecret", corpsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        //如果请求成功
        if (null != jsonObject) {  
            try {
            	if(!jsonObject.getString("errcode").equals("0")){
            		log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            		return null;
            	}
                accessToken = new AccessToken();
                accessToken.setW_accessToken(jsonObject.getString("access_token"));
                accessToken.setW_expiresTime(jsonObject.getLong("expires_in"));
            } catch (JSONException e) {  
                accessToken = null;   
            }  
        }  
        return accessToken;  
    }  
    
    /**
     * 获取钉钉access_token
     */
    public static AccessToken getDdAccessToken(String corpid, String corpsecret) {  
        AccessToken accessToken = null;  
        String requestUrl = ddaccess_token_url.replace("CORPID", corpid).replace("SECRECT", corpsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
        //如果请求成功
        if (null != jsonObject) {  
            try {
            	if(!jsonObject.getString("errcode").equals("0")){
            		log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
            		return null;
            	}
                accessToken = new AccessToken();
                accessToken.setD_accessToken(jsonObject.getString("access_token"));
                accessToken.setD_expiresTime(jsonObject.getLong("expires_in"));
            } catch (JSONException e) {  
            	accessToken = null;   
            }  
        }  
        return accessToken;  
    } 
}
