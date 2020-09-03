package mobile.tokenThread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import mobile.biz.IInsorgBiz;
import mobile.biz.impl.InsorgBizImpl;
import mobile.entity.AccessToken;
import mobile.util.HttpUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TokenThread {
 	private static final Log log = LogFactory.getLog(TokenThread.class);
    public static Map<String, AccessToken> accWXMap=new HashMap<String, AccessToken>();
    public static Map<String, AccessToken> accDDMap=new HashMap<String, AccessToken>();  
    private static IInsorgBiz insorgBiz = new InsorgBizImpl();
    
	/**
	 * 获取钉钉 授权码
     * AccessToken 
     * @param acckey 密钥
     * @return
     * dingdszywglqgzfbup5a-243772065
     * dingdszywglqgzfbup5a-243772065
     */
    public static AccessToken getDDAccToken(String acckey) { 
    	AccessToken token = accDDMap.get(acckey);
    	if(token == null) { 
    		log.info("初始化AccessToken");
			return ddAccToken(acckey); 
    	}else { 
			if(token.ddIsinvalid()) { 
				log.info("更新AccessToken时间");
    			return ddAccToken(acckey);
			}  
    	}
    	return token;
    }
	/**
	 * 获取微信 授权码
     * AccessToken 
     * @param acckey 密钥
     * @return
     */
    public static AccessToken getWXAccToken(String acckey) { 
    	AccessToken token = accWXMap.get(acckey);
    	if(token == null) {  
    		log.info("初始化AccessToken;acckey:"+acckey);
			return wxAccToken(acckey); 
    	}else { 
			if(token.wxIsinvalid()) {
				log.info("更新AccessToken时间");
    			return wxAccToken(acckey);
			} 
    	}
    	return token;
    }
    
	/**
	 * 获取钉钉 授权码
     * AccessToken 
     * @param acckey 密钥-应用编码
     * @return
     */
    private static AccessToken ddAccToken(String key) {
    	try {
    		//dingdszywglqgzfbup5a-243772065
    		//dingdszywglqgzfbup5a-243772065
    		String[] keys = key.split("-");
    		key = keys[0];
    		AccessToken accessToken  = null;
			AccessToken acc = insorgBiz.getDDInsAcc(key);
		  	log.info("获取钉钉AccessToken");
		  	if(acc !=null){
		  		accessToken = HttpUtil.getDdAccessToken(acc.getD_appkey(),acc.getD_appsecret());
	    		if (null != accessToken) {
	    			accessToken.setD_corpIDid(acc.getD_corpIDid());
	    			accessToken.setD_secret(acc.getD_secret());
	        		accessToken.setCompanyId(acc.getCompanyId());//公司标识
	        		accessToken.setDomainName(acc.getDomainName());//访问域名
	        		accessToken.setServerurl(acc.getServerurl());//信息来源地址
	        		accessToken.setDbid(acc.getDbid());//数据库连接标识
	        		accessToken.setCreateTime(new Date());
	        		accessToken.setD_applyId(keys[1]);
	        		accessToken.setD_appkey(acc.getD_appkey());
	        		accDDMap.put(accessToken.getD_appkey()+"-"+accessToken.getD_applyId(), accessToken);
	            }
		  	}
	    	return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
	/**
	 * 获取微信 授权码
     * AccessToken 
     * @param acckey 密钥-应用编码
     * @return
     */
    private static AccessToken wxAccToken(String key) {
    	String[] keys = key.split("-");
		key = keys[0];
    	//从数据库里拿每个公司微信配置信息
		AccessToken accessToken = null;
		try {
			log.info("获取微信AccessToken");
			AccessToken acc = insorgBiz.getDDInsAcc(key);
			if(acc != null)
			accessToken = HttpUtil.getWxAccessToken(acc.getW_corpIDid(), acc.getW_secret());
			if (null != accessToken) {
				accessToken.setW_applyId(acc.getW_applyId());//应用id
				accessToken.setCompanyId(acc.getCompanyId());//公司标识
				accessToken.setW_corpIDid(acc.getW_corpIDid());//企业号标识
				accessToken.setDomainName(acc.getDomainName());//访问域名
				accessToken.setServerurl(acc.getServerurl());//信息来源地址
				accessToken.setDbid(acc.getDbid());//数据库连接标识
				accessToken.setCreateTime(new Date());
				
				accWXMap.put(accessToken.getW_corpIDid()+"-"+acc.getW_applyId(), accessToken);
		    }
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
